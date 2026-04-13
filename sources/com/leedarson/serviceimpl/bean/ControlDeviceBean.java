package com.leedarson.serviceimpl.bean;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: MatterInfo.kt */
public final class ControlDeviceBean {
    public static ChangeQuickRedirect changeQuickRedirect;
    private int CCT = -1;
    private int Dimming = -1;
    @NotNull
    private float[] HSL = new float[0];
    private int OnOff = -1;
    private long RGBW = -1;
    @NotNull
    private String fabricId = "";
    private long matterAddr;

    public final long getMatterAddr() {
        return this.matterAddr;
    }

    public final void setMatterAddr(long j) {
        this.matterAddr = j;
    }

    @NotNull
    public final String getFabricId() {
        return this.fabricId;
    }

    public final void setFabricId(@NotNull String str) {
        if (!PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 6157, new Class[]{String.class}, Void.TYPE).isSupported) {
            k.e(str, "<set-?>");
            this.fabricId = str;
        }
    }

    public final int getOnOff() {
        return this.OnOff;
    }

    public final void setOnOff(int i) {
        this.OnOff = i;
    }

    public final int getCCT() {
        return this.CCT;
    }

    public final void setCCT(int i) {
        this.CCT = i;
    }

    public final int getDimming() {
        return this.Dimming;
    }

    public final void setDimming(int i) {
        this.Dimming = i;
    }

    public final long getRGBW() {
        return this.RGBW;
    }

    public final void setRGBW(long j) {
        this.RGBW = j;
    }

    @NotNull
    public final float[] getHSL() {
        return this.HSL;
    }

    public final void setHSL(@NotNull float[] fArr) {
        if (!PatchProxy.proxy(new Object[]{fArr}, this, changeQuickRedirect, false, 6158, new Class[]{float[].class}, Void.TYPE).isSupported) {
            k.e(fArr, "<set-?>");
            this.HSL = fArr;
        }
    }

    public final boolean hasOnOff() {
        return this.OnOff != -1;
    }

    public final boolean hasCCT() {
        return this.CCT != -1;
    }

    public final boolean hasDimming() {
        return this.Dimming != -1;
    }

    public final boolean hasRGBW() {
        return this.RGBW > -1;
    }

    public final boolean hasHSL() {
        return !(this.HSL.length == 0);
    }

    @NotNull
    public final int[] parseRGB() {
        long j = this.RGBW;
        return new int[]{(int) ((4278190080L & j) >> 24), (int) ((16711680 & j) >> 16), (int) ((j & 65280) >> 8)};
    }

    @NotNull
    public final float[] getXY(int r, int g, int b) {
        float X = (((float) r) * 0.649926f) + (((float) g) * 0.103455f) + (((float) b) * 0.197109f);
        float Y = (((float) r) * 0.234327f) + (((float) g) * 0.743075f) + (((float) b) * 0.022598f);
        float Z = (((float) g) * 0.053077f) + (((float) b) * 1.035763f);
        return new float[]{X / ((X + Y) + Z), Y / ((X + Y) + Z)};
    }

    @NotNull
    public final float[] getXY2(int r, int g, int b) {
        float X = (((float) r) * 0.664511f) + (((float) g) * 0.154324f) + (((float) b) * 0.162028f);
        float Y = (((float) r) * 0.283881f) + (((float) g) * 0.668433f) + (((float) b) * 0.047685f);
        float Z = (((float) r) * 8.8E-5f) + (((float) g) * 0.07231f) + (((float) b) * 0.986039f);
        return new float[]{X / ((X + Y) + Z), Y / ((X + Y) + Z)};
    }
}
