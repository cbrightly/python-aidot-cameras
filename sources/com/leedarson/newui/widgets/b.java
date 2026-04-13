package com.leedarson.newui.widgets;

import android.view.View;
import com.leedarson.adapter.EventTagAdapter;

/* compiled from: lambda */
public final /* synthetic */ class b implements EventTagAdapter.a {
    public final /* synthetic */ LDSAiFeedbackBottomDialog a;

    public /* synthetic */ b(LDSAiFeedbackBottomDialog lDSAiFeedbackBottomDialog) {
        this.a = lDSAiFeedbackBottomDialog;
    }

    public final void onItemClick(View view, int i) {
        this.a.v(view, i);
    }
}
