package org.aplatanao.billing.configuration;

import com.coxautodev.graphql.tools.SchemaParser;
import graphql.schema.GraphQLSchema;
import org.aplatanao.billing.graphql.Query;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.file.Files;

@Configuration
public class GraphQL {

    @Value("classpath:root.graphqls")
    private Resource schemaFile;

    @Bean
    GraphQLSchema schema(Query query) throws IOException {
        return SchemaParser.newParser()
                .schemaString(new String(Files.readAllBytes(schemaFile.getFile().toPath())))
                .resolvers(query).build().makeExecutableSchema();
    }
}
