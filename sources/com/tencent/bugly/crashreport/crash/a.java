package com.tencent.bugly.crashreport.crash;

/* compiled from: BUGLY */
public final class a implements Comparable<a> {
    public long a = -1;
    public long b = -1;
    public String c = null;
    public boolean d = false;
    public boolean e = false;
    public int f = 0;

    public final /* bridge */ /* synthetic */ int compareTo(Object obj) {
        int i;
        a aVar = (a) obj;
        if (aVar == null || this.b - aVar.b > 0) {
            return 1;
        }
        return i < 0 ? -1 : 0;
    }
}
