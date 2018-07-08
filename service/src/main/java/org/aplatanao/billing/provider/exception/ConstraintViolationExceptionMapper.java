package org.aplatanao.billing.provider.exception;

import org.aplatanao.billing.rest.model.Validation;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.stream.Collectors;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(final ConstraintViolationException exception) {
        Response.Status status = Response.Status.BAD_REQUEST;
        return Response.status(status)
                .type(MediaType.APPLICATION_JSON)
                .entity(new Validation()
                        .code(status.getStatusCode())
                        .status(status.getReasonPhrase())
                        .message("Constraint violation.")
                        .messages(exception.getConstraintViolations().stream()
                                .collect(Collectors.toMap(
                                        violation -> violation.getPropertyPath().toString(),
                                        ConstraintViolation::getMessage))))
                .build();
    }
}