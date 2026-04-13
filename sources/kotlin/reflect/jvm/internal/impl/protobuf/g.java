package kotlin.reflect.jvm.internal.impl.protobuf;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.reflect.jvm.internal.impl.protobuf.g.b;
import kotlin.reflect.jvm.internal.impl.protobuf.i;
import kotlin.reflect.jvm.internal.impl.protobuf.j;
import kotlin.reflect.jvm.internal.impl.protobuf.o;
import kotlin.reflect.jvm.internal.impl.protobuf.w;

/* compiled from: FieldSet */
public final class g<FieldDescriptorType extends b<FieldDescriptorType>> {
    private static final g a = new g(true);
    private final t<FieldDescriptorType, Object> b = t.o(16);
    private boolean c;
    private boolean d = false;

    /* compiled from: FieldSet */
    public interface b<T extends b<T>> extends Comparable<T> {
        w.c E();

        boolean J();

        int getNumber();

        o.a n(o.a aVar, o oVar);

        boolean r();

        w.b s();
    }

    private g() {
    }

    private g(boolean dummy) {
        q();
    }

    public static <T extends b<T>> g<T> t() {
        return new g<>();
    }

    public static <T extends b<T>> g<T> g() {
        return a;
    }

    public void q() {
        if (!this.c) {
            this.b.n();
            this.c = true;
        }
    }

    /* renamed from: b */
    public g<FieldDescriptorType> clone() {
        FieldSet<FieldDescriptorType> clone = t();
        for (int i = 0; i < this.b.j(); i++) {
            Map.Entry<FieldDescriptorType, Object> entry = this.b.i(i);
            clone.v((b) entry.getKey(), entry.getValue());
        }
        for (Map.Entry<FieldDescriptorType, Object> entry2 : this.b.k()) {
            clone.v((b) entry2.getKey(), entry2.getValue());
        }
        clone.d = this.d;
        return clone;
    }

    public Iterator<Map.Entry<FieldDescriptorType, Object>> p() {
        if (this.d) {
            return new j.c(this.b.entrySet().iterator());
        }
        return this.b.entrySet().iterator();
    }

    public boolean m(FieldDescriptorType descriptor) {
        if (!descriptor.r()) {
            return this.b.get(descriptor) != null;
        }
        throw new IllegalArgumentException("hasField() can only be called on non-repeated fields.");
    }

    public Object h(FieldDescriptorType descriptor) {
        Object o = this.b.get(descriptor);
        if (o instanceof j) {
            return ((j) o).e();
        }
        return o;
    }

    public void v(FieldDescriptorType descriptor, Object value) {
        if (!descriptor.r()) {
            w(descriptor.s(), value);
        } else if (value instanceof List) {
            List<Object> newList = new ArrayList<>();
            newList.addAll((List) value);
            for (Object element : newList) {
                w(descriptor.s(), element);
            }
            value = newList;
        } else {
            throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
        }
        if (value instanceof j) {
            this.d = true;
        }
        this.b.p(descriptor, value);
    }

    public int j(FieldDescriptorType descriptor) {
        if (descriptor.r()) {
            Object value = h(descriptor);
            if (value == null) {
                return 0;
            }
            return ((List) value).size();
        }
        throw new IllegalArgumentException("getRepeatedField() can only be called on repeated fields.");
    }

    public Object i(FieldDescriptorType descriptor, int index) {
        if (descriptor.r()) {
            Object value = h(descriptor);
            if (value != null) {
                return ((List) value).get(index);
            }
            throw new IndexOutOfBoundsException();
        }
        throw new IllegalArgumentException("getRepeatedField() can only be called on repeated fields.");
    }

    public void a(FieldDescriptorType descriptor, Object value) {
        List<Object> list;
        if (descriptor.r()) {
            w(descriptor.s(), value);
            Object existingValue = h(descriptor);
            if (existingValue == null) {
                list = new ArrayList<>();
                this.b.p(descriptor, list);
            } else {
                list = (List) existingValue;
            }
            list.add(value);
            return;
        }
        throw new IllegalArgumentException("addRepeatedField() can only be called on repeated fields.");
    }

