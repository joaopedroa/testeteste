package br.com.itau.geradornotafiscal.core.strategies.notaFiscal;

import br.com.itau.geradornotafiscal.core.model.ItemNotaFiscal;
import br.com.itau.geradornotafiscal.core.strategies.ProcessaNotaFiscalStrategy;
import br.com.itau.geradornotafiscal.core.strategies.RegimeTributacaoStrategy;
import br.com.itau.geradornotafiscal.entrypoint.domains.Pedido;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static br.com.itau.geradornotafiscal.core.constants.GestaoNotaFiscalConstants.REGIME_TRIBUTACAO;

@Service("NOTA_FISCAL_PJ")
@RequiredArgsConstructor
public class ProcessaNotaFiscalStrategyPessoaJuridica implements ProcessaNotaFiscalStrategy {

    private final Map<String, RegimeTributacaoStrategy> regimeTributacaoStrategies;

    @Override
    public List<ItemNotaFiscal> processarNotaFiscal(Pedido pedido) {

        return regimeTributacaoStrategies.get(REGIME_TRIBUTACAO + pedido.destinatario().getRegimeTributacao())
                .processarNotaFiscal(pedido);

    }
}
