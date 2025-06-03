package br.com.itau.geradornotafiscal.core.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum TipoPessoa {
    FISICA("PF"),
    JURIDICA("PJ");

    private final String codigoTipoPessoa;

}


