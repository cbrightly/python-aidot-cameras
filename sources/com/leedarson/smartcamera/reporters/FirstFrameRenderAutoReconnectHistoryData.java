package com.leedarson.smartcamera.reporters;

import com.google.gson.Gson;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.ArrayList;

public class FirstFrameRenderAutoReconnectHistoryData {
    public static ChangeQuickRedirect changeQuickRedirect;
    private ArrayList<CauseReasonDescriptionBean> a = new ArrayList<>();

    public void d(WebRtcReporterV3 historyDatas) {
        if (!PatchProxy.proxy(new Object[]{historyDatas}, this, changeQuickRedirect, false, 10160, new Class[]{WebRtcReporterV3.class}, Void.TYPE).isSupported) {
            int lastIndex = 0;
            if (historyDatas.r().size() != 0) {
                lastIndex = historyDatas.r().size() - 1;
            }
            Gson gson = new Gson();
            WebRtcLogStepBean anyLizeStepBean = historyDatas.r().get(lastIndex);
            CauseReasonDescriptionBean _reason = new CauseReasonDescriptionBean();
            _reason.causeReason = anyLizeStepBean.desc;
            _reason.code = anyLizeStepBean.getCode();
            _reason.peerId = historyDatas.l;
            _reason.extroDatas = gson.toJson((Object) historyDatas.r());
            if (!historyDatas.l.contains("已回收(peerId为空,自动重建)")) {
                this.a.add(_reason);
            }
        }
    }

    public int c() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10161, new Class[0], Integer.TYPE);
        return proxy.isSupported ? ((Integer) proxy.result).intValue() : this.a.size();
    }

    public String b() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10162, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        StringBuilder strBuilders = new StringBuilder();
        for (int i = 0; i < this.a.size(); i++) {
            if (!this.a.get(i).peerId.contains("已回收(peerId为空,自动重建)")) {
                strBuilders.append(this.a.get(i).peerId + ",");
            }
        }
        return strBuilders.toString();
    }

    public String a() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10163, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return new Gson().toJson((Object) this.a);
    }

    public class CauseReasonDescriptionBean {
        public String causeReason = "";
        public int code = 0;
        public String extroDatas = "";
        public String peerId = "";

        CauseReasonDescriptionBean() {
        }
    }
}