    private static void w(w.b type, Object value) {
        if (value != null) {
            boolean isValid = false;
            boolean z = false;
            switch (a.a[type.getJavaType().ordinal()]) {
                case 1:
                    isValid = value instanceof Integer;
                    break;
                case 2:
                    isValid = value instanceof Long;
                    break;
                case 3:
                    isValid = value instanceof Float;
                    break;
                case 4:
                    isValid = value instanceof Double;
                    break;
                case 5:
                    isValid = value instanceof Boolean;
                    break;
                case 6:
                    isValid = value instanceof String;
                    break;
                case 7:
                    if ((value instanceof d) || (value instanceof byte[])) {
                        z = true;
                    }
                    isValid = z;
                    break;
                case 8:
                    if ((value instanceof Integer) || (value instanceof i.a)) {
                        z = true;
                    }
                    isValid = z;
                    break;
                case 9:
                    if ((value instanceof o) || (value instanceof j)) {
                        z = true;
                    }
                    isValid = z;
                    break;
            }
            if (!isValid) {
                throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
            }
            return;
        }
        throw new NullPointerException();
    }

    public boolean n() {
        for (int i = 0; i < this.b.j(); i++) {
            if (!o(this.b.i(i))) {
                return false;
            }
        }
        for (Map.Entry<FieldDescriptorType, Object> entry : this.b.k()) {
            if (!o(entry)) {
                return false;
            }
        }
        return true;
    }

    private boolean o(Map.Entry<FieldDescriptorType, Object> entry) {
        FieldDescriptorType descriptor = (b) entry.getKey();
        if (descriptor.E() == w.c.MESSAGE) {
            if (descriptor.r()) {
                for (o element : (List) entry.getValue()) {
                    if (!element.isInitialized()) {
                        return false;
                    }
                }
            } else {
                Object value = entry.getValue();
                if (value instanceof o) {
                    if (!((o) value).isInitialized()) {
                        return false;
                    }
                } else if (value instanceof j) {
                    return true;
                } else {
                    throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
                }
            }
        }
        return true;
    }

    static int l(w.b type, boolean isPacked) {
        if (isPacked) {
            return 2;
        }
        return type.getWireType();
    }

    public void r(g<FieldDescriptorType> other) {
        for (int i = 0; i < other.b.j(); i++) {
            s(other.b.i(i));
        }
        for (Map.Entry<FieldDescriptorType, Object> entry : other.b.k()) {
            s(entry);
        }
    }

    private Object c(Object value) {
        if (!(value instanceof byte[])) {
            return value;
        }
        byte[] bytes = (byte[]) value;
        byte[] copy = new byte[bytes.length];
        System.arraycopy(bytes, 0, copy, 0, bytes.length);
        return copy;
    }

    private void s(Map.Entry<FieldDescriptorType, Object> entry) {
        FieldDescriptorType descriptor = (b) entry.getKey();
        Object otherValue = entry.getValue();
        if (otherValue instanceof j) {
            otherValue = ((j) otherValue).e();
        }
        if (descriptor.r()) {
            Object value = h(descriptor);
            if (value == null) {
                value = new ArrayList();
            }
            for (Object element : (List) otherValue) {
                ((List) value).add(c(element));
            }
            this.b.p(descriptor, value);
        } else if (descriptor.E() == w.c.MESSAGE) {
            Object value2 = h(descriptor);
            if (value2 == null) {
                this.b.p(descriptor, c(otherValue));
                return;
            }
            this.b.p(descriptor, descriptor.n(((o) value2).toBuilder(), (o) otherValue).build());
        } else {
            this.b.p(descriptor, c(otherValue));
        }
    }

