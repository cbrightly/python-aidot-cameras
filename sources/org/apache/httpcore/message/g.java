package org.apache.httpcore.message;

import java.net.Socket;
import org.apache.httpcore.m;
import org.apache.httpcore.t;
import org.apache.httpcore.util.a;
import org.apache.httpcore.v;
import org.apache.httpcore.x;

/* compiled from: BasicHttpRequest */
public class g extends a implements m {
    private final Socket c;
    private final String d;
    private final String e;
    private x f;
    private int g = -1;
    private int h = -1;

    public g(Socket socket, x requestline) {
        this.c = socket;
        this.f = (x) a.g(requestline, "Request line");
        this.d = requestline.getMethod();
        this.e = requestline.getUri();
    }

    public v getProtocolVersion() {
        return r().getProtocolVersion();
    }

    public x r() {
        if (this.f == null) {
            this.f = new m(this.d, this.e, t.HTTP_1_1);
        }
        return this.f;
    }

    public String toString() {
        return this.d + ' ' + this.e + ' ' + this.a;
    }
}
