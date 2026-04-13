package me.jessyan.autosize.external;

import android.os.Parcel;
import android.os.Parcelable;

public class ExternalAdaptInfo implements Parcelable {
    public static final Parcelable.Creator<ExternalAdaptInfo> CREATOR = new Parcelable.Creator<ExternalAdaptInfo>() {
        public ExternalAdaptInfo createFromParcel(Parcel source) {
            return new ExternalAdaptInfo(source);
        }

        public ExternalAdaptInfo[] newArray(int size) {
            return new ExternalAdaptInfo[size];
        }
    };
    private boolean isBaseOnWidth;
    private float sizeInDp;

    public ExternalAdaptInfo(boolean isBaseOnWidth2) {
        this.isBaseOnWidth = isBaseOnWidth2;
    }

    public ExternalAdaptInfo(boolean isBaseOnWidth2, float sizeInDp2) {
        this.isBaseOnWidth = isBaseOnWidth2;
        this.sizeInDp = sizeInDp2;
    }

    public boolean isBaseOnWidth() {
        return this.isBaseOnWidth;
    }

    public void setBaseOnWidth(boolean baseOnWidth) {
        this.isBaseOnWidth = baseOnWidth;
    }

    public float getSizeInDp() {
        return this.sizeInDp;
    }

    public void setSizeInDp(float sizeInDp2) {
        this.sizeInDp = sizeInDp2;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.isBaseOnWidth ? (byte) 1 : 0);
        dest.writeFloat(this.sizeInDp);
    }

    protected ExternalAdaptInfo(Parcel in) {
        this.isBaseOnWidth = in.readByte() != 0;
        this.sizeInDp = in.readFloat();
    }

    public String toString() {
        return "ExternalAdaptInfo{isBaseOnWidth=" + this.isBaseOnWidth + ", sizeInDp=" + this.sizeInDp + '}';
    }
}
