package kotlin.reflect.jvm.internal.impl.descriptors.annotations;

import kotlin.TypeCastException;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: AnnotationUseSiteTarget.kt */
public enum e {
    FIELD((String) null, 1, (String) null),
    FILE((String) null, 1, (String) null),
    PROPERTY((String) null, 1, (String) null),
    PROPERTY_GETTER("get"),
    PROPERTY_SETTER("set"),
    RECEIVER((String) null, 1, (String) null),
    CONSTRUCTOR_PARAMETER("param"),
    SETTER_PARAMETER("setparam"),
    PROPERTY_DELEGATE_FIELD("delegate");
    
    @NotNull
    private final String renderName;

    private e(String renderName2) {
        String str;
        if (renderName2 != null) {
            str = renderName2;
        } else {
            String name = name();
            if (name != null) {
                str = name.toLowerCase();
                k.b(str, "(this as java.lang.String).toLowerCase()");
            } else {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
        }
        this.renderName = str;
    }

    @NotNull
    public final String getRenderName() {
        return this.renderName;
    }
}
