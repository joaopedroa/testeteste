package br.com.itau.geradornotafiscal.dataprovider.simulacoes.adapters;

import br.com.itau.geradornotafiscal.core.model.NotaFiscal;
import br.com.itau.geradornotafiscal.core.ports.out.RegistroPort;
import br.com.itau.geradornotafiscal.dataprovider.simulacoes.services.RegistroService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistroAdapter implements RegistroPort {

    private final RegistroService registroService;

    @Override
    public void registrarNotaFiscal(NotaFiscal notaFiscal) {
        registroService.registrarNotaFiscal(notaFiscal);
    }
}
