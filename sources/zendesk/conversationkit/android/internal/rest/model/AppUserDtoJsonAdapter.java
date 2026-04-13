package zendesk.conversationkit.android.internal.rest.model;

import com.squareup.moshi.JsonDataException;
import com.squareup.moshi.f;
import com.squareup.moshi.i;
import com.squareup.moshi.internal.b;
import com.squareup.moshi.o;
import com.squareup.moshi.r;
import com.squareup.moshi.t;
import java.util.List;
import java.util.Map;
import kotlin.collections.o0;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: AppUserDtoJsonAdapter.kt */
public final class AppUserDtoJsonAdapter extends f<AppUserDto> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<String> b;
    @NotNull
    private final f<String> c;
    @NotNull
    private final f<List<ClientDto>> d;
    @NotNull
    private final f<Map<String, Object>> e;

    public AppUserDtoJsonAdapter(@NotNull r moshi) {
        Class<String> cls = String.class;
        k.e(moshi, "moshi");
        i.a a2 = i.a.a("_id", "userId", "givenName", "surname", "email", "locale", "signedUpAt", "clients", "pendingClients", "properties");
        k.d(a2, "of(\"_id\", \"userId\", \"giv…ngClients\", \"properties\")");
        this.a = a2;
        f<String> f = moshi.f(cls, o0.d(), "id");
        k.d(f, "moshi.adapter(String::cl…, emptySet(),\n      \"id\")");
        this.b = f;
        f<String> f2 = moshi.f(cls, o0.d(), "userId");
        k.d(f2, "moshi.adapter(String::cl…    emptySet(), \"userId\")");
        this.c = f2;
        f<List<ClientDto>> f3 = moshi.f(t.j(List.class, ClientDto.class), o0.d(), "clients");
        k.d(f3, "moshi.adapter(Types.newP…tySet(),\n      \"clients\")");
        this.d = f3;
        f<Map<String, Object>> f4 = moshi.f(t.j(Map.class, cls, Object.class), o0.d(), "properties");
        k.d(f4, "moshi.adapter(Types.newP…emptySet(), \"properties\")");
        this.e = f4;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(32);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("AppUserDto");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public AppUserDto b(@NotNull i reader) {
        i iVar = reader;
        k.e(iVar, "reader");
        String id = null;
        String userId = null;
        String givenName = null;
        String surname = null;
        String email = null;
        String locale = null;
        String signedUpAt = null;
        List clients = null;
        List pendingClients = null;
        Map properties = null;
        reader.c();
        while (true) {
            String signedUpAt2 = signedUpAt;
            String locale2 = locale;
            if (reader.l()) {
                switch (iVar.J(this.a)) {
                    case -1:
                        reader.o0();
                        reader.u0();
                        break;
                    case 0:
                        String b2 = this.b.b(iVar);
                        if (b2 != null) {
                            id = b2;
                            signedUpAt = signedUpAt2;
                            locale = locale2;
                            continue;
                        } else {
                            JsonDataException u = b.u("id", "_id", iVar);
                            k.d(u, "unexpectedNull(\"id\", \"_id\", reader)");
                            throw u;
                        }
                    case 1:
                        userId = this.c.b(iVar);
                        signedUpAt = signedUpAt2;
                        locale = locale2;
                        continue;
                    case 2:
                        givenName = this.c.b(iVar);
                        signedUpAt = signedUpAt2;
                        locale = locale2;
                        continue;
                    case 3:
                        surname = this.c.b(iVar);
                        signedUpAt = signedUpAt2;
                        locale = locale2;
                        continue;
                    case 4:
                        email = this.c.b(iVar);
                        signedUpAt = signedUpAt2;
                        locale = locale2;
                        continue;
                    case 5:
                        locale = this.c.b(iVar);
                        signedUpAt = signedUpAt2;
                        continue;
                    case 6:
                        signedUpAt = this.c.b(iVar);
                        locale = locale2;
                        continue;
                    case 7:
                        List b3 = this.d.b(iVar);
                        if (b3 != null) {
                            clients = b3;
                            signedUpAt = signedUpAt2;
                            locale = locale2;
                            continue;
                        } else {
                            JsonDataException u2 = b.u("clients", "clients", iVar);
                            k.d(u2, "unexpectedNull(\"clients\", \"clients\", reader)");
                            throw u2;
                        }
                    case 8:
                        List b4 = this.d.b(iVar);
                        if (b4 != null) {
                            pendingClients = b4;
                            signedUpAt = signedUpAt2;
                            locale = locale2;
                            continue;
                        } else {
                            JsonDataException u3 = b.u("pendingClients", "pendingClients", iVar);
                            k.d(u3, "unexpectedNull(\"pendingC…\"pendingClients\", reader)");
                            throw u3;
                        }
                    case 9:
                        Map b5 = this.e.b(iVar);
                        if (b5 != null) {
                            properties = b5;
                            signedUpAt = signedUpAt2;
                            locale = locale2;
                            continue;
                        } else {
                            JsonDataException u4 = b.u("properties", "properties", iVar);
                            k.d(u4, "unexpectedNull(\"properties\", \"properties\", reader)");
                            throw u4;
                        }
                }
                signedUpAt = signedUpAt2;
                locale = locale2;
            } else {
                reader.i();
                if (id == null) {
                    JsonDataException l = b.l("id", "_id", iVar);
                    k.d(l, "missingProperty(\"id\", \"_id\", reader)");
                    throw l;
                } else if (clients == null) {
                    JsonDataException l2 = b.l("clients", "clients", iVar);
                    k.d(l2, "missingProperty(\"clients\", \"clients\", reader)");
                    throw l2;
                } else if (pendingClients == null) {
                    JsonDataException l3 = b.l("pendingClients", "pendingClients", iVar);
                    k.d(l3, "missingProperty(\"pending…\"pendingClients\", reader)");
                    throw l3;
                } else if (properties != null) {
                    return new AppUserDto(id, userId, givenName, surname, email, locale2, signedUpAt2, clients, pendingClients, properties);
                } else {
                    JsonDataException l4 = b.l("properties", "properties", iVar);
                    k.d(l4, "missingProperty(\"propert…s\", \"properties\", reader)");
                    throw l4;
                }
            }
        }
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable AppUserDto value_) {
        k.e(writer, "writer");
        if (value_ != null) {
            writer.g();
            writer.r("_id");
            this.b.i(writer, value_.d());
            writer.r("userId");
            this.c.i(writer, value_.j());
            writer.r("givenName");
            this.c.i(writer, value_.c());
            writer.r("surname");
            this.c.i(writer, value_.i());
            writer.r("email");
            this.c.i(writer, value_.b());
            writer.r("locale");
            this.c.i(writer, value_.e());
            writer.r("signedUpAt");
            this.c.i(writer, value_.h());
            writer.r("clients");
            this.d.i(writer, value_.a());
            writer.r("pendingClients");
            this.d.i(writer, value_.f());
            writer.r("properties");
            this.e.i(writer, value_.g());
            writer.m();
            return;
        }
        throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
    }
}
