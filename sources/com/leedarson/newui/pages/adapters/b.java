package com.leedarson.newui.pages.adapters;

import android.view.View;
import com.leedarson.bean.SDRecord;
import com.leedarson.newui.pages.adapters.SDCardEventEditAdapter;

/* compiled from: lambda */
public final /* synthetic */ class b implements View.OnClickListener {
    public final /* synthetic */ SDCardEventEditAdapter.a c;
    public final /* synthetic */ int d;
    public final /* synthetic */ SDRecord f;

    public /* synthetic */ b(SDCardEventEditAdapter.a aVar, int i, SDRecord sDRecord) {
        this.c = aVar;
        this.d = i;
        this.f = sDRecord;
    }

    public final void onClick(View view) {
        SDEventEditViewHolder.b(this.c, this.d, this.f, view);
    }
}
