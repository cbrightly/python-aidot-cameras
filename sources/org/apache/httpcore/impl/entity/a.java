package org.apache.httpcore.impl.entity;

import org.apache.httpcore.ProtocolException;
import org.apache.httpcore.entity.d;
import org.apache.httpcore.l;

/* compiled from: DisallowIdentityContentLengthStrategy */
public class a implements d {
    public static final a a = new a(new b(0));
    private final d b;

    public a(d contentLengthStrategy) {
        this.b = contentLengthStrategy;
    }

    public long a(l message) {
        long result = this.b.a(message);
        if (result != -1) {
            return result;
        }
        throw new ProtocolException("Identity transfer encoding cannot be used");
    }
}
