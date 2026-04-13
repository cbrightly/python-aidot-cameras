package org.glassfish.grizzly.http.server.util;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Grizzly;

public class ClassLoaderUtil {
    private static final Logger LOGGER = Grizzly.logger(ClassLoaderUtil.class);

    @Deprecated
    public static ClassLoader createClassloader(File libDir, ClassLoader cl) {
        if (!libDir.exists() || !libDir.isDirectory()) {
            return null;
        }
        String[] jars = libDir.list(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".jar") || name.endsWith(".zip");
            }
        });
        URL[] urls = new URL[jars.length];
        for (int i = 0; i < jars.length; i++) {
            urls[i] = new URL(new File(libDir.getName() + File.separator + jars[i]).getCanonicalFile().toURI().toURL().toString());
        }
        return createClassLoaderWithSecCheck(urls, cl);
    }

    public static URLClassLoader createURLClassLoader(String dirPath) {
        String path;
        URL classesURL;
        URL appRoot;
        URL[] urls;
        String dirPath2 = dirPath;
        String str = File.separator;
        if (!dirPath2.endsWith(str) && !dirPath2.endsWith(".war") && !dirPath2.endsWith(".jar")) {
            dirPath2 = dirPath2 + str;
        }
        String separator = System.getProperty("os.name").toLowerCase().startsWith("win") ? "/" : "//";
        if (dirPath2 == null || (!dirPath2.endsWith(".war") && !dirPath2.endsWith(".jar"))) {
            path = dirPath2;
            classesURL = new URL("file://" + path + "WEB-INF/classes/");
            StringBuilder sb = new StringBuilder();
            sb.append("file://");
            sb.append(path);
            appRoot = new URL(sb.toString());
        } else {
            File file = new File(dirPath2);
            appRoot = new URL("jar:file:" + separator + file.getCanonicalPath().replace('\\', '/') + "!/");
            classesURL = new URL("jar:file:" + separator + file.getCanonicalPath().replace('\\', '/') + "!/WEB-INF/classes/");
            path = ExpandJar.expand(appRoot);
        }
        File libFiles = new File(new File(path).getAbsolutePath() + str + "WEB-INF" + str + "lib");
        if (!libFiles.exists() || !libFiles.isDirectory()) {
            File file2 = libFiles;
            urls = new URL[4];
        } else {
            urls = new URL[(libFiles.listFiles().length + 4)];
            int i = 0;
            while (i < libFiles.listFiles().length) {
                urls[i] = new URL("jar:file:" + separator + libFiles.listFiles()[i].toString().replace('\\', '/') + "!/");
                i++;
                libFiles = libFiles;
                dirPath2 = dirPath2;
            }
            File file3 = libFiles;
        }
        urls[urls.length - 1] = classesURL;
        urls[urls.length - 2] = appRoot;
        urls[urls.length - 3] = new URL("file://" + path + "/WEB-INF/classes/");
        StringBuilder sb2 = new StringBuilder();
        sb2.append("file://");
        sb2.append(path);
        urls[urls.length + -4] = new URL(sb2.toString());
        return createClassLoaderWithSecCheck(urls, Thread.currentThread().getContextClassLoader());
    }

    public static URLClassLoader createURLClassLoader(String location, ClassLoader parent) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(parent);
        try {
            return createURLClassLoader(location);
        } finally {
            Thread.currentThread().setContextClassLoader(loader);
        }
    }

    public static Object load(String clazzName) {
        return load(clazzName, Thread.currentThread().getContextClassLoader());
    }

    public static Object load(String clazzName, ClassLoader classLoader) {
        try {
            return Class.forName(clazzName, true, classLoader).newInstance();
        } catch (Throwable t) {
            Logger logger = LOGGER;
            Level level = Level.SEVERE;
            logger.log(level, "Unable to load class " + clazzName, t);
            return null;
        }
    }

    private static URLClassLoader createClassLoaderWithSecCheck(final URL[] urls, final ClassLoader parent) {
        if (System.getSecurityManager() == null) {
            return new URLClassLoader(urls, parent);
        }
        return (URLClassLoader) AccessController.doPrivileged(new PrivilegedAction<URLClassLoader>() {
            public URLClassLoader run() {
                return new URLClassLoader(urls, parent);
            }
        });
    }
}
