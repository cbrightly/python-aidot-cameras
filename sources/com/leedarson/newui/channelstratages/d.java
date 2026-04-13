package com.leedarson.newui.channelstratages;

import androidx.exifinterface.media.ExifInterface;
import com.leedarson.base.http.observer.j;
import com.leedarson.base.http.observer.l;
import com.leedarson.bean.IceServerBean;
import com.leedarson.bean.IpcDeviceBean;
import com.leedarson.bean.PropsBean;
import com.leedarson.log.f;
import com.leedarson.manager.a;
import com.leedarson.newui.cloud_play_back.repos.beans.LDSBaseBean;
import com.leedarson.newui.repos.n;
import com.leedarson.smartcamera.bean.KVSParamBean;
import com.leedarson.smartcamera.kvswebrtc.f0;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import io.reactivex.processors.b;

/* compiled from: LDSPreConnectStrategy */
public class d {
    public static ChangeQuickRedirect changeQuickRedirect;
    private String a = "";
    private String b = "";
    private String c = "";
    private String d = "";
    private IpcDeviceBean e = null;
    private f0 f = null;
    public b<IceServerBean> g = b.Y();
    public b<String> h = b.Y();
    public b<f0> i = b.Y();
    public b<?> j = b.Y();
    n k = new n();
    io.reactivex.disposables.b l;
    private boolean m = false;

    public d(IpcDeviceBean deviceBean, String userId, boolean isFromBackEnd) {
        PropsBean propsBean;
        if (deviceBean != null && (propsBean = deviceBean.props) != null) {
            this.a = deviceBean.id;
            this.c = propsBean.supportIpv6;
            this.e = deviceBean;
            this.b = userId;
            this.d = propsBean.liveType;
            this.m = isFromBackEnd;
            f.a("WebRTC LDSPreConnectStrategy 初始化预连接 deviceId=" + this.a);
            String str = deviceBean.props.liveType;
            if (str != null && ExifInterface.GPS_MEASUREMENT_2D.equals(str)) {
                a();
            }
        }
    }

    private void a() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3564, new Class[0], Void.TYPE).isSupported) {
            boolean isSupport = c();
            f.a("WebRTC createChannel isSupportPreCon: " + isSupport);
            if (isSupport) {
                f0 n = a.i().n(this.a, this.d, this.c, (KVSParamBean) null, f0.r.PRE_LINK);
                this.f = n;
                if (n != null) {
                    n.h3(this.e.getSpkNSLevel());
                    this.f.j3(this.e.props.getVideoCodesArr());
                    this.f.i3(isSupport);
                    this.f.c3(this.e.props.enableSdes);
                    this.f.l3(this.m);
                    try {
                        timber.log.a.a("WebRTC deviceBean.modelId:" + this.e.modelId, new Object[0]);
                        this.f.b3(false);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    a.i().a(this.a, this.f);
                    h();
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0023, code lost:
        r2 = r2.props;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean c() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Boolean.TYPE
            r4 = 0
            r5 = 3565(0xded, float:4.996E-42)
            r2 = r8
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r2 = r1.isSupported
            if (r2 == 0) goto L_0x001e
            java.lang.Object r0 = r1.result
            java.lang.Boolean r0 = (java.lang.Boolean) r0
            boolean r0 = r0.booleanValue()
            return r0
        L_0x001e:
            r1 = r8
            com.leedarson.bean.IpcDeviceBean r2 = r1.e
            if (r2 == 0) goto L_0x002e
            com.leedarson.bean.PropsBean r2 = r2.props
            if (r2 == 0) goto L_0x002e
            boolean r2 = r2.isSupportPreCon()
            if (r2 == 0) goto L_0x002e
            r0 = 1
        L_0x002e:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.channelstratages.d.c():boolean");
    }

    private boolean b() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3566, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        IpcDeviceBean ipcDeviceBean = this.e;
        return ipcDeviceBean != null && ipcDeviceBean.isLowPowerDevice();
    }

    public void h() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3567, new Class[0], Void.TYPE).isSupported) {
            if (b()) {
                io.reactivex.disposables.b bVar = this.l;
                if (bVar != null && !bVar.isDisposed()) {
                    this.l.dispose();
                }
                this.l = this.k.k(this.a, false).G(new j(2, 1500)).c(l.c()).I(new b(this), new a(this));
                this.f.y2();
                return;
            }
            f.a("WebRTC startConnect currentThreadId: 识别出来非低功耗设备" + Thread.currentThread().getId());
            this.f.y2();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: d */
    public /* synthetic */ void e(LDSBaseBean ldsBaseBean) {
        if (!PatchProxy.proxy(new Object[]{ldsBaseBean}, this, changeQuickRedirect, false, 3569, new Class[]{LDSBaseBean.class}, Void.TYPE).isSupported) {
            f.a("WebRTC 预连接唤醒接口调用成功--> " + ldsBaseBean.desc + "  deviceId=" + this.a);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: f */
    public /* synthetic */ void g(Throwable throwable) {
        if (!PatchProxy.proxy(new Object[]{throwable}, this, changeQuickRedirect, false, 3568, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
            f.a("WebRTC 预连接唤醒接口调用失败--> " + throwable.toString() + "  deviceId=" + this.a);
        }
    }
}