    /* compiled from: FieldSet */
    public static /* synthetic */ class a {
        static final /* synthetic */ int[] a;
        static final /* synthetic */ int[] b;

        static {
            int[] iArr = new int[w.b.values().length];
            b = iArr;
            try {
                iArr[w.b.DOUBLE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                b[w.b.FLOAT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                b[w.b.INT64.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                b[w.b.UINT64.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                b[w.b.INT32.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                b[w.b.FIXED64.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                b[w.b.FIXED32.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                b[w.b.BOOL.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                b[w.b.STRING.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                b[w.b.BYTES.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
            try {
                b[w.b.UINT32.ordinal()] = 11;
            } catch (NoSuchFieldError e11) {
            }
            try {
                b[w.b.SFIXED32.ordinal()] = 12;
            } catch (NoSuchFieldError e12) {
            }
            try {
                b[w.b.SFIXED64.ordinal()] = 13;
            } catch (NoSuchFieldError e13) {
            }
            try {
                b[w.b.SINT32.ordinal()] = 14;
            } catch (NoSuchFieldError e14) {
            }
            try {
                b[w.b.SINT64.ordinal()] = 15;
            } catch (NoSuchFieldError e15) {
            }
            try {
                b[w.b.GROUP.ordinal()] = 16;
            } catch (NoSuchFieldError e16) {
            }
            try {
                b[w.b.MESSAGE.ordinal()] = 17;
            } catch (NoSuchFieldError e17) {
            }
            try {
                b[w.b.ENUM.ordinal()] = 18;
            } catch (NoSuchFieldError e18) {
            }
            int[] iArr2 = new int[w.c.values().length];
            a = iArr2;
            try {
                iArr2[w.c.INT.ordinal()] = 1;
            } catch (NoSuchFieldError e19) {
            }
            try {
                a[w.c.LONG.ordinal()] = 2;
            } catch (NoSuchFieldError e20) {
            }
            try {
                a[w.c.FLOAT.ordinal()] = 3;
            } catch (NoSuchFieldError e21) {
            }
            try {
                a[w.c.DOUBLE.ordinal()] = 4;
            } catch (NoSuchFieldError e22) {
            }
            try {
                a[w.c.BOOLEAN.ordinal()] = 5;
            } catch (NoSuchFieldError e23) {
            }
            try {
                a[w.c.STRING.ordinal()] = 6;
            } catch (NoSuchFieldError e24) {
            }
            try {
                a[w.c.BYTE_STRING.ordinal()] = 7;
            } catch (NoSuchFieldError e25) {
            }
            try {
                a[w.c.ENUM.ordinal()] = 8;
            } catch (NoSuchFieldError e26) {
            }
            try {
                a[w.c.MESSAGE.ordinal()] = 9;
            } catch (NoSuchFieldError e27) {
            }
        }
    }

    public static Object u(e input, w.b type, boolean checkUtf8) {
        switch (a.b[type.ordinal()]) {
            case 1:
                return Double.valueOf(input.m());
            case 2:
                return Float.valueOf(input.q());
            case 3:
                return Long.valueOf(input.t());
            case 4:
                return Long.valueOf(input.M());
            case 5:
                return Integer.valueOf(input.s());
            case 6:
                return Long.valueOf(input.p());
            case 7:
                return Integer.valueOf(input.o());
            case 8:
                return Boolean.valueOf(input.k());
            case 9:
                if (checkUtf8) {
                    return input.J();
                }
                return input.I();
            case 10:
                return input.l();
            case 11:
                return Integer.valueOf(input.L());
            case 12:
                return Integer.valueOf(input.E());
            case 13:
                return Long.valueOf(input.F());
            case 14:
                return Integer.valueOf(input.G());
            case 15:
                return Long.valueOf(input.H());
            case 16:
                throw new IllegalArgumentException("readPrimitiveField() cannot handle nested groups.");
            case 17:
                throw new IllegalArgumentException("readPrimitiveField() cannot handle embedded messages.");
            case 18:
                throw new IllegalArgumentException("readPrimitiveField() cannot handle enums.");
            default:
                throw new RuntimeException("There is no way to get here, but the compiler thinks otherwise.");
        }
    }

    private static void x(CodedOutputStream output, w.b type, int number, Object value) {
        if (type == w.b.GROUP) {
            output.Y(number, (o) value);
            return;
        }
        output.w0(number, l(type, false));
        y(output, type, value);
    }

    private static void y(CodedOutputStream output, w.b type, Object value) {
        switch (a.b[type.ordinal()]) {
            case 1:
                output.R(((Double) value).doubleValue());
                return;
            case 2:
                output.X(((Float) value).floatValue());
                return;
            case 3:
                output.c0(((Long) value).longValue());
                return;
            case 4:
                output.z0(((Long) value).longValue());
                return;
            case 5:
                output.b0(((Integer) value).intValue());
                return;
            case 6:
                output.V(((Long) value).longValue());
                return;
            case 7:
                output.U(((Integer) value).intValue());
                return;
            case 8:
                output.M(((Boolean) value).booleanValue());
                return;
            case 9:
                output.v0((String) value);
                return;
            case 10:
                if (value instanceof d) {
                    output.P((d) value);
                    return;
                } else {
                    output.N((byte[]) value);
                    return;
                }
            case 11:
                output.y0(((Integer) value).intValue());
                return;
            case 12:
                output.q0(((Integer) value).intValue());
                return;
            case 13:
                output.r0(((Long) value).longValue());
                return;
            case 14:
                output.s0(((Integer) value).intValue());
                return;
            case 15:
                output.u0(((Long) value).longValue());
                return;
            case 16:
                output.Z((o) value);
                return;
            case 17:
                output.e0((o) value);
                return;
            case 18:
                if (value instanceof i.a) {
                    output.T(((i.a) value).getNumber());
                    return;
                } else {
                    output.T(((Integer) value).intValue());
                    return;
                }
            default:
                return;
        }
    }

    public static void z(b<?> descriptor, Object value, CodedOutputStream output) {
        w.b type = descriptor.s();
        int number = descriptor.getNumber();
        if (descriptor.r()) {
            List<?> valueList = (List) value;
            if (descriptor.J()) {
                output.w0(number, 2);
                int dataSize = 0;
                for (Object element : valueList) {
                    dataSize += e(type, element);
                }
                output.o0(dataSize);
                for (Object element2 : valueList) {
                    y(output, type, element2);
                }
                return;
            }
            for (Object element3 : valueList) {
                x(output, type, number, element3);
            }
        } else if (value instanceof j) {
            x(output, type, number, ((j) value).e());
        } else {
            x(output, type, number, value);
        }
    }

    public int k() {
        int size = 0;
        for (int i = 0; i < this.b.j(); i++) {
            Map.Entry<FieldDescriptorType, Object> entry = this.b.i(i);
            size += f((b) entry.getKey(), entry.getValue());
        }
        for (Map.Entry<FieldDescriptorType, Object> entry2 : this.b.k()) {
            size += f((b) entry2.getKey(), entry2.getValue());
        }
        return size;
    }

    private static int d(w.b type, int number, Object value) {
        int tagSize = CodedOutputStream.D(number);
        if (type == w.b.GROUP) {
            tagSize *= 2;
        }
        return e(type, value) + tagSize;
    }

    private static int e(w.b type, Object value) {
        switch (a.b[type.ordinal()]) {
            case 1:
                return CodedOutputStream.g(((Double) value).doubleValue());
            case 2:
                return CodedOutputStream.m(((Float) value).floatValue());
            case 3:
                return CodedOutputStream.q(((Long) value).longValue());
            case 4:
                return CodedOutputStream.F(((Long) value).longValue());
            case 5:
                return CodedOutputStream.p(((Integer) value).intValue());
            case 6:
                return CodedOutputStream.k(((Long) value).longValue());
            case 7:
                return CodedOutputStream.j(((Integer) value).intValue());
            case 8:
                return CodedOutputStream.b(((Boolean) value).booleanValue());
            case 9:
                return CodedOutputStream.C((String) value);
            case 10:
                if (value instanceof d) {
                    return CodedOutputStream.e((d) value);
                }
                return CodedOutputStream.c((byte[]) value);
            case 11:
                return CodedOutputStream.E(((Integer) value).intValue());
            case 12:
                return CodedOutputStream.x(((Integer) value).intValue());
            case 13:
                return CodedOutputStream.y(((Long) value).longValue());
            case 14:
                return CodedOutputStream.z(((Integer) value).intValue());
            case 15:
                return CodedOutputStream.B(((Long) value).longValue());
            case 16:
                return CodedOutputStream.n((o) value);
            case 17:
                if (value instanceof j) {
                    return CodedOutputStream.r((j) value);
                }
                return CodedOutputStream.t((o) value);
            case 18:
                if (value instanceof i.a) {
                    return CodedOutputStream.i(((i.a) value).getNumber());
                }
                return CodedOutputStream.i(((Integer) value).intValue());
            default:
                throw new RuntimeException("There is no way to get here, but the compiler thinks otherwise.");
        }
    }

    public static int f(b<?> descriptor, Object value) {
        w.b type = descriptor.s();
        int number = descriptor.getNumber();
        if (!descriptor.r()) {
            return d(type, number, value);
        }
        if (descriptor.J()) {
            int dataSize = 0;
            for (Object element : (List) value) {
                dataSize += e(type, element);
            }
            return CodedOutputStream.D(number) + dataSize + CodedOutputStream.v(dataSize);
        }
        int size = 0;
        for (Object element2 : (List) value) {
            size += d(type, number, element2);
        }
        return size;
    }
}
