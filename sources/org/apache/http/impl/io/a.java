package org.apache.http.impl.io;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.MessageConstraintException;
import org.apache.http.ParseException;
import org.apache.http.ProtocolException;
import org.apache.http.io.c;
import org.apache.http.io.h;
import org.apache.http.message.k;
import org.apache.http.message.u;
import org.apache.http.n;
import org.apache.http.params.HttpParams;
import org.apache.http.util.d;

/* compiled from: AbstractMessageParser */
public abstract class a<T extends n> implements c<T> {
    private final h a;
    private final org.apache.http.config.c b;
    private final List<d> c;
    protected final u d;
    private int e;
    private T f;

    /* access modifiers changed from: protected */
    public abstract T b(h hVar);

    @Deprecated
    public a(h buffer, u parser, HttpParams params) {
        org.apache.http.util.a.i(buffer, "Session input buffer");
        org.apache.http.util.a.i(params, "HTTP parameters");
        this.a = buffer;
        this.b = org.apache.http.params.a.a(params);
        this.d = parser != null ? parser : k.b;
        this.c = new ArrayList();
        this.e = 0;
    }

    public a(h buffer, u lineParser, org.apache.http.config.c constraints) {
        this.a = (h) org.apache.http.util.a.i(buffer, "Session input buffer");
        this.d = lineParser != null ? lineParser : k.b;
        this.b = constraints != null ? constraints : org.apache.http.config.c.c;
        this.c = new ArrayList();
        this.e = 0;
    }

    public static org.apache.http.d[] c(h inbuffer, int maxHeaderCount, int maxLineLen, u parser) {
        return d(inbuffer, maxHeaderCount, maxLineLen, parser != null ? parser : k.b, new ArrayList<>());
    }

    public static org.apache.http.d[] d(h inbuffer, int maxHeaderCount, int maxLineLen, u parser, List<d> headerLines) {
        org.apache.http.util.a.i(inbuffer, "Session input buffer");
        org.apache.http.util.a.i(parser, "Line parser");
        org.apache.http.util.a.i(headerLines, "Header line list");
        d current = null;
        d previous = null;
        while (true) {
            if (current == null) {
                current = new d(64);
            } else {
                current.clear();
            }
            if (inbuffer.a(current) == -1 || current.length() < 1) {
                org.apache.http.d[] headers = new org.apache.http.d[headerLines.size()];
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
        org.apache.http.d[] headers2 = new org.apache.http.d[headerLines.size()];
        int i3 = 0;
        while (i3 < headerLines.size()) {
            try {
                headers2[i3] = parser.b(headerLines.get(i3));
                i3++;
            } catch (ParseException ex) {
                throw new ProtocolException(ex.getMessage());
            }
        }
        return headers2;
    }

    public T a() {
        switch (this.e) {
            case 0:
                try {
                    this.f = b(this.a);
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
        this.f.u0(d(this.a, this.b.c(), this.b.d(), this.d, this.c));
        T result = this.f;
        this.f = null;
        this.c.clear();
        this.e = 0;
        return result;
    }
}
