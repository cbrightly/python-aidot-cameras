package zendesk.conversationkit.android.model;

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
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: AuthorJsonAdapter.kt */
public final class AuthorJsonAdapter extends f<Author> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<String> b;
    @NotNull
    private final f<g> c;
    @NotNull
    private final f<String> d;
    @Nullable
    private volatile Constructor<Author> e;

    public AuthorJsonAdapter(@NotNull r moshi) {
        Class<String> cls = String.class;
        k.e(moshi, "moshi");
        i.a a2 = i.a.a("userId", IjkMediaMeta.IJKM_KEY_TYPE, "displayName", "avatarUrl");
        k.d(a2, "of(\"userId\", \"type\", \"di…Name\",\n      \"avatarUrl\")");
        this.a = a2;
        f<String> f = moshi.f(cls, o0.d(), "userId");
        k.d(f, "moshi.adapter(String::cl…ptySet(),\n      \"userId\")");
        this.b = f;
        f<g> f2 = moshi.f(g.class, o0.d(), IjkMediaMeta.IJKM_KEY_TYPE);
        k.d(f2, "moshi.adapter(AuthorType…      emptySet(), \"type\")");
        this.c = f2;
        f<String> f3 = moshi.f(cls, o0.d(), "avatarUrl");
        k.d(f3, "moshi.adapter(String::cl… emptySet(), \"avatarUrl\")");
        this.d = f3;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(28);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("Author");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public Author b(@NotNull i reader) {
        i iVar = reader;
        Class<String> cls = String.class;
        k.e(iVar, "reader");
        String userId = null;
        g type = null;
        String displayName = null;
        String avatarUrl = null;
        int mask0 = -1;
        reader.c();
        while (reader.l()) {
            switch (iVar.J(this.a)) {
                case -1:
                    reader.o0();
                    reader.u0();
                    break;
                case 0:
                    String b2 = this.b.b(iVar);
                    if (b2 != null) {
                        userId = b2;
                        mask0 &= -2;
                        break;
                    } else {
                        JsonDataException u = b.u("userId", "userId", iVar);
                        k.d(u, "unexpectedNull(\"userId\",…d\",\n              reader)");
                        throw u;
                    }
                case 1:
                    g b3 = this.c.b(iVar);
                    if (b3 != null) {
                        type = b3;
                        mask0 &= -3;
                        break;
                    } else {
                        JsonDataException u2 = b.u(IjkMediaMeta.IJKM_KEY_TYPE, IjkMediaMeta.IJKM_KEY_TYPE, iVar);
                        k.d(u2, "unexpectedNull(\"type\", \"…e\",\n              reader)");
                        throw u2;
                    }
                case 2:
                    String b4 = this.b.b(iVar);
                    if (b4 != null) {
                        displayName = b4;
                        mask0 &= -5;
                        break;
                    } else {
                        JsonDataException u3 = b.u("displayName", "displayName", iVar);
                        k.d(u3, "unexpectedNull(\"displayN…   \"displayName\", reader)");
                        throw u3;
                    }
                case 3:
                    avatarUrl = this.d.b(iVar);
                    mask0 &= -9;
                    break;
            }
        }
        reader.i();
        if (mask0 != -16) {
            Constructor it = this.e;
            if (it == null) {
                it = Author.class.getDeclaredConstructor(new Class[]{cls, g.class, cls, cls, Integer.TYPE, b.c});
                this.e = it;
                k.d(it, "Author::class.java.getDe…his.constructorRef = it }");
            }
            Author newInstance = it.newInstance(new Object[]{userId, type, displayName, avatarUrl, Integer.valueOf(mask0), null});
            k.d(newInstance, "localConstructor.newInst…torMarker */ null\n      )");
            return newInstance;
        } else if (userId == null) {
            throw new NullPointerException("null cannot be cast to non-null type kotlin.String");
        } else if (type == null) {
            throw new NullPointerException("null cannot be cast to non-null type zendesk.conversationkit.android.model.AuthorType");
        } else if (displayName != null) {
            return new Author(userId, type, displayName, avatarUrl);
        } else {
            throw new NullPointerException("null cannot be cast to non-null type kotlin.String");
        }
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable Author value_) {
        k.e(writer, "writer");
        if (value_ != null) {
            writer.g();
            writer.r("userId");
            this.b.i(writer, value_.f());
            writer.r(IjkMediaMeta.IJKM_KEY_TYPE);
            this.c.i(writer, value_.e());
            writer.r("displayName");
            this.b.i(writer, value_.d());
            writer.r("avatarUrl");
            this.d.i(writer, value_.c());
            writer.m();
            return;
        }
        throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
    }
}
