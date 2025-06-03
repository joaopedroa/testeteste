package br.com.itau.geradorNotaFiscal.dataprovider.simulacoes.adapters;

import br.com.itau.geradornotafiscal.core.model.NotaFiscal;
import br.com.itau.geradornotafiscal.dataprovider.simulacoes.adapters.FinanceiroAdapter;
import br.com.itau.geradornotafiscal.dataprovider.simulacoes.services.FinanceiroService;
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
class FinanceiroAdapterTest {

    @Mock
    private FinanceiroService financeiroService;

    @InjectMocks
    private FinanceiroAdapter financeiroAdapter;

    private NotaFiscal notaFiscal;

    @BeforeEach
    void setUp() {
        notaFiscal = new NotaFiscal();
    }

    @Test
    @DisplayName("Deve enviar nota fiscal para contas a receber com sucesso")
    void deveEnviarNotaFiscalParaContasReceberComSucesso() {
        // Act
        financeiroAdapter.enviarNotaFiscalParaContasReceber(notaFiscal);

        // Assert
        verify(financeiroService, times(1)).enviarNotaFiscalParaContasReceber(notaFiscal);
    }

    @Test
    @DisplayName("Deve enviar nota fiscal nula para contas a receber")
    void deveEnviarNotaFiscalNulaParaContasReceber() {
        // Act
        financeiroAdapter.enviarNotaFiscalParaContasReceber(null);

        // Assert
        verify(financeiroService, times(1)).enviarNotaFiscalParaContasReceber(null);
    }

    @Test
    @DisplayName("Deve propagar exceção quando serviço financeiro lança exceção")
    void devePropagarExcecaoQuandoServicoLancaExcecao() {
        // Arrange
        doThrow(new RuntimeException("Erro no serviço financeiro"))
                .when(financeiroService).enviarNotaFiscalParaContasReceber(notaFiscal);

        // Act & Assert
        assertThrows(RuntimeException.class, () ->
                financeiroAdapter.enviarNotaFiscalParaContasReceber(notaFiscal)
        );

        verify(financeiroService, times(1)).enviarNotaFiscalParaContasReceber(notaFiscal);
    }

    @Test
    @DisplayName("Deve chamar serviço financeiro apenas uma vez")
    void deveChamarServicoFinanceiroApenasUmaVez() {
        // Act
        financeiroAdapter.enviarNotaFiscalParaContasReceber(notaFiscal);
        financeiroAdapter.enviarNotaFiscalParaContasReceber(notaFiscal);

        // Assert
        verify(financeiroService, times(2)).enviarNotaFiscalParaContasReceber(notaFiscal);
        verifyNoMoreInteractions(financeiroService);
    }
}
