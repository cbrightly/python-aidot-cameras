package org.apache.commons.fileupload;

import java.io.IOException;
import java.io.InputStream;

public interface FileItemStream extends d {

    public static class ItemSkippedException extends IOException {
        private static final long serialVersionUID = -7280778431581963740L;
    }

    String b();

    boolean c();

    InputStream f();

    String getContentType();
}
