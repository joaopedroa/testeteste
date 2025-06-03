package br.com.itau.geradorNotaFiscal.dataprovider.simulacoes.adapters;

import br.com.itau.geradornotafiscal.core.model.Item;
import br.com.itau.geradornotafiscal.core.model.ItemNotaFiscal;
import br.com.itau.geradornotafiscal.core.model.NotaFiscal;
import br.com.itau.geradornotafiscal.dataprovider.simulacoes.adapters.EntregaAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class EntregaAdapterTest {

    private EntregaAdapter entregaAdapter;
    private NotaFiscal notaFiscal;

    @BeforeEach
    void setUp() {
        entregaAdapter = new EntregaAdapter();
        notaFiscal = new NotaFiscal();
        notaFiscal.setItens(new ArrayList<>());
    }

    @Test
    @DisplayName("Deve agendar entrega com sucesso para nota fiscal com poucos itens")
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    void deveAgendarEntregaComSucessoParaPoucosItens() {
        // Arrange
        List<ItemNotaFiscal> itens = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            itens.add(new ItemNotaFiscal());
        }
        notaFiscal.setItens(itens);

        // Act & Assert
        assertDoesNotThrow(() -> entregaAdapter.agendarEntrega(notaFiscal));
    }

    @Test
    @DisplayName("Deve agendar entrega com sucesso para nota fiscal com muitos itens")
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    void deveAgendarEntregaComSucessoParaMuitosItens() {
        // Arrange
        List<ItemNotaFiscal> itens = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            itens.add(new ItemNotaFiscal());
        }
        notaFiscal.setItens(itens);

        // Act & Assert
        assertDoesNotThrow(() -> entregaAdapter.agendarEntrega(notaFiscal));
    }

    @Test
    @DisplayName("Deve lançar RuntimeException quando Thread.sleep é interrompido")
    void deveLancarRuntimeExceptionQuandoInterrompido() {
        // Arrange
        Thread.currentThread().interrupt();

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> entregaAdapter.agendarEntrega(notaFiscal)
        );

        assertTrue(exception.getCause() instanceof InterruptedException);
    }

}