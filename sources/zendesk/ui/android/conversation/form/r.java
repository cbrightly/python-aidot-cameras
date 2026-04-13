package zendesk.ui.android.conversation.form;

import java.util.List;
import kotlin.collections.q;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: FormResponseState.kt */
public final class r {
    @NotNull
    private final List<j> a;

    public r() {
        this((List) null, 1, (DefaultConstructorMarker) null);
    }

    @NotNull
    public final r a(@NotNull List<j> list) {
        k.e(list, "fieldResponses");
        return new r(list);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof r) && k.a(this.a, ((r) obj).a);
    }

    public int hashCode() {
        return this.a.hashCode();
    }

    @NotNull
    public String toString() {
        return "FormResponseState(fieldResponses=" + this.a + ')';
    }

    public r(@NotNull List<j> fieldResponses) {
        k.e(fieldResponses, "fieldResponses");
        this.a = fieldResponses;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ r(List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? q.g() : list);
    }

    @NotNull
    public final List<j> b() {
        return this.a;
    }

    /* compiled from: FormResponseState.kt */
    public static final class a {
        @NotNull
        private r a = new r((List) null, 1, (DefaultConstructorMarker) null);

        @NotNull
        public final a b(@NotNull List<j> fieldResponses) {
            k.e(fieldResponses, "fieldResponses");
            this.a = this.a.a(fieldResponses);
            return this;
        }

        @NotNull
        public final r a() {
            return this.a;
        }
    }
}
