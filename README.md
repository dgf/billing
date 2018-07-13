# Aplatanao Billing

## Requirements

run a local PostgreSQL server

    docker run --rm --name billing-postgres -p 5432:5432 -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=billing postgres:9

adapt the `connection.properties` configuration

## Database migrations

call Flyway

    ../migrations$ mvn clean initialize flyway:info

see https://flywaydb.org/documentation/migrations#sql-based-migrations
and add a new one to the structure

    $ ls -al migrations/src/main/resources/db/migration/
    ...
    -rw-r--r-- 1 dgf 1049089 376 Jul  9 08:56 V2__Create_invoice_table.sql
    ...

migrate to the latest one

    ../migrations$ mvn clean initialize flyway:migrate

## Run it

build the whole project

    mvn clean install

boot it up

    ../service mvn clean spring-boot:run

## Development

### JPA entities

generate classes

    ../api$ mvn clean verify

### JAX-RS API

adapt the OpenAPI 2.0 definition `api/src/main/resources/api.yaml` file

generate classes

    ../api$ mvn clean verify

### Data type mappings

- JAX-RS 2.1 + JPA 2.1 https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html
- OpenAPI 2.0 https://github.com/OAI/OpenAPI-Specification/blob/master/versions/2.0.md#data-types
- PostgreSQL 9.4
  - https://www.postgresql.org/docs/9.4/static/datatype.html
  - https://www.tutorialspoint.com/postgresql/postgresql_data_types.htm

| Common name | OpenAPI 2.0     | JAX-RS 2.1  | JPA 2.1   | PostgreSQL 9.4 | Restriction                                           |
| ----------- | --------------- | ----------- | --------- | -------------- |------------------------------------------------------ |
| date        | string > date   | LocalDate   | LocalDate | **date**       | 4713 BC to 5874897 AD                                 |
| integer     | integer > int32 | Integer     | short     | **smallint**   | 2 bytes: -32768 to +32767                             |
| integer     | integer > int32 | Integer     | Integer   | **integer**    | 4 bytes: -2147483648 to +2147483647                   |
| long        | integer > int64 | Long        | Long      | **bigint**     | 8 bytes: -9223372036854775808 to +9223372036854775807 |
| string      | string          | String      | string    | varchar(**n**) | **n** characters                                      |
| UUIDv4      | string          | String      | UUID      | **uuid**       | 16 bytes                                              |

### TBD

- [ ] re-add custom revengStrategy parameter for the Hibernate Codegen configuration, see https://github.com/hibernate/hibernate-tools/pull/1017
- [ ] support Java long value literal restrictions for the @Min and @Max annotations in Swagger Codegen
- [ ] support referenced type definitions in Swagger Codegen to reuse common restrictions like year and code
