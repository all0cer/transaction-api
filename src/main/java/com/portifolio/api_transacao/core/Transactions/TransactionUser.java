package com.portifolio.api_transacao.core.Transactions;

import com.portifolio.api_transacao.core.Transactions.TransactionDTO.TransactionsRequest;

public interface TransactionUser {

    public void createTransaction(TransactionsRequest transactionsRequest);

    public void deleteAllTransactions();

    public TransactionStatistics getTransactionStatistics();

}
