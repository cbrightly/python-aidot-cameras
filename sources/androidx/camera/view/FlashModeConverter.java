package androidx.camera.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public final class FlashModeConverter {
    private FlashModeConverter() {
    }

    public static int valueOf(@Nullable String name) {
        if (name != null) {
            char c = 65535;
            switch (name.hashCode()) {
                case 2527:
                    if (name.equals("ON")) {
                        c = 1;
                        break;
                    }
                    break;
                case 78159:
                    if (name.equals("OFF")) {
                        c = 2;
                        break;
                    }
                    break;
                case 2020783:
                    if (name.equals("AUTO")) {
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
                case 2:
                    return 2;
                default:
                    throw new IllegalArgumentException("Unknown flash mode name " + name);
            }
        } else {
            throw new NullPointerException("name cannot be null");
        }
    }

    @NonNull
    public static String nameOf(int flashMode) {
        switch (flashMode) {
            case 0:
                return "AUTO";
            case 1:
                return "ON";
            case 2:
                return "OFF";
            default:
                throw new IllegalArgumentException("Unknown flash mode " + flashMode);
        }
    }
}
