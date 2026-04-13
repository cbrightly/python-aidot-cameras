package com.leedarson.newui;

import com.leedarson.newui.cloud_play_back.repos.beans.LDSBaseBean;
import io.reactivex.functions.e;

/* compiled from: lambda */
public final /* synthetic */ class e5 implements e {
    public final /* synthetic */ e6 c;
    public final /* synthetic */ boolean d;
    public final /* synthetic */ String f;

    public /* synthetic */ e5(e6 e6Var, boolean z, String str) {
        this.c = e6Var;
        this.d = z;
        this.f = str;
    }

    public final void accept(Object obj) {
        this.c.u0(this.d, this.f, (LDSBaseBean) obj);
    }
}
