package com.github.slamdev.siberian.showcase.integration;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.serverError;

@Provider
public class AllExceptionsMapper implements ExceptionMapper<Throwable> {

    @Context
    private HttpServletRequest request;

    @Override
    public Response toResponse(Throwable exception) {
        return serverError().entity(new SerializableException(exception, requestToHost(request))).type(APPLICATION_JSON)
                .build();
    }

    private static String requestToHost(HttpServletRequest request) {
        return request.getServerName() + ":" + request.getServerPort() + request.getRequestURI();
    }
}
