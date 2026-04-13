package okhttp3;

import io.netty.util.internal.StringUtil;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.TypeCastException;
import kotlin.collections.l;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.ranges.g;
import kotlin.ranges.n;
import kotlin.text.w;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: MediaType.kt */
public final class x {
    /* access modifiers changed from: private */
    public static final Pattern a = Pattern.compile("([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)/([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)");
    /* access modifiers changed from: private */
    public static final Pattern b = Pattern.compile(";\\s*(?:([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)=(?:([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)|\"([^\"]*)\"))?");
    public static final a c = new a((DefaultConstructorMarker) null);
    private final String d;
    @NotNull
    private final String e;
    @NotNull
    private final String f;
    private final String[] g;

    @NotNull
    public static final x f(@NotNull String str) {
        return c.a(str);
    }

    @Nullable
    public static final x h(@NotNull String str) {
        return c.b(str);
    }

    @Nullable
    public final Charset c() {
        return e(this, (Charset) null, 1, (Object) null);
    }

    private x(String mediaType, String type, String subtype, String[] parameterNamesAndValues) {
        this.d = mediaType;
        this.e = type;
        this.f = subtype;
        this.g = parameterNamesAndValues;
    }

    public /* synthetic */ x(String mediaType, String type, String subtype, String[] parameterNamesAndValues, DefaultConstructorMarker $constructor_marker) {
        this(mediaType, type, subtype, parameterNamesAndValues);
    }

    @NotNull
    public final String j() {
        return this.e;
    }

    @NotNull
    public final String i() {
        return this.f;
    }

    public static /* synthetic */ Charset e(x xVar, Charset charset, int i, Object obj) {
        if ((i & 1) != 0) {
            charset = null;
        }
        return xVar.d(charset);
    }

    @Nullable
    public final Charset d(@Nullable Charset defaultValue) {
        String charset = g("charset");
        if (charset == null) {
            return defaultValue;
        }
        try {
            return Charset.forName(charset);
        } catch (IllegalArgumentException e2) {
            return defaultValue;
        }
    }

    @Nullable
    public final String g(@NotNull String name) {
        k.f(name, "name");
        g k = n.k(l.w(this.g), 2);
        int i = k.a();
        int b2 = k.b();
        int e2 = k.e();
        if (e2 >= 0) {
            if (i > b2) {
                return null;
            }
        } else if (i < b2) {
            return null;
        }
        while (!w.y(this.g[i], name, true)) {
            if (i == b2) {
                return null;
            }
            i += e2;
        }
        return this.g[i + 1];
    }

    @NotNull
    public String toString() {
        return this.d;
    }

    public boolean equals(@Nullable Object other) {
        return (other instanceof x) && k.a(((x) other).d, this.d);
    }

    public int hashCode() {
        return this.d.hashCode();
    }

    /* compiled from: MediaType.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final x a(@NotNull String $this$toMediaType) {
            String value;
            String str = $this$toMediaType;
            k.f(str, "$this$toMediaType");
            Matcher typeSubtype = x.a.matcher(str);
            if (typeSubtype.lookingAt()) {
                String group = typeSubtype.group(1);
                k.b(group, "typeSubtype.group(1)");
                Locale locale = Locale.US;
                k.b(locale, "Locale.US");
                if (group != null) {
                    String type = group.toLowerCase(locale);
                    k.b(type, "(this as java.lang.String).toLowerCase(locale)");
                    String group2 = typeSubtype.group(2);
                    k.b(group2, "typeSubtype.group(2)");
                    k.b(locale, "Locale.US");
                    if (group2 != null) {
                        String subtype = group2.toLowerCase(locale);
                        k.b(subtype, "(this as java.lang.String).toLowerCase(locale)");
                        Collection parameterNamesAndValues = new ArrayList();
                        Matcher parameter = x.b.matcher(str);
                        int s = typeSubtype.end();
                        while (s < $this$toMediaType.length()) {
                            parameter.region(s, $this$toMediaType.length());
                            if (parameter.lookingAt()) {
                                String name = parameter.group(1);
                                if (name == null) {
                                    s = parameter.end();
                                } else {
                                    String token = parameter.group(2);
                                    if (token == null) {
                                        value = parameter.group(3);
                                    } else if (!w.N(token, "'", false, 2, (Object) null) || !w.x(token, "'", false, 2, (Object) null) || token.length() <= 2) {
                                        value = token;
                                    } else {
                                        value = token.substring(1, token.length() - 1);
                                        k.b(value, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                                    }
                                    parameterNamesAndValues.add(name);
                                    parameterNamesAndValues.add(value);
                                    s = parameter.end();
                                }
                            } else {
                                StringBuilder sb = new StringBuilder();
                                sb.append("Parameter is not formatted correctly: \"");
                                String substring = str.substring(s);
                                k.b(substring, "(this as java.lang.String).substring(startIndex)");
                                sb.append(substring);
                                sb.append("\" for: \"");
                                sb.append(str);
                                sb.append(StringUtil.DOUBLE_QUOTE);
                                throw new IllegalArgumentException(sb.toString().toString());
                            }
                        }
                        Object[] array = parameterNamesAndValues.toArray(new String[0]);
                        if (array != null) {
                            return new x($this$toMediaType, type, subtype, (String[]) array, (DefaultConstructorMarker) null);
                        }
                        throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
                    }
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
            throw new IllegalArgumentException(("No subtype found for: \"" + str + StringUtil.DOUBLE_QUOTE).toString());
        }

        @Nullable
        public final x b(@NotNull String $this$toMediaTypeOrNull) {
            k.f($this$toMediaTypeOrNull, "$this$toMediaTypeOrNull");
            try {
                return a($this$toMediaTypeOrNull);
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
    }
}
