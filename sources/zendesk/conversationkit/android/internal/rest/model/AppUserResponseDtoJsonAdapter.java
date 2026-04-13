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

/* compiled from: AppUserResponseDtoJsonAdapter.kt */
public final class AppUserResponseDtoJsonAdapter extends f<AppUserResponseDto> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<UserSettingsDto> b;
    @NotNull
    private final f<List<ConversationDto>> c;
    @NotNull
    private final f<ConversationsPaginationDto> d;
    @NotNull
    private final f<AppUserDto> e;
    @NotNull
    private final f<Map<String, AppUserDto>> f;
    @NotNull
    private final f<String> g;

    public AppUserResponseDtoJsonAdapter(@NotNull r moshi) {
        Class<String> cls = String.class;
        Class<AppUserDto> cls2 = AppUserDto.class;
        k.e(moshi, "moshi");
        i.a a2 = i.a.a("settings", "conversations", "conversationsPagination", "appUser", "appUsers", "sessionToken");
        k.d(a2, "of(\"settings\", \"conversa…ppUsers\", \"sessionToken\")");
        this.a = a2;
        f<UserSettingsDto> f2 = moshi.f(UserSettingsDto.class, o0.d(), "settings");
        k.d(f2, "moshi.adapter(UserSettin…, emptySet(), \"settings\")");
        this.b = f2;
        f<List<ConversationDto>> f3 = moshi.f(t.j(List.class, ConversationDto.class), o0.d(), "conversations");
        k.d(f3, "moshi.adapter(Types.newP…tySet(), \"conversations\")");
        this.c = f3;
        f<ConversationsPaginationDto> f4 = moshi.f(ConversationsPaginationDto.class, o0.d(), "conversationsPagination");
        k.d(f4, "moshi.adapter(Conversati…conversationsPagination\")");
        this.d = f4;
        f<AppUserDto> f5 = moshi.f(cls2, o0.d(), "appUser");
        k.d(f5, "moshi.adapter(AppUserDto…   emptySet(), \"appUser\")");
        this.e = f5;
        f<Map<String, AppUserDto>> f6 = moshi.f(t.j(Map.class, cls, cls2), o0.d(), "appUsers");
        k.d(f6, "moshi.adapter(Types.newP…, emptySet(), \"appUsers\")");
        this.f = f6;
        f<String> f7 = moshi.f(cls, o0.d(), "sessionToken");
        k.d(f7, "moshi.adapter(String::cl…ptySet(), \"sessionToken\")");
        this.g = f7;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(40);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("AppUserResponseDto");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public AppUserResponseDto b(@NotNull i reader) {
        k.e(reader, "reader");
        UserSettingsDto settings = null;
        List conversations = null;
        ConversationsPaginationDto conversationsPagination = null;
        AppUserDto appUser = null;
        Map appUsers = null;
        String sessionToken = null;
        reader.c();
        while (reader.l()) {
            switch (reader.J(this.a)) {
                case -1:
                    reader.o0();
                    reader.u0();
                    break;
                case 0:
                    UserSettingsDto b2 = this.b.b(reader);
                    if (b2 != null) {
                        settings = b2;
                        break;
                    } else {
                        JsonDataException u = b.u("settings", "settings", reader);
                        k.d(u, "unexpectedNull(\"settings\", \"settings\", reader)");
                        throw u;
                    }
                case 1:
                    List b3 = this.c.b(reader);
                    if (b3 != null) {
                        conversations = b3;
                        break;
                    } else {
                        JsonDataException u2 = b.u("conversations", "conversations", reader);
                        k.d(u2, "unexpectedNull(\"conversa… \"conversations\", reader)");
                        throw u2;
                    }
                case 2:
                    ConversationsPaginationDto b4 = this.d.b(reader);
                    if (b4 != null) {
                        conversationsPagination = b4;
                        break;
                    } else {
                        JsonDataException u3 = b.u("conversationsPagination", "conversationsPagination", reader);
                        k.d(u3, "unexpectedNull(\"conversa…tionsPagination\", reader)");
                        throw u3;
                    }
                case 3:
                    AppUserDto b5 = this.e.b(reader);
                    if (b5 != null) {
                        appUser = b5;
                        break;
                    } else {
                        JsonDataException u4 = b.u("appUser", "appUser", reader);
                        k.d(u4, "unexpectedNull(\"appUser\"…       \"appUser\", reader)");
                        throw u4;
                    }
                case 4:
                    Map b6 = this.f.b(reader);
                    if (b6 != null) {
                        appUsers = b6;
                        break;
                    } else {
                        JsonDataException u5 = b.u("appUsers", "appUsers", reader);
                        k.d(u5, "unexpectedNull(\"appUsers\", \"appUsers\", reader)");
                        throw u5;
                    }
                case 5:
                    sessionToken = this.g.b(reader);
                    break;
            }
        }
        reader.i();
        if (settings == null) {
            JsonDataException l = b.l("settings", "settings", reader);
            k.d(l, "missingProperty(\"settings\", \"settings\", reader)");
            throw l;
        } else if (conversations == null) {
            JsonDataException l2 = b.l("conversations", "conversations", reader);
            k.d(l2, "missingProperty(\"convers… \"conversations\", reader)");
            throw l2;
        } else if (conversationsPagination == null) {
            JsonDataException l3 = b.l("conversationsPagination", "conversationsPagination", reader);
            k.d(l3, "missingProperty(\"convers…ion\",\n            reader)");
            throw l3;
        } else if (appUser == null) {
            JsonDataException l4 = b.l("appUser", "appUser", reader);
            k.d(l4, "missingProperty(\"appUser\", \"appUser\", reader)");
            throw l4;
        } else if (appUsers != null) {
            return new AppUserResponseDto(settings, conversations, conversationsPagination, appUser, appUsers, sessionToken);
        } else {
            JsonDataException l5 = b.l("appUsers", "appUsers", reader);
            k.d(l5, "missingProperty(\"appUsers\", \"appUsers\", reader)");
            throw l5;
        }
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable AppUserResponseDto value_) {
        k.e(writer, "writer");
        if (value_ != null) {
            writer.g();
            writer.r("settings");
            this.b.i(writer, value_.f());
            writer.r("conversations");
            this.c.i(writer, value_.c());
            writer.r("conversationsPagination");
            this.d.i(writer, value_.d());
            writer.r("appUser");
            this.e.i(writer, value_.a());
            writer.r("appUsers");
            this.f.i(writer, value_.b());
            writer.r("sessionToken");
            this.g.i(writer, value_.e());
            writer.m();
            return;
        }
        throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
    }
}
