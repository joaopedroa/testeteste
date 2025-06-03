package br.com.itau.geradornotafiscal.core.ports.out;

import br.com.itau.geradornotafiscal.core.model.NotaFiscal;

public interface EntregaIntegrationPort {

    void criarAgendamentoEntrega(NotaFiscal notaFiscal);
}
