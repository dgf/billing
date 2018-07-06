package org.aplatanao.billing.provider;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.aplatanao.billing.rest.model.Error;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class JsonMappingExceptionMapper implements ExceptionMapper<JsonMappingException> {

    @Override
    public Response toResponse(final JsonMappingException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .type(MediaType.APPLICATION_JSON)
                .entity(new Error()
                        .status(Response.Status.BAD_REQUEST.getStatusCode())
                        .message(exception.getMessage()))
                .build();
    }
}