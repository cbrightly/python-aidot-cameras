package com.android.volley;

/* compiled from: DefaultRetryPolicy */
public class c implements m {
    private int a;
    private int b;
    private final int c;
    private final float d;

    public c() {
        this(2500, 1, 1.0f);
    }

    public c(int initialTimeoutMs, int maxNumRetries, float backoffMultiplier) {
        this.a = initialTimeoutMs;
        this.c = maxNumRetries;
        this.d = backoffMultiplier;
    }

    public int c() {
        return this.a;
    }

    public int a() {
        return this.b;
    }

    public void b(VolleyError error) {
        this.b++;
        int i = this.a;
        this.a = i + ((int) (((float) i) * this.d));
        if (!d()) {
            throw error;
        }
    }

    /* access modifiers changed from: protected */
    public boolean d() {
        return this.b <= this.c;
    }
}
