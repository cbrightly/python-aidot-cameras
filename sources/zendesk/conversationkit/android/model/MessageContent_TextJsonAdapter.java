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
import zendesk.conversationkit.android.model.MessageContent;

/* compiled from: MessageContent_TextJsonAdapter.kt */
public final class MessageContent_TextJsonAdapter extends f<MessageContent.Text> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<String> b;
    @NotNull
    private final f<List<MessageAction>> c;
    @Nullable
    private volatile Constructor<MessageContent.Text> d;

    public MessageContent_TextJsonAdapter(@NotNull r moshi) {
        k.e(moshi, "moshi");
        i.a a2 = i.a.a("text", "actions");
        k.d(a2, "of(\"text\", \"actions\")");
        this.a = a2;
        f<String> f = moshi.f(String.class, o0.d(), "text");
        k.d(f, "moshi.adapter(String::cl…emptySet(),\n      \"text\")");
        this.b = f;
        f<List<MessageAction>> f2 = moshi.f(t.j(List.class, MessageAction.class), o0.d(), "actions");
        k.d(f2, "moshi.adapter(Types.newP…   emptySet(), \"actions\")");
        this.c = f2;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(41);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("MessageContent.Text");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public MessageContent.Text b(@NotNull i reader) {
        k.e(reader, "reader");
        String text = null;
        List actions = null;
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
                        text = b2;
                        break;
                    } else {
                        JsonDataException u = b.u("text", "text", reader);
                        k.d(u, "unexpectedNull(\"text\", \"text\",\n            reader)");
                        throw u;
                    }
                case 1:
                    actions = this.c.b(reader);
                    mask0 &= -3;
                    break;
            }
        }
        reader.i();
        if (mask0 != -3) {
            Constructor localConstructor = this.d;
            if (localConstructor == null) {
                localConstructor = MessageContent.Text.class.getDeclaredConstructor(new Class[]{String.class, List.class, Integer.TYPE, b.c});
                this.d = localConstructor;
                k.d(localConstructor, "MessageContent.Text::cla…his.constructorRef = it }");
            }
            Object[] objArr = new Object[4];
            if (text != null) {
                objArr[0] = text;
                objArr[1] = actions;
                objArr[2] = Integer.valueOf(mask0);
                objArr[3] = null;
                MessageContent.Text newInstance = localConstructor.newInstance(objArr);
                k.d(newInstance, "localConstructor.newInst…torMarker */ null\n      )");
                return newInstance;
            }
            JsonDataException l = b.l("text", "text", reader);
            k.d(l, "missingProperty(\"text\", \"text\", reader)");
            throw l;
        } else if (text != null) {
            return new MessageContent.Text(text, actions);
        } else {
            JsonDataException l2 = b.l("text", "text", reader);
            k.d(l2, "missingProperty(\"text\", \"text\", reader)");
            throw l2;
        }
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable MessageContent.Text value_) {
        k.e(writer, "writer");
        if (value_ != null) {
            writer.g();
            writer.r("text");
            this.b.i(writer, value_.c());
            writer.r("actions");
            this.c.i(writer, value_.b());
            writer.m();
            return;
        }
        throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
    }
}
