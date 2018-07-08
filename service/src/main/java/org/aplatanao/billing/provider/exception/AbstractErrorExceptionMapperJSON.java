package org.aplatanao.billing.provider.exception;

import org.aplatanao.billing.rest.model.Error;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public abstract class AbstractErrorExceptionMapperJSON<E extends Throwable> implements ExceptionMapper<E> {

    abstract Response.Status getStatus(E exception);

    @Override
    public Response toResponse(E exception) {
        return Response
                .status(getStatus(exception))
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(new Error()
                        .status(getStatus(exception).getStatusCode())
                        .message(exception.getMessage()))
                .build();
    }
}
