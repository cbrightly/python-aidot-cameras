package zendesk.conversationkit.android.model;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: App.kt */
public final class d {
    @NotNull
    private final String a;
    @NotNull
    private final String b;
    @NotNull
    private final String c;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof d)) {
            return false;
        }
        d dVar = (d) obj;
        return k.a(this.a, dVar.a) && k.a(this.b, dVar.b) && k.a(this.c, dVar.c);
    }

    public int hashCode() {
        return (((this.a.hashCode() * 31) + this.b.hashCode()) * 31) + this.c.hashCode();
    }

    @NotNull
    public String toString() {
        return "App(id=" + this.a + ", status=" + this.b + ", name=" + this.c + ')';
    }

    public d(@NotNull String id, @NotNull String status, @NotNull String name) {
        k.e(id, "id");
        k.e(status, "status");
        k.e(name, "name");
        this.a = id;
        this.b = status;
        this.c = name;
    }

    @NotNull
    public final String a() {
        return this.a;
    }
}
