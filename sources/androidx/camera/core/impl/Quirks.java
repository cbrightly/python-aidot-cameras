package androidx.camera.core.impl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class Quirks {
    @NonNull
    private final List<Quirk> mQuirks;

    public Quirks(@NonNull List<Quirk> quirks) {
        this.mQuirks = new ArrayList(quirks);
    }

    @Nullable
    public <T extends Quirk> T get(@NonNull Class<T> quirkClass) {
        for (Quirk quirk : this.mQuirks) {
            if (quirk.getClass() == quirkClass) {
                return quirk;
            }
        }
        return null;
    }

    public boolean contains(@NonNull Class<? extends Quirk> quirkClass) {
        for (Quirk quirk : this.mQuirks) {
            if (quirkClass.isAssignableFrom(quirk.getClass())) {
                return true;
            }
        }
        return false;
    }
}
