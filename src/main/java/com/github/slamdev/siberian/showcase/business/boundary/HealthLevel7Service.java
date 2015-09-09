package com.github.slamdev.siberian.showcase.business.boundary;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.parser.GenericParser;
import com.github.slamdev.siberian.showcase.integration.TempDirectory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Path;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Files.write;

@ApplicationScoped
public class HealthLevel7Service {

    private GenericParser parser;

    @Inject
    @TempDirectory
    private Path tempDirectory;

    @PostConstruct
    public void init() {
        @SuppressWarnings("resource") // closed in #destroy()
        DefaultHapiContext context = new DefaultHapiContext();
        parser = context.getGenericParser();
    }

    @PreDestroy
    public void destroy() throws IOException {
        parser.getHapiContext().close();
    }

    public String getData(String name) {
        try {
            byte[] data = readAllBytes(tempDirectory.resolve(name));
            Message message = parser.parse(new String(data));
            return message.toString();
        } catch (IOException | HL7Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void save(String name, String data) {
        try {
            Message message = parser.parse(data);
            write(tempDirectory.resolve(name), message.toString().getBytes());
        } catch (IOException | HL7Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
