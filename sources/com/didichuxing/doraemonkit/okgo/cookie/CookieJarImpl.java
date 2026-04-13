package com.didichuxing.doraemonkit.okgo.cookie;

import com.didichuxing.doraemonkit.okgo.cookie.store.CookieStore;
import java.util.List;
import okhttp3.m;
import okhttp3.n;
import okhttp3.v;

public class CookieJarImpl implements n {
    private CookieStore cookieStore;

    public CookieJarImpl(CookieStore cookieStore2) {
        if (cookieStore2 != null) {
            this.cookieStore = cookieStore2;
            return;
        }
        throw new IllegalArgumentException("cookieStore can not be null!");
    }

    public synchronized void saveFromResponse(v url, List<m> cookies) {
        this.cookieStore.saveCookie(url, cookies);
    }

    public synchronized List<m> loadForRequest(v url) {
        return this.cookieStore.loadCookie(url);
    }

    public CookieStore getCookieStore() {
        return this.cookieStore;
    }
}
