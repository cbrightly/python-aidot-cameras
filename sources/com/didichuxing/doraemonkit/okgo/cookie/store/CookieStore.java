package com.didichuxing.doraemonkit.okgo.cookie.store;

import java.util.List;
import okhttp3.m;
import okhttp3.v;

public interface CookieStore {
    List<m> getAllCookie();

    List<m> getCookie(v vVar);

    List<m> loadCookie(v vVar);

    boolean removeAllCookie();

    boolean removeCookie(v vVar);

    boolean removeCookie(v vVar, m mVar);

    void saveCookie(v vVar, List<m> list);

    void saveCookie(v vVar, m mVar);
}
