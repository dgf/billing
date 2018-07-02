package org.aplatanao.billing;

import org.aplatanao.billing.api.InvoiceResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        registerComponents();
    }

    private void registerComponents() {
        System.out.println("CONFIGURE Jersey");
        property(ServerProperties.APPLICATION_NAME, "Aplatanao Billing Service");
        //property(ServerProperties.PROCESSING_RESPONSE_ERRORS_ENABLED, "true");
        register(InvoiceResource.class);
    }
}
