package io.netty.handler.ssl;

import io.netty.internal.tcnative.SSL;
import io.netty.internal.tcnative.SSLContext;
import io.netty.internal.tcnative.SessionTicketKey;
import io.netty.util.internal.ObjectUtil;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.util.concurrent.locks.Lock;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSessionContext;

public abstract class OpenSslSessionContext implements SSLSessionContext {
    private static final Enumeration<byte[]> EMPTY = new EmptyEnumeration();
    final ReferenceCountedOpenSslContext context;
    private final OpenSslSessionStats stats;

    public abstract boolean isSessionCacheEnabled();

    public abstract void setSessionCacheEnabled(boolean z);

    OpenSslSessionContext(ReferenceCountedOpenSslContext context2) {
        this.context = context2;
        this.stats = new OpenSslSessionStats(context2);
    }

    public SSLSession getSession(byte[] bytes) {
        if (bytes != null) {
            return null;
        }
        throw new NullPointerException("bytes");
    }

    public Enumeration<byte[]> getIds() {
        return EMPTY;
    }

    @Deprecated
    public void setTicketKeys(byte[] keys) {
        if (keys.length % 48 == 0) {
            SessionTicketKey[] tickets = new SessionTicketKey[(keys.length / 48)];
            int i = 0;
            int a = 0;
            while (i < tickets.length) {
                byte[] name = Arrays.copyOfRange(keys, a, 16);
                int a2 = a + 16;
                byte[] hmacKey = Arrays.copyOfRange(keys, a2, 16);
                int i2 = i + 16;
                byte[] aesKey = Arrays.copyOfRange(keys, a2, 16);
                a = a2 + 16;
                tickets[i2] = new SessionTicketKey(name, hmacKey, aesKey);
                i = i2 + 1;
            }
            Lock writerLock = this.context.ctxLock.writeLock();
            writerLock.lock();
            try {
                SSLContext.clearOptions(this.context.ctx, SSL.SSL_OP_NO_TICKET);
                SSLContext.setSessionTicketKeys(this.context.ctx, tickets);
            } finally {
                writerLock.unlock();
            }
        } else {
            throw new IllegalArgumentException("keys.length % 48 != 0");
        }
    }

    public void setTicketKeys(OpenSslSessionTicketKey... keys) {
        ObjectUtil.checkNotNull(keys, "keys");
        SessionTicketKey[] ticketKeys = new SessionTicketKey[keys.length];
        for (int i = 0; i < ticketKeys.length; i++) {
            ticketKeys[i] = keys[i].key;
        }
        Lock writerLock = this.context.ctxLock.writeLock();
        writerLock.lock();
        try {
            SSLContext.clearOptions(this.context.ctx, SSL.SSL_OP_NO_TICKET);
            SSLContext.setSessionTicketKeys(this.context.ctx, ticketKeys);
        } finally {
            writerLock.unlock();
        }
    }

    public OpenSslSessionStats stats() {
        return this.stats;
    }

    public static final class EmptyEnumeration implements Enumeration<byte[]> {
        private EmptyEnumeration() {
        }

        public boolean hasMoreElements() {
            return false;
        }

        public byte[] nextElement() {
            throw new NoSuchElementException();
        }
    }
}
