package android.support.v4.app;

import androidx.annotation.RestrictTo;
import androidx.core.app.RemoteActionCompat;
import androidx.versionedparcelable.VersionedParcel;

@RestrictTo({RestrictTo.Scope.LIBRARY})
public final class RemoteActionCompatParcelizer extends androidx.core.app.RemoteActionCompatParcelizer {
    public static RemoteActionCompat read(VersionedParcel parcel) {
        return androidx.core.app.RemoteActionCompatParcelizer.read(parcel);
    }

    public static void write(RemoteActionCompat obj, VersionedParcel parcel) {
        androidx.core.app.RemoteActionCompatParcelizer.write(obj, parcel);
    }
}
