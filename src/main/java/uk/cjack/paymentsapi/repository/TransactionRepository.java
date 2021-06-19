/*
 * Copyright (C) Chris Jackson (github.com/c-jack) - All Rights Reserved
 * Unauthorised copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Chris Jackson <chris@cjack.uk>,  2021.
 */

package uk.cjack.paymentsapi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import uk.cjack.paymentsapi.model.Transaction;

import java.util.List;

/**
 * Transaction Repository
 * Can have some custom bits added, but otherwise just defines the Mongo implementation
 */
public interface TransactionRepository extends MongoRepository<Transaction, String>
{
    List<Transaction> findAllByStatus(final Transaction.Status status);
}
