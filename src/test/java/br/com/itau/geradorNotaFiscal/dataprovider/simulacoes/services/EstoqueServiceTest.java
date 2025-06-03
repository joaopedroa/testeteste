package br.com.itau.geradorNotaFiscal.dataprovider.simulacoes.services;

import br.com.itau.geradornotafiscal.core.model.NotaFiscal;
import br.com.itau.geradornotafiscal.dataprovider.simulacoes.services.EstoqueService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class EstoqueServiceTest {

    private EstoqueService estoqueService;
    private NotaFiscal notaFiscal;

    @BeforeEach
    void setUp() {
        estoqueService = new EstoqueService();
        notaFiscal = new NotaFiscal();
    }

    @Test
    @DisplayName("Deve enviar nota fiscal com sucesso")
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    void deveExecutarComSucesso() {
        // Act & Assert
        assertDoesNotThrow(() -> estoqueService.enviarNotaFiscalParaBaixaEstoque(notaFiscal));
    }

    @Test
    @DisplayName("Deve lançar RuntimeException quando Thread.sleep é interrompido")
    void deveLancarRuntimeExceptionQuandoInterrompido() {
        // Arrange
        Thread.currentThread().interrupt();

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> estoqueService.enviarNotaFiscalParaBaixaEstoque(notaFiscal)
        );

        assertTrue(exception.getCause() instanceof InterruptedException);
    }

    @Test
    @DisplayName("Não deve lançar exceção quando nota fiscal é nula")
    void naoDeveLancarExcecaoQuandoNotaFiscalNula() {
        // Act & Assert
        assertDoesNotThrow(() -> estoqueService.enviarNotaFiscalParaBaixaEstoque(null));
    }
}
