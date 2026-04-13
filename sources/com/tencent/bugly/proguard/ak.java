package com.tencent.bugly.proguard;

/* compiled from: BUGLY */
public final class ak extends k implements Cloneable {
    public String a = "";
    public String b = "";
    public String c = "";
    public String d = "";
    private String e = "";

    public final void a(j jVar) {
        jVar.a(this.a, 0);
        String str = this.b;
        if (str != null) {
            jVar.a(str, 1);
        }
        String str2 = this.c;
        if (str2 != null) {
            jVar.a(str2, 2);
        }
        String str3 = this.e;
        if (str3 != null) {
            jVar.a(str3, 3);
        }
        String str4 = this.d;
        if (str4 != null) {
            jVar.a(str4, 4);
        }
    }

    public final void a(i iVar) {
        this.a = iVar.b(0, true);
        this.b = iVar.b(1, false);
        this.c = iVar.b(2, false);
        this.e = iVar.b(3, false);
        this.d = iVar.b(4, false);
    }

    public final void a(StringBuilder sb, int i) {
    }
}
