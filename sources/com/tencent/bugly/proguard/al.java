package com.tencent.bugly.proguard;

import java.util.ArrayList;

/* compiled from: BUGLY */
public final class al extends k implements Cloneable {
    private static ArrayList<String> c;
    private String a = "";
    private ArrayList<String> b = null;

    public final void a(j jVar) {
        jVar.a(this.a, 0);
        ArrayList<String> arrayList = this.b;
        if (arrayList != null) {
            jVar.a(arrayList, 1);
        }
    }

    public final void a(i iVar) {
        this.a = iVar.b(0, true);
        if (c == null) {
            ArrayList<String> arrayList = new ArrayList<>();
            c = arrayList;
            arrayList.add("");
        }
        this.b = (ArrayList) iVar.a(c, 1, false);
    }

    public final void a(StringBuilder sb, int i) {
    }
}
