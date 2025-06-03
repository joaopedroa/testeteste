package br.com.itau.geradorNotaFiscal.dataprovider.simulacoes.adapters;

import br.com.itau.geradornotafiscal.core.model.NotaFiscal;
import br.com.itau.geradornotafiscal.dataprovider.simulacoes.adapters.EstoqueAdapter;
import br.com.itau.geradornotafiscal.dataprovider.simulacoes.services.EstoqueService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EstoqueAdapterTest {

    @Mock
    private EstoqueService estoqueService;

    @InjectMocks
    private EstoqueAdapter estoqueAdapter;

    private NotaFiscal notaFiscal;

    @BeforeEach
    void setUp() {
        notaFiscal = new NotaFiscal();
    }

    @Test
    @DisplayName("Deve enviar nota fiscal para baixa de estoque com sucesso")
    void deveEnviarNotaFiscalParaBaixaEstoqueComSucesso() {
        // Act
        estoqueAdapter.enviarNotaFiscalParaBaixaEstoque(notaFiscal);

        // Assert
        verify(estoqueService, times(1)).enviarNotaFiscalParaBaixaEstoque(notaFiscal);
    }

    @Test
    @DisplayName("Deve enviar nota fiscal nula para baixa de estoque")
    void deveEnviarNotaFiscalNulaParaBaixaEstoque() {
        // Act
        estoqueAdapter.enviarNotaFiscalParaBaixaEstoque(null);

        // Assert
        verify(estoqueService, times(1)).enviarNotaFiscalParaBaixaEstoque(null);
    }

    @Test
    @DisplayName("Deve propagar exceção quando serviço lança exceção")
    void devePropagarExcecaoQuandoServicoLancaExcecao() {
        // Arrange
        doThrow(new RuntimeException("Erro no serviço"))
                .when(estoqueService).enviarNotaFiscalParaBaixaEstoque(notaFiscal);

        // Act & Assert
        assertThrows(RuntimeException.class, () ->
                estoqueAdapter.enviarNotaFiscalParaBaixaEstoque(notaFiscal)
        );

        verify(estoqueService, times(1)).enviarNotaFiscalParaBaixaEstoque(notaFiscal);
    }
}
