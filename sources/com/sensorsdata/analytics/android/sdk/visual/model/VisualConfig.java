package com.sensorsdata.analytics.android.sdk.visual.model;

import java.util.List;

public class VisualConfig {
    public String appId;
    public List<VisualPropertiesConfig> events;
    public String os;
    public String project;
    public String version;

    public static class VisualPropertiesConfig {
        public VisualEvent event;
        public String eventName;
        public String eventType;
        public List<VisualProperty> properties;

        public String toString() {
            return "VisualPropertiesConfig{eventName='" + this.eventName + '\'' + ", eventType='" + this.eventType + '\'' + ", event=" + this.event + ", properties=" + this.properties + '}';
        }
    }

    public static class VisualEvent {
        public String elementContent;
        public String elementPath;
        public String elementPosition;
        public boolean isH5;
        public boolean limitElementContent;
        public boolean limitElementPosition;
        public String screenName;

        public String toString() {
            return "VisualEvent{elementPath='" + this.elementPath + '\'' + ", elementPosition='" + this.elementPosition + '\'' + ", elementContent='" + this.elementContent + '\'' + ", screenName='" + this.screenName + '\'' + ", limitElementPosition=" + this.limitElementPosition + ", limitElementContent=" + this.limitElementContent + ", isH5=" + this.isH5 + '}';
        }
    }

    public static class VisualProperty {
        public String elementPath;
        public String elementPosition;
        public boolean isH5;
        public String name;
        public String regular;
        public String screenName;
        public String type;
        public String webViewElementPath;

        public String toString() {
            return "VisualProperty{elementPath='" + this.elementPath + '\'' + ", elementPosition='" + this.elementPosition + '\'' + ", screenName='" + this.screenName + '\'' + ", name='" + this.name + '\'' + ", regular='" + this.regular + '\'' + ", type='" + this.type + '\'' + ", isH5=" + this.isH5 + ", webViewElementPath='" + this.webViewElementPath + '\'' + '}';
        }
    }

    public String toString() {
        return "VisualConfig{appId='" + this.appId + '\'' + ", os='" + this.os + '\'' + ", project='" + this.project + '\'' + ", version='" + this.version + '\'' + ", events=" + this.events + '}';
    }
}
