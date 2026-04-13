package zendesk.conversationkit.android.internal.user;

import com.squareup.moshi.e;
import com.squareup.moshi.g;
import com.squareup.moshi.r;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.text.c;
import kotlin.text.x;
import okio.f;
import org.jetbrains.annotations.NotNull;
import zendesk.conversationkit.android.g;

/* compiled from: Jwt.kt */
public abstract class Jwt {
    public /* synthetic */ Jwt(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    @NotNull
    public abstract String a();

    private Jwt() {
    }

    @g(generateAdapter = true)
    /* compiled from: Jwt.kt */
    public static final class Unified extends Jwt {
        @NotNull
        private final String a;

        @NotNull
        public String a() {
            return this.a;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public Unified(@e(name = "external_id") @NotNull String externalId) {
            super((DefaultConstructorMarker) null);
            k.e(externalId, "externalId");
            this.a = externalId;
        }
    }

    /* compiled from: Jwt.kt */
    public static final class a {
        @NotNull
        private final r a;

        public a(@NotNull r moshi) {
            k.e(moshi, "moshi");
            this.a = moshi;
        }

        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public /* synthetic */ a(com.squareup.moshi.r r1, int r2, kotlin.jvm.internal.DefaultConstructorMarker r3) {
            /*
                r0 = this;
                r2 = r2 & 1
                if (r2 == 0) goto L_0x0012
                com.squareup.moshi.r$b r1 = new com.squareup.moshi.r$b
                r1.<init>()
                com.squareup.moshi.r r1 = r1.c()
                java.lang.String r2 = "Builder().build()"
                kotlin.jvm.internal.k.d(r1, r2)
            L_0x0012:
                r0.<init>(r1)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: zendesk.conversationkit.android.internal.user.Jwt.a.<init>(com.squareup.moshi.r, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
        }

        @NotNull
        public final zendesk.conversationkit.android.g<Jwt> a(@NotNull String jwt) {
            k.e(jwt, "jwt");
            try {
                f a2 = f.Companion.a((String) x.E0(jwt, new char[]{'.'}, false, 0, 6, (Object) null).get(1));
                String decodedPayload = a2 == null ? null : a2.string(c.a);
                if (decodedPayload == null) {
                    decodedPayload = "";
                }
                Unified deserializedPayload = this.a.c(Unified.class).c(decodedPayload);
                k.c(deserializedPayload);
                k.d(deserializedPayload, "moshi\n                  …romJson(decodedPayload)!!");
                return new g.b(deserializedPayload);
            } catch (Exception e) {
                return new g.a(e);
            }
        }
    }
}
