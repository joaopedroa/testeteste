package br.com.itau.geradornotafiscal.core.usecases;

import br.com.itau.geradornotafiscal.core.enums.Finalidade;
import br.com.itau.geradornotafiscal.core.enums.TipoPessoa;
import br.com.itau.geradornotafiscal.core.model.Destinatario;
import br.com.itau.geradornotafiscal.core.model.Endereco;
import br.com.itau.geradornotafiscal.core.model.ItemNotaFiscal;
import br.com.itau.geradornotafiscal.core.model.NotaFiscal;
import br.com.itau.geradornotafiscal.core.ports.out.*;
import br.com.itau.geradornotafiscal.core.strategies.ProcessaNotaFiscalStrategy;
import br.com.itau.geradornotafiscal.entrypoint.domains.Pedido;
import lombok.RequiredArgsConstructor;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static br.com.itau.geradornotafiscal.core.constants.GestaoNotaFiscalConstants.NOTA_FISCAL;

@Service
@RequiredArgsConstructor
public class GeradorNotaFiscalUseCase {

    private final Map<String, ProcessaNotaFiscalStrategy> processaNotaFiscalStrategies;
    private final AsyncTaskExecutor asyncTaskExecutor;

    private final EntregaPort entregaPort;
    private final EntregaIntegrationPort entregaIntegrationPort;
    private final EstoquePort estoquePort;
    private final FinanceiroPort financeiroPort;
    private final RegistroPort registroPort;

    public NotaFiscal gerarNotaFiscal(Pedido pedido) {

        Destinatario destinatario = pedido.destinatario();
        TipoPessoa tipoPessoa = destinatario.getTipoPessoa();

        var strategyNotaFiscal = this.processaNotaFiscalStrategies.get(NOTA_FISCAL + tipoPessoa.getCodigoTipoPessoa());
        List<ItemNotaFiscal> itemNotaFiscalList = strategyNotaFiscal.processarNotaFiscal(pedido);

        //Regras diferentes para frete
        var finalidadesValidasParaProcessamento = List.of(Finalidade.ENTREGA, Finalidade.COBRANCA_ENTREGA);

        var valorFreteComPercentual = destinatario.getEnderecos().stream()
                .filter(endereco -> finalidadesValidasParaProcessamento.contains(endereco.getFinalidade()))
                .map(Endereco::getRegiao)
                .findFirst()
                .map(x -> x.calcularValorFrete(pedido.valorFrete()))
                .orElse(0.0);

        // Create the NotaFiscal object
        String idNotaFiscal = UUID.randomUUID().toString();

        NotaFiscal notaFiscal = NotaFiscal.builder()
                .idNotaFiscal(idNotaFiscal)
                .data(LocalDateTime.now())
                .valorTotalItens(pedido.valorTotalItens())
                .valorFrete(valorFreteComPercentual)
                .itens(itemNotaFiscalList)
                .destinatario(pedido.destinatario())
                .build();

        var envioNotaFiscalParaBaixa = asyncTaskExecutor.submitCompletable(() -> estoquePort.enviarNotaFiscalParaBaixaEstoque(notaFiscal));
        var registroNotaFiscal = asyncTaskExecutor.submitCompletable(() -> registroPort.registrarNotaFiscal(notaFiscal));
        var entregaService = asyncTaskExecutor.submitCompletable(() -> entregaPort.agendarEntrega(notaFiscal));
        var enviarNotaFiscal = asyncTaskExecutor.submitCompletable(() -> financeiroPort.enviarNotaFiscalParaContasReceber(notaFiscal));

        CompletableFuture.allOf(envioNotaFiscalParaBaixa, registroNotaFiscal, entregaService, enviarNotaFiscal).join();

        return notaFiscal;
    }
}
