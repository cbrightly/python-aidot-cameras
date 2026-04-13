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

/* compiled from: ClientDtoJsonAdapter.kt */
public final class ClientDtoJsonAdapter extends f<ClientDto> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<String> b;
    @NotNull
    private final f<String> c;
    @NotNull
    private final f<ClientInfoDto> d;
    @Nullable
    private volatile Constructor<ClientDto> e;

    public ClientDtoJsonAdapter(@NotNull r moshi) {
        Class<String> cls = String.class;
        k.e(moshi, "moshi");
        i.a a2 = i.a.a("id", "status", "lastSeen", "platform", "integrationId", "pushNotificationToken", "appVersion", "displayName", "info");
        k.d(a2, "of(\"id\", \"status\", \"last…\", \"displayName\", \"info\")");
        this.a = a2;
        f<String> f = moshi.f(cls, o0.d(), "id");
        k.d(f, "moshi.adapter(String::cl…, emptySet(),\n      \"id\")");
        this.b = f;
        f<String> f2 = moshi.f(cls, o0.d(), "status");
        k.d(f2, "moshi.adapter(String::cl…    emptySet(), \"status\")");
        this.c = f2;
        f<ClientInfoDto> f3 = moshi.f(ClientInfoDto.class, o0.d(), "info");
        k.d(f3, "moshi.adapter(ClientInfo…java, emptySet(), \"info\")");
        this.d = f3;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(31);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("ClientDto");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public ClientDto b(@NotNull i reader) {
        String str;
        i iVar = reader;
        Class<String> cls = String.class;
        k.e(iVar, "reader");
        String id = null;
        String status = null;
        String lastSeen = null;
        String platform = null;
        String integrationId = null;
        String pushNotificationToken = null;
        String appVersion = null;
        String displayName = null;
        ClientInfoDto info = null;
        int mask0 = -1;
        reader.c();
        while (true) {
            Class<String> cls2 = cls;
            String displayName2 = displayName;
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
                            cls = cls2;
                            displayName = displayName2;
                            continue;
                        } else {
                            JsonDataException u = b.u("id", "id", iVar);
                            k.d(u, "unexpectedNull(\"id\", \"id\", reader)");
                            throw u;
                        }
                    case 1:
                        status = this.c.b(iVar);
                        mask0 &= -3;
                        cls = cls2;
                        displayName = displayName2;
                        continue;
                    case 2:
                        lastSeen = this.c.b(iVar);
                        mask0 &= -5;
                        cls = cls2;
                        displayName = displayName2;
                        continue;
                    case 3:
                        String b3 = this.b.b(iVar);
                        if (b3 != null) {
                            platform = b3;
                            cls = cls2;
                            displayName = displayName2;
                            continue;
                        } else {
                            JsonDataException u2 = b.u("platform", "platform", iVar);
                            k.d(u2, "unexpectedNull(\"platform…      \"platform\", reader)");
                            throw u2;
                        }
                    case 4:
                        String b4 = this.b.b(iVar);
                        if (b4 != null) {
                            integrationId = b4;
                            cls = cls2;
                            displayName = displayName2;
                            continue;
                        } else {
                            JsonDataException u3 = b.u("integrationId", "integrationId", iVar);
                            k.d(u3, "unexpectedNull(\"integrat… \"integrationId\", reader)");
                            throw u3;
                        }
                    case 5:
                        pushNotificationToken = this.c.b(iVar);
                        cls = cls2;
                        displayName = displayName2;
                        continue;
                    case 6:
                        appVersion = this.c.b(iVar);
                        cls = cls2;
                        displayName = displayName2;
                        continue;
                    case 7:
                        displayName = this.c.b(iVar);
                        mask0 &= -129;
                        cls = cls2;
                        continue;
                    case 8:
                        ClientInfoDto b5 = this.d.b(iVar);
                        if (b5 != null) {
                            info = b5;
                            cls = cls2;
                            displayName = displayName2;
                            continue;
                        } else {
                            JsonDataException u4 = b.u("info", "info", iVar);
                            k.d(u4, "unexpectedNull(\"info\",\n            \"info\", reader)");
                            throw u4;
                        }
                }
                cls = cls2;
                displayName = displayName2;
            } else {
                reader.i();
                String str2 = "id";
                String str3 = "missingProperty(\"platform\", \"platform\", reader)";
                String str4 = "missingProperty(\"id\", \"id\", reader)";
                if (mask0 != -135) {
                    String str5 = str2;
                    String str6 = str3;
                    String str7 = "platform";
                    Constructor localConstructor = this.e;
                    String str8 = "missingProperty(\"integra… \"integrationId\", reader)";
                    if (localConstructor == null) {
                        str = "integrationId";
                        localConstructor = ClientDto.class.getDeclaredConstructor(new Class[]{cls2, cls2, cls2, cls2, cls2, cls2, cls2, cls2, ClientInfoDto.class, Integer.TYPE, b.c});
                        this.e = localConstructor;
                        k.d(localConstructor, "ClientDto::class.java.ge…his.constructorRef = it }");
                    } else {
                        str = "integrationId";
                    }
                    Object[] objArr = new Object[11];
                    if (id != null) {
                        objArr[0] = id;
                        objArr[1] = status;
                        objArr[2] = lastSeen;
                        if (platform != null) {
                            objArr[3] = platform;
                            if (integrationId != null) {
                                objArr[4] = integrationId;
                                objArr[5] = pushNotificationToken;
                                objArr[6] = appVersion;
                                objArr[7] = displayName2;
                                if (info != null) {
                                    objArr[8] = info;
                                    objArr[9] = Integer.valueOf(mask0);
                                    objArr[10] = null;
                                    ClientDto newInstance = localConstructor.newInstance(objArr);
                                    k.d(newInstance, "localConstructor.newInst…torMarker */ null\n      )");
                                    return newInstance;
                                }
                                JsonDataException l = b.l("info", "info", iVar);
                                k.d(l, "missingProperty(\"info\", \"info\", reader)");
                                throw l;
                            }
                            String str9 = str;
                            JsonDataException l2 = b.l(str9, str9, iVar);
                            k.d(l2, str8);
                            throw l2;
                        }
                        String str10 = str7;
                        JsonDataException l3 = b.l(str10, str10, iVar);
                        k.d(l3, str3);
                        throw l3;
                    }
                    String str11 = str2;
                    JsonDataException l4 = b.l(str11, str11, iVar);
                    k.d(l4, str4);
                    throw l4;
                } else if (id == null) {
                    String str12 = str2;
                    JsonDataException l5 = b.l(str12, str12, iVar);
                    k.d(l5, str4);
                    throw l5;
                } else if (platform == null) {
                    JsonDataException l6 = b.l("platform", "platform", iVar);
                    k.d(l6, str3);
                    throw l6;
                } else if (integrationId == null) {
                    JsonDataException l7 = b.l("integrationId", "integrationId", iVar);
                    k.d(l7, "missingProperty(\"integra… \"integrationId\", reader)");
                    throw l7;
                } else if (info != null) {
                    return new ClientDto(id, status, lastSeen, platform, integrationId, pushNotificationToken, appVersion, displayName2, info);
                } else {
                    JsonDataException l8 = b.l("info", "info", iVar);
                    k.d(l8, "missingProperty(\"info\", \"info\", reader)");
                    throw l8;
                }
            }
        }
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable ClientDto value_) {
        k.e(writer, "writer");
        if (value_ != null) {
            writer.g();
            writer.r("id");
            this.b.i(writer, value_.c());
            writer.r("status");
            this.c.i(writer, value_.i());
            writer.r("lastSeen");
            this.c.i(writer, value_.f());
            writer.r("platform");
            this.b.i(writer, value_.g());
            writer.r("integrationId");
            this.b.i(writer, value_.e());
            writer.r("pushNotificationToken");
            this.c.i(writer, value_.h());
            writer.r("appVersion");
            this.c.i(writer, value_.a());
            writer.r("displayName");
            this.c.i(writer, value_.b());
            writer.r("info");
            this.d.i(writer, value_.d());
            writer.m();
            return;
        }
        throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
    }
}
