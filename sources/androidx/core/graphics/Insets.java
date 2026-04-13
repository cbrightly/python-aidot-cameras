package androidx.core.graphics;

import android.graphics.Rect;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;

public final class Insets {
    @NonNull
    public static final Insets NONE = new Insets(0, 0, 0, 0);
    public final int bottom;
    public final int left;
    public final int right;
    public final int top;

    private Insets(int left2, int top2, int right2, int bottom2) {
        this.left = left2;
        this.top = top2;
        this.right = right2;
        this.bottom = bottom2;
    }

    @NonNull
    public static Insets of(int left2, int top2, int right2, int bottom2) {
        if (left2 == 0 && top2 == 0 && right2 == 0 && bottom2 == 0) {
            return NONE;
        }
        return new Insets(left2, top2, right2, bottom2);
    }

    @NonNull
    public static Insets of(@NonNull Rect r) {
        return of(r.left, r.top, r.right, r.bottom);
    }

    @NonNull
    public static Insets add(@NonNull Insets a, @NonNull Insets b) {
        return of(a.left + b.left, a.top + b.top, a.right + b.right, a.bottom + b.bottom);
    }

    @NonNull
    public static Insets subtract(@NonNull Insets a, @NonNull Insets b) {
        return of(a.left - b.left, a.top - b.top, a.right - b.right, a.bottom - b.bottom);
    }

    @NonNull
    public static Insets max(@NonNull Insets a, @NonNull Insets b) {
        return of(Math.max(a.left, b.left), Math.max(a.top, b.top), Math.max(a.right, b.right), Math.max(a.bottom, b.bottom));
    }

    @NonNull
    public static Insets min(@NonNull Insets a, @NonNull Insets b) {
        return of(Math.min(a.left, b.left), Math.min(a.top, b.top), Math.min(a.right, b.right), Math.min(a.bottom, b.bottom));
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Insets insets = (Insets) o;
        if (this.bottom == insets.bottom && this.left == insets.left && this.right == insets.right && this.top == insets.top) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (((((this.left * 31) + this.top) * 31) + this.right) * 31) + this.bottom;
    }

    public String toString() {
        return "Insets{left=" + this.left + ", top=" + this.top + ", right=" + this.right + ", bottom=" + this.bottom + '}';
    }

    @RequiresApi(api = 29)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    @NonNull
    @Deprecated
    public static Insets wrap(@NonNull android.graphics.Insets insets) {
        return toCompatInsets(insets);
    }

    @RequiresApi(api = 29)
    @NonNull
    public static Insets toCompatInsets(@NonNull android.graphics.Insets insets) {
        return of(insets.left, insets.top, insets.right, insets.bottom);
    }

    @RequiresApi(api = 29)
    @NonNull
    public android.graphics.Insets toPlatformInsets() {
        return android.graphics.Insets.of(this.left, this.top, this.right, this.bottom);
    }
}
