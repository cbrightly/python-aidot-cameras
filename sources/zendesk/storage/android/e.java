package zendesk.storage.android;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: StorageType.kt */
public abstract class e {
    public /* synthetic */ e(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private e() {
    }

    /* compiled from: StorageType.kt */
    public static final class a extends e {
        @NotNull
        public static final a a = new a();

        private a() {
            super((DefaultConstructorMarker) null);
        }
    }

    /* compiled from: StorageType.kt */
    public static final class b extends e {
        @NotNull
        private final b a;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public b(@NotNull b serializer) {
            super((DefaultConstructorMarker) null);
            k.e(serializer, "serializer");
            this.a = serializer;
        }

        @NotNull
        public final b a() {
            return this.a;
        }
    }
}
