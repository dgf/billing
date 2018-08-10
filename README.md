# Aplatanao Billing

[![Build Status](https://travis-ci.org/dgf/billing.svg?branch=master)](https://travis-ci.org/dgf/billing)

More a development template than a usable billing application.

Demonstration of a model driven and process based development on an actual Java stack.

## Requirements

### Database

Adapt the `connection.properties` configuration for an existing [PostgreSQL] database.

Or use a local [dockerized PostgreSQL]

    # docker run --rm --name billing-postgres -p 5432:5432 -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=billing postgres:9

### Migrations

All database changes are managed with [Flyway] migrations.

Retrieve the actual migration meta data from the configured database with the [Flyway Maven Plugin]

    $ mvn -P migration initialize flyway:info

Migrate the database to the latest version.

    $ mvn -P migration initialize flyway:migrate

## Development

Build the whole project with all [Maven] modules.

    $ mvn clean install

Start the service with the [Spring Boot Maven Plugin]

    $ cd service && mvn spring-boot:run

### Useful development tools

- IntelliJ [IDEA] to code and for SQL
- [Swagger Editor] for Open API definitions
- [Camunda Modeler] for BPMN definitions
- [Apache JMeter] for integration testing and benchmarking

### Migrations

Change your database and record the necessary statements in [Flyway SQL Migrations]

    $ ls -al migrations/src/main/resources/db/migration/
    ...
    -rw-r--r-- 1 dgf dgf   77 ago  4 12:59 V1__Create_extensions_and_schema.sql
    -rw-r--r-- 1 dgf dgf  549 ago  4 12:59 V2__Create_invoice_table.sql
    ...

Adapt your changes with the [Flyway Maven Plugin]

    $ cd migrations && mvn clean initialize flyway:migrate

### Persistence

Generate JPA entity classes for the actual connected database with the [Hibernate Tools Maven Plugin]

    $ cd persistence && mvn clean verify

### REST API

Adapt the OpenAPI 2.0 definition `specification/api.yaml`

Generate the JAX-RS interfaces and POJOs with the [Swagger Codegen Maven Plugin]

    $ cd specification && mvn clean verify

### Data type mappings

- JAX-RS 2.1 + JPA 2.1 https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html
- OpenAPI 2.0 https://github.com/OAI/OpenAPI-Specification/blob/master/versions/2.0.md#data-types
- GraphQL June 2018 Edition http://facebook.github.io/graphql/June2018/#sec-Type-System
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

### Typical Failures

#### build it with an invalid database connection

Event:
A call of Maven results in the following error:

    $ mvn clean install
    ...
    [ERROR] Failed to execute goal org.hibernate:hibernate-tools-maven-plugin:5.3.4.Final:hbm2java
    (Entity generation) on project persistence: Execution Entity generation of goal
    org.hibernate:hibernate-tools-maven-plugin:5.3.4.Final:hbm2java failed:
    Unable to create requested service [org.hibernate.engine.jdbc.env.spi.JdbcEnvironment]:
    Error calling Driver#connect: Connection to localhost:5432 refused.
    Check that the hostname and port are correct and that the postmaster is accepting TCP/IP connections.
    Connection refused (Connection refused) -> [Help 1]

Solution:
check the connection parameters and the access to your database from the machine the service should run on.

### TBD

- [x] re-add custom revengStrategy parameter for the Hibernate Codegen configuration, see https://github.com/hibernate/hibernate-tools/pull/1017
- [ ] support Java long value literal restrictions for the @Min and @Max annotations in Swagger Codegen
- [ ] support referenced type definitions in Swagger Codegen to reuse common restrictions like year and code

[Apache JMeter]: https://jmeter.apache.org/
[Camunda]: https://camunda.com/
[Camunda Modeler]: https://camunda.com/products/modeler/
[dockerized PostgreSQL]: https://hub.docker.com/_/postgres/
[Flyway]: https://flywaydb.org/
[Flyway Maven Plugin]: https://flywaydb.org/documentation/maven/
[Flyway SQL Migrations]: https://flywaydb.org/documentation/migrations#sql-based-migrations
[Hibernate Tools]: https://hibernate.org/tools/
[Hibernate Tools Maven Plugin]: https://github.com/hibernate/hibernate-tools/tree/master/maven
[IDEA]: https://www.jetbrains.com/idea/
[Maven]: https://maven.apache.org/
[PostgreSQL]: https://www.postgresql.org
[Spring Boot]: https://spring.io/projects/spring-boot
[Spring Boot Maven Plugin]: https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html
[Swagger Codegen Maven Plugin]: https://github.com/swagger-api/swagger-codegen/tree/master/modules/swagger-codegen-maven-plugin
[Swagger Editor]: https://editor.swagger.io/
