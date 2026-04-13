package com.didichuxing.doraemonkit.datapick;

import com.blankj.utilcode.util.d;
import com.blankj.utilcode.util.d0;
import com.blankj.utilcode.util.g;
import com.didichuxing.doraemonkit.constant.DokitConstant;
import java.util.List;
import java.util.Locale;

public class DataPickBean {
    private String appId = d.c();
    private String appName = d.a();
    private String dokitVersion = "3.2.0";
    private List<EventBean> events;
    private String language = Locale.getDefault().getDisplayLanguage();
    private String pId = DokitConstant.PRODUCT_ID;
    private String phoneMode = g.j();
    private String platform = "Android";
    private String systemVersion = g.k();
    private String time = ("" + d0.b());

    DataPickBean() {
    }

    /* access modifiers changed from: package-private */
    public void setEvents(List<EventBean> events2) {
        this.events = events2;
    }

    public static class EventBean {
        private String eventName;
        private String time = ("" + d0.b());

        EventBean(String eventName2) {
            this.eventName = eventName2;
        }

        /* access modifiers changed from: package-private */
        public String getTime() {
            return this.time;
        }

        public String toString() {
            return "EventBean{, eventName='" + this.eventName + '\'' + ", time=" + this.time + '}';
        }
    }

    public String toString() {
        return "DataPickBean{platform='" + this.platform + '\'' + ", pId='" + this.pId + '\'' + ", time='" + this.time + '\'' + ", phoneMode='" + this.phoneMode + '\'' + ", systemVersion='" + this.systemVersion + '\'' + ", appName='" + this.appName + '\'' + ", appId='" + this.appId + '\'' + ", dokitVersion='" + this.dokitVersion + '\'' + ", language='" + this.language + '\'' + ", events=" + this.events + '}';
    }
}
