package com.leedarson.serviceimpl.ctrl;

import android.content.Context;
import chip.devicecontroller.ChipDeviceController;
import chip.devicecontroller.DiscoveredDevice;
import chip.devicecontroller.NetworkCredentials;
import com.didichuxing.doraemonkit.kit.toolpanel.KitWrapItem;
import com.google.chip.chiptool.ChipClient;
import com.google.chip.chiptool.GenericChipDeviceListener;
import com.google.chip.chiptool.setuppayloadscanner.CHIPDeviceInfo;
import com.google.gson.Gson;
import com.leedarson.base.http.observer.l;
import com.leedarson.serviceimpl.bean.AddDeviceBean;
import com.leedarson.serviceimpl.bean.LeedarsonParams;
import com.leedarson.serviceimpl.i;
import com.leedarson.serviceimpl.j;
import com.leedarson.serviceimpl.listener.d;
import com.leedarson.serviceimpl.matter.R$string;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.tutk.IOTC.AVIOCTRLDEFs;
import io.reactivex.e;
import io.reactivex.f;
import java.util.concurrent.TimeUnit;
import kotlin.coroutines.g;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.x;
import kotlinx.coroutines.o0;
import kotlinx.coroutines.q0;
import kotlinx.coroutines.s1;
import kotlinx.coroutines.z1;
import meshsdk.BaseResp;
import net.sqlcipher.database.SQLiteDatabase;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ProvisionController.kt */
public final class p {
    @NotNull
    public static final a a = new a((DefaultConstructorMarker) null);
    /* access modifiers changed from: private */
    public static final int b = -1;
    /* access modifiers changed from: private */
    public static final int c = 1;
    public static ChangeQuickRedirect changeQuickRedirect;
    private static final int d = 2;
    /* access modifiers changed from: private */
    @NotNull
    public final String e = "ProvisionController";
    /* access modifiers changed from: private */
    public final int f;
    /* access modifiers changed from: private */
    public final int g = KitWrapItem.TYPE_VERSION;
    /* access modifiers changed from: private */
    @NotNull
    public Context h;
    public d i;
    private z1 j;
    /* access modifiers changed from: private */
    public boolean k;
    public j l;
    private long m;
    private final int n;
    @Nullable
    private io.reactivex.disposables.b o;

    public p(@NotNull Context context) {
        k.e(context, "context");
        this.h = context;
        this.n = 3;
    }

