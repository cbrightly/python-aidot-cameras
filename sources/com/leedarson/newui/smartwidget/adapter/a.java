package com.leedarson.newui.smartwidget.adapter;

import android.view.View;

/* compiled from: lambda */
public final /* synthetic */ class a implements View.OnClickListener {
    public final /* synthetic */ CamerasAdapter c;
    public final /* synthetic */ int d;

    public /* synthetic */ a(CamerasAdapter camerasAdapter, int i) {
        this.c = camerasAdapter;
        this.d = i;
    }

    public final void onClick(View view) {
        this.c.H(this.d, view);
    }
}
