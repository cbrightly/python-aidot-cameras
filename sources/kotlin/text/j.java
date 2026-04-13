package kotlin.text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.collections.p;
import kotlin.collections.v;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.i;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.ranges.n;
import kotlin.sequences.h;
import kotlin.sequences.m;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Regex.kt */
public final class j implements Serializable {
    @NotNull
    public static final a Companion = new a((DefaultConstructorMarker) null);
    private Set<? extends m> _options;
    private final Pattern nativePattern;

    public j(@NotNull Pattern nativePattern2) {
        k.e(nativePattern2, "nativePattern");
        this.nativePattern = nativePattern2;
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public j(@org.jetbrains.annotations.NotNull java.lang.String r3) {
        /*
            r2 = this;
            java.lang.String r0 = "pattern"
            kotlin.jvm.internal.k.e(r3, r0)
            java.util.regex.Pattern r0 = java.util.regex.Pattern.compile(r3)
            java.lang.String r1 = "Pattern.compile(pattern)"
            kotlin.jvm.internal.k.d(r0, r1)
            r2.<init>((java.util.regex.Pattern) r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.text.j.<init>(java.lang.String):void");
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public j(@org.jetbrains.annotations.NotNull java.lang.String r3, @org.jetbrains.annotations.NotNull kotlin.text.m r4) {
        /*
            r2 = this;
            java.lang.String r0 = "pattern"
            kotlin.jvm.internal.k.e(r3, r0)
            java.lang.String r0 = "option"
            kotlin.jvm.internal.k.e(r4, r0)
            kotlin.text.j$a r0 = Companion
            int r1 = r4.getValue()
            int r0 = r0.b(r1)
            java.util.regex.Pattern r0 = java.util.regex.Pattern.compile(r3, r0)
            java.lang.String r1 = "Pattern.compile(pattern,…nicodeCase(option.value))"
            kotlin.jvm.internal.k.d(r0, r1)
            r2.<init>((java.util.regex.Pattern) r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.text.j.<init>(java.lang.String, kotlin.text.m):void");
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public j(@org.jetbrains.annotations.NotNull java.lang.String r3, @org.jetbrains.annotations.NotNull java.util.Set<? extends kotlin.text.m> r4) {
        /*
            r2 = this;
            java.lang.String r0 = "pattern"
            kotlin.jvm.internal.k.e(r3, r0)
            java.lang.String r0 = "options"
            kotlin.jvm.internal.k.e(r4, r0)
            kotlin.text.j$a r0 = Companion
            int r1 = kotlin.text.l.j(r4)
            int r0 = r0.b(r1)
            java.util.regex.Pattern r0 = java.util.regex.Pattern.compile(r3, r0)
            java.lang.String r1 = "Pattern.compile(pattern,…odeCase(options.toInt()))"
            kotlin.jvm.internal.k.d(r0, r1)
            r2.<init>((java.util.regex.Pattern) r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.text.j.<init>(java.lang.String, java.util.Set):void");
    }

    @NotNull
    public final String getPattern() {
        String pattern = this.nativePattern.pattern();
        k.d(pattern, "nativePattern.pattern()");
        return pattern;
    }

    @NotNull
    public final Set<m> getOptions() {
        Set it = this._options;
        if (it != null) {
            return it;
        }
        int value$iv = this.nativePattern.flags();
        EnumSet $this$apply$iv = EnumSet.allOf(m.class);
        v.D($this$apply$iv, new k(value$iv));
        Set unmodifiableSet = Collections.unmodifiableSet($this$apply$iv);
        k.d(unmodifiableSet, "Collections.unmodifiable…mask == it.value }\n    })");
        Set it2 = unmodifiableSet;
        this._options = it2;
        return it2;
    }

    public final boolean matches(@NotNull CharSequence input) {
        k.e(input, "input");
        return this.nativePattern.matcher(input).matches();
    }

    public final boolean containsMatchIn(@NotNull CharSequence input) {
        k.e(input, "input");
        return this.nativePattern.matcher(input).find();
    }

    public static /* synthetic */ h find$default(j jVar, CharSequence charSequence, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = 0;
        }
        return jVar.find(charSequence, i);
    }

    @Nullable
    public final h find(@NotNull CharSequence input, int startIndex) {
        k.e(input, "input");
        Matcher matcher = this.nativePattern.matcher(input);
        k.d(matcher, "nativePattern.matcher(input)");
        return l.f(matcher, startIndex, input);
    }

    public static /* synthetic */ h findAll$default(j jVar, CharSequence charSequence, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = 0;
        }
        return jVar.findAll(charSequence, i);
    }

    @NotNull
    public final h<h> findAll(@NotNull CharSequence input, int startIndex) {
        k.e(input, "input");
        if (startIndex >= 0 && startIndex <= input.length()) {
            return m.j(new c(this, input, startIndex), d.INSTANCE);
        }
        throw new IndexOutOfBoundsException("Start index out of bounds: " + startIndex + ", input length: " + input.length());
    }

    /* compiled from: Regex.kt */
    public static final class c extends l implements kotlin.jvm.functions.a<h> {
        final /* synthetic */ CharSequence $input;
        final /* synthetic */ int $startIndex;
        final /* synthetic */ j this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(j jVar, CharSequence charSequence, int i) {
            super(0);
            this.this$0 = jVar;
            this.$input = charSequence;
            this.$startIndex = i;
        }

