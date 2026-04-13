package zendesk.android;

import com.google.android.gms.actions.SearchIntents;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import kotlin.collections.l0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.t;
import kotlin.text.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ZendeskCredentials.kt */
public final class d {
    @NotNull
    public static final b a = new b((DefaultConstructorMarker) null);
    @NotNull
    private final String b;

    public /* synthetic */ d(String str, DefaultConstructorMarker defaultConstructorMarker) {
        this(str);
    }

    private d(String channelKey) {
        this.b = channelKey;
    }

    @NotNull
    public final String a() {
        return this.b;
    }

    public boolean equals(@Nullable Object other) {
        return (other instanceof d) && k.a(this.b, ((d) other).b);
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.b});
    }

    @NotNull
    public String toString() {
        return "ZendeskCredentials(channelKey='" + this.b + "')";
    }

    /* compiled from: ZendeskCredentials.kt */
    public static final class b {
        public /* synthetic */ b(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private b() {
        }

        @NotNull
        public final a a(@NotNull String channelKey) {
            k.e(channelKey, "channelKey");
            return new a(channelKey);
        }

        @NotNull
        public final String c(@NotNull d $this$toQuery) {
            k.e($this$toQuery, "<this>");
            return k.l("channelKey=", $this$toQuery.a());
        }

        @Nullable
        public final d b(@NotNull String query) {
            k.e(query, SearchIntents.EXTRA_QUERY);
            Iterable $this$mapNotNull$iv = x.F0(query, new String[]{"&"}, false, 0, 6, (Object) null);
            Collection destination$iv$iv = new ArrayList();
            Iterator it = $this$mapNotNull$iv.iterator();
            while (true) {
                Object it$iv$iv = null;
                if (!it.hasNext()) {
                    break;
                }
                String it2 = (String) it.next();
                Iterable $this$mapNotNull$iv2 = $this$mapNotNull$iv;
                if (x.S(it2, "=", false, 2, (Object) null)) {
                    String str = it2;
                    List F0 = x.F0(it2, new String[]{"="}, false, 2, 2, (Object) null);
                    it$iv$iv = t.a((String) F0.get(0), (String) F0.get(1));
                }
                if (it$iv$iv != null) {
                    destination$iv$iv.add(it$iv$iv);
                }
                $this$mapNotNull$iv = $this$mapNotNull$iv2;
            }
            String channelKey = (String) l0.o(destination$iv$iv).get("channelKey");
            if (channelKey != null) {
                return new d(channelKey, (DefaultConstructorMarker) null);
            }
            zendesk.logger.a.h("ZendeskCredentials", "Invalid query provided, unable to obtain an instance of MessagingCredentials.", new Object[0]);
            return null;
        }
    }

    /* compiled from: ZendeskCredentials.kt */
    public static final class a {
        @NotNull
        private final String a;

        public a(@NotNull String channelKey) {
            k.e(channelKey, "channelKey");
            this.a = channelKey;
        }

        @NotNull
        public final d a() {
            return new d(this.a, (DefaultConstructorMarker) null);
        }
    }
}
