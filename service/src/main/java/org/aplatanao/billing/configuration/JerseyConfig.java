package org.aplatanao.billing.configuration;

import org.aplatanao.billing.provider.exception.CatchAllExceptionMapper;
import org.aplatanao.billing.provider.exception.ConstraintViolationExceptionMapper;
import org.aplatanao.billing.provider.exception.JsonMappingExceptionMapper;
import org.aplatanao.billing.provider.exception.WebApplicationExceptionMapper;
import org.aplatanao.billing.provider.locale.AcceptLanguageRequestFilter;
import org.aplatanao.billing.provider.locale.ValidationConfigurationContextResolver;
import org.aplatanao.billing.endpoint.InvoiceEndpoint;
import org.aplatanao.billing.endpoint.PaymentEndpoint;
import org.aplatanao.billing.endpoint.ReportsEndpoint;
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

        register(JsonMappingExceptionMapper.class, 1);
        register(ConstraintViolationExceptionMapper.class);
        register(WebApplicationExceptionMapper.class);
        register(CatchAllExceptionMapper.class);

        register(InvoiceEndpoint.class);
        register(PaymentEndpoint.class);
        register(ReportsEndpoint.class);
    }
}
