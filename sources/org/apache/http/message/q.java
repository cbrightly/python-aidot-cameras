package org.apache.http.message;

import java.io.Serializable;
import org.apache.http.ParseException;
import org.apache.http.c;
import org.apache.http.e;
import org.apache.http.util.a;
import org.apache.http.util.d;

/* compiled from: BufferedHeader */
public class q implements c, Cloneable, Serializable {
    private static final long serialVersionUID = -2768352615787625448L;
    private final d buffer;
    private final String name;
    private final int valuePos;

    public q(d buffer2) {
        a.i(buffer2, "Char array buffer");
        int colon = buffer2.indexOf(58);
        if (colon != -1) {
            String s = buffer2.substringTrimmed(0, colon);
            if (s.length() != 0) {
                this.buffer = buffer2;
                this.name = s;
                this.valuePos = colon + 1;
                return;
            }
            throw new ParseException("Invalid header: " + buffer2.toString());
        }
        throw new ParseException("Invalid header: " + buffer2.toString());
    }

    public String getName() {
        return this.name;
    }

    public String getValue() {
        d dVar = this.buffer;
        return dVar.substringTrimmed(this.valuePos, dVar.length());
    }

    public e[] getElements() {
        v cursor = new v(0, this.buffer.length());
        cursor.d(this.valuePos);
        return g.b.b(this.buffer, cursor);
    }

    public int getValuePos() {
        return this.valuePos;
    }

    public d getBuffer() {
        return this.buffer;
    }

    public String toString() {
        return this.buffer.toString();
    }

    public Object clone() {
        return super.clone();
    }
}
