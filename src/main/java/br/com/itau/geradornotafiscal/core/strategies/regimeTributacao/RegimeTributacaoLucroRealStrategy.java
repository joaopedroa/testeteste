package br.com.itau.geradornotafiscal.core.strategies.regimeTributacao;

import br.com.itau.geradornotafiscal.core.model.ItemNotaFiscal;
import br.com.itau.geradornotafiscal.core.strategies.RegimeTributacaoStrategy;
import br.com.itau.geradornotafiscal.dataprovider.calculadora.CalculadoraAliquotaProduto;
import br.com.itau.geradornotafiscal.entrypoint.domains.Pedido;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("REGIME_TRIBUTACAO_LUCRO_REAL")
public class RegimeTributacaoLucroRealStrategy implements RegimeTributacaoStrategy {

    @Override
    public List<ItemNotaFiscal> processarNotaFiscal(Pedido pedido) {
        double valorTotalItens = pedido.valorTotalItens();
        double aliquota;

        if (valorTotalItens < 1000) {
            aliquota = 0.03;
        } else if (valorTotalItens <= 2000) {
            aliquota = 0.09;
        } else if (valorTotalItens <= 5000) {
            aliquota = 0.15;
        } else {
            aliquota = 0.20;
        }
        return  new CalculadoraAliquotaProduto().calcularAliquota(pedido.itens(),aliquota);
    }
}
