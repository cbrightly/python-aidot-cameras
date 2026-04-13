package com.leedarson.base.views;

import android.view.View;
import com.leedarson.base.views.d;

/* compiled from: lambda */
public final /* synthetic */ class b implements View.OnClickListener {
    public final /* synthetic */ d c;
    public final /* synthetic */ int d;
    public final /* synthetic */ d.b f;

    public /* synthetic */ b(d dVar, int i, d.b bVar) {
        this.c = dVar;
        this.d = i;
        this.f = bVar;
    }

    public final void onClick(View view) {
        this.c.i(this.d, this.f, view);
    }
}
