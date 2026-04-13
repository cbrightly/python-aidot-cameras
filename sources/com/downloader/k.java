package com.downloader;

/* compiled from: Response */
public class k {
    private a a;
    private boolean b;
    private boolean c;
    private boolean d;

    public a a() {
        return this.a;
    }

    public void f(a error) {
        this.a = error;
    }

    public boolean d() {
        return this.b;
    }

    public void h(boolean successful) {
        this.b = successful;
    }

    public boolean c() {
        return this.c;
    }

    public void g(boolean paused) {
        this.c = paused;
    }

    public boolean b() {
        return this.d;
    }

    public void e(boolean cancelled) {
        this.d = cancelled;
    }
}
