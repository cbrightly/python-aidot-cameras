package com.leedarson.smartcamera.reporters;

import android.text.TextUtils;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.didichuxing.doraemonkit.okgo.cookie.SerializableCookie;
import com.didichuxing.doraemonkit.widget.JustifyTextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.maps.android.BuildConfig;
import com.leedarson.base.utils.c;
import com.leedarson.serviceinterface.HttpReportService;
import com.leedarson.serviceinterface.IpcService;
import com.leedarson.serviceinterface.LDSBaseMqttService;
import com.leedarson.serviceinterface.TcpService;
import com.leedarson.smartcamera.bean.AckBean;
import com.leedarson.smartcamera.kvswebrtc.utils.e;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import meshsdk.ctrl.GroupCtrlAdapter;
import org.json.JSONArray;
import org.json.JSONObject;
import timber.log.a;

public class WebRtcReporterV3 {
    private static HashMap<String, WebRtcReporterV3> a = new HashMap<>();
    private static HashMap<String, String> b = new HashMap<>();
    private static HashMap<String, String> c = new HashMap<>();
    public static ChangeQuickRedirect changeQuickRedirect;
    private static HashMap<String, String> d = new HashMap<>();
    private static HashMap<String, e> e = new HashMap<>();
    public static String f = "PreLink";
    public static String g = "FirstFrameRender";
    public static String h = WebRtcLogStepBean.EVENT_PEER_AND_DATACHANNEL_CONNECTED;
    private CopyOnWriteArrayList<WebRtcLogStepBean> i = new CopyOnWriteArrayList<>();
    private FirstFrameRenderAutoReconnectHistoryData j = null;
    public String k = "";
    public String l = "";
    private TempIpcDeviceBean m;
    public String n = f;
    public String o = "";
    com.leedarson.smartcamera.kvswebrtc.datarepos.a p = new com.leedarson.smartcamera.kvswebrtc.datarepos.a();
    private long q = GroupCtrlAdapter.RETRY_TIMEOUT;

    public static class TempIpcDeviceBean {
        public String firmwareVersion = "";
        public String hardwareVersion = "";
        public int liveType = -1;
        public String modelId = "";
        public int onlineStatue = -1;
        public int p2pCache = 0;
        public int powerType = 1;
    }

