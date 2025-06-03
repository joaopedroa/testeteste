package br.com.itau.geradorNotaFiscal.core.usecases;

import br.com.itau.geradornotafiscal.core.enums.Finalidade;
import br.com.itau.geradornotafiscal.core.enums.Regiao;
import br.com.itau.geradornotafiscal.core.enums.TipoPessoa;
import br.com.itau.geradornotafiscal.core.model.Destinatario;
import br.com.itau.geradornotafiscal.core.model.Endereco;
import br.com.itau.geradornotafiscal.core.model.ItemNotaFiscal;
import br.com.itau.geradornotafiscal.core.model.NotaFiscal;
import br.com.itau.geradornotafiscal.core.ports.out.*;
import br.com.itau.geradornotafiscal.core.strategies.ProcessaNotaFiscalStrategy;
import br.com.itau.geradornotafiscal.core.usecases.GeradorNotaFiscalUseCase;
import br.com.itau.geradornotafiscal.entrypoint.domains.Pedido;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.task.AsyncTaskExecutor;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GeradorNotaFiscalUseCaseTest {

    @Mock
    private Map<String, ProcessaNotaFiscalStrategy> processaNotaFiscalStrategies;

    @Mock
    private AsyncTaskExecutor asyncTaskExecutor;

    @Mock
    private EntregaPort entregaPort;

    @Mock
    private EntregaIntegrationPort entregaIntegrationPort;

    @Mock
    private EstoquePort estoquePort;

    @Mock
    private FinanceiroPort financeiroPort;

    @Mock
    private RegistroPort registroPort;

    @Mock
    private ProcessaNotaFiscalStrategy strategyNotaFiscal;

    @InjectMocks
    private GeradorNotaFiscalUseCase geradorNotaFiscalUseCase;

    private Pedido pedido;
    private Destinatario destinatario;
    private Endereco endereco;
    private Regiao regiao;

    @BeforeEach
    void setUp() {
        // Setup do destinatário
        destinatario = new Destinatario();
        destinatario.setTipoPessoa(TipoPessoa.FISICA);

        regiao = Regiao.SUDESTE;

        // Setup do endereço
        endereco = new Endereco();
        endereco.setFinalidade(Finalidade.ENTREGA);
        endereco.setRegiao(regiao);
        destinatario.setEnderecos(List.of(endereco));

        // Setup do pedido
        pedido = Pedido.builder()
                .idPedido(1)
                .data(LocalDate.now())
                .valorTotalItens(1000.0)
                .valorFrete(50.0)
                .itens(List.of())
                .destinatario(destinatario)
                .build();

        // Setup do strategy
        when(processaNotaFiscalStrategies.get(any())).thenReturn(strategyNotaFiscal);
        when(strategyNotaFiscal.processarNotaFiscal(any())).thenReturn(List.of());
        when(asyncTaskExecutor.submitCompletable(any(Runnable.class))).thenReturn(CompletableFuture.completedFuture(null));

    }

    @Test
    @DisplayName("Deve gerar nota fiscal com sucesso")
    void deveGerarNotaFiscalComSucesso() {
        // Act
        NotaFiscal notaFiscal = geradorNotaFiscalUseCase.gerarNotaFiscal(pedido);

        // Assert
        assertNotNull(notaFiscal);
        assertEquals(pedido.valorTotalItens(), notaFiscal.getValorTotalItens());
        assertEquals(52.400000000000006, notaFiscal.getValorFrete()); // 50.0 + 10%
        assertEquals(pedido.destinatario(), notaFiscal.getDestinatario());
        assertNotNull(notaFiscal.getIdNotaFiscal());
        assertNotNull(notaFiscal.getData());

    }

    @Test
    @DisplayName("Deve processar nota fiscal com strategy correta")
    void deveProcessarNotaFiscalComStrategyCorreta() {
        // Arrange
        String expectedKey = "NOTA_FISCAL" + TipoPessoa.FISICA.getCodigoTipoPessoa();
        List<ItemNotaFiscal> expectedItems = List.of(
                ItemNotaFiscal.builder().idItem("UUID").build()
        );
        when(strategyNotaFiscal.processarNotaFiscal(pedido)).thenReturn(expectedItems);

        // Act
        NotaFiscal notaFiscal = geradorNotaFiscalUseCase.gerarNotaFiscal(pedido);

        // Assert
        assertEquals(expectedItems, notaFiscal.getItens());
    }

    @Test
    @DisplayName("Deve gerar nota fiscal com múltiplos endereços")
    void deveGerarNotaFiscalComMultiplosEnderecos() {
        // Arrange
        Endereco endereco2 = new Endereco();
        endereco2.setFinalidade(Finalidade.COBRANCA_ENTREGA);
        Regiao regiao2 = Regiao.NORDESTE;
        endereco2.setRegiao(regiao2);
        destinatario.setEnderecos(List.of(endereco, endereco2));

        // Act
        NotaFiscal notaFiscal = geradorNotaFiscalUseCase.gerarNotaFiscal(pedido);

        // Assert
        assertEquals(52.400000000000006, notaFiscal.getValorFrete());
    }
}
