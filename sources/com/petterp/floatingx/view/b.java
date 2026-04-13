package com.petterp.floatingx.view;

import android.content.res.Configuration;
import kotlin.jvm.internal.k;
import kotlin.n;
import kotlin.t;
import org.jetbrains.annotations.NotNull;

/* compiled from: FxLocationHelper.kt */
public final class b {
    private com.petterp.floatingx.assist.helper.b a;
    private int b;
    private int c;
    private float d;
    private float e;
    private boolean f;
    private boolean g;
    private boolean h = true;

    public final void d(@NotNull com.petterp.floatingx.assist.helper.b config) {
        k.e(config, "config");
        this.a = config;
    }

    public final boolean f() {
        return this.g;
    }

    public final boolean e() {
        if (!this.h) {
            return false;
        }
        this.h = false;
        return true;
    }

    @NotNull
    public final b g(float x, float y, @NotNull c configHelper) {
        k.e(configHelper, "configHelper");
        this.d = x;
        this.e = y;
        this.f = configHelper.j(x);
        return this;
    }

    public final boolean h(@NotNull Configuration config) {
        boolean isChangedScreen;
        k.e(config, "config");
        int i = config.screenWidthDp;
        if (i == this.b && config.screenHeightDp == this.c) {
            isChangedScreen = false;
        } else {
            this.b = i;
            this.c = config.screenHeightDp;
            isChangedScreen = true;
        }
        this.g = isChangedScreen;
        return isChangedScreen;
    }

    @NotNull
    public final n<Float, Float> a(@NotNull c viewConfig) {
        k.e(viewConfig, "viewConfig");
        float newX = b(viewConfig.e(), viewConfig.e());
        float newY = c(viewConfig.d(), viewConfig.c());
        this.g = false;
        return t.a(Float.valueOf(newX), Float.valueOf(newY));
    }

    private final float b(float min, float max) {
        com.petterp.floatingx.assist.helper.b bVar = this.a;
        if (bVar == null) {
            k.t("config");
            throw null;
        } else if (bVar.n) {
            return this.f ? min : max;
        } else {
            return com.petterp.floatingx.util.b.a(this.d, min, max);
        }
    }

    private final float c(float min, float max) {
        return com.petterp.floatingx.util.b.a(this.e, min, max);
    }
}
