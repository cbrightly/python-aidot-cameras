package zendesk.conversationkit.android.internal.rest.model;

import com.squareup.moshi.JsonDataException;
import com.squareup.moshi.f;
import com.squareup.moshi.i;
import com.squareup.moshi.internal.b;
import com.squareup.moshi.o;
import com.squareup.moshi.r;
import kotlin.collections.o0;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ActivityDataRequestDtoJsonAdapter.kt */
public final class ActivityDataRequestDtoJsonAdapter extends f<ActivityDataRequestDto> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<AuthorDto> b;
    @NotNull
    private final f<ActivityDataDto> c;

    public ActivityDataRequestDtoJsonAdapter(@NotNull r moshi) {
        k.e(moshi, "moshi");
        i.a a2 = i.a.a("author", "activity");
        k.d(a2, "of(\"author\", \"activity\")");
        this.a = a2;
        f<AuthorDto> f = moshi.f(AuthorDto.class, o0.d(), "author");
        k.d(f, "moshi.adapter(AuthorDto:…    emptySet(), \"author\")");
        this.b = f;
        f<ActivityDataDto> f2 = moshi.f(ActivityDataDto.class, o0.d(), "activity");
        k.d(f2, "moshi.adapter(ActivityDa…, emptySet(), \"activity\")");
        this.c = f2;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(44);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("ActivityDataRequestDto");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public ActivityDataRequestDto b(@NotNull i reader) {
        k.e(reader, "reader");
        AuthorDto author = null;
        ActivityDataDto activity = null;
        reader.c();
        while (reader.l()) {
            switch (reader.J(this.a)) {
                case -1:
                    reader.o0();
                    reader.u0();
                    break;
                case 0:
                    AuthorDto b2 = this.b.b(reader);
                    if (b2 != null) {
                        author = b2;
                        break;
                    } else {
                        JsonDataException u = b.u("author", "author", reader);
                        k.d(u, "unexpectedNull(\"author\",…        \"author\", reader)");
                        throw u;
                    }
                case 1:
                    ActivityDataDto b3 = this.c.b(reader);
                    if (b3 != null) {
                        activity = b3;
                        break;
                    } else {
                        JsonDataException u2 = b.u("activity", "activity", reader);
                        k.d(u2, "unexpectedNull(\"activity\", \"activity\", reader)");
                        throw u2;
                    }
            }
        }
        reader.i();
        if (author == null) {
            JsonDataException l = b.l("author", "author", reader);
            k.d(l, "missingProperty(\"author\", \"author\", reader)");
            throw l;
        } else if (activity != null) {
            return new ActivityDataRequestDto(author, activity);
        } else {
            JsonDataException l2 = b.l("activity", "activity", reader);
            k.d(l2, "missingProperty(\"activity\", \"activity\", reader)");
            throw l2;
        }
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable ActivityDataRequestDto value_) {
        k.e(writer, "writer");
        if (value_ != null) {
            writer.g();
            writer.r("author");
            this.b.i(writer, value_.b());
            writer.r("activity");
            this.c.i(writer, value_.a());
            writer.m();
            return;
        }
        throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
    }
}
