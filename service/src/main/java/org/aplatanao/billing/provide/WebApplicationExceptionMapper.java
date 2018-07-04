package org.aplatanao.billing.provide;

import org.aplatanao.billing.rest.model.Error;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class WebApplicationExceptionMapper implements ExceptionMapper<WebApplicationException> {

    @Override
    public Response toResponse(WebApplicationException e) {
        return Response.status(e.getResponse().getStatus())
                .type(MediaType.APPLICATION_JSON)
                .entity(new Error()
                        .status(e.getResponse().getStatus())
                        .message(e.getMessage()))
                .build();
    }
}