package br.com.itau.geradornotafiscal.dataprovider.calculadora;

import br.com.itau.geradornotafiscal.core.model.Item;
import br.com.itau.geradornotafiscal.core.model.ItemNotaFiscal;

import java.util.List;

public class CalculadoraAliquotaProduto {

    public List<ItemNotaFiscal> calcularAliquota(List<Item> items, double aliquotaPercentual) {

        return items.stream()
                .map(item -> ItemNotaFiscal.builder()
                        .idItem(item.getIdItem())
                        .descricao(item.getDescricao())
                        .valorUnitario(item.getValorUnitario())
                        .quantidade(item.getQuantidade())
                        .valorTributoItem(item.getValorUnitario() * aliquotaPercentual)
                        .build())
                .toList();
    }
}



