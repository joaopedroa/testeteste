package br.com.itau.geradornotafiscal.core.strategies;

import br.com.itau.geradornotafiscal.core.model.ItemNotaFiscal;
import br.com.itau.geradornotafiscal.entrypoint.domains.Pedido;

import java.util.List;

public interface RegimeTributacaoStrategy {

    List<ItemNotaFiscal> processarNotaFiscal(Pedido pedido);
}
