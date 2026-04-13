package org.apache.commons.fileupload;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

/* compiled from: FileItem */
public interface a extends d {
    String a();

    String b();

    boolean c();

    void d(File file);

    void delete();

    boolean g();

    byte[] get();

    String getContentType();

    InputStream getInputStream();

    String getName();

    OutputStream getOutputStream();

    long getSize();

    String getString(String str);
}
