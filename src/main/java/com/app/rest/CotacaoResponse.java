package com.app.rest;

import java.math.BigDecimal;

public record CotacaoResponse(BigDecimal valorTotal, BigDecimal valorVista,
                              BigDecimal valorParcelado) {
}
