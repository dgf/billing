# Aplatanao Billing

More a development template than a usable billing application.

Demonstration of a model driven and process based development on an actual Java stack.

Operated with the following basics:

- Spring Boot https://spring.io/projects/spring-boot
- Camunda https://camunda.com/
- PostgreSQL https://www.postgresql.org/

And the following helpers:

- Flyway https://flywaydb.org/
- Hibernate Tools https://hibernate.org/tools/
- Swagger Codegen https://swagger.io/tools/swagger-codegen/

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

    ../service$ mvn clean spring-boot:run

## Development

### Useful development tools

- IntelliJ IDEA to code and for SQL https://www.jetbrains.com/idea/ 
- Swagger Editor for Open API definitions https://editor.swagger.io/ 
- Camunda Modeler for BPMN definitions https://camunda.com/products/modeler/
- Apache JMeter for integration testing and benchmarking https://jmeter.apache.org/ 

### JPA entities

generate classes

    ../persistence$ mvn clean verify

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

| Common name | OpenAPI 2.0        | JAX-RS 2.1         | JPA 2.1            | PostgreSQL 9.4 | Restrictions                                          |
| ----------- | ------------------ | ------------------ | ------------------ | -------------- |------------------------------------------------------ |
| integer     | integer > int32    | j.l.Integer        | short              | **smallint**   | 2 bytes: -32768 to +32767                             |
| integer     | integer > int32    | j.l.Integer        | j.l.Integer        | **integer**    | 4 bytes: -2147483648 to +2147483647                   |
| long        | integer > int64    | j.l.Long           | j.l.Long           | **bigint**     | 8 bytes: -9223372036854775808 to +9223372036854775807 |
| string      | string             | j.l.String         | j.l.String         | varchar(**n**) | **n** characters                                      |
| UUID        | string             | j.l.String         | j.u.UUID           | **uuid**       | 16 bytes                                              |
| date        | string > date      | j.t.LocalDate      | j.t.LocalDate      | **date**       | 4713 BC to 5874897 AD                                 |
| date time   | string > date-time | j.t.OffsetDateTime | j.t.OffsetDateTime | **timestamp**  | 4713 BC	294276 AD 1 microsecond / 14 digits           |

### TBD

- [ ] re-add custom revengStrategy parameter for the Hibernate Codegen configuration, see https://github.com/hibernate/hibernate-tools/pull/1017
- [ ] support Java long value literal restrictions for the @Min and @Max annotations in Swagger Codegen
- [ ] support referenced type definitions in Swagger Codegen to reuse common restrictions like year and code
