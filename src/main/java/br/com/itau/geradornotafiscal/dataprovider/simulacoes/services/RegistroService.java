package br.com.itau.geradornotafiscal.dataprovider.simulacoes.services;

import br.com.itau.geradornotafiscal.core.model.NotaFiscal;
import org.springframework.stereotype.Service;

@Service
public class RegistroService {

    public void registrarNotaFiscal(NotaFiscal notaFiscal) {

        try {
            //Simula o registro da nota fiscal
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
