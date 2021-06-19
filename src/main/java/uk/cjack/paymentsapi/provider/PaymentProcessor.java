/*
 * Copyright (C) Chris Jackson (github.com/c-jack) - All Rights Reserved
 * Unauthorised copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Chris Jackson <chris@cjack.uk>,  2021.
 */

package uk.cjack.paymentsapi.provider;

import uk.cjack.paymentsapi.model.Transaction;

public interface PaymentProcessor
{
    /**
     * Process the Provider's Transaction
     * @param transaction the transaction to process
     */
    void processTransaction(final Transaction transaction) throws Exception;
}
