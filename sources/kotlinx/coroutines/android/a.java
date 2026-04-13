package kotlinx.coroutines.android;

import kotlinx.coroutines.f1;

/* compiled from: lambda */
public final /* synthetic */ class a implements f1 {
    public final /* synthetic */ b c;
    public final /* synthetic */ Runnable d;

    public /* synthetic */ a(b bVar, Runnable runnable) {
        this.c = bVar;
        this.d = runnable;
    }

    public final void dispose() {
        b.a1(this.c, this.d);
    }
}
