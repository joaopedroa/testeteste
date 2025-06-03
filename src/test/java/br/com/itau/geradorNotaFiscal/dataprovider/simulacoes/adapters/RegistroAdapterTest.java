package br.com.itau.geradorNotaFiscal.dataprovider.simulacoes.adapters;
import br.com.itau.geradornotafiscal.core.model.NotaFiscal;
import br.com.itau.geradornotafiscal.dataprovider.simulacoes.adapters.RegistroAdapter;
import br.com.itau.geradornotafiscal.dataprovider.simulacoes.services.RegistroService;
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
class RegistroAdapterTest {

    @Mock
    private RegistroService registroService;

    @InjectMocks
    private RegistroAdapter registroAdapter;

    private NotaFiscal notaFiscal;

    @BeforeEach
    void setUp() {
        notaFiscal = new NotaFiscal();
    }

    @Test
    @DisplayName("Deve registrar nota fiscal com sucesso")
    void deveRegistrarNotaFiscalComSucesso() {
        // Act
        registroAdapter.registrarNotaFiscal(notaFiscal);

        // Assert
        verify(registroService, times(1)).registrarNotaFiscal(notaFiscal);
    }

    @Test
    @DisplayName("Deve registrar nota fiscal nula")
    void deveRegistrarNotaFiscalNula() {
        // Act
        registroAdapter.registrarNotaFiscal(null);

        // Assert
        verify(registroService, times(1)).registrarNotaFiscal(null);
    }

    @Test
    @DisplayName("Deve propagar exceção quando serviço de registro lança exceção")
    void devePropagarExcecaoQuandoServicoLancaExcecao() {
        // Arrange
        doThrow(new RuntimeException("Erro no serviço de registro"))
                .when(registroService).registrarNotaFiscal(notaFiscal);

        // Act & Assert
        assertThrows(RuntimeException.class, () ->
                registroAdapter.registrarNotaFiscal(notaFiscal)
        );

        verify(registroService, times(1)).registrarNotaFiscal(notaFiscal);
    }

    @Test
    @DisplayName("Deve chamar serviço de registro apenas uma vez")
    void deveChamarServicoRegistroApenasUmaVez() {
        // Act
        registroAdapter.registrarNotaFiscal(notaFiscal);

        // Assert
        verify(registroService, times(1)).registrarNotaFiscal(notaFiscal);
        verifyNoMoreInteractions(registroService);
    }

    @Test
    @DisplayName("Deve chamar serviço de registro com a mesma nota fiscal")
    void deveChamarServicoRegistroComMesmaNotaFiscal() {
        // Arrange
        NotaFiscal notaFiscalEsperada = notaFiscal;

        // Act
        registroAdapter.registrarNotaFiscal(notaFiscal);

        // Assert
        verify(registroService).registrarNotaFiscal(same(notaFiscalEsperada));
    }
}
