package com.amazonaws.mobileconnectors.kinesisvideo.mediasource.android;

import android.os.Parcel;
import android.os.Parcelable;
import com.amazonaws.kinesisvideo.client.mediasource.CameraMediaSourceConfiguration;
import com.amazonaws.kinesisvideo.producer.StreamInfo;

public class AndroidCameraMediaSourceConfiguration extends CameraMediaSourceConfiguration implements Parcelable {
    public static final Parcelable.Creator<AndroidCameraMediaSourceConfiguration> CREATOR = new Parcelable.Creator<AndroidCameraMediaSourceConfiguration>() {
        public AndroidCameraMediaSourceConfiguration createFromParcel(Parcel in) {
            return new AndroidCameraMediaSourceConfiguration(AndroidCameraMediaSourceConfiguration.readFromParcel(in));
        }

        public AndroidCameraMediaSourceConfiguration[] newArray(int size) {
            return new AndroidCameraMediaSourceConfiguration[size];
        }
    };

    public AndroidCameraMediaSourceConfiguration(CameraMediaSourceConfiguration.Builder builder) {
        super(builder);
    }

    public static CameraMediaSourceConfiguration.Builder builder() {
        return new CameraMediaSourceConfiguration.Builder();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(getCameraId());
        parcel.writeString(getOutputFileName());
        parcel.writeString(getEncoderMimeType());
        parcel.writeInt(getFrameRate());
        parcel.writeInt(getHorizontalResolution());
        parcel.writeInt(getVerticalResolution());
        parcel.writeInt(getCameraFacing());
        parcel.writeInt(getCameraOrientation());
        parcel.writeInt(getBitRate());
        parcel.writeInt(getRetentionPeriodInHours());
        parcel.writeString(String.valueOf(isEndcoderHardwareAccelerated()));
        parcel.writeLong(getTimeScale());
        parcel.writeInt(getNalAdaptationFlags().getIntValue());
        parcel.writeString(String.valueOf(getIsAbsoluteTimecode()));
        if (getCodecPrivateData() == null) {
            parcel.writeInt(0);
            return;
        }
        parcel.writeInt(getCodecPrivateData().length);
        parcel.writeByteArray(getCodecPrivateData());
    }

    /* access modifiers changed from: private */
    public static CameraMediaSourceConfiguration.Builder readFromParcel(Parcel parcel) {
        CameraMediaSourceConfiguration.Builder builder = new CameraMediaSourceConfiguration.Builder().withCameraId(parcel.readString()).withFileOutput(parcel.readString()).withEncodingMimeType(parcel.readString()).withFrameRate(parcel.readInt()).withHorizontalResolution(parcel.readInt()).withVerticalResolution(parcel.readInt()).withCameraFacing(parcel.readInt()).withCameraOrientation(parcel.readInt()).withEncodingBitRate(parcel.readInt()).withRetentionPeriodInHours(parcel.readInt()).withIsEncoderHardwareAccelerated(Boolean.parseBoolean(parcel.readString())).withFrameTimeScale(parcel.readLong()).withNalAdaptationFlags(StreamInfo.NalAdaptationFlags.getFlag(parcel.readInt())).withIsAbsoluteTimecode(Boolean.parseBoolean(parcel.readString()));
        int codecPrivateDataSize = parcel.readInt();
        if (codecPrivateDataSize > 0) {
            byte[] privateData = new byte[codecPrivateDataSize];
            parcel.readByteArray(privateData);
            builder.withCodecPrivateData(privateData);
        }
        return builder;
    }
}
