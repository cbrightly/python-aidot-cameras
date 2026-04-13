package zendesk.messaging.android.internal.conversationscreen.cache;

import com.squareup.moshi.JsonDataException;
import com.squareup.moshi.f;
import com.squareup.moshi.i;
import com.squareup.moshi.internal.b;
import com.squareup.moshi.o;
import com.squareup.moshi.r;
import java.lang.reflect.Constructor;
import kotlin.collections.o0;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: MessagingUIPersistenceJsonAdapter.kt */
public final class MessagingUIPersistenceJsonAdapter extends f<MessagingUIPersistence> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<String> b;
    @Nullable
    private volatile Constructor<MessagingUIPersistence> c;

    public MessagingUIPersistenceJsonAdapter(@NotNull r moshi) {
        k.e(moshi, "moshi");
        i.a a2 = i.a.a("conversationId", "composerText");
        k.d(a2, "of(\"conversationId\", \"composerText\")");
        this.a = a2;
        f<String> f = moshi.f(String.class, o0.d(), "conversationId");
        k.d(f, "moshi.adapter(String::cl…,\n      \"conversationId\")");
        this.b = f;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(44);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("MessagingUIPersistence");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public MessagingUIPersistence b(@NotNull i reader) {
        Class<String> cls = String.class;
        k.e(reader, "reader");
        String conversationId = null;
        String composerText = null;
        int mask0 = -1;
        reader.c();
        while (reader.l()) {
            switch (reader.J(this.a)) {
                case -1:
                    reader.o0();
                    reader.u0();
                    break;
                case 0:
                    String b2 = this.b.b(reader);
                    if (b2 != null) {
                        conversationId = b2;
                        break;
                    } else {
                        JsonDataException u = b.u("conversationId", "conversationId", reader);
                        k.d(u, "unexpectedNull(\"conversa…\"conversationId\", reader)");
                        throw u;
                    }
                case 1:
                    String b3 = this.b.b(reader);
                    if (b3 != null) {
                        composerText = b3;
                        mask0 &= -3;
                        break;
                    } else {
                        JsonDataException u2 = b.u("composerText", "composerText", reader);
                        k.d(u2, "unexpectedNull(\"composer…  \"composerText\", reader)");
                        throw u2;
                    }
            }
        }
        reader.i();
        if (mask0 != -3) {
            Constructor it = this.c;
            if (it == null) {
                it = MessagingUIPersistence.class.getDeclaredConstructor(new Class[]{cls, cls, Integer.TYPE, b.c});
                this.c = it;
                k.d(it, "MessagingUIPersistence::…his.constructorRef = it }");
            }
            Constructor localConstructor = it;
            Object[] objArr = new Object[4];
            if (conversationId != null) {
                objArr[0] = conversationId;
                objArr[1] = composerText;
                objArr[2] = Integer.valueOf(mask0);
                objArr[3] = null;
                MessagingUIPersistence newInstance = localConstructor.newInstance(objArr);
                k.d(newInstance, "localConstructor.newInst…torMarker */ null\n      )");
                return newInstance;
            }
            JsonDataException l = b.l("conversationId", "conversationId", reader);
            k.d(l, "missingProperty(\"convers…\"conversationId\", reader)");
            throw l;
        } else if (conversationId == null) {
            JsonDataException l2 = b.l("conversationId", "conversationId", reader);
            k.d(l2, "missingProperty(\"convers…\"conversationId\", reader)");
            throw l2;
        } else if (composerText != null) {
            return new MessagingUIPersistence(conversationId, composerText);
        } else {
            throw new NullPointerException("null cannot be cast to non-null type kotlin.String");
        }
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable MessagingUIPersistence value_) {
        k.e(writer, "writer");
        if (value_ != null) {
            writer.g();
            writer.r("conversationId");
            this.b.i(writer, value_.d());
            writer.r("composerText");
            this.b.i(writer, value_.c());
            writer.m();
            return;
        }
        throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
    }
}
