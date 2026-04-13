package com.petterp.floatingx.assist;

import com.google.android.material.badge.BadgeDrawable;

/* compiled from: FxGravity.kt */
public enum FxGravity {
    DEFAULT(BadgeDrawable.TOP_START, 1),
    LEFT_OR_TOP(BadgeDrawable.TOP_START, 1),
    LEFT_OR_CENTER(8388627, 2),
    LEFT_OR_BOTTOM(BadgeDrawable.BOTTOM_START, 3),
    RIGHT_OR_TOP(BadgeDrawable.TOP_END, 1),
    RIGHT_OR_CENTER(8388629, 2),
    RIGHT_OR_BOTTOM(BadgeDrawable.BOTTOM_END, 3),
    CENTER(17, 2),
    TOP_OR_CENTER(49, 1),
    BOTTOM_OR_CENTER(81, 3);
    
    private final int scope;
    private final int value;

    private FxGravity(int value2, int scope2) {
        this.value = value2;
        this.scope = scope2;
    }

    public final int getScope() {
        return this.scope;
    }

    public final int getValue() {
        return this.value;
    }

    public final boolean isDefault() {
        return this == DEFAULT;
    }
}
