package kotlin.reflect.jvm.internal.impl.resolve;

import java.util.Comparator;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.i0;
import kotlin.reflect.jvm.internal.impl.descriptors.l;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.s0;
import kotlin.reflect.jvm.internal.impl.descriptors.u;
import org.jetbrains.annotations.Nullable;

/* compiled from: MemberComparator */
public class f implements Comparator<m> {
    public static final f c = new f();

    private f() {
    }

    private static int c(m descriptor) {
        if (c.B(descriptor)) {
            return 8;
        }
        if (descriptor instanceof l) {
            return 7;
        }
        if (descriptor instanceof i0) {
            if (((i0) descriptor).L() == null) {
                return 6;
            }
            return 5;
        } else if (descriptor instanceof u) {
            if (((u) descriptor).L() == null) {
                return 4;
            }
            return 3;
        } else if (descriptor instanceof e) {
            return 2;
        } else {
            if (descriptor instanceof s0) {
                return 1;
            }
            return 0;
        }
    }

    /* renamed from: a */
    public int compare(m o1, m o2) {
        Integer compareInternal = b(o1, o2);
        if (compareInternal != null) {
            return compareInternal.intValue();
        }
        return 0;
    }

    @Nullable
    private static Integer b(m o1, m o2) {
        int prioritiesCompareTo = c(o2) - c(o1);
        if (prioritiesCompareTo != 0) {
            return Integer.valueOf(prioritiesCompareTo);
        }
        if (c.B(o1) && c.B(o2)) {
            return 0;
        }
        int namesCompareTo = o1.getName().compareTo(o2.getName());
        if (namesCompareTo != 0) {
            return Integer.valueOf(namesCompareTo);
        }
        return null;
    }
}
