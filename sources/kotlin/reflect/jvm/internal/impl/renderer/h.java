package kotlin.reflect.jvm.internal.impl.renderer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import kotlin.collections.l;
import kotlin.collections.y;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;

/* compiled from: DescriptorRenderer.kt */
public enum h {
    VISIBILITY(true),
    MODALITY(true),
    OVERRIDE(true),
    ANNOTATIONS(false),
    INNER(true),
    MEMBER_KIND(true),
    DATA(true),
    INLINE(true),
    EXPECT(true),
    ACTUAL(true),
    CONST(true),
    LATEINIT(true);
    
    @NotNull
    public static final Set<h> ALL = null;
    @NotNull
    public static final Set<h> ALL_EXCEPT_ANNOTATIONS = null;
    public static final a Companion = null;
    private final boolean includeByDefault;

    private h(boolean includeByDefault2) {
        this.includeByDefault = includeByDefault2;
    }

    static {
        int i;
        Companion = new a((DefaultConstructorMarker) null);
        h[] values = values();
        Collection destination$iv$iv = new ArrayList();
        for (h it : values) {
            if (it.includeByDefault) {
                destination$iv$iv.add(it);
            }
        }
        ALL_EXCEPT_ANNOTATIONS = y.H0(destination$iv$iv);
        ALL = l.g0(values());
    }

    /* compiled from: DescriptorRenderer.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
