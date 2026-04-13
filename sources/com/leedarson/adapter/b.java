package com.leedarson.adapter;

import android.view.View;
import com.leedarson.adapter.EventList2Adapter;

/* compiled from: lambda */
public final /* synthetic */ class b implements View.OnClickListener {
    public final /* synthetic */ EventList2Adapter.ViewHolder c;
    public final /* synthetic */ EventList2Adapter.c d;
    public final /* synthetic */ EventList2Adapter.b f;

    public /* synthetic */ b(EventList2Adapter.ViewHolder viewHolder, EventList2Adapter.c cVar, EventList2Adapter.b bVar) {
        this.c = viewHolder;
        this.d = cVar;
        this.f = bVar;
    }

    public final void onClick(View view) {
        this.c.e(this.d, this.f, view);
    }
}
