# Aplatanao Billing Service

## requirements

run a local PostgreSQL server

    docker run --rm --name billing-postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=billing postgres:9

adapt connection properties, see `pom.xml`

    <properties>
        ...
        <postgres.url>jdbc:postgresql://192.168.99.100:5432/billing</postgres.url>
        <postgres.username>postgres</postgres.username>
        ...
    </properties>

## development flow

migrate the storage

    mvn flyway:migrate

update JPA entities

    mvn generate-sources

## crudl invoices

run the service

    mvn spring-boot:run

create invoice

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

get invoice

    curl --dump-header - http://localhost:8080/invoices/1

    HTTP/1.1 200
    Content-Type: application/json
    Content-Length: 70
    Date: Sun, 01 Jul 2018 19:47:13 GMT
    {"id":1,"date":"2018-07-01","code":"OFF_2018_07","comment":"kick off"}%

