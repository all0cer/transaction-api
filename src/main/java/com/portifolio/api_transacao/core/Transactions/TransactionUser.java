package com.portifolio.api_transacao.core;

import org.springframework.boot.autoconfigure.pulsar.PulsarProperties;

public interface TransactionUser {

    public void createTransaction(TransactionsRequest transactionsRequest);

    public void deleteAllTransactions();

    public TransactionStatistics getTransactionStatistics();

}
