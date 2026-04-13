package org.apache.httpcore.impl.io;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import org.apache.httpcore.MessageConstraintException;
import org.apache.httpcore.ParseException;
import org.apache.httpcore.ProtocolException;
import org.apache.httpcore.e;
import org.apache.httpcore.io.b;
import org.apache.httpcore.io.g;
import org.apache.httpcore.l;
import org.apache.httpcore.message.j;
import org.apache.httpcore.message.t;
import org.apache.httpcore.util.d;

/* compiled from: AbstractMessageParser */
public abstract class a<T extends l> implements b<T> {
    private final g a;
    private final org.apache.httpcore.config.b b;
    private final List<d> c;
    protected final t d;
    private int e;
    private T f;

    /* access modifiers changed from: protected */
    public abstract T b(Socket socket, g gVar);

    public a(g buffer, t lineParser, org.apache.httpcore.config.b constraints) {
        this.a = (g) org.apache.httpcore.util.a.g(buffer, "Session input buffer");
        this.d = lineParser != null ? lineParser : j.b;
        this.b = constraints != null ? constraints : org.apache.httpcore.config.b.c;
        this.c = new ArrayList();
        this.e = 0;
    }

    public static e[] c(g inBuffer, int maxHeaderCount, int maxLineLen, t parser) {
        return d(inBuffer, maxHeaderCount, maxLineLen, parser != null ? parser : j.b, new ArrayList<>());
    }

    public static e[] d(g inBuffer, int maxHeaderCount, int maxLineLen, t parser, List<d> headerLines) {
        org.apache.httpcore.util.a.g(inBuffer, "Session input buffer");
        org.apache.httpcore.util.a.g(parser, "Line parser");
        org.apache.httpcore.util.a.g(headerLines, "Header line list");
        d current = null;
        d previous = null;
        while (true) {
            if (current == null) {
                current = new d(64);
            } else {
                current.clear();
            }
            if (inBuffer.a(current) == -1 || current.length() < 1) {
                e[] headers = new e[headerLines.size()];
                int i = 0;
            } else {
                if ((current.charAt(0) == ' ' || current.charAt(0) == 9) && previous != null) {
                    int i2 = 0;
                    while (i2 < current.length() && ((ch = current.charAt(i2)) == ' ' || ch == 9)) {
                        i2++;
                    }
                    if (maxLineLen <= 0 || ((previous.length() + 1) + current.length()) - i2 <= maxLineLen) {
                        previous.append(' ');
                        previous.append(current, i2, current.length() - i2);
                    } else {
                        throw new MessageConstraintException("Maximum line length limit exceeded");
                    }
                } else {
                    headerLines.add(current);
                    previous = current;
                    current = null;
                }
                if (maxHeaderCount > 0 && headerLines.size() >= maxHeaderCount) {
                    throw new MessageConstraintException("Maximum header count exceeded");
                }
            }
        }
        e[] headers2 = new e[headerLines.size()];
        int i3 = 0;
        while (i3 < headerLines.size()) {
            try {
                headers2[i3] = parser.a(headerLines.get(i3));
                i3++;
            } catch (ParseException ex) {
                throw new ProtocolException(ex.getMessage());
            }
        }
        return headers2;
    }

    public T a(Socket socket) {
        switch (this.e) {
            case 0:
                try {
                    this.f = b(socket, this.a);
                    this.e = 1;
                    break;
                } catch (ParseException px) {
                    throw new ProtocolException(px.getMessage(), px);
                }
            case 1:
                break;
            default:
                throw new IllegalStateException("Inconsistent parser state");
        }
        this.f.d(d(this.a, this.b.b(), this.b.c(), this.d, this.c));
        T result = this.f;
        this.f = null;
        this.c.clear();
        this.e = 0;
        return result;
    }
}
