package zendesk.conversationkit.android.model;

import com.squareup.moshi.JsonDataException;
import com.squareup.moshi.f;
import com.squareup.moshi.i;
import com.squareup.moshi.internal.b;
import com.squareup.moshi.o;
import com.squareup.moshi.r;
import com.squareup.moshi.t;
import java.lang.reflect.Constructor;
import java.util.List;
import kotlin.collections.o0;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: UserJsonAdapter.kt */
public final class UserJsonAdapter extends f<User> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<String> b;
    @NotNull
    private final f<String> c;
    @NotNull
    private final f<List<Conversation>> d;
    @NotNull
    private final f<RealtimeSettings> e;
    @NotNull
    private final f<TypingSettings> f;
    @Nullable
    private volatile Constructor<User> g;

    public UserJsonAdapter(@NotNull r moshi) {
        Class<String> cls = String.class;
        k.e(moshi, "moshi");
        i.a a2 = i.a.a("id", "externalId", "givenName", "surname", "email", "locale", "signedUpAt", "conversations", "realtimeSettings", "typingSettings", "sessionToken", "jwt");
        k.d(a2, "of(\"id\", \"externalId\", \"…\", \"sessionToken\", \"jwt\")");
        this.a = a2;
        f<String> f2 = moshi.f(cls, o0.d(), "id");
        k.d(f2, "moshi.adapter(String::cl…, emptySet(),\n      \"id\")");
        this.b = f2;
        f<String> f3 = moshi.f(cls, o0.d(), "externalId");
        k.d(f3, "moshi.adapter(String::cl…emptySet(), \"externalId\")");
        this.c = f3;
        f<List<Conversation>> f4 = moshi.f(t.j(List.class, Conversation.class), o0.d(), "conversations");
        k.d(f4, "moshi.adapter(Types.newP…tySet(), \"conversations\")");
        this.d = f4;
        f<RealtimeSettings> f5 = moshi.f(RealtimeSettings.class, o0.d(), "realtimeSettings");
        k.d(f5, "moshi.adapter(RealtimeSe…et(), \"realtimeSettings\")");
        this.e = f5;
        f<TypingSettings> f6 = moshi.f(TypingSettings.class, o0.d(), "typingSettings");
        k.d(f6, "moshi.adapter(TypingSett…ySet(), \"typingSettings\")");
        this.f = f6;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(26);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("User");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public User b(@NotNull i reader) {
        String str;
        i iVar = reader;
        Class<String> cls = String.class;
        k.e(iVar, "reader");
        String id = null;
        String externalId = null;
        String givenName = null;
        String surname = null;
        String email = null;
        String locale = null;
        String signedUpAt = null;
        List conversations = null;
        RealtimeSettings realtimeSettings = null;
        TypingSettings typingSettings = null;
        String sessionToken = null;
        String jwt = null;
        int mask0 = -1;
        reader.c();
        while (true) {
            Class<String> cls2 = cls;
            String jwt2 = jwt;
            String sessionToken2 = sessionToken;
            String signedUpAt2 = signedUpAt;
            if (reader.l()) {
                String locale2 = locale;
                switch (iVar.J(this.a)) {
                    case -1:
                        reader.o0();
                        reader.u0();
                        break;
                    case 0:
                        String b2 = this.b.b(iVar);
                        if (b2 != null) {
                            id = b2;
                            cls = cls2;
                            jwt = jwt2;
                            sessionToken = sessionToken2;
                            signedUpAt = signedUpAt2;
                            locale = locale2;
                            continue;
                        } else {
                            JsonDataException u = b.u("id", "id", iVar);
                            k.d(u, "unexpectedNull(\"id\", \"id\", reader)");
                            throw u;
                        }
                    case 1:
                        externalId = this.c.b(iVar);
                        cls = cls2;
                        jwt = jwt2;
                        sessionToken = sessionToken2;
                        signedUpAt = signedUpAt2;
                        locale = locale2;
                        continue;
                    case 2:
                        givenName = this.c.b(iVar);
                        cls = cls2;
                        jwt = jwt2;
                        sessionToken = sessionToken2;
                        signedUpAt = signedUpAt2;
                        locale = locale2;
                        continue;
                    case 3:
                        surname = this.c.b(iVar);
                        cls = cls2;
                        jwt = jwt2;
                        sessionToken = sessionToken2;
                        signedUpAt = signedUpAt2;
                        locale = locale2;
                        continue;
                    case 4:
                        email = this.c.b(iVar);
                        cls = cls2;
                        jwt = jwt2;
                        sessionToken = sessionToken2;
                        signedUpAt = signedUpAt2;
                        locale = locale2;
                        continue;
                    case 5:
                        locale = this.c.b(iVar);
                        cls = cls2;
                        jwt = jwt2;
                        sessionToken = sessionToken2;
                        signedUpAt = signedUpAt2;
                        continue;
                    case 6:
                        signedUpAt = this.c.b(iVar);
                        cls = cls2;
                        jwt = jwt2;
                        sessionToken = sessionToken2;
                        locale = locale2;
                        continue;
                    case 7:
                        List b3 = this.d.b(iVar);
                        if (b3 != null) {
                            conversations = b3;
                            cls = cls2;
                            jwt = jwt2;
                            sessionToken = sessionToken2;
                            signedUpAt = signedUpAt2;
                            locale = locale2;
                            continue;
                        } else {
                            JsonDataException u2 = b.u("conversations", "conversations", iVar);
                            k.d(u2, "unexpectedNull(\"conversa… \"conversations\", reader)");
                            throw u2;
                        }
                    case 8:
                        RealtimeSettings b4 = this.e.b(iVar);
                        if (b4 != null) {
                            realtimeSettings = b4;
                            cls = cls2;
                            jwt = jwt2;
                            sessionToken = sessionToken2;
                            signedUpAt = signedUpAt2;
                            locale = locale2;
                            continue;
                        } else {
                            JsonDataException u3 = b.u("realtimeSettings", "realtimeSettings", iVar);
                            k.d(u3, "unexpectedNull(\"realtime…ealtimeSettings\", reader)");
                            throw u3;
                        }
                    case 9:
                        TypingSettings b5 = this.f.b(iVar);
                        if (b5 != null) {
                            typingSettings = b5;
                            cls = cls2;
                            jwt = jwt2;
                            sessionToken = sessionToken2;
                            signedUpAt = signedUpAt2;
                            locale = locale2;
                            continue;
                        } else {
                            JsonDataException u4 = b.u("typingSettings", "typingSettings", iVar);
                            k.d(u4, "unexpectedNull(\"typingSe…\"typingSettings\", reader)");
                            throw u4;
                        }
                    case 10:
                        sessionToken = this.c.b(iVar);
                        mask0 &= -1025;
                        cls = cls2;
                        jwt = jwt2;
                        signedUpAt = signedUpAt2;
                        locale = locale2;
                        continue;
                    case 11:
                        jwt = this.c.b(iVar);
                        mask0 &= -2049;
                        cls = cls2;
                        sessionToken = sessionToken2;
                        signedUpAt = signedUpAt2;
                        locale = locale2;
                        continue;
                }
                cls = cls2;
                jwt = jwt2;
                sessionToken = sessionToken2;
                signedUpAt = signedUpAt2;
                locale = locale2;
            } else {
                String locale3 = locale;
                reader.i();
                String str2 = "id";
                if (mask0 != -3073) {
                    String str3 = str2;
                    String str4 = "missingProperty(\"id\", \"id\", reader)";
                    Constructor localConstructor = this.g;
                    String str5 = "missingProperty(\"convers… \"conversations\", reader)";
                    if (localConstructor == null) {
                        str = "conversations";
                        localConstructor = User.class.getDeclaredConstructor(new Class[]{cls2, cls2, cls2, cls2, cls2, cls2, cls2, List.class, RealtimeSettings.class, TypingSettings.class, cls2, cls2, Integer.TYPE, b.c});
                        this.g = localConstructor;
                        k.d(localConstructor, "User::class.java.getDecl…his.constructorRef = it }");
                    } else {
                        str = "conversations";
                    }
                    Object[] objArr = new Object[14];
                    if (id != null) {
                        objArr[0] = id;
                        objArr[1] = externalId;
                        objArr[2] = givenName;
                        objArr[3] = surname;
                        objArr[4] = email;
                        objArr[5] = locale3;
                        objArr[6] = signedUpAt2;
                        if (conversations != null) {
                            objArr[7] = conversations;
                            if (realtimeSettings != null) {
                                objArr[8] = realtimeSettings;
                                if (typingSettings != null) {
                                    objArr[9] = typingSettings;
                                    objArr[10] = sessionToken2;
                                    objArr[11] = jwt2;
                                    objArr[12] = Integer.valueOf(mask0);
                                    objArr[13] = null;
                                    User newInstance = localConstructor.newInstance(objArr);
                                    k.d(newInstance, "localConstructor.newInst…torMarker */ null\n      )");
                                    return newInstance;
                                }
                                JsonDataException l = b.l("typingSettings", "typingSettings", iVar);
                                k.d(l, "missingProperty(\"typingS…\"typingSettings\", reader)");
                                throw l;
                            }
                            JsonDataException l2 = b.l("realtimeSettings", "realtimeSettings", iVar);
                            k.d(l2, "missingProperty(\"realtim…s\",\n              reader)");
                            throw l2;
                        }
                        String str6 = str;
                        JsonDataException l3 = b.l(str6, str6, iVar);
                        k.d(l3, str5);
                        throw l3;
                    }
                    String str7 = str3;
                    JsonDataException l4 = b.l(str7, str7, iVar);
                    k.d(l4, str4);
                    throw l4;
                } else if (id == null) {
                    String str8 = str2;
                    JsonDataException l5 = b.l(str8, str8, iVar);
                    k.d(l5, "missingProperty(\"id\", \"id\", reader)");
                    throw l5;
                } else if (conversations == null) {
                    JsonDataException l6 = b.l("conversations", "conversations", iVar);
                    k.d(l6, "missingProperty(\"convers… \"conversations\", reader)");
                    throw l6;
                } else if (realtimeSettings == null) {
                    JsonDataException l7 = b.l("realtimeSettings", "realtimeSettings", iVar);
                    k.d(l7, "missingProperty(\"realtim…ealtimeSettings\", reader)");
                    throw l7;
                } else if (typingSettings != null) {
                    return new User(id, externalId, givenName, surname, email, locale3, signedUpAt2, conversations, realtimeSettings, typingSettings, sessionToken2, jwt2);
                } else {
                    JsonDataException l8 = b.l("typingSettings", "typingSettings", iVar);
                    k.d(l8, "missingProperty(\"typingS…\"typingSettings\", reader)");
                    throw l8;
                }
            }
        }
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable User value_) {
        k.e(writer, "writer");
        if (value_ != null) {
            writer.g();
            writer.r("id");
            this.b.i(writer, value_.h());
            writer.r("externalId");
            this.c.i(writer, value_.f());
            writer.r("givenName");
            this.c.i(writer, value_.g());
            writer.r("surname");
            this.c.i(writer, value_.n());
            writer.r("email");
            this.c.i(writer, value_.e());
            writer.r("locale");
            this.c.i(writer, value_.j());
            writer.r("signedUpAt");
            this.c.i(writer, value_.m());
            writer.r("conversations");
            this.d.i(writer, value_.d());
            writer.r("realtimeSettings");
            this.e.i(writer, value_.k());
            writer.r("typingSettings");
            this.f.i(writer, value_.o());
            writer.r("sessionToken");
            this.c.i(writer, value_.l());
            writer.r("jwt");
            this.c.i(writer, value_.i());
            writer.m();
            return;
        }
        throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
    }
}
