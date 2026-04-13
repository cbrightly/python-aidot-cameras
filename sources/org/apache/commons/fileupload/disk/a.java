package org.apache.commons.fileupload.disk;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.c;
import org.apache.commons.fileupload.g;
import org.apache.commons.fileupload.util.d;
import org.apache.commons.io.output.b;

/* compiled from: DiskFileItem */
public class a implements org.apache.commons.fileupload.a {
    private static final String a = UUID.randomUUID().toString().replace('-', '_');
    private static final AtomicInteger b = new AtomicInteger(0);
    private String c;
    private final String d;
    private boolean e;
    private final String f;
    private long g = -1;
    private final int h;
    private final File i;
    private byte[] j;
    private transient b k;
    private transient File l;
    private c m;
    private String n = "ISO-8859-1";

    public a(String fieldName, String contentType, boolean isFormField, String fileName, int sizeThreshold, File repository) {
        this.c = fieldName;
        this.d = contentType;
        this.e = isFormField;
        this.f = fileName;
        this.h = sizeThreshold;
        this.i = repository;
    }

    public InputStream getInputStream() {
        if (!g()) {
            return new FileInputStream(this.k.l());
        }
        if (this.j == null) {
            this.j = this.k.j();
        }
        return new ByteArrayInputStream(this.j);
    }

    public String getContentType() {
        return this.d;
    }

    public String h() {
        g parser = new g();
        parser.j(true);
        return parser.d(getContentType(), ';').get("charset");
    }

    public String getName() {
        return d.a(this.f);
    }

    public boolean g() {
        if (this.j != null) {
            return true;
        }
        return this.k.m();
    }

    public long getSize() {
        long j2 = this.g;
        if (j2 >= 0) {
            return j2;
        }
        byte[] bArr = this.j;
        if (bArr != null) {
            return (long) bArr.length;
        }
        if (this.k.m()) {
            return (long) this.k.j().length;
        }
        return this.k.l().length();
    }

    public byte[] get() {
        b bVar;
        if (g()) {
            if (this.j == null && (bVar = this.k) != null) {
                this.j = bVar.j();
            }
            return this.j;
        }
        byte[] fileData = new byte[((int) getSize())];
        InputStream fis = null;
        try {
            fis = new FileInputStream(this.k.l());
            org.apache.commons.io.d.e(fis, fileData);
        } catch (IOException e2) {
            fileData = null;
        } catch (Throwable th) {
            org.apache.commons.io.d.b(fis);
            throw th;
        }
        org.apache.commons.io.d.b(fis);
        return fileData;
    }

    public String getString(String charset) {
        return new String(get(), charset);
    }

    public String a() {
        byte[] rawdata = get();
        String charset = h();
        if (charset == null) {
            charset = this.n;
        }
        try {
            return new String(rawdata, charset);
        } catch (UnsupportedEncodingException e2) {
            return new String(rawdata);
        }
    }

    public void d(File file) {
        if (g()) {
            FileOutputStream fout = null;
            try {
                fout = new FileOutputStream(file);
                fout.write(get());
                fout.close();
            } finally {
                org.apache.commons.io.d.c(fout);
            }
        } else {
            File outputFile = i();
            if (outputFile != null) {
                this.g = outputFile.length();
                org.apache.commons.io.c.l(outputFile, file);
                return;
            }
            throw new FileUploadException("Cannot write uploaded file to disk!");
        }
    }

    public void delete() {
        this.j = null;
        File outputFile = i();
        if (outputFile != null && !g() && outputFile.exists()) {
            outputFile.delete();
        }
    }

    public String b() {
        return this.c;
    }

    public boolean c() {
        return this.e;
    }

    public OutputStream getOutputStream() {
        if (this.k == null) {
            this.k = new b(this.h, j());
        }
        return this.k;
    }

    public File i() {
        if (this.k != null && !g()) {
            return this.k.l();
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        File outputFile;
        b bVar = this.k;
        if (bVar != null && !bVar.m() && (outputFile = this.k.l()) != null && outputFile.exists()) {
            outputFile.delete();
        }
    }

    /* access modifiers changed from: protected */
    public File j() {
        if (this.l == null) {
            File tempDir = this.i;
            if (tempDir == null) {
                tempDir = new File(System.getProperty("java.io.tmpdir"));
            }
            this.l = new File(tempDir, String.format("upload_%s_%s.tmp", new Object[]{a, k()}));
        }
        return this.l;
    }

    private static String k() {
        int current = b.getAndIncrement();
        String id = Integer.toString(current);
        if (current >= 100000000) {
            return id;
        }
        return ("00000000" + id).substring(id.length());
    }

    public String toString() {
        return String.format("name=%s, StoreLocation=%s, size=%s bytes, isFormField=%s, FieldName=%s", new Object[]{getName(), i(), Long.valueOf(getSize()), Boolean.valueOf(c()), b()});
    }

    public void e(c pHeaders) {
        this.m = pHeaders;
    }

    public void l(String charset) {
        this.n = charset;
    }
}
