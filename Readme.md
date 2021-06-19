# Payments API

June 2021 | Chris Jackson | [github/c-jack](http://www.github.com/c-jack)

An example 'Payments' API, as based on the Crowdcube Payments Challenge.



---

## Running

The project can be packaged as a self-contained `JAR` by using the Maven wrapper, meaning the process should be agnostic
and simple. To run the entire solution, a Docker setup is provided. This will, therefore, need a reasonably up-to-date
installation of Docker on the host machine.

#### Packaging the API

Run `./mvnw clean package` from the project base.

#### Running the Docker Solution

navigate to the `Docker` directory, then run `docker-compose up`

MongoDB will be available at `mongodb://localhost:27017` with `admin/admin` credentials. The Payments API will be
available at the base `http://localhost:9080/api/`. See 'testing' for specifics.

#### Running outside Docker

If running outside Docker (e.g. in an IDE) with a local Mongo instance, comment out the 'host' property
in `application.properties`

#### Testing

Example `JSON` files are available in `test/resources` for use with the method of choice.

Alternatively, a Postman collection can be found in `Payment API.postman_collection.json`


---

## Guide

In its current state, there are the following endpoints:

### Payment Methods

#### api/payment

- A `POST` request with valid JSON will create a PaymentMethod document
- A `GET` request will return all payments

#### api/payment/{id}

- A `GET` request will return the PaymentMethod with the provided ID
- A `DELETE` request will attempt to delete an entry of the provided ID
- A `PUT` request will update an entry with any difference in the provided JSON for an entry of the provided ID

### Transactions

Pending transactions are processed via a scheduler (`TransactionProcessorService`).

#### api/transaction

- A `POST` request with valid JSON will create a PaymentMethod document
- A `GET` request will return all payments

#### api/transaction/refund/{id}

- A `GET` request will cancel the PaymentMethod with the provided ID
    - If the matching transaction is 'PENDING', it will be cancelled (no refund req'd)
    - If the matching transaction is 'COMPLETE', a new 'REFUND' transaction will be created
    - Otherwise, it should be 'FAILED' The matching transaction will be returned, except for when a refund is required,
      at which point that transaction will be returned (with new `id` reference)

No validation or specific 'error codes' have been implemented, as that would require more refinement of the solution.


### Payment Providers
Each Payment Provider should implement `PaymentProcessor` and extend `PaymentProvider` (see `StripePayment`) so that a common implementation of `processPendingTransaction()` is available.
Each provider can have its own schema within their POJO, which can be passed to the superclass for an outgoing RESTful transaction.

This would potentially allow a 'per-transaction' provider implementation, and a somewhat straightforward extensibility.  

[Lots of assumptions and caveats]


---

## Testing

There isn't a full and complete level of Test Coverage provided due to the scale of the challenge.  Example BDD-style UTs are available for the `model` classes using Spock, and some very basic examples of the API tests have been made using JUnit5 and MockMVC.