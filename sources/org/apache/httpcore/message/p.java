package org.apache.httpcore.message;

import java.io.Serializable;
import org.apache.httpcore.ParseException;
import org.apache.httpcore.d;
import org.apache.httpcore.f;
import org.apache.httpcore.util.a;

/* compiled from: BufferedHeader */
public class p implements d, Cloneable, Serializable {
    private static final long serialVersionUID = -2768352615787625448L;
    private final org.apache.httpcore.util.d buffer;
    private final String name;
    private final int valuePos;

    public p(org.apache.httpcore.util.d buffer2) {
        a.g(buffer2, "Char array buffer");
        int colon = buffer2.indexOf(58);
        if (colon != -1) {
            String s = buffer2.substringTrimmed(0, colon);
            if (!s.isEmpty()) {
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
        org.apache.httpcore.util.d dVar = this.buffer;
        return dVar.substringTrimmed(this.valuePos, dVar.length());
    }

    public f[] getElements() {
        u cursor = new u(0, this.buffer.length());
        cursor.d(this.valuePos);
        return e.b.a(this.buffer, cursor);
    }

    public int getValuePos() {
        return this.valuePos;
    }

    public org.apache.httpcore.util.d getBuffer() {
        return this.buffer;
    }

    public String toString() {
        return this.buffer.toString();
    }

    public Object clone() {
        return super.clone();
    }
}
