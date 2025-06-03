package br.com.itau.geradornotafiscal.entrypoint.domains;

import java.time.LocalDate;
import java.util.List;

import br.com.itau.geradornotafiscal.core.model.Destinatario;
import br.com.itau.geradornotafiscal.core.model.Item;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record Pedido(
        @JsonProperty("id_pedido")
        int idPedido,

        @JsonProperty("data")
        LocalDate data,

        @JsonProperty("valor_total_itens")
        double valorTotalItens,

        @JsonProperty("valor_frete")
        double valorFrete,

        @JsonProperty("itens")
        List<Item> itens,

        @JsonProperty("destinatario")
        Destinatario destinatario
) {
}
