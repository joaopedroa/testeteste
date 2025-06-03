package br.com.itau.geradornotafiscal.core.strategies.notaFiscal;

import br.com.itau.geradornotafiscal.core.model.ItemNotaFiscal;
import br.com.itau.geradornotafiscal.core.strategies.ProcessaNotaFiscalStrategy;
import br.com.itau.geradornotafiscal.dataprovider.calculadora.CalculadoraAliquotaProduto;
import br.com.itau.geradornotafiscal.entrypoint.domains.Pedido;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("NOTA_FISCAL_PF")
public class ProcessaNotaFiscalStrategyPessoaFisica implements ProcessaNotaFiscalStrategy {

    @Override
    public List<ItemNotaFiscal> processarNotaFiscal(Pedido pedido) {
        double valorTotalItens = pedido.valorTotalItens();
        double aliquota;

        if (valorTotalItens < 500) {
            aliquota = 0;
        } else if (valorTotalItens <= 2000) {
            aliquota = 0.12;
        } else if (valorTotalItens <= 3500) {
            aliquota = 0.15;
        } else {
            aliquota = 0.17;
        }
        return new CalculadoraAliquotaProduto().calcularAliquota(pedido.itens(), aliquota);
    }
}
