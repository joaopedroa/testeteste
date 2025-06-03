package br.com.itau.geradorNotaFiscal.entrypoint.resources;

import br.com.itau.geradornotafiscal.core.model.Destinatario;
import br.com.itau.geradornotafiscal.core.model.NotaFiscal;
import br.com.itau.geradornotafiscal.core.usecases.GeradorNotaFiscalUseCase;
import br.com.itau.geradornotafiscal.entrypoint.domains.Pedido;
import br.com.itau.geradornotafiscal.entrypoint.resources.GeradorNFResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GeradorNFResourceTest {

    @Mock
    private GeradorNotaFiscalUseCase notaFiscalUseCase;

    @InjectMocks
    private GeradorNFResource geradorNFResource;

    private Pedido pedido;
    private NotaFiscal notaFiscal;

    @BeforeEach
    void setUp() {
        pedido = Pedido.builder()
                .idPedido(1)
                .data(LocalDate.now())
                .valorTotalItens(1000.0)
                .valorFrete(50.0)
                .itens(List.of())
                .destinatario(new Destinatario())
                .build();

        notaFiscal = new NotaFiscal();
    }

    @Test
    @DisplayName("Deve gerar nota fiscal com sucesso")
    void deveGerarNotaFiscalComSucesso() {
        // Arrange
        when(notaFiscalUseCase.gerarNotaFiscal(pedido)).thenReturn(notaFiscal);

        // Act
        ResponseEntity<NotaFiscal> response = geradorNFResource.gerarNotaFiscal(pedido);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(notaFiscal, response.getBody());
        verify(notaFiscalUseCase, times(1)).gerarNotaFiscal(pedido);
    }

    @Test
    @DisplayName("Deve retornar nota fiscal nula quando useCase retorna nulo")
    void deveRetornarNotaFiscalNula() {
        // Arrange
        when(notaFiscalUseCase.gerarNotaFiscal(pedido)).thenReturn(null);

        // Act
        ResponseEntity<NotaFiscal> response = geradorNFResource.gerarNotaFiscal(pedido);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
        verify(notaFiscalUseCase, times(1)).gerarNotaFiscal(pedido);
    }

    @Test
    @DisplayName("Deve lançar exceção quando useCase lança exceção")
    void deveLancarExcecao() {
        // Arrange
        when(notaFiscalUseCase.gerarNotaFiscal(pedido))
                .thenThrow(new RuntimeException("Erro ao gerar nota fiscal"));

        // Act & Assert
        assertThrows(RuntimeException.class, () ->
                geradorNFResource.gerarNotaFiscal(pedido)
        );

        verify(notaFiscalUseCase, times(1)).gerarNotaFiscal(pedido);
    }
}