package com.blankj.utilcode.util;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.RequiresApi;
import com.blankj.utilcode.util.f0;
import com.blankj.utilcode.util.y;
import com.google.gson.Gson;
import java.io.File;

/* compiled from: UtilsBridge */
public class h0 {
    static void y(Application app) {
        g0.c.m(app);
    }

    static void K(Application app) {
        g0.c.r(app);
    }

    static void F() {
        G(b.f());
    }

    static Activity v() {
        return g0.c.l();
    }

    static void addOnAppStatusChangedListener(f0.b listener) {
        g0.c.addOnAppStatusChangedListener(listener);
    }

    static void removeOnAppStatusChangedListener(f0.b listener) {
        g0.c.removeOnAppStatusChangedListener(listener);
    }

    static void a(Activity activity, f0.a callbacks) {
        g0.c.b(activity, callbacks);
    }

    static Application m() {
        return g0.c.k();
    }

    static boolean z(Activity activity) {
        return a.c(activity);
    }

    static String t(String pkg) {
        return a.a(pkg);
    }

    static Context w() {
        if (!d.p()) {
            return f0.a();
        }
        Activity topActivity = v();
        return topActivity == null ? f0.a() : topActivity;
    }

    static String l() {
        return d.n();
    }

    static int k() {
        return d.l();
    }

    static String c(byte[] bytes) {
        return f.c(bytes);
    }

    static byte[] x(byte[] data, String algorithm) {
        return h.a(data, algorithm);
    }

    static boolean L(String filePath, String content, boolean append) {
        return i.g(filePath, content, append);
    }

    static boolean A(File file) {
        return j.y(file);
    }

    static File o(String filePath) {
        return j.l(filePath);
    }

    static boolean e(File file) {
        return j.c(file);
    }

    static boolean d(File file) {
        return j.a(file);
    }

    static Gson q() {
        return k.g();
    }

    static boolean C(Intent intent) {
        return m.d(intent);
    }

    static Intent s(String pkgName) {
        return m.c(pkgName);
    }

    static Intent r(String pkgName, boolean isNewTask) {
        return m.b(pkgName, isNewTask);
    }

    static String j(String json) {
        return n.a(json);
    }

    static void i(Activity activity) {
        o.a(activity);
    }

    static void b(Activity activity) {
        p.a(activity);
    }

    @RequiresApi(api = 23)
    static boolean B() {
        return s.a();
    }

    static String n() {
        return u.a();
    }

    static boolean D() {
        return v.a();
    }

    static y.a h(String command, boolean isRooted) {
        return y.a(command, isRooted);
    }

    static int f(float dpValue) {
        return z.a(dpValue);
    }

    static int H(float pxValue) {
        return z.b(pxValue);
    }

    static w u() {
        return w.a("Utils");
    }

    static boolean E(String s) {
        return a0.b(s);
    }

    static boolean g(CharSequence s1, CharSequence s2) {
        return a0.a(s1, s2);
    }

    static void I(Runnable runnable) {
        b0.l(runnable);
    }

    static void J(Runnable runnable, long delayMillis) {
        b0.m(runnable, delayMillis);
    }

    static String p(Throwable throwable) {
        return c0.a(throwable);
    }

    private static void G(Runnable... runs) {
        for (Runnable r : runs) {
            b0.h().execute(r);
        }
    }
}
