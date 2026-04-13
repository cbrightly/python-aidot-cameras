package com.leedarson.adapter;

import android.view.View;
import com.leedarson.adapter.EventListAdapter;

/* compiled from: lambda */
public final /* synthetic */ class e implements View.OnClickListener {
    public final /* synthetic */ EventListAdapter.ViewHolder c;
    public final /* synthetic */ EventListAdapter.c d;
    public final /* synthetic */ EventListAdapter.b f;

    public /* synthetic */ e(EventListAdapter.ViewHolder viewHolder, EventListAdapter.c cVar, EventListAdapter.b bVar) {
        this.c = viewHolder;
        this.d = cVar;
        this.f = bVar;
    }

    public final void onClick(View view) {
        this.c.d(this.d, this.f, view);
    }
}
