package com.leedarson.serviceimpl.business.utils;

import io.reactivex.f;
import io.reactivex.g;

/* compiled from: lambda */
public final /* synthetic */ class h implements g {
    public final /* synthetic */ PackageLocalLogFileUtil a;
    public final /* synthetic */ String b;

    public /* synthetic */ h(PackageLocalLogFileUtil packageLocalLogFileUtil, String str) {
        this.a = packageLocalLogFileUtil;
        this.b = str;
    }

    public final void subscribe(f fVar) {
        this.a.k(this.b, fVar);
    }
}
