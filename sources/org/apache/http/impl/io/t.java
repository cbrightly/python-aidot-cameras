package org.apache.http.impl.io;

import java.net.Socket;
import org.apache.http.io.b;
import org.apache.http.params.HttpParams;
import org.apache.http.util.a;

@Deprecated
/* compiled from: SocketInputBuffer */
public class t extends c implements b {
    private final Socket o;
    private boolean p = false;

    public t(Socket socket, int buffersize, HttpParams params) {
        a.i(socket, "Socket");
        this.o = socket;
        int n = buffersize;
        n = n < 0 ? socket.getReceiveBufferSize() : n;
        i(socket.getInputStream(), n < 1024 ? 1024 : n, params);
    }

    /* access modifiers changed from: protected */
    public int f() {
        int i = super.f();
        this.p = i == -1;
        return i;
    }

    /* JADX INFO: finally extract failed */
    public boolean b(int timeout) {
        boolean result = h();
        if (result) {
            return result;
        }
        int oldtimeout = this.o.getSoTimeout();
        try {
            this.o.setSoTimeout(timeout);
            f();
            boolean result2 = h();
            this.o.setSoTimeout(oldtimeout);
            return result2;
        } catch (Throwable th) {
            this.o.setSoTimeout(oldtimeout);
            throw th;
        }
    }

    public boolean c() {
        return this.p;
    }
}
