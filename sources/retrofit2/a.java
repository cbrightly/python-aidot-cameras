package retrofit2;

import retrofit2.i;

/* compiled from: lambda */
public final /* synthetic */ class a implements Runnable {
    public final /* synthetic */ i.b.a c;
    public final /* synthetic */ f d;
    public final /* synthetic */ Throwable f;

    public /* synthetic */ a(i.b.a aVar, f fVar, Throwable th) {
        this.c = aVar;
        this.d = fVar;
        this.f = th;
    }

    public final void run() {
        this.c.d(this.d, this.f);
    }
}
