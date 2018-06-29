package org.aplatanao.billing;

import org.aplatanao.billing.api.InvoiceResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        registerComponents();
    }

    private void registerComponents() {
        System.out.println("CONFIGURE Jersey");
        register(InvoiceResource.class);
    }
}
