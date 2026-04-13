package me.jessyan.autosize;

import android.os.Parcel;
import android.os.Parcelable;

public class DisplayMetricsInfo implements Parcelable {
    public static final Parcelable.Creator<DisplayMetricsInfo> CREATOR = new Parcelable.Creator<DisplayMetricsInfo>() {
        public DisplayMetricsInfo createFromParcel(Parcel source) {
            return new DisplayMetricsInfo(source);
        }

        public DisplayMetricsInfo[] newArray(int size) {
            return new DisplayMetricsInfo[size];
        }
    };
    private float density;
    private int densityDpi;
    private float scaledDensity;
    private int screenHeightDp;
    private int screenWidthDp;
    private float xdpi;

    public DisplayMetricsInfo(float density2, int densityDpi2, float scaledDensity2, float xdpi2) {
        this.density = density2;
        this.densityDpi = densityDpi2;
        this.scaledDensity = scaledDensity2;
        this.xdpi = xdpi2;
    }

    public DisplayMetricsInfo(float density2, int densityDpi2, float scaledDensity2, float xdpi2, int screenWidthDp2, int screenHeightDp2) {
        this.density = density2;
        this.densityDpi = densityDpi2;
        this.scaledDensity = scaledDensity2;
        this.xdpi = xdpi2;
        this.screenWidthDp = screenWidthDp2;
        this.screenHeightDp = screenHeightDp2;
    }

    public float getDensity() {
        return this.density;
    }

    public void setDensity(float density2) {
        this.density = density2;
    }

    public int getDensityDpi() {
        return this.densityDpi;
    }

    public void setDensityDpi(int densityDpi2) {
        this.densityDpi = densityDpi2;
    }

    public float getScaledDensity() {
        return this.scaledDensity;
    }

    public void setScaledDensity(float scaledDensity2) {
        this.scaledDensity = scaledDensity2;
    }

    public float getXdpi() {
        return this.xdpi;
    }

    public void setXdpi(float xdpi2) {
        this.xdpi = xdpi2;
    }

    public int getScreenWidthDp() {
        return this.screenWidthDp;
    }

    public void setScreenWidthDp(int screenWidthDp2) {
        this.screenWidthDp = screenWidthDp2;
    }

    public int getScreenHeightDp() {
        return this.screenHeightDp;
    }

    public void setScreenHeightDp(int screenHeightDp2) {
        this.screenHeightDp = screenHeightDp2;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(this.density);
        dest.writeInt(this.densityDpi);
        dest.writeFloat(this.scaledDensity);
        dest.writeFloat(this.xdpi);
        dest.writeInt(this.screenWidthDp);
        dest.writeInt(this.screenHeightDp);
    }

    protected DisplayMetricsInfo(Parcel in) {
        this.density = in.readFloat();
        this.densityDpi = in.readInt();
        this.scaledDensity = in.readFloat();
        this.xdpi = in.readFloat();
        this.screenWidthDp = in.readInt();
        this.screenHeightDp = in.readInt();
    }

    public String toString() {
        return "DisplayMetricsInfo{density=" + this.density + ", densityDpi=" + this.densityDpi + ", scaledDensity=" + this.scaledDensity + ", xdpi=" + this.xdpi + ", screenWidthDp=" + this.screenWidthDp + ", screenHeightDp=" + this.screenHeightDp + '}';
    }
}
