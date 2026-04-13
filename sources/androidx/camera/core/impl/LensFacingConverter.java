package androidx.camera.core.impl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class LensFacingConverter {
    private LensFacingConverter() {
    }

    @NonNull
    public static Integer[] values() {
        return new Integer[]{0, 1};
    }

    public static int valueOf(@Nullable String name) {
        if (name != null) {
            char c = 65535;
            switch (name.hashCode()) {
                case 2030823:
                    if (name.equals("BACK")) {
                        c = 1;
                        break;
                    }
                    break;
                case 67167753:
                    if (name.equals("FRONT")) {
                        c = 0;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    return 0;
                case 1:
                    return 1;
                default:
                    throw new IllegalArgumentException("Unknown len facing name " + name);
            }
        } else {
            throw new NullPointerException("name cannot be null");
        }
    }

    @NonNull
    public static String nameOf(int lensFacing) {
        switch (lensFacing) {
            case 0:
                return "FRONT";
            case 1:
                return "BACK";
            default:
                throw new IllegalArgumentException("Unknown lens facing " + lensFacing);
        }
    }
}
