package com.google.chip.chiptool.setuppayloadscanner;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.core.view.PointerIconCompat;
import chip.platform.ConfigurationManager;
import chip.setuppayload.DiscoveryCapability;
import chip.setuppayload.OptionalQRCodeInfo;
import chip.setuppayload.SetupPayload;
import com.leedarson.base.utils.e;
import com.leedarson.serviceimpl.bean.LeedarsonParams;
import com.leedarson.serviceimpl.blec075.h;
import com.tutk.IOTC.AVIOCTRLDEFs;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import kotlin.collections.k0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.l;
import kotlin.text.p;
import meshsdk.ConfigUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

/* compiled from: CHIPDeviceInfo.kt */
public final class CHIPDeviceInfo implements Parcelable {
    @NotNull
    public static final Parcelable.Creator<CHIPDeviceInfo> CREATOR = new Creator();
    @NotNull
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    private int commissioningFlow;
    @NotNull
    private final Set<DiscoveryCapability> discoveryCapabilities;
    private final int discriminator;
    private final int hasShortDiscriminator;
    @Nullable
    private final String ipAddress;
    @Nullable
    private LeedarsonParams leedarsonParams;
    @NotNull
    private final Map<Integer, QrCodeInfo> optionalQrCodeInfoMap;
    private final int productId;
    private final long setupPinCode;
    private final int vendorId;
    private final int version;

    @l(d1 = {}, d2 = {}, k = 3, mv = {1, 5, 1})
    /* compiled from: CHIPDeviceInfo.kt */
    public static final class Creator implements Parcelable.Creator<CHIPDeviceInfo> {
        @NotNull
        public final CHIPDeviceInfo createFromParcel(@NotNull Parcel parcel) {
            k.e(parcel, "parcel");
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            int readInt3 = parcel.readInt();
            int readInt4 = parcel.readInt();
            long readLong = parcel.readLong();
            int readInt5 = parcel.readInt();
            int readInt6 = parcel.readInt();
            LinkedHashMap linkedHashMap = new LinkedHashMap(readInt6);
            int i = 0;
            for (int i2 = 0; i2 != readInt6; i2++) {
                linkedHashMap.put(Integer.valueOf(parcel.readInt()), QrCodeInfo.CREATOR.createFromParcel(parcel));
            }
            int readInt7 = parcel.readInt();
            LinkedHashSet linkedHashSet = new LinkedHashSet(readInt7);
            while (true) {
                String readString = parcel.readString();
                if (i == readInt7) {
                    return new CHIPDeviceInfo(readInt, readInt2, readInt3, readInt4, readLong, readInt5, linkedHashMap, linkedHashSet, readString, parcel.readInt(), (LeedarsonParams) parcel.readParcelable(CHIPDeviceInfo.class.getClassLoader()));
                }
                linkedHashSet.add(DiscoveryCapability.valueOf(readString));
                i++;
            }
        }

        @NotNull
        public final CHIPDeviceInfo[] newArray(int i) {
            return new CHIPDeviceInfo[i];
        }
    }

    public CHIPDeviceInfo() {
        this(0, 0, 0, 0, 0, 0, (Map) null, (Set) null, (String) null, 0, (LeedarsonParams) null, 2047, (DefaultConstructorMarker) null);
    }

    public static /* synthetic */ CHIPDeviceInfo copy$default(CHIPDeviceInfo cHIPDeviceInfo, int i, int i2, int i3, int i4, long j, int i5, Map map, Set set, String str, int i6, LeedarsonParams leedarsonParams2, int i7, Object obj) {
        CHIPDeviceInfo cHIPDeviceInfo2 = cHIPDeviceInfo;
        int i8 = i7;
        return cHIPDeviceInfo.copy((i8 & 1) != 0 ? cHIPDeviceInfo2.version : i, (i8 & 2) != 0 ? cHIPDeviceInfo2.vendorId : i2, (i8 & 4) != 0 ? cHIPDeviceInfo2.productId : i3, (i8 & 8) != 0 ? cHIPDeviceInfo2.discriminator : i4, (i8 & 16) != 0 ? cHIPDeviceInfo2.setupPinCode : j, (i8 & 32) != 0 ? cHIPDeviceInfo2.commissioningFlow : i5, (i8 & 64) != 0 ? cHIPDeviceInfo2.optionalQrCodeInfoMap : map, (i8 & 128) != 0 ? cHIPDeviceInfo2.discoveryCapabilities : set, (i8 & 256) != 0 ? cHIPDeviceInfo2.ipAddress : str, (i8 & 512) != 0 ? cHIPDeviceInfo2.hasShortDiscriminator : i6, (i8 & 1024) != 0 ? cHIPDeviceInfo2.leedarsonParams : leedarsonParams2);
    }

