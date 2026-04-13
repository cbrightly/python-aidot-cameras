package com.leedarson.smartcamera.bean;

import com.google.gson.annotations.SerializedName;

public class IceCandidateEventBean {
    @SerializedName("extensions")
    public ExtensionsBean extensions;
    @SerializedName("id")
    public String id;

    public static class ExtensionsBean {
        @SerializedName("method")
        public String method;
        @SerializedName("payload")
        public PayloadBean payload;

        public static class PayloadBean {
            @SerializedName("candidate")
            public CandidateBean candidate;
            @SerializedName("peerid")
            public String peerid;

            public static class CandidateBean {
                @SerializedName("candidate")
                public String candidate;
            }
        }
    }
}
