package androidx.core.widget;

/* compiled from: lambda */
public final /* synthetic */ class d implements Runnable {
    public final /* synthetic */ ContentLoadingProgressBar c;

    public /* synthetic */ d(ContentLoadingProgressBar contentLoadingProgressBar) {
        this.c = contentLoadingProgressBar;
    }

    public final void run() {
        this.c.showOnUiThread();
    }
}
