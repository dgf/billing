package org.aplatanao.billing.provider.locale;

import org.glassfish.jersey.server.validation.ValidationConfig;

import javax.validation.Validation;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

@Provider
public class ValidationConfigurationContextResolver implements ContextResolver<ValidationConfig> {

    @Context
    private HttpHeaders headers;

    @Override
    public ValidationConfig getContext(Class<?> type) {
        return new ValidationConfig().messageInterpolator(
                new LocaleSpecificMessageInterpolator(
                        Validation.byDefaultProvider().configure().getDefaultMessageInterpolator()));

    }
}