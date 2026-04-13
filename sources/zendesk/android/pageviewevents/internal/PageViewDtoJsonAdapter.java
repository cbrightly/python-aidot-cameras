package zendesk.android.pageviewevents.internal;

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

/* compiled from: PageViewDtoJsonAdapter.kt */
public final class PageViewDtoJsonAdapter extends f<PageViewDto> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<String> b;

    public PageViewDtoJsonAdapter(@NotNull r moshi) {
        k.e(moshi, "moshi");
        i.a a2 = i.a.a("pageTitle", "navigatorLanguage", "userAgent");
        k.d(a2, "of(\"pageTitle\", \"navigat…uage\",\n      \"userAgent\")");
        this.a = a2;
        f<String> f = moshi.f(String.class, o0.d(), "pageTitle");
        k.d(f, "moshi.adapter(String::cl…Set(),\n      \"pageTitle\")");
        this.b = f;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(33);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("PageViewDto");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public PageViewDto b(@NotNull i reader) {
        k.e(reader, "reader");
        String pageTitle = null;
        String navigatorLanguage = null;
        String userAgent = null;
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
                        pageTitle = b2;
                        break;
                    } else {
                        JsonDataException u = b.u("pageTitle", "pageTitle", reader);
                        k.d(u, "unexpectedNull(\"pageTitl…     \"pageTitle\", reader)");
                        throw u;
                    }
                case 1:
                    String b3 = this.b.b(reader);
                    if (b3 != null) {
                        navigatorLanguage = b3;
                        break;
                    } else {
                        JsonDataException u2 = b.u("navigatorLanguage", "navigatorLanguage", reader);
                        k.d(u2, "unexpectedNull(\"navigato…vigatorLanguage\", reader)");
                        throw u2;
                    }
                case 2:
                    String b4 = this.b.b(reader);
                    if (b4 != null) {
                        userAgent = b4;
                        break;
                    } else {
                        JsonDataException u3 = b.u("userAgent", "userAgent", reader);
                        k.d(u3, "unexpectedNull(\"userAgen…     \"userAgent\", reader)");
                        throw u3;
                    }
            }
        }
        reader.i();
        if (pageTitle == null) {
            JsonDataException l = b.l("pageTitle", "pageTitle", reader);
            k.d(l, "missingProperty(\"pageTitle\", \"pageTitle\", reader)");
            throw l;
        } else if (navigatorLanguage == null) {
            JsonDataException l2 = b.l("navigatorLanguage", "navigatorLanguage", reader);
            k.d(l2, "missingProperty(\"navigat…vigatorLanguage\", reader)");
            throw l2;
        } else if (userAgent != null) {
            return new PageViewDto(pageTitle, navigatorLanguage, userAgent);
        } else {
            JsonDataException l3 = b.l("userAgent", "userAgent", reader);
            k.d(l3, "missingProperty(\"userAgent\", \"userAgent\", reader)");
            throw l3;
        }
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable PageViewDto value_) {
        k.e(writer, "writer");
        if (value_ != null) {
            writer.g();
            writer.r("pageTitle");
            this.b.i(writer, value_.b());
            writer.r("navigatorLanguage");
            this.b.i(writer, value_.a());
            writer.r("userAgent");
            this.b.i(writer, value_.c());
            writer.m();
            return;
        }
        throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
    }
}
