package com.petterp.floatingx.util;

import org.jetbrains.annotations.NotNull;

/* compiled from: FxScopeEnum.kt */
public enum FxScopeEnum {
    APP_SCOPE("app"),
    ACTIVITY_SCOPE("activity"),
    FRAGMENT_SCOPE("fragment"),
    VIEW_GROUP_SCOPE("view");
    
    @NotNull
    private final String tag;

    private FxScopeEnum(String tag2) {
        this.tag = tag2;
    }

    @NotNull
    public final String getTag() {
        return this.tag;
    }
}
