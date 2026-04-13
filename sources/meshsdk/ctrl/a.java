package meshsdk.ctrl;

/* compiled from: lambda */
public final /* synthetic */ class a implements Runnable {
    public final /* synthetic */ GroupCtrl c;
    public final /* synthetic */ int d;
    public final /* synthetic */ int f;
    public final /* synthetic */ Object q;
    public final /* synthetic */ int x;

    public /* synthetic */ a(GroupCtrl groupCtrl, int i, int i2, Object obj, int i3) {
        this.c = groupCtrl;
        this.d = i;
        this.f = i2;
        this.q = obj;
        this.x = i3;
    }

    public final void run() {
        this.c.a(this.d, this.f, this.q, this.x);
    }
}
