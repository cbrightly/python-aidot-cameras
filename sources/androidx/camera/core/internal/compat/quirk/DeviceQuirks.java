package androidx.camera.core.internal.compat.quirk;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.impl.Quirk;
import androidx.camera.core.impl.Quirks;

public class DeviceQuirks {
    @NonNull
    private static final Quirks QUIRKS = new Quirks(DeviceQuirksLoader.loadQuirks());

    private DeviceQuirks() {
    }

    @Nullable
    public static <T extends Quirk> T get(@NonNull Class<T> quirkClass) {
        return QUIRKS.get(quirkClass);
    }
}
