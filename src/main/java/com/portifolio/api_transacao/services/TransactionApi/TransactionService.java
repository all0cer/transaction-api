package com.portifolio.api_transacao.services.TransactionApi;

import com.portifolio.api_transacao.core.TransactionStatistics;
import com.portifolio.api_transacao.core.TransactionUser;
import com.portifolio.api_transacao.core.TransactionsRequest;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;

@Service
public class TransactionService implements TransactionUser {


    private final List<TransactionsRequest> transactionsList;

    public TransactionService() {
        this.transactionsList = new ArrayList<TransactionsRequest>();
    }



    @Override
    public void createTransaction(TransactionsRequest transactionsRequest) {
        transactionsList.add(transactionsRequest);
    }

    @Override
    public void deleteAllTransactions() {
        transactionsList.clear();
    }

    @Override
    public TransactionStatistics getTransactionStatistics() {
        return null;
    }

    private TransactionStatistics CalculateStatistics(){
        OffsetDateTime oneMinuteAgo = OffsetDateTime.now().minusSeconds(60);

        var lastMinuteTxs = transactionsList.stream()
                .filter(t -> t.date().isAfter(oneMinuteAgo))
                .mapToDouble(TransactionsRequest::value);

        DoubleSummaryStatistics stats = lastMinuteTxs.summaryStatistics();

        return new TransactionStatistics(
                stats.getCount(),
                stats.getSum(),
                stats.getAverage(),
                stats.getCount() > 0 ? stats.getMin() : 0.0,
                stats.getCount() > 0 ? stats.getMax() : 0.0
        );
    }
}
