package br.com.itau.geradorNotaFiscal.dataprovider.simulacoes.adapters;

import br.com.itau.geradornotafiscal.core.model.Item;
import br.com.itau.geradornotafiscal.core.model.ItemNotaFiscal;
import br.com.itau.geradornotafiscal.core.model.NotaFiscal;
import br.com.itau.geradornotafiscal.dataprovider.simulacoes.adapters.EntregaIntegrationAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class EntregaIntegrationAdapterTest {

    private EntregaIntegrationAdapter entregaIntegrationAdapter;
    private NotaFiscal notaFiscal;

    @BeforeEach
    void setUp() {
        entregaIntegrationAdapter = new EntregaIntegrationAdapter();
        notaFiscal = new NotaFiscal();
        notaFiscal.setItens(new ArrayList<>());
    }

    @Test
    @DisplayName("Deve criar agendamento com sucesso para nota fiscal com poucos itens")
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    void deveCriarAgendamentoComSucessoParaPoucosItens() {
        // Arrange
        List<ItemNotaFiscal> itens = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            itens.add(new ItemNotaFiscal());
        }
        notaFiscal.setItens(itens);

        // Act & Assert
        assertDoesNotThrow(() -> entregaIntegrationAdapter.criarAgendamentoEntrega(notaFiscal));
    }

    @Test
    @DisplayName("Deve criar agendamento com sucesso para nota fiscal com muitos itens")
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    void deveCriarAgendamentoComSucessoParaMuitosItens() {
        // Arrange
        List<ItemNotaFiscal> itens = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            itens.add(new ItemNotaFiscal());
        }
        notaFiscal.setItens(itens);

        // Act & Assert
        assertDoesNotThrow(() -> entregaIntegrationAdapter.criarAgendamentoEntrega(notaFiscal));
    }

    @Test
    @DisplayName("Deve lançar RuntimeException quando Thread.sleep é interrompido")
    void deveLancarRuntimeExceptionQuandoInterrompido() {
        // Arrange
        Thread.currentThread().interrupt();

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> entregaIntegrationAdapter.criarAgendamentoEntrega(notaFiscal)
        );

        assertTrue(exception.getCause() instanceof InterruptedException);
    }

     @Test
    @DisplayName("Deve ter tempo de execução diferente para notas com mais de 5 itens")
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    void deveTerTempoExecucaoDiferenteParaNotasComMaisDe5Itens() {
        // Arrange
        List<ItemNotaFiscal> itensPoucos = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            itensPoucos.add(new ItemNotaFiscal());
        }

        List<ItemNotaFiscal> itensMuitos = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            itensMuitos.add(new ItemNotaFiscal());
        }

        NotaFiscal notaPoucosItens = new NotaFiscal();
        notaPoucosItens.setItens(itensPoucos);

        NotaFiscal notaMuitosItens = new NotaFiscal();
        notaMuitosItens.setItens(itensMuitos);

        // Act & Assert
        long inicioPoucos = System.currentTimeMillis();
        entregaIntegrationAdapter.criarAgendamentoEntrega(notaPoucosItens);
        long fimPoucos = System.currentTimeMillis();

        long inicioMuitos = System.currentTimeMillis();
        entregaIntegrationAdapter.criarAgendamentoEntrega(notaMuitosItens);
        long fimMuitos = System.currentTimeMillis();

        long tempoPoucos = fimPoucos - inicioPoucos;
        long tempoMuitos = fimMuitos - inicioMuitos;

        // Verifica se o tempo de execução é diferente
        assertNotEquals(tempoPoucos, tempoMuitos,
                "O tempo de execução deve ser diferente para notas com quantidade diferente de itens");
    }
}
