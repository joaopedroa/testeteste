package br.com.itau.geradornotafiscal.dataprovider.simulacoes.services;

import br.com.itau.geradornotafiscal.core.model.NotaFiscal;
import org.springframework.stereotype.Service;

@Service
public class FinanceiroService {

    public void enviarNotaFiscalParaContasReceber(NotaFiscal notaFiscal) {

        try {
            //Simula o envio da nota fiscal para o contas a receber
            Thread.sleep(250);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
