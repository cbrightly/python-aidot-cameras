package com.leedarson.smartcamera.kvswebrtc.utils;

import com.didichuxing.doraemonkit.widget.JustifyTextView;
import com.google.gson.Gson;
import com.leedarson.smartcamera.kvswebrtc.beans.WebRtcVideoTrackReportDetailBean;
import com.leedarson.smartcamera.kvswebrtc.utils.WebRtcRealTimeReportBean;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.v;
import kotlin.collections.y;
import kotlin.jvm.internal.k;
import kotlin.text.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.webrtc.RTCStats;
import org.webrtc.RTCStatsReport;

/* compiled from: LargeLogCollectHelperV2.kt */
public final class e {
    public static ChangeQuickRedirect changeQuickRedirect;
    @NotNull
    private final String a = "LargeLogCollectHelper";
    private final int b = 20;
    private final int c;
    private final int d = 1;
    private final int e = 2;
    @NotNull
    private final a f = new a();
    @NotNull
    private final CopyOnWriteArrayList<WebRtcRealTimeReportBean> g = new CopyOnWriteArrayList<>();
    @NotNull
    private final CopyOnWriteArrayList<WebRtcRealTimeReportBean> h = new CopyOnWriteArrayList<>();
    @NotNull
    private final CopyOnWriteArrayList<WebRtcRealTimeReportBean> i = new CopyOnWriteArrayList<>();
    @NotNull
    private final CopyOnWriteArrayList<WebRtcRealTimeReportBean> j = new CopyOnWriteArrayList<>();
    @NotNull
    private final CopyOnWriteArrayList<WebRtcRealTimeReportBean> k = new CopyOnWriteArrayList<>();
    @NotNull
    private final HashMap<String, Long> l = new HashMap<>();
    @NotNull
    private String m = "";
    private final int n = 200;
    @Nullable
    private b o;
    @NotNull
    private WebRtcVideoTrackReportDetailBean p = new WebRtcVideoTrackReportDetailBean();
    private double q;

    /* compiled from: LargeLogCollectHelperV2.kt */
    public interface b {
        void a(double d);
    }

