package androidx.camera.core.internal.compat.quirk;

import androidx.annotation.NonNull;
import androidx.camera.core.impl.Quirk;
import java.util.ArrayList;
import java.util.List;

public class DeviceQuirksLoader {
    private DeviceQuirksLoader() {
    }

    @NonNull
    static List<Quirk> loadQuirks() {
        List<Quirk> quirks = new ArrayList<>();
        if (IncompleteCameraListQuirk.load()) {
            quirks.add(new IncompleteCameraListQuirk());
        }
        if (ImageCaptureRotationOptionQuirk.load()) {
            quirks.add(new ImageCaptureRotationOptionQuirk());
        }
        if (ImageCaptureWashedOutImageQuirk.load()) {
            quirks.add(new ImageCaptureWashedOutImageQuirk());
        }
        return quirks;
    }
}
