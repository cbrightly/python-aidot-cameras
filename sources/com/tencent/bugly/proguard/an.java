package com.tencent.bugly.proguard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* compiled from: BUGLY */
public final class an extends k {
    private static ArrayList<am> A = new ArrayList<>();
    private static Map<String, String> B;
    private static Map<String, String> C;
    private static Map<String, String> v;
    private static al w = new al();
    private static ak x = new ak();
    private static ArrayList<ak> y = new ArrayList<>();
    private static ArrayList<ak> z = new ArrayList<>();
    public String a = "";
    public long b = 0;
    public String c = "";
    public String d = "";
    public String e = "";
    public String f = "";
    public String g = "";
    public Map<String, String> h = null;
    public String i = "";
    public al j = null;
    public int k = 0;
    public String l = "";
    public String m = "";
    public ak n = null;
    public ArrayList<ak> o = null;
    public ArrayList<ak> p = null;
    public ArrayList<am> q = null;
    public Map<String, String> r = null;
    public Map<String, String> s = null;
    public String t = "";
    private boolean u = true;

    public final void a(j jVar) {
        jVar.a(this.a, 0);
        jVar.a(this.b, 1);
        jVar.a(this.c, 2);
        String str = this.d;
        if (str != null) {
            jVar.a(str, 3);
        }
        String str2 = this.e;
        if (str2 != null) {
            jVar.a(str2, 4);
        }
        String str3 = this.f;
        if (str3 != null) {
            jVar.a(str3, 5);
        }
        String str4 = this.g;
        if (str4 != null) {
            jVar.a(str4, 6);
        }
        Map<String, String> map = this.h;
        if (map != null) {
            jVar.a(map, 7);
        }
        String str5 = this.i;
        if (str5 != null) {
            jVar.a(str5, 8);
        }
        al alVar = this.j;
        if (alVar != null) {
            jVar.a((k) alVar, 9);
        }
        jVar.a(this.k, 10);
        String str6 = this.l;
        if (str6 != null) {
            jVar.a(str6, 11);
        }
        String str7 = this.m;
        if (str7 != null) {
            jVar.a(str7, 12);
        }
        ak akVar = this.n;
        if (akVar != null) {
            jVar.a((k) akVar, 13);
        }
        ArrayList<ak> arrayList = this.o;
        if (arrayList != null) {
            jVar.a(arrayList, 14);
        }
        ArrayList<ak> arrayList2 = this.p;
        if (arrayList2 != null) {
            jVar.a(arrayList2, 15);
        }
        ArrayList<am> arrayList3 = this.q;
        if (arrayList3 != null) {
            jVar.a(arrayList3, 16);
        }
        Map<String, String> map2 = this.r;
        if (map2 != null) {
            jVar.a(map2, 17);
        }
        Map<String, String> map3 = this.s;
        if (map3 != null) {
            jVar.a(map3, 18);
        }
        String str8 = this.t;
        if (str8 != null) {
            jVar.a(str8, 19);
        }
        jVar.a(this.u, 20);
    }

    static {
        HashMap hashMap = new HashMap();
        v = hashMap;
        hashMap.put("", "");
        y.add(new ak());
        z.add(new ak());
        A.add(new am());
        HashMap hashMap2 = new HashMap();
        B = hashMap2;
        hashMap2.put("", "");
        HashMap hashMap3 = new HashMap();
        C = hashMap3;
        hashMap3.put("", "");
    }

    public final void a(i iVar) {
        this.a = iVar.b(0, true);
        this.b = iVar.a(this.b, 1, true);
        this.c = iVar.b(2, true);
        this.d = iVar.b(3, false);
        this.e = iVar.b(4, false);
        this.f = iVar.b(5, false);
        this.g = iVar.b(6, false);
        this.h = (Map) iVar.a(v, 7, false);
        this.i = iVar.b(8, false);
        this.j = (al) iVar.a((k) w, 9, false);
        this.k = iVar.a(this.k, 10, false);
        this.l = iVar.b(11, false);
        this.m = iVar.b(12, false);
        this.n = (ak) iVar.a((k) x, 13, false);
        this.o = (ArrayList) iVar.a(y, 14, false);
        this.p = (ArrayList) iVar.a(z, 15, false);
        this.q = (ArrayList) iVar.a(A, 16, false);
        this.r = (Map) iVar.a(B, 17, false);
        this.s = (Map) iVar.a(C, 18, false);
        this.t = iVar.b(19, false);
        this.u = iVar.a(20, false);
    }
}
