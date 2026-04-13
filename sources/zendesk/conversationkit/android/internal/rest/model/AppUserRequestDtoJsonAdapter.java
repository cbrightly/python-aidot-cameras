package zendesk.conversationkit.android.internal.rest.model;

import com.squareup.moshi.JsonDataException;
import com.squareup.moshi.f;
import com.squareup.moshi.i;
import com.squareup.moshi.internal.b;
import com.squareup.moshi.o;
import com.squareup.moshi.r;
import com.squareup.moshi.t;
import java.lang.reflect.Constructor;
import java.util.Map;
import kotlin.collections.o0;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: AppUserRequestDtoJsonAdapter.kt */
public final class AppUserRequestDtoJsonAdapter extends f<AppUserRequestDto> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<ClientDto> b;
    @NotNull
    private final f<String> c;
    @NotNull
    private final f<Map<String, Object>> d;
    @NotNull
    private final f<String> e;
    @Nullable
    private volatile Constructor<AppUserRequestDto> f;

    public AppUserRequestDtoJsonAdapter(@NotNull r moshi) {
        Class<String> cls = String.class;
        k.e(moshi, "moshi");
        i.a a2 = i.a.a("client", "userId", "givenName", "surname", "email", "properties", "intent");
        k.d(a2, "of(\"client\", \"userId\", \"…, \"properties\", \"intent\")");
        this.a = a2;
        f<ClientDto> f2 = moshi.f(ClientDto.class, o0.d(), "client");
        k.d(f2, "moshi.adapter(ClientDto:…    emptySet(), \"client\")");
        this.b = f2;
        f<String> f3 = moshi.f(cls, o0.d(), "userId");
        k.d(f3, "moshi.adapter(String::cl…    emptySet(), \"userId\")");
        this.c = f3;
        f<Map<String, Object>> f4 = moshi.f(t.j(Map.class, cls, Object.class), o0.d(), "properties");
        k.d(f4, "moshi.adapter(Types.newP…emptySet(), \"properties\")");
        this.d = f4;
        f<String> f5 = moshi.f(cls, o0.d(), "intent");
        k.d(f5, "moshi.adapter(String::cl…ptySet(),\n      \"intent\")");
        this.e = f5;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(39);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("AppUserRequestDto");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public AppUserRequestDto b(@NotNull i reader) {
        i iVar = reader;
        Class<String> cls = String.class;
        k.e(iVar, "reader");
        ClientDto client = null;
        String userId = null;
        String givenName = null;
        String surname = null;
        String email = null;
        Map properties = null;
        String intent = null;
        int mask0 = -1;
        reader.c();
        while (reader.l()) {
            switch (iVar.J(this.a)) {
                case -1:
                    reader.o0();
                    reader.u0();
                    break;
                case 0:
                    ClientDto b2 = this.b.b(iVar);
                    if (b2 != null) {
                        client = b2;
                        break;
                    } else {
                        JsonDataException u = b.u("client", "client", iVar);
                        k.d(u, "unexpectedNull(\"client\",…        \"client\", reader)");
                        throw u;
                    }
                case 1:
                    userId = this.c.b(iVar);
                    mask0 &= -3;
                    break;
                case 2:
                    givenName = this.c.b(iVar);
                    mask0 &= -5;
                    break;
                case 3:
                    surname = this.c.b(iVar);
                    mask0 &= -9;
                    break;
                case 4:
                    email = this.c.b(iVar);
                    mask0 &= -17;
                    break;
                case 5:
                    properties = this.d.b(iVar);
                    mask0 &= -33;
                    break;
                case 6:
                    String b3 = this.e.b(iVar);
                    if (b3 != null) {
                        intent = b3;
                        mask0 &= -65;
                        break;
                    } else {
                        JsonDataException u2 = b.u("intent", "intent", iVar);
                        k.d(u2, "unexpectedNull(\"intent\",…t\",\n              reader)");
                        throw u2;
                    }
            }
        }
        reader.i();
        if (mask0 != -127) {
            Constructor it = this.f;
            if (it == null) {
                it = AppUserRequestDto.class.getDeclaredConstructor(new Class[]{ClientDto.class, cls, cls, cls, cls, Map.class, cls, Integer.TYPE, b.c});
                this.f = it;
                k.d(it, "AppUserRequestDto::class…his.constructorRef = it }");
            }
            Constructor localConstructor = it;
            Object[] objArr = new Object[9];
            if (client != null) {
                objArr[0] = client;
                objArr[1] = userId;
                objArr[2] = givenName;
                objArr[3] = surname;
                objArr[4] = email;
                objArr[5] = properties;
                objArr[6] = intent;
                objArr[7] = Integer.valueOf(mask0);
                objArr[8] = null;
                AppUserRequestDto newInstance = localConstructor.newInstance(objArr);
                k.d(newInstance, "localConstructor.newInst…torMarker */ null\n      )");
                return newInstance;
            }
            JsonDataException l = b.l("client", "client", iVar);
            k.d(l, "missingProperty(\"client\", \"client\", reader)");
            throw l;
        } else if (client == null) {
            JsonDataException l2 = b.l("client", "client", iVar);
            k.d(l2, "missingProperty(\"client\", \"client\", reader)");
            throw l2;
        } else if (intent != null) {
            return new AppUserRequestDto(client, userId, givenName, surname, email, properties, intent);
        } else {
            throw new NullPointerException("null cannot be cast to non-null type kotlin.String");
        }
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable AppUserRequestDto value_) {
        k.e(writer, "writer");
        if (value_ != null) {
            writer.g();
            writer.r("client");
            this.b.i(writer, value_.a());
            writer.r("userId");
            this.c.i(writer, value_.g());
            writer.r("givenName");
            this.c.i(writer, value_.c());
            writer.r("surname");
            this.c.i(writer, value_.f());
            writer.r("email");
            this.c.i(writer, value_.b());
            writer.r("properties");
            this.d.i(writer, value_.e());
            writer.r("intent");
            this.e.i(writer, value_.d());
            writer.m();
            return;
        }
        throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
    }
}
