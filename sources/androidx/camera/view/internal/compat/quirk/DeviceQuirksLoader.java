package androidx.camera.view.internal.compat.quirk;

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
        if (PreviewOneThirdWiderQuirk.load()) {
            quirks.add(new PreviewOneThirdWiderQuirk());
        }
        if (SurfaceViewStretchedQuirk.load()) {
            quirks.add(new SurfaceViewStretchedQuirk());
        }
        return quirks;
    }
}
