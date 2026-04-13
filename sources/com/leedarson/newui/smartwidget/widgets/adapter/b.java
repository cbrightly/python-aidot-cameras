package com.leedarson.newui.smartwidget.widgets.adapter;

import android.view.View;
import com.leedarson.newui.smartwidget.widgets.adapter.SecurityEventItemAdapter;

/* compiled from: lambda */
public final /* synthetic */ class b implements View.OnClickListener {
    public final /* synthetic */ SecurityEventItemAdapter.EventViewHolder c;

    public /* synthetic */ b(SecurityEventItemAdapter.EventViewHolder eventViewHolder) {
        this.c = eventViewHolder;
    }

    public final void onClick(View view) {
        this.c.f(view);
    }
}
