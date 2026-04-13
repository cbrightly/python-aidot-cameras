package com.google.android.libraries.places.widget.model;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.RecentlyNonNull;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public enum AutocompleteActivityMode implements Parcelable {
    FULLSCREEN,
    OVERLAY;
    
    @RecentlyNonNull
    public static final Parcelable.Creator<AutocompleteActivityMode> CREATOR = null;

    static {
        CREATOR = new zza();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(@RecentlyNonNull Parcel dest, int i) {
        dest.writeString(name());
    }
}
