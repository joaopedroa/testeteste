package br.com.itau.geradorNotaFiscal.dataprovider.calculadora;

import br.com.itau.geradornotafiscal.core.model.Item;
import br.com.itau.geradornotafiscal.core.model.ItemNotaFiscal;
import br.com.itau.geradornotafiscal.dataprovider.calculadora.CalculadoraAliquotaProduto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CalculadoraAliquotaProdutoTest {

    private CalculadoraAliquotaProduto calculadora;
    private List<Item> items;

    @BeforeEach
    void setUp() {
        calculadora = new CalculadoraAliquotaProduto();
        items = new ArrayList<>();
    }

    @Test
    @DisplayName("Deve calcular alíquota corretamente para um item")
    void deveCalcularAliquotaCorretamenteParaUmItem() {
        // Arrange
        Item item = Item.builder()
                .idItem("UUID")
                .descricao("Produto Teste")
                .valorUnitario(100.0)
                .quantidade(2)
                .build();
        items.add(item);
        double aliquotaPercentual = 0.12; // 12%

        // Act
        List<ItemNotaFiscal> resultado = calculadora.calcularAliquota(items, aliquotaPercentual);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());

        ItemNotaFiscal itemNotaFiscal = resultado.getFirst();
        assertEquals(item.getIdItem(), itemNotaFiscal.getIdItem());
        assertEquals(item.getDescricao(), itemNotaFiscal.getDescricao());
        assertEquals(item.getValorUnitario(), itemNotaFiscal.getValorUnitario());
        assertEquals(item.getQuantidade(), itemNotaFiscal.getQuantidade());
        assertEquals(12.0, itemNotaFiscal.getValorTributoItem()); // 100 * 0.12
    }

    @Test
    @DisplayName("Deve calcular alíquota para múltiplos itens")
    void deveCalcularAliquotaParaMultiplosItens() {
        // Arrange
        items.add(Item.builder()
                .idItem("UUID")
                .descricao("Produto 1")
                .valorUnitario(100.0)
                .quantidade(1)
                .build());
        items.add(Item.builder()
                .idItem("UUID")
                .descricao("Produto 2")
                .valorUnitario(200.0)
                .quantidade(2)
                .build());
        double aliquotaPercentual = 0.15; // 15%

        // Act
        List<ItemNotaFiscal> resultado = calculadora.calcularAliquota(items, aliquotaPercentual);

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());

        // Verifica primeiro item
        ItemNotaFiscal item1 = resultado.getFirst();
        assertEquals("UUID", item1.getIdItem());
        assertEquals(15.0, item1.getValorTributoItem()); // 100 * 0.15

        // Verifica segundo item
        ItemNotaFiscal item2 = resultado.get(1);
        assertEquals("UUID", item2.getIdItem());
        assertEquals(30.0, item2.getValorTributoItem()); // 200 * 0.15
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando lista de itens é vazia")
    void deveRetornarListaVaziaQuandoListaItensVazia() {
        // Act
        List<ItemNotaFiscal> resultado = calculadora.calcularAliquota(items, 0.12);

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("Deve calcular com alíquota zero")
    void deveCalcularComAliquotaZero() {
        // Arrange
        Item item = Item.builder()
                .idItem("UUID")
                .descricao("Produto Teste")
                .valorUnitario(100.0)
                .quantidade(1)
                .build();
        items.add(item);

        // Act
        List<ItemNotaFiscal> resultado = calculadora.calcularAliquota(items, 0.0);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(0.0, resultado.getFirst().getValorTributoItem());
    }

    @Test
    @DisplayName("Deve calcular com alíquota negativa")
    void deveCalcularComAliquotaNegativa() {
        // Arrange
        Item item = Item.builder()
                .idItem("UUID")
                .descricao("Produto Teste")
                .valorUnitario(100.0)
                .quantidade(1)
                .build();
        items.add(item);
        double aliquotaNegativa = -0.10; // -10%

        // Act
        List<ItemNotaFiscal> resultado = calculadora.calcularAliquota(items, aliquotaNegativa);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(-10.0, resultado.getFirst().getValorTributoItem()); // 100 * -0.10
    }
}
