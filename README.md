# Aplatanao Billing Service

migrate the storage

    mvn flyway:migrate

run the service

    mvn spring-boot:run

update JPA entities

    mvn hibernate-tools:hbm2java

update JAX-RS layer

    mvn swagger-codegen:generate

## crudl invoices

create one

    curl --dump-header - \
         --request POST \
         --header "Content-Type: application/json" \
         --data '{"date":"2018-07-01","code":"OFF_2018_07","comment":"kick off"}' \
        http://localhost:8080/invoices

    HTTP/1.1 200
    Content-Type: application/json
    Content-Length: 70
    Date: Sun, 01 Jul 2018 18:57:55 GMT
    {"id":1,"date":"2018-07-01","code":"OFF_2018_07","comment":"kick off"}%

list invoices

    curl --dump-header - http://localhost:8080/invoices

    HTTP/1.1 200
    Content-Type: application/json
    Content-Length: 72
    Date: Sun, 01 Jul 2018 18:58:24 GMT
    [{"id":1,"date":"2018-07-01","code":"OFF_2018_07","comment":"kick off"}]%
