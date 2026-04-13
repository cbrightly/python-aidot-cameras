package androidx.constraintlayout.motion.widget;

import android.view.View;

/* compiled from: lambda */
public final /* synthetic */ class a implements Runnable {
    public final /* synthetic */ ViewTransition c;
    public final /* synthetic */ View[] d;

    public /* synthetic */ a(ViewTransition viewTransition, View[] viewArr) {
        this.c = viewTransition;
        this.d = viewArr;
    }

    public final void run() {
        this.c.a(this.d);
    }
}
