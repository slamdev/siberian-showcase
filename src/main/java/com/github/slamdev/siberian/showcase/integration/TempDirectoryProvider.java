package com.github.slamdev.siberian.showcase.integration;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import java.nio.file.Path;
import java.nio.file.Paths;

@ApplicationScoped
public class TempDirectoryProvider {

    private Path directory;

    @PostConstruct
    public void init() {
        directory = Paths.get(System.getProperty("jboss.server.temp.dir"));
    }

    @Produces
    @TempDirectory
    public Path provide() {
        return directory;
    }
}
