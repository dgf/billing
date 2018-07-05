package org.aplatanao.billing.configuration;

import org.aplatanao.billing.provider.ConstraintViolationExceptionMapper;
import org.aplatanao.billing.provider.WebApplicationExceptionMapper;
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
        register(WebApplicationExceptionMapper.class);
        register(ConstraintViolationExceptionMapper.class);
        register(InvoiceResource.class);
    }
}
