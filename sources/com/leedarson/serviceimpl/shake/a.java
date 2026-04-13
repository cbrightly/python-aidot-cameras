package com.leedarson.serviceimpl.shake;

import android.widget.CompoundButton;

/* compiled from: lambda */
public final /* synthetic */ class a implements CompoundButton.OnCheckedChangeListener {
    public final /* synthetic */ ShakeActivity a;

    public /* synthetic */ a(ShakeActivity shakeActivity) {
        this.a = shakeActivity;
    }

    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        this.a.k(compoundButton, z);
    }
}
