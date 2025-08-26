package com.portifolio.api_transacao.core;

import java.math.BigInteger;


public record TransactionStatistics(
                                    long count,
                                    double sum,
                                    double avg,
                                    double min,
                                    double max)
{}
