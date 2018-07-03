package org.aplatanao.billing.config;

import org.aplatanao.billing.provide.InvoiceResource;
import org.aplatanao.billing.provide.WebApplicationExceptionMapper;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        registerComponents();
    }

    private void registerComponents() {
        register(WebApplicationExceptionMapper.class);
        register(InvoiceResource.class);
    }
}
