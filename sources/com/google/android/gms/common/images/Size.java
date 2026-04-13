package com.google.android.gms.common.images;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
public final class Size {
    private final int zaa;
    private final int zab;

    public Size(int i, int i2) {
        this.zaa = i;
        this.zab = i2;
    }

    @NonNull
    public static Size parseSize(@NonNull String string) {
        if (string != null) {
            int indexOf = string.indexOf(42);
            if (indexOf < 0) {
                indexOf = string.indexOf(120);
            }
            if (indexOf >= 0) {
                try {
                    return new Size(Integer.parseInt(string.substring(0, indexOf)), Integer.parseInt(string.substring(indexOf + 1)));
                } catch (NumberFormatException e) {
                    throw zaa(string);
                }
            } else {
                throw zaa(string);
            }
        } else {
            throw new IllegalArgumentException("string must not be null");
        }
    }

    private static NumberFormatException zaa(String str) {
        throw new NumberFormatException("Invalid Size: \"" + str + "\"");
    }

    public boolean equals(@Nullable Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof Size) {
            Size size = (Size) obj;
            return this.zaa == size.zaa && this.zab == size.zab;
        }
    }

    public int getHeight() {
        return this.zab;
    }

    public int getWidth() {
        return this.zaa;
    }

    public int hashCode() {
        int i = this.zab;
        int i2 = this.zaa;
        return i ^ ((i2 >>> 16) | (i2 << 16));
    }

    @NonNull
    public String toString() {
        int i = this.zaa;
        int i2 = this.zab;
        return i + "x" + i2;
    }
}
