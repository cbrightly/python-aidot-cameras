package com.squareup.okhttp.internal;

import java.io.IOException;
import okio.b0;
import okio.j;

/* compiled from: FaultHidingSink */
public class c extends j {
    private boolean c;

    /* access modifiers changed from: protected */
    public void a(IOException iOException) {
        throw null;
    }

    public c(b0 delegate) {
        super(delegate);
    }

    public void write(okio.c source, long byteCount) {
        if (this.c) {
            source.skip(byteCount);
            return;
        }
        try {
            super.write(source, byteCount);
        } catch (IOException e) {
            this.c = true;
            a(e);
        }
    }

    public void flush() {
        if (!this.c) {
            try {
                super.flush();
            } catch (IOException e) {
                this.c = true;
                a(e);
            }
        }
    }

    public void close() {
        if (!this.c) {
            try {
                super.close();
            } catch (IOException e) {
                this.c = true;
                a(e);
            }
        }
    }
}
