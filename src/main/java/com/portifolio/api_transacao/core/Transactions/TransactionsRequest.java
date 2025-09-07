package com.portifolio.api_transacao.core.Transactions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

import java.time.OffsetDateTime;

@JsonIgnoreProperties(ignoreUnknown = false)
public record TransactionsRequest(@NotNull @Positive(message = "The value must be greater than 0") Double value,
                                  @NotNull @PastOrPresent(message = "Must be a date in the past or in the present") OffsetDateTime date) {

}
