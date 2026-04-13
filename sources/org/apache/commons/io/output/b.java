package org.apache.commons.io.output;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.commons.io.c;

/* compiled from: DeferredFileOutputStream */
public class b extends d {
    private final File a1;
    private final String p0;
    private boolean p1;
    private a q;
    private OutputStream x;
    private File y;
    private final String z;

    public b(int threshold, File outputFile) {
        this(threshold, outputFile, (String) null, (String) null, (File) null, 1024);
    }

    private b(int threshold, File outputFile, String prefix, String suffix, File directory, int initialBufferSize) {
        super(threshold);
        this.p1 = false;
        this.y = outputFile;
        this.z = prefix;
        this.p0 = suffix;
        this.a1 = directory;
        a aVar = new a(initialBufferSize);
        this.q = aVar;
        this.x = aVar;
    }

    /* access modifiers changed from: protected */
    public OutputStream c() {
        return this.x;
    }

    /* access modifiers changed from: protected */
    public void i() {
        String str = this.z;
        if (str != null) {
            this.y = File.createTempFile(str, this.p0, this.a1);
        }
        c.j(this.y);
        FileOutputStream fos = new FileOutputStream(this.y);
        try {
            this.q.g(fos);
            this.x = fos;
            this.q = null;
        } catch (IOException e) {
            fos.close();
            throw e;
        }
    }

    public boolean m() {
        return !g();
    }

    public byte[] j() {
        a aVar = this.q;
        if (aVar != null) {
            return aVar.c();
        }
        return null;
    }

    public File l() {
        return this.y;
    }

    public void close() {
        super.close();
        this.p1 = true;
    }
}
