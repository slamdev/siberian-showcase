package com.github.slamdev.siberian.showcase.integration;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath(RestApplication.PATH)
public class RestApplication extends Application {

    public static final String PATH = "rest";
}