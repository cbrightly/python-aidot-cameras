package com.didichuxing.doraemonkit.okgo.cookie.store;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import okhttp3.Cookie;
import okhttp3.m;
import okhttp3.v;

public class MemoryCookieStore implements CookieStore {
    private final Map<String, List<m>> memoryCookies = new HashMap();

    public synchronized void saveCookie(v url, List<m> cookies) {
        List<Cookie> oldCookies = this.memoryCookies.get(url.j());
        List<Cookie> needRemove = new ArrayList<>();
        for (m newCookie : cookies) {
            Iterator<Cookie> it = oldCookies.iterator();
            while (it.hasNext()) {
                m oldCookie = (m) it.next();
                if (newCookie.i().equals(oldCookie.i())) {
                    needRemove.add(oldCookie);
                }
            }
        }
        oldCookies.removeAll(needRemove);
        oldCookies.addAll(cookies);
    }

    public synchronized void saveCookie(v url, m cookie) {
        List<Cookie> cookies = this.memoryCookies.get(url.j());
        List<Cookie> needRemove = new ArrayList<>();
        Iterator<Cookie> it = cookies.iterator();
        while (it.hasNext()) {
            m item = (m) it.next();
            if (cookie.i().equals(item.i())) {
                needRemove.add(item);
            }
        }
        cookies.removeAll(needRemove);
        cookies.add(cookie);
    }

    public synchronized List<m> loadCookie(v url) {
        List<Cookie> cookies;
        cookies = this.memoryCookies.get(url.j());
        if (cookies == null) {
            cookies = new ArrayList<>();
            this.memoryCookies.put(url.j(), cookies);
        }
        return cookies;
    }

    public synchronized List<m> getAllCookie() {
        List<Cookie> cookies;
        cookies = new ArrayList<>();
        for (String url : this.memoryCookies.keySet()) {
            cookies.addAll(this.memoryCookies.get(url));
        }
        return cookies;
    }

    public List<m> getCookie(v url) {
        List<Cookie> cookies = new ArrayList<>();
        List<Cookie> urlCookies = this.memoryCookies.get(url.j());
        if (urlCookies != null) {
            cookies.addAll(urlCookies);
        }
        return cookies;
    }

    public synchronized boolean removeCookie(v url, m cookie) {
        return cookie != null && this.memoryCookies.get(url.j()).remove(cookie);
    }

    public synchronized boolean removeCookie(v url) {
        return this.memoryCookies.remove(url.j()) != null;
    }

    public synchronized boolean removeAllCookie() {
        this.memoryCookies.clear();
        return true;
    }
}
