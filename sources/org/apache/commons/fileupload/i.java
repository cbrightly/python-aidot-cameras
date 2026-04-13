package org.apache.commons.fileupload;

import java.io.InputStream;

/* compiled from: RequestContext */
public interface i {
    String b();

    @Deprecated
    int getContentLength();

    String getContentType();

    InputStream getInputStream();
}
