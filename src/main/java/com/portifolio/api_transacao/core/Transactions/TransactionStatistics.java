package com.portifolio.api_transacao.core.Transactions;


public record TransactionStatistics(
        long count,
        double sum,
        double avg,
        double min,
        double max) {
}
