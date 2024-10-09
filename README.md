Transactions Routine

Controllers:

1. AccountController
   1. GET Accounts
      1. curl --location 'http://localhost:8080/api/v1/accounts/<account_id>'

   2. POST Accounts
      1. curl --location 'http://localhost:8080/api/v1/accounts' \
         --header 'Content-Type: application/json' \
         --data '{
         "document_number": "<document_number>"
         }'

2. OperationTypeController
   1. GET OperationTypes
      1. curl --location 'http://localhost:8080/api/v1/operation-type'

   2. GET OperationType by ID
      1. curl --location 'http://localhost:8080/api/v1/operation-type/<operationTypeId>'

   3. POST OperationType
      1. curl --location 'http://localhost:8080/api/v1/operation-type' \
         --header 'Content-Type: application/json' \
         --data '{
         "description": "<description_string>"
         }'

3. TransactionController
   1. GET Transaction By ID
      1. curl --location 'http://localhost:8080/api/v1/transactions/<transaction_id>'

   2. GET Transaction By AccountId and/or OperationTypeId
      1. curl --location 'http://localhost:8080/api/v1/transactions/account-operation-type?accountId=<account_id>&operationTypeId=<operation_type_id>'
      2. curl --location 'http://localhost:8080/api/v1/transactions/account-operation-type?accountId=<account_id>'
      3. curl --location 'http://localhost:8080/api/v1/transactions/account-operation-type?operationTypeId=<operation_type_id>'

   3. POST Transactions
      1. curl --location 'http://localhost:8080/api/v1/transactions' \
         --header 'Content-Type: application/json' \
         --data '{
         "account": {
         "account_id": <account_id>
         },
         "operation_type": {
         "operation_type_id": <operation_type_id>
         },
         "amount": "<amount_value>"
         }'