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

/* compiled from: ConversationResponseDtoJsonAdapter.kt */
public final class ConversationResponseDtoJsonAdapter extends f<ConversationResponseDto> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<ConversationDto> b;
    @NotNull
    private final f<List<MessageDto>> c;
    @NotNull
    private final f<Boolean> d;
    @NotNull
    private final f<AppUserDto> e;
    @NotNull
    private final f<Map<String, AppUserDto>> f;

    public ConversationResponseDtoJsonAdapter(@NotNull r moshi) {
        Class<AppUserDto> cls = AppUserDto.class;
        k.e(moshi, "moshi");
        i.a a2 = i.a.a("conversation", "messages", "hasPrevious", "appUser", "appUsers");
        k.d(a2, "of(\"conversation\", \"mess…\", \"appUser\", \"appUsers\")");
        this.a = a2;
        f<ConversationDto> f2 = moshi.f(ConversationDto.class, o0.d(), "conversation");
        k.d(f2, "moshi.adapter(Conversati…ptySet(), \"conversation\")");
        this.b = f2;
        f<List<MessageDto>> f3 = moshi.f(t.j(List.class, MessageDto.class), o0.d(), "messages");
        k.d(f3, "moshi.adapter(Types.newP…  emptySet(), \"messages\")");
        this.c = f3;
        f<Boolean> f4 = moshi.f(Boolean.class, o0.d(), "hasPrevious");
        k.d(f4, "moshi.adapter(Boolean::c…mptySet(), \"hasPrevious\")");
        this.d = f4;
        f<AppUserDto> f5 = moshi.f(cls, o0.d(), "appUser");
        k.d(f5, "moshi.adapter(AppUserDto…   emptySet(), \"appUser\")");
        this.e = f5;
        f<Map<String, AppUserDto>> f6 = moshi.f(t.j(Map.class, String.class, cls), o0.d(), "appUsers");
        k.d(f6, "moshi.adapter(Types.newP…, emptySet(), \"appUsers\")");
        this.f = f6;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(45);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("ConversationResponseDto");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public ConversationResponseDto b(@NotNull i reader) {
        k.e(reader, "reader");
        ConversationDto conversation = null;
        List messages = null;
        Boolean hasPrevious = null;
        AppUserDto appUser = null;
        Map appUsers = null;
        reader.c();
        while (reader.l()) {
            switch (reader.J(this.a)) {
                case -1:
                    reader.o0();
                    reader.u0();
                    break;
                case 0:
                    ConversationDto b2 = this.b.b(reader);
                    if (b2 != null) {
                        conversation = b2;
                        break;
                    } else {
                        JsonDataException u = b.u("conversation", "conversation", reader);
                        k.d(u, "unexpectedNull(\"conversa…, \"conversation\", reader)");
                        throw u;
                    }
                case 1:
                    messages = this.c.b(reader);
                    break;
                case 2:
                    hasPrevious = this.d.b(reader);
                    break;
                case 3:
                    AppUserDto b3 = this.e.b(reader);
                    if (b3 != null) {
                        appUser = b3;
                        break;
                    } else {
                        JsonDataException u2 = b.u("appUser", "appUser", reader);
                        k.d(u2, "unexpectedNull(\"appUser\"…       \"appUser\", reader)");
                        throw u2;
                    }
                case 4:
                    Map b4 = this.f.b(reader);
                    if (b4 != null) {
                        appUsers = b4;
                        break;
                    } else {
                        JsonDataException u3 = b.u("appUsers", "appUsers", reader);
                        k.d(u3, "unexpectedNull(\"appUsers\", \"appUsers\", reader)");
                        throw u3;
                    }
            }
        }
        reader.i();
        if (conversation == null) {
            JsonDataException l = b.l("conversation", "conversation", reader);
            k.d(l, "missingProperty(\"convers…ion\",\n            reader)");
            throw l;
        } else if (appUser == null) {
            JsonDataException l2 = b.l("appUser", "appUser", reader);
            k.d(l2, "missingProperty(\"appUser\", \"appUser\", reader)");
            throw l2;
        } else if (appUsers != null) {
            return new ConversationResponseDto(conversation, messages, hasPrevious, appUser, appUsers);
        } else {
            JsonDataException l3 = b.l("appUsers", "appUsers", reader);
            k.d(l3, "missingProperty(\"appUsers\", \"appUsers\", reader)");
            throw l3;
        }
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable ConversationResponseDto value_) {
        k.e(writer, "writer");
        if (value_ != null) {
            writer.g();
            writer.r("conversation");
            this.b.i(writer, value_.c());
            writer.r("messages");
            this.c.i(writer, value_.e());
            writer.r("hasPrevious");
            this.d.i(writer, value_.d());
            writer.r("appUser");
            this.e.i(writer, value_.a());
            writer.r("appUsers");
            this.f.i(writer, value_.b());
            writer.m();
            return;
        }
        throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
    }
}
