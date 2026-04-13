package zendesk.conversationkit.android.model;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.squareup.moshi.JsonDataException;
import com.squareup.moshi.f;
import com.squareup.moshi.i;
import com.squareup.moshi.internal.b;
import com.squareup.moshi.o;
import com.squareup.moshi.r;
import com.squareup.moshi.t;
import java.util.List;
import kotlin.collections.o0;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.conversationkit.android.model.MessageContent;

/* compiled from: MessageContent_CarouselJsonAdapter.kt */
public final class MessageContent_CarouselJsonAdapter extends f<MessageContent.Carousel> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<List<MessageItem>> b;

    public MessageContent_CarouselJsonAdapter(@NotNull r moshi) {
        k.e(moshi, "moshi");
        i.a a2 = i.a.a(FirebaseAnalytics.Param.ITEMS);
        k.d(a2, "of(\"items\")");
        this.a = a2;
        f<List<MessageItem>> f = moshi.f(t.j(List.class, MessageItem.class), o0.d(), FirebaseAnalytics.Param.ITEMS);
        k.d(f, "moshi.adapter(Types.newP…     emptySet(), \"items\")");
        this.b = f;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(45);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("MessageContent.Carousel");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public MessageContent.Carousel b(@NotNull i reader) {
        k.e(reader, "reader");
        List items = null;
        reader.c();
        while (reader.l()) {
            switch (reader.J(this.a)) {
                case -1:
                    reader.o0();
                    reader.u0();
                    break;
                case 0:
                    List b2 = this.b.b(reader);
                    if (b2 != null) {
                        items = b2;
                        break;
                    } else {
                        JsonDataException u = b.u(FirebaseAnalytics.Param.ITEMS, FirebaseAnalytics.Param.ITEMS, reader);
                        k.d(u, "unexpectedNull(\"items\",\n…         \"items\", reader)");
                        throw u;
                    }
            }
        }
        reader.i();
        if (items != null) {
            return new MessageContent.Carousel(items);
        }
        JsonDataException l = b.l(FirebaseAnalytics.Param.ITEMS, FirebaseAnalytics.Param.ITEMS, reader);
        k.d(l, "missingProperty(\"items\", \"items\", reader)");
        throw l;
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable MessageContent.Carousel value_) {
        k.e(writer, "writer");
        if (value_ != null) {
            writer.g();
            writer.r(FirebaseAnalytics.Param.ITEMS);
            this.b.i(writer, value_.b());
            writer.m();
            return;
        }
        throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
    }
}
