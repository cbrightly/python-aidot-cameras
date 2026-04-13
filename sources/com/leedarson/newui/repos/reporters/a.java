package com.leedarson.newui.repos.reporters;

import android.text.TextUtils;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.google.gson.Gson;
import com.leedarson.smartcamera.reporters.WebRtcLogStepBean;
import com.leedarson.smartcamera.reporters.WebRtcReporterV3;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import kotlin.collections.y;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.text.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;
import timber.log.a;

/* compiled from: LivePlayContinueTimeReporter.kt */
public final class a {
    @NotNull
    public static final C0116a a = new C0116a((DefaultConstructorMarker) null);
    /* access modifiers changed from: private */
    @NotNull
    public static HashMap<String, a> b = new HashMap<>();
    public static ChangeQuickRedirect changeQuickRedirect;
    private int c;
    private long d;
    @NotNull
    private final ArrayList<WebRtcLogStepBean> e = new ArrayList<>();
    @NotNull
    private String f = "";
    @Nullable
    private String g;

    public a(@NotNull String peerId) {
        k.e(peerId, "peerId");
        this.f = peerId;
    }

    /* renamed from: com.leedarson.newui.repos.reporters.a$a  reason: collision with other inner class name */
    /* compiled from: LivePlayContinueTimeReporter.kt */
    public static final class C0116a {
        public static ChangeQuickRedirect changeQuickRedirect;

        public /* synthetic */ C0116a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private C0116a() {
        }

