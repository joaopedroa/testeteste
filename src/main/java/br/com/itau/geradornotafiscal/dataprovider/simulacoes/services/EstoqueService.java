package br.com.itau.geradornotafiscal.dataprovider.simulacoes.services;

import br.com.itau.geradornotafiscal.core.model.NotaFiscal;
import org.springframework.stereotype.Service;

@Service
public class EstoqueService {
    public void enviarNotaFiscalParaBaixaEstoque(NotaFiscal notaFiscal) {
        try {
            //Simula envio de nota fiscal para baixa de estoque
            Thread.sleep(380);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
