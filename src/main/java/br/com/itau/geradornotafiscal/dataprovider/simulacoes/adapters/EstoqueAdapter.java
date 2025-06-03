package br.com.itau.geradornotafiscal.dataprovider.simulacoes.adapters;

import br.com.itau.geradornotafiscal.core.model.NotaFiscal;
import br.com.itau.geradornotafiscal.core.ports.out.EstoquePort;
import br.com.itau.geradornotafiscal.dataprovider.simulacoes.services.EstoqueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EstoqueAdapter implements EstoquePort {

    private final EstoqueService estoqueService;

    @Override
    public void enviarNotaFiscalParaBaixaEstoque(NotaFiscal notaFiscal) {
        estoqueService.enviarNotaFiscalParaBaixaEstoque(notaFiscal);
    }
}
