package com.leedarson.newui.pages.adapters;

import android.view.View;
import com.leedarson.bean.SDRecord;
import com.leedarson.newui.pages.adapters.SDCardEventEditAdapter;

/* compiled from: lambda */
public final /* synthetic */ class a implements View.OnClickListener {
    public final /* synthetic */ SDCardEventEditAdapter.a c;
    public final /* synthetic */ int d;
    public final /* synthetic */ SDRecord f;

    public /* synthetic */ a(SDCardEventEditAdapter.a aVar, int i, SDRecord sDRecord) {
        this.c = aVar;
        this.d = i;
        this.f = sDRecord;
    }

    public final void onClick(View view) {
        SDEventEditGroupTitleViewHolder.b(this.c, this.d, this.f, view);
    }
}
