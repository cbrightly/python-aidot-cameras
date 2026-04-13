package org.apache.httpcore.message;

import com.amazonaws.http.HttpHeader;
import java.net.Socket;
import org.apache.httpcore.e;
import org.apache.httpcore.j;
import org.apache.httpcore.k;
import org.apache.httpcore.x;

/* compiled from: BasicHttpEntityEnclosingRequest */
public class f extends g implements k {
    private j i;

    public f(Socket socket, x requestline) {
        super(socket, requestline);
    }

    public j a() {
        return this.i;
    }

    public void b(j entity) {
        this.i = entity;
    }

    public boolean m() {
        e expect = u(HttpHeader.EXPECT);
        return expect != null && "100-continue".equalsIgnoreCase(expect.getValue());
    }
}
