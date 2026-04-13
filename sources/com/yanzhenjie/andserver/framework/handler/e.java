package com.yanzhenjie.andserver.framework.handler;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.amazonaws.auth.policy.internal.JsonDocumentFields;
import com.yanzhenjie.andserver.error.NotFoundException;
import com.yanzhenjie.andserver.framework.cross.a;
import com.yanzhenjie.andserver.framework.mapping.b;
import com.yanzhenjie.andserver.http.HttpMethod;
import com.yanzhenjie.andserver.http.c;
import com.yanzhenjie.andserver.http.d;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import org.glassfish.tyrus.spi.UpgradeRequest;

/* compiled from: OptionsHandler */
public class e implements d {
    private List<b> h;
    private Map<b, f> i;
    private b j;
    private d k;

    public e(c optionsRequest, List<b> mappings, Map<b, f> mappingMap) {
        b exactMapping;
        this.h = mappings;
        this.i = mappingMap;
        this.j = mappings.get(0);
        String requestMethod = optionsRequest.getHeader("Access-Control-Request-Method");
        if (!TextUtils.isEmpty(requestMethod) && (exactMapping = b.b(this.h, com.yanzhenjie.andserver.http.b.reverse(requestMethod))) != null) {
            this.j = exactMapping;
        }
        this.k = (d) this.i.get(this.j);
    }

    @Nullable
    public a b() {
        return this.k.b();
    }

    public com.yanzhenjie.andserver.framework.view.c c(@NonNull c request, @NonNull d response) {
        List<String> requestHeaders;
        StringTokenizer st;
        List<String> requestHeaders2;
        c cVar = request;
        d dVar = response;
        String requestOrigin = cVar.getHeader(UpgradeRequest.ORIGIN_HEADER);
        if (TextUtils.isEmpty(requestOrigin)) {
            return a(dVar);
        }
        String requestMethodText = cVar.getHeader("Access-Control-Request-Method");
        if (TextUtils.isEmpty(requestMethodText)) {
            return a(dVar);
        }
        com.yanzhenjie.andserver.http.b requestMethod = com.yanzhenjie.andserver.http.b.reverse(requestMethodText);
        b mapping = b.b(this.h, requestMethod);
        if (mapping == null) {
            return a(dVar);
        }
        d handler = (d) this.i.get(mapping);
        if (handler != null) {
            a crossOrigin = handler.b();
            if (crossOrigin == null) {
                return a(dVar);
            }
            List<HttpMethod> allowMethods = new ArrayList<>();
            Collections.addAll(allowMethods, crossOrigin.d());
            List<com.yanzhenjie.andserver.http.b> b = mapping.c().b();
            if (allowMethods.isEmpty()) {
                allowMethods.addAll(b);
            }
            if (!allowMethods.contains(requestMethod)) {
                return a(dVar);
            }
            List<String> allowOrigins = Arrays.asList(crossOrigin.e());
            if (!allowOrigins.isEmpty() && !allowOrigins.contains(org.slf4j.e.ANY_MARKER) && !allowOrigins.contains(requestOrigin)) {
                return a(dVar);
            }
            List<String> allowedHeaders = Arrays.asList(crossOrigin.a());
            List<String> outHeaders = new ArrayList<>();
            String str = requestMethodText;
            String requestHeader = cVar.getHeader("Access-Control-Request-Headers");
            List<String> requestHeaders3 = new ArrayList<>();
            if (!TextUtils.isEmpty(requestHeader)) {
                com.yanzhenjie.andserver.http.b bVar = requestMethod;
                StringTokenizer st2 = new StringTokenizer(requestHeader, ",");
                while (st2.hasMoreTokens()) {
                    String token = st2.nextToken().trim();
                    if (token.length() > 0) {
                        st = st2;
                        requestHeaders2 = requestHeaders3;
                        requestHeaders2.add(token);
                    } else {
                        st = st2;
                        requestHeaders2 = requestHeaders3;
                    }
                    requestHeaders3 = requestHeaders2;
                    st2 = st;
                }
                requestHeaders = requestHeaders3;
            } else {
                requestHeaders = requestHeaders3;
            }
            if (allowedHeaders.contains(org.slf4j.e.ANY_MARKER)) {
                if (requestHeaders.size() > 0) {
                    outHeaders.addAll(requestHeaders);
                    String str2 = requestHeader;
                } else {
                    String str3 = requestHeader;
                }
            } else if (allowedHeaders.size() <= 0) {
                String headerHeadersText = requestHeader;
                if (requestHeaders.size() > 0) {
                    outHeaders.addAll(requestHeaders);
                }
            } else if (requestHeaders.size() > 0) {
                for (String allowedHeader : allowedHeaders) {
                    for (String requestHeader2 : requestHeaders) {
                        String headerHeadersText2 = requestHeader;
                        if (allowedHeader.equalsIgnoreCase(requestHeader2)) {
                            outHeaders.add(requestHeader2);
                        }
                        requestHeader = headerHeadersText2;
                    }
                    String headerHeadersText3 = requestHeader;
                }
                String headerHeadersText4 = requestHeader;
                if (outHeaders.isEmpty()) {
                    return a(dVar);
                }
            } else {
                String headerHeadersText5 = requestHeader;
            }
            String[] exposeHeaders = crossOrigin.b();
            dVar.setHeader("Access-Control-Allow-Origin", requestOrigin);
            dVar.setHeader("Access-Control-Allow-Methods", TextUtils.join(", ", allowMethods));
            if (outHeaders.size() > 0) {
                dVar.setHeader("Access-Control-Allow-Headers", TextUtils.join(", ", outHeaders));
            }
            if (exposeHeaders.length > 0) {
                dVar.setHeader("Access-Control-Expose-Headers", TextUtils.join(", ", exposeHeaders));
            }
            boolean credentials = crossOrigin.f();
            boolean z = credentials;
            dVar.setHeader("Access-Control-Allow-Credentials", Boolean.toString(credentials));
            dVar.setHeader("Access-Control-Max-Age", Long.toString(crossOrigin.c()));
            dVar.setHeader(JsonDocumentFields.EFFECT_VALUE_ALLOW, TextUtils.join(", ", com.yanzhenjie.andserver.http.b.values()));
            dVar.setHeader("Vary", UpgradeRequest.ORIGIN_HEADER);
            return new com.yanzhenjie.andserver.framework.view.a(new com.yanzhenjie.andserver.framework.body.b("OK"));
        }
        throw new NotFoundException();
    }

    private com.yanzhenjie.andserver.framework.view.c a(d response) {
        response.e(403);
        response.setHeader(JsonDocumentFields.EFFECT_VALUE_ALLOW, TextUtils.join(", ", com.yanzhenjie.andserver.http.b.values()));
        return new com.yanzhenjie.andserver.framework.view.a(new com.yanzhenjie.andserver.framework.body.b("Invalid CORS request."));
    }

    public String f(@NonNull c request) {
        return this.k.f(request);
    }

    public long e(@NonNull c request) {
        return this.k.e(request);
    }
}
