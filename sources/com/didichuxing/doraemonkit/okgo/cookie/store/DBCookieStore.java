package com.didichuxing.doraemonkit.okgo.cookie.store;

import android.content.Context;
import com.didichuxing.doraemonkit.okgo.cookie.SerializableCookie;
import com.didichuxing.doraemonkit.okgo.db.CookieManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import okhttp3.Cookie;
import okhttp3.m;
import okhttp3.v;

public class DBCookieStore implements CookieStore {
    private final Map<String, ConcurrentHashMap<String, m>> cookies = new HashMap();

    public DBCookieStore(Context context) {
        CookieManager.init(context);
        for (SerializableCookie serializableCookie : CookieManager.getInstance().queryAll()) {
            if (!this.cookies.containsKey(serializableCookie.host)) {
                this.cookies.put(serializableCookie.host, new ConcurrentHashMap());
            }
            m cookie = serializableCookie.getCookie();
            this.cookies.get(serializableCookie.host).put(getCookieToken(cookie), cookie);
        }
    }

    private String getCookieToken(m cookie) {
        return cookie.i() + "@" + cookie.e();
    }

    private static boolean isCookieExpired(m cookie) {
        return cookie.f() < System.currentTimeMillis();
    }

    public synchronized void saveCookie(v url, List<m> urlCookies) {
        for (m cookie : urlCookies) {
            saveCookie(url, cookie);
        }
    }

    public synchronized void saveCookie(v url, m cookie) {
        if (!this.cookies.containsKey(url.j())) {
            this.cookies.put(url.j(), new ConcurrentHashMap());
        }
        if (isCookieExpired(cookie)) {
            removeCookie(url, cookie);
        } else {
            this.cookies.get(url.j()).put(getCookieToken(cookie), cookie);
            CookieManager.getInstance().replace(new SerializableCookie(url.j(), cookie));
        }
    }

    public synchronized List<m> loadCookie(v url) {
        List<Cookie> ret = new ArrayList<>();
        if (!this.cookies.containsKey(url.j())) {
            return ret;
        }
        for (SerializableCookie serializableCookie : CookieManager.getInstance().query("host=?", new String[]{url.j()})) {
            m cookie = serializableCookie.getCookie();
            if (isCookieExpired(cookie)) {
                removeCookie(url, cookie);
            } else {
                ret.add(cookie);
            }
        }
        return ret;
    }

    public synchronized boolean removeCookie(v url, m cookie) {
        if (!this.cookies.containsKey(url.j())) {
            return false;
        }
        String cookieToken = getCookieToken(cookie);
        if (!this.cookies.get(url.j()).containsKey(cookieToken)) {
            return false;
        }
        this.cookies.get(url.j()).remove(cookieToken);
        CookieManager.getInstance().delete("host=? and name=? and domain=?", new String[]{url.j(), cookie.i(), cookie.e()});
        return true;
    }

    public synchronized boolean removeCookie(v url) {
        if (!this.cookies.containsKey(url.j())) {
            return false;
        }
        this.cookies.remove(url.j());
        CookieManager.getInstance().delete("host=?", new String[]{url.j()});
        return true;
    }

    public synchronized boolean removeAllCookie() {
        this.cookies.clear();
        CookieManager.getInstance().deleteAll();
        return true;
    }

    public synchronized List<m> getAllCookie() {
        List<Cookie> ret;
        ret = new ArrayList<>();
        for (String key : this.cookies.keySet()) {
            ret.addAll(this.cookies.get(key).values());
        }
        return ret;
    }

    public synchronized List<m> getCookie(v url) {
        List<Cookie> ret;
        ret = new ArrayList<>();
        Map<String, Cookie> mapCookie = this.cookies.get(url.j());
        if (mapCookie != null) {
            ret.addAll(mapCookie.values());
        }
        return ret;
    }
}
