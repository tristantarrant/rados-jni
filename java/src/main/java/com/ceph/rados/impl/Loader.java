package com.ceph.rados.impl;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Logger;

public class Loader {
    private static final Logger log = Logger.getLogger(Loader.class.getName());
    private static final String NATIVE_RESOURCE_HOME = "META-INF/native/";
    private static final Path NATIVE_DIR;

    static {
        NATIVE_DIR = Paths.get(SecurityActions.getProperty("java.io.tmpdir"));
    }

    static void load(String name, ClassLoader loader) {
        String libname = System.mapLibraryName(name);
        String path = NATIVE_RESOURCE_HOME + libname;
        try {
            URL url = loader == null ? ClassLoader.getSystemResource(path) : loader.getResource(path);
            URI target = url != null ? url.toURI() :  Paths.get("target", "native-build", "target", "lib", libname).toUri();
            Path tempFile = Files.createTempFile(NATIVE_DIR, name, null);
            Files.copy(Paths.get(target), tempFile, StandardCopyOption.REPLACE_EXISTING);
            SecurityActions.systemLoad(tempFile.toAbsolutePath().toString());
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
