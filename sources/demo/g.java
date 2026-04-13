package demo;

/* compiled from: lambda */
public final /* synthetic */ class g implements Runnable {
    public final /* synthetic */ ProvisionActivity c;
    public final /* synthetic */ String d;

    public /* synthetic */ g(ProvisionActivity provisionActivity, String str) {
        this.c = provisionActivity;
        this.d = str;
    }

    public final void run() {
        ProvisionActivity.Y(this.c, this.d);
    }
}
