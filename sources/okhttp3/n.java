package okhttp3;

import java.util.List;
import kotlin.collections.q;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: CookieJar.kt */
public interface n {
    @NotNull
    public static final n a = new a.C0475a();
    public static final a b = new a((DefaultConstructorMarker) null);

    @NotNull
    List<m> loadForRequest(@NotNull v vVar);

    void saveFromResponse(@NotNull v vVar, @NotNull List<m> list);

    /* compiled from: CookieJar.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        /* renamed from: okhttp3.n$a$a  reason: collision with other inner class name */
        /* compiled from: CookieJar.kt */
        public static final class C0475a implements n {
            public void saveFromResponse(@NotNull v url, @NotNull List<m> cookies) {
                k.f(url, "url");
                k.f(cookies, "cookies");
            }

            @NotNull
            public List<m> loadForRequest(@NotNull v url) {
                k.f(url, "url");
                return q.g();
            }
        }
    }
}
