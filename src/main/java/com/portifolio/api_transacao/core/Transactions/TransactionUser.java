package com.portifolio.api_transacao.core.Transactions;

public interface TransactionUser {

    public void createTransaction(TransactionsRequest transactionsRequest);

    public void deleteAllTransactions();

    public TransactionStatistics getTransactionStatistics();

}
