package com.leedarson.bean;

import com.google.gson.annotations.SerializedName;

public class PartialUpdateEventBean {
    @SerializedName("extensions")
    public ExtensionsBean extensions;
    @SerializedName("id")
    public String id;

    public static class ExtensionsBean {
        @SerializedName("method")
        public String method;
        @SerializedName("stage")
        public Integer stage;
        @SerializedName("version")
        public String version;
    }
}
