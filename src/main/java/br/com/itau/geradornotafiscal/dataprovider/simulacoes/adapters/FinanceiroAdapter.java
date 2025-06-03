package br.com.itau.geradornotafiscal.dataprovider.simulacoes.adapters;

import br.com.itau.geradornotafiscal.core.model.NotaFiscal;
import br.com.itau.geradornotafiscal.core.ports.out.FinanceiroPort;
import br.com.itau.geradornotafiscal.dataprovider.simulacoes.services.FinanceiroService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FinanceiroAdapter implements FinanceiroPort {

    private final FinanceiroService financeiroService;

    @Override
    public void enviarNotaFiscalParaContasReceber(NotaFiscal notaFiscal) {
        financeiroService.enviarNotaFiscalParaContasReceber(notaFiscal);
    }
}
