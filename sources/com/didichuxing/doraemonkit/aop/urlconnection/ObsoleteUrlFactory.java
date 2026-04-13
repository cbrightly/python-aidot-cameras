package com.didichuxing.doraemonkit.aop.urlconnection;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.didichuxing.doraemonkit.okgo.model.HttpHeaders;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.SocketPermission;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;
import java.security.AccessControlException;
import java.security.Permission;
import java.security.Principal;
import java.security.cert.Certificate;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import okhttp3.a0;
import okhttp3.b0;
import okhttp3.c0;
import okhttp3.d0;
import okhttp3.e;
import okhttp3.f;
import okhttp3.p;
import okhttp3.t;
import okhttp3.u;
import okhttp3.v;
import okhttp3.w;
import okhttp3.x;
import okhttp3.z;
import okio.c;
import okio.d;
import okio.f0;
import org.apache.http.l;
import org.glassfish.grizzly.http.server.Constants;

public final class ObsoleteUrlFactory implements URLStreamHandlerFactory, Cloneable {
    private static final Comparator<String> FIELD_NAME_COMPARATOR = new Comparator<String>() {
        public int compare(String a, String b) {
            if (a == null) {
                return -1;
            }
            if (b == null) {
                return 1;
            }
            if (a.equals(b)) {
                return 0;
            }
            return String.CASE_INSENSITIVE_ORDER.compare(a, b);
        }
    };
    static final int HTTP_CONTINUE = 100;
    static final Set<String> METHODS = new LinkedHashSet(Arrays.asList(new String[]{"OPTIONS", Constants.GET, Constants.HEAD, Constants.POST, "PUT", "DELETE", "TRACE", "PATCH"}));
    static final String RESPONSE_SOURCE = "ObsoleteUrlFactory-Response-Source";
    static final String SELECTED_PROTOCOL = "ObsoleteUrlFactory-Selected-Protocol";
    private static final ThreadLocal<DateFormat> STANDARD_DATE_FORMAT = new ThreadLocal<DateFormat>() {
        /* access modifiers changed from: protected */
        public DateFormat initialValue() {
            DateFormat rfc1123 = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
            rfc1123.setLenient(false);
            rfc1123.setTimeZone(ObsoleteUrlFactory.UTC);
            return rfc1123;
        }
    };
    static final TimeZone UTC = TimeZone.getTimeZone("GMT");
    private z client;

    private ObsoleteUrlFactory(z client2) {
        this.client = client2;
    }

    public z client() {
        return this.client;
    }

    public ObsoleteUrlFactory setClient(z client2) {
        this.client = client2;
        return this;
    }

    public ObsoleteUrlFactory clone() {
        return new ObsoleteUrlFactory(this.client);
    }

    public HttpURLConnection open(URL url) {
        return open(url, this.client.D());
    }

    /* access modifiers changed from: package-private */
    public HttpURLConnection open(URL url, @Nullable Proxy proxy) {
        String protocol = url.getProtocol();
        z copy = this.client.z().Q(proxy).c();
        if (protocol.equals(l.DEFAULT_SCHEME_NAME)) {
            return new OkHttpURLConnection(url, copy);
        }
        if (protocol.equals("https")) {
            return new OkHttpsURLConnection(url, copy);
        }
        throw new IllegalArgumentException("Unexpected protocol: " + protocol);
    }

    public URLStreamHandler createURLStreamHandler(final String protocol) {
        if (protocol.equals(l.DEFAULT_SCHEME_NAME) || protocol.equals("https")) {
            return new URLStreamHandler() {
                /* access modifiers changed from: protected */
                public URLConnection openConnection(URL url) {
                    return ObsoleteUrlFactory.this.open(url);
                }

                /* access modifiers changed from: protected */
                public URLConnection openConnection(URL url, Proxy proxy) {
                    return ObsoleteUrlFactory.this.open(url, proxy);
                }

                /* access modifiers changed from: protected */
                public int getDefaultPort() {
                    if (protocol.equals(l.DEFAULT_SCHEME_NAME)) {
                        return 80;
                    }
                    if (protocol.equals("https")) {
                        return 443;
                    }
                    throw new AssertionError();
                }
            };
        }
        return null;
    }

    static String format(Date value) {
        return STANDARD_DATE_FORMAT.get().format(value);
    }

    static boolean permitsRequestBody(String method) {
        return !method.equals(Constants.GET) && !method.equals(Constants.HEAD);
    }

    static boolean hasBody(d0 response) {
        if (response.J().h().equals(Constants.HEAD)) {
            return false;
        }
        int responseCode = response.j();
        if (((responseCode >= 100 && responseCode < 200) || responseCode == 204 || responseCode == 304) && contentLength(response.s()) == -1 && !"chunked".equalsIgnoreCase(response.n(Constants.TRANSFERENCODING))) {
            return false;
        }
        return true;
    }

