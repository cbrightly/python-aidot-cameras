package com.leedarson.serviceimpl.map;

import android.content.Intent;
import android.view.View;

/* compiled from: lambda */
public final /* synthetic */ class a implements View.OnClickListener {
    public final /* synthetic */ GoogleMapActivity c;
    public final /* synthetic */ Intent d;

    public /* synthetic */ a(GoogleMapActivity googleMapActivity, Intent intent) {
        this.c = googleMapActivity;
        this.d = intent;
    }

    public final void onClick(View view) {
        this.c.u0(this.d, view);
    }
}
