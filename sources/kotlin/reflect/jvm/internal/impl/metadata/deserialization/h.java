package kotlin.reflect.jvm.internal.impl.metadata.deserialization;

import java.util.ArrayList;
import java.util.List;
import kotlin.collections.r;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.metadata.q;
import kotlin.reflect.jvm.internal.impl.metadata.t;
import org.jetbrains.annotations.NotNull;

/* compiled from: TypeTable.kt */
public final class h {
    @NotNull
    private final List<q> a;

    public h(@NotNull t typeTable) {
        h $this$run;
        k.f(typeTable, "typeTable");
        h $this$run2 = this;
        List originalTypes = typeTable.getTypeList();
        if (typeTable.hasFirstNullable()) {
            int firstNullable = typeTable.getFirstNullable();
            Iterable $this$mapIndexedTo$iv$iv = typeTable.getTypeList();
            k.b($this$mapIndexedTo$iv$iv, "typeTable.typeList");
            List arrayList = new ArrayList(r.r($this$mapIndexedTo$iv$iv, 10));
            int index$iv$iv = 0;
            for (T next : $this$mapIndexedTo$iv$iv) {
                int index$iv$iv2 = index$iv$iv + 1;
                if (index$iv$iv < 0) {
                    kotlin.collections.q.q();
                }
                q type = (q) next;
                if (index$iv$iv >= firstNullable) {
                    $this$run = $this$run2;
                    type = type.toBuilder().O(true).build();
                } else {
                    $this$run = $this$run2;
                }
                arrayList.add(type);
                t tVar = typeTable;
                index$iv$iv = index$iv$iv2;
                $this$run2 = $this$run;
            }
            originalTypes = arrayList;
        } else {
            k.b(originalTypes, "originalTypes");
        }
        this.a = originalTypes;
    }

    @NotNull
    public final q a(int index) {
        return this.a.get(index);
    }
}
