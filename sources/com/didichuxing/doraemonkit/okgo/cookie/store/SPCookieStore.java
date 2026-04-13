package com.didichuxing.doraemonkit.okgo.cookie.store;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.didichuxing.doraemonkit.okgo.cookie.SerializableCookie;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import okhttp3.Cookie;
import okhttp3.m;
import okhttp3.v;

public class SPCookieStore implements CookieStore {
    private static final String COOKIE_NAME_PREFIX = "cookie_";
    private static final String COOKIE_PREFS = "okgo_cookie";
    private final SharedPreferences cookiePrefs;
    private final Map<String, ConcurrentHashMap<String, m>> cookies = new HashMap();

    public SPCookieStore(Context context) {
        m decodedCookie;
        SharedPreferences sharedPreferences = context.getSharedPreferences(COOKIE_PREFS, 0);
        this.cookiePrefs = sharedPreferences;
        for (Map.Entry<String, ?> entry : sharedPreferences.getAll().entrySet()) {
            if (entry.getValue() != null && !entry.getKey().startsWith(COOKIE_NAME_PREFIX)) {
                for (String name : TextUtils.split((String) entry.getValue(), ",")) {
                    String encodedCookie = this.cookiePrefs.getString(COOKIE_NAME_PREFIX + name, (String) null);
                    if (!(encodedCookie == null || (decodedCookie = SerializableCookie.decodeCookie(encodedCookie)) == null)) {
                        if (!this.cookies.containsKey(entry.getKey())) {
                            this.cookies.put(entry.getKey(), new ConcurrentHashMap());
                        }
                        this.cookies.get(entry.getKey()).put(name, decodedCookie);
                    }
                }
            }
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
            saveCookie(url, cookie, getCookieToken(cookie));
        }
    }

    private void saveCookie(v url, m cookie, String cookieToken) {
        this.cookies.get(url.j()).put(cookieToken, cookie);
        SharedPreferences.Editor prefsWriter = this.cookiePrefs.edit();
        prefsWriter.putString(url.j(), TextUtils.join(",", this.cookies.get(url.j()).keySet()));
        prefsWriter.putString(COOKIE_NAME_PREFIX + cookieToken, SerializableCookie.encodeCookie(url.j(), cookie));
        prefsWriter.apply();
    }

    public synchronized List<m> loadCookie(v url) {
        List<Cookie> ret = new ArrayList<>();
        if (!this.cookies.containsKey(url.j())) {
            return ret;
        }
        Iterator<Cookie> it = this.cookies.get(url.j()).values().iterator();
        while (it.hasNext()) {
            m cookie = (m) it.next();
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
        SharedPreferences.Editor prefsWriter = this.cookiePrefs.edit();
        SharedPreferences sharedPreferences = this.cookiePrefs;
        if (sharedPreferences.contains(COOKIE_NAME_PREFIX + cookieToken)) {
            prefsWriter.remove(COOKIE_NAME_PREFIX + cookieToken);
        }
        prefsWriter.putString(url.j(), TextUtils.join(",", this.cookies.get(url.j()).keySet()));
        prefsWriter.apply();
        return true;
    }

    public synchronized boolean removeCookie(v url) {
        if (!this.cookies.containsKey(url.j())) {
            return false;
        }
        Set<String> cookieTokens = this.cookies.remove(url.j()).keySet();
        SharedPreferences.Editor prefsWriter = this.cookiePrefs.edit();
        for (String cookieToken : cookieTokens) {
            SharedPreferences sharedPreferences = this.cookiePrefs;
            if (sharedPreferences.contains(COOKIE_NAME_PREFIX + cookieToken)) {
                prefsWriter.remove(COOKIE_NAME_PREFIX + cookieToken);
            }
        }
        prefsWriter.remove(url.j());
        prefsWriter.apply();
        return true;
    }

    public synchronized boolean removeAllCookie() {
        this.cookies.clear();
        SharedPreferences.Editor prefsWriter = this.cookiePrefs.edit();
        prefsWriter.clear();
        prefsWriter.apply();
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
