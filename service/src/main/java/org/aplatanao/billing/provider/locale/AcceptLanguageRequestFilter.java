package org.aplatanao.billing.provider.locale;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class AcceptLanguageRequestFilter implements ContainerRequestFilter {

    @Context
    private HttpHeaders headers;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        if (!headers.getAcceptableLanguages().isEmpty()) {
            LocaleThreadLocal.set(headers.getAcceptableLanguages().get(0));
        }
    }
}