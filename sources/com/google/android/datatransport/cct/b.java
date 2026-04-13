package com.google.android.datatransport.cct;

import com.google.android.datatransport.cct.CctTransportBackend;
import com.google.android.datatransport.runtime.retries.Function;

/* compiled from: lambda */
public final /* synthetic */ class b implements Function {
    public final /* synthetic */ CctTransportBackend a;

    public /* synthetic */ b(CctTransportBackend cctTransportBackend) {
        this.a = cctTransportBackend;
    }

    public final Object apply(Object obj) {
        return this.a.doSend((CctTransportBackend.HttpRequest) obj);
    }
}
