package org.aplatanao.billing.provider;

import com.fasterxml.jackson.databind.JsonMappingException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
public class JsonMappingExceptionMapper extends AbstractErrorExceptionMapperJSON<JsonMappingException> {

    @Override
    Response.Status getStatus(JsonMappingException exception) {
        return Response.Status.BAD_REQUEST;
    }
}