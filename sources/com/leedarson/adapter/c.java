package com.leedarson.adapter;

import android.view.View;
import com.leedarson.adapter.EventList2Adapter;

/* compiled from: lambda */
public final /* synthetic */ class c implements View.OnClickListener {
    public final /* synthetic */ EventList2Adapter.ViewHolder c;
    public final /* synthetic */ EventList2Adapter.c d;

    public /* synthetic */ c(EventList2Adapter.ViewHolder viewHolder, EventList2Adapter.c cVar) {
        this.c = viewHolder;
        this.d = cVar;
    }

    public final void onClick(View view) {
        this.c.i(this.d, view);
    }
}