        @Nullable
        public final h invoke() {
            return this.this$0.find(this.$input, this.$startIndex);
        }
    }

    /* compiled from: Regex.kt */
    public static final /* synthetic */ class d extends i implements kotlin.jvm.functions.l<h, h> {
        public static final d INSTANCE = new d();

        d() {
            super(1, h.class, "next", "next()Lkotlin/text/MatchResult;", 0);
        }

        @Nullable
        public final h invoke(@NotNull h p1) {
            k.e(p1, "p1");
            return p1.next();
        }
    }

    @Nullable
    public final h matchEntire(@NotNull CharSequence input) {
        k.e(input, "input");
        Matcher matcher = this.nativePattern.matcher(input);
        k.d(matcher, "nativePattern.matcher(input)");
        return l.g(matcher, input);
    }

    @NotNull
    public final String replace(@NotNull CharSequence input, @NotNull String replacement) {
        k.e(input, "input");
        k.e(replacement, "replacement");
        String replaceAll = this.nativePattern.matcher(input).replaceAll(replacement);
        k.d(replaceAll, "nativePattern.matcher(in…).replaceAll(replacement)");
        return replaceAll;
    }

    @NotNull
    public final String replace(@NotNull CharSequence input, @NotNull kotlin.jvm.functions.l<? super h, ? extends CharSequence> transform) {
        k.e(input, "input");
        k.e(transform, "transform");
        h match = find$default(this, input, 0, 2, (Object) null);
        if (match == null) {
            return input.toString();
        }
        int lastStart = 0;
        int length = input.length();
        StringBuilder sb = new StringBuilder(length);
        do {
            k.c(match);
            h foundMatch = match;
            sb.append(input, lastStart, foundMatch.c().getStart().intValue());
            sb.append((CharSequence) transform.invoke(foundMatch));
            lastStart = foundMatch.c().getEndInclusive().intValue() + 1;
            match = foundMatch.next();
            if (lastStart >= length) {
                break;
            }
        } while (match != null);
        if (lastStart < length) {
            sb.append(input, lastStart, length);
        }
        String sb2 = sb.toString();
        k.d(sb2, "sb.toString()");
        return sb2;
    }

    @NotNull
    public final String replaceFirst(@NotNull CharSequence input, @NotNull String replacement) {
        k.e(input, "input");
        k.e(replacement, "replacement");
        String replaceFirst = this.nativePattern.matcher(input).replaceFirst(replacement);
        k.d(replaceFirst, "nativePattern.matcher(in…replaceFirst(replacement)");
        return replaceFirst;
    }

    public static /* synthetic */ List split$default(j jVar, CharSequence charSequence, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = 0;
        }
        return jVar.split(charSequence, i);
    }

    @NotNull
    public final List<String> split(@NotNull CharSequence input, int limit) {
        k.e(input, "input");
        if (limit >= 0) {
            Matcher matcher = this.nativePattern.matcher(input);
            if (!matcher.find() || limit == 1) {
                return p.b(input.toString());
            }
            int i = 10;
            if (limit > 0) {
                i = n.e(limit, 10);
            }
            ArrayList result = new ArrayList(i);
            int lastStart = 0;
            int lastSplit = limit - 1;
            do {
                result.add(input.subSequence(lastStart, matcher.start()).toString());
                lastStart = matcher.end();
                if ((lastSplit >= 0 && result.size() == lastSplit) || !matcher.find()) {
                    result.add(input.subSequence(lastStart, input.length()).toString());
                }
                result.add(input.subSequence(lastStart, matcher.start()).toString());
                lastStart = matcher.end();
                break;
            } while (!matcher.find());
            result.add(input.subSequence(lastStart, input.length()).toString());
            return result;
        }
        throw new IllegalArgumentException(("Limit must be non-negative, but was " + limit + '.').toString());
    }

    @NotNull
    public String toString() {
        String pattern = this.nativePattern.toString();
        k.d(pattern, "nativePattern.toString()");
        return pattern;
    }

    @NotNull
    public final Pattern toPattern() {
        return this.nativePattern;
    }

    private final Object writeReplace() {
        String pattern = this.nativePattern.pattern();
        k.d(pattern, "nativePattern.pattern()");
        return new b(pattern, this.nativePattern.flags());
    }

    /* compiled from: Regex.kt */
    public static final class b implements Serializable {
        @NotNull
        public static final a Companion = new a((DefaultConstructorMarker) null);
        private static final long serialVersionUID = 0;
        private final int flags;
        @NotNull
        private final String pattern;

        /* compiled from: Regex.kt */
        public static final class a {
            private a() {
            }

            public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
                this();
            }
        }

        public b(@NotNull String pattern2, int flags2) {
            k.e(pattern2, "pattern");
            this.pattern = pattern2;
            this.flags = flags2;
        }

        public final int getFlags() {
            return this.flags;
        }

        @NotNull
        public final String getPattern() {
            return this.pattern;
        }

        private final Object readResolve() {
            Pattern compile = Pattern.compile(this.pattern, this.flags);
            k.d(compile, "Pattern.compile(pattern, flags)");
            return new j(compile);
        }
    }

    /* compiled from: Regex.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        /* access modifiers changed from: private */
        public final int b(int flags) {
            return (flags & 2) != 0 ? flags | 64 : flags;
        }
    }
}
