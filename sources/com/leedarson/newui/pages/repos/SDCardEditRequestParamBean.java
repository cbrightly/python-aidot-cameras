package com.leedarson.newui.pages.repos;

import com.google.chip.chiptool.setuppayloadscanner.a;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.List;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ExtroIntentRepo.kt */
public final class SDCardEditRequestParamBean {
    public static ChangeQuickRedirect changeQuickRedirect;
    @Nullable
    private String deviceId;
    private long endTime;
    @Nullable
    private String eventStr;
    private int eventType;
    private boolean isSupportPreCon;
    private boolean isWebRTC;
    @Nullable
    private String p2pId;
    @Nullable
    private List<Long> sdVideoList;
    @Nullable
    private String selectedDate;
    private long startTime;

    public static /* synthetic */ SDCardEditRequestParamBean copy$default(SDCardEditRequestParamBean sDCardEditRequestParamBean, String str, String str2, String str3, String str4, int i, boolean z, boolean z2, List list, long j, long j2, int i2, Object obj) {
        SDCardEditRequestParamBean sDCardEditRequestParamBean2 = sDCardEditRequestParamBean;
        int i3 = i2;
        Class<String> cls = String.class;
        int i4 = i;
        long j3 = j;
        long j4 = j2;
        Object[] objArr = {sDCardEditRequestParamBean2, str, str2, str3, str4, new Integer(i4), new Byte(z ? (byte) 1 : 0), new Byte(z2 ? (byte) 1 : 0), list, new Long(j3), new Long(j4), new Integer(i3), obj};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls2 = Integer.TYPE;
        Class cls3 = Boolean.TYPE;
        Class cls4 = Long.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 4358, new Class[]{SDCardEditRequestParamBean.class, cls, cls, cls, cls, cls2, cls3, cls3, List.class, cls4, cls4, cls2, Object.class}, SDCardEditRequestParamBean.class);
        if (proxy.isSupported) {
            return (SDCardEditRequestParamBean) proxy.result;
        }
        String str5 = (i3 & 1) != 0 ? sDCardEditRequestParamBean2.deviceId : str;
        String str6 = (i3 & 2) != 0 ? sDCardEditRequestParamBean2.selectedDate : str2;
        String str7 = (i3 & 4) != 0 ? sDCardEditRequestParamBean2.p2pId : str3;
        String str8 = (i3 & 8) != 0 ? sDCardEditRequestParamBean2.eventStr : str4;
        int i5 = (i3 & 16) != 0 ? sDCardEditRequestParamBean2.eventType : i4;
        boolean z3 = (i3 & 32) != 0 ? sDCardEditRequestParamBean2.isWebRTC : z;
        boolean z4 = (i3 & 64) != 0 ? sDCardEditRequestParamBean2.isSupportPreCon : z2;
        List list2 = (i3 & 128) != 0 ? sDCardEditRequestParamBean2.sdVideoList : list;
        long j5 = (i3 & 256) != 0 ? sDCardEditRequestParamBean2.startTime : j3;
        if ((i3 & 512) != 0) {
            j4 = sDCardEditRequestParamBean2.endTime;
        }
        return sDCardEditRequestParamBean.copy(str5, str6, str7, str8, i5, z3, z4, list2, j5, j4);
    }

    @Nullable
    public final String component1() {
        return this.deviceId;
    }

    public final long component10() {
        return this.endTime;
    }

    @Nullable
    public final String component2() {
        return this.selectedDate;
    }

    @Nullable
    public final String component3() {
        return this.p2pId;
    }

    @Nullable
    public final String component4() {
        return this.eventStr;
    }

    public final int component5() {
        return this.eventType;
    }

    public final boolean component6() {
        return this.isWebRTC;
    }

    public final boolean component7() {
        return this.isSupportPreCon;
    }

    @Nullable
    public final List<Long> component8() {
        return this.sdVideoList;
    }

    public final long component9() {
        return this.startTime;
    }

    @NotNull
    public final SDCardEditRequestParamBean copy(@Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, int i, boolean z, boolean z2, @Nullable List<Long> list, long j, long j2) {
        Class<String> cls = String.class;
        Object[] objArr = {str, str2, str3, str4, new Integer(i), new Byte(z ? (byte) 1 : 0), new Byte(z2 ? (byte) 1 : 0), list, new Long(j), new Long(j2)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls2 = Boolean.TYPE;
        Class cls3 = Long.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4357, new Class[]{cls, cls, cls, cls, Integer.TYPE, cls2, cls2, List.class, cls3, cls3}, SDCardEditRequestParamBean.class);
        if (proxy.isSupported) {
            return (SDCardEditRequestParamBean) proxy.result;
        }
        return new SDCardEditRequestParamBean(str, str2, str3, str4, i, z, z2, list, j, j2);
    }

    public boolean equals(@Nullable Object obj) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 4361, new Class[]{Object.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SDCardEditRequestParamBean)) {
            return false;
        }
        SDCardEditRequestParamBean sDCardEditRequestParamBean = (SDCardEditRequestParamBean) obj;
        return k.a(this.deviceId, sDCardEditRequestParamBean.deviceId) && k.a(this.selectedDate, sDCardEditRequestParamBean.selectedDate) && k.a(this.p2pId, sDCardEditRequestParamBean.p2pId) && k.a(this.eventStr, sDCardEditRequestParamBean.eventStr) && this.eventType == sDCardEditRequestParamBean.eventType && this.isWebRTC == sDCardEditRequestParamBean.isWebRTC && this.isSupportPreCon == sDCardEditRequestParamBean.isSupportPreCon && k.a(this.sdVideoList, sDCardEditRequestParamBean.sdVideoList) && this.startTime == sDCardEditRequestParamBean.startTime && this.endTime == sDCardEditRequestParamBean.endTime;
    }

    public int hashCode() {
        int i = 0;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4360, new Class[0], Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        String str = this.deviceId;
        int hashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.selectedDate;
        int hashCode2 = (hashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.p2pId;
        int hashCode3 = (hashCode2 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.eventStr;
        int hashCode4 = (((hashCode3 + (str4 == null ? 0 : str4.hashCode())) * 31) + this.eventType) * 31;
        boolean z = this.isWebRTC;
        boolean z2 = true;
        if (z) {
            z = true;
        }
        int i2 = (hashCode4 + (z ? 1 : 0)) * 31;
        boolean z3 = this.isSupportPreCon;
        if (!z3) {
            z2 = z3;
        }
        int i3 = (i2 + (z2 ? 1 : 0)) * 31;
        List<Long> list = this.sdVideoList;
        if (list != null) {
            i = list.hashCode();
        }
        return ((((i3 + i) * 31) + a.a(this.startTime)) * 31) + a.a(this.endTime);
    }

    @NotNull
    public String toString() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4359, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return "SDCardEditRequestParamBean(deviceId=" + this.deviceId + ", selectedDate=" + this.selectedDate + ", p2pId=" + this.p2pId + ", eventStr=" + this.eventStr + ", eventType=" + this.eventType + ", isWebRTC=" + this.isWebRTC + ", isSupportPreCon=" + this.isSupportPreCon + ", sdVideoList=" + this.sdVideoList + ", startTime=" + this.startTime + ", endTime=" + this.endTime + ')';
    }

    public SDCardEditRequestParamBean(@Nullable String deviceId2, @Nullable String selectedDate2, @Nullable String p2pId2, @Nullable String eventStr2, int eventType2, boolean isWebRTC2, boolean isSupportPreCon2, @Nullable List<Long> sdVideoList2, long startTime2, long endTime2) {
        this.deviceId = deviceId2;
        this.selectedDate = selectedDate2;
        this.p2pId = p2pId2;
        this.eventStr = eventStr2;
        this.eventType = eventType2;
        this.isWebRTC = isWebRTC2;
        this.isSupportPreCon = isSupportPreCon2;
        this.sdVideoList = sdVideoList2;
        this.startTime = startTime2;
        this.endTime = endTime2;
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public /* synthetic */ SDCardEditRequestParamBean(java.lang.String r16, java.lang.String r17, java.lang.String r18, java.lang.String r19, int r20, boolean r21, boolean r22, java.util.List r23, long r24, long r26, int r28, kotlin.jvm.internal.DefaultConstructorMarker r29) {
        /*
            r15 = this;
            r0 = r28 & 16
            r1 = 0
            if (r0 == 0) goto L_0x0007
            r7 = r1
            goto L_0x0009
        L_0x0007:
            r7 = r20
        L_0x0009:
            r0 = r28 & 32
            if (r0 == 0) goto L_0x000f
            r8 = r1
            goto L_0x0011
        L_0x000f:
            r8 = r21
        L_0x0011:
            r0 = r28 & 64
            if (r0 == 0) goto L_0x0017
            r9 = r1
            goto L_0x0019
        L_0x0017:
            r9 = r22
        L_0x0019:
            r2 = r15
            r3 = r16
            r4 = r17
            r5 = r18
            r6 = r19
            r10 = r23
            r11 = r24
            r13 = r26
            r2.<init>(r3, r4, r5, r6, r7, r8, r9, r10, r11, r13)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.pages.repos.SDCardEditRequestParamBean.<init>(java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, boolean, boolean, java.util.List, long, long, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    @Nullable
    public final String getDeviceId() {
        return this.deviceId;
    }

    @Nullable
    public final String getP2pId() {
        return this.p2pId;
    }

    @Nullable
    public final String getSelectedDate() {
        return this.selectedDate;
    }

    public final void setDeviceId(@Nullable String str) {
        this.deviceId = str;
    }

    public final void setP2pId(@Nullable String str) {
        this.p2pId = str;
    }

    public final void setSelectedDate(@Nullable String str) {
        this.selectedDate = str;
    }

    @Nullable
    public final String getEventStr() {
        return this.eventStr;
    }

    public final int getEventType() {
        return this.eventType;
    }

    public final boolean isSupportPreCon() {
        return this.isSupportPreCon;
    }

    public final boolean isWebRTC() {
        return this.isWebRTC;
    }

    public final void setEventStr(@Nullable String str) {
        this.eventStr = str;
    }

    public final void setEventType(int i) {
        this.eventType = i;
    }

    public final void setSupportPreCon(boolean z) {
        this.isSupportPreCon = z;
    }

    public final void setWebRTC(boolean z) {
        this.isWebRTC = z;
    }

    public final long getEndTime() {
        return this.endTime;
    }

    @Nullable
    public final List<Long> getSdVideoList() {
        return this.sdVideoList;
    }

    public final long getStartTime() {
        return this.startTime;
    }

    public final void setEndTime(long j) {
        this.endTime = j;
    }

    public final void setSdVideoList(@Nullable List<Long> list) {
        this.sdVideoList = list;
    }

    public final void setStartTime(long j) {
        this.startTime = j;
    }
}
