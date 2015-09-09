package com.github.slamdev.siberian.showcase.business.boundary;

import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;

import static java.util.Arrays.stream;
import static javax.ws.rs.core.MediaType.MULTIPART_FORM_DATA;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Stateless
@Path(HealthLevel7Endpoint.PATH)
public class HealthLevel7Endpoint {

    public static final String PATH = "hl7";

    @Inject
    private HealthLevel7Service service;

    @GET
    @Produces(TEXT_PLAIN)
    @Path("{fileName}")
    public String get(@PathParam("fileName") String fileName) {
        return service.getData(fileName);
    }

    @POST
    @Consumes(MULTIPART_FORM_DATA)
    public void upload(MultipartFormDataInput input) throws IOException {
        for (InputPart part : input.getFormDataMap().get("inputFile")) {
            service.save(acquireFileName(part.getHeaders()), part.getBodyAsString());
        }
    }

    private static String acquireFileName(MultivaluedMap<String, String> headers) {
        String[] contentDisposition = headers.getFirst("Content-Disposition").split(";");
        return stream(contentDisposition).filter(e -> e.trim().startsWith("filename")).map(e -> e.split("="))
                .map(e -> e[1]).map(e -> e.trim().replaceAll("\"", "")).findAny().orElse(null);
    }
}
