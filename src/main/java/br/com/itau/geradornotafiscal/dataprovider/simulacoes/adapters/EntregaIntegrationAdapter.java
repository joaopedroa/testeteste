package br.com.itau.geradornotafiscal.dataprovider.simulacoes.adapters;

import br.com.itau.geradornotafiscal.core.model.NotaFiscal;
import br.com.itau.geradornotafiscal.core.ports.out.EntregaIntegrationPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EntregaIntegrationAdapter implements EntregaIntegrationPort {

    @Override
    public void criarAgendamentoEntrega(NotaFiscal notaFiscal) {

        try {
            //Simula o agendamento da entrega
            if(notaFiscal.getItens().size() > 5){

                Thread.sleep(0);
            }
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
