package kotlin.reflect.jvm.internal.impl.descriptors.annotations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import kotlin.collections.l;
import kotlin.collections.l0;
import kotlin.collections.y;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.t;
import org.jetbrains.annotations.NotNull;

/* compiled from: KotlinTarget.kt */
public enum n {
    CLASS("class", false, 2, (boolean) null),
    ANNOTATION_CLASS("annotation class", false, 2, (boolean) null),
    TYPE_PARAMETER("type parameter", false),
    PROPERTY("property", false, 2, (boolean) null),
    FIELD("field", false, 2, (boolean) null),
    LOCAL_VARIABLE("local variable", false, 2, (boolean) null),
    VALUE_PARAMETER("value parameter", false, 2, (boolean) null),
    CONSTRUCTOR("constructor", false, 2, (boolean) null),
    FUNCTION("function", false, 2, (boolean) null),
    PROPERTY_GETTER("getter", false, 2, (boolean) null),
    PROPERTY_SETTER("setter", false, 2, (boolean) null),
    TYPE("type usage", false),
    EXPRESSION("expression", false),
    FILE("file", false),
    TYPEALIAS("typealias", false),
    TYPE_PROJECTION("type projection", false),
    STAR_PROJECTION("star projection", false),
    PROPERTY_PARAMETER("property constructor parameter", false),
    CLASS_ONLY("class", false),
    OBJECT("object", false),
    COMPANION_OBJECT("companion object", false),
    INTERFACE("interface", false),
    ENUM_CLASS("enum class", false),
    ENUM_ENTRY("enum entry", false),
    LOCAL_CLASS("local class", false),
    LOCAL_FUNCTION("local function", false),
    MEMBER_FUNCTION("member function", false),
    TOP_LEVEL_FUNCTION("top level function", false),
    MEMBER_PROPERTY("member property", false),
    MEMBER_PROPERTY_WITH_BACKING_FIELD("member property with backing field", false),
    MEMBER_PROPERTY_WITH_DELEGATE("member property with delegate", false),
    MEMBER_PROPERTY_WITHOUT_FIELD_OR_DELEGATE("member property without backing field or delegate", false),
    TOP_LEVEL_PROPERTY("top level property", false),
    TOP_LEVEL_PROPERTY_WITH_BACKING_FIELD("top level property with backing field", false),
    TOP_LEVEL_PROPERTY_WITH_DELEGATE("top level property with delegate", false),
    TOP_LEVEL_PROPERTY_WITHOUT_FIELD_OR_DELEGATE("top level property without backing field or delegate", false),
    INITIALIZER("initializer", false),
    DESTRUCTURING_DECLARATION("destructuring declaration", false),
    LAMBDA_EXPRESSION("lambda expression", false),
    ANONYMOUS_FUNCTION("anonymous function", false),
    OBJECT_LITERAL("object literal", false);
    
    public static final a Companion = null;
    private static final HashMap<String, n> d = null;
    @NotNull
    private static final Set<n> f = null;
    @NotNull
    private static final Set<n> q = null;
    @NotNull
    private static final Map<e, n> x = null;
    @NotNull
    private final String description;
    private final boolean isDefault;

    private n(String description2, boolean isDefault2) {
        this.description = description2;
        this.isDefault = isDefault2;
    }

    static {
        Companion = new a((DefaultConstructorMarker) null);
        d = new HashMap<>();
        for (n target : values()) {
            d.put(target.name(), target);
        }
        n[] values = values();
        Collection destination$iv$iv = new ArrayList();
        for (n it : values) {
            if (it.isDefault) {
                destination$iv$iv.add(it);
            }
        }
        f = y.H0(destination$iv$iv);
        q = l.g0(values());
        e eVar = e.CONSTRUCTOR_PARAMETER;
        n nVar = VALUE_PARAMETER;
        e eVar2 = e.FIELD;
        n nVar2 = FIELD;
        x = l0.h(t.a(eVar, nVar), t.a(eVar2, nVar2), t.a(e.PROPERTY, PROPERTY), t.a(e.FILE, FILE), t.a(e.PROPERTY_GETTER, PROPERTY_GETTER), t.a(e.PROPERTY_SETTER, PROPERTY_SETTER), t.a(e.RECEIVER, nVar), t.a(e.SETTER_PARAMETER, nVar), t.a(e.PROPERTY_DELEGATE_FIELD, nVar2));
    }

    /* compiled from: KotlinTarget.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
