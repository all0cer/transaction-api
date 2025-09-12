package com.portifolio.api_transacao.controller.TransactionApi;

import com.portifolio.api_transacao.core.Transactions.TransactionStatistics;
import com.portifolio.api_transacao.core.Transactions.TransactionDTO.TransactionsRequest;
import com.portifolio.api_transacao.services.TransactionApi.TransactionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequestMapping("/transacao")
@Tag(name = "Transactions", description = "Endpoints for create and consult transactions")
public class TransactionController {

    @Autowired
    public final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Operation(summary = "Crate transaction", description = "Adds a new transaction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transação registrada"),
            @ApiResponse(responseCode = "422", description = "Validação do request falhou"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    @PostMapping
    public ResponseEntity<String> save(@RequestBody @Valid TransactionsRequest transaction) {
        transactionService.createTransaction(transaction);
        log.info("Transaction Sucessfully Created");
        return ResponseEntity.status(HttpStatus.CREATED).body("Transaction Sucessfully Created");
    }

    @Operation(summary = "Consult statistics", description = "Return transaction statistics for the last 60 seconds")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estatísticas retornadas com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    @GetMapping
    public ResponseEntity<TransactionStatistics> findAll() {
        transactionService.getTransactionStatistics();
        return ResponseEntity.ok().body(transactionService.getTransactionStatistics());
    }
}
