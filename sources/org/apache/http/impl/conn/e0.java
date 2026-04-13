package org.apache.http.impl.conn;

import io.netty.util.internal.StringUtil;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.apache.commons.logging.a;

/* compiled from: Wire */
public class e0 {
    private final a a;
    private final String b;

    public e0(a log, String id) {
        this.a = log;
        this.b = id;
    }

    public e0(a log) {
        this(log, "");
    }

    private void j(String header, InputStream instream) {
        StringBuilder buffer = new StringBuilder();
        while (true) {
            int read = instream.read();
            int ch = read;
            if (read == -1) {
                break;
            } else if (ch == 13) {
                buffer.append("[\\r]");
            } else if (ch == 10) {
                buffer.append("[\\n]\"");
                buffer.insert(0, "\"");
                buffer.insert(0, header);
                a aVar = this.a;
                aVar.debug(this.b + " " + buffer.toString());
                buffer.setLength(0);
            } else if (ch < 32 || ch > 127) {
                buffer.append("[0x");
                buffer.append(Integer.toHexString(ch));
                buffer.append("]");
            } else {
                buffer.append((char) ch);
            }
        }
        if (buffer.length() > 0) {
            buffer.append(StringUtil.DOUBLE_QUOTE);
            buffer.insert(0, StringUtil.DOUBLE_QUOTE);
            buffer.insert(0, header);
            a aVar2 = this.a;
            aVar2.debug(this.b + " " + buffer.toString());
        }
    }

    public boolean a() {
        return this.a.isDebugEnabled();
    }

    public void i(byte[] b2, int off, int len) {
        org.apache.http.util.a.i(b2, "Output");
        j(">> ", new ByteArrayInputStream(b2, off, len));
    }

    public void e(byte[] b2, int off, int len) {
        org.apache.http.util.a.i(b2, "Input");
        j("<< ", new ByteArrayInputStream(b2, off, len));
    }

    public void h(byte[] b2) {
        org.apache.http.util.a.i(b2, "Output");
        j(">> ", new ByteArrayInputStream(b2));
    }

    public void d(byte[] b2) {
        org.apache.http.util.a.i(b2, "Input");
        j("<< ", new ByteArrayInputStream(b2));
    }

    public void f(int b2) {
        h(new byte[]{(byte) b2});
    }

    public void b(int b2) {
        d(new byte[]{(byte) b2});
    }

    public void g(String s) {
        org.apache.http.util.a.i(s, "Output");
        h(s.getBytes());
    }

    public void c(String s) {
        org.apache.http.util.a.i(s, "Input");
        d(s.getBytes());
    }
}
