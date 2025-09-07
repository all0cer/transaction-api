package com.portifolio.api_transacao.controller.TransactionApi;

import com.portifolio.api_transacao.core.Transactions.TransactionStatistics;
import com.portifolio.api_transacao.core.Transactions.TransactionsRequest;
import com.portifolio.api_transacao.services.TransactionApi.TransactionService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequestMapping("/transacao")
public class TransactionController {

    @Autowired
    public final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<String> save(@RequestBody @Valid TransactionsRequest transaction) {
        transactionService.createTransaction(transaction);
        log.info("Transaction Sucessfully Created");
        return ResponseEntity.status(HttpStatus.CREATED).body("Transaction Sucessfully Created");
    }

    @GetMapping
    public ResponseEntity<TransactionStatistics> findAll() {
        transactionService.getTransactionStatistics();
        return ResponseEntity.ok().body(transactionService.getTransactionStatistics());
    }
}
