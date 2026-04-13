package androidx.camera.core.impl;

import androidx.annotation.Nullable;

public interface CamcorderProfileProvider {
    public static final CamcorderProfileProvider EMPTY = new CamcorderProfileProvider() {
        public boolean hasProfile(int quality) {
            return false;
        }

        @Nullable
        public CamcorderProfileProxy get(int quality) {
            return null;
        }
    };

    @Nullable
    CamcorderProfileProxy get(int i);

    boolean hasProfile(int i);
}
