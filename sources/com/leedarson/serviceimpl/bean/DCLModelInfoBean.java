package com.leedarson.serviceimpl.bean;

import com.google.gson.Gson;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

/* compiled from: MatterInfo.kt */
public final class DCLModelInfoBean {
    public static ChangeQuickRedirect changeQuickRedirect;
    @Nullable
    private String creator = "";
    @NotNull
    private transient String errJson = "";
    @Nullable
    private Integer pid = 0;
    @Nullable
    private String productLabel = "";
    @Nullable
    private String productName = "";
    @Nullable
    private Integer vid = 0;

    @Nullable
    public final Integer getVid() {
        return this.vid;
    }

    public final void setVid(@Nullable Integer num) {
        this.vid = num;
    }

    @Nullable
    public final Integer getPid() {
        return this.pid;
    }

    public final void setPid(@Nullable Integer num) {
        this.pid = num;
    }

    @Nullable
    public final String getProductName() {
        return this.productName;
    }

    public final void setProductName(@Nullable String str) {
        this.productName = str;
    }

    @Nullable
    public final String getProductLabel() {
        return this.productLabel;
    }

    public final void setProductLabel(@Nullable String str) {
        this.productLabel = str;
    }

    @Nullable
    public final String getCreator() {
        return this.creator;
    }

    public final void setCreator(@Nullable String str) {
        this.creator = str;
    }

    @NotNull
    public final String getErrJson() {
        return this.errJson;
    }

    public final void setErrJson(@NotNull String str) {
        if (!PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 6159, new Class[]{String.class}, Void.TYPE).isSupported) {
            k.e(str, "<set-?>");
            this.errJson = str;
        }
    }

    @NotNull
    public final JSONObject asJSON() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6160, new Class[0], JSONObject.class);
        if (proxy.isSupported) {
            return (JSONObject) proxy.result;
        }
        JSONObject jsonObject = new JSONObject(new Gson().toJson((Object) this));
        jsonObject.put("vendorId", (Object) this.vid);
        jsonObject.put("productId", (Object) this.pid);
        return jsonObject;
    }
}
