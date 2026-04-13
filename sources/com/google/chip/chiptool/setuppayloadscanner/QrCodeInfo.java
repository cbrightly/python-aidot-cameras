package com.google.chip.chiptool.setuppayloadscanner;

import android.os.Parcel;
import android.os.Parcelable;
import chip.setuppayload.OptionalQRCodeInfo;
import kotlin.jvm.internal.k;
import kotlin.l;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: QrCodeInfo.kt */
public final class QrCodeInfo implements Parcelable {
    @NotNull
    public static final Parcelable.Creator<QrCodeInfo> CREATOR = new Creator();
    @NotNull
    private final String data;
    private final int intDataValue;
    private final int tag;
    @NotNull
    private final OptionalQRCodeInfo.OptionalQRCodeInfoType type;

    @l(d1 = {}, d2 = {}, k = 3, mv = {1, 5, 1})
    /* compiled from: QrCodeInfo.kt */
    public static final class Creator implements Parcelable.Creator<QrCodeInfo> {
        @NotNull
        public final QrCodeInfo createFromParcel(@NotNull Parcel parcel) {
            k.e(parcel, "parcel");
            return new QrCodeInfo(parcel.readInt(), OptionalQRCodeInfo.OptionalQRCodeInfoType.valueOf(parcel.readString()), parcel.readString(), parcel.readInt());
        }

        @NotNull
        public final QrCodeInfo[] newArray(int i) {
            return new QrCodeInfo[i];
        }
    }

    public static /* synthetic */ QrCodeInfo copy$default(QrCodeInfo qrCodeInfo, int i, OptionalQRCodeInfo.OptionalQRCodeInfoType optionalQRCodeInfoType, String str, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = qrCodeInfo.tag;
        }
        if ((i3 & 2) != 0) {
            optionalQRCodeInfoType = qrCodeInfo.type;
        }
        if ((i3 & 4) != 0) {
            str = qrCodeInfo.data;
        }
        if ((i3 & 8) != 0) {
            i2 = qrCodeInfo.intDataValue;
        }
        return qrCodeInfo.copy(i, optionalQRCodeInfoType, str, i2);
    }

    public final int component1() {
        return this.tag;
    }

    @NotNull
    public final OptionalQRCodeInfo.OptionalQRCodeInfoType component2() {
        return this.type;
    }

    @NotNull
    public final String component3() {
        return this.data;
    }

    public final int component4() {
        return this.intDataValue;
    }

    @NotNull
    public final QrCodeInfo copy(int i, @NotNull OptionalQRCodeInfo.OptionalQRCodeInfoType optionalQRCodeInfoType, @NotNull String str, int i2) {
        k.e(optionalQRCodeInfoType, IjkMediaMeta.IJKM_KEY_TYPE);
        k.e(str, "data");
        return new QrCodeInfo(i, optionalQRCodeInfoType, str, i2);
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof QrCodeInfo)) {
            return false;
        }
        QrCodeInfo qrCodeInfo = (QrCodeInfo) obj;
        return this.tag == qrCodeInfo.tag && this.type == qrCodeInfo.type && k.a(this.data, qrCodeInfo.data) && this.intDataValue == qrCodeInfo.intDataValue;
    }

    public int hashCode() {
        return (((((this.tag * 31) + this.type.hashCode()) * 31) + this.data.hashCode()) * 31) + this.intDataValue;
    }

    @NotNull
    public String toString() {
        return "QrCodeInfo(tag=" + this.tag + ", type=" + this.type + ", data=" + this.data + ", intDataValue=" + this.intDataValue + ')';
    }

    public void writeToParcel(@NotNull Parcel parcel, int i) {
        k.e(parcel, "out");
        parcel.writeInt(this.tag);
        parcel.writeString(this.type.name());
        parcel.writeString(this.data);
        parcel.writeInt(this.intDataValue);
    }

    public QrCodeInfo(int tag2, @NotNull OptionalQRCodeInfo.OptionalQRCodeInfoType type2, @NotNull String data2, int intDataValue2) {
        k.e(type2, IjkMediaMeta.IJKM_KEY_TYPE);
        k.e(data2, "data");
        this.tag = tag2;
        this.type = type2;
        this.data = data2;
        this.intDataValue = intDataValue2;
    }

    public final int getTag() {
        return this.tag;
    }

    @NotNull
    public final OptionalQRCodeInfo.OptionalQRCodeInfoType getType() {
        return this.type;
    }

    @NotNull
    public final String getData() {
        return this.data;
    }

    public final int getIntDataValue() {
        return this.intDataValue;
    }
}
