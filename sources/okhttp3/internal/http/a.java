package okhttp3.internal.http;

import com.didichuxing.doraemonkit.okgo.model.HttpHeaders;
import java.util.List;
import kotlin.collections.q;
import kotlin.jvm.internal.k;
import okhttp3.b0;
import okhttp3.c0;
import okhttp3.d0;
import okhttp3.e0;
import okhttp3.internal.b;
import okhttp3.n;
import okhttp3.w;
import okhttp3.x;
import okio.m;
import okio.p;
import org.glassfish.grizzly.http.GZipContentEncoding;
import org.glassfish.grizzly.http.server.Constants;
import org.jetbrains.annotations.NotNull;

/* compiled from: BridgeInterceptor.kt */
public final class a implements w {
    private final n b;

    public a(@NotNull n cookieJar) {
        k.f(cookieJar, "cookieJar");
        this.b = cookieJar;
    }

    @NotNull
    public d0 intercept(@NotNull w.a chain) {
        e0 responseBody;
        w.a aVar = chain;
        k.f(aVar, "chain");
        b0 userRequest = chain.g();
        b0.a requestBuilder = userRequest.i();
        c0 body = userRequest.a();
        if (body != null) {
            x contentType = body.contentType();
            if (contentType != null) {
                requestBuilder.g("Content-Type", contentType.toString());
            }
            long contentLength = body.contentLength();
            if (contentLength != -1) {
                requestBuilder.g("Content-Length", String.valueOf(contentLength));
                requestBuilder.m(Constants.TRANSFERENCODING);
            } else {
                requestBuilder.g(Constants.TRANSFERENCODING, "chunked");
                requestBuilder.m("Content-Length");
            }
        }
        if (userRequest.d("Host") == null) {
            requestBuilder.g("Host", b.Q(userRequest.l(), false, 1, (Object) null));
        }
        if (userRequest.d("Connection") == null) {
            requestBuilder.g("Connection", "Keep-Alive");
        }
        boolean transparentGzip = false;
        if (userRequest.d(HttpHeaders.HEAD_KEY_ACCEPT_ENCODING) == null && userRequest.d(HttpHeaders.HEAD_KEY_RANGE) == null) {
            transparentGzip = true;
            requestBuilder.g(HttpHeaders.HEAD_KEY_ACCEPT_ENCODING, GZipContentEncoding.NAME);
        }
        List cookies = this.b.loadForRequest(userRequest.l());
        if (!cookies.isEmpty()) {
            requestBuilder.g(HttpHeaders.HEAD_KEY_COOKIE, a(cookies));
        }
        if (userRequest.d("User-Agent") == null) {
            requestBuilder.g("User-Agent", "okhttp/4.8.1");
        }
        d0 networkResponse = aVar.a(requestBuilder.b());
        e.g(this.b, userRequest.l(), networkResponse.s());
        d0.a responseBuilder = networkResponse.v().r(userRequest);
        if (transparentGzip && kotlin.text.w.y(GZipContentEncoding.NAME, d0.r(networkResponse, HttpHeaders.HEAD_KEY_CONTENT_ENCODING, (String) null, 2, (Object) null), true) && e.c(networkResponse) && (responseBody = networkResponse.a()) != null) {
            m gzipSource = new m(responseBody.source());
            responseBuilder.k(networkResponse.s().f().i(HttpHeaders.HEAD_KEY_CONTENT_ENCODING).i("Content-Length").f());
            responseBuilder.b(new h(d0.r(networkResponse, "Content-Type", (String) null, 2, (Object) null), -1, p.d(gzipSource)));
        }
        return responseBuilder.c();
    }

    private final String a(List<okhttp3.m> cookies) {
        StringBuilder sb = new StringBuilder();
        StringBuilder $this$buildString = sb;
        int index = 0;
        for (T next : cookies) {
            int index$iv = index + 1;
            if (index < 0) {
                q.q();
            }
            okhttp3.m cookie = (okhttp3.m) next;
            if (index > 0) {
                $this$buildString.append("; ");
            }
            $this$buildString.append(cookie.i());
            $this$buildString.append('=');
            $this$buildString.append(cookie.n());
            index = index$iv;
        }
        String sb2 = sb.toString();
        k.b(sb2, "StringBuilder().apply(builderAction).toString()");
        return sb2;
    }
}
