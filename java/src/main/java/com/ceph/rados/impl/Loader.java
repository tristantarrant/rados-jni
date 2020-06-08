package com.ceph.rados.impl;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Locale;
import java.util.logging.Logger;

public class Loader {
    private static final Logger log = Logger.getLogger(Loader.class.getName());
    private static final Path NATIVE_DIR;

    static {
        NATIVE_DIR = Paths.get(SecurityActions.getProperty("java.io.tmpdir"));
    }

    static void load(String name, ClassLoader loader) {
        String libname = System.mapLibraryName(name);
        String path = osName() + '-' + osArch() + '/' + libname;
        try {
            URL url = loader == null ? ClassLoader.getSystemResource(path) : loader.getResource(path);
            URI target = url != null ? url.toURI() : Paths.get("target", "native-build", "target", "lib", libname).toUri();
            Path tempFile = Files.createTempFile(NATIVE_DIR, name, null);
            Files.copy(Paths.get(target), tempFile, StandardCopyOption.REPLACE_EXISTING);
            SecurityActions.systemLoad(tempFile.toAbsolutePath().toString());
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static String osName() {
        String name = SecurityActions.getProperty("os.name").toUpperCase(Locale.US);
        if (name.startsWith("LINUX")) {
            return "linux";
        } else if (name.startsWith("MAC OS")) {
            return "macosx";
        } else if (name.startsWith("WINDOWS")) {
            return "win";
        } else if (name.startsWith("OS/2")) {
            return "os2";
        } else if (name.startsWith("SOLARIS") || name.startsWith("SUNOS")) {
            return "solaris";
        } else if (name.startsWith("MPE/IX")) {
            return "mpeix";
        } else if (name.startsWith("HP-UX")) {
            return "hpux";
        } else if (name.startsWith("AIX")) {
            return "aix";
        } else if (name.startsWith("OS/390")) {
            return "os390";
        } else if (name.startsWith("OS/400")) {
            return "os400";
        } else if (name.startsWith("FREEBSD")) {
            return "freebsd";
        } else if (name.startsWith("OPENBSD")) {
            return "openbsd";
        } else if (name.startsWith("NETBSD")) {
            return "netbsd";
        } else if (name.startsWith("IRIX")) {
            return "irix";
        } else if (name.startsWith("DIGITAL UNIX")) {
            return "digitalunix";
        } else if (name.startsWith("OSF1")) {
            return "osf1";
        } else if (name.startsWith("OPENVMS")) {
            return "openvms";
        } else if (name.startsWith("IOS")) {
            return "iOS";
        } else {
            throw new IllegalArgumentException("Unknown OS: " + name);
        }
    }

    public static String osArch() {
        String arch = SecurityActions.getProperty("os.arch").toUpperCase(Locale.US);
        if (arch.startsWith("SPARCV9") || arch.startsWith("SPARC64")) {
            return "sparcv9";
        } else if (arch.startsWith("SPARC")) {
            return "sparc";
        } else if (arch.startsWith("X86_64") || arch.startsWith("AMD64")) {
            return "x86_64";
        } else if (arch.startsWith("I386")) {
            return "i386";
        } else if (arch.startsWith("I486")) {
            return "i486";
        } else if (arch.startsWith("I586")) {
            return "i586";
        } else if (arch.startsWith("I686") || arch.startsWith("X86") || arch.contains("IA32")) {
            return "i686";
        } else if (arch.startsWith("X32")) {
            return "x32";
        } else if (arch.startsWith("PPC64LE")) {
            return "ppc64le";
        } else if (arch.startsWith("PPC64")) {
            return "ppc64";
        } else if (arch.startsWith("PPC") || arch.startsWith("POWER")) {
            return "ppc";
        } else if (arch.startsWith("AARCH64") || arch.startsWith("ARM64") || arch.startsWith("ARMV8") || arch.startsWith("PXA9") || arch.startsWith("PXA10")) {
            return "aarch64";
        } else {
            throw new IllegalArgumentException("Unknown Arch: " + arch);
        }
    }
}
