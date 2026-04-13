package retrofit2;

import retrofit2.i;

/* compiled from: lambda */
public final /* synthetic */ class b implements Runnable {
    public final /* synthetic */ i.b.a c;
    public final /* synthetic */ f d;
    public final /* synthetic */ s f;

    public /* synthetic */ b(i.b.a aVar, f fVar, s sVar) {
        this.c = aVar;
        this.d = fVar;
        this.f = sVar;
    }

    public final void run() {
        this.c.f(this.d, this.f);
    }
}
