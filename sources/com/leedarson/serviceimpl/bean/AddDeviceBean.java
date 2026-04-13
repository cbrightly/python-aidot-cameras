package com.leedarson.serviceimpl.bean;

import android.text.TextUtils;
import com.leedarson.bean.Constants;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.ArrayList;
import java.util.Locale;
import kotlin.jvm.internal.k;
import kotlin.text.a;
import kotlin.text.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: MatterInfo.kt */
public final class AddDeviceBean {
    public static ChangeQuickRedirect changeQuickRedirect;
    @Nullable
    private String QRCode = "";
    @Nullable
    private String discriminator = "";
    @Nullable
    private String mac = "";
    @Nullable
    private String manualCode = "";
    private long matterAddr;
    @Nullable
    private ArrayList<String> pairingTargets = new ArrayList<>();
    @Nullable
    private String pairingToken = "";
    @NotNull
    private String password = "";
    @NotNull
    private String ssid = "";
    @Nullable
    private String timezone = "";
    @NotNull
    private String traceId = "";

    @NotNull
    public final String getSsid() {
        return this.ssid;
    }

    public final void setSsid(@NotNull String str) {
        if (!PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 6147, new Class[]{String.class}, Void.TYPE).isSupported) {
            k.e(str, "<set-?>");
            this.ssid = str;
        }
    }

    @NotNull
    public final String getPassword() {
        return this.password;
    }

    public final void setPassword(@NotNull String str) {
        if (!PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 6148, new Class[]{String.class}, Void.TYPE).isSupported) {
            k.e(str, "<set-?>");
            this.password = str;
        }
    }

    public final long getMatterAddr() {
        return this.matterAddr;
    }

    public final void setMatterAddr(long j) {
        this.matterAddr = j;
    }

    @Nullable
    public final String getTimezone() {
        return this.timezone;
    }

    public final void setTimezone(@Nullable String str) {
        this.timezone = str;
    }

    @Nullable
    public final String getPairingToken() {
        return this.pairingToken;
    }

    public final void setPairingToken(@Nullable String str) {
        this.pairingToken = str;
    }

    @Nullable
    public final String getQRCode() {
        return this.QRCode;
    }

    public final void setQRCode(@Nullable String str) {
        this.QRCode = str;
    }

    @Nullable
    public final String getDiscriminator() {
        return this.discriminator;
    }

    public final void setDiscriminator(@Nullable String str) {
        this.discriminator = str;
    }

    @Nullable
    public final String getManualCode() {
        return this.manualCode;
    }

    public final void setManualCode(@Nullable String str) {
        this.manualCode = str;
    }

    @Nullable
    public final String getMac() {
        return this.mac;
    }

    public final void setMac(@Nullable String str) {
        this.mac = str;
    }

    @NotNull
    public final String getTraceId() {
        return this.traceId;
    }

    public final void setTraceId(@NotNull String str) {
        if (!PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 6149, new Class[]{String.class}, Void.TYPE).isSupported) {
            k.e(str, "<set-?>");
            this.traceId = str;
        }
    }

    @Nullable
    public final ArrayList<String> getPairingTargets() {
        return this.pairingTargets;
    }

    public final void setPairingTargets(@Nullable ArrayList<String> arrayList) {
        this.pairingTargets = arrayList;
    }

    public final boolean isOnlyMqtt() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6150, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        ArrayList it = this.pairingTargets;
        if (it == null) {
            return false;
        }
        String upperCase = String.valueOf(getPairingTargets()).toUpperCase(Locale.ROOT);
        k.d(upperCase, "(this as java.lang.Strin….toUpperCase(Locale.ROOT)");
        if (!x.S(upperCase, Constants.SERVICE_MQTT, false, 2, (Object) null) || it.size() != 1) {
            return false;
        }
        return true;
    }

    public final boolean isLeedarsonMatter() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6151, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        return (!TextUtils.isEmpty(this.pairingToken)) & (!TextUtils.isEmpty(this.timezone));
    }

    public final boolean fromQrcode() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6152, new Class[0], Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : !TextUtils.isEmpty(this.QRCode);
    }

    public final boolean ManulCode() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6153, new Class[0], Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : !TextUtils.isEmpty(this.manualCode);
    }

    @NotNull
    public final String getHexNodeId() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6154, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        String l = Long.toString(this.matterAddr, a.a(16));
        k.d(l, "java.lang.Long.toString(this, checkRadix(radix))");
        return l;
    }
}
