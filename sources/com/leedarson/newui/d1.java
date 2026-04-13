package com.leedarson.newui;

import android.view.View;

/* compiled from: lambda */
public final /* synthetic */ class d1 implements View.OnClickListener {
    public final /* synthetic */ CloudPlaybackFragment c;
    public final /* synthetic */ String d;
    public final /* synthetic */ String f;

    public /* synthetic */ d1(CloudPlaybackFragment cloudPlaybackFragment, String str, String str2) {
        this.c = cloudPlaybackFragment;
        this.d = str;
        this.f = str2;
    }

    public final void onClick(View view) {
        this.c.w5(this.d, this.f, view);
    }
}
