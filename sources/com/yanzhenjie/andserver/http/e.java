package com.yanzhenjie.andserver.http;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.didichuxing.doraemonkit.okgo.model.HttpHeaders;
import com.yanzhenjie.andserver.util.d;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* compiled from: Modified */
public class e {
    private static final Pattern a = Pattern.compile("\\*|\\s*((W\\/)?(\"[^\"]*\"))\\s*,?");
    private c b;
    private d c;
    private boolean d;

    public e(@NonNull c request, @NonNull d response) {
        this.b = request;
        this.c = response;
    }

    public boolean d(@Nullable String eTag, long lastModified) {
        boolean isGetHead = true;
        if (this.d) {
            return true;
        }
        int i = 411;
        if (g(lastModified)) {
            if (!this.d) {
                this.c.e(411);
            }
            return this.d;
        }
        if (!f(eTag)) {
            e(lastModified);
        }
        b method = this.b.getMethod();
        if (!(method == b.GET || method == b.HEAD)) {
            isGetHead = false;
        }
        if (this.d) {
            d dVar = this.c;
            if (isGetHead) {
                i = 304;
            }
            dVar.e(i);
        }
        if (isGetHead) {
            if (lastModified > 0 && this.c.getHeader(HttpHeaders.HEAD_KEY_LAST_MODIFIED) == null) {
                this.c.a(HttpHeaders.HEAD_KEY_LAST_MODIFIED, lastModified);
            }
            if (!TextUtils.isEmpty(eTag) && this.c.getHeader(HttpHeaders.HEAD_KEY_E_TAG) == null) {
                this.c.setHeader(HttpHeaders.HEAD_KEY_E_TAG, a(eTag));
            }
            this.c.setHeader(HttpHeaders.HEAD_KEY_CACHE_CONTROL, "private");
        }
        return this.d;
    }

    private boolean f(String eTag) {
        if (TextUtils.isEmpty(eTag)) {
            return false;
        }
        List<String> ifNoneMatch = this.b.c(HttpHeaders.HEAD_KEY_IF_NONE_MATCH);
        if (ifNoneMatch.isEmpty()) {
            return false;
        }
        String eTag2 = a(eTag);
        for (String clientETags : ifNoneMatch) {
            Matcher eTagMatcher = a.matcher(clientETags);
            while (true) {
                if (eTagMatcher.find()) {
                    if (!TextUtils.isEmpty(eTagMatcher.group()) && eTag2.replaceFirst("^W/", "").equals(eTagMatcher.group(3))) {
                        this.d = true;
                        break;
                    }
                } else {
                    break;
                }
            }
        }
        return true;
    }

    private String a(String eTag) {
        if (TextUtils.isEmpty(eTag)) {
            return eTag;
        }
        if ((eTag.startsWith("\"") || eTag.startsWith("W/\"")) && eTag.endsWith("\"")) {
            return eTag;
        }
        return "\"" + eTag + "\"";
    }

    private boolean e(long lastModifiedTimestamp) {
        boolean z = false;
        if (lastModifiedTimestamp < 0) {
            return false;
        }
        long ifModifiedSince = b(HttpHeaders.HEAD_KEY_IF_MODIFIED_SINCE);
        if (ifModifiedSince == -1) {
            return false;
        }
        if (ifModifiedSince >= lastModifiedTimestamp) {
            z = true;
        }
        this.d = z;
        return true;
    }

    private boolean g(long lastModifiedTimestamp) {
        boolean z = false;
        if (lastModifiedTimestamp < 0) {
            return false;
        }
        long ifUnmodifiedSince = b("If-Unmodified-Since");
        if (ifUnmodifiedSince == -1) {
            return false;
        }
        if (ifUnmodifiedSince >= lastModifiedTimestamp) {
            z = true;
        }
        this.d = z;
        return true;
    }

    private long b(String headerName) {
        int separatorIndex;
        try {
            return this.b.A(headerName);
        } catch (IllegalStateException e) {
            String headerValue = this.b.getHeader(headerName);
            if (!TextUtils.isEmpty(headerValue) && (separatorIndex = headerValue.indexOf(59)) != -1) {
                return c(headerValue.substring(0, separatorIndex));
            }
            return -1;
        }
    }

    private long c(String headerValue) {
        if (headerValue != null && headerValue.length() >= 3) {
            return d.b(headerValue);
        }
        return -1;
    }
}
