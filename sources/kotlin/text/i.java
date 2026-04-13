package kotlin.text;

import java.util.Iterator;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import kotlin.collections.d;
import kotlin.collections.q;
import kotlin.collections.y;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.sequences.o;
import kotlin.text.h;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Regex.kt */
public final class i implements h {
    @NotNull
    private final g a = new b(this);
    private List<String> b;
    private final Matcher c;
    private final CharSequence d;

    public i(@NotNull Matcher matcher, @NotNull CharSequence input) {
        k.e(matcher, "matcher");
        k.e(input, "input");
        this.c = matcher;
        this.d = input;
    }

    @NotNull
    public h.b a() {
        return h.a.a(this);
    }

    /* access modifiers changed from: private */
    public final MatchResult f() {
        return this.c;
    }

    @NotNull
    public kotlin.ranges.i c() {
        return l.h(f());
    }

    /* compiled from: Regex.kt */
    public static final class b extends kotlin.collections.a<f> implements g {
        final /* synthetic */ i c;

        /* compiled from: Regex.kt */
        public static final class a extends l implements kotlin.jvm.functions.l<Integer, f> {
            final /* synthetic */ b this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(b bVar) {
                super(1);
                this.this$0 = bVar;
            }

            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                return invoke(((Number) obj).intValue());
            }

            @Nullable
            public final f invoke(int it) {
                return this.this$0.get(it);
            }
        }

        b(i this$0) {
            this.c = this$0;
        }

        public /* bridge */ boolean b(f fVar) {
            return super.contains(fVar);
        }

        public final /* bridge */ boolean contains(Object obj) {
            if (obj != null ? obj instanceof f : true) {
                return b((f) obj);
            }
            return false;
        }

        public int a() {
            return this.c.f().groupCount() + 1;
        }

        public boolean isEmpty() {
            return false;
        }

        @NotNull
        public Iterator<f> iterator() {
            return o.w(y.L(q.h(this)), new a(this)).iterator();
        }

        @Nullable
        public f get(int index) {
            kotlin.ranges.i range = l.i(this.c.f(), index);
            if (range.getStart().intValue() < 0) {
                return null;
            }
            String group = this.c.f().group(index);
            k.d(group, "matchResult.group(index)");
            return new f(group, range);
        }
    }

    @NotNull
    public g d() {
        return this.a;
    }

    /* compiled from: Regex.kt */
    public static final class a extends d<String> {
        final /* synthetic */ i d;

        a(i this$0) {
            this.d = this$0;
        }

        public /* bridge */ boolean b(String str) {
            return super.contains(str);
        }

        public final /* bridge */ boolean contains(Object obj) {
            if (obj instanceof String) {
                return b((String) obj);
            }
            return false;
        }

        public /* bridge */ int f(String str) {
            return super.indexOf(str);
        }

        public /* bridge */ int g(String str) {
            return super.lastIndexOf(str);
        }

        public final /* bridge */ int indexOf(Object obj) {
            if (obj instanceof String) {
                return f((String) obj);
            }
            return -1;
        }

        public final /* bridge */ int lastIndexOf(Object obj) {
            if (obj instanceof String) {
                return g((String) obj);
            }
            return -1;
        }

        public int a() {
            return this.d.f().groupCount() + 1;
        }

        @NotNull
        /* renamed from: e */
        public String get(int index) {
            String group = this.d.f().group(index);
            return group != null ? group : "";
        }
    }

    @NotNull
    public List<String> b() {
        if (this.b == null) {
            this.b = new a(this);
        }
        List<String> list = this.b;
        k.c(list);
        return list;
    }

    @Nullable
    public h next() {
        int nextIndex = f().end() + (f().end() == f().start() ? 1 : 0);
        if (nextIndex > this.d.length()) {
            return null;
        }
        Matcher matcher = this.c.pattern().matcher(this.d);
        k.d(matcher, "matcher.pattern().matcher(input)");
        return l.f(matcher, nextIndex, this.d);
    }
}
