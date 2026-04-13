package org.apache.httpcore.impl.bootstrap;

import java.io.IOException;
import org.apache.httpcore.c;
import org.apache.httpcore.protocol.a;
import org.apache.httpcore.protocol.e;
import org.apache.httpcore.protocol.l;
import org.apache.httpcore.s;

/* compiled from: Worker */
public class f implements Runnable {
    private final l c;
    private final s d;
    private final c f;

    f(l httpservice, s conn, c exceptionLogger) {
        this.c = httpservice;
        this.d = conn;
        this.f = exceptionLogger;
    }

    public s a() {
        return this.d;
    }

    public void run() {
        try {
            a localContext = new a();
            e context = e.a(localContext);
            while (!Thread.interrupted() && this.d.isOpen()) {
                this.c.d(this.d, context);
                localContext.a();
            }
            this.d.close();
            try {
                this.d.shutdown();
            } catch (IOException ex) {
                this.f.a(ex);
            }
        } catch (Exception ex2) {
            this.f.a(ex2);
            this.d.shutdown();
        } catch (Throwable th) {
            try {
                this.d.shutdown();
            } catch (IOException ex3) {
                this.f.a(ex3);
            }
            throw th;
        }
    }
}
