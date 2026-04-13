package com.petterp.floatingx.impl.control;

import android.view.View;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.WindowInsetsCompat;

/* compiled from: lambda */
public final /* synthetic */ class a implements OnApplyWindowInsetsListener {
    public final /* synthetic */ b a;

    public /* synthetic */ a(b bVar) {
        this.a = bVar;
    }

    public final WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
        return b.E(this.a, view, windowInsetsCompat);
    }
}
