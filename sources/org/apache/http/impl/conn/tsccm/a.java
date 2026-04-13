package org.apache.http.impl.conn.tsccm;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.commons.logging.h;
import org.apache.http.impl.conn.s;

@Deprecated
/* compiled from: AbstractConnPool */
public abstract class a {
    private final org.apache.commons.logging.a a = h.n(getClass());
    protected final Lock b = new ReentrantLock();
    protected Set<b> c = new HashSet();
    protected s d = new s();

    protected a() {
    }
}
