package org.aplatanao.billing.configuration;

import org.aplatanao.billing.provider.*;
import org.aplatanao.billing.service.InvoiceResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.ApplicationPath;

@Configuration
@ApplicationPath("/api")
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        registerComponents();
    }

    private void registerComponents() {
        register(AcceptLanguageRequestFilter.class);
        register(ValidationConfigurationContextResolver.class);

        register(WebApplicationExceptionMapper.class);
        register(JsonMappingExceptionMapper.class, 1);
        register(ConstraintViolationExceptionMapper.class);

        register(InvoiceResource.class);
    }
}
