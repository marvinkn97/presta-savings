#  Savings Management - Spring Boot API for a simple savings system.
   +  This API provides a simple savings system, allowing appUsers to create savings accounts, deposit and withdraw funds, check their account balance, and view transaction history.
       +   Objectives
           +  Implemented endpoints to capture a list of customers, including their bio data
           +  Implemented endpoints to record savings transactions per customer, including transaction details such as transaction ID, date, payment method, and amount.
           +  Implemented endpoints to track the total savings amount for each person.
           +  Implemented endpoints to track the total savings amount received across all appUsers
           +  Implemented endpoints to create and manage multiple savings products, such as Education Savings, Personal Savings, and Vacation Savings.
           +  Ensured proper error handling and validation of incoming requests.
           +  Implemented unit tests to validate the functionality of the API endpoints.
           +  Document the APIs using Swagger, providing clear descriptions and examples for each endpoint.

       +   Technologies Used
           +  Spring - Boot 3, JPA, Security with JWT
           +  Postgres on Docker
           +  TestContainers for DAO Layer unit testing
           +  Lombok
           +  REST API Documentation with SpringDoc OPENAPI
           +  Tiara Connect SMS Gateway [https://www.tiaraconnect.io/]
           +  MailDev [https://github.com/maildev/maildev]
           
  +    TODO
       +  Implement Testing
       +  Implement Security
       +  Notification Service
          +  email notification