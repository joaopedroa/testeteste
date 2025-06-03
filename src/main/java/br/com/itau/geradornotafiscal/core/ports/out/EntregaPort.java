package br.com.itau.geradornotafiscal.core.ports.out;

import br.com.itau.geradornotafiscal.core.model.NotaFiscal;

public interface EntregaPort {

    void agendarEntrega(NotaFiscal notaFiscal);
}
