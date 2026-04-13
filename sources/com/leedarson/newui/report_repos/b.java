package com.leedarson.newui.report_repos;

import io.reactivex.functions.e;
import org.webrtc.PeerConnection;

/* compiled from: lambda */
public final /* synthetic */ class b implements e {
    public final /* synthetic */ k c;

    public /* synthetic */ b(k kVar) {
        this.c = kVar;
    }

    public final void accept(Object obj) {
        this.c.o((PeerConnection.IceConnectionState) obj);
    }
}
