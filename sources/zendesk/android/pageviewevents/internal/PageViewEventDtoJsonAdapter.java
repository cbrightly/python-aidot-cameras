package zendesk.android.pageviewevents.internal;

import com.squareup.moshi.JsonDataException;
import com.squareup.moshi.f;
import com.squareup.moshi.i;
import com.squareup.moshi.internal.b;
import com.squareup.moshi.o;
import com.squareup.moshi.r;
import kotlin.collections.o0;
import kotlin.jvm.internal.k;
import meshsdk.ConfigUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: PageViewEventDtoJsonAdapter.kt */
public final class PageViewEventDtoJsonAdapter extends f<PageViewEventDto> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<String> b;
    @NotNull
    private final f<PageViewDto> c;

    public PageViewEventDtoJsonAdapter(@NotNull r moshi) {
        k.e(moshi, "moshi");
        i.a a2 = i.a.a("url", "buid", "channel", ConfigUtil.VERSION_FILE, "timestamp", "suid", "pageView");
        k.d(a2, "of(\"url\", \"buid\", \"chann…amp\", \"suid\", \"pageView\")");
        this.a = a2;
        f<String> f = moshi.f(String.class, o0.d(), "url");
        k.d(f, "moshi.adapter(String::cl… emptySet(),\n      \"url\")");
        this.b = f;
        f<PageViewDto> f2 = moshi.f(PageViewDto.class, o0.d(), "pageView");
        k.d(f2, "moshi.adapter(PageViewDt…  emptySet(), \"pageView\")");
        this.c = f2;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(38);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("PageViewEventDto");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public PageViewEventDto b(@NotNull i reader) {
        i iVar = reader;
        k.e(iVar, "reader");
        String url = null;
        String buid = null;
        String channel = null;
        String version = null;
        String timestamp = null;
        String suid = null;
        PageViewDto pageView = null;
        reader.c();
        while (true) {
            PageViewDto pageView2 = pageView;
            if (reader.l()) {
                switch (iVar.J(this.a)) {
                    case -1:
                        reader.o0();
                        reader.u0();
                        break;
                    case 0:
                        String b2 = this.b.b(iVar);
                        if (b2 != null) {
                            url = b2;
                            pageView = pageView2;
                            continue;
                        } else {
                            JsonDataException u = b.u("url", "url", iVar);
                            k.d(u, "unexpectedNull(\"url\", \"url\", reader)");
                            throw u;
                        }
                    case 1:
                        String b3 = this.b.b(iVar);
                        if (b3 != null) {
                            buid = b3;
                            pageView = pageView2;
                            continue;
                        } else {
                            JsonDataException u2 = b.u("buid", "buid", iVar);
                            k.d(u2, "unexpectedNull(\"buid\", \"buid\",\n            reader)");
                            throw u2;
                        }
                    case 2:
                        String b4 = this.b.b(iVar);
                        if (b4 != null) {
                            channel = b4;
                            pageView = pageView2;
                            continue;
                        } else {
                            JsonDataException u3 = b.u("channel", "channel", iVar);
                            k.d(u3, "unexpectedNull(\"channel\"…       \"channel\", reader)");
                            throw u3;
                        }
                    case 3:
                        String b5 = this.b.b(iVar);
                        if (b5 != null) {
                            version = b5;
                            pageView = pageView2;
                            continue;
                        } else {
                            JsonDataException u4 = b.u(ConfigUtil.VERSION_FILE, ConfigUtil.VERSION_FILE, iVar);
                            k.d(u4, "unexpectedNull(\"version\"…       \"version\", reader)");
                            throw u4;
                        }
                    case 4:
                        String b6 = this.b.b(iVar);
                        if (b6 != null) {
                            timestamp = b6;
                            pageView = pageView2;
                            continue;
                        } else {
                            JsonDataException u5 = b.u("timestamp", "timestamp", iVar);
                            k.d(u5, "unexpectedNull(\"timestam…     \"timestamp\", reader)");
                            throw u5;
                        }
                    case 5:
                        String b7 = this.b.b(iVar);
                        if (b7 != null) {
                            suid = b7;
                            pageView = pageView2;
                            continue;
                        } else {
                            JsonDataException u6 = b.u("suid", "suid", iVar);
                            k.d(u6, "unexpectedNull(\"suid\", \"suid\",\n            reader)");
                            throw u6;
                        }
                    case 6:
                        pageView = this.c.b(iVar);
                        if (pageView != null) {
                            continue;
                        } else {
                            JsonDataException u7 = b.u("pageView", "pageView", iVar);
                            k.d(u7, "unexpectedNull(\"pageView…      \"pageView\", reader)");
                            throw u7;
                        }
                }
                pageView = pageView2;
            } else {
                reader.i();
                if (url == null) {
                    JsonDataException l = b.l("url", "url", iVar);
                    k.d(l, "missingProperty(\"url\", \"url\", reader)");
                    throw l;
                } else if (buid == null) {
                    JsonDataException l2 = b.l("buid", "buid", iVar);
                    k.d(l2, "missingProperty(\"buid\", \"buid\", reader)");
                    throw l2;
                } else if (channel == null) {
                    JsonDataException l3 = b.l("channel", "channel", iVar);
                    k.d(l3, "missingProperty(\"channel\", \"channel\", reader)");
                    throw l3;
                } else if (version == null) {
                    JsonDataException l4 = b.l(ConfigUtil.VERSION_FILE, ConfigUtil.VERSION_FILE, iVar);
                    k.d(l4, "missingProperty(\"version\", \"version\", reader)");
                    throw l4;
                } else if (timestamp == null) {
                    JsonDataException l5 = b.l("timestamp", "timestamp", iVar);
                    k.d(l5, "missingProperty(\"timestamp\", \"timestamp\", reader)");
                    throw l5;
                } else if (suid == null) {
                    JsonDataException l6 = b.l("suid", "suid", iVar);
                    k.d(l6, "missingProperty(\"suid\", \"suid\", reader)");
                    throw l6;
                } else if (pageView2 != null) {
                    return new PageViewEventDto(url, buid, channel, version, timestamp, suid, pageView2);
                } else {
                    JsonDataException l7 = b.l("pageView", "pageView", iVar);
                    k.d(l7, "missingProperty(\"pageView\", \"pageView\", reader)");
                    throw l7;
                }
            }
        }
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable PageViewEventDto value_) {
        k.e(writer, "writer");
        if (value_ != null) {
            writer.g();
            writer.r("url");
            this.b.i(writer, value_.f());
            writer.r("buid");
            this.b.i(writer, value_.a());
            writer.r("channel");
            this.b.i(writer, value_.b());
            writer.r(ConfigUtil.VERSION_FILE);
            this.b.i(writer, value_.g());
            writer.r("timestamp");
            this.b.i(writer, value_.e());
            writer.r("suid");
            this.b.i(writer, value_.d());
            writer.r("pageView");
            this.c.i(writer, value_.c());
            writer.m();
            return;
        }
        throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
    }
}