    public final int component1() {
        return this.version;
    }

    public final int component10() {
        return this.hasShortDiscriminator;
    }

    @Nullable
    public final LeedarsonParams component11() {
        return this.leedarsonParams;
    }

    public final int component2() {
        return this.vendorId;
    }

    public final int component3() {
        return this.productId;
    }

    public final int component4() {
        return this.discriminator;
    }

    public final long component5() {
        return this.setupPinCode;
    }

    public final int component6() {
        return this.commissioningFlow;
    }

    @NotNull
    public final Map<Integer, QrCodeInfo> component7() {
        return this.optionalQrCodeInfoMap;
    }

    @NotNull
    public final Set<DiscoveryCapability> component8() {
        return this.discoveryCapabilities;
    }

    @Nullable
    public final String component9() {
        return this.ipAddress;
    }

    @NotNull
    public final CHIPDeviceInfo copy(int i, int i2, int i3, int i4, long j, int i5, @NotNull Map<Integer, QrCodeInfo> map, @NotNull Set<? extends DiscoveryCapability> set, @Nullable String str, int i6, @Nullable LeedarsonParams leedarsonParams2) {
        k.e(map, "optionalQrCodeInfoMap");
        k.e(set, "discoveryCapabilities");
        return new CHIPDeviceInfo(i, i2, i3, i4, j, i5, map, set, str, i6, leedarsonParams2);
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CHIPDeviceInfo)) {
            return false;
        }
        CHIPDeviceInfo cHIPDeviceInfo = (CHIPDeviceInfo) obj;
        return this.version == cHIPDeviceInfo.version && this.vendorId == cHIPDeviceInfo.vendorId && this.productId == cHIPDeviceInfo.productId && this.discriminator == cHIPDeviceInfo.discriminator && this.setupPinCode == cHIPDeviceInfo.setupPinCode && this.commissioningFlow == cHIPDeviceInfo.commissioningFlow && k.a(this.optionalQrCodeInfoMap, cHIPDeviceInfo.optionalQrCodeInfoMap) && k.a(this.discoveryCapabilities, cHIPDeviceInfo.discoveryCapabilities) && k.a(this.ipAddress, cHIPDeviceInfo.ipAddress) && this.hasShortDiscriminator == cHIPDeviceInfo.hasShortDiscriminator && k.a(this.leedarsonParams, cHIPDeviceInfo.leedarsonParams);
    }

    public int hashCode() {
        int a = ((((((((((((((this.version * 31) + this.vendorId) * 31) + this.productId) * 31) + this.discriminator) * 31) + a.a(this.setupPinCode)) * 31) + this.commissioningFlow) * 31) + this.optionalQrCodeInfoMap.hashCode()) * 31) + this.discoveryCapabilities.hashCode()) * 31;
        String str = this.ipAddress;
        int i = 0;
        int hashCode = (((a + (str == null ? 0 : str.hashCode())) * 31) + this.hasShortDiscriminator) * 31;
        LeedarsonParams leedarsonParams2 = this.leedarsonParams;
        if (leedarsonParams2 != null) {
            i = leedarsonParams2.hashCode();
        }
        return hashCode + i;
    }

    @NotNull
    public String toString() {
        return "CHIPDeviceInfo(version=" + this.version + ", vendorId=" + this.vendorId + ", productId=" + this.productId + ", discriminator=" + this.discriminator + ", setupPinCode=" + this.setupPinCode + ", commissioningFlow=" + this.commissioningFlow + ", optionalQrCodeInfoMap=" + this.optionalQrCodeInfoMap + ", discoveryCapabilities=" + this.discoveryCapabilities + ", ipAddress=" + this.ipAddress + ", hasShortDiscriminator=" + this.hasShortDiscriminator + ", leedarsonParams=" + this.leedarsonParams + ')';
    }

    public void writeToParcel(@NotNull Parcel parcel, int i) {
        k.e(parcel, "out");
        parcel.writeInt(this.version);
        parcel.writeInt(this.vendorId);
        parcel.writeInt(this.productId);
        parcel.writeInt(this.discriminator);
        parcel.writeLong(this.setupPinCode);
        parcel.writeInt(this.commissioningFlow);
        Map<Integer, QrCodeInfo> map = this.optionalQrCodeInfoMap;
        parcel.writeInt(map.size());
        for (Map.Entry next : map.entrySet()) {
            parcel.writeInt(((Number) next.getKey()).intValue());
            ((QrCodeInfo) next.getValue()).writeToParcel(parcel, i);
        }
        Set<DiscoveryCapability> set = this.discoveryCapabilities;
        parcel.writeInt(set.size());
        for (DiscoveryCapability name : set) {
            parcel.writeString(name.name());
        }
        parcel.writeString(this.ipAddress);
        parcel.writeInt(this.hasShortDiscriminator);
        parcel.writeParcelable(this.leedarsonParams, i);
    }

    public CHIPDeviceInfo(int version2, int vendorId2, int productId2, int discriminator2, long setupPinCode2, int commissioningFlow2, @NotNull Map<Integer, QrCodeInfo> optionalQrCodeInfoMap2, @NotNull Set<? extends DiscoveryCapability> discoveryCapabilities2, @Nullable String ipAddress2, int hasShortDiscriminator2, @Nullable LeedarsonParams leedarsonParams2) {
        k.e(optionalQrCodeInfoMap2, "optionalQrCodeInfoMap");
        k.e(discoveryCapabilities2, "discoveryCapabilities");
        this.version = version2;
        this.vendorId = vendorId2;
        this.productId = productId2;
        this.discriminator = discriminator2;
        this.setupPinCode = setupPinCode2;
        this.commissioningFlow = commissioningFlow2;
        this.optionalQrCodeInfoMap = optionalQrCodeInfoMap2;
        this.discoveryCapabilities = discoveryCapabilities2;
        this.ipAddress = ipAddress2;
        this.hasShortDiscriminator = hasShortDiscriminator2;
        this.leedarsonParams = leedarsonParams2;
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public /* synthetic */ CHIPDeviceInfo(int r15, int r16, int r17, int r18, long r19, int r21, java.util.Map r22, java.util.Set r23, java.lang.String r24, int r25, com.leedarson.serviceimpl.bean.LeedarsonParams r26, int r27, kotlin.jvm.internal.DefaultConstructorMarker r28) {
        /*
            r14 = this;
            r0 = r27
            r1 = r0 & 1
            r2 = 0
            if (r1 == 0) goto L_0x0009
            r1 = r2
            goto L_0x000a
        L_0x0009:
            r1 = r15
        L_0x000a:
            r3 = r0 & 2
            if (r3 == 0) goto L_0x0010
            r3 = r2
            goto L_0x0012
        L_0x0010:
            r3 = r16
        L_0x0012:
            r4 = r0 & 4
            if (r4 == 0) goto L_0x0018
            r4 = r2
            goto L_0x001a
        L_0x0018:
            r4 = r17
        L_0x001a:
            r5 = r0 & 8
            if (r5 == 0) goto L_0x0020
            r5 = r2
            goto L_0x0022
        L_0x0020:
            r5 = r18
        L_0x0022:
            r6 = r0 & 16
            if (r6 == 0) goto L_0x0029
            r6 = 0
            goto L_0x002b
        L_0x0029:
            r6 = r19
        L_0x002b:
            r8 = r0 & 32
            if (r8 == 0) goto L_0x0031
            r8 = r2
            goto L_0x0033
        L_0x0031:
            r8 = r21
        L_0x0033:
            r9 = r0 & 64
            if (r9 == 0) goto L_0x003c
            java.util.Map r9 = kotlin.collections.l0.f()
            goto L_0x003e
        L_0x003c:
            r9 = r22
        L_0x003e:
            r10 = r0 & 128(0x80, float:1.794E-43)
            if (r10 == 0) goto L_0x0047
            java.util.Set r10 = kotlin.collections.o0.d()
            goto L_0x0049
        L_0x0047:
            r10 = r23
        L_0x0049:
            r11 = r0 & 256(0x100, float:3.59E-43)
            r12 = 0
            if (r11 == 0) goto L_0x0050
            r11 = r12
            goto L_0x0052
        L_0x0050:
            r11 = r24
        L_0x0052:
            r13 = r0 & 512(0x200, float:7.175E-43)
            if (r13 == 0) goto L_0x0057
            goto L_0x0059
        L_0x0057:
            r2 = r25
        L_0x0059:
            r0 = r0 & 1024(0x400, float:1.435E-42)
            if (r0 == 0) goto L_0x005e
            goto L_0x0060
        L_0x005e:
            r12 = r26
        L_0x0060:
            r15 = r14
            r16 = r1
            r17 = r3
            r18 = r4
            r19 = r5
            r20 = r6
            r22 = r8
            r23 = r9
            r24 = r10
            r25 = r11
            r26 = r2
            r27 = r12
            r15.<init>(r16, r17, r18, r19, r20, r22, r23, r24, r25, r26, r27)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.chip.chiptool.setuppayloadscanner.CHIPDeviceInfo.<init>(int, int, int, int, long, int, java.util.Map, java.util.Set, java.lang.String, int, com.leedarson.serviceimpl.bean.LeedarsonParams, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    public final int getVersion() {
        return this.version;
    }

    public final int getVendorId() {
        return this.vendorId;
    }

    public final int getProductId() {
        return this.productId;
    }

    public final int getDiscriminator() {
        return this.discriminator;
    }

    public final long getSetupPinCode() {
        return this.setupPinCode;
    }

    public final int getCommissioningFlow() {
        return this.commissioningFlow;
    }

    public final void setCommissioningFlow(int i) {
        this.commissioningFlow = i;
    }

    @NotNull
    public final Map<Integer, QrCodeInfo> getOptionalQrCodeInfoMap() {
        return this.optionalQrCodeInfoMap;
    }

    @NotNull
    public final Set<DiscoveryCapability> getDiscoveryCapabilities() {
        return this.discoveryCapabilities;
    }

    @Nullable
    public final String getIpAddress() {
        return this.ipAddress;
    }

    public final int getHasShortDiscriminator() {
        return this.hasShortDiscriminator;
    }

    @Nullable
    public final LeedarsonParams getLeedarsonParams() {
        return this.leedarsonParams;
    }

    public final void setLeedarsonParams(@Nullable LeedarsonParams leedarsonParams2) {
        this.leedarsonParams = leedarsonParams2;
    }

    /* compiled from: CHIPDeviceInfo.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @NotNull
        public final CHIPDeviceInfo fromSetupPayload(@NotNull SetupPayload setupPayload) {
            SetupPayload setupPayload2 = setupPayload;
            k.e(setupPayload2, "setupPayload");
            int i = setupPayload2.version;
            int i2 = setupPayload2.vendorId;
            int i3 = setupPayload2.productId;
            int i4 = setupPayload2.discriminator;
            long j = setupPayload2.setupPinCode;
            int i5 = setupPayload2.commissioningFlow;
            Map $this$mapValues$iv = setupPayload2.optionalQRCodeInfo;
            k.d($this$mapValues$iv, "setupPayload.optionalQRCodeInfo");
            int $i$f$mapValues = false;
            Map destination$iv$iv = new LinkedHashMap(k0.b($this$mapValues$iv.size()));
            Map $this$mapValuesTo$iv$iv = $this$mapValues$iv;
            int $i$f$mapValuesTo = false;
            Iterable $this$associateByTo$iv$iv$iv = $this$mapValuesTo$iv$iv.entrySet();
            int $i$f$associateByTo = false;
            for (Iterator<T> it = $this$associateByTo$iv$iv$iv.iterator(); it.hasNext(); it = it) {
                Map.Entry it$iv$iv = it.next();
                Map $this$mapValues$iv2 = $this$mapValues$iv;
                Object key = it$iv$iv.getKey();
                int $i$f$mapValues2 = $i$f$mapValues;
                OptionalQRCodeInfo info = (OptionalQRCodeInfo) it$iv$iv.getValue();
                int $i$f$mapValuesTo2 = $i$f$mapValuesTo;
                int $i$f$mapValuesTo3 = info.tag;
                Iterable $this$associateByTo$iv$iv$iv2 = $this$associateByTo$iv$iv$iv;
                OptionalQRCodeInfo.OptionalQRCodeInfoType optionalQRCodeInfoType = info.type;
                int $i$f$associateByTo2 = $i$f$associateByTo;
                k.d(optionalQRCodeInfoType, "info.type");
                String str = info.data;
                k.d(str, "info.data");
                destination$iv$iv.put(key, new QrCodeInfo($i$f$mapValuesTo3, optionalQRCodeInfoType, str, info.int32));
                $this$mapValues$iv = $this$mapValues$iv2;
                $this$mapValuesTo$iv$iv = $this$mapValuesTo$iv$iv;
                $i$f$mapValues = $i$f$mapValues2;
                $i$f$mapValuesTo = $i$f$mapValuesTo2;
                $this$associateByTo$iv$iv$iv = $this$associateByTo$iv$iv$iv2;
                $i$f$associateByTo = $i$f$associateByTo2;
            }
            int i6 = $i$f$mapValues;
            Map map = $this$mapValuesTo$iv$iv;
            int i7 = $i$f$mapValuesTo;
            Iterable iterable = $this$associateByTo$iv$iv$iv;
            int i8 = $i$f$associateByTo;
            Set<DiscoveryCapability> set = setupPayload2.discoveryCapabilities;
            k.d(set, "setupPayload.discoveryCapabilities");
            CHIPDeviceInfo cHIPDeviceInfo = new CHIPDeviceInfo(i, i2, i3, i4, j, i5, destination$iv$iv, set, (String) null, setupPayload2.hasShortDiscriminator, (LeedarsonParams) null, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GET_SAVE_DROPBOX_REQ, (DefaultConstructorMarker) null);
            cHIPDeviceInfo.setLeedarsonParams(LeedarsonParams.fromOptionalInfo(setupPayload2.optionalQRCodeInfo));
            return cHIPDeviceInfo;
        }

        @Nullable
        public final CHIPDeviceInfo fromBleFullScanRecord(@NotNull byte[] scanRecord) {
            k.e(scanRecord, "scanRecord");
            Map parseAdvertisePacket = h.k(scanRecord);
            h.a dataUnit = parseAdvertisePacket.get((byte) 22);
            h.a customDataUnit = parseAdvertisePacket.get((byte) -1);
            k.c(dataUnit);
            String adDataStr = e.a(dataUnit.c);
            if ((customDataUnit == null ? null : customDataUnit.c) != null) {
                adDataStr = k.l(adDataStr, e.a(customDataUnit.c));
            }
            byte[] g = e.g(adDataStr);
            k.d(g, "hexToBytes(adDataStr)");
            return fromScanRecord(g);
        }

        @Nullable
        public final CHIPDeviceInfo fromScanRecord(@NotNull byte[] dataRecord) {
            byte[] bArr = dataRecord;
            k.e(bArr, "dataRecord");
            byte[] bArr2 = dataRecord;
            if ((bArr[0] != -10) && (bArr[1] != -1)) {
                return null;
            }
            LeedarsonParams leedarsonParams = null;
            try {
                if (bArr.length > 10) {
                    leedarsonParams = LeedarsonParams.fromScanRecord(dataRecord);
                }
                byte b = bArr[2];
                byte[] bArr3 = {bArr[4], bArr[3]};
                int version = bArr[4] >> 4;
                int discriminator = ((bArr[4] & 15) << 8) | (bArr[3] & 255);
                byte[] vidBytes = {bArr[6], bArr[5]};
                long vid = h.a(vidBytes, 2);
                long pid = h.a(new byte[]{bArr[8], bArr[7]}, 2);
                byte b2 = bArr[9];
                long j = pid;
                long j2 = vid;
                byte[] bArr4 = vidBytes;
                CHIPDeviceInfo info = new CHIPDeviceInfo(version, (int) vid, (int) pid, discriminator, 0, 0, (Map) null, (Set) null, (String) null, 0, leedarsonParams, PointerIconCompat.TYPE_TEXT, (DefaultConstructorMarker) null);
                com.leedarson.serviceimpl.k kVar = com.leedarson.serviceimpl.k.a;
                com.leedarson.serviceimpl.k.m(kVar, "Ble 发现 serviceData:" + e.a(dataRecord) + ",info:" + info, (String) null, 2, (Object) null);
                return info;
            } catch (Exception e) {
                com.leedarson.serviceimpl.k.e(com.leedarson.serviceimpl.k.a, k.l("解析Matter Ble广播包出错，非matter 设备:", e), (String) null, 2, (Object) null);
                return null;
            }
        }
    }

    @NotNull
    public final String getFormat() {
        return p.f("\n                  Version:" + this.version + "\n                  Vendor ID:" + this.vendorId + "\n                  Product ID:" + this.productId + "\n                  Discriminator:" + this.discriminator + "\n                  Setup PIN Code:" + this.setupPinCode + "\n                  Disconvery capabilities:" + this.discoveryCapabilities + "\n                  Commissioning Flow:\"" + this.commissioningFlow + " \n                  leedarsonParams:" + this.leedarsonParams + " \n                  ");
    }

    public static /* synthetic */ JSONObject toJSON$default(CHIPDeviceInfo cHIPDeviceInfo, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            str = "qrcode";
        }
        return cHIPDeviceInfo.toJSON(str);
    }

    @NotNull
    public final JSONObject toJSON(@NotNull String target) {
        k.e(target, TypedValues.AttributesType.S_TARGET);
        JSONObject json = new JSONObject();
        JSONObject $this$toJSON_u24lambda_u2d1 = json;
        $this$toJSON_u24lambda_u2d1.put(ConfigUtil.VERSION_FILE, (Object) String.valueOf(getVersion()));
        $this$toJSON_u24lambda_u2d1.put("vendorId", (Object) String.valueOf(getVendorId()));
        $this$toJSON_u24lambda_u2d1.put("productId", (Object) String.valueOf(getProductId()));
        $this$toJSON_u24lambda_u2d1.put("commissioingFlow", getCommissioningFlow());
        $this$toJSON_u24lambda_u2d1.put(ConfigurationManager.kConfigKey_SetupDiscriminator, (Object) String.valueOf(getDiscriminator()));
        if ("qrocde".equals(target)) {
            $this$toJSON_u24lambda_u2d1.put("setupPinCode", (Object) String.valueOf(getSetupPinCode()));
            $this$toJSON_u24lambda_u2d1.put("serialNumber", (Object) "");
            $this$toJSON_u24lambda_u2d1.put("rendezvousInformation", (Object) "");
            $this$toJSON_u24lambda_u2d1.put("hasShortDiscriminator", (Object) "0");
        }
        if (getLeedarsonParams() != null) {
            LeedarsonParams leedarsonParams2 = getLeedarsonParams();
            String str = null;
            $this$toJSON_u24lambda_u2d1.put("mac", (Object) leedarsonParams2 == null ? null : leedarsonParams2.wifiMac);
            LeedarsonParams leedarsonParams3 = getLeedarsonParams();
            $this$toJSON_u24lambda_u2d1.put("shortModelId", (Object) leedarsonParams3 == null ? null : leedarsonParams3.shortModelId);
            LeedarsonParams leedarsonParams4 = getLeedarsonParams();
            $this$toJSON_u24lambda_u2d1.put("ldsVersion", (Object) leedarsonParams4 == null ? null : Integer.valueOf(leedarsonParams4.version));
            LeedarsonParams leedarsonParams5 = getLeedarsonParams();
            if (leedarsonParams5 != null) {
                str = leedarsonParams5.pairingFlag;
            }
            $this$toJSON_u24lambda_u2d1.put("pairingFlag", (Object) str);
        }
        return json;
    }
}