        @Nullable
        public final a a(@Nullable String peerId) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{peerId}, this, changeQuickRedirect, false, 4479, new Class[]{String.class}, a.class);
            if (proxy.isSupported) {
                return (a) proxy.result;
            }
            if (peerId != null && a.b.containsKey(peerId)) {
                return (a) a.b.get(peerId);
            }
            if (peerId == null) {
                String newPeerId = k.l(peerId, "_empty");
                a tpReporter = new a(newPeerId);
                a.b.put(newPeerId, tpReporter);
                return tpReporter;
            }
            a tpReporter2 = new a(peerId);
            a.b.put(peerId, tpReporter2);
            return tpReporter2;
        }
    }

    @NotNull
    public final a h(@NotNull String deviceId) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{deviceId}, this, changeQuickRedirect, false, 4471, new Class[]{String.class}, a.class);
        if (proxy.isSupported) {
            return (a) proxy.result;
        }
        k.e(deviceId, "deviceId");
        this.g = deviceId;
        return this;
    }

    public final void g() {
        boolean flagIsInterupt;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4472, new Class[0], Void.TYPE).isSupported) {
            ArrayList<WebRtcLogStepBean> arrayList = this.e;
            if (!(arrayList instanceof Collection) || !arrayList.isEmpty()) {
                Iterator<T> it = arrayList.iterator();
                while (true) {
                    if (it.hasNext()) {
                        if (((WebRtcLogStepBean) it.next()).step.equals("STEP_ONRECEIVE_FIRST_FRAME")) {
                            flagIsInterupt = true;
                            break;
                        }
                    } else {
                        flagIsInterupt = false;
                        break;
                    }
                }
            } else {
                flagIsInterupt = false;
            }
            if (!flagIsInterupt) {
                a(k.l("当前步骤非中断拉流操作 ---> ", this.f));
                this.e.clear();
            } else if (x.S(this.f, "empty", false, 2, (Object) null)) {
                a("当前PeerId 是一个空的，无效数据不用上报 ");
                this.e.clear();
            } else {
                i();
                com.leedarson.log.elk.a builder = com.leedarson.log.elk.a.y(this).c(WebRtcReporterV3.class.getSimpleName()).t("WebRTCLive").x(this.f).o("info").e("LiveDuration").u(TypedValues.TransitionType.S_DURATION, Long.valueOf(this.d)).u("code", Integer.valueOf(this.c)).u("desc", ((WebRtcLogStepBean) y.d0(this.e)).desc).u("peerId", this.f);
                JSONArray _stepArray = new JSONArray(new Gson().toJson((Object) this.e));
                JSONObject _jsonObject = new JSONObject();
                _jsonObject.put("webrtcQuanlity", (Object) WebRtcReporterV3.D(this.f));
                _stepArray.put((Object) _jsonObject);
                builder.r(_stepArray);
                k.d(builder, "builder");
                c(builder);
                builder.a().b();
                this.e.clear();
                b.remove(this.f);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x001d, code lost:
        r0 = r11;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void c(com.leedarson.log.elk.a r12) {
        /*
            r11 = this;
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r8 = 0
            r1[r8] = r12
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<com.leedarson.log.elk.a> r0 = com.leedarson.log.elk.a.class
            r6[r8] = r0
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 4473(0x1179, float:6.268E-42)
            r2 = r11
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x001d
            return
        L_0x001d:
            r0 = r11
            java.lang.String r1 = r0.g
            if (r1 != 0) goto L_0x0024
            goto L_0x00b6
        L_0x0024:
            r2 = 0
            r3 = 0
            r3 = -1
            com.alibaba.android.arouter.launcher.a r4 = com.alibaba.android.arouter.launcher.a.c()
            java.lang.Class<com.leedarson.serviceinterface.LDSBaseMqttService> r5 = com.leedarson.serviceinterface.LDSBaseMqttService.class
            java.lang.Object r4 = r4.g(r5)
            com.leedarson.serviceinterface.LDSBaseMqttService r4 = (com.leedarson.serviceinterface.LDSBaseMqttService) r4
            if (r4 != 0) goto L_0x0037
            goto L_0x003e
        L_0x0037:
            r5 = 0
            int r3 = r4.getMqttConnectStatue()
        L_0x003e:
            com.leedarson.bean.IpcDeviceBean r4 = com.leedarson.serviceimpl.ipcservice.IpcServiceImpl.p(r1)
            if (r4 != 0) goto L_0x0047
            goto L_0x00b5
        L_0x0047:
            r5 = 0
            r6 = r12
            r7 = 0
            java.lang.String r9 = r4.id
            java.lang.String r10 = "deviceId"
            r6.u(r10, r9)
            java.lang.String r9 = r4.modelId
            java.lang.String r10 = "modelId"
            r6.u(r10, r9)
            java.lang.String r9 = r4.firmwareVersion
            java.lang.String r10 = "fireWareVersion"
            r6.u(r10, r9)
            java.lang.String r9 = r4.hardwareVersion
            java.lang.String r10 = "hardwareVersion"
            r6.u(r10, r9)
            java.lang.Boolean r9 = r4.online
            java.lang.String r10 = "mqttOnlineStatue"
            if (r9 == 0) goto L_0x007d
            java.lang.String r8 = "deviceBean.online"
            kotlin.jvm.internal.k.d(r9, r8)
            boolean r8 = r9.booleanValue()
            java.lang.Integer r8 = java.lang.Integer.valueOf(r8)
            r6.u(r10, r8)
            goto L_0x0084
        L_0x007d:
            java.lang.Integer r8 = java.lang.Integer.valueOf(r8)
            r6.u(r10, r8)
        L_0x0084:
            java.lang.String r8 = "AndroidLiveType"
            com.leedarson.bean.PropsBean r9 = r4.props     // Catch:{ Exception -> 0x009f }
            r10 = 0
            if (r9 != 0) goto L_0x008d
        L_0x008c:
            goto L_0x009b
        L_0x008d:
            java.lang.String r9 = r9.liveType     // Catch:{ Exception -> 0x009f }
            if (r9 != 0) goto L_0x0092
            goto L_0x008c
        L_0x0092:
            int r9 = java.lang.Integer.parseInt(r9)     // Catch:{ Exception -> 0x009f }
            java.lang.Integer r10 = java.lang.Integer.valueOf(r9)     // Catch:{ Exception -> 0x009f }
            goto L_0x008c
        L_0x009b:
            r6.u(r8, r10)     // Catch:{ Exception -> 0x009f }
            goto L_0x00a9
        L_0x009f:
            r8 = move-exception
            java.lang.String r9 = "LiveType 转化异常 e="
            java.lang.String r9 = kotlin.jvm.internal.k.l(r9, r8)
            r0.a(r9)
        L_0x00a9:
            java.lang.Integer r8 = java.lang.Integer.valueOf(r3)
            java.lang.String r9 = "androidMqttOnline"
            r6.u(r9, r8)
        L_0x00b5:
        L_0x00b6:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.repos.reporters.a.c(com.leedarson.log.elk.a):void");
    }

    public final void d() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4474, new Class[0], Void.TYPE).isSupported) {
            this.e.clear();
            ArrayList<WebRtcLogStepBean> arrayList = this.e;
            WebRtcLogStepBean $this$onReceiveFirstRender_u24lambda_u2d5 = new WebRtcLogStepBean("STEP_ONRECEIVE_FIRST_FRAME", 200);
            $this$onReceiveFirstRender_u24lambda_u2d5.endTrace("收到首帧渲染", 200);
            kotlin.x xVar = kotlin.x.a;
            arrayList.add($this$onReceiveFirstRender_u24lambda_u2d5);
        }
    }

    public final void e(int code, @NotNull String desc) {
        Object[] objArr = {new Integer(code), desc};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4475, new Class[]{Integer.TYPE, String.class}, Void.TYPE).isSupported) {
            k.e(desc, "desc");
            ArrayList<WebRtcLogStepBean> arrayList = this.e;
            WebRtcLogStepBean $this$onUserLeaveLiveRoom_u24lambda_u2d6 = new WebRtcLogStepBean("STEP_LEAVEROOM", 200);
            $this$onUserLeaveLiveRoom_u24lambda_u2d6.endTrace(k.l("用户离开直播间", desc), code);
            kotlin.x xVar = kotlin.x.a;
            arrayList.add($this$onUserLeaveLiveRoom_u24lambda_u2d6);
            g();
        }
    }

    public final void f(@NotNull String str, @NotNull String str2) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{str, str2}, this, changeQuickRedirect, false, 4476, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            String desc = str2;
            String peerId = str;
            k.e(peerId, "peerId");
            k.e(desc, "desc");
            Iterable $this$filterTo$iv$iv = this.e;
            ArrayList arrayList = new ArrayList();
            for (T next : $this$filterTo$iv$iv) {
                if (((WebRtcLogStepBean) next).step.equals("STEP_ONRECEIVE_FIRST_FRAME")) {
                    arrayList.add(next);
                }
            }
            if (arrayList.size() == 0) {
                a.b g2 = timber.log.a.g("WebRtc");
                g2.c("通道发生异常(但用户没有拉流行为-丢弃本次日志上报)  peerId=" + this.f + "  desc= " + desc, new Object[0]);
            }
            if (!TextUtils.isEmpty(desc) && x.S(this.f, peerId, false, 2, (Object) null)) {
                if (x.S(desc, "Unwritable after 5 ping", false, 2, (Object) null)) {
                    ArrayList<WebRtcLogStepBean> arrayList2 = this.e;
                    WebRtcLogStepBean webRtcLogStepBean = new WebRtcLogStepBean("STEP_WEBRTC_TRANSPORT_ERROR", -31021001);
                    WebRtcLogStepBean $this$onWebRtcChannelTransportErrorReceive_u24lambda_u2d9 = webRtcLogStepBean;
                    $this$onWebRtcChannelTransportErrorReceive_u24lambda_u2d9.endTrace("[归因] Ping 设备超时导致通道断开(无法Ping通设备)", -31021001);
                    $this$onWebRtcChannelTransportErrorReceive_u24lambda_u2d9.responseParams = desc;
                    kotlin.x xVar = kotlin.x.a;
                    arrayList2.add(webRtcLogStepBean);
                } else if (x.S(desc, "closed by remote", false, 2, (Object) null)) {
                    ArrayList<WebRtcLogStepBean> arrayList3 = this.e;
                    WebRtcLogStepBean $this$onWebRtcChannelTransportErrorReceive_u24lambda_u2d10 = new WebRtcLogStepBean("STEP_WEBRTC_TRANSPORT_ERROR", -31022001);
                    $this$onWebRtcChannelTransportErrorReceive_u24lambda_u2d10.endTrace(k.l("[归因] 远程设备将通道断开 ", desc), -31022001);
                    kotlin.x xVar2 = kotlin.x.a;
                    arrayList3.add($this$onWebRtcChannelTransportErrorReceive_u24lambda_u2d10);
                } else if (x.S(desc, "App has no Network", false, 2, (Object) null)) {
                    ArrayList<WebRtcLogStepBean> arrayList4 = this.e;
                    WebRtcLogStepBean $this$onWebRtcChannelTransportErrorReceive_u24lambda_u2d11 = new WebRtcLogStepBean("STEP_WEBRTC_SYSTEM_NET_CHANGE", -31006001);
                    $this$onWebRtcChannelTransportErrorReceive_u24lambda_u2d11.endTrace(k.l("[归因] 手机断网了 ", desc), -31006001);
                    kotlin.x xVar3 = kotlin.x.a;
                    arrayList4.add($this$onWebRtcChannelTransportErrorReceive_u24lambda_u2d11);
                } else {
                    ArrayList<WebRtcLogStepBean> arrayList5 = this.e;
                    WebRtcLogStepBean $this$onWebRtcChannelTransportErrorReceive_u24lambda_u2d12 = new WebRtcLogStepBean("STEP_WEBRTC_TRANSPORT_ERROR", 0);
                    $this$onWebRtcChannelTransportErrorReceive_u24lambda_u2d12.endTrace(k.l("[归因] 其它未知原因 ", desc), 0);
                    kotlin.x xVar4 = kotlin.x.a;
                    arrayList5.add($this$onWebRtcChannelTransportErrorReceive_u24lambda_u2d12);
                }
                g();
            }
        }
    }

    public final void i() {
        Long l;
        Object element$iv;
        boolean flagIsInterupt = false;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4477, new Class[0], Void.TYPE).isSupported) {
            WebRtcLogStepBean anylizeStep = new WebRtcLogStepBean("STEP_APP_REASON_CONFIG", 200);
            ArrayList<WebRtcLogStepBean> arrayList = this.e;
            if (!(arrayList instanceof Collection) || !arrayList.isEmpty()) {
                Iterator<T> it = arrayList.iterator();
                while (true) {
                    if (it.hasNext()) {
                        if (((WebRtcLogStepBean) it.next()).step.equals("STEP_ONRECEIVE_FIRST_FRAME")) {
                            flagIsInterupt = true;
                            break;
                        }
                    } else {
                        break;
                    }
                }
            }
            if (flagIsInterupt) {
                Iterator<T> it2 = this.e.iterator();
                while (true) {
                    l = null;
                    if (!it2.hasNext()) {
                        element$iv = null;
                        break;
                    }
                    element$iv = it2.next();
                    if (((WebRtcLogStepBean) element$iv).step.equals("STEP_ONRECEIVE_FIRST_FRAME")) {
                        break;
                    }
                }
                WebRtcLogStepBean webRtcLogStepBean = (WebRtcLogStepBean) element$iv;
                if (webRtcLogStepBean != null) {
                    l = Long.valueOf(webRtcLogStepBean._beginTraceTimeSpan);
                }
                Long startTimeSpan = l;
                WebRtcLogStepBean $this$toGetDetailReasonForDisconnect_u24lambda_u2d15 = (WebRtcLogStepBean) y.d0(this.e);
                anylizeStep.endTrace($this$toGetDetailReasonForDisconnect_u24lambda_u2d15.desc, $this$toGetDetailReasonForDisconnect_u24lambda_u2d15.code);
                this.c = $this$toGetDetailReasonForDisconnect_u24lambda_u2d15.code;
                long j = $this$toGetDetailReasonForDisconnect_u24lambda_u2d15._endTraceTimeSpan;
                k.c(startTimeSpan);
                this.d = j - startTimeSpan.longValue();
                this.e.add(anylizeStep);
                return;
            }
            WebRtcLogStepBean $this$toGetDetailReasonForDisconnect_u24lambda_u2d16 = (WebRtcLogStepBean) y.d0(this.e);
            anylizeStep.endTrace($this$toGetDetailReasonForDisconnect_u24lambda_u2d16.desc, $this$toGetDetailReasonForDisconnect_u24lambda_u2d16.code);
            this.c = $this$toGetDetailReasonForDisconnect_u24lambda_u2d16.code;
            this.d = 0;
            this.e.add(anylizeStep);
            a("（Warning）不是拉流成功后，又被中断的数据事件(用户在拉流过程中，中断了)");
        }
    }

    private final void a(String message) {
        if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 4478, new Class[]{String.class}, Void.TYPE).isSupported) {
            a.b g2 = timber.log.a.g("WebRtc");
            g2.c(message + " peerId=" + this.f, new Object[0]);
        }
    }
}
