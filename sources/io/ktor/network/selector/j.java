package io.ktor.network.selector;

import java.util.ArrayList;
import java.util.Collection;
import kotlin.collections.y;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;

/* compiled from: SelectorManager.kt */
public enum j {
    READ(1),
    WRITE(4),
    ACCEPT(16),
    CONNECT(8);
    
    public static final a Companion = null;
    /* access modifiers changed from: private */
    @NotNull
    public static final j[] d = null;
    /* access modifiers changed from: private */
    @NotNull
    public static final int[] f = null;
    /* access modifiers changed from: private */
    public static final int q = 0;
    private final int flag;

    private j(int flag2) {
        this.flag = flag2;
    }

    public final int getFlag() {
        return this.flag;
    }

    static {
        int i;
        Companion = new a((DefaultConstructorMarker) null);
        d = values();
        j[] values = values();
        Collection destination$iv$iv = new ArrayList(values.length);
        for (j it : values) {
            destination$iv$iv.add(Integer.valueOf(it.flag));
        }
        f = y.B0(destination$iv$iv);
        q = values().length;
    }

    /* compiled from: SelectorManager.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final j[] a() {
            return j.d;
        }

        @NotNull
        public final int[] b() {
            return j.f;
        }
    }
}
