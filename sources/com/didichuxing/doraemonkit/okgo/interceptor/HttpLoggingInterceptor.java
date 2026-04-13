package com.didichuxing.doraemonkit.okgo.interceptor;

import com.didichuxing.doraemonkit.okgo.utils.IOUtils;
import com.didichuxing.doraemonkit.okgo.utils.OkLogger;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import okhttp3.a0;
import okhttp3.b0;
import okhttp3.c0;
import okhttp3.d0;
import okhttp3.e0;
import okhttp3.internal.http.e;
import okhttp3.j;
import okhttp3.u;
import okhttp3.w;
import okhttp3.x;
import okio.c;

public class HttpLoggingInterceptor implements w {
    private static final Charset UTF8 = Charset.forName("UTF-8");
    private java.util.logging.Level colorLevel;
    private Logger logger;
    private volatile Level printLevel = Level.NONE;

    public enum Level {
        NONE,
        BASIC,
        HEADERS,
        BODY
    }

    public HttpLoggingInterceptor(String tag) {
        this.logger = Logger.getLogger(tag);
    }

    public void setPrintLevel(Level level) {
        if (level != null) {
            this.printLevel = level;
            return;
        }
        throw new NullPointerException("level == null. Use Level.NONE instead.");
    }

    public void setColorLevel(java.util.logging.Level level) {
        this.colorLevel = level;
    }

    private void log(String message) {
        this.logger.log(this.colorLevel, message);
    }

    public d0 intercept(w.a chain) {
        d0 response;
        b0 request = chain.g();
        if (this.printLevel == Level.NONE) {
            return chain.a(request);
        }
        logForRequest(request, chain.b());
        long startNs = System.nanoTime();
        try {
            response = chain.a(request);
        } catch (Exception e) {
            log("<-- HTTP FAILED: " + e);
            x h = x.h("text/plain;charset=utf-8");
            e0 responseBody = e0.create(h, "" + e.getMessage());
            d0.a g = new d0.a().g(404);
            response = g.m("" + e.getMessage()).r(request).b(responseBody).p(a0.HTTP_1_1).c();
        }
        return logForResponse(response, TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs));
    }

    private void logForRequest(b0 request, j connection) {
        StringBuilder sb;
        Level level = this.printLevel;
        Level level2 = Level.BODY;
        boolean hasRequestBody = false;
        boolean logBody = level == level2;
        boolean logHeaders = this.printLevel == level2 || this.printLevel == Level.HEADERS;
        c0 requestBody = request.a();
        if (requestBody != null) {
            hasRequestBody = true;
        }
        try {
            log("--> " + request.h() + ' ' + request.l() + ' ' + (connection != null ? connection.protocol() : a0.HTTP_1_1));
            if (logHeaders) {
                if (hasRequestBody) {
                    if (requestBody.contentType() != null) {
                        log("\tContent-Type: " + requestBody.contentType());
                    }
                    if (requestBody.contentLength() != -1) {
                        log("\tContent-Length: " + requestBody.contentLength());
                    }
                }
                u headers = request.f();
                int count = headers.size();
                for (int i = 0; i < count; i++) {
                    String name = headers.b(i);
                    if (!"Content-Type".equalsIgnoreCase(name) && !"Content-Length".equalsIgnoreCase(name)) {
                        log("\t" + name + ": " + headers.h(i));
                    }
                }
                log(" ");
                if (logBody && hasRequestBody) {
                    if (isPlaintext(requestBody.contentType())) {
                        bodyToString(request);
                    } else {
                        log("\tbody: maybe [binary body], omitted!");
                    }
                }
            }
            sb = new StringBuilder();
        } catch (Exception e) {
            OkLogger.printStackTrace(e);
            sb = new StringBuilder();
        } catch (Throwable th) {
            log("--> END " + request.h());
            throw th;
        }
        sb.append("--> END ");
        sb.append(request.h());
        log(sb.toString());
    }

    private d0 logForResponse(d0 response, long tookMs) {
        try {
            d0 clone = response.v().c();
            e0 responseBody = clone.a();
            Level level = this.printLevel;
            Level level2 = Level.BODY;
            boolean z = false;
            boolean logBody = level == level2;
            if (this.printLevel == level2 || this.printLevel == Level.HEADERS) {
                z = true;
            }
            boolean logHeaders = z;
            log("<-- " + clone.j() + ' ' + clone.t() + ' ' + clone.J().l() + " (" + tookMs + "ms）");
            if (logHeaders) {
                u headers = clone.s();
                int count = headers.size();
                for (int i = 0; i < count; i++) {
                    log("\t" + headers.b(i) + ": " + headers.h(i));
                }
                log(" ");
                if (logBody && e.a(clone)) {
                    if (responseBody == null) {
                        log("<-- END HTTP");
                        return response;
                    } else if (isPlaintext(responseBody.contentType())) {
                        byte[] bytes = IOUtils.toByteArray(responseBody.byteStream());
                        log("\tbody:" + new String(bytes, getCharset(responseBody.contentType())));
                        d0 c = response.v().b(e0.create(responseBody.contentType(), bytes)).c();
                        log("<-- END HTTP");
                        return c;
                    } else {
                        log("\tbody: maybe [binary body], omitted!");
                    }
                }
            }
        } catch (Exception e) {
        } catch (Throwable th) {
            log("<-- END HTTP");
            throw th;
        }
        log("<-- END HTTP");
        return response;
    }

    private static Charset getCharset(x contentType) {
        Charset charset = UTF8;
        if (contentType != null) {
            charset = contentType.d(charset);
        }
        if (charset == null) {
            return UTF8;
        }
        return charset;
    }

    private static boolean isPlaintext(x mediaType) {
        if (mediaType == null) {
            return false;
        }
        if (mediaType.j() != null && mediaType.j().equals("text")) {
            return true;
        }
        String subtype = mediaType.i();
        if (subtype != null) {
            String subtype2 = subtype.toLowerCase();
            if (subtype2.contains("x-www-form-urlencoded") || subtype2.contains("json") || subtype2.contains("xml") || subtype2.contains("html")) {
                return true;
            }
        }
        return false;
    }

    private void bodyToString(b0 request) {
        try {
            c0 body = request.i().b().a();
            if (body != null) {
                c buffer = new c();
                body.writeTo(buffer);
                Charset charset = getCharset(body.contentType());
                log("\tbody:" + buffer.x0(charset));
            }
        } catch (Exception e) {
            OkLogger.printStackTrace(e);
        }
    }
}
