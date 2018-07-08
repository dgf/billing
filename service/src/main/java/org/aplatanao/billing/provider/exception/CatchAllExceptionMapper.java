package org.aplatanao.billing.provider.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
public class CatchAllExceptionMapper extends AbstractErrorExceptionMapperJSON<Exception> {

    @Override
    Response.Status getStatus(Exception exception) {
        return Response.Status.INTERNAL_SERVER_ERROR;
    }

}