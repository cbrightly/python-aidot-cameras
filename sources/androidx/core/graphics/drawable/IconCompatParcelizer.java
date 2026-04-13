package androidx.core.graphics.drawable;

import android.content.res.ColorStateList;
import android.os.Parcelable;
import androidx.annotation.RestrictTo;
import androidx.versionedparcelable.VersionedParcel;

@RestrictTo({RestrictTo.Scope.LIBRARY})
public class IconCompatParcelizer {
    public static IconCompat read(VersionedParcel parcel) {
        IconCompat obj = new IconCompat();
        obj.mType = parcel.readInt(obj.mType, 1);
        obj.mData = parcel.readByteArray(obj.mData, 2);
        obj.mParcelable = parcel.readParcelable(obj.mParcelable, 3);
        obj.mInt1 = parcel.readInt(obj.mInt1, 4);
        obj.mInt2 = parcel.readInt(obj.mInt2, 5);
        obj.mTintList = (ColorStateList) parcel.readParcelable(obj.mTintList, 6);
        obj.mTintModeStr = parcel.readString(obj.mTintModeStr, 7);
        obj.mString1 = parcel.readString(obj.mString1, 8);
        obj.onPostParceling();
        return obj;
    }

    public static void write(IconCompat obj, VersionedParcel parcel) {
        parcel.setSerializationFlags(true, true);
        obj.onPreParceling(parcel.isStream());
        int i = obj.mType;
        if (-1 != i) {
            parcel.writeInt(i, 1);
        }
        byte[] bArr = obj.mData;
        if (bArr != null) {
            parcel.writeByteArray(bArr, 2);
        }
        Parcelable parcelable = obj.mParcelable;
        if (parcelable != null) {
            parcel.writeParcelable(parcelable, 3);
        }
        int i2 = obj.mInt1;
        if (i2 != 0) {
            parcel.writeInt(i2, 4);
        }
        int i3 = obj.mInt2;
        if (i3 != 0) {
            parcel.writeInt(i3, 5);
        }
        ColorStateList colorStateList = obj.mTintList;
        if (colorStateList != null) {
            parcel.writeParcelable(colorStateList, 6);
        }
        String str = obj.mTintModeStr;
        if (str != null) {
            parcel.writeString(str, 7);
        }
        String str2 = obj.mString1;
        if (str2 != null) {
            parcel.writeString(str2, 8);
        }
    }
}
