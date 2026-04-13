package com.leedarson.adapter;

import android.view.View;
import com.leedarson.adapter.EventListAdapter;

/* compiled from: lambda */
public final /* synthetic */ class d implements View.OnClickListener {
    public final /* synthetic */ EventListAdapter.ViewHolder c;
    public final /* synthetic */ EventListAdapter.c d;

    public /* synthetic */ d(EventListAdapter.ViewHolder viewHolder, EventListAdapter.c cVar) {
        this.c = viewHolder;
        this.d = cVar;
    }

    public final void onClick(View view) {
        this.c.f(this.d, view);
    }
}
