package org.aplatanao.billing.provider.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
public class WebApplicationExceptionMapper extends AbstractErrorExceptionMapperJSON<WebApplicationException> {

    @Override
    Response.Status getStatus(WebApplicationException exception) {
        return Response.Status.fromStatusCode(exception.getResponse().getStatus());
    }
}