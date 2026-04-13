package com.leedarson.log.mgr;

import android.content.Context;
import io.reactivex.functions.e;
import java.io.File;

/* compiled from: lambda */
public final /* synthetic */ class h implements e {
    public final /* synthetic */ Context c;

    public /* synthetic */ h(Context context) {
        this.c = context;
    }

    public final void accept(Object obj) {
        q.M(this.c, (File) obj);
    }
}
