package org.apache.http.impl.client;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.net.ssl.SSLException;
import org.apache.http.client.h;
import org.apache.http.client.methods.p;
import org.apache.http.k;
import org.apache.http.protocol.f;
import org.apache.http.util.a;

/* compiled from: DefaultHttpRequestRetryHandler */
public class o implements h {
    public static final o INSTANCE = new o();
    private final Set<Class<? extends IOException>> nonRetriableClasses;
    private final boolean requestSentRetryEnabled;
    private final int retryCount;

    protected o(int retryCount2, boolean requestSentRetryEnabled2, Collection<Class<? extends IOException>> clazzes) {
        this.retryCount = retryCount2;
        this.requestSentRetryEnabled = requestSentRetryEnabled2;
        this.nonRetriableClasses = new HashSet();
        for (Class<? extends IOException> clazz : clazzes) {
            this.nonRetriableClasses.add(clazz);
        }
    }

    public o(int retryCount2, boolean requestSentRetryEnabled2) {
        this(retryCount2, requestSentRetryEnabled2, Arrays.asList(new Class[]{InterruptedIOException.class, UnknownHostException.class, ConnectException.class, SSLException.class}));
    }

    public o() {
        this(3, false);
    }

    public boolean retryRequest(IOException exception, int executionCount, f context) {
        a.i(exception, "Exception parameter");
        a.i(context, "HTTP context");
        if (executionCount > this.retryCount || this.nonRetriableClasses.contains(exception.getClass())) {
            return false;
        }
        for (Class<? extends IOException> rejectException : this.nonRetriableClasses) {
            if (rejectException.isInstance(exception)) {
                return false;
            }
        }
        org.apache.http.client.protocol.a clientContext = org.apache.http.client.protocol.a.g(context);
        org.apache.http.o request = clientContext.d();
        if (requestIsAborted(request)) {
            return false;
        }
        if (!handleAsIdempotent(request) && clientContext.f() && !this.requestSentRetryEnabled) {
            return false;
        }
        return true;
    }

    public boolean isRequestSentRetryEnabled() {
        return this.requestSentRetryEnabled;
    }

    public int getRetryCount() {
        return this.retryCount;
    }

    /* access modifiers changed from: protected */
    public boolean handleAsIdempotent(org.apache.http.o request) {
        return !(request instanceof k);
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public boolean requestIsAborted(org.apache.http.o request) {
        org.apache.http.o req = request;
        if (request instanceof f0) {
            req = ((f0) request).h();
        }
        return (req instanceof p) && ((p) req).n();
    }
}
