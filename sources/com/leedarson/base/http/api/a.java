package com.leedarson.base.http.api;

import io.reactivex.l;
import java.util.List;
import java.util.Map;
import okhttp3.c0;
import okhttp3.e0;
import okhttp3.y;
import retrofit2.http.b;
import retrofit2.http.f;
import retrofit2.http.j;
import retrofit2.http.k;
import retrofit2.http.n;
import retrofit2.http.o;
import retrofit2.http.p;
import retrofit2.http.q;
import retrofit2.http.r;
import retrofit2.http.u;
import retrofit2.http.w;
import retrofit2.http.y;

/* compiled from: UserApiStores */
public interface a {
    @f
    l<String> a(@y String str);

    @o
    @retrofit2.http.l
    l<String> b(@y String str, @j Map<String, Object> map, @r Map<String, Object> map2, @q List<y.c> list);

    @b
    l<String> c(@retrofit2.http.y String str, @j Map<String, Object> map, @u Map<String, Object> map2);

    @p
    l<String> d(@retrofit2.http.y String str, @j Map<String, Object> map, @retrofit2.http.a c0 c0Var);

    @w
    @p
    l<String> e(@retrofit2.http.y String str, @retrofit2.http.a c0 c0Var, @j Map<String, Object> map);

    @n
    l<String> f(@retrofit2.http.y String str, @j Map<String, Object> map, @retrofit2.http.a c0 c0Var);

    @f
    l<String> g(@retrofit2.http.y String str, @u Map<String, Object> map, @j Map<String, Object> map2);

    @w
    @k({"Cache-Control: max-age=0,no-cache"})
    @f
    l<e0> h(@retrofit2.http.y String str);

    @o
    @retrofit2.http.l
    l<String> i(@retrofit2.http.y String str, @r Map<String, c0> map, @j Map<String, Object> map2);

    @o
    l<String> j(@retrofit2.http.y String str, @retrofit2.http.a c0 c0Var, @j Map<String, Object> map);
}
