package zendesk.conversationkit.android.internal.rest.model;

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

/* compiled from: AuthorDtoJsonAdapter.kt */
public final class AuthorDtoJsonAdapter extends f<AuthorDto> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<String> b;
    @NotNull
    private final f<ClientDto> c;
    @NotNull
    private final f<String> d;
    @Nullable
    private volatile Constructor<AuthorDto> e;

    public AuthorDtoJsonAdapter(@NotNull r moshi) {
        Class<String> cls = String.class;
        k.e(moshi, "moshi");
        i.a a2 = i.a.a("appUserId", "role", "client", "sessionId");
        k.d(a2, "of(\"appUserId\", \"role\", …ient\",\n      \"sessionId\")");
        this.a = a2;
        f<String> f = moshi.f(cls, o0.d(), "appUserId");
        k.d(f, "moshi.adapter(String::cl…Set(),\n      \"appUserId\")");
        this.b = f;
        f<ClientDto> f2 = moshi.f(ClientDto.class, o0.d(), "client");
        k.d(f2, "moshi.adapter(ClientDto:…    emptySet(), \"client\")");
        this.c = f2;
        f<String> f3 = moshi.f(cls, o0.d(), "sessionId");
        k.d(f3, "moshi.adapter(String::cl… emptySet(), \"sessionId\")");
        this.d = f3;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(31);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("AuthorDto");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public AuthorDto b(@NotNull i reader) {
        String str;
        i iVar = reader;
        Class<String> cls = String.class;
        k.e(iVar, "reader");
        String appUserId = null;
        String role = null;
        ClientDto client = null;
        String sessionId = null;
        int mask0 = -1;
        reader.c();
        while (reader.l()) {
            switch (iVar.J(this.a)) {
                case -1:
                    reader.o0();
                    reader.u0();
                    break;
                case 0:
                    String b2 = this.b.b(iVar);
                    if (b2 != null) {
                        appUserId = b2;
                        break;
                    } else {
                        JsonDataException u = b.u("appUserId", "appUserId", iVar);
                        k.d(u, "unexpectedNull(\"appUserI…     \"appUserId\", reader)");
                        throw u;
                    }
                case 1:
                    String b3 = this.b.b(iVar);
                    if (b3 != null) {
                        role = b3;
                        break;
                    } else {
                        JsonDataException u2 = b.u("role", "role", iVar);
                        k.d(u2, "unexpectedNull(\"role\", \"role\",\n            reader)");
                        throw u2;
                    }
                case 2:
                    ClientDto b4 = this.c.b(iVar);
                    if (b4 != null) {
                        client = b4;
                        break;
                    } else {
                        JsonDataException u3 = b.u("client", "client", iVar);
                        k.d(u3, "unexpectedNull(\"client\",…        \"client\", reader)");
                        throw u3;
                    }
                case 3:
                    sessionId = this.d.b(iVar);
                    mask0 &= -9;
                    break;
            }
        }
        reader.i();
        if (mask0 != -9) {
            Constructor it = this.e;
            if (it == null) {
                str = "missingProperty(\"appUserId\", \"appUserId\", reader)";
                it = AuthorDto.class.getDeclaredConstructor(new Class[]{cls, cls, ClientDto.class, cls, Integer.TYPE, b.c});
                this.e = it;
                k.d(it, "AuthorDto::class.java.ge…his.constructorRef = it }");
            } else {
                str = "missingProperty(\"appUserId\", \"appUserId\", reader)";
            }
            Constructor localConstructor = it;
            Object[] objArr = new Object[6];
            if (appUserId != null) {
                objArr[0] = appUserId;
                if (role != null) {
                    objArr[1] = role;
                    if (client != null) {
                        objArr[2] = client;
                        objArr[3] = sessionId;
                        objArr[4] = Integer.valueOf(mask0);
                        objArr[5] = null;
                        AuthorDto newInstance = localConstructor.newInstance(objArr);
                        k.d(newInstance, "localConstructor.newInst…torMarker */ null\n      )");
                        return newInstance;
                    }
                    JsonDataException l = b.l("client", "client", iVar);
                    k.d(l, "missingProperty(\"client\", \"client\", reader)");
                    throw l;
                }
                JsonDataException l2 = b.l("role", "role", iVar);
                k.d(l2, "missingProperty(\"role\", \"role\", reader)");
                throw l2;
            }
            JsonDataException l3 = b.l("appUserId", "appUserId", iVar);
            k.d(l3, str);
            throw l3;
        } else if (appUserId == null) {
            JsonDataException l4 = b.l("appUserId", "appUserId", iVar);
            k.d(l4, "missingProperty(\"appUserId\", \"appUserId\", reader)");
            throw l4;
        } else if (role == null) {
            JsonDataException l5 = b.l("role", "role", iVar);
            k.d(l5, "missingProperty(\"role\", \"role\", reader)");
            throw l5;
        } else if (client != null) {
            return new AuthorDto(appUserId, role, client, sessionId);
        } else {
            JsonDataException l6 = b.l("client", "client", iVar);
            k.d(l6, "missingProperty(\"client\", \"client\", reader)");
            throw l6;
        }
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable AuthorDto value_) {
        k.e(writer, "writer");
        if (value_ != null) {
            writer.g();
            writer.r("appUserId");
            this.b.i(writer, value_.a());
            writer.r("role");
            this.b.i(writer, value_.c());
            writer.r("client");
            this.c.i(writer, value_.b());
            writer.r("sessionId");
            this.d.i(writer, value_.d());
            writer.m();
            return;
        }
        throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
    }
}
