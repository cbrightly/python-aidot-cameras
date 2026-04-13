package kotlin.reflect.jvm.internal.impl.metadata.deserialization;

import java.util.LinkedList;
import java.util.List;
import kotlin.collections.y;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.metadata.o;
import kotlin.reflect.jvm.internal.impl.metadata.p;
import kotlin.s;
import org.jetbrains.annotations.NotNull;

/* compiled from: NameResolverImpl.kt */
public final class e implements c {
    private final p a;
    private final o b;

    public e(@NotNull p strings, @NotNull o qualifiedNames) {
        k.f(strings, "strings");
        k.f(qualifiedNames, "qualifiedNames");
        this.a = strings;
        this.b = qualifiedNames;
    }

    @NotNull
    public String getString(int index) {
        String string = this.a.getString(index);
        k.b(string, "strings.getString(index)");
        return string;
    }

    @NotNull
    public String b(int index) {
        s<List<String>, List<String>, Boolean> c = c(index);
        List packageFqNameSegments = c.component1();
        String className = y.b0(c.component2(), ".", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (l) null, 62, (Object) null);
        if (packageFqNameSegments.isEmpty()) {
            return className;
        }
        return y.b0(packageFqNameSegments, "/", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (l) null, 62, (Object) null) + '/' + className;
    }

    public boolean a(int index) {
        return c(index).getThird().booleanValue();
    }

    private final s<List<String>, List<String>, Boolean> c(int startingIndex) {
        int index = startingIndex;
        LinkedList packageNameSegments = new LinkedList();
        LinkedList relativeClassNameSegments = new LinkedList();
        boolean local = false;
        while (index != -1) {
            o.c proto = this.b.getQualifiedName(index);
            p pVar = this.a;
            k.b(proto, "proto");
            String shortName = pVar.getString(proto.getShortName());
            o.c.C0396c kind = proto.getKind();
            if (kind == null) {
                k.n();
            }
            switch (d.a[kind.ordinal()]) {
                case 1:
                    relativeClassNameSegments.addFirst(shortName);
                    break;
                case 2:
                    packageNameSegments.addFirst(shortName);
                    break;
                case 3:
                    relativeClassNameSegments.addFirst(shortName);
                    local = true;
                    break;
            }
            index = proto.getParentQualifiedName();
        }
        return new s<>(packageNameSegments, relativeClassNameSegments, Boolean.valueOf(local));
    }
}