    public static WebRtcReporterV3 v(String peerId, String deviceId) {
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{peerId, deviceId}, (Object) null, changeQuickRedirect, true, 10171, new Class[]{cls, cls}, WebRtcReporterV3.class);
        if (proxy.isSupported) {
            return (WebRtcReporterV3) proxy.result;
        }
        if (a.containsKey(peerId) && !TextUtils.isEmpty(peerId)) {
            return a.get(peerId);
        }
        WebRtcReporterV3 v3 = new WebRtcReporterV3();
        if (TextUtils.isEmpty(peerId)) {
            peerId = System.currentTimeMillis() + "_已回收(peerId为空,自动重建)";
        }
        v3.H(peerId);
        v3.F(deviceId);
        v3.b();
        a.put(peerId, v3);
        timber.log.a.g("WebRtcLog").h(" 新创建webrtc日志收集器 peerId=" + peerId + " deviceId=" + deviceId, new Object[0]);
        return a.get(peerId);
    }

    public static void S(String peerId, String connectType) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls};
        if (!PatchProxy.proxy(new Object[]{peerId, connectType}, (Object) null, changeQuickRedirect, true, 10172, clsArr, Void.TYPE).isSupported) {
            b.put(peerId, connectType);
        }
    }

    public static String t(String _peerId) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{_peerId}, (Object) null, changeQuickRedirect, true, 10173, new Class[]{String.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if (b.containsKey(_peerId)) {
            return b.get(_peerId);
        }
        return "";
    }

    public static void R(String peerId, String connectType) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls};
        if (!PatchProxy.proxy(new Object[]{peerId, connectType}, (Object) null, changeQuickRedirect, true, 10174, clsArr, Void.TYPE).isSupported) {
            c.put(peerId, connectType);
        }
    }

    public static String s(String _peerId) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{_peerId}, (Object) null, changeQuickRedirect, true, 10175, new Class[]{String.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if (c.containsKey(_peerId)) {
            return c.get(_peerId);
        }
        return "";
    }

    public static void Q(String peerId, String message) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls};
        if (!PatchProxy.proxy(new Object[]{peerId, message}, (Object) null, changeQuickRedirect, true, 10176, clsArr, Void.TYPE).isSupported) {
            d.put(peerId, message);
        }
    }

    public static String x(String peerId) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{peerId}, (Object) null, changeQuickRedirect, true, 10177, new Class[]{String.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if (d.containsKey(peerId)) {
            return d.get(peerId);
        }
        return "";
    }

    public static void T(String _peerId, e logCollectHelperV2) {
        Class[] clsArr = {String.class, e.class};
        if (!PatchProxy.proxy(new Object[]{_peerId, logCollectHelperV2}, (Object) null, changeQuickRedirect, true, 10178, clsArr, Void.TYPE).isSupported) {
            e.put(_peerId, logCollectHelperV2);
        }
    }

    public static String D(String peerId) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{peerId}, (Object) null, changeQuickRedirect, true, 10179, new Class[]{String.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        String tp = peerId.replace("_player", "");
        if (e.containsKey(tp)) {
            return e.get(tp).k();
        }
        return "NoDetailInfo";
    }

    public static WebRtcReporterV3 w(String peerId, String deviceId) {
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{peerId, deviceId}, (Object) null, changeQuickRedirect, true, 10180, new Class[]{cls, cls}, WebRtcReporterV3.class);
        if (proxy.isSupported) {
            return (WebRtcReporterV3) proxy.result;
        }
        a.b g2 = timber.log.a.g("WebRtcLog");
        g2.h("===> 新创建webrtc日志收集器 peerId=" + peerId + " deviceId=" + deviceId, new Object[0]);
        return v(peerId + "_player", deviceId).G(g);
    }

    public WebRtcReporterV3 K(WebRtcLogStepBean stepBean) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{stepBean}, this, changeQuickRedirect, false, 10182, new Class[]{WebRtcLogStepBean.class}, WebRtcReporterV3.class);
        if (proxy.isSupported) {
            return (WebRtcReporterV3) proxy.result;
        }
        this.i.add(stepBean);
        return this;
    }

    public int C() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10183, new Class[0], Integer.TYPE);
        return proxy.isSupported ? ((Integer) proxy.result).intValue() : this.i.size();
    }

    public CopyOnWriteArrayList<WebRtcLogStepBean> r() {
        return this.i;
    }

    public WebRtcReporterV3 H(String data) {
        this.l = data;
        return this;
    }

    public WebRtcReporterV3 G(String data) {
        this.n = data;
        return this;
    }

    public WebRtcReporterV3 F(String deviceId) {
        this.k = deviceId;
        return this;
    }

    public void I(FirstFrameRenderAutoReconnectHistoryData data) {
        this.j = data;
    }

    public WebRtcReporterV3 J(WebRtcLogStepBean logStepBean) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{logStepBean}, this, changeQuickRedirect, false, 10184, new Class[]{WebRtcLogStepBean.class}, WebRtcReporterV3.class);
        if (proxy.isSupported) {
            return (WebRtcReporterV3) proxy.result;
        }
        CopyOnWriteArrayList<WebRtcLogStepBean> copyOnWriteArrayList = this.i;
        if (copyOnWriteArrayList != null && copyOnWriteArrayList.size() > 0) {
            synchronized (this.i) {
                Gson gson = new Gson();
                ArrayList<WebRtcLogStepBean> oldSteps = (ArrayList) gson.fromJson(gson.toJson((Object) this.i), new a().getType());
                this.i.clear();
                a("putStartEnterLiveRoomStep 清除日志----> 进入直播间，在之前的步骤前加入进入直播间步骤  oldStep.Size=" + oldSteps.size());
                this.i.add(logStepBean);
                this.i.addAll(oldSteps);
                a("putStartEnterLiveRoomStep 清除日志----> 进入直播间，在之前的步骤前加入进入直播间步骤  nowSteps.Size=" + this.i.size());
            }
        }
        return this;
    }

    public class a extends TypeToken<ArrayList<WebRtcLogStepBean>> {
        a() {
        }
    }

    public void o() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10185, new Class[0], Void.TYPE).isSupported) {
            a("clearLog 清除日志----> ");
            this.i.clear();
        }
    }

    public void L(String ref) {
        if (!PatchProxy.proxy(new Object[]{ref}, this, changeQuickRedirect, false, 10186, new Class[]{String.class}, Void.TYPE).isSupported) {
            c(ref, 0);
        }
    }

    private void c(String str, int i2) {
        String str2;
        if (!PatchProxy.proxy(new Object[]{str, new Integer(i2)}, this, changeQuickRedirect, false, 10187, new Class[]{String.class, Integer.TYPE}, Void.TYPE).isSupported) {
            int retryCount = i2;
            String ref = str;
            if (retryCount >= 3) {
                try {
                    a("reportError: 重试上报已达最大限度防止递归已做抛弃");
                } catch (Exception e2) {
                    timber.log.a.c("WebRtcReportV3 22数据上报出现异常  ref=" + ref + "  exception=" + e2.toString(), new Object[0]);
                    StringBuilder sb = new StringBuilder();
                    sb.append(ref);
                    sb.append("重试上报: retry=");
                    sb.append(retryCount + 1);
                    c(sb.toString(), retryCount + 1);
                }
            } else {
                CopyOnWriteArrayList<WebRtcLogStepBean> copyOnWriteArrayList = this.i;
                if (copyOnWriteArrayList != null) {
                    if (copyOnWriteArrayList.size() != 0) {
                        if (!this.l.contains(BuildConfig.TRAVIS)) {
                            if (this.n.equals(f)) {
                                a.a.a().d(this);
                            }
                            int nativeMqttOnline = -1;
                            LDSBaseMqttService _mqttService = (LDSBaseMqttService) com.alibaba.android.arouter.launcher.a.c().g(LDSBaseMqttService.class);
                            if (_mqttService != null) {
                                nativeMqttOnline = _mqttService.getMqttConnectStatue();
                            }
                            String tcpConnectState = "";
                            TcpService _tcpService = (TcpService) com.alibaba.android.arouter.launcher.a.c().g(TcpService.class);
                            if (_tcpService != null) {
                                tcpConnectState = _tcpService.getTcpConnectStatus(this.k) ? "已连接" : "未连接";
                            }
                            Gson gson = new Gson();
                            com.leedarson.log.elk.a u = com.leedarson.log.elk.a.y(this).c(WebRtcReporterV3.class.getSimpleName()).t("WebRTCLive").x(this.l).o("info").e(this.n).u(TypedValues.TransitionType.S_DURATION, Long.valueOf(l())).u("getIceConfig", Long.valueOf(g())).u("offer2anwser", Long.valueOf(i())).u("candidateDuration", Long.valueOf(e())).u("anwser2P2P", Long.valueOf(h())).u("P2P2Dtls", 0).u("powerType", Integer.valueOf(E(this.m))).u("AndroidChannelType", -1).u("answerSource", this.o).u("tcpConnectState", tcpConnectState).u("code", Integer.valueOf(f()));
                            if (O() == null) {
                                str2 = N().desc;
                            } else {
                                str2 = O().desc + "";
                            }
                            com.leedarson.log.elk.a builder = u.u("desc", str2).u("deviceId", u()).u("modelId", this.m.modelId).u("fireWareVersion", this.m.firmwareVersion).u("hardwareVersion", this.m.hardwareVersion).u("mqttOnlineStatue", Integer.valueOf(this.m.onlineStatue)).u("peerId", this.l.replace("_player", "")).u("AndroidLiveType", Integer.valueOf(this.m.liveType)).u("logVersion", "0.1.0.0825155016").u("signalConnectDuration", Long.valueOf(j())).u("androidMqttOnline", Integer.valueOf(nativeMqttOnline)).u("androidPageName", c.h().c() != null ? c.h().c().getClass().getSimpleName() : "UnKnowPage").u("androidFromBack", Integer.valueOf(((IpcService) com.alibaba.android.arouter.launcher.a.c().g(IpcService.class)).checkWebRtcChannelIsFromBack(this.k) ? 1 : 0)).u("androidH265HardCode", Integer.valueOf(this.p.a() ? 1 : 0)).u("iceSelectedType", t(this.l.replace("_player", "")));
                            JSONArray _stepArray = new JSONArray(gson.toJson((Object) this.i));
                            JSONObject _jsonObject = new JSONObject();
                            _jsonObject.put("candidatePairMsg", (Object) s(this.l.replace("_player", "")));
                            _jsonObject.put("nackInfo", (Object) x(this.l.replace("_player", "")));
                            _jsonObject.put("webrtcQuanlity", (Object) D(this.l));
                            _jsonObject.put("ref", (Object) ref);
                            _stepArray.put((Object) _jsonObject);
                            q(builder);
                            builder.r(_stepArray).a().b();
                            this.i.clear();
                            a("----> report日志上报清除" + a.get(this.l).toString() + "  ref=" + ref);
                            a.remove(this.l);
                            return;
                        }
                    }
                }
                a("----> 准备日志上报(但是_steps的长度为空)" + a.get(this.l).toString() + "  ref=" + ref);
            }
        }
    }

    private void q(com.leedarson.log.elk.a builder) {
        if (!PatchProxy.proxy(new Object[]{builder}, this, changeQuickRedirect, false, 10188, new Class[]{com.leedarson.log.elk.a.class}, Void.TYPE).isSupported) {
            int retryTimes = 0;
            String retryPeerIds = "";
            String retryDetailSteps = "";
            FirstFrameRenderAutoReconnectHistoryData firstFrameRenderAutoReconnectHistoryData = this.j;
            if (firstFrameRenderAutoReconnectHistoryData != null) {
                retryTimes = firstFrameRenderAutoReconnectHistoryData.c();
                retryPeerIds = this.j.b();
                retryDetailSteps = this.j.a();
            }
            builder.u("autoRetryConnectTime", Integer.valueOf(retryTimes)).u("oldPeerIDs", retryPeerIds).u("retryDetailSteps", retryDetailSteps);
        }
    }

    public void M(boolean z, boolean z2, CopyOnWriteArrayList<WebRtcLogStepBean> copyOnWriteArrayList, WebRtcLogStepBean webRtcLogStepBean, boolean z3) {
        Object obj;
        String str;
        Object[] objArr = {new Byte(z ? (byte) 1 : 0), new Byte(z2 ? (byte) 1 : 0), copyOnWriteArrayList, webRtcLogStepBean, new Byte(z3 ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Boolean.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 10189, new Class[]{cls, cls, CopyOnWriteArrayList.class, WebRtcLogStepBean.class, cls}, Void.TYPE).isSupported) {
            boolean dataChannelConnectState = z2;
            WebRtcLogStepBean startUseLiveRoom = webRtcLogStepBean;
            boolean p2pConnectState = z;
            CopyOnWriteArrayList<WebRtcLogStepBean> preLinkSteps = copyOnWriteArrayList;
            boolean z4 = z3;
            CopyOnWriteArrayList<WebRtcLogStepBean> copyOnWriteArrayList2 = this.i;
            if (copyOnWriteArrayList2 != null && copyOnWriteArrayList2.size() != 0 && !this.l.contains(BuildConfig.TRAVIS) && startUseLiveRoom != null) {
                d(p2pConnectState, dataChannelConnectState, preLinkSteps, startUseLiveRoom);
                int nativeMqttOnline = -1;
                LDSBaseMqttService _mqttService = (LDSBaseMqttService) com.alibaba.android.arouter.launcher.a.c().g(LDSBaseMqttService.class);
                if (_mqttService != null) {
                    nativeMqttOnline = _mqttService.getMqttConnectStatue();
                }
                Gson gson = new Gson();
                try {
                    com.leedarson.log.elk.a u = com.leedarson.log.elk.a.y(this).c(WebRtcReporterV3.class.getSimpleName()).t("WebRTCLive").x(this.l).o("info").e(this.n).u(TypedValues.TransitionType.S_DURATION, Long.valueOf(System.currentTimeMillis() - startUseLiveRoom._endTraceTimeSpan)).u("powerType", Integer.valueOf(E(this.m)));
                    if (O() == null) {
                        obj = Integer.valueOf(N().code);
                    } else {
                        obj = O().code + "";
                    }
                    com.leedarson.log.elk.a u2 = u.u("code", obj);
                    if (O() == null) {
                        str = N().desc;
                    } else {
                        str = O().desc + "";
                    }
                    com.leedarson.log.elk.a builder = u2.u("desc", str).u("deviceId", u()).u("modelId", this.m.modelId).u("fireWareVersion", this.m.firmwareVersion).u("hardwareVersion", this.m.hardwareVersion).u("mqttOnlineStatue", Integer.valueOf(this.m.onlineStatue)).u("peerId", this.l.replace("_player", "")).u("AndroidLiveType", Integer.valueOf(this.m.liveType)).u("logVersion", "0.1.0.0825155016").u("signalConnectDuration", Long.valueOf(j())).u("androidMqttOnline", Integer.valueOf(nativeMqttOnline)).u("androidPageName", c.h().c() != null ? c.h().c().getClass().getSimpleName() : "UnKnowPage").u("androidFromBack", Integer.valueOf(((IpcService) com.alibaba.android.arouter.launcher.a.c().g(IpcService.class)).checkWebRtcChannelIsFromBack(this.k) ? 1 : 0)).u("iceSelectedType", t(this.l.replace("_player", ""))).u("androidH265HardCode", Integer.valueOf(this.p.a() ? 1 : 0)).u("candidatePairMsg", s(this.l.replace("_player", "")));
                    q(builder);
                    JSONArray _stepArray = new JSONArray(gson.toJson((Object) this.i));
                    JSONObject _jsonObject = new JSONObject();
                    _jsonObject.put("candidatePairMsg", (Object) s(this.l.replace("_player", "")));
                    _jsonObject.put("nackInfo", (Object) x(this.l.replace("_player", "")));
                    _jsonObject.put("webrtcQuanlity", (Object) D(this.l));
                    _stepArray.put((Object) _jsonObject);
                    builder.r(_stepArray).a().b();
                } catch (Exception e2) {
                    timber.log.a.g("WebRtcLog").c(" 日志上报发生异常:" + e2.toString(), new Object[0]);
                }
                this.i.clear();
                a("----> reportFirstFrameDidNotAppearBeforeLeaveRoom report日志上报清除" + a.get(this.l).toString() + JustifyTextView.TWO_CHINESE_BLANK);
                a.remove(this.l);
            }
        }
    }

    public void d(boolean z, boolean z2, CopyOnWriteArrayList<WebRtcLogStepBean> copyOnWriteArrayList, WebRtcLogStepBean webRtcLogStepBean) {
        Object[] objArr = {new Byte(z ? (byte) 1 : 0), new Byte(z2 ? (byte) 1 : 0), copyOnWriteArrayList, webRtcLogStepBean};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Boolean.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 10190, new Class[]{cls, cls, CopyOnWriteArrayList.class, WebRtcLogStepBean.class}, Void.TYPE).isSupported) {
            boolean dataChannelConnectState = z2;
            WebRtcLogStepBean startUseLiveRoom = webRtcLogStepBean;
            boolean p2pConnectState = z;
            CopyOnWriteArrayList<WebRtcLogStepBean> preLinkSteps = copyOnWriteArrayList;
            if (!p2pConnectState || !dataChannelConnectState) {
                if (p2pConnectState) {
                    WebRtcLogStepBean _waitingStreamComing = new WebRtcLogStepBean(WebRtcLogStepBean.FIGOUT_AND_ANLYZE_WEBRTC_DETAIL_CODE, -31007705);
                    _waitingStreamComing.endTrace("原因：DataChannel还未打开 or 打开失败 - 用户离开直播间", -31007705);
                    this.i.add(_waitingStreamComing);
                    return;
                }
                Gson gson = new Gson();
                CopyOnWriteArrayList<WebRtcLogStepBean> preLinkCopySteps = (CopyOnWriteArrayList) gson.fromJson(gson.toJson((Object) preLinkSteps), new b().getType());
                int nativeMqttOnline = -1;
                LDSBaseMqttService _mqttService = (LDSBaseMqttService) com.alibaba.android.arouter.launcher.a.c().g(LDSBaseMqttService.class);
                String mqttConnectMsg = "";
                if (_mqttService != null) {
                    nativeMqttOnline = _mqttService.getMqttConnectStatue();
                    mqttConnectMsg = _mqttService.getErrorDetailMessage();
                }
                boolean z3 = dataChannelConnectState;
                HttpReportService logReport = (HttpReportService) com.alibaba.android.arouter.launcher.a.c().g(HttpReportService.class);
                if (logReport != null) {
                    boolean z4 = p2pConnectState;
                    String logInfoDetail = logReport.getNetWorkInfoDetail();
                    HttpReportService httpReportService = logReport;
                    if (!logInfoDetail.contains("enableNetworkFlag=true") && !logInfoDetail.contains("未检测")) {
                        WebRtcLogStepBean _stepCheckNetWork = new WebRtcLogStepBean(WebRtcLogStepBean.CHECK_NET_WORK_STEP, -31006001);
                        _stepCheckNetWork.endTrace("无网络 mqttOnline=" + nativeMqttOnline, -31006001);
                        this.i.add(_stepCheckNetWork);
                        return;
                    }
                } else {
                    boolean z5 = p2pConnectState;
                }
                if (this.m.onlineStatue == 0) {
                    WebRtcLogStepBean _checkDeviceOnlineStep = new WebRtcLogStepBean(WebRtcLogStepBean.DEVICE_ONLINE_CHECK_STEP, -31006101);
                    _checkDeviceOnlineStep.endTrace("原因：摄像机设备已离线", -31006101);
                    this.i.add(_checkDeviceOnlineStep);
                    return;
                }
                String result_desc = "";
                if (System.currentTimeMillis() - startUseLiveRoom._endTraceTimeSpan < this.q) {
                    WebRtcLogStepBean _checkStayInLiveRoom = new WebRtcLogStepBean(WebRtcLogStepBean.CHECK_IN_WAITING_IN_LEAVING_ROOM_STEP, -31005901);
                    _checkStayInLiveRoom.endTrace("原因：拉流等待时间太短(小于3S)", -31005901);
                    this.i.add(_checkStayInLiveRoom);
                } else if (nativeMqttOnline == 0) {
                    WebRtcLogStepBean _stepCheckAppMqttOnline = new WebRtcLogStepBean(WebRtcLogStepBean.CHECK_NET_WORK_STEP, -31007300);
                    _stepCheckAppMqttOnline.responseParams = mqttConnectMsg;
                    _stepCheckAppMqttOnline.endTrace("原因：Android Mqtt 离线 or还未连上 ", -31007300);
                    this.i.add(_stepCheckAppMqttOnline);
                } else {
                    WebRtcLogStepBean _figoutDetailCodeStep = new WebRtcLogStepBean(WebRtcLogStepBean.FIGOUT_AND_ANLYZE_WEBRTC_DETAIL_CODE, 200);
                    CopyOnWriteArrayList<WebRtcLogStepBean> _iceConfigSteps = B("GET_ICECONFIG", preLinkCopySteps);
                    if (_iceConfigSteps.size() != 0) {
                        WebRtcLogStepBean getIceConfigStep = _iceConfigSteps.get(0);
                        if (getIceConfigStep.code < 0) {
                            _figoutDetailCodeStep.endTrace("原因：获取iceConfig 异常", getIceConfigStep.code);
                            this.i.add(_figoutDetailCodeStep);
                            return;
                        }
                    }
                    CopyOnWriteArrayList<WebRtcLogStepBean> _netWorkChanged = B(WebRtcLogStepBean.DEVICE_NET_WORK_CHANGED_STEP, preLinkCopySteps);
                    if (_netWorkChanged.size() != 0) {
                        _figoutDetailCodeStep.endTrace("原因：用户手机网络发生切换拉流失败", -31006002);
                        this.i.add(_figoutDetailCodeStep);
                        return;
                    }
                    CopyOnWriteArrayList<WebRtcLogStepBean> _offerSteps = B(WebRtcLogStepBean.EXCHANGE_SDP, preLinkCopySteps);
                    String tip = "";
                    if (_mqttService != null) {
                        CopyOnWriteArrayList<WebRtcLogStepBean> copyOnWriteArrayList2 = _netWorkChanged;
                        WebRtcLogStepBean webRtcLogStepBean2 = startUseLiveRoom;
                        tip = _mqttService.getMqttConnectStatue() == 1 ? "mqtt在线" : "mqtt离线";
                    } else {
                        WebRtcLogStepBean webRtcLogStepBean3 = startUseLiveRoom;
                    }
                    if (_offerSteps.size() != 0) {
                        WebRtcLogStepBean _stepOffer = _offerSteps.get(0);
                        if (TextUtils.isEmpty(_stepOffer.responseParams)) {
                            _figoutDetailCodeStep.endTrace("原因：用户离开直播间时，还未收到来自设备的answer,用户离开直播间时还未等到固件的answer (固件&&broker)", -31007502);
                            this.i.add(_figoutDetailCodeStep);
                            return;
                        }
                        int result_code = _stepOffer.code;
                        String str = result_desc;
                        if (result_code != 200) {
                            switch (result_code) {
                                case AckBean.LIVE_SD_MAX_CONNECT_ERROR:
                                    _figoutDetailCodeStep.endTrace("原因：" + _stepOffer.desc, AckBean.LIVE_SD_MAX_CONNECT_ERROR);
                                    this.i.add(_figoutDetailCodeStep);
                                    return;
                                case AckBean.WEBRTC_ERROR_EN_RTC_ERR_CODE_SESSION_EXCEED:
                                    _figoutDetailCodeStep.endTrace("原因：" + _stepOffer.desc, AckBean.WEBRTC_ERROR_EN_RTC_ERR_CODE_SESSION_EXCEED);
                                    this.i.add(_figoutDetailCodeStep);
                                    return;
                                case 4081:
                                    _figoutDetailCodeStep.endTrace("原因：Mqtt还未连上，用户离开直播间时mqtt引擎还未来开始投递（App）", -31007301);
                                    this.i.add(_figoutDetailCodeStep);
                                    return;
                                case 4082:
                                    _figoutDetailCodeStep.endTrace("原因：Mqtt已投递，用户离开直播间还在等mqtt的ack（App && broker）" + tip, -31007302);
                                    this.i.add(_figoutDetailCodeStep);
                                    return;
                                case 4083:
                                    _figoutDetailCodeStep.endTrace("原因：Mqtt已投递但未收到ack，用户离开直播间时等待offer ack等待已超时(App && broker) ", -31007303);
                                    this.i.add(_figoutDetailCodeStep);
                                    return;
                                default:
                                    _figoutDetailCodeStep.endTrace("原因：未收到来自设备的answer,用户离开直播间时还未等到固件的answer (固件&&broker)", -31007502);
                                    this.i.add(_figoutDetailCodeStep);
                                    return;
                            }
                        }
                    } else {
                        String str2 = result_desc;
                    }
                    CopyOnWriteArrayList<WebRtcLogStepBean> _candidateSteps = B(WebRtcLogStepBean.EXCHANGE_CENDIDITE_SEND_TO, preLinkCopySteps);
                    boolean sendHost = false;
                    boolean sendRelay = false;
                    boolean sendsrflx = false;
                    CopyOnWriteArrayList<WebRtcLogStepBean> copyOnWriteArrayList3 = _offerSteps;
                    int i2 = 0;
                    while (true) {
                        String tip2 = tip;
                        LDSBaseMqttService _mqttService2 = _mqttService;
                        CopyOnWriteArrayList<WebRtcLogStepBean> _iceConfigSteps2 = _iceConfigSteps;
                        String mqttConnectMsg2 = mqttConnectMsg;
                        if (i2 >= _candidateSteps.size()) {
                            break;
                        }
                        String _requestParams = _candidateSteps.get(i2).requestParams;
                        if (!TextUtils.isEmpty(_requestParams)) {
                            if (_requestParams.contains(SerializableCookie.HOST)) {
                                sendHost = true;
                            } else if (_requestParams.contains("srflx")) {
                                sendsrflx = true;
                            } else if (_requestParams.contains("relay")) {
                                sendRelay = true;
                            }
                        }
                        i2++;
                        tip = tip2;
                        _mqttService = _mqttService2;
                        _iceConfigSteps = _iceConfigSteps2;
                        mqttConnectMsg = mqttConnectMsg2;
                    }
                    if (!sendHost) {
                        _figoutDetailCodeStep.endTrace("原因：未发送过host类型的candidate给设备(App)", -310074012);
                        this.i.add(_figoutDetailCodeStep);
                    } else if (!sendRelay) {
                        _figoutDetailCodeStep.endTrace("原因：未发送过relay类型的candidate给设备(App)", -310074011);
                        this.i.add(_figoutDetailCodeStep);
                    } else if (!sendsrflx) {
                        _figoutDetailCodeStep.endTrace("原因：未发送过srflx类型的candidate给设备(App)", -310074013);
                        this.i.add(_figoutDetailCodeStep);
                    } else {
                        CopyOnWriteArrayList<WebRtcLogStepBean> _candidateReceiveSteps = B(WebRtcLogStepBean.EXCHANGE_CENDIDITE_RECEIVED, preLinkSteps);
                        boolean receiveHost = false;
                        boolean receiveRelay = false;
                        boolean receivesrflx = false;
                        CopyOnWriteArrayList<WebRtcLogStepBean> copyOnWriteArrayList4 = _candidateSteps;
                        int i3 = 0;
                        while (true) {
                            boolean sendHost2 = sendHost;
                            if (i3 >= _candidateReceiveSteps.size()) {
                                break;
                            }
                            String _requestParams2 = _candidateReceiveSteps.get(i3).responseParams;
                            if (!TextUtils.isEmpty(_requestParams2)) {
                                if (_requestParams2.contains(SerializableCookie.HOST)) {
                                    receiveHost = true;
                                } else if (_requestParams2.contains("srflx")) {
                                    receivesrflx = true;
                                } else if (_requestParams2.contains("relay")) {
                                    receiveRelay = true;
                                }
                            }
                            i3++;
                            sendHost = sendHost2;
                        }
                        if (!receiveHost) {
                            _figoutDetailCodeStep.endTrace("原因：未收到固件的host类型的candidate (固件)", -310076012);
                            this.i.add(_figoutDetailCodeStep);
                        } else if (!receiveRelay) {
                            _figoutDetailCodeStep.endTrace("原因：未收到固件的relay类型的candidate (固件)", -310076011);
                            this.i.add(_figoutDetailCodeStep);
                        } else if (!receivesrflx) {
                            _figoutDetailCodeStep.endTrace("原因：未收到固件的srflx类型的candidate (固件)", -310076013);
                            this.i.add(_figoutDetailCodeStep);
                        } else {
                            CopyOnWriteArrayList<WebRtcLogStepBean> _getIceConfigStep = B("GET_ICECONFIG", preLinkCopySteps);
                            if (_getIceConfigStep.size() != 0 && _getIceConfigStep.get(0).code != 200) {
                                _figoutDetailCodeStep.endTrace("原因：获取iceConfig失败", -31007001);
                                this.i.add(_figoutDetailCodeStep);
                            }
                        }
                    }
                }
            } else if (System.currentTimeMillis() - startUseLiveRoom._endTraceTimeSpan < this.q) {
                WebRtcLogStepBean _checkStayInLiveRoom2 = new WebRtcLogStepBean(WebRtcLogStepBean.FIGOUT_AND_ANLYZE_WEBRTC_DETAIL_CODE, -31005901);
                _checkStayInLiveRoom2.endTrace("原因：等待拉流时间太短(小于3S)", -31005901);
                this.i.add(_checkStayInLiveRoom2);
            } else if (B(WebRtcLogStepBean.DEVICE_NET_WORK_CHANGED_STEP, preLinkSteps).size() != 0) {
                WebRtcLogStepBean _figoutDetailCodeStep2 = new WebRtcLogStepBean(WebRtcLogStepBean.FIGOUT_AND_ANLYZE_WEBRTC_DETAIL_CODE, -31006002);
                _figoutDetailCodeStep2.endTrace("原因：用户手机网络发生切换拉流失败", -31006002);
                _figoutDetailCodeStep2.endTrace("原因：用户手机网络发生切换拉流失败", -31006002);
                this.i.add(_figoutDetailCodeStep2);
            } else {
                WebRtcLogStepBean _waitingStreamComing2 = new WebRtcLogStepBean(WebRtcLogStepBean.FIGOUT_AND_ANLYZE_WEBRTC_DETAIL_CODE, -31005902);
                _waitingStreamComing2.endTrace("原因：用户等待出流过程中提前离开直播间", -31005902);
                this.i.add(_waitingStreamComing2);
            }
        }
    }

    public class b extends TypeToken<CopyOnWriteArrayList<WebRtcLogStepBean>> {
        b() {
        }
    }

    public boolean n() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10191, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (y(-310074011) != null) {
            return false;
        }
        return true;
    }

    private WebRtcLogStepBean y(int targetCode) {
        Object[] objArr = {new Integer(targetCode)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 10192, new Class[]{Integer.TYPE}, WebRtcLogStepBean.class);
        if (proxy.isSupported) {
            return (WebRtcLogStepBean) proxy.result;
        }
        for (int i2 = 0; i2 < this.i.size(); i2++) {
            if (this.i.get(i2).code == targetCode) {
                return this.i.get(i2);
            }
        }
        return null;
    }

    public CopyOnWriteArrayList<WebRtcLogStepBean> A(String stepName) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{stepName}, this, changeQuickRedirect, false, 10193, new Class[]{String.class}, CopyOnWriteArrayList.class);
        if (proxy.isSupported) {
            return (CopyOnWriteArrayList) proxy.result;
        }
        CopyOnWriteArrayList<WebRtcLogStepBean> _tempConfig = new CopyOnWriteArrayList<>();
        for (int i2 = 0; i2 < this.i.size(); i2++) {
            WebRtcLogStepBean _item = this.i.get(i2);
            if (_item != null && stepName.equals(_item.getStep())) {
                _tempConfig.add(_item);
            }
        }
        return _tempConfig;
    }

    private WebRtcLogStepBean z(String stepName) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{stepName}, this, changeQuickRedirect, false, 10194, new Class[]{String.class}, WebRtcLogStepBean.class);
        if (proxy.isSupported) {
            return (WebRtcLogStepBean) proxy.result;
        }
        CopyOnWriteArrayList<WebRtcLogStepBean> _tempConfig = A(stepName);
        if (_tempConfig == null || _tempConfig.size() == 0) {
            return null;
        }
        return _tempConfig.get(0);
    }

    private CopyOnWriteArrayList<WebRtcLogStepBean> B(String stepName, CopyOnWriteArrayList<WebRtcLogStepBean> targetSteps) {
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{stepName, targetSteps}, this, changeQuickRedirect2, false, 10195, new Class[]{String.class, CopyOnWriteArrayList.class}, CopyOnWriteArrayList.class);
        if (proxy.isSupported) {
            return (CopyOnWriteArrayList) proxy.result;
        }
        CopyOnWriteArrayList<WebRtcLogStepBean> _tempConfig = new CopyOnWriteArrayList<>();
        for (int i2 = 0; i2 < targetSteps.size(); i2++) {
            WebRtcLogStepBean _item = targetSteps.get(i2);
            if (_item != null && stepName.equals(_item.getStep())) {
                _tempConfig.add(_item);
            }
        }
        return _tempConfig;
    }

    private long k(CopyOnWriteArrayList<WebRtcLogStepBean> _tempConfig) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{_tempConfig}, this, changeQuickRedirect, false, 10196, new Class[]{CopyOnWriteArrayList.class}, Long.TYPE);
        if (proxy.isSupported) {
            return ((Long) proxy.result).longValue();
        }
        if (_tempConfig.size() == 0) {
            return 0;
        }
        if (_tempConfig.size() == 1) {
            return _tempConfig.get(0).getDuration();
        }
        long maxValue = _tempConfig.get(0)._endTraceTimeSpan;
        for (int i2 = 0; i2 < _tempConfig.size(); i2++) {
            if (_tempConfig.get(i2)._endTraceTimeSpan > maxValue) {
                maxValue = _tempConfig.get(i2)._endTraceTimeSpan;
            }
        }
        return maxValue - _tempConfig.get(0)._beginTraceTimeSpan;
    }

    private long g() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10197, new Class[0], Long.TYPE);
        if (proxy.isSupported) {
            return ((Long) proxy.result).longValue();
        }
        return k(A("GET_ICECONFIG"));
    }

    private long i() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10198, new Class[0], Long.TYPE);
        if (proxy.isSupported) {
            return ((Long) proxy.result).longValue();
        }
        return k(A(WebRtcLogStepBean.EXCHANGE_SDP));
    }

    private long e() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10199, new Class[0], Long.TYPE);
        if (proxy.isSupported) {
            return ((Long) proxy.result).longValue();
        }
        return k(A(WebRtcLogStepBean.EXCHANGE_CENDIDITE_SEND_TO)) + k(A(WebRtcLogStepBean.EXCHANGE_CENDIDITE_RECEIVED));
    }

    private long h() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10200, new Class[0], Long.TYPE);
        if (proxy.isSupported) {
            return ((Long) proxy.result).longValue();
        }
        return k(A(WebRtcLogStepBean.PEER_CONNECTION_CONNECT));
    }

    private long l() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10201, new Class[0], Long.TYPE);
        if (proxy.isSupported) {
            return ((Long) proxy.result).longValue();
        }
        return k(this.i);
    }

    private long j() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10202, new Class[0], Long.TYPE);
        if (proxy.isSupported) {
            return ((Long) proxy.result).longValue();
        }
        return k(A("SIGNAL_CONNECT"));
    }

    private int f() {
        int detailcode;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10203, new Class[0], Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        int code = 200;
        int i2 = 0;
        while (true) {
            if (i2 >= this.i.size()) {
                break;
            } else if (this.i.get(i2).code < 0) {
                code = this.i.get(i2).code;
                break;
            } else {
                i2++;
            }
        }
        if (code != 200) {
            int nativeMqttOnline = -1;
            LDSBaseMqttService _mqttService = (LDSBaseMqttService) com.alibaba.android.arouter.launcher.a.c().g(LDSBaseMqttService.class);
            if (_mqttService != null) {
                nativeMqttOnline = _mqttService.getMqttConnectStatue();
            }
            HttpReportService logReport = (HttpReportService) com.alibaba.android.arouter.launcher.a.c().g(HttpReportService.class);
            if (logReport != null) {
                String logInfoDetail = logReport.getNetWorkInfoDetail();
                if (!logInfoDetail.contains("enableNetworkFlag=true") && !logInfoDetail.contains("未检测")) {
                    WebRtcLogStepBean _stepCheckNetWork = new WebRtcLogStepBean(WebRtcLogStepBean.CHECK_NET_WORK_STEP, -31006001);
                    _stepCheckNetWork.endTrace("无网络 mqttOnline=" + nativeMqttOnline, -31006001);
                    this.i.add(_stepCheckNetWork);
                    return -31006001;
                }
            }
            if (this.m.onlineStatue == 0) {
                WebRtcLogStepBean _checkDeviceOnlineStep = new WebRtcLogStepBean(WebRtcLogStepBean.DEVICE_ONLINE_CHECK_STEP, -31006101);
                _checkDeviceOnlineStep.endTrace("设备离线", -31006101);
                this.i.add(_checkDeviceOnlineStep);
                return -31006101;
            } else if (g.equals(this.n) && l() < GroupCtrlAdapter.RETRY_TIMEOUT) {
                WebRtcLogStepBean _checkStayInLiveRoom = new WebRtcLogStepBean(WebRtcLogStepBean.CHECK_IN_WAITING_IN_LEAVING_ROOM_STEP, -31005901);
                _checkStayInLiveRoom.endTrace("原因：等待拉流时间太短(小于3S)", -31005901);
                this.i.add(_checkStayInLiveRoom);
                return -31005901;
            }
        }
        if (f.equals(this.n)) {
            CopyOnWriteArrayList<WebRtcLogStepBean> _connectPreStep = A(WebRtcLogStepBean.PEER_CONNECTION_CONNECT);
            if ((_connectPreStep.size() == 0 || !(_connectPreStep.size() == 0 || _connectPreStep.get(0).code == 200)) && (detailcode = P()) != 0) {
                return detailcode;
            }
        }
        return code;
    }

    private int P() {
        WebRtcLogStepBean getIceConfigStep;
        int i2;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10204, new Class[0], Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        try {
            WebRtcLogStepBean _figoutDetailCodeStep = new WebRtcLogStepBean(WebRtcLogStepBean.FIGOUT_AND_ANLYZE_WEBRTC_DETAIL_CODE, 200);
            CopyOnWriteArrayList<WebRtcLogStepBean> _netWorkChanged = A(WebRtcLogStepBean.DEVICE_NET_WORK_CHANGED_STEP);
            if (_netWorkChanged.size() != 0) {
                _figoutDetailCodeStep.endTrace("原因：用户手机网络发生切换拉流失败", -31006002);
                this.i.add(_figoutDetailCodeStep);
                return -31006002;
            }
            CopyOnWriteArrayList<WebRtcLogStepBean> _offerSteps = A(WebRtcLogStepBean.EXCHANGE_SDP);
            LDSBaseMqttService _mqttService = (LDSBaseMqttService) com.alibaba.android.arouter.launcher.a.c().g(LDSBaseMqttService.class);
            String tip = "";
            if (_mqttService != null) {
                tip = _mqttService.getMqttConnectStatue() == 1 ? "mqtt在线" : "mqtt离线";
            }
            if (this.m.onlineStatue == 0) {
                _figoutDetailCodeStep.endTrace("原因：摄像机设备已离线", -31006101);
                this.i.add(_figoutDetailCodeStep);
                return -31006101;
            }
            CopyOnWriteArrayList<WebRtcLogStepBean> _iceConfigSteps = A("GET_ICECONFIG");
            if (_iceConfigSteps.size() == 0 || (i2 = getIceConfigStep.code) >= 0) {
                if (_offerSteps.size() != 0) {
                    WebRtcLogStepBean _stepOffer = _offerSteps.get(0);
                    if (TextUtils.isEmpty(_stepOffer.responseParams)) {
                        _figoutDetailCodeStep.endTrace("原因：未收到来自设备的answer，业务等待时间已超时(固件&&broker)", -31007502);
                        this.i.add(_figoutDetailCodeStep);
                        return -31007502;
                    }
                    int i3 = _stepOffer.code;
                    if (i3 != 200) {
                        switch (i3) {
                            case AckBean.LIVE_SD_MAX_CONNECT_ERROR:
                                _figoutDetailCodeStep.endTrace("原因：" + _stepOffer.desc, AckBean.LIVE_SD_MAX_CONNECT_ERROR);
                                this.i.add(_figoutDetailCodeStep);
                                return AckBean.LIVE_SD_MAX_CONNECT_ERROR;
                            case AckBean.WEBRTC_ERROR_EN_RTC_ERR_CODE_SESSION_EXCEED:
                                _figoutDetailCodeStep.endTrace("原因：" + _stepOffer.desc, AckBean.WEBRTC_ERROR_EN_RTC_ERR_CODE_SESSION_EXCEED);
                                this.i.add(_figoutDetailCodeStep);
                                return AckBean.WEBRTC_ERROR_EN_RTC_ERR_CODE_SESSION_EXCEED;
                            case 4081:
                                _figoutDetailCodeStep.endTrace("原因：Mqtt还未连上，消息待投递，业务等待时间已超时(App)", -31007301);
                                this.i.add(_figoutDetailCodeStep);
                                return -31007301;
                            case 4082:
                                _figoutDetailCodeStep.endTrace("原因：Mqtt已投递，业务等待时间已超时(App)" + tip, -31007302);
                                this.i.add(_figoutDetailCodeStep);
                                return -31007302;
                            case 4083:
                                _figoutDetailCodeStep.endTrace("原因：Mqtt已投递但未收到ack，业务等待时间已超时(App&&broker)", -31007303);
                                this.i.add(_figoutDetailCodeStep);
                                return -31007303;
                            default:
                                _figoutDetailCodeStep.endTrace("原因：未收到来自设备的answer，业务等待时间已超时(固件&&broker)", -31007502);
                                this.i.add(_figoutDetailCodeStep);
                                return -31007502;
                        }
                    }
                }
                WebRtcLogStepBean _sdpAnswerCheck = z(WebRtcLogStepBean.EXCHANGE_SDP_CHECKING);
                if (_sdpAnswerCheck != null) {
                    _figoutDetailCodeStep.endTrace("原因：" + _sdpAnswerCheck.desc, _sdpAnswerCheck.code);
                    this.i.add(_figoutDetailCodeStep);
                    return _sdpAnswerCheck.code;
                }
                WebRtcLogStepBean _candidateChecking = z(WebRtcLogStepBean.EXCHANGE_CENDIDITE_CHECKING);
                if (_candidateChecking != null) {
                    _figoutDetailCodeStep.endTrace("原因：" + _candidateChecking.desc, _candidateChecking.code);
                    this.i.add(_figoutDetailCodeStep);
                    return _candidateChecking.code;
                }
                CopyOnWriteArrayList<WebRtcLogStepBean> _candidateSteps = A(WebRtcLogStepBean.EXCHANGE_CENDIDITE_SEND_TO);
                boolean sendHost = false;
                boolean sendRelay = false;
                boolean sendsrflx = false;
                int i4 = 0;
                while (true) {
                    CopyOnWriteArrayList<WebRtcLogStepBean> _netWorkChanged2 = _netWorkChanged;
                    CopyOnWriteArrayList<WebRtcLogStepBean> _offerSteps2 = _offerSteps;
                    LDSBaseMqttService _mqttService2 = _mqttService;
                    if (i4 >= _candidateSteps.size()) {
                        break;
                    }
                    String _requestParams = _candidateSteps.get(i4).requestParams;
                    if (!TextUtils.isEmpty(_requestParams)) {
                        if (_requestParams.contains(SerializableCookie.HOST)) {
                            sendHost = true;
                        } else if (_requestParams.contains("srflx")) {
                            sendsrflx = true;
                        } else if (_requestParams.contains("relay")) {
                            sendRelay = true;
                        }
                    }
                    i4++;
                    _netWorkChanged = _netWorkChanged2;
                    _offerSteps = _offerSteps2;
                    _mqttService = _mqttService2;
                }
                if (!sendHost) {
                    _figoutDetailCodeStep.endTrace("原因：未发送过host类型的candidate给设备(App)", -310074012);
                    this.i.add(_figoutDetailCodeStep);
                    return -310074012;
                } else if (!sendRelay) {
                    _figoutDetailCodeStep.endTrace("原因：未发送过relay类型的candidate给设备(App)", -310074011);
                    this.i.add(_figoutDetailCodeStep);
                    return -310074011;
                } else if (!sendsrflx) {
                    _figoutDetailCodeStep.endTrace("原因：未发送过srflx类型的candidate给设备(App)", -310074013);
                    this.i.add(_figoutDetailCodeStep);
                    return -310074013;
                } else {
                    CopyOnWriteArrayList<WebRtcLogStepBean> _candidateReceiveSteps = A(WebRtcLogStepBean.EXCHANGE_CENDIDITE_RECEIVED);
                    boolean receiveHost = false;
                    boolean receiveRelay = false;
                    boolean receivesrflx = false;
                    String str = tip;
                    int i5 = 0;
                    while (true) {
                        CopyOnWriteArrayList<WebRtcLogStepBean> _iceConfigSteps2 = _iceConfigSteps;
                        if (i5 >= _candidateReceiveSteps.size()) {
                            break;
                        }
                        String _requestParams2 = _candidateReceiveSteps.get(i5).responseParams;
                        if (!TextUtils.isEmpty(_requestParams2)) {
                            if (_requestParams2.contains(SerializableCookie.HOST)) {
                                receiveHost = true;
                            } else if (_requestParams2.contains("srflx")) {
                                receivesrflx = true;
                            } else if (_requestParams2.contains("relay")) {
                                receiveRelay = true;
                            }
                        }
                        i5++;
                        _iceConfigSteps = _iceConfigSteps2;
                    }
                    if (!receiveHost) {
                        _figoutDetailCodeStep.endTrace("原因：未收到固件host类型的candidate (固件)", -310076012);
                        this.i.add(_figoutDetailCodeStep);
                        return -310076012;
                    } else if (!receiveRelay) {
                        _figoutDetailCodeStep.endTrace("原因：未收到固件relay类型的candidate (固件)", -310076011);
                        this.i.add(_figoutDetailCodeStep);
                        return -310076011;
                    } else if (!receivesrflx) {
                        _figoutDetailCodeStep.endTrace("原因：未收到固件srflx类型的candidate (固件)", -310076013);
                        this.i.add(_figoutDetailCodeStep);
                        return -310076013;
                    } else {
                        CopyOnWriteArrayList<WebRtcLogStepBean> _getIceConfigStep = A("GET_ICECONFIG");
                        if (_getIceConfigStep.size() == 0 || _getIceConfigStep.get(0).code == 200) {
                            return 0;
                        }
                        _figoutDetailCodeStep.endTrace("原因：获取iceConfig失败", -31007001);
                        this.i.add(_figoutDetailCodeStep);
                        return -31007001;
                    }
                }
            } else {
                _figoutDetailCodeStep.endTrace("原因：获取iceConfig 异常", i2);
                this.i.add(_figoutDetailCodeStep);
                return (getIceConfigStep = _iceConfigSteps.get(0)).code;
            }
        } catch (Exception e2) {
            timber.log.a.g("webrtclogV3").c("toGetMoreDetailInfoCode 异常=" + e2.toString(), new Object[0]);
            e2.printStackTrace();
            return 0;
        }
    }

    private String u() {
        return this.k;
    }

    private WebRtcLogStepBean O() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10205, new Class[0], WebRtcLogStepBean.class);
        if (proxy.isSupported) {
            return (WebRtcLogStepBean) proxy.result;
        }
        WebRtcLogStepBean _errorStep = null;
        for (int i2 = 0; i2 < this.i.size(); i2++) {
            if (this.i.get(i2).code < 0) {
                _errorStep = this.i.get(i2);
            }
        }
        return _errorStep;
    }

    private WebRtcLogStepBean N() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10206, new Class[0], WebRtcLogStepBean.class);
        if (proxy.isSupported) {
            return (WebRtcLogStepBean) proxy.result;
        }
        WebRtcLogStepBean _errorStep = new WebRtcLogStepBean("TempStep", 200);
        if (this.i.size() == 0) {
            return _errorStep;
        }
        CopyOnWriteArrayList<WebRtcLogStepBean> copyOnWriteArrayList = this.i;
        return copyOnWriteArrayList.get(copyOnWriteArrayList.size() - 1);
    }

    private void b() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10207, new Class[0], Void.TYPE).isSupported) {
            TempIpcDeviceBean ipcDeviceBean = new TempIpcDeviceBean();
            IpcService _serviceOfIpc = (IpcService) com.alibaba.android.arouter.launcher.a.c().g(IpcService.class);
            this.m = ipcDeviceBean;
            if (_serviceOfIpc != null) {
                this.m = (TempIpcDeviceBean) new Gson().fromJson(_serviceOfIpc.getIPCDeviceInfoByDeviceId(this.k).toString(), TempIpcDeviceBean.class);
            }
        }
    }

    public boolean m(String stepName) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{stepName}, this, changeQuickRedirect, false, 10208, new Class[]{String.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        return A(stepName).size() != 0;
    }

    public boolean p(String stepName) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{stepName}, this, changeQuickRedirect, false, 10209, new Class[]{String.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        return A(stepName).size() != 0;
    }

    private int E(TempIpcDeviceBean ipcDeviceBean) {
        boolean isLowerPower = false;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{ipcDeviceBean}, this, changeQuickRedirect, false, 10210, new Class[]{TempIpcDeviceBean.class}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        String str = ipcDeviceBean.modelId;
        if (str != null && (str.contains("IPC.A001108") || ipcDeviceBean.modelId.contains("IPC.A001360") || ipcDeviceBean.modelId.contains("LK.IPC.A001513"))) {
            isLowerPower = true;
        }
        if (isLowerPower) {
            return 2;
        }
        return 1;
    }

    private void a(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 10211, new Class[]{String.class}, Void.TYPE).isSupported) {
            a.b g2 = timber.log.a.g("WebRtc");
            g2.h(msg + "  peerId=" + this.l + "  event=" + this.n, new Object[0]);
        }
    }
}
