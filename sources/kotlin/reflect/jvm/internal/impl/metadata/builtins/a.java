package kotlin.reflect.jvm.internal.impl.metadata.builtins;

import java.io.DataInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import kotlin.collections.g0;
import kotlin.collections.r;
import kotlin.collections.y;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.ranges.i;
import org.jetbrains.annotations.NotNull;

/* compiled from: BuiltInsBinaryVersion.kt */
public final class a extends kotlin.reflect.jvm.internal.impl.metadata.deserialization.a {
    @NotNull
    public static final a g = new a(1, 0, 7);
    @NotNull
    public static final a h = new a(new int[0]);
    public static final C0383a i = new C0383a((DefaultConstructorMarker) null);

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public a(@NotNull int... numbers) {
        super(Arrays.copyOf(numbers, numbers.length));
        k.f(numbers, "numbers");
    }

    public boolean g() {
        return e(g);
    }

    /* renamed from: kotlin.reflect.jvm.internal.impl.metadata.builtins.a$a  reason: collision with other inner class name */
    /* compiled from: BuiltInsBinaryVersion.kt */
    public static final class C0383a {
        private C0383a() {
        }

        public /* synthetic */ C0383a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final a a(@NotNull InputStream stream) {
            k.f(stream, "stream");
            DataInputStream dataInput = new DataInputStream(stream);
            Iterable $this$mapTo$iv$iv = new i(1, dataInput.readInt());
            Collection destination$iv$iv = new ArrayList(r.r($this$mapTo$iv$iv, 10));
            Iterator it = $this$mapTo$iv$iv.iterator();
            while (it.hasNext()) {
                int nextInt = ((g0) it).nextInt();
                destination$iv$iv.add(Integer.valueOf(dataInput.readInt()));
            }
            int[] B0 = y.B0(destination$iv$iv);
            return new a(Arrays.copyOf(B0, B0.length));
        }
    }
}
