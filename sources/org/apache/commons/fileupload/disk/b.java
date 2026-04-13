package org.apache.commons.fileupload.disk;

import androidx.work.Data;
import java.io.File;
import org.apache.commons.fileupload.a;

/* compiled from: DiskFileItemFactory */
public class b implements org.apache.commons.fileupload.b {
    private File a;
    private int b;
    private org.apache.commons.io.b c;
    private String d;

    public b() {
        this(Data.MAX_DATA_BYTES, (File) null);
    }

    public b(int sizeThreshold, File repository) {
        this.b = Data.MAX_DATA_BYTES;
        this.d = "ISO-8859-1";
        this.b = sizeThreshold;
        this.a = repository;
    }

    public void c(File repository) {
        this.a = repository;
    }

    public void d(int sizeThreshold) {
        this.b = sizeThreshold;
    }

    public a a(String fieldName, String contentType, boolean isFormField, String fileName) {
        a result = new a(fieldName, contentType, isFormField, fileName, this.b, this.a);
        result.l(this.d);
        org.apache.commons.io.b tracker = b();
        if (tracker != null) {
            tracker.a(result.j(), result);
        }
        return result;
    }

    public org.apache.commons.io.b b() {
        return this.c;
    }
}
