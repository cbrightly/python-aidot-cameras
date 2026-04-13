package org.glassfish.grizzly.http.server;

import java.io.File;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.http.Method;
import org.glassfish.grizzly.http.util.Header;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.grizzly.utils.ArraySet;

public class StaticHttpHandler extends StaticHttpHandlerBase {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Logger LOGGER = Grizzly.logger(StaticHttpHandler.class);
    private boolean directorySlashOff;
    protected final ArraySet<File> docRoots = new ArraySet<>(File.class);

    public StaticHttpHandler() {
        addDocRoot(".");
    }

    public StaticHttpHandler(String... docRoots2) {
        if (docRoots2 != null) {
            for (String docRoot : docRoots2) {
                addDocRoot(docRoot);
            }
        }
    }

    public StaticHttpHandler(Set<String> docRoots2) {
        if (docRoots2 != null) {
            for (String docRoot : docRoots2) {
                addDocRoot(docRoot);
            }
        }
    }

    public File getDefaultDocRoot() {
        File[] array = (File[]) this.docRoots.getArray();
        if (array == null || array.length <= 0) {
            return null;
        }
        return array[0];
    }

    public ArraySet<File> getDocRoots() {
        return this.docRoots;
    }

    public final File addDocRoot(String docRoot) {
        if (docRoot != null) {
            File file = new File(docRoot);
            addDocRoot(file);
            return file;
        }
        throw new NullPointerException("docRoot can't be null");
    }

    public final void addDocRoot(File docRoot) {
        this.docRoots.add(docRoot);
    }

    public void removeDocRoot(File docRoot) {
        this.docRoots.remove(docRoot);
    }

    public boolean isDirectorySlashOff() {
        return this.directorySlashOff;
    }

    public void setDirectorySlashOff(boolean directorySlashOff2) {
        this.directorySlashOff = directorySlashOff2;
    }

    /* access modifiers changed from: protected */
    public boolean handle(String uri, Request request, Response response) {
        boolean found = false;
        File[] fileFolders = (File[]) this.docRoots.getArray();
        if (fileFolders == null) {
            return false;
        }
        File resource = null;
        int i = 0;
        while (true) {
            if (i >= fileFolders.length) {
                break;
            }
            resource = new File(fileFolders[i], uri);
            boolean exists = resource.exists();
            boolean isDirectory = resource.isDirectory();
            if (exists && isDirectory) {
                if (this.directorySlashOff || uri.endsWith("/")) {
                    File f = new File(resource, "/index.html");
                    if (f.exists()) {
                        resource = f;
                        found = true;
                        break;
                    }
                } else {
                    response.setStatus(HttpStatus.MOVED_PERMANENTLY_301);
                    Header header = Header.Location;
                    response.setHeader(header, response.encodeRedirectURL(uri + "/"));
                    return true;
                }
            }
            if (!isDirectory && exists) {
                found = true;
                break;
            }
            found = false;
            i++;
        }
        if (!found) {
            Logger logger = LOGGER;
            Level level = Level.FINE;
            if (logger.isLoggable(level)) {
                logger.log(level, "File not found {0}", resource);
            }
            return false;
        } else if (resource == null) {
            throw new AssertionError();
        } else if (!Method.GET.equals(request.getMethod())) {
            Logger logger2 = LOGGER;
            Level level2 = Level.FINE;
            if (logger2.isLoggable(level2)) {
                logger2.log(level2, "File found {0}, but HTTP method {1} is not allowed", new Object[]{resource, request.getMethod()});
            }
            response.setStatus(HttpStatus.METHOD_NOT_ALLOWED_405);
            response.setHeader(Header.Allow, Constants.GET);
            return true;
        } else {
            StaticHttpHandlerBase.pickupContentType(response, resource.getPath());
            addToFileCache(request, response, resource);
            StaticHttpHandlerBase.sendFile(response, resource);
            return true;
        }
    }
}
