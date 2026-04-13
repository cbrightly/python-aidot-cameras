package androidx.core.widget;

/* compiled from: lambda */
public final /* synthetic */ class b implements Runnable {
    public final /* synthetic */ ContentLoadingProgressBar c;

    public /* synthetic */ b(ContentLoadingProgressBar contentLoadingProgressBar) {
        this.c = contentLoadingProgressBar;
    }

    public final void run() {
        this.c.hideOnUiThread();
    }
}