    /* compiled from: LargeLogCollectHelperV2.kt */
    public final /* synthetic */ class c {
        public static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[WebRtcRealTimeReportBean.MSG_TYPE.values().length];
            iArr[WebRtcRealTimeReportBean.MSG_TYPE.STUNPING.ordinal()] = 1;
            iArr[WebRtcRealTimeReportBean.MSG_TYPE.RTT.ordinal()] = 2;
            iArr[WebRtcRealTimeReportBean.MSG_TYPE.HEART_BEAT.ordinal()] = 3;
            iArr[WebRtcRealTimeReportBean.MSG_TYPE.FRAMEREPORT.ordinal()] = 4;
            iArr[WebRtcRealTimeReportBean.MSG_TYPE.NACK.ordinal()] = 5;
            a = iArr;
        }
    }

    public final void g(@NotNull String log) {
        if (!PatchProxy.proxy(new Object[]{log}, this, changeQuickRedirect, false, 10115, new Class[]{String.class}, Void.TYPE).isSupported) {
            k.e(log, "log");
            b(log, WebRtcRealTimeReportBean.MSG_TYPE.STUNPING);
        }
    }

    public final void f(@NotNull String log) {
        if (!PatchProxy.proxy(new Object[]{log}, this, changeQuickRedirect, false, 10116, new Class[]{String.class}, Void.TYPE).isSupported) {
            k.e(log, "log");
            b(log, WebRtcRealTimeReportBean.MSG_TYPE.RTT);
        }
    }

    public final void c(@NotNull String log) {
        if (!PatchProxy.proxy(new Object[]{log}, this, changeQuickRedirect, false, 10117, new Class[]{String.class}, Void.TYPE).isSupported) {
            k.e(log, "log");
            b(log, WebRtcRealTimeReportBean.MSG_TYPE.HEART_BEAT);
        }
    }

    public final void d(@NotNull String str, @NotNull RTCStatsReport rTCStatsReport) {
        int diffTimeSecond;
        RTCStatsReport rtcReport;
        Iterator it;
        String log;
        if (!PatchProxy.proxy(new Object[]{str, rTCStatsReport}, this, changeQuickRedirect, false, 10118, new Class[]{String.class, RTCStatsReport.class}, Void.TYPE).isSupported) {
            RTCStatsReport rtcReport2 = rTCStatsReport;
            String log2 = str;
            k.e(log2, "log");
            k.e(rtcReport2, "rtcReport");
            Map $this$filterKeys$iv = rtcReport2.getStatsMap();
            k.d($this$filterKeys$iv, "rtcReport\n            .statsMap");
            LinkedHashMap result$iv = new LinkedHashMap();
            for (Map.Entry entry$iv : $this$filterKeys$iv.entrySet()) {
                String it2 = (String) entry$iv.getKey();
                k.d(it2, "it");
                if (x.S(it2, "RTCInboundRTPVideoStream", false, 2, (Object) null)) {
                    result$iv.put(entry$iv.getKey(), entry$iv.getValue());
                }
            }
            Map $this$forEach$iv = result$iv;
            StringBuilder resultBuilder = new StringBuilder();
            WebRtcVideoTrackReportDetailBean currentWebRtcQualityDetail = new WebRtcVideoTrackReportDetailBean();
            Iterator it3 = $this$forEach$iv.entrySet().iterator();
            while (it3.hasNext()) {
                Map.Entry item = (Map.Entry) it3.next();
                WebRtcVideoTrackReportDetailBean reportBean = (WebRtcVideoTrackReportDetailBean) new Gson().fromJson(new Gson().toJson((Object) ((RTCStats) item.getValue()).getMembers()), WebRtcVideoTrackReportDetailBean.class);
                if (reportBean == null) {
                    reportBean = null;
                } else {
                    WebRtcVideoTrackReportDetailBean $this$addFrameReportLog_u24lambda_u2d3_u24lambda_u2d1 = reportBean;
                    $this$addFrameReportLog_u24lambda_u2d3_u24lambda_u2d1.trackId = (String) item.getKey();
                    $this$addFrameReportLog_u24lambda_u2d3_u24lambda_u2d1.infoUpdateTime = System.currentTimeMillis();
                }
                if (reportBean == null) {
                    rtcReport = rtcReport2;
                    log = log2;
                    it = it3;
                } else {
                    WebRtcVideoTrackReportDetailBean $this$addFrameReportLog_u24lambda_u2d3_u24lambda_u2d2 = reportBean;
                    long j2 = $this$addFrameReportLog_u24lambda_u2d3_u24lambda_u2d2.framesReceived;
                    if (j2 != 0) {
                        rtcReport = rtcReport2;
                        log = log2;
                        it = it3;
                        currentWebRtcQualityDetail.framesDecoded += $this$addFrameReportLog_u24lambda_u2d3_u24lambda_u2d2.framesDecoded;
                        currentWebRtcQualityDetail.framesReceived += j2;
                        currentWebRtcQualityDetail.infoUpdateTime = $this$addFrameReportLog_u24lambda_u2d3_u24lambda_u2d2.infoUpdateTime;
                        currentWebRtcQualityDetail.keyFramesDecoded += $this$addFrameReportLog_u24lambda_u2d3_u24lambda_u2d2.keyFramesDecoded;
                        currentWebRtcQualityDetail.packetsLost += $this$addFrameReportLog_u24lambda_u2d3_u24lambda_u2d2.packetsLost;
                        currentWebRtcQualityDetail.packetsReceived += $this$addFrameReportLog_u24lambda_u2d3_u24lambda_u2d2.packetsReceived;
                        if (k.a(this.m, $this$addFrameReportLog_u24lambda_u2d3_u24lambda_u2d2.trackId)) {
                            currentWebRtcQualityDetail.decoderImplementation = $this$addFrameReportLog_u24lambda_u2d3_u24lambda_u2d2.decoderImplementation;
                        }
                        resultBuilder.append(new Gson().toJson((Object) reportBean));
                        String str2 = $this$addFrameReportLog_u24lambda_u2d3_u24lambda_u2d2.trackId;
                        k.d(str2, "trackId");
                        h(str2, $this$addFrameReportLog_u24lambda_u2d3_u24lambda_u2d2.framesReceived);
                    } else {
                        rtcReport = rtcReport2;
                        log = log2;
                        it = it3;
                    }
                }
                rtcReport2 = rtcReport;
                log2 = log;
                it3 = it;
            }
            String str3 = log2;
            String tip = "";
            try {
                WebRtcVideoTrackReportDetailBean webRtcVideoTrackReportDetailBean = this.p;
                if (webRtcVideoTrackReportDetailBean.framesReceived == 0) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(" webRtc通话质量反馈： 丢包率（");
                    sb.append(this.q * ((double) 100));
                    sb.append(" %） 丢包数:");
                    sb.append(currentWebRtcQualityDetail.packetsLost);
                    sb.append("  丟帧率：");
                    long j3 = currentWebRtcQualityDetail.framesReceived;
                    sb.append(j3 != 0 ? currentWebRtcQualityDetail.framesDropped / j3 : 0);
                    sb.append("  解码成功率：");
                    long j4 = currentWebRtcQualityDetail.framesReceived;
                    sb.append(j4 != 0 ? currentWebRtcQualityDetail.framesDropped / j4 : 0);
                    sb.append(" 丟帧数：");
                    sb.append(currentWebRtcQualityDetail.framesDropped);
                    sb.append("  解码帧数：");
                    sb.append(currentWebRtcQualityDetail.framesDecoded);
                    sb.append(" 收到帧数：");
                    sb.append(currentWebRtcQualityDetail.framesReceived);
                    sb.append(" 解码关键帧个数：");
                    sb.append(currentWebRtcQualityDetail.keyFramesDecoded);
                    sb.append(" 解码方式：");
                    sb.append(currentWebRtcQualityDetail.decoderImplementation);
                    sb.append(' ');
                    tip = sb.toString();
                    i(this.p, currentWebRtcQualityDetail);
                    this.p = currentWebRtcQualityDetail;
                } else {
                    long diffTimeMs = currentWebRtcQualityDetail.infoUpdateTime - webRtcVideoTrackReportDetailBean.infoUpdateTime;
                    if (diffTimeMs <= 1000) {
                        diffTimeSecond = 1;
                    } else {
                        diffTimeSecond = (int) Math.ceil((double) (diffTimeMs / ((long) 1000)));
                    }
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(" webRtc通话质量反馈1： 丢包率（");
                    long j5 = diffTimeMs;
                    sb2.append(this.q * ((double) 100));
                    sb2.append(" %）丢包数:");
                    sb2.append(currentWebRtcQualityDetail.packetsLost - this.p.packetsLost);
                    sb2.append("  丢包数(平均)：");
                    sb2.append((currentWebRtcQualityDetail.packetsLost - this.p.packetsLost) / ((long) diffTimeSecond));
                    sb2.append(" 丟帧数：");
                    sb2.append(currentWebRtcQualityDetail.framesDropped - this.p.framesDropped);
                    sb2.append("  丟帧数(平均)：");
                    sb2.append((currentWebRtcQualityDetail.framesDropped - this.p.framesDropped) / ((long) diffTimeSecond));
                    sb2.append("  解码成功帧数：");
                    sb2.append(currentWebRtcQualityDetail.framesDecoded - this.p.framesDecoded);
                    sb2.append(" 解码帧数(平均)：");
                    sb2.append((currentWebRtcQualityDetail.framesDecoded - this.p.framesDecoded) / ((long) diffTimeSecond));
                    sb2.append(" 收到帧数：");
                    sb2.append(currentWebRtcQualityDetail.framesReceived - this.p.framesReceived);
                    sb2.append(" 收到帧数(共计)： ");
                    sb2.append(currentWebRtcQualityDetail.framesReceived);
                    tip = sb2.toString();
                    "收到帧数(平均)：" + ((currentWebRtcQualityDetail.framesReceived - this.p.framesReceived) / ((long) diffTimeSecond)) + " 解码关键帧(共计)：" + currentWebRtcQualityDetail.keyFramesDecoded + " 解码方式：" + currentWebRtcQualityDetail.decoderImplementation;
                    i(this.p, currentWebRtcQualityDetail);
                    this.p = currentWebRtcQualityDetail;
                }
            } catch (Exception e2) {
                a(k.l("webRtc通话质量反馈 数据转化异常 e=", e2));
            }
            if (resultBuilder.length() > 0) {
                b(tip, WebRtcRealTimeReportBean.MSG_TYPE.FRAMEREPORT);
            }
        }
    }

    private final void h(String trackId, long receiveFrames) {
        if (!PatchProxy.proxy(new Object[]{trackId, new Long(receiveFrames)}, this, changeQuickRedirect, false, 10119, new Class[]{String.class, Long.TYPE}, Void.TYPE).isSupported) {
            if (this.l.containsKey(trackId)) {
                Long l2 = this.l.get(trackId);
                k.c(l2);
                k.d(l2, "trackIdReceivedFrames[trackId]!!");
                if (l2.longValue() < receiveFrames) {
                    this.m = trackId;
                    this.l.put(trackId, Long.valueOf(receiveFrames));
                    return;
                }
                return;
            }
            this.l.put(trackId, Long.valueOf(receiveFrames));
            if (receiveFrames != 0) {
                this.m = trackId;
            }
        }
    }

    public final void e(@NotNull String log) {
        if (!PatchProxy.proxy(new Object[]{log}, this, changeQuickRedirect, false, 10120, new Class[]{String.class}, Void.TYPE).isSupported) {
            k.e(log, "log");
            b(log, WebRtcRealTimeReportBean.MSG_TYPE.NACK);
        }
    }

    private final void b(String log, WebRtcRealTimeReportBean.MSG_TYPE type) {
        Class[] clsArr = {String.class, WebRtcRealTimeReportBean.MSG_TYPE.class};
        if (!PatchProxy.proxy(new Object[]{log, type}, this, changeQuickRedirect, false, 10121, clsArr, Void.TYPE).isSupported) {
            try {
                int tempMaxSize = this.b;
                CopyOnWriteArrayList eventCache = l(type);
                WebRtcRealTimeReportBean newRealTimeStep = new WebRtcRealTimeReportBean(log, type);
                if (eventCache.size() >= tempMaxSize) {
                    WebRtcRealTimeReportBean last = (WebRtcRealTimeReportBean) y.f0(eventCache);
                    if (last != null) {
                        if (newRealTimeStep.cts - last.cts > ((long) this.n)) {
                            eventCache.add(newRealTimeStep);
                            v.B(eventCache);
                        }
                    }
                } else if (((WebRtcRealTimeReportBean) y.f0(eventCache)) == null) {
                    eventCache.add(newRealTimeStep);
                } else {
                    WebRtcRealTimeReportBean last2 = (WebRtcRealTimeReportBean) y.f0(eventCache);
                    if (last2 != null) {
                        if (newRealTimeStep.cts - last2.cts > ((long) this.n)) {
                            eventCache.add(newRealTimeStep);
                        }
                    }
                }
            } catch (Exception e2) {
                a("(添加步骤发生易常)  " + type + JustifyTextView.TWO_CHINESE_BLANK + log);
            }
        }
    }

    private final CopyOnWriteArrayList<WebRtcRealTimeReportBean> l(WebRtcRealTimeReportBean.MSG_TYPE type) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{type}, this, changeQuickRedirect, false, 10122, new Class[]{WebRtcRealTimeReportBean.MSG_TYPE.class}, CopyOnWriteArrayList.class);
        if (proxy.isSupported) {
            return (CopyOnWriteArrayList) proxy.result;
        }
        switch (c.a[type.ordinal()]) {
            case 1:
                return this.g;
            case 2:
                return this.h;
            case 3:
                return this.i;
            case 4:
                return this.j;
            case 5:
                return this.k;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    private final String n(WebRtcRealTimeReportBean.MSG_TYPE type) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{type}, this, changeQuickRedirect, false, 10123, new Class[]{WebRtcRealTimeReportBean.MSG_TYPE.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        StringBuilder messageBuilder = new StringBuilder();
        Iterator<WebRtcRealTimeReportBean> it = l(type).iterator();
        while (it.hasNext()) {
            WebRtcRealTimeReportBean log = it.next();
            messageBuilder.append("\n " + log.ct + " ==> " + log.message);
        }
        String sb = messageBuilder.toString();
        k.d(sb, "messageBuilder.toString()");
        return sb;
    }

    @NotNull
    public final String k() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10124, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        StringBuilder sb = new StringBuilder();
        StringBuilder $this$getAllMessageDetail_u24lambda_u2d6 = sb;
        $this$getAllMessageDetail_u24lambda_u2d6.append(n(WebRtcRealTimeReportBean.MSG_TYPE.NACK));
        $this$getAllMessageDetail_u24lambda_u2d6.append("\n");
        $this$getAllMessageDetail_u24lambda_u2d6.append(n(WebRtcRealTimeReportBean.MSG_TYPE.FRAMEREPORT));
        $this$getAllMessageDetail_u24lambda_u2d6.append("\n");
        $this$getAllMessageDetail_u24lambda_u2d6.append(n(WebRtcRealTimeReportBean.MSG_TYPE.RTT));
        $this$getAllMessageDetail_u24lambda_u2d6.append("\n");
        $this$getAllMessageDetail_u24lambda_u2d6.append(n(WebRtcRealTimeReportBean.MSG_TYPE.STUNPING));
        $this$getAllMessageDetail_u24lambda_u2d6.append("\n");
        $this$getAllMessageDetail_u24lambda_u2d6.append(n(WebRtcRealTimeReportBean.MSG_TYPE.HEART_BEAT));
        $this$getAllMessageDetail_u24lambda_u2d6.append("\n");
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder().apply {\n            append(getMessageDetail(MSG_TYPE.NACK))\n            append(\"\\n\")\n            append(getMessageDetail(MSG_TYPE.FRAMEREPORT))\n            append(\"\\n\")\n            append(getMessageDetail(MSG_TYPE.RTT))\n            append(\"\\n\")\n            append(getMessageDetail(MSG_TYPE.STUNPING))\n            append(\"\\n\")\n            append(getMessageDetail(MSG_TYPE.HEART_BEAT))\n            append(\"\\n\")\n        }.toString()");
        return sb2;
    }

    public final void o() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10125, new Class[0], Void.TYPE).isSupported) {
            try {
                a("..........................本次直播数据汇总如下..........................");
                Iterator<WebRtcRealTimeReportBean> it = this.g.iterator();
                while (it.hasNext()) {
                    WebRtcRealTimeReportBean log = it.next();
                    a(k.l(log.ct, log.message));
                }
                Iterator<WebRtcRealTimeReportBean> it2 = this.k.iterator();
                while (it2.hasNext()) {
                    WebRtcRealTimeReportBean log2 = it2.next();
                    a(k.l(log2.ct, log2.message));
                }
                Iterator<WebRtcRealTimeReportBean> it3 = this.h.iterator();
                while (it3.hasNext()) {
                    WebRtcRealTimeReportBean log3 = it3.next();
                    a(k.l(log3.ct, log3.message));
                }
                Iterator<WebRtcRealTimeReportBean> it4 = this.i.iterator();
                while (it4.hasNext()) {
                    WebRtcRealTimeReportBean log4 = it4.next();
                    a(k.l(log4.ct, log4.message));
                }
                Iterator<WebRtcRealTimeReportBean> it5 = this.j.iterator();
                while (it5.hasNext()) {
                    WebRtcRealTimeReportBean log5 = it5.next();
                    a(k.l(log5.ct, log5.message));
                }
                a("..........................本次直播数据汇总如上..........................");
            } catch (Exception e2) {
                a(k.l("直播数据打印异常 exception=", e2));
            }
        }
    }

    private final void a(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 10126, new Class[]{String.class}, Void.TYPE).isSupported) {
            timber.log.a.g(this.a).a(msg, new Object[0]);
        }
    }

    public final void j() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10127, new Class[0], Void.TYPE).isSupported) {
            this.g.clear();
            this.h.clear();
            this.i.clear();
            this.j.clear();
        }
    }

    public final double m() {
        return this.q;
    }

    private final void i(WebRtcVideoTrackReportDetailBean pre, WebRtcVideoTrackReportDetailBean current) {
        int lostQuality;
        Class<WebRtcVideoTrackReportDetailBean> cls = WebRtcVideoTrackReportDetailBean.class;
        Class[] clsArr = {cls, cls};
        if (!PatchProxy.proxy(new Object[]{pre, current}, this, changeQuickRedirect, false, 10128, clsArr, Void.TYPE).isSupported) {
            long diffLostPacketCount = current.packetsLost - pre.packetsLost;
            long diffReceivePacketCount = current.packetsReceived - pre.packetsReceived;
            if (diffReceivePacketCount != 0) {
                double temp = (((double) diffLostPacketCount) * 1.0d) / ((double) diffReceivePacketCount);
                if (temp >= 0.0d) {
                    this.q = temp;
                    a("lost packet " + diffLostPacketCount + " / " + diffReceivePacketCount + " = " + this.q + " %");
                } else {
                    return;
                }
            }
            double d2 = this.q;
            if (d2 <= 0.02d) {
                lostQuality = this.c;
            } else if (d2 <= 0.02d || d2 > 0.05d) {
                lostQuality = this.e;
            } else {
                lostQuality = this.d;
            }
            if (lostQuality == this.e) {
                this.f.a().add(Long.valueOf(System.currentTimeMillis()));
                if (this.f.a().size() >= 2) {
                    b it = this.o;
                    if (it != null) {
                        it.a(m());
                    }
                    this.f.a().clear();
                    return;
                }
                return;
            }
            this.f.a().clear();
        }
    }

    public final void p(@Nullable b lostHandler) {
        this.o = lostHandler;
    }

    /* compiled from: LargeLogCollectHelperV2.kt */
    public static final class a {
        public static ChangeQuickRedirect changeQuickRedirect;
        @NotNull
        private final CopyOnWriteArrayList<Long> a = new CopyOnWriteArrayList<>();

        @NotNull
        public final CopyOnWriteArrayList<Long> a() {
            return this.a;
        }
    }
}