    static long contentLength(u headers) {
        String s = headers.a("Content-Length");
        if (s == null) {
            return -1;
        }
        try {
            return Long.parseLong(s);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    static String responseSourceHeader(d0 response) {
        if (response.u() == null) {
            if (response.g() == null) {
                return "NONE";
            }
            return "CACHE " + response.j();
        } else if (response.g() == null) {
            return "NETWORK " + response.j();
        } else {
            return "CONDITIONAL_CACHE " + response.u().j();
        }
    }

    static String statusLineToString(d0 response) {
        StringBuilder sb = new StringBuilder();
        sb.append(response.E() == a0.HTTP_1_0 ? Constants.HTTP_10 : Constants.HTTP_11);
        sb.append(' ');
        sb.append(response.j());
        sb.append(' ');
        sb.append(response.t());
        return sb.toString();
    }

    static String toHumanReadableAscii(String s) {
        int i = 0;
        int length = s.length();
        while (i < length) {
            int c = s.codePointAt(i);
            if (c <= 31 || c >= 127) {
                c buffer = new c();
                buffer.writeUtf8(s, 0, i);
                buffer.writeUtf8CodePoint(63);
                int j = Character.charCount(c) + i;
                while (j < length) {
                    int c2 = s.codePointAt(j);
                    buffer.writeUtf8CodePoint((c2 <= 31 || c2 >= 127) ? 63 : c2);
                    j += Character.charCount(c2);
                }
                return buffer.a1();
            }
            i += Character.charCount(c);
        }
        return s;
    }

    static Map<String, List<String>> toMultimap(u headers, @Nullable String valueForNullKey) {
        Map<String, List<String>> result = new TreeMap<>(FIELD_NAME_COMPARATOR);
        int size = headers.size();
        for (int i = 0; i < size; i++) {
            String fieldName = headers.b(i);
            String value = headers.h(i);
            List<String> allValues = new ArrayList<>();
            List<String> otherValues = result.get(fieldName);
            if (otherValues != null) {
                allValues.addAll(otherValues);
            }
            allValues.add(value);
            result.put(fieldName, Collections.unmodifiableList(allValues));
        }
        if (valueForNullKey != null) {
            result.put((Object) null, Collections.unmodifiableList(Collections.singletonList(valueForNullKey)));
        }
        return Collections.unmodifiableMap(result);
    }

    static String getSystemProperty(String key, @Nullable String defaultValue) {
        try {
            String value = System.getProperty(key);
            return value != null ? value : defaultValue;
        } catch (AccessControlException e) {
            return defaultValue;
        }
    }

    /* access modifiers changed from: private */
    public static String defaultUserAgent() {
        String agent = getSystemProperty("http.agent", (String) null);
        return agent != null ? toHumanReadableAscii(agent) : "ObsoleteUrlFactory";
    }

    static IOException propagate(Throwable throwable) {
        if (throwable instanceof IOException) {
            throw ((IOException) throwable);
        } else if (throwable instanceof Error) {
            throw ((Error) throwable);
        } else if (throwable instanceof RuntimeException) {
            throw ((RuntimeException) throwable);
        } else {
            throw new AssertionError();
        }
    }

    public static final class OkHttpURLConnection extends HttpURLConnection implements f {
        e call;
        private Throwable callFailure;
        z client;
        boolean connectPending = true;
        boolean executed;
        long fixedContentLength = -1;
        t handshake;
        /* access modifiers changed from: private */
        public final Object lock = new Object();
        final NetworkInterceptor networkInterceptor = new NetworkInterceptor();
        d0 networkResponse;
        Proxy proxy;
        u.a requestHeaders = new u.a();
        private d0 response;
        u responseHeaders;

        OkHttpURLConnection(URL url, z client2) {
            super(url);
            this.client = client2;
        }

        public void connect() {
            if (!this.executed) {
                e call2 = buildCall();
                this.executed = true;
                call2.o0(this);
                synchronized (this.lock) {
                    while (this.connectPending && this.response == null && this.callFailure == null) {
                        try {
                            this.lock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            throw new InterruptedIOException();
                        }
                    }
                    Throwable th = this.callFailure;
                    if (th != null) {
                        throw ObsoleteUrlFactory.propagate(th);
                    }
                }
            }
        }

        public void disconnect() {
            if (this.call != null) {
                this.networkInterceptor.proceed();
                this.call.cancel();
            }
        }

        public InputStream getErrorStream() {
            try {
                d0 response2 = getResponse(true);
                if (!ObsoleteUrlFactory.hasBody(response2) || response2.j() < 400) {
                    return null;
                }
                return response2.a().byteStream();
            } catch (IOException e) {
                return null;
            }
        }

        /* access modifiers changed from: package-private */
        public u getHeaders() {
            if (this.responseHeaders == null) {
                d0 response2 = getResponse(true);
                this.responseHeaders = response2.s().f().a(ObsoleteUrlFactory.SELECTED_PROTOCOL, response2.E().toString()).a(ObsoleteUrlFactory.RESPONSE_SOURCE, ObsoleteUrlFactory.responseSourceHeader(response2)).f();
            }
            return this.responseHeaders;
        }

        public String getHeaderField(int position) {
            try {
                u headers = getHeaders();
                if (position >= 0) {
                    if (position < headers.size()) {
                        return headers.h(position);
                    }
                }
                return null;
            } catch (IOException e) {
                return null;
            }
        }

        public String getHeaderField(String fieldName) {
            if (fieldName != null) {
                return getHeaders().a(fieldName);
            }
            try {
                return ObsoleteUrlFactory.statusLineToString(getResponse(true));
            } catch (IOException e) {
                return null;
            }
        }

        public String getHeaderFieldKey(int position) {
            try {
                u headers = getHeaders();
                if (position >= 0) {
                    if (position < headers.size()) {
                        return headers.b(position);
                    }
                }
                return null;
            } catch (IOException e) {
                return null;
            }
        }

        public Map<String, List<String>> getHeaderFields() {
            try {
                return ObsoleteUrlFactory.toMultimap(getHeaders(), ObsoleteUrlFactory.statusLineToString(getResponse(true)));
            } catch (IOException e) {
                return Collections.emptyMap();
            }
        }

        public Map<String, List<String>> getRequestProperties() {
            if (!this.connected) {
                return ObsoleteUrlFactory.toMultimap(this.requestHeaders.f(), (String) null);
            }
            throw new IllegalStateException("Cannot access request header fields after connection is set");
        }

        public InputStream getInputStream() {
            if (this.doInput) {
                d0 response2 = getResponse(false);
                if (response2.j() < 400) {
                    return response2.a().byteStream();
                }
                throw new FileNotFoundException(this.url.toString());
            }
            throw new ProtocolException("This protocol does not support input");
        }

        public OutputStream getOutputStream() {
            OutputStreamRequestBody requestBody = (OutputStreamRequestBody) buildCall().g().a();
            if (requestBody != null) {
                if (requestBody instanceof StreamedRequestBody) {
                    connect();
                    this.networkInterceptor.proceed();
                }
                if (!requestBody.closed) {
                    return requestBody.outputStream;
                }
                throw new ProtocolException("cannot write request body after response has been read");
            }
            throw new ProtocolException("method does not support a request body: " + this.method);
        }

        public Permission getPermission() {
            int hostPort;
            URL url = getURL();
            String hostname = url.getHost();
            if (url.getPort() != -1) {
                hostPort = url.getPort();
            } else {
                hostPort = v.b(url.getProtocol());
            }
            if (usingProxy()) {
                InetSocketAddress proxyAddress = (InetSocketAddress) this.client.D().address();
                hostname = proxyAddress.getHostName();
                hostPort = proxyAddress.getPort();
            }
            return new SocketPermission(hostname + ":" + hostPort, "connect, resolve");
        }

        public String getRequestProperty(String field) {
            if (field == null) {
                return null;
            }
            return this.requestHeaders.g(field);
        }

        public void setConnectTimeout(int timeoutMillis) {
            this.client = this.client.z().e((long) timeoutMillis, TimeUnit.MILLISECONDS).c();
        }

        public void setInstanceFollowRedirects(boolean followRedirects) {
            this.client = this.client.z().h(followRedirects).c();
        }

        public boolean getInstanceFollowRedirects() {
            return this.client.r();
        }

        public int getConnectTimeout() {
            return this.client.k();
        }

        public void setReadTimeout(int timeoutMillis) {
            this.client = this.client.z().R((long) timeoutMillis, TimeUnit.MILLISECONDS).c();
        }

        public int getReadTimeout() {
            return this.client.H();
        }

        private e buildCall() {
            e eVar = this.call;
            if (eVar != null) {
                return eVar;
            }
            boolean stream = true;
            this.connected = true;
            if (this.doOutput) {
                if (this.method.equals(Constants.GET)) {
                    this.method = Constants.POST;
                } else if (!ObsoleteUrlFactory.permitsRequestBody(this.method)) {
                    throw new ProtocolException(this.method + " does not support writing");
                }
            }
            if (this.requestHeaders.g("User-Agent") == null) {
                this.requestHeaders.a("User-Agent", ObsoleteUrlFactory.defaultUserAgent());
            }
            OutputStreamRequestBody requestBody = null;
            if (ObsoleteUrlFactory.permitsRequestBody(this.method)) {
                if (this.requestHeaders.g("Content-Type") == null) {
                    this.requestHeaders.a("Content-Type", "application/x-www-form-urlencoded");
                }
                if (this.fixedContentLength == -1 && this.chunkLength <= 0) {
                    stream = false;
                }
                long contentLength = -1;
                String contentLengthString = this.requestHeaders.g("Content-Length");
                if (this.fixedContentLength != -1) {
                    contentLength = this.fixedContentLength;
                } else if (contentLengthString != null) {
                    contentLength = Long.parseLong(contentLengthString);
                }
                requestBody = stream ? new StreamedRequestBody(contentLength) : new BufferedRequestBody(contentLength);
                requestBody.timeout.g((long) this.client.N(), TimeUnit.MILLISECONDS);
            }
            try {
                b0 request = new b0.a().q(v.i(getURL().toString())).h(this.requestHeaders.f()).i(this.method, requestBody).b();
                z.a clientBuilder = this.client.z();
                clientBuilder.O().add(this.networkInterceptor);
                clientBuilder.f(new p(this.client.o().c()));
                if (!getUseCaches()) {
                    clientBuilder.d((okhttp3.c) null);
                }
                e a = clientBuilder.c().a(request);
                this.call = a;
                return a;
            } catch (IllegalArgumentException e) {
                MalformedURLException malformedUrl = new MalformedURLException();
                malformedUrl.initCause(e);
                throw malformedUrl;
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:17:0x001b, code lost:
            r1 = buildCall();
            r5.networkInterceptor.proceed();
            r2 = (com.didichuxing.doraemonkit.aop.urlconnection.ObsoleteUrlFactory.OutputStreamRequestBody) r1.g().a();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x002f, code lost:
            if (r2 == null) goto L_0x0036;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x0031, code lost:
            r2.outputStream.close();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x0038, code lost:
            if (r5.executed == false) goto L_0x0060;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x003a, code lost:
            r0 = r5.lock;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x003c, code lost:
            monitor-enter(r0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:0x003f, code lost:
            if (r5.response != null) goto L_0x004c;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:28:0x0043, code lost:
            if (r5.callFailure != null) goto L_0x004c;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:29:0x0045, code lost:
            r5.lock.wait();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:31:?, code lost:
            monitor-exit(r0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:33:0x004e, code lost:
            r3 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:36:0x0051, code lost:
            java.lang.Thread.currentThread().interrupt();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:37:0x005d, code lost:
            throw new java.io.InterruptedIOException();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:39:0x005f, code lost:
            throw r3;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:40:0x0060, code lost:
            r5.executed = true;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:42:?, code lost:
            onResponse(r1, r1.execute());
         */
        /* JADX WARNING: Code restructure failed: missing block: B:43:0x006b, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:44:0x006c, code lost:
            onFailure(r1, r0);
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private okhttp3.d0 getResponse(boolean r6) {
            /*
                r5 = this;
                java.lang.Object r0 = r5.lock
                monitor-enter(r0)
                okhttp3.d0 r1 = r5.response     // Catch:{ all -> 0x008b }
                if (r1 == 0) goto L_0x0009
                monitor-exit(r0)     // Catch:{ all -> 0x008b }
                return r1
            L_0x0009:
                java.lang.Throwable r1 = r5.callFailure     // Catch:{ all -> 0x008b }
                if (r1 == 0) goto L_0x001a
                if (r6 == 0) goto L_0x0015
                okhttp3.d0 r2 = r5.networkResponse     // Catch:{ all -> 0x008b }
                if (r2 == 0) goto L_0x0015
                monitor-exit(r0)     // Catch:{ all -> 0x008b }
                return r2
            L_0x0015:
                java.io.IOException r1 = com.didichuxing.doraemonkit.aop.urlconnection.ObsoleteUrlFactory.propagate(r1)     // Catch:{ all -> 0x008b }
                throw r1     // Catch:{ all -> 0x008b }
            L_0x001a:
                monitor-exit(r0)     // Catch:{ all -> 0x008b }
                okhttp3.e r1 = r5.buildCall()
                com.didichuxing.doraemonkit.aop.urlconnection.ObsoleteUrlFactory$OkHttpURLConnection$NetworkInterceptor r0 = r5.networkInterceptor
                r0.proceed()
                okhttp3.b0 r0 = r1.g()
                okhttp3.c0 r0 = r0.a()
                r2 = r0
                com.didichuxing.doraemonkit.aop.urlconnection.ObsoleteUrlFactory$OutputStreamRequestBody r2 = (com.didichuxing.doraemonkit.aop.urlconnection.ObsoleteUrlFactory.OutputStreamRequestBody) r2
                if (r2 == 0) goto L_0x0036
                java.io.OutputStream r0 = r2.outputStream
                r0.close()
            L_0x0036:
                boolean r0 = r5.executed
                if (r0 == 0) goto L_0x0060
                java.lang.Object r0 = r5.lock
                monitor-enter(r0)
            L_0x003d:
                okhttp3.d0 r3 = r5.response     // Catch:{ InterruptedException -> 0x0050 }
                if (r3 != 0) goto L_0x004b
                java.lang.Throwable r3 = r5.callFailure     // Catch:{ InterruptedException -> 0x0050 }
                if (r3 != 0) goto L_0x004b
                java.lang.Object r3 = r5.lock     // Catch:{ InterruptedException -> 0x0050 }
                r3.wait()     // Catch:{ InterruptedException -> 0x0050 }
                goto L_0x003d
            L_0x004b:
                monitor-exit(r0)     // Catch:{ all -> 0x004e }
                goto L_0x006f
            L_0x004e:
                r3 = move-exception
                goto L_0x005e
            L_0x0050:
                r3 = move-exception
                java.lang.Thread r4 = java.lang.Thread.currentThread()     // Catch:{ all -> 0x004e }
                r4.interrupt()     // Catch:{ all -> 0x004e }
                java.io.InterruptedIOException r4 = new java.io.InterruptedIOException     // Catch:{ all -> 0x004e }
                r4.<init>()     // Catch:{ all -> 0x004e }
                throw r4     // Catch:{ all -> 0x004e }
            L_0x005e:
                monitor-exit(r0)     // Catch:{ all -> 0x004e }
                throw r3
            L_0x0060:
                r0 = 1
                r5.executed = r0
                okhttp3.d0 r0 = r1.execute()     // Catch:{ IOException -> 0x006b }
                r5.onResponse(r1, r0)     // Catch:{ IOException -> 0x006b }
                goto L_0x006f
            L_0x006b:
                r0 = move-exception
                r5.onFailure(r1, r0)
            L_0x006f:
                java.lang.Object r3 = r5.lock
                monitor-enter(r3)
                java.lang.Throwable r0 = r5.callFailure     // Catch:{ all -> 0x0088 }
                if (r0 != 0) goto L_0x0083
                okhttp3.d0 r0 = r5.response     // Catch:{ all -> 0x0088 }
                if (r0 == 0) goto L_0x007c
                monitor-exit(r3)     // Catch:{ all -> 0x0088 }
                return r0
            L_0x007c:
                monitor-exit(r3)     // Catch:{ all -> 0x0088 }
                java.lang.AssertionError r0 = new java.lang.AssertionError
                r0.<init>()
                throw r0
            L_0x0083:
                java.io.IOException r0 = com.didichuxing.doraemonkit.aop.urlconnection.ObsoleteUrlFactory.propagate(r0)     // Catch:{ all -> 0x0088 }
                throw r0     // Catch:{ all -> 0x0088 }
            L_0x0088:
                r0 = move-exception
                monitor-exit(r3)     // Catch:{ all -> 0x0088 }
                throw r0
            L_0x008b:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x008b }
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.didichuxing.doraemonkit.aop.urlconnection.ObsoleteUrlFactory.OkHttpURLConnection.getResponse(boolean):okhttp3.d0");
        }

        public boolean usingProxy() {
            if (this.proxy != null) {
                return true;
            }
            Proxy clientProxy = this.client.D();
            if (clientProxy == null || clientProxy.type() == Proxy.Type.DIRECT) {
                return false;
            }
            return true;
        }

        public String getResponseMessage() {
            return getResponse(true).t();
        }

        public int getResponseCode() {
            return getResponse(true).j();
        }

        public void setRequestProperty(String field, String newValue) {
            if (this.connected) {
                throw new IllegalStateException("Cannot set request property after connection is made");
            } else if (field == null) {
                throw new NullPointerException("field == null");
            } else if (newValue != null) {
                this.requestHeaders.j(field, newValue);
            }
        }

        public void setIfModifiedSince(long newValue) {
            super.setIfModifiedSince(newValue);
            if (this.ifModifiedSince != 0) {
                this.requestHeaders.j(HttpHeaders.HEAD_KEY_IF_MODIFIED_SINCE, ObsoleteUrlFactory.format(new Date(this.ifModifiedSince)));
            } else {
                this.requestHeaders.i(HttpHeaders.HEAD_KEY_IF_MODIFIED_SINCE);
            }
        }

        public void addRequestProperty(String field, String value) {
            if (this.connected) {
                throw new IllegalStateException("Cannot add request property after connection is made");
            } else if (field == null) {
                throw new NullPointerException("field == null");
            } else if (value != null) {
                this.requestHeaders.a(field, value);
            }
        }

        public void setRequestMethod(String method) {
            Set<String> set = ObsoleteUrlFactory.METHODS;
            if (set.contains(method)) {
                this.method = method;
                return;
            }
            throw new ProtocolException("Expected one of " + set + " but was " + method);
        }

        public void setFixedLengthStreamingMode(int contentLength) {
            setFixedLengthStreamingMode((long) contentLength);
        }

        public void setFixedLengthStreamingMode(long contentLength) {
            if (this.connected) {
                throw new IllegalStateException("Already connected");
            } else if (this.chunkLength > 0) {
                throw new IllegalStateException("Already in chunked mode");
            } else if (contentLength >= 0) {
                this.fixedContentLength = contentLength;
                this.fixedContentLength = (int) Math.min(contentLength, 2147483647L);
            } else {
                throw new IllegalArgumentException("contentLength < 0");
            }
        }

        public void onFailure(e call2, IOException e) {
            synchronized (this.lock) {
                this.callFailure = e instanceof UnexpectedException ? e.getCause() : e;
                this.lock.notifyAll();
            }
        }

        public void onResponse(e call2, d0 response2) {
            synchronized (this.lock) {
                this.response = response2;
                this.handshake = response2.m();
                this.url = response2.J().l().v();
                this.lock.notifyAll();
            }
        }

        public final class NetworkInterceptor implements w {
            private boolean proceed;

            NetworkInterceptor() {
            }

            public void proceed() {
                synchronized (OkHttpURLConnection.this.lock) {
                    this.proceed = true;
                    OkHttpURLConnection.this.lock.notifyAll();
                }
            }

            public d0 intercept(w.a chain) {
                b0 request;
                b0 request2 = chain.g();
                synchronized (OkHttpURLConnection.this.lock) {
                    OkHttpURLConnection okHttpURLConnection = OkHttpURLConnection.this;
                    okHttpURLConnection.connectPending = false;
                    okHttpURLConnection.proxy = chain.b().a().b();
                    OkHttpURLConnection.this.handshake = chain.b().b();
                    OkHttpURLConnection.this.lock.notifyAll();
                    while (!this.proceed) {
                        try {
                            OkHttpURLConnection.this.lock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            throw new InterruptedIOException();
                        }
                    }
                }
                if (request2.a() instanceof OutputStreamRequestBody) {
                    request = ((OutputStreamRequestBody) request2.a()).prepareToSendRequest(request2);
                } else {
                    request = request2;
                }
                d0 response = chain.a(request);
                synchronized (OkHttpURLConnection.this.lock) {
                    OkHttpURLConnection okHttpURLConnection2 = OkHttpURLConnection.this;
                    okHttpURLConnection2.networkResponse = response;
                    URL unused = okHttpURLConnection2.url = response.J().l().v();
                }
                return response;
            }
        }
    }

    public static abstract class OutputStreamRequestBody extends c0 {
        boolean closed;
        long expectedContentLength;
        OutputStream outputStream;
        f0 timeout;

        OutputStreamRequestBody() {
        }

        /* access modifiers changed from: package-private */
        public void initOutputStream(final d sink, final long expectedContentLength2) {
            this.timeout = sink.timeout();
            this.expectedContentLength = expectedContentLength2;
            this.outputStream = new OutputStream() {
                private long bytesReceived;

                public void write(int b) {
                    write(new byte[]{(byte) b}, 0, 1);
                }

                public void write(byte[] source, int offset, int byteCount) {
                    if (!OutputStreamRequestBody.this.closed) {
                        long j = expectedContentLength2;
                        if (j == -1 || this.bytesReceived + ((long) byteCount) <= j) {
                            this.bytesReceived += (long) byteCount;
                            try {
                                sink.write(source, offset, byteCount);
                            } catch (InterruptedIOException e) {
                                throw new SocketTimeoutException(e.getMessage());
                            }
                        } else {
                            throw new ProtocolException("expected " + expectedContentLength2 + " bytes but received " + this.bytesReceived + byteCount);
                        }
                    } else {
                        throw new IOException("closed");
                    }
                }

                public void flush() {
                    if (!OutputStreamRequestBody.this.closed) {
                        sink.flush();
                    }
                }

                public void close() {
                    OutputStreamRequestBody.this.closed = true;
                    long j = expectedContentLength2;
                    if (j == -1 || this.bytesReceived >= j) {
                        sink.close();
                        return;
                    }
                    throw new ProtocolException("expected " + expectedContentLength2 + " bytes but received " + this.bytesReceived);
                }
            };
        }

        public long contentLength() {
            return this.expectedContentLength;
        }

        @Nullable
        public final x contentType() {
            return null;
        }

        public b0 prepareToSendRequest(b0 request) {
            return request;
        }
    }

    public static final class BufferedRequestBody extends OutputStreamRequestBody {
        final c buffer;
        long contentLength = -1;

        BufferedRequestBody(long expectedContentLength) {
            c cVar = new c();
            this.buffer = cVar;
            initOutputStream(cVar, expectedContentLength);
        }

        public long contentLength() {
            return this.contentLength;
        }

        public b0 prepareToSendRequest(b0 request) {
            if (request.d("Content-Length") != null) {
                return request;
            }
            this.outputStream.close();
            this.contentLength = this.buffer.e1();
            return request.i().m(Constants.TRANSFERENCODING).g("Content-Length", Long.toString(this.buffer.e1())).b();
        }

        public void writeTo(d sink) {
            this.buffer.j(sink.buffer(), 0, this.buffer.e1());
        }
    }

    public static final class StreamedRequestBody extends OutputStreamRequestBody {
        private final okio.v pipe;

        StreamedRequestBody(long expectedContentLength) {
            okio.v vVar = new okio.v(8192);
            this.pipe = vVar;
            initOutputStream(okio.p.c(vVar.i()), expectedContentLength);
        }

        public void writeTo(d sink) {
            c buffer = new c();
            while (this.pipe.j().read(buffer, 8192) != -1) {
                sink.write(buffer, buffer.e1());
            }
        }
    }

    public static abstract class DelegatingHttpsURLConnection extends HttpsURLConnection {
        private final HttpURLConnection delegate;

        public abstract HostnameVerifier getHostnameVerifier();

        public abstract SSLSocketFactory getSSLSocketFactory();

        /* access modifiers changed from: protected */
        public abstract t handshake();

        public abstract void setHostnameVerifier(HostnameVerifier hostnameVerifier);

        public abstract void setSSLSocketFactory(SSLSocketFactory sSLSocketFactory);

        DelegatingHttpsURLConnection(HttpURLConnection delegate2) {
            super(delegate2.getURL());
            this.delegate = delegate2;
        }

        public String getCipherSuite() {
            t handshake = handshake();
            if (handshake != null) {
                return handshake.a().c();
            }
            return null;
        }

        public Certificate[] getLocalCertificates() {
            t handshake = handshake();
            if (handshake == null) {
                return null;
            }
            List<Certificate> result = handshake.c();
            if (!result.isEmpty()) {
                return (Certificate[]) result.toArray(new Certificate[result.size()]);
            }
            return null;
        }

        public Certificate[] getServerCertificates() {
            t handshake = handshake();
            if (handshake == null) {
                return null;
            }
            List<Certificate> result = handshake.e();
            if (!result.isEmpty()) {
                return (Certificate[]) result.toArray(new Certificate[result.size()]);
            }
            return null;
        }

        public Principal getPeerPrincipal() {
            t handshake = handshake();
            if (handshake != null) {
                return handshake.f();
            }
            return null;
        }

        public Principal getLocalPrincipal() {
            t handshake = handshake();
            if (handshake != null) {
                return handshake.d();
            }
            return null;
        }

        public void connect() {
            this.connected = true;
            this.delegate.connect();
        }

        public void disconnect() {
            this.delegate.disconnect();
        }

        public InputStream getErrorStream() {
            return this.delegate.getErrorStream();
        }

        public String getRequestMethod() {
            return this.delegate.getRequestMethod();
        }

        public int getResponseCode() {
            return this.delegate.getResponseCode();
        }

        public String getResponseMessage() {
            return this.delegate.getResponseMessage();
        }

        public void setRequestMethod(String method) {
            this.delegate.setRequestMethod(method);
        }

        public boolean usingProxy() {
            return this.delegate.usingProxy();
        }

        public boolean getInstanceFollowRedirects() {
            return this.delegate.getInstanceFollowRedirects();
        }

        public void setInstanceFollowRedirects(boolean followRedirects) {
            this.delegate.setInstanceFollowRedirects(followRedirects);
        }

        public boolean getAllowUserInteraction() {
            return this.delegate.getAllowUserInteraction();
        }

        public Object getContent() {
            return this.delegate.getContent();
        }

        public Object getContent(Class[] types) {
            return this.delegate.getContent(types);
        }

        public String getContentEncoding() {
            return this.delegate.getContentEncoding();
        }

        public int getContentLength() {
            return this.delegate.getContentLength();
        }

        @RequiresApi(api = 24)
        public long getContentLengthLong() {
            return this.delegate.getContentLengthLong();
        }

        public String getContentType() {
            return this.delegate.getContentType();
        }

        public long getDate() {
            return this.delegate.getDate();
        }

        public boolean getDefaultUseCaches() {
            return this.delegate.getDefaultUseCaches();
        }

        public boolean getDoInput() {
            return this.delegate.getDoInput();
        }

        public boolean getDoOutput() {
            return this.delegate.getDoOutput();
        }

        public long getExpiration() {
            return this.delegate.getExpiration();
        }

        public String getHeaderField(int pos) {
            return this.delegate.getHeaderField(pos);
        }

        public Map<String, List<String>> getHeaderFields() {
            return this.delegate.getHeaderFields();
        }

        public Map<String, List<String>> getRequestProperties() {
            return this.delegate.getRequestProperties();
        }

        public void addRequestProperty(String field, String newValue) {
            this.delegate.addRequestProperty(field, newValue);
        }

        public String getHeaderField(String key) {
            return this.delegate.getHeaderField(key);
        }

        @RequiresApi(api = 24)
        public long getHeaderFieldLong(String field, long defaultValue) {
            return this.delegate.getHeaderFieldLong(field, defaultValue);
        }

        public long getHeaderFieldDate(String field, long defaultValue) {
            return this.delegate.getHeaderFieldDate(field, defaultValue);
        }

        public int getHeaderFieldInt(String field, int defaultValue) {
            return this.delegate.getHeaderFieldInt(field, defaultValue);
        }

        public String getHeaderFieldKey(int position) {
            return this.delegate.getHeaderFieldKey(position);
        }

        public long getIfModifiedSince() {
            return this.delegate.getIfModifiedSince();
        }

        public InputStream getInputStream() {
            return this.delegate.getInputStream();
        }

        public long getLastModified() {
            return this.delegate.getLastModified();
        }

        public OutputStream getOutputStream() {
            return this.delegate.getOutputStream();
        }

        public Permission getPermission() {
            return this.delegate.getPermission();
        }

        public String getRequestProperty(String field) {
            return this.delegate.getRequestProperty(field);
        }

        public URL getURL() {
            return this.delegate.getURL();
        }

        public boolean getUseCaches() {
            return this.delegate.getUseCaches();
        }

        public void setAllowUserInteraction(boolean newValue) {
            this.delegate.setAllowUserInteraction(newValue);
        }

        public void setDefaultUseCaches(boolean newValue) {
            this.delegate.setDefaultUseCaches(newValue);
        }

        public void setDoInput(boolean newValue) {
            this.delegate.setDoInput(newValue);
        }

        public void setDoOutput(boolean newValue) {
            this.delegate.setDoOutput(newValue);
        }

        @RequiresApi(api = 19)
        public void setFixedLengthStreamingMode(long contentLength) {
            this.delegate.setFixedLengthStreamingMode(contentLength);
        }

        public void setIfModifiedSince(long newValue) {
            this.delegate.setIfModifiedSince(newValue);
        }

        public void setRequestProperty(String field, String newValue) {
            this.delegate.setRequestProperty(field, newValue);
        }

        public void setUseCaches(boolean newValue) {
            this.delegate.setUseCaches(newValue);
        }

        public void setConnectTimeout(int timeoutMillis) {
            this.delegate.setConnectTimeout(timeoutMillis);
        }

        public int getConnectTimeout() {
            return this.delegate.getConnectTimeout();
        }

        public void setReadTimeout(int timeoutMillis) {
            this.delegate.setReadTimeout(timeoutMillis);
        }

        public int getReadTimeout() {
            return this.delegate.getReadTimeout();
        }

        public String toString() {
            return this.delegate.toString();
        }

        public void setFixedLengthStreamingMode(int contentLength) {
            this.delegate.setFixedLengthStreamingMode(contentLength);
        }

        public void setChunkedStreamingMode(int chunkLength) {
            this.delegate.setChunkedStreamingMode(chunkLength);
        }
    }

    public static final class OkHttpsURLConnection extends DelegatingHttpsURLConnection {
        private final OkHttpURLConnection delegate;

        OkHttpsURLConnection(URL url, z client) {
            this(new OkHttpURLConnection(url, client));
        }

        OkHttpsURLConnection(OkHttpURLConnection delegate2) {
            super(delegate2);
            this.delegate = delegate2;
        }

        /* access modifiers changed from: protected */
        public t handshake() {
            OkHttpURLConnection okHttpURLConnection = this.delegate;
            if (okHttpURLConnection.call != null) {
                return okHttpURLConnection.handshake;
            }
            throw new IllegalStateException("Connection has not yet been established");
        }

        public void setHostnameVerifier(HostnameVerifier hostnameVerifier) {
            OkHttpURLConnection okHttpURLConnection = this.delegate;
            okHttpURLConnection.client = okHttpURLConnection.client.z().M(hostnameVerifier).c();
        }

        public HostnameVerifier getHostnameVerifier() {
            return this.delegate.client.u();
        }

        public void setSSLSocketFactory(SSLSocketFactory sslSocketFactory) {
            if (sslSocketFactory != null) {
                OkHttpURLConnection okHttpURLConnection = this.delegate;
                okHttpURLConnection.client = okHttpURLConnection.client.z().T(sslSocketFactory).c();
                return;
            }
            throw new IllegalArgumentException("sslSocketFactory == null");
        }

        public SSLSocketFactory getSSLSocketFactory() {
            return this.delegate.client.L();
        }
    }

    public static final class UnexpectedException extends IOException {
        static final w INTERCEPTOR = new w() {
            public d0 intercept(w.a chain) {
                try {
                    return chain.a(chain.g());
                } catch (Error | RuntimeException e) {
                    throw new UnexpectedException(e);
                }
            }
        };

        UnexpectedException(Throwable cause) {
            super(cause);
        }
    }
}
