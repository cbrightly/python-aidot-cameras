package androidx.camera.view;

import android.view.View;

/* compiled from: lambda */
public final /* synthetic */ class k implements View.OnLayoutChangeListener {
    public final /* synthetic */ PreviewView c;

    public /* synthetic */ k(PreviewView previewView) {
        this.c = previewView;
    }

    public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        this.c.a(view, i, i2, i3, i4, i5, i6, i7, i8);
    }
}