    public static final /* synthetic */ void e(p $this, AddDeviceBean addDeviceBean, CHIPDeviceInfo chipInfo, int retryMode) {
        Object[] objArr = {$this, addDeviceBean, chipInfo, new Integer(retryMode)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 7789, new Class[]{p.class, AddDeviceBean.class, CHIPDeviceInfo.class, Integer.TYPE}, Void.TYPE).isSupported) {
            $this.p(addDeviceBean, chipInfo, retryMode);
        }
    }

    public static final /* synthetic */ void l(p $this, String msg) {
        Class[] clsArr = {p.class, String.class};
        if (!PatchProxy.proxy(new Object[]{$this, msg}, (Object) null, changeQuickRedirect, true, 7790, clsArr, Void.TYPE).isSupported) {
            $this.G(msg);
        }
    }

    public static final /* synthetic */ void n(p $this, int msgResId, String stringArgs) {
        Object[] objArr = {$this, new Integer(msgResId), stringArgs};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 7788, new Class[]{p.class, Integer.TYPE, String.class}, Void.TYPE).isSupported) {
            $this.L(msgResId, stringArgs);
        }
    }

    @NotNull
    public final d A() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7762, new Class[0], d.class);
        if (proxy.isSupported) {
            return (d) proxy.result;
        }
        d dVar = this.i;
        if (dVar != null) {
            return dVar;
        }
        k.t("provisionCallback");
        throw null;
    }

    public final void K(@NotNull d dVar) {
        if (!PatchProxy.proxy(new Object[]{dVar}, this, changeQuickRedirect, false, 7763, new Class[]{d.class}, Void.TYPE).isSupported) {
            k.e(dVar, "<set-?>");
            this.i = dVar;
        }
    }

    public final void J(@NotNull j jVar) {
        if (!PatchProxy.proxy(new Object[]{jVar}, this, changeQuickRedirect, false, 7765, new Class[]{j.class}, Void.TYPE).isSupported) {
            k.e(jVar, "<set-?>");
            this.l = jVar;
        }
    }

    @NotNull
    public final j z() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7764, new Class[0], j.class);
        if (proxy.isSupported) {
            return (j) proxy.result;
        }
        j jVar = this.l;
        if (jVar != null) {
            return jVar;
        }
        k.t("ldsMatterGatt");
        throw null;
    }

    public final void I(long j2) {
        this.m = j2;
    }

    public final long v() {
        return this.m;
    }

    /* compiled from: ProvisionController.kt */
    public static final class a {
        public static ChangeQuickRedirect changeQuickRedirect;

        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private a() {
        }

        public final int b() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7791, new Class[0], Integer.TYPE);
            if (proxy.isSupported) {
                return ((Integer) proxy.result).intValue();
            }
            return p.b;
        }

        public final int a() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7792, new Class[0], Integer.TYPE);
            if (proxy.isSupported) {
                return ((Integer) proxy.result).intValue();
            }
            return p.c;
        }
    }

    public final void o(@NotNull AddDeviceBean addDeviceBean, @NotNull CHIPDeviceInfo chipInfo) {
        Class[] clsArr = {AddDeviceBean.class, CHIPDeviceInfo.class};
        if (!PatchProxy.proxy(new Object[]{addDeviceBean, chipInfo}, this, changeQuickRedirect, false, 7767, clsArr, Void.TYPE).isSupported) {
            k.e(addDeviceBean, "addDeviceBean");
            k.e(chipInfo, "chipInfo");
            H(chipInfo, addDeviceBean.getMatterAddr());
        }
    }

    @Nullable
    public final io.reactivex.disposables.b w() {
        return this.o;
    }

    public final void t(@NotNull AddDeviceBean addDeviceBean, @NotNull CHIPDeviceInfo chipInfo, int retryMode) {
        Object[] objArr = {addDeviceBean, chipInfo, new Integer(retryMode)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7769, new Class[]{AddDeviceBean.class, CHIPDeviceInfo.class, Integer.TYPE}, Void.TYPE).isSupported) {
            k.e(addDeviceBean, "addDeviceBean");
            k.e(chipInfo, "chipInfo");
            if (retryMode == b) {
                s(addDeviceBean, chipInfo, retryMode);
            } else if (retryMode == c) {
                p(addDeviceBean, chipInfo, retryMode);
            }
        }
    }

    private final void s(AddDeviceBean addDeviceBean, CHIPDeviceInfo cHIPDeviceInfo, int i2) {
        Object[] objArr = {addDeviceBean, cHIPDeviceInfo, new Integer(i2)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7770, new Class[]{AddDeviceBean.class, CHIPDeviceInfo.class, Integer.TYPE}, Void.TYPE).isSupported) {
            CHIPDeviceInfo chipInfo = cHIPDeviceInfo;
            AddDeviceBean addDeviceBean2 = addDeviceBean;
            int i3 = i2;
            com.leedarson.serviceimpl.k.a.n("SUFUN=> add device wifi with token");
            A().e(200, "准备蓝牙连接设备", "蓝牙连接设备ing");
            NetworkCredentials networkCredentials = NetworkCredentials.forWiFi(new NetworkCredentials.WiFiCredentials(addDeviceBean2.getSsid().toString(), addDeviceBean2.getPassword().toString()));
            k.d(networkCredentials, "networkCredentials");
            N(chipInfo, networkCredentials, addDeviceBean2.getMatterAddr(), addDeviceBean2);
        }
    }

    private final void p(AddDeviceBean addDeviceBean, CHIPDeviceInfo chipInfo, int i2) {
        io.reactivex.disposables.b w;
        Object[] objArr = {addDeviceBean, chipInfo, new Integer(i2)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7771, new Class[]{AddDeviceBean.class, CHIPDeviceInfo.class, Integer.TYPE}, Void.TYPE).isSupported) {
            io.reactivex.disposables.b bVar = this.o;
            if (!(bVar == null || bVar.isDisposed() || (w = w()) == null)) {
                w.dispose();
            }
            A().e(200, "开始发现设备", "发现局域网设备..");
            com.leedarson.serviceimpl.k.a.n("SUFUN=> discover devices in wifi ...");
            this.o = x().i().I(new d(chipInfo, this, addDeviceBean), new f(this));
        }
    }

    /* access modifiers changed from: private */
    public static final void q(CHIPDeviceInfo cHIPDeviceInfo, p pVar, AddDeviceBean addDeviceBean, DiscoveredDevice discoveredDevice) {
        if (!PatchProxy.proxy(new Object[]{cHIPDeviceInfo, pVar, addDeviceBean, discoveredDevice}, (Object) null, changeQuickRedirect, true, 7783, new Class[]{CHIPDeviceInfo.class, p.class, AddDeviceBean.class, DiscoveredDevice.class}, Void.TYPE).isSupported) {
            CHIPDeviceInfo $chipInfo = cHIPDeviceInfo;
            AddDeviceBean $addDeviceBean = addDeviceBean;
            p this$0 = pVar;
            DiscoveredDevice it = discoveredDevice;
            k.e($chipInfo, "$chipInfo");
            k.e(this$0, "this$0");
            k.e($addDeviceBean, "$addDeviceBean");
            CHIPDeviceInfo cHIPDeviceInfo2 = new CHIPDeviceInfo($chipInfo.getVersion(), $chipInfo.getVendorId(), $chipInfo.getProductId(), (int) it.discriminator, $chipInfo.getSetupPinCode(), $chipInfo.getCommissioningFlow(), $chipInfo.getOptionalQrCodeInfoMap(), $chipInfo.getDiscoveryCapabilities(), it.ipAddress, 0, (LeedarsonParams) null, AVIOCTRLDEFs.IOTYPE_CRUISEMODE_CRUISE_START, (DefaultConstructorMarker) null);
            this$0.A().e(200, k.l("发现到设备", new Gson().toJson((Object) cHIPDeviceInfo2)), "DNSSD发现设备");
            this$0.o($addDeviceBean, cHIPDeviceInfo2);
        }
    }

    /* access modifiers changed from: private */
    public static final void r(p this$0, Throwable it) {
        if (!PatchProxy.proxy(new Object[]{this$0, it}, (Object) null, changeQuickRedirect, true, 7784, new Class[]{p.class, Throwable.class}, Void.TYPE).isSupported) {
            k.e(this$0, "this$0");
            j.a = false;
            this$0.A().e(BaseResp.ERR_MSG_SEND_FAIL, k.l("未发现设备:exception=", it), "未扫描到设备");
            it.printStackTrace();
            com.leedarson.serviceimpl.k.a.n("SUFUN=> not find devices, try add by ble");
        }
    }

    private final e<DiscoveredDevice> x() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7772, new Class[0], e.class);
        if (proxy.isSupported) {
            return (e) proxy.result;
        }
        e<R> c2 = c().g(2, TimeUnit.SECONDS).o(new e(this)).c(l.c());
        k.d(c2, "_getCommisionableNodes()\n                .delay(2, TimeUnit.SECONDS)\n                .flatMap { _actualGetDiscoveredDivice() }\n                .compose(LDSPublishCompose.flowableToMain())");
        return c2;
    }

    /* access modifiers changed from: private */
    public static final org.reactivestreams.a y(p this$0, Integer it) {
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{this$0, it}, (Object) null, changeQuickRedirect2, true, 7785, new Class[]{p.class, Integer.class}, org.reactivestreams.a.class);
        if (proxy.isSupported) {
            return (org.reactivestreams.a) proxy.result;
        }
        k.e(this$0, "this$0");
        k.e(it, "it");
        return this$0.a();
    }

    /* access modifiers changed from: private */
    public static final void d(f it) {
        if (!PatchProxy.proxy(new Object[]{it}, (Object) null, changeQuickRedirect, true, 7786, new Class[]{f.class}, Void.TYPE).isSupported) {
            k.e(it, "it");
            i.a.l().discoverCommissionableNodes();
            it.onNext(1);
            it.onComplete();
        }
    }

    @NotNull
    public final e<Integer> c() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7773, new Class[0], e.class);
        if (proxy.isSupported) {
            return (e) proxy.result;
        }
        e<Integer> M = e.d(h.a, io.reactivex.a.DROP).M(io.reactivex.schedulers.a.c());
        k.d(M, "create<Int?>({\n            LDSMatter.getDeviceController().discoverCommissionableNodes()\n            it.onNext(1)\n            it.onComplete()\n        }, BackpressureStrategy.DROP).subscribeOn(Schedulers.io())");
        return M;
    }

    @NotNull
    public final e<DiscoveredDevice> a() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7774, new Class[0], e.class);
        if (proxy.isSupported) {
            return (e) proxy.result;
        }
        com.leedarson.serviceimpl.k.a.n("_actualGetDiscoveredDivice ...");
        e<DiscoveredDevice> d2 = e.d(g.a, io.reactivex.a.DROP);
        k.d(d2, "create({\n            var _discoveredCount = 0;\n            var _startQueryTime = System.currentTimeMillis();\n            var _hasFindDevice = false\n            //两个条件决定是否停止搜索（是否已超过50s，已经找到）\n            while ((_startQueryTime + 50 * 1000) >= System.currentTimeMillis() && !_hasFindDevice) {\n                Thread.sleep(1000)\n                _discoveredCount = 0\n                for (i in 0..10) {\n                    val data = LDSMatter.getDeviceController().getDiscoveredDevice(i);\n                    data?.let { _data ->\n                        it.onNext(_data)\n                        MatterLog.i(\"SUFUN=>_actualGetDiscoveredDivice  已找到设备  ${_discoveredCount}\")\n                        _discoveredCount += 1\n                        //return@create\n                        it.onComplete()\n                    }\n                }\n\n                if (_discoveredCount != 0) {\n                    _hasFindDevice = true;\n                    MatterLog.i(\"SUFUN=>_actualGetDiscoveredDivice  已发现设备Ok,发现到了设备  ${_discoveredCount}\")\n                    break\n                } else {\n                    MatterLog.i(\"SUFUN=>_actualGetDiscoveredDivice  未发现设备，进行重试  ${_discoveredCount}  剩余时间：${(_startQueryTime + 50 * 1000 - System.currentTimeMillis())}\")\n                }\n            }\n            if (!_hasFindDevice) {\n                it.onError(Exception(\"未发现设备\"))\n            }\n        }, BackpressureStrategy.DROP)");
        return d2;
    }

    /* access modifiers changed from: private */
    public static final void b(f fVar) {
        boolean _hasFindDevice;
        int i2 = 1;
        int i3 = 0;
        if (!PatchProxy.proxy(new Object[]{fVar}, (Object) null, changeQuickRedirect, true, 7787, new Class[]{f.class}, Void.TYPE).isSupported) {
            f it = fVar;
            k.e(it, "it");
            long _startQueryTime = System.currentTimeMillis();
            boolean _hasFindDevice2 = false;
            while (true) {
                long j2 = (long) SQLiteDatabase.SQLITE_MAX_LIKE_PATTERN_LENGTH;
                if (_startQueryTime + j2 < System.currentTimeMillis()) {
                    break;
                }
                Thread.sleep(1000);
                int _discoveredCount = 0;
                int i4 = i3;
                while (true) {
                    int i5 = i4;
                    i4 += i2;
                    DiscoveredDevice data = i.a.l().getDiscoveredDevice(i5);
                    if (data == null) {
                        _hasFindDevice = _hasFindDevice2;
                    } else {
                        it.onNext(data);
                        _hasFindDevice = _hasFindDevice2;
                        com.leedarson.serviceimpl.k.h(com.leedarson.serviceimpl.k.a, k.l("SUFUN=>_actualGetDiscoveredDivice  已找到设备  ", Integer.valueOf(_discoveredCount)), (String) null, 2, (Object) null);
                        _discoveredCount++;
                        it.onComplete();
                    }
                    if (i4 > 10) {
                        break;
                    }
                    i2 = 1;
                    _hasFindDevice2 = _hasFindDevice;
                }
                if (_discoveredCount != 0) {
                    _hasFindDevice2 = true;
                    com.leedarson.serviceimpl.k.h(com.leedarson.serviceimpl.k.a, k.l("SUFUN=>_actualGetDiscoveredDivice  已发现设备Ok,发现到了设备  ", Integer.valueOf(_discoveredCount)), (String) null, 2, (Object) null);
                    break;
                }
                com.leedarson.serviceimpl.k kVar = com.leedarson.serviceimpl.k.a;
                com.leedarson.serviceimpl.k.h(kVar, "SUFUN=>_actualGetDiscoveredDivice  未发现设备，进行重试  " + _discoveredCount + "  剩余时间：" + ((j2 + _startQueryTime) - System.currentTimeMillis()), (String) null, 2, (Object) null);
                i2 = 1;
                i3 = 0;
                _hasFindDevice2 = _hasFindDevice;
            }
            if (!_hasFindDevice2) {
                it.onError(new Exception("未发现设备"));
            }
        }
    }

    public final void H(@NotNull CHIPDeviceInfo cHIPDeviceInfo, long addr) {
        if (!PatchProxy.proxy(new Object[]{cHIPDeviceInfo, new Long(addr)}, this, changeQuickRedirect, false, 7775, new Class[]{CHIPDeviceInfo.class, Long.TYPE}, Void.TYPE).isSupported) {
            CHIPDeviceInfo deviceInfo = cHIPDeviceInfo;
            k.e(deviceInfo, "deviceInfo");
            long id = addr;
            ChipDeviceController deviceController = ChipClient.INSTANCE.getDeviceController(this.h);
            this.m = id;
            deviceController.setCompletionListener(new b((String) null, 1, (DefaultConstructorMarker) null));
            deviceController.pairDeviceWithAddress(id, deviceInfo.getIpAddress(), 5540, deviceInfo.getDiscriminator(), deviceInfo.getSetupPinCode(), (byte[]) null);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "com.leedarson.serviceimpl.ctrl.ProvisionController$startConnectingToDevice$1", f = "ProvisionController.kt", l = {246, 275, 292, 297}, m = "invokeSuspend")
    /* compiled from: ProvisionController.kt */
    public static final class c extends kotlin.coroutines.jvm.internal.l implements kotlin.jvm.functions.p<o0, kotlin.coroutines.d<? super x>, Object> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ AddDeviceBean $addDeviceBean;
        final /* synthetic */ CHIPDeviceInfo $deviceInfo;
        final /* synthetic */ NetworkCredentials $networkCredentials;
        final /* synthetic */ long $nodeId;
        private /* synthetic */ Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;
        final /* synthetic */ p this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(p pVar, NetworkCredentials networkCredentials, CHIPDeviceInfo cHIPDeviceInfo, AddDeviceBean addDeviceBean, long j, kotlin.coroutines.d<? super c> dVar) {
            super(2, dVar);
            this.this$0 = pVar;
            this.$networkCredentials = networkCredentials;
            this.$deviceInfo = cHIPDeviceInfo;
            this.$addDeviceBean = addDeviceBean;
            this.$nodeId = j;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, dVar}, this, changeQuickRedirect2, false, 7803, new Class[]{Object.class, kotlin.coroutines.d.class}, kotlin.coroutines.d.class);
            if (proxy.isSupported) {
                return (kotlin.coroutines.d) proxy.result;
            }
            c cVar = new c(this.this$0, this.$networkCredentials, this.$deviceInfo, this.$addDeviceBean, this.$nodeId, dVar);
            cVar.L$0 = obj;
            return cVar;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            Class<Object> cls = Object.class;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, obj2}, this, changeQuickRedirect, false, 7805, new Class[]{cls, cls}, Object.class);
            return proxy.isSupported ? proxy.result : invoke((o0) obj, (kotlin.coroutines.d<? super x>) (kotlin.coroutines.d) obj2);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable kotlin.coroutines.d<? super x> dVar) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{o0Var, dVar}, this, changeQuickRedirect, false, 7804, new Class[]{o0.class, kotlin.coroutines.d.class}, Object.class);
            return proxy.isSupported ? proxy.result : ((c) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v15, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v22, resolved type: com.google.chip.chiptool.bluetooth.BluetoothManager} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v16, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v13, resolved type: chip.devicecontroller.ChipDeviceController} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v14, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v25, resolved type: java.lang.String} */
        /* JADX WARNING: Code restructure failed: missing block: B:16:0x0144, code lost:
            r10 = (com.telink.ble.mesh.entity.AdvertisingDevice) r10;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:17:0x0146, code lost:
            if (r10 != null) goto L_0x0162;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x0148, code lost:
            r0 = r3.this$0;
            com.leedarson.serviceimpl.ctrl.p.M(r0, com.leedarson.serviceimpl.matter.R$string.rendezvous_over_ble_scanning_failed_text, (java.lang.String) null, 2, (java.lang.Object) null);
            r0.A().c(com.leedarson.serviceimpl.ctrl.i.a.b(), "device not found,search timeout", (java.lang.Exception) null);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x0161, code lost:
            return kotlin.x.a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:0x0162, code lost:
            r13 = com.google.chip.chiptool.setuppayloadscanner.CHIPDeviceInfo.Companion;
            r14 = r10.f;
            kotlin.jvm.internal.k.d(r14, "advertisingDevice.scanRecord");
            r13 = r13.fromBleFullScanRecord(r14);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x016f, code lost:
            if (r13 != null) goto L_0x0190;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x0171, code lost:
            r3.this$0.A().e(200, "蓝牙添加失败(蓝牙广播包解析失败)-->尝试admin模式添加", "尝试Admin模式添加");
            com.leedarson.serviceimpl.ctrl.p.e(r3.this$0, r3.$addDeviceBean, r3.$deviceInfo, com.leedarson.serviceimpl.ctrl.p.a.a());
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x018f, code lost:
            return kotlin.x.a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:24:0x0190, code lost:
            r14 = r13.getLeedarsonParams();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:0x0194, code lost:
            if (r14 != null) goto L_0x0198;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:0x0196, code lost:
            r14 = null;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:0x0198, code lost:
            r14 = r14.wifiMac;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:28:0x019a, code lost:
            r3.$addDeviceBean.setMac(r14);
            com.leedarson.serviceimpl.k.a.g("搜到设备，发起连接,ble(" + r10.c.getAddress() + ",rssi:" + r10.d + ") 解析信息:" + r13.getFormat() + "\n scanRecord:" + com.leedarson.base.utils.e.a(r10.f), "BluetoothManager");
            r0 = r3.this$0;
            r5 = com.leedarson.serviceimpl.matter.R$string.rendezvous_over_ble_connecting_text;
            r15 = r10.c.getName();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:29:0x01eb, code lost:
            if (r15 != null) goto L_0x01f7;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:30:0x01ed, code lost:
            r15 = r10.c.getAddress().toString();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:31:0x01f7, code lost:
            com.leedarson.serviceimpl.ctrl.p.n(r0, r5, r15);
            r3.this$0.A().e(200, "发起连接蓝牙ble(" + r10.c.getAddress() + ",rssi:" + r10.d + ") 解析信息:" + r13.getFormat() + "\n scanRecord:" + com.leedarson.base.utils.e.a(r10.f), "蓝牙连接ing...");
            r0 = com.leedarson.serviceimpl.ctrl.p.f(r3.this$0);
            r4 = r10.c;
            kotlin.jvm.internal.k.d(r4, "advertisingDevice.device");
            r3.L$0 = r12;
            r3.L$1 = r7;
            r3.L$2 = r10;
            r3.L$3 = r14;
            r3.label = 2;
            r9 = r7.connect(r0, r4, r3);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:32:0x0259, code lost:
            if (r9 != r1) goto L_0x025c;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:33:0x025b, code lost:
            return r1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:34:0x025c, code lost:
            r0 = r3;
            r3 = r11;
            r8 = r14;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:35:0x025f, code lost:
            r4 = (android.bluetooth.BluetoothGatt) r9;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:36:0x0262, code lost:
            if (r4 != null) goto L_0x0285;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:37:0x0264, code lost:
            r0.this$0.A().e(200, "蓝牙连接失败（五次连接都失败）", "开始Admin模式添加");
            com.leedarson.serviceimpl.ctrl.p.e(r0.this$0, r0.$addDeviceBean, r0.$deviceInfo, com.leedarson.serviceimpl.ctrl.p.a.a());
         */
        /* JADX WARNING: Code restructure failed: missing block: B:38:0x0284, code lost:
            return kotlin.x.a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:39:0x0285, code lost:
            r0.this$0.J(new com.leedarson.serviceimpl.ctrl.j(com.leedarson.serviceimpl.ctrl.p.f(r0.this$0), r4, r10));
            r0.this$0.A().e(200, "蓝牙通道连接成功", "蓝牙连接成功");
         */
        /* JADX WARNING: Code restructure failed: missing block: B:40:0x02ae, code lost:
            if (r0.this$0.z().h() == false) goto L_0x034e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:41:0x02b0, code lost:
            r7.setLdsGattListener(r0.this$0.z());
            r5 = r0.$addDeviceBean.getPairingToken();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:42:0x02bf, code lost:
            if (r5 == null) goto L_0x02cb;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:44:0x02c5, code lost:
            if (r5.length() != 0) goto L_0x02c8;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:45:0x02c8, code lost:
            r16 = false;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:46:0x02cb, code lost:
            r16 = true;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:47:0x02cd, code lost:
            if (r16 == false) goto L_0x02df;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:48:0x02cf, code lost:
            com.leedarson.serviceimpl.k.e(com.leedarson.serviceimpl.k.a, "pairingToken 为空，不下发UTC数据", (java.lang.String) null, 2, (java.lang.Object) null);
            r9 = r12;
            r17 = r8;
            r8 = r7;
            r7 = r17;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:49:0x02df, code lost:
            r0.this$0.z().k(r0.$addDeviceBean);
            r0.L$0 = r12;
            r0.L$1 = r7;
            r0.L$2 = r8;
            r0.L$3 = r4;
            r0.label = 3;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:50:0x02fb, code lost:
            if (kotlinx.coroutines.z0.a(300, r0) != r1) goto L_0x02fe;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:51:0x02fd, code lost:
            return r1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:52:0x02fe, code lost:
            r9 = r7;
            r10 = r12;
            r7 = r4;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:53:0x0301, code lost:
            r0.this$0.A().e(200, "写入时区&&任务成功", "时区&&任务写入成功");
            r4 = r7;
            r7 = r8;
            r8 = r9;
            r9 = r10;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:54:0x0314, code lost:
            com.leedarson.serviceimpl.k.b(com.leedarson.serviceimpl.k.a, kotlin.jvm.internal.k.l("notify result :", kotlin.coroutines.jvm.internal.b.a(r0.this$0.z().e((com.clj.fastble.callback.e) null))), (java.lang.String) null, 2, (java.lang.Object) null);
            r0.L$0 = r9;
            r0.L$1 = r8;
            r0.L$2 = r7;
            r0.L$3 = r4;
            r0.label = 4;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:55:0x0340, code lost:
            if (kotlinx.coroutines.z0.a(200, r0) != r1) goto L_0x0343;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:56:0x0342, code lost:
            return r1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:57:0x0343, code lost:
            r1 = r3;
            r3 = r4;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:58:0x0345, code lost:
            r5 = r3;
            r4 = r9;
            r3 = r1;
            r17 = r8;
            r8 = r7;
            r7 = r17;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:59:0x034e, code lost:
            r5 = r4;
            r4 = r12;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:60:0x0350, code lost:
            com.leedarson.serviceimpl.ctrl.p.M(r0.this$0, com.leedarson.serviceimpl.matter.R$string.rendezvous_over_ble_pairing_text, (java.lang.String) null, 2, (java.lang.Object) null);
            r4.setCompletionListener(new com.leedarson.serviceimpl.ctrl.p.b(r0.this$0, r8));
            com.google.chip.chiptool.util.MatterSpUtil.INSTANCE.getHouseId(com.leedarson.serviceimpl.ctrl.p.f(r0.this$0));
            r0.this$0.I(r0.$nodeId);
            r1 = r7.getConnectionId();
            r6 = r0.this$0;
            r7 = com.leedarson.serviceimpl.matter.R$string.rendezvous_over_ble_pairing_dev_id;
            r8 = new java.lang.StringBuilder();
            r8.append(r0.this$0.v());
            r8.append(" ,hexAddr:");
            r9 = java.lang.Long.toString(r0.this$0.v(), kotlin.text.a.a(16));
            kotlin.jvm.internal.k.d(r9, "java.lang.Long.toString(this, checkRadix(radix))");
            r8.append(r9);
            r8.append(", connectId:");
            r8.append(r1);
            com.leedarson.serviceimpl.ctrl.p.n(r6, r7, r8.toString());
            r0.this$0.A().e(200, "启动matter配网", "启动matter配网");
            r4.pairDevice(r5, r1, r0.this$0.v(), r0.$deviceInfo.getSetupPinCode(), r0.$networkCredentials);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:61:0x03d8, code lost:
            return kotlin.x.a;
         */
        /* JADX WARNING: Multi-variable type inference failed */
        @org.jetbrains.annotations.Nullable
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r19) {
            /*
                r18 = this;
                r0 = 1
                java.lang.Object[] r1 = new java.lang.Object[r0]
                r8 = 0
                r1[r8] = r19
                com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
                java.lang.Class[] r6 = new java.lang.Class[r0]
                java.lang.Class<java.lang.Object> r2 = java.lang.Object.class
                r6[r8] = r2
                java.lang.Class<java.lang.Object> r7 = java.lang.Object.class
                r4 = 0
                r5 = 7802(0x1e7a, float:1.0933E-41)
                r2 = r18
                com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
                boolean r2 = r1.isSupported
                if (r2 == 0) goto L_0x0020
                java.lang.Object r0 = r1.result
                return r0
            L_0x0020:
                java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
                r2 = r18
                int r3 = r2.label
                r4 = 2
                r5 = 200(0xc8, float:2.8E-43)
                r6 = 0
                switch(r3) {
                    case 0: goto L_0x00a5;
                    case 1: goto L_0x0088;
                    case 2: goto L_0x0069;
                    case 3: goto L_0x0050;
                    case 4: goto L_0x0037;
                    default: goto L_0x002f;
                }
            L_0x002f:
                java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
                java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
                r0.<init>(r1)
                throw r0
            L_0x0037:
                r0 = r18
                r1 = r19
                java.lang.Object r3 = r0.L$3
                android.bluetooth.BluetoothGatt r3 = (android.bluetooth.BluetoothGatt) r3
                java.lang.Object r7 = r0.L$2
                java.lang.String r7 = (java.lang.String) r7
                java.lang.Object r8 = r0.L$1
                com.google.chip.chiptool.bluetooth.BluetoothManager r8 = (com.google.chip.chiptool.bluetooth.BluetoothManager) r8
                java.lang.Object r9 = r0.L$0
                chip.devicecontroller.ChipDeviceController r9 = (chip.devicecontroller.ChipDeviceController) r9
                kotlin.p.b(r1)
                goto L_0x0345
            L_0x0050:
                r0 = r18
                r3 = r19
                java.lang.Object r7 = r0.L$3
                android.bluetooth.BluetoothGatt r7 = (android.bluetooth.BluetoothGatt) r7
                java.lang.Object r8 = r0.L$2
                java.lang.String r8 = (java.lang.String) r8
                java.lang.Object r9 = r0.L$1
                com.google.chip.chiptool.bluetooth.BluetoothManager r9 = (com.google.chip.chiptool.bluetooth.BluetoothManager) r9
                java.lang.Object r10 = r0.L$0
                chip.devicecontroller.ChipDeviceController r10 = (chip.devicecontroller.ChipDeviceController) r10
                kotlin.p.b(r3)
                goto L_0x0301
            L_0x0069:
                r3 = r18
                r7 = r6
                r9 = r19
                java.lang.Object r10 = r3.L$3
                r7 = r10
                java.lang.String r7 = (java.lang.String) r7
                java.lang.Object r10 = r3.L$2
                com.telink.ble.mesh.entity.AdvertisingDevice r10 = (com.telink.ble.mesh.entity.AdvertisingDevice) r10
                java.lang.Object r11 = r3.L$1
                com.google.chip.chiptool.bluetooth.BluetoothManager r11 = (com.google.chip.chiptool.bluetooth.BluetoothManager) r11
                java.lang.Object r12 = r3.L$0
                chip.devicecontroller.ChipDeviceController r12 = (chip.devicecontroller.ChipDeviceController) r12
                kotlin.p.b(r9)
                r0 = r3
                r8 = r7
                r3 = r9
                r7 = r11
                goto L_0x025f
            L_0x0088:
                r3 = r18
                r7 = r6
                r9 = r6
                r10 = r19
                r11 = r6
                java.lang.Object r12 = r3.L$2
                r7 = r12
                com.google.chip.chiptool.bluetooth.BluetoothManager r7 = (com.google.chip.chiptool.bluetooth.BluetoothManager) r7
                java.lang.Object r12 = r3.L$1
                r11 = r12
                chip.devicecontroller.ChipDeviceController r11 = (chip.devicecontroller.ChipDeviceController) r11
                java.lang.Object r12 = r3.L$0
                r9 = r12
                kotlinx.coroutines.o0 r9 = (kotlinx.coroutines.o0) r9
                kotlin.p.b(r10)
                r12 = r11
                r11 = r10
                goto L_0x0144
            L_0x00a5:
                kotlin.p.b(r19)
                r3 = r18
                r10 = r19
                java.lang.Object r7 = r3.L$0
                r9 = r7
                kotlinx.coroutines.o0 r9 = (kotlinx.coroutines.o0) r9
                com.leedarson.serviceimpl.ctrl.p r7 = r3.this$0
                int r11 = com.leedarson.serviceimpl.matter.R$string.provision_wifi_credit
                java.lang.StringBuilder r12 = new java.lang.StringBuilder
                r12.<init>()
                java.lang.String r13 = "ssid:"
                r12.append(r13)
                chip.devicecontroller.NetworkCredentials r13 = r3.$networkCredentials
                chip.devicecontroller.NetworkCredentials$WiFiCredentials r13 = r13.getWiFiCredentials()
                java.lang.String r13 = r13.getSsid()
                r12.append(r13)
                java.lang.String r13 = ",pwd:"
                r12.append(r13)
                chip.devicecontroller.NetworkCredentials r13 = r3.$networkCredentials
                chip.devicecontroller.NetworkCredentials$WiFiCredentials r13 = r13.getWiFiCredentials()
                java.lang.String r13 = r13.getPassword()
                r12.append(r13)
                java.lang.String r12 = r12.toString()
                com.leedarson.serviceimpl.ctrl.p.n(r7, r11, r12)
                com.google.chip.chiptool.ChipClient r7 = com.google.chip.chiptool.ChipClient.INSTANCE
                com.leedarson.serviceimpl.ctrl.p r11 = r3.this$0
                android.content.Context r11 = r11.h
                chip.devicecontroller.ChipDeviceController r11 = r7.getDeviceController(r11)
                com.google.chip.chiptool.bluetooth.BluetoothManager r7 = new com.google.chip.chiptool.bluetooth.BluetoothManager
                r7.<init>()
                com.leedarson.serviceimpl.ctrl.p r12 = r3.this$0
                com.leedarson.serviceimpl.listener.d r12 = r12.A()
                com.google.chip.chiptool.setuppayloadscanner.CHIPDeviceInfo r13 = r3.$deviceInfo
                r12.f(r13)
                com.leedarson.serviceimpl.ctrl.p r12 = r3.this$0
                int r13 = com.leedarson.serviceimpl.matter.R$string.rendezvous_over_ble_scanning_text
                com.google.chip.chiptool.setuppayloadscanner.CHIPDeviceInfo r14 = r3.$deviceInfo
                int r14 = r14.getDiscriminator()
                java.lang.String r14 = java.lang.String.valueOf(r14)
                com.leedarson.serviceimpl.ctrl.p.n(r12, r13, r14)
                com.leedarson.serviceimpl.ctrl.p r12 = r3.this$0
                com.leedarson.serviceimpl.listener.d r12 = r12.A()
                java.lang.String r13 = "搜索蓝牙"
                r12.e(r5, r13, r13)
                com.leedarson.serviceimpl.ctrl.p r12 = r3.this$0
                android.content.Context r12 = r12.h
                com.google.chip.chiptool.setuppayloadscanner.CHIPDeviceInfo r13 = r3.$deviceInfo
                int r13 = r13.getDiscriminator()
                com.google.chip.chiptool.setuppayloadscanner.CHIPDeviceInfo r14 = r3.$deviceInfo
                int r14 = r14.getHasShortDiscriminator()
                r3.L$0 = r9
                r3.L$1 = r11
                r3.L$2 = r7
                r3.label = r0
                java.lang.Object r12 = r7.getBluetoothDevice(r12, r13, r14, r3)
                if (r12 != r1) goto L_0x013e
                return r1
            L_0x013e:
                r17 = r11
                r11 = r10
                r10 = r12
                r12 = r17
            L_0x0144:
                com.telink.ble.mesh.entity.AdvertisingDevice r10 = (com.telink.ble.mesh.entity.AdvertisingDevice) r10
                if (r10 != 0) goto L_0x0162
                com.leedarson.serviceimpl.ctrl.p r0 = r3.this$0
                r1 = 0
                int r5 = com.leedarson.serviceimpl.matter.R$string.rendezvous_over_ble_scanning_failed_text
                com.leedarson.serviceimpl.ctrl.p.M(r0, r5, r6, r4, r6)
                com.leedarson.serviceimpl.listener.d r0 = r0.A()
                com.leedarson.serviceimpl.ctrl.i$a r4 = com.leedarson.serviceimpl.ctrl.i.a
                int r4 = r4.b()
                java.lang.String r5 = "device not found,search timeout"
                r0.c(r4, r5, r6)
                kotlin.x r0 = kotlin.x.a
                return r0
            L_0x0162:
                com.google.chip.chiptool.setuppayloadscanner.CHIPDeviceInfo$Companion r13 = com.google.chip.chiptool.setuppayloadscanner.CHIPDeviceInfo.Companion
                byte[] r14 = r10.f
                java.lang.String r15 = "advertisingDevice.scanRecord"
                kotlin.jvm.internal.k.d(r14, r15)
                com.google.chip.chiptool.setuppayloadscanner.CHIPDeviceInfo r13 = r13.fromBleFullScanRecord(r14)
                if (r13 != 0) goto L_0x0190
                com.leedarson.serviceimpl.ctrl.p r0 = r3.this$0
                com.leedarson.serviceimpl.listener.d r0 = r0.A()
                java.lang.String r1 = "蓝牙添加失败(蓝牙广播包解析失败)-->尝试admin模式添加"
                java.lang.String r4 = "尝试Admin模式添加"
                r0.e(r5, r1, r4)
                com.leedarson.serviceimpl.ctrl.p r0 = r3.this$0
                com.leedarson.serviceimpl.bean.AddDeviceBean r1 = r3.$addDeviceBean
                com.google.chip.chiptool.setuppayloadscanner.CHIPDeviceInfo r4 = r3.$deviceInfo
                com.leedarson.serviceimpl.ctrl.p$a r5 = com.leedarson.serviceimpl.ctrl.p.a
                int r5 = r5.a()
                com.leedarson.serviceimpl.ctrl.p.e(r0, r1, r4, r5)
                kotlin.x r0 = kotlin.x.a
                return r0
            L_0x0190:
                com.leedarson.serviceimpl.bean.LeedarsonParams r14 = r13.getLeedarsonParams()
                if (r14 != 0) goto L_0x0198
                r14 = r6
                goto L_0x019a
            L_0x0198:
                java.lang.String r14 = r14.wifiMac
            L_0x019a:
                com.leedarson.serviceimpl.bean.AddDeviceBean r15 = r3.$addDeviceBean
                r15.setMac(r14)
                com.leedarson.serviceimpl.k r15 = com.leedarson.serviceimpl.k.a
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                r0.<init>()
                java.lang.String r8 = "搜到设备，发起连接,ble("
                r0.append(r8)
                android.bluetooth.BluetoothDevice r8 = r10.c
                java.lang.String r8 = r8.getAddress()
                r0.append(r8)
                java.lang.String r8 = ",rssi:"
                r0.append(r8)
                int r6 = r10.d
                r0.append(r6)
                java.lang.String r6 = ") 解析信息:"
                r0.append(r6)
                java.lang.String r4 = r13.getFormat()
                r0.append(r4)
                java.lang.String r4 = "\n scanRecord:"
                r0.append(r4)
                byte[] r5 = r10.f
                java.lang.String r5 = com.leedarson.base.utils.e.a(r5)
                r0.append(r5)
                java.lang.String r0 = r0.toString()
                java.lang.String r5 = "BluetoothManager"
                r15.g(r0, r5)
                com.leedarson.serviceimpl.ctrl.p r0 = r3.this$0
                int r5 = com.leedarson.serviceimpl.matter.R$string.rendezvous_over_ble_connecting_text
                android.bluetooth.BluetoothDevice r15 = r10.c
                java.lang.String r15 = r15.getName()
                if (r15 != 0) goto L_0x01f7
                android.bluetooth.BluetoothDevice r15 = r10.c
                java.lang.String r15 = r15.getAddress()
                java.lang.String r15 = r15.toString()
            L_0x01f7:
                com.leedarson.serviceimpl.ctrl.p.n(r0, r5, r15)
                com.leedarson.serviceimpl.ctrl.p r0 = r3.this$0
                com.leedarson.serviceimpl.listener.d r0 = r0.A()
                java.lang.StringBuilder r5 = new java.lang.StringBuilder
                r5.<init>()
                java.lang.String r15 = "发起连接蓝牙ble("
                r5.append(r15)
                android.bluetooth.BluetoothDevice r15 = r10.c
                java.lang.String r15 = r15.getAddress()
                r5.append(r15)
                r5.append(r8)
                int r8 = r10.d
                r5.append(r8)
                r5.append(r6)
                java.lang.String r6 = r13.getFormat()
                r5.append(r6)
                r5.append(r4)
                byte[] r4 = r10.f
                java.lang.String r4 = com.leedarson.base.utils.e.a(r4)
                r5.append(r4)
                java.lang.String r4 = r5.toString()
                java.lang.String r5 = "蓝牙连接ing..."
                r6 = 200(0xc8, float:2.8E-43)
                r0.e(r6, r4, r5)
                com.leedarson.serviceimpl.ctrl.p r0 = r3.this$0
                android.content.Context r0 = r0.h
                android.bluetooth.BluetoothDevice r4 = r10.c
                java.lang.String r5 = "advertisingDevice.device"
                kotlin.jvm.internal.k.d(r4, r5)
                r3.L$0 = r12
                r3.L$1 = r7
                r3.L$2 = r10
                r3.L$3 = r14
                r5 = 2
                r3.label = r5
                java.lang.Object r9 = r7.connect(r0, r4, r3)
                if (r9 != r1) goto L_0x025c
                return r1
            L_0x025c:
                r0 = r3
                r3 = r11
                r8 = r14
            L_0x025f:
                r4 = r9
                android.bluetooth.BluetoothGatt r4 = (android.bluetooth.BluetoothGatt) r4
                if (r4 != 0) goto L_0x0285
                com.leedarson.serviceimpl.ctrl.p r1 = r0.this$0
                com.leedarson.serviceimpl.listener.d r1 = r1.A()
                java.lang.String r5 = "蓝牙连接失败（五次连接都失败）"
                java.lang.String r6 = "开始Admin模式添加"
                r7 = 200(0xc8, float:2.8E-43)
                r1.e(r7, r5, r6)
                com.leedarson.serviceimpl.ctrl.p r1 = r0.this$0
                com.leedarson.serviceimpl.bean.AddDeviceBean r5 = r0.$addDeviceBean
                com.google.chip.chiptool.setuppayloadscanner.CHIPDeviceInfo r6 = r0.$deviceInfo
                com.leedarson.serviceimpl.ctrl.p$a r7 = com.leedarson.serviceimpl.ctrl.p.a
                int r7 = r7.a()
                com.leedarson.serviceimpl.ctrl.p.e(r1, r5, r6, r7)
                kotlin.x r1 = kotlin.x.a
                return r1
            L_0x0285:
                com.leedarson.serviceimpl.ctrl.p r5 = r0.this$0
                com.leedarson.serviceimpl.ctrl.j r6 = new com.leedarson.serviceimpl.ctrl.j
                com.leedarson.serviceimpl.ctrl.p r9 = r0.this$0
                android.content.Context r9 = r9.h
                r6.<init>(r9, r4, r10)
                r5.J(r6)
                com.leedarson.serviceimpl.ctrl.p r5 = r0.this$0
                com.leedarson.serviceimpl.listener.d r5 = r5.A()
                java.lang.String r6 = "蓝牙通道连接成功"
                java.lang.String r9 = "蓝牙连接成功"
                r10 = 200(0xc8, float:2.8E-43)
                r5.e(r10, r6, r9)
                com.leedarson.serviceimpl.ctrl.p r5 = r0.this$0
                com.leedarson.serviceimpl.ctrl.j r5 = r5.z()
                boolean r5 = r5.h()
                if (r5 == 0) goto L_0x034e
                com.leedarson.serviceimpl.ctrl.p r5 = r0.this$0
                com.leedarson.serviceimpl.ctrl.j r5 = r5.z()
                r7.setLdsGattListener(r5)
                com.leedarson.serviceimpl.bean.AddDeviceBean r5 = r0.$addDeviceBean
                java.lang.String r5 = r5.getPairingToken()
                if (r5 == 0) goto L_0x02cb
                int r5 = r5.length()
                if (r5 != 0) goto L_0x02c8
                goto L_0x02cb
            L_0x02c8:
                r16 = 0
                goto L_0x02cd
            L_0x02cb:
                r16 = 1
            L_0x02cd:
                if (r16 == 0) goto L_0x02df
                com.leedarson.serviceimpl.k r5 = com.leedarson.serviceimpl.k.a
                java.lang.String r6 = "pairingToken 为空，不下发UTC数据"
                r9 = 2
                r10 = 0
                com.leedarson.serviceimpl.k.e(r5, r6, r10, r9, r10)
                r9 = r12
                r17 = r8
                r8 = r7
                r7 = r17
                goto L_0x0314
            L_0x02df:
                com.leedarson.serviceimpl.ctrl.p r5 = r0.this$0
                com.leedarson.serviceimpl.ctrl.j r5 = r5.z()
                com.leedarson.serviceimpl.bean.AddDeviceBean r6 = r0.$addDeviceBean
                r5.k(r6)
                r5 = 300(0x12c, double:1.48E-321)
                r0.L$0 = r12
                r0.L$1 = r7
                r0.L$2 = r8
                r0.L$3 = r4
                r9 = 3
                r0.label = r9
                java.lang.Object r5 = kotlinx.coroutines.z0.a(r5, r0)
                if (r5 != r1) goto L_0x02fe
                return r1
            L_0x02fe:
                r9 = r7
                r10 = r12
                r7 = r4
            L_0x0301:
                com.leedarson.serviceimpl.ctrl.p r4 = r0.this$0
                com.leedarson.serviceimpl.listener.d r4 = r4.A()
                java.lang.String r5 = "写入时区&&任务成功"
                java.lang.String r6 = "时区&&任务写入成功"
                r11 = 200(0xc8, float:2.8E-43)
                r4.e(r11, r5, r6)
                r4 = r7
                r7 = r8
                r8 = r9
                r9 = r10
            L_0x0314:
                com.leedarson.serviceimpl.ctrl.p r5 = r0.this$0
                com.leedarson.serviceimpl.ctrl.j r5 = r5.z()
                r6 = 0
                boolean r5 = r5.e(r6)
                com.leedarson.serviceimpl.k r10 = com.leedarson.serviceimpl.k.a
                java.lang.Boolean r11 = kotlin.coroutines.jvm.internal.b.a(r5)
                java.lang.String r12 = "notify result :"
                java.lang.String r11 = kotlin.jvm.internal.k.l(r12, r11)
                r12 = 2
                com.leedarson.serviceimpl.k.b(r10, r11, r6, r12, r6)
                r10 = 200(0xc8, double:9.9E-322)
                r0.L$0 = r9
                r0.L$1 = r8
                r0.L$2 = r7
                r0.L$3 = r4
                r6 = 4
                r0.label = r6
                java.lang.Object r5 = kotlinx.coroutines.z0.a(r10, r0)
                if (r5 != r1) goto L_0x0343
                return r1
            L_0x0343:
                r1 = r3
                r3 = r4
            L_0x0345:
                r5 = r3
                r4 = r9
                r3 = r1
                r17 = r8
                r8 = r7
                r7 = r17
                goto L_0x0350
            L_0x034e:
                r5 = r4
                r4 = r12
            L_0x0350:
                com.leedarson.serviceimpl.ctrl.p r1 = r0.this$0
                int r6 = com.leedarson.serviceimpl.matter.R$string.rendezvous_over_ble_pairing_text
                r9 = 2
                r10 = 0
                com.leedarson.serviceimpl.ctrl.p.M(r1, r6, r10, r9, r10)
                com.leedarson.serviceimpl.ctrl.p$b r1 = new com.leedarson.serviceimpl.ctrl.p$b
                com.leedarson.serviceimpl.ctrl.p r6 = r0.this$0
                r1.<init>(r6, r8)
                r4.setCompletionListener(r1)
                com.google.chip.chiptool.util.MatterSpUtil r1 = com.google.chip.chiptool.util.MatterSpUtil.INSTANCE
                com.leedarson.serviceimpl.ctrl.p r6 = r0.this$0
                android.content.Context r6 = r6.h
                r1.getHouseId(r6)
                com.leedarson.serviceimpl.ctrl.p r1 = r0.this$0
                long r8 = r0.$nodeId
                r1.I(r8)
                int r1 = r7.getConnectionId()
                com.leedarson.serviceimpl.ctrl.p r6 = r0.this$0
                int r7 = com.leedarson.serviceimpl.matter.R$string.rendezvous_over_ble_pairing_dev_id
                java.lang.StringBuilder r8 = new java.lang.StringBuilder
                r8.<init>()
                com.leedarson.serviceimpl.ctrl.p r9 = r0.this$0
                long r9 = r9.v()
                r8.append(r9)
                java.lang.String r9 = " ,hexAddr:"
                r8.append(r9)
                com.leedarson.serviceimpl.ctrl.p r9 = r0.this$0
                long r9 = r9.v()
                r11 = 16
                int r11 = kotlin.text.a.a(r11)
                java.lang.String r9 = java.lang.Long.toString(r9, r11)
                java.lang.String r10 = "java.lang.Long.toString(this, checkRadix(radix))"
                kotlin.jvm.internal.k.d(r9, r10)
                r8.append(r9)
                java.lang.String r9 = ", connectId:"
                r8.append(r9)
                r8.append(r1)
                java.lang.String r8 = r8.toString()
                com.leedarson.serviceimpl.ctrl.p.n(r6, r7, r8)
                com.leedarson.serviceimpl.ctrl.p r6 = r0.this$0
                com.leedarson.serviceimpl.listener.d r6 = r6.A()
                java.lang.String r7 = "启动matter配网"
                r8 = 200(0xc8, float:2.8E-43)
                r6.e(r8, r7, r7)
                com.leedarson.serviceimpl.ctrl.p r6 = r0.this$0
                long r7 = r6.v()
                com.google.chip.chiptool.setuppayloadscanner.CHIPDeviceInfo r6 = r0.$deviceInfo
                long r9 = r6.getSetupPinCode()
                chip.devicecontroller.NetworkCredentials r11 = r0.$networkCredentials
                r6 = r1
                r4.pairDevice(r5, r6, r7, r9, r11)
                kotlin.x r4 = kotlin.x.a
                return r4
            */
            throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.ctrl.p.c.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    private final void N(CHIPDeviceInfo deviceInfo, NetworkCredentials networkCredentials, long nodeId, AddDeviceBean addDeviceBean) {
        Object[] objArr = {deviceInfo, networkCredentials, new Long(nodeId), addDeviceBean};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7776, new Class[]{CHIPDeviceInfo.class, NetworkCredentials.class, Long.TYPE, AddDeviceBean.class}, Void.TYPE).isSupported) {
            this.k = false;
            z1 d2 = kotlinx.coroutines.j.d(s1.c, (g) null, (q0) null, new c(this, networkCredentials, deviceInfo, addDeviceBean, nodeId, (kotlin.coroutines.d<? super c>) null), 3, (Object) null);
            this.j = d2;
            if (d2 != null) {
                d2.start();
            } else {
                k.t("job");
                throw null;
            }
        }
    }

    static /* synthetic */ void M(p pVar, int i2, String str, int i3, Object obj) {
        Object[] objArr = {pVar, new Integer(i2), str, new Integer(i3), obj};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 7779, new Class[]{p.class, cls, String.class, cls, Object.class}, Void.TYPE).isSupported) {
            if ((i3 & 2) != 0) {
                str = null;
            }
            pVar.L(i2, str);
        }
    }

    private final void L(int msgResId, String stringArgs) {
        if (!PatchProxy.proxy(new Object[]{new Integer(msgResId), stringArgs}, this, changeQuickRedirect, false, 7778, new Class[]{Integer.TYPE, String.class}, Void.TYPE).isSupported) {
            String msg = this.h.getString(msgResId, new Object[]{stringArgs});
            k.d(msg, "ctx.getString(msgResId, stringArgs)");
            com.leedarson.serviceimpl.k kVar = com.leedarson.serviceimpl.k.a;
            kVar.a(msg, this.e);
            kVar.n(msg);
            d A = A();
            if (A != null) {
                A.g(msg);
            }
        }
    }

    /* compiled from: ProvisionController.kt */
    public final class b extends GenericChipDeviceListener {
        public static ChangeQuickRedirect changeQuickRedirect;
        @Nullable
        private String a;

        public b(@Nullable p this$0, String wifiMac) {
            k.e(this$0, "this$0");
            p.this = this$0;
            this.a = wifiMac;
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        public /* synthetic */ b(String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(p.this, (i & 1) != 0 ? "" : str);
        }

        public void onConnectDeviceComplete() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7794, new Class[0], Void.TYPE).isSupported) {
                p.l(p.this, "onConnectDeviceComplete");
                p.this.A().e(200, "matter-onConnectDeviceComplete", "连接matter设备成功");
            }
        }

        public void onStatusUpdate(int status) {
            Object[] objArr = {new Integer(status)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7795, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                p.l(p.this, k.l("Pairing status update: ", Integer.valueOf(status)));
                p.this.A().e(status, "matter-onStatusUpdate", "matter状态变更");
                if (status == p.this.g) {
                    p.l(p.this, k.l("检测到不受信任的PAA证书: ", Integer.valueOf(status)));
                    d A = p.this.A();
                    if (A != null) {
                        A.d(p.this.v());
                    }
                }
            }
        }

        public void onCommissioningComplete(long nodeId, int errorCode) {
            Object[] objArr = {new Long(nodeId), new Integer(errorCode)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7796, new Class[]{Long.TYPE, Integer.TYPE}, Void.TYPE).isSupported) {
                p pVar = p.this;
                p.l(pVar, "onCommissioningComplete nodeId: " + nodeId + ",errorCode:" + errorCode);
                p.this.A().e(errorCode, k.l("matter-onCommissioningComplete nodeId=", Long.valueOf(nodeId)), "matter-onCommissioningComplete");
                if (errorCode == p.this.f) {
                    com.leedarson.serviceimpl.k kVar = com.leedarson.serviceimpl.k.a;
                    StringBuilder sb = new StringBuilder();
                    sb.append("Matter 配网成功: ");
                    sb.append(nodeId);
                    sb.append(",hexAddr:");
                    String l = Long.toString(nodeId, kotlin.text.a.a(16));
                    k.d(l, "java.lang.Long.toString(this, checkRadix(radix))");
                    sb.append(l);
                    com.leedarson.serviceimpl.k.e(kVar, sb.toString(), (String) null, 2, (Object) null);
                    p.this.k = true;
                    d A = p.this.A();
                    if (A != null) {
                        A.a(nodeId, this.a);
                        return;
                    }
                    return;
                }
                p.n(p.this, R$string.rendezvous_over_ble_pairing_failure_text, String.valueOf(errorCode));
                d A2 = p.this.A();
                if (A2 != null) {
                    A2.c(errorCode, "pairing failed", new IllegalStateException("pairing failed"));
                }
            }
        }

        public void onPairingComplete(int code) {
            String errMsg;
            Object[] objArr = {new Integer(code)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7797, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                p.l(p.this, k.l("onPairingComplete code: ", Integer.valueOf(code)));
                if (code != p.this.f) {
                    if (code == 50) {
                        errMsg = "timed out";
                    } else {
                        errMsg = "";
                    }
                    d A = p.this.A();
                    if (A != null) {
                        A.c(code, k.l("onPairingComplete sdk error code:", Integer.valueOf(code)), new Exception(errMsg));
                    }
                }
            }
        }

        public void onOpCSRGenerationComplete(@NotNull byte[] csr) {
            if (!PatchProxy.proxy(new Object[]{csr}, this, changeQuickRedirect, false, 7798, new Class[]{byte[].class}, Void.TYPE).isSupported) {
                k.e(csr, "csr");
                p.this.A().e(200, "onOpCSRGenerationComplete ", "onOpCSRGenerationComplete");
                p.l(p.this, k.l("onOpCSRGenerationComplete csr:", new String(csr, kotlin.text.c.a)));
            }
        }

        public void onPairingDeleted(int code) {
            Object[] objArr = {new Integer(code)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7799, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                p.this.A().e(code, k.l("onPairingDeleted code=", Integer.valueOf(code)), "onPairingDeleted");
                p.l(p.this, k.l("onPairingDeleted: ", Integer.valueOf(code)));
            }
        }

        public void onCloseBleComplete() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7800, new Class[0], Void.TYPE).isSupported) {
                p.l(p.this, "onCloseBleComplete");
            }
        }

        public void onError(@Nullable Throwable error) {
            if (!PatchProxy.proxy(new Object[]{error}, this, changeQuickRedirect, false, 7801, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
                com.leedarson.serviceimpl.k.a.c(k.l("onError: ", error), p.this.e);
                if (error != null) {
                    p.this.A().c(-1, error.getMessage(), (Exception) error);
                }
            }
        }
    }

    private final void G(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 7781, new Class[]{String.class}, Void.TYPE).isSupported) {
            com.leedarson.serviceimpl.k.a.a(msg, this.e);
        }
    }

    public final void u() {
        j z;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7782, new Class[0], Void.TYPE).isSupported && (z = z()) != null) {
            z.d();
        }
    }
}
