package com.leedarson.newui.report_repos;

import com.leedarson.newui.repoter.h;
import com.leedarson.smartcamera.kvswebrtc.beans.WebRtcServiceStateChangeLogBean;
import com.leedarson.smartcamera.kvswebrtc.f0;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import io.reactivex.disposables.b;
import org.webrtc.PeerConnection;

/* compiled from: WebRtcLiveBzReportLog */
public class k {
    public static ChangeQuickRedirect changeQuickRedirect;
    io.reactivex.disposables.a a = new io.reactivex.disposables.a();
    String b = "";
    h c;
    f0 d;
    a e;

    /* compiled from: WebRtcLiveBzReportLog */
    public enum a {
        LIVE,
        SDCARD,
        SECURITY_CAMERA,
        PRELINK;
        
        public static ChangeQuickRedirect changeQuickRedirect;
    }

    public k(String deviceId, h reporter, f0 webRTCChannel, a channelBzType) {
        this.b = deviceId;
        this.c = reporter;
        this.d = webRTCChannel;
        this.e = channelBzType;
        a(webRTCChannel.D0.I(new h(this), g.c));
        a(webRTCChannel.E0.I(new e(this), f.c));
        a(webRTCChannel.F0.I(new c(this), i.c));
        a(webRTCChannel.G0.I(new a(this), j.c));
        a(webRTCChannel.H0.I(new b(this), d.c));
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public /* synthetic */ void c(WebRtcServiceStateChangeLogBean stateBean) {
        if (!PatchProxy.proxy(new Object[]{stateBean}, this, changeQuickRedirect, false, 4415, new Class[]{WebRtcServiceStateChangeLogBean.class}, Void.TYPE).isSupported) {
            this.c.m(this.b, stateBean);
        }
    }

    static /* synthetic */ void d(Throwable throwable) {
    }

    /* access modifiers changed from: private */
    /* renamed from: e */
    public /* synthetic */ void f(WebRtcServiceStateChangeLogBean stateBean) {
        if (!PatchProxy.proxy(new Object[]{stateBean}, this, changeQuickRedirect, false, 4414, new Class[]{WebRtcServiceStateChangeLogBean.class}, Void.TYPE).isSupported) {
            this.c.k(this.b, stateBean);
        }
    }

    static /* synthetic */ void g(Throwable throwable) {
    }

    /* access modifiers changed from: private */
    /* renamed from: h */
    public /* synthetic */ void i(WebRtcServiceStateChangeLogBean stateBean) {
        if (!PatchProxy.proxy(new Object[]{stateBean}, this, changeQuickRedirect, false, 4413, new Class[]{WebRtcServiceStateChangeLogBean.class}, Void.TYPE).isSupported) {
            this.c.l(this.b, stateBean);
        }
    }

    static /* synthetic */ void j(Throwable throwable) {
    }

    /* access modifiers changed from: private */
    /* renamed from: k */
    public /* synthetic */ void l(WebRtcServiceStateChangeLogBean stateBean) {
        if (!PatchProxy.proxy(new Object[]{stateBean}, this, changeQuickRedirect, false, 4412, new Class[]{WebRtcServiceStateChangeLogBean.class}, Void.TYPE).isSupported) {
            this.c.j(this.b, stateBean);
        }
    }

    static /* synthetic */ void m(Throwable throwable) {
    }

    /* access modifiers changed from: private */
    /* renamed from: n */
    public /* synthetic */ void o(PeerConnection.IceConnectionState iceConnectionState) {
        if (!PatchProxy.proxy(new Object[]{iceConnectionState}, this, changeQuickRedirect, false, 4411, new Class[]{PeerConnection.IceConnectionState.class}, Void.TYPE).isSupported) {
            if (iceConnectionState == PeerConnection.IceConnectionState.FAILED) {
                h hVar = this.c;
                String str = this.b;
                hVar.g(str, "WebRTC ICE通道状态变更 ===> PeerConnection.IceConnectionState: " + iceConnectionState);
            }
        }
    }

    static /* synthetic */ void p(Throwable throwable) {
    }

    public void q() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4409, new Class[0], Void.TYPE).isSupported) {
            if (!this.a.isDisposed()) {
                this.a.dispose();
            }
        }
    }

    private void a(b disposable) {
        if (!PatchProxy.proxy(new Object[]{disposable}, this, changeQuickRedirect, false, 4410, new Class[]{b.class}, Void.TYPE).isSupported) {
            this.a.b(disposable);
        }
    }
}
