package zendesk.conversationkit.android.internal.rest.user.model;

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
import zendesk.conversationkit.android.internal.rest.model.ClientDto;

/* compiled from: LoginRequestBodyJsonAdapter.kt */
public final class LoginRequestBodyJsonAdapter extends f<LoginRequestBody> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<String> b;
    @NotNull
    private final f<ClientDto> c;
    @NotNull
    private final f<String> d;
    @Nullable
    private volatile Constructor<LoginRequestBody> e;

    public LoginRequestBodyJsonAdapter(@NotNull r moshi) {
        Class<String> cls = String.class;
        k.e(moshi, "moshi");
        i.a a2 = i.a.a("userId", "client", "appUserId", "sessionToken");
        k.d(a2, "of(\"userId\", \"client\", \"…d\",\n      \"sessionToken\")");
        this.a = a2;
        f<String> f = moshi.f(cls, o0.d(), "userId");
        k.d(f, "moshi.adapter(String::cl…ptySet(),\n      \"userId\")");
        this.b = f;
        f<ClientDto> f2 = moshi.f(ClientDto.class, o0.d(), "client");
        k.d(f2, "moshi.adapter(ClientDto:…    emptySet(), \"client\")");
        this.c = f2;
        f<String> f3 = moshi.f(cls, o0.d(), "appUserId");
        k.d(f3, "moshi.adapter(String::cl… emptySet(), \"appUserId\")");
        this.d = f3;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(38);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("LoginRequestBody");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public LoginRequestBody b(@NotNull i reader) {
        i iVar = reader;
        Class<String> cls = String.class;
        k.e(iVar, "reader");
        String userId = null;
        ClientDto client = null;
        String appUserId = null;
        String sessionToken = null;
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
                        userId = b2;
                        break;
                    } else {
                        JsonDataException u = b.u("userId", "userId", iVar);
                        k.d(u, "unexpectedNull(\"userId\",…        \"userId\", reader)");
                        throw u;
                    }
                case 1:
                    ClientDto b3 = this.c.b(iVar);
                    if (b3 != null) {
                        client = b3;
                        break;
                    } else {
                        JsonDataException u2 = b.u("client", "client", iVar);
                        k.d(u2, "unexpectedNull(\"client\",…        \"client\", reader)");
                        throw u2;
                    }
                case 2:
                    appUserId = this.d.b(iVar);
                    mask0 &= -5;
                    break;
                case 3:
                    sessionToken = this.d.b(iVar);
                    mask0 &= -9;
                    break;
            }
        }
        reader.i();
        if (mask0 != -13) {
            Constructor it = this.e;
            if (it == null) {
                it = LoginRequestBody.class.getDeclaredConstructor(new Class[]{cls, ClientDto.class, cls, cls, Integer.TYPE, b.c});
                this.e = it;
                k.d(it, "LoginRequestBody::class.…his.constructorRef = it }");
            }
            Constructor localConstructor = it;
            Object[] objArr = new Object[6];
            if (userId != null) {
                objArr[0] = userId;
                if (client != null) {
                    objArr[1] = client;
                    objArr[2] = appUserId;
                    objArr[3] = sessionToken;
                    objArr[4] = Integer.valueOf(mask0);
                    objArr[5] = null;
                    LoginRequestBody newInstance = localConstructor.newInstance(objArr);
                    k.d(newInstance, "localConstructor.newInst…torMarker */ null\n      )");
                    return newInstance;
                }
                JsonDataException l = b.l("client", "client", iVar);
                k.d(l, "missingProperty(\"client\", \"client\", reader)");
                throw l;
            }
            JsonDataException l2 = b.l("userId", "userId", iVar);
            k.d(l2, "missingProperty(\"userId\", \"userId\", reader)");
            throw l2;
        } else if (userId == null) {
            JsonDataException l3 = b.l("userId", "userId", iVar);
            k.d(l3, "missingProperty(\"userId\", \"userId\", reader)");
            throw l3;
        } else if (client != null) {
            return new LoginRequestBody(userId, client, appUserId, sessionToken);
        } else {
            JsonDataException l4 = b.l("client", "client", iVar);
            k.d(l4, "missingProperty(\"client\", \"client\", reader)");
            throw l4;
        }
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable LoginRequestBody value_) {
        k.e(writer, "writer");
        if (value_ != null) {
            writer.g();
            writer.r("userId");
            this.b.i(writer, value_.d());
            writer.r("client");
            this.c.i(writer, value_.b());
            writer.r("appUserId");
            this.d.i(writer, value_.a());
            writer.r("sessionToken");
            this.d.i(writer, value_.c());
            writer.m();
            return;
        }
        throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
    }
}
