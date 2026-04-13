package com.caverock.androidsvg;

import android.util.Log;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.caverock.androidsvg.CSSParser;
import com.caverock.androidsvg.g;
import com.caverock.androidsvg.j;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/* compiled from: CSSParser */
public class a {
    private f a;
    private u b;
    private boolean c;

    /* compiled from: CSSParser */
    public enum c {
        EXISTS,
        EQUALS,
        INCLUDES,
        DASHMATCH
    }

    /* compiled from: CSSParser */
    public enum e {
        DESCENDANT,
        CHILD,
        FOLLOWS
    }

    /* compiled from: CSSParser */
    public enum f {
        all,
        aural,
        braille,
        embossed,
        handheld,
        print,
        projection,
        screen,
        speech,
        tty,
        tv
    }

    /* compiled from: CSSParser */
    public interface g {
        boolean a(q qVar, g.l0 l0Var);
    }

    /* compiled from: CSSParser */
    public enum u {
        Document,
        RenderOptions
    }

    /* compiled from: CSSParser */
    public enum j {
        target,
        root,
        nth_child,
        nth_last_child,
        nth_of_type,
        nth_last_of_type,
        first_child,
        last_child,
        first_of_type,
        last_of_type,
        only_child,
        only_of_type,
        empty,
        not,
        lang,
        link,
        visited,
        hover,
        active,
        focus,
        enabled,
        disabled,
        checked,
        indeterminate,
        UNSUPPORTED;
        
        private static final Map<String, j> c = null;

        static {
            c = new HashMap();
            for (j attr : values()) {
                if (attr != UNSUPPORTED) {
                    c.put(attr.name().replace('_', '-'), attr);
                }
            }
        }

        public static j fromString(String str) {
            j attr = c.get(str);
            if (attr != null) {
                return attr;
            }
            return UNSUPPORTED;
        }
    }

    /* compiled from: CSSParser */
    public static class b {
        public final String a;
        final c b;
        public final String c;

        b(String name, c op, String value) {
            this.a = name;
            this.b = op;
            this.c = value;
        }
    }

    /* compiled from: CSSParser */
    public static class t {
        e a = null;
        String b = null;
        List<b> c = null;
        List<g> d = null;

        t(e combinator, String tag) {
            this.a = combinator != null ? combinator : e.DESCENDANT;
            this.b = tag;
        }

        /* access modifiers changed from: package-private */
        public void a(String attrName, c op, String attrValue) {
            if (this.c == null) {
                this.c = new ArrayList();
            }
            this.c.add(new b(attrName, op, attrValue));
        }

        /* access modifiers changed from: package-private */
        public void b(g pseudo) {
            if (this.d == null) {
                this.d = new ArrayList();
            }
            this.d.add(pseudo);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            e eVar = this.a;
            if (eVar == e.CHILD) {
                sb.append("> ");
            } else if (eVar == e.FOLLOWS) {
                sb.append("+ ");
            }
            String str = this.b;
            if (str == null) {
                str = org.slf4j.e.ANY_MARKER;
            }
            sb.append(str);
            List<b> list = this.c;
            if (list != null) {
                for (b attr : list) {
                    sb.append('[');
                    sb.append(attr.a);
                    switch (C0049a.a[attr.b.ordinal()]) {
                        case 1:
                            sb.append('=');
                            sb.append(attr.c);
                            break;
                        case 2:
                            sb.append("~=");
                            sb.append(attr.c);
                            break;
                        case 3:
                            sb.append("|=");
                            sb.append(attr.c);
                            break;
                    }
                    sb.append(']');
                }
            }
            List<g> list2 = this.d;
            if (list2 != null) {
                for (g pseu : list2) {
                    sb.append(':');
                    sb.append(pseu);
                }
            }
            return sb.toString();
        }
    }

    /* compiled from: CSSParser */
    public static class r {
        private List<p> a = null;

        r() {
        }

        /* access modifiers changed from: package-private */
        public void a(p rule) {
            if (this.a == null) {
                this.a = new ArrayList();
            }
            for (int i = 0; i < this.a.size(); i++) {
                if (this.a.get(i).a.b > rule.a.b) {
                    this.a.add(i, rule);
                    return;
                }
            }
            this.a.add(rule);
        }

        /* access modifiers changed from: package-private */
        public void b(r rules) {
            if (rules.a != null) {
                if (this.a == null) {
                    this.a = new ArrayList(rules.a.size());
                }
                for (p rule : rules.a) {
                    a(rule);
                }
            }
        }

        /* access modifiers changed from: package-private */
        public List<p> c() {
            return this.a;
        }

        /* access modifiers changed from: package-private */
        public boolean d() {
            List<p> list = this.a;
            return list == null || list.isEmpty();
        }

        /* access modifiers changed from: package-private */
        public int f() {
            List<p> list = this.a;
            if (list != null) {
                return list.size();
            }
            return 0;
        }

        /* access modifiers changed from: package-private */
        public void e(u sourceToBeRemoved) {
            List<p> list = this.a;
            if (list != null) {
                Iterator<p> it = list.iterator();
                while (it.hasNext()) {
                    if (it.next().c == sourceToBeRemoved) {
                        it.remove();
                    }
                }
            }
        }

        public String toString() {
            if (this.a == null) {
                return "";
            }
            StringBuilder sb = new StringBuilder();
            for (p rule : this.a) {
                sb.append(rule.toString());
                sb.append(10);
            }
            return sb.toString();
        }
    }

    /* compiled from: CSSParser */
    public static class p {
        s a = null;
        g.e0 b = null;
        u c;

        p(s selector, g.e0 style, u source) {
            this.a = selector;
            this.b = style;
            this.c = source;
        }

        public String toString() {
            return String.valueOf(this.a) + " {...} (src=" + this.c + ")";
        }
    }

    /* compiled from: CSSParser */
    public static class s {
        List<t> a;
        int b;

        private s() {
            this.a = null;
            this.b = 0;
        }

        /* synthetic */ s(C0049a x0) {
            this();
        }

        /* access modifiers changed from: package-private */
        public void a(t part) {
            if (this.a == null) {
                this.a = new ArrayList();
            }
            this.a.add(part);
        }

        /* access modifiers changed from: package-private */
        public int g() {
            List<t> list = this.a;
            if (list == null) {
                return 0;
            }
            return list.size();
        }

        /* access modifiers changed from: package-private */
        public t e(int i) {
            return this.a.get(i);
        }

        /* access modifiers changed from: package-private */
        public boolean f() {
            List<t> list = this.a;
            return list == null || list.isEmpty();
        }

        /* access modifiers changed from: package-private */
        public void d() {
            this.b += 1000000;
        }

        /* access modifiers changed from: package-private */
        public void b() {
            this.b += 1000;
        }

        /* access modifiers changed from: package-private */
        public void c() {
            this.b++;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (t sel : this.a) {
                sb.append(sel);
                sb.append(' ');
            }
            sb.append('[');
            sb.append(this.b);
            sb.append(']');
            return sb.toString();
        }
    }

    a(u source) {
        this(f.screen, source);
    }

    a(f rendererMediaType, u source) {
        this.a = null;
        this.b = null;
        this.c = false;
        this.a = rendererMediaType;
        this.b = source;
    }

    /* access modifiers changed from: package-private */
    public r d(String sheet) {
        d scan = new d(sheet);
        scan.A();
        return j(scan);
    }

    static boolean b(String mediaListStr, f rendererMediaType) {
        d scan = new d(mediaListStr);
        scan.A();
        return c(h(scan), rendererMediaType);
    }

    private static void p(String format, Object... args) {
        Log.w("CSSParser", String.format(format, args));
    }

    /* compiled from: CSSParser */
    public static class d extends j.i {
        d(String input) {
            super(input.replaceAll("(?s)/\\*.*?\\*/", ""));
        }

        /* access modifiers changed from: package-private */
        public String H() {
            int end = P();
            int i = this.b;
            if (end == i) {
                return null;
            }
            String result = this.a.substring(i, end);
            this.b = end;
            return result;
        }

        private int P() {
            if (h()) {
                return this.b;
            }
            int start = this.b;
            int lastValidPos = this.b;
            int ch = this.a.charAt(this.b);
            if (ch == 45) {
                ch = a();
            }
            if ((ch >= 65 && ch <= 90) || ((ch >= 97 && ch <= 122) || ch == 95)) {
                int ch2 = a();
                while (true) {
                    if ((ch2 < 65 || ch2 > 90) && ((ch2 < 97 || ch2 > 122) && !((ch2 >= 48 && ch2 <= 57) || ch2 == 45 || ch2 == 95))) {
                        break;
                    }
                    ch2 = a();
                }
                lastValidPos = this.b;
            }
            this.b = start;
            return lastValidPos;
        }

        /* access modifiers changed from: private */
        public List<s> L() {
            if (h()) {
                return null;
            }
            ArrayList<CSSParser.Selector> selectorGroup = new ArrayList<>(1);
            s selector = new s((C0049a) null);
            while (!h() && M(selector)) {
                if (z()) {
                    selectorGroup.add(selector);
                    selector = new s((C0049a) null);
                }
            }
            if (!selector.f()) {
                selectorGroup.add(selector);
            }
            return selectorGroup;
        }

        /* access modifiers changed from: package-private */
        public boolean M(s selector) {
            if (h()) {
                return false;
            }
            int start = this.b;
            e combinator = null;
            t selectorPart = null;
            if (!selector.f()) {
                if (f('>')) {
                    combinator = e.CHILD;
                    A();
                } else if (f('+')) {
                    combinator = e.FOLLOWS;
                    A();
                }
            }
            if (f('*')) {
                selectorPart = new t(combinator, (String) null);
            } else {
                String tag = H();
                if (tag != null) {
                    selectorPart = new t(combinator, tag);
                    selector.c();
                }
            }
            while (!h()) {
                if (!f('.')) {
                    if (!f('#')) {
                        if (!f('[')) {
                            if (!f(':')) {
                                break;
                            }
                            if (selectorPart == null) {
                                selectorPart = new t(combinator, (String) null);
                            }
                            O(selector, selectorPart);
                        } else {
                            if (selectorPart == null) {
                                selectorPart = new t(combinator, (String) null);
                            }
                            A();
                            String attrName = H();
                            String attrValue = null;
                            if (attrName != null) {
                                A();
                                c op = null;
                                if (f('=')) {
                                    op = c.EQUALS;
                                } else if (g("~=")) {
                                    op = c.INCLUDES;
                                } else if (g("|=")) {
                                    op = c.DASHMATCH;
                                }
                                if (op != null) {
                                    A();
                                    attrValue = E();
                                    if (attrValue != null) {
                                        A();
                                    } else {
                                        throw new CSSParseException("Invalid attribute simpleSelectors");
                                    }
                                }
                                if (f(']')) {
                                    selectorPart.a(attrName, op == null ? c.EXISTS : op, attrValue);
                                    selector.b();
                                } else {
                                    throw new CSSParseException("Invalid attribute simpleSelectors");
                                }
                            } else {
                                throw new CSSParseException("Invalid attribute simpleSelectors");
                            }
                        }
                    } else {
                        if (selectorPart == null) {
                            selectorPart = new t(combinator, (String) null);
                        }
                        String value = H();
                        if (value != null) {
                            selectorPart.a("id", c.EQUALS, value);
                            selector.d();
                        } else {
                            throw new CSSParseException("Invalid \"#id\" simpleSelectors");
                        }
                    }
                } else {
                    if (selectorPart == null) {
                        selectorPart = new t(combinator, (String) null);
                    }
                    String value2 = H();
                    if (value2 != null) {
                        selectorPart.a("class", c.EQUALS, value2);
                        selector.b();
                    } else {
                        throw new CSSParseException("Invalid \".class\" simpleSelectors");
                    }
                }
            }
            if (selectorPart != null) {
                selector.a(selectorPart);
                return true;
            }
            this.b = start;
            return false;
        }

        /* renamed from: com.caverock.androidsvg.a$d$a  reason: collision with other inner class name */
        /* compiled from: CSSParser */
        public static class C0050a {
            public int a;
            public int b;

            C0050a(int a2, int b2) {
                this.a = a2;
                this.b = b2;
            }
        }

        private C0050a D() {
            C0050a result;
            int bSign;
            if (h()) {
                return null;
            }
            int start = this.b;
            if (!f('(')) {
                return null;
            }
            A();
            if (g("odd")) {
                result = new C0050a(2, 1);
            } else {
                int i = 0;
                if (g("even")) {
                    result = new C0050a(2, 0);
                } else {
                    int aSign = 1;
                    int bSign2 = 1;
                    if (!f('+') && f('-')) {
                        bSign2 = -1;
                    }
                    c a = null;
                    c b = c.c(this.a, this.b, this.c, false);
                    if (b != null) {
                        this.b = b.a();
                    }
                    if (f('n') || f('N')) {
                        a = b != null ? b : new c(1, this.b);
                        aSign = bSign;
                        b = null;
                        bSign = 1;
                        A();
                        boolean hasB = f('+');
                        if (!hasB && (hasB = f('-'))) {
                            bSign = -1;
                        }
                        if (hasB) {
                            A();
                            b = c.c(this.a, this.b, this.c, false);
                            if (b != null) {
                                this.b = b.a();
                            } else {
                                this.b = start;
                                return null;
                            }
                        }
                    }
                    int d = a == null ? 0 : a.d() * aSign;
                    if (b != null) {
                        i = b.d() * bSign;
                    }
                    result = new C0050a(d, i);
                }
            }
            A();
            if (f(')')) {
                return result;
            }
            this.b = start;
            return null;
        }

        private List<String> G() {
            if (h()) {
                return null;
            }
            int start = this.b;
            ArrayList<String> result = null;
            if (!f('(')) {
                return null;
            }
            A();
            do {
                String ident = H();
                if (ident == null) {
                    this.b = start;
                    return null;
                }
                if (result == null) {
                    result = new ArrayList<>();
                }
                result.add(ident);
                A();
            } while (z());
            if (f(')')) {
                return result;
            }
            this.b = start;
            return null;
        }

        private List<s> K() {
            List<t> list;
            List<g> list2;
            if (h()) {
                return null;
            }
            int start = this.b;
            if (!f('(')) {
                return null;
            }
            A();
            List<s> L = L();
            if (L == null) {
                this.b = start;
                return null;
            } else if (!f(')')) {
                this.b = start;
                return null;
            } else {
                Iterator<s> it = L.iterator();
                while (it.hasNext() && (list = it.next().a) != null) {
                    Iterator<t> it2 = list.iterator();
                    while (it2.hasNext() && (list2 = it2.next().d) != null) {
                        Iterator<g> it3 = list2.iterator();
                        while (true) {
                            if (it3.hasNext()) {
                                if (it3.next() instanceof k) {
                                    return null;
                                }
                            }
                        }
                    }
                }
                return L;
            }
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v2, resolved type: com.caverock.androidsvg.a$k} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v2, resolved type: com.caverock.androidsvg.a$h} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v3, resolved type: com.caverock.androidsvg.a$k} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v3, resolved type: com.caverock.androidsvg.a$h} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v4, resolved type: com.caverock.androidsvg.a$k} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v4, resolved type: com.caverock.androidsvg.a$m} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v5, resolved type: com.caverock.androidsvg.a$k} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v5, resolved type: com.caverock.androidsvg.a$h} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v6, resolved type: com.caverock.androidsvg.a$k} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v6, resolved type: com.caverock.androidsvg.a$h} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v7, resolved type: com.caverock.androidsvg.a$k} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v7, resolved type: com.caverock.androidsvg.a$m} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v8, resolved type: com.caverock.androidsvg.a$k} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v8, resolved type: com.caverock.androidsvg.a$n} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v9, resolved type: com.caverock.androidsvg.a$k} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v9, resolved type: com.caverock.androidsvg.a$i} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v10, resolved type: com.caverock.androidsvg.a$k} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v5, resolved type: com.caverock.androidsvg.a$h} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v11, resolved type: com.caverock.androidsvg.a$k} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v12, resolved type: com.caverock.androidsvg.a$k} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v16, resolved type: com.caverock.androidsvg.a$o} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v13, resolved type: com.caverock.androidsvg.a$k} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v12, resolved type: com.caverock.androidsvg.a$l} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v14, resolved type: com.caverock.androidsvg.a$k} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v18, resolved type: com.caverock.androidsvg.a$l} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v15, resolved type: com.caverock.androidsvg.a$k} */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private void O(com.caverock.androidsvg.a.s r14, com.caverock.androidsvg.a.t r15) {
            /*
                r13 = this;
                java.lang.String r0 = r13.H()
                if (r0 == 0) goto L_0x0136
                r1 = 0
                com.caverock.androidsvg.a$j r2 = com.caverock.androidsvg.a.j.fromString(r0)
                int[] r3 = com.caverock.androidsvg.a.C0049a.b
                int r4 = r2.ordinal()
                r3 = r3[r4]
                java.lang.String r4 = "Invalid or missing parameter section for pseudo class: "
                r5 = 1
                r6 = 0
                r7 = 0
                switch(r3) {
                    case 1: goto L_0x0122;
                    case 2: goto L_0x0112;
                    case 3: goto L_0x0108;
                    case 4: goto L_0x00f7;
                    case 5: goto L_0x00e6;
                    case 6: goto L_0x00da;
                    case 7: goto L_0x00d0;
                    case 8: goto L_0x00c5;
                    case 9: goto L_0x0080;
                    case 10: goto L_0x0080;
                    case 11: goto L_0x0080;
                    case 12: goto L_0x0080;
                    case 13: goto L_0x0057;
                    case 14: goto L_0x004c;
                    case 15: goto L_0x003d;
                    case 16: goto L_0x0032;
                    case 17: goto L_0x0032;
                    case 18: goto L_0x0032;
                    case 19: goto L_0x0032;
                    case 20: goto L_0x0032;
                    case 21: goto L_0x0032;
                    case 22: goto L_0x0032;
                    case 23: goto L_0x0032;
                    case 24: goto L_0x0032;
                    default: goto L_0x001b;
                }
            L_0x001b:
                com.caverock.androidsvg.CSSParseException r3 = new com.caverock.androidsvg.CSSParseException
                java.lang.StringBuilder r4 = new java.lang.StringBuilder
                r4.<init>()
                java.lang.String r5 = "Unsupported pseudo class: "
                r4.append(r5)
                r4.append(r0)
                java.lang.String r4 = r4.toString()
                r3.<init>(r4)
                throw r3
            L_0x0032:
                com.caverock.androidsvg.a$l r3 = new com.caverock.androidsvg.a$l
                r3.<init>(r0)
                r1 = r3
                r14.b()
                goto L_0x0132
            L_0x003d:
                java.util.List r3 = r13.G()
                com.caverock.androidsvg.a$l r4 = new com.caverock.androidsvg.a$l
                r4.<init>(r0)
                r1 = r4
                r14.b()
                goto L_0x0132
            L_0x004c:
                com.caverock.androidsvg.a$o r3 = new com.caverock.androidsvg.a$o
                r3.<init>(r7)
                r1 = r3
                r14.b()
                goto L_0x0132
            L_0x0057:
                java.util.List r3 = r13.K()
                if (r3 == 0) goto L_0x006b
                com.caverock.androidsvg.a$k r4 = new com.caverock.androidsvg.a$k
                r4.<init>(r3)
                r1 = r4
                int r4 = r1.b()
                r14.b = r4
                goto L_0x0132
            L_0x006b:
                com.caverock.androidsvg.CSSParseException r5 = new com.caverock.androidsvg.CSSParseException
                java.lang.StringBuilder r6 = new java.lang.StringBuilder
                r6.<init>()
                r6.append(r4)
                r6.append(r0)
                java.lang.String r4 = r6.toString()
                r5.<init>(r4)
                throw r5
            L_0x0080:
                com.caverock.androidsvg.a$j r3 = com.caverock.androidsvg.a.j.nth_child
                if (r2 == r3) goto L_0x008b
                com.caverock.androidsvg.a$j r3 = com.caverock.androidsvg.a.j.nth_of_type
                if (r2 != r3) goto L_0x0089
                goto L_0x008b
            L_0x0089:
                r10 = r6
                goto L_0x008c
            L_0x008b:
                r10 = r5
            L_0x008c:
                com.caverock.androidsvg.a$j r3 = com.caverock.androidsvg.a.j.nth_of_type
                if (r2 == r3) goto L_0x0097
                com.caverock.androidsvg.a$j r3 = com.caverock.androidsvg.a.j.nth_last_of_type
                if (r2 != r3) goto L_0x0095
                goto L_0x0097
            L_0x0095:
                r11 = r6
                goto L_0x0098
            L_0x0097:
                r11 = r5
            L_0x0098:
                com.caverock.androidsvg.a$d$a r3 = r13.D()
                if (r3 == 0) goto L_0x00b0
                com.caverock.androidsvg.a$h r4 = new com.caverock.androidsvg.a$h
                int r8 = r3.a
                int r9 = r3.b
                java.lang.String r12 = r15.b
                r7 = r4
                r7.<init>(r8, r9, r10, r11, r12)
                r1 = r4
                r14.b()
                goto L_0x0132
            L_0x00b0:
                com.caverock.androidsvg.CSSParseException r5 = new com.caverock.androidsvg.CSSParseException
                java.lang.StringBuilder r6 = new java.lang.StringBuilder
                r6.<init>()
                r6.append(r4)
                r6.append(r0)
                java.lang.String r4 = r6.toString()
                r5.<init>(r4)
                throw r5
            L_0x00c5:
                com.caverock.androidsvg.a$i r3 = new com.caverock.androidsvg.a$i
                r3.<init>(r7)
                r1 = r3
                r14.b()
                goto L_0x0132
            L_0x00d0:
                com.caverock.androidsvg.a$n r3 = new com.caverock.androidsvg.a$n
                r3.<init>(r7)
                r1 = r3
                r14.b()
                goto L_0x0132
            L_0x00da:
                com.caverock.androidsvg.a$m r3 = new com.caverock.androidsvg.a$m
                java.lang.String r4 = r15.b
                r3.<init>(r5, r4)
                r1 = r3
                r14.b()
                goto L_0x0132
            L_0x00e6:
                com.caverock.androidsvg.a$h r9 = new com.caverock.androidsvg.a$h
                r4 = 0
                r5 = 1
                r6 = 0
                r7 = 1
                java.lang.String r8 = r15.b
                r3 = r9
                r3.<init>(r4, r5, r6, r7, r8)
                r1 = r9
                r14.b()
                goto L_0x0132
            L_0x00f7:
                com.caverock.androidsvg.a$h r9 = new com.caverock.androidsvg.a$h
                r4 = 0
                r5 = 1
                r6 = 1
                r7 = 1
                java.lang.String r8 = r15.b
                r3 = r9
                r3.<init>(r4, r5, r6, r7, r8)
                r1 = r9
                r14.b()
                goto L_0x0132
            L_0x0108:
                com.caverock.androidsvg.a$m r3 = new com.caverock.androidsvg.a$m
                r3.<init>(r6, r7)
                r1 = r3
                r14.b()
                goto L_0x0132
            L_0x0112:
                com.caverock.androidsvg.a$h r9 = new com.caverock.androidsvg.a$h
                r4 = 0
                r5 = 1
                r6 = 0
                r7 = 0
                r8 = 0
                r3 = r9
                r3.<init>(r4, r5, r6, r7, r8)
                r1 = r9
                r14.b()
                goto L_0x0132
            L_0x0122:
                com.caverock.androidsvg.a$h r9 = new com.caverock.androidsvg.a$h
                r4 = 0
                r5 = 1
                r6 = 1
                r7 = 0
                r8 = 0
                r3 = r9
                r3.<init>(r4, r5, r6, r7, r8)
                r1 = r9
                r14.b()
            L_0x0132:
                r15.b(r1)
                return
            L_0x0136:
                com.caverock.androidsvg.CSSParseException r1 = new com.caverock.androidsvg.CSSParseException
                java.lang.String r2 = "Invalid pseudo class"
                r1.<init>(r2)
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.caverock.androidsvg.a.d.O(com.caverock.androidsvg.a$s, com.caverock.androidsvg.a$t):void");
        }

        private String E() {
            if (h()) {
                return null;
            }
            String result = q();
            if (result != null) {
                return result;
            }
            return H();
        }

        /* access modifiers changed from: package-private */
        public String J() {
            if (h()) {
                return null;
            }
            int start = this.b;
            int lastValidPos = this.b;
            int ch = this.a.charAt(this.b);
            while (ch != -1 && ch != 59 && ch != 125 && ch != 33 && !j(ch)) {
                if (!k(ch)) {
                    lastValidPos = this.b + 1;
                }
                ch = a();
            }
            if (this.b > start) {
                return this.a.substring(start, lastValidPos);
            }
            this.b = start;
            return null;
        }

        /* access modifiers changed from: package-private */
        /* JADX WARNING: Code restructure failed: missing block: B:27:0x0059, code lost:
            r0 = l().intValue();
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.lang.String F() {
            /*
                r8 = this;
                boolean r0 = r8.h()
                r1 = 0
                if (r0 == 0) goto L_0x0008
                return r1
            L_0x0008:
                java.lang.String r0 = r8.a
                int r2 = r8.b
                char r0 = r0.charAt(r2)
                r2 = r0
                r3 = 39
                if (r0 == r3) goto L_0x001a
                r3 = 34
                if (r0 == r3) goto L_0x001a
                return r1
            L_0x001a:
                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                r1.<init>()
                int r3 = r8.b
                int r3 = r3 + 1
                r8.b = r3
                java.lang.Integer r3 = r8.l()
                int r0 = r3.intValue()
            L_0x002d:
                r3 = -1
                if (r0 == r3) goto L_0x008a
                if (r0 == r2) goto L_0x008a
                r4 = 92
                if (r0 != r4) goto L_0x007d
                java.lang.Integer r4 = r8.l()
                int r0 = r4.intValue()
                if (r0 != r3) goto L_0x0041
                goto L_0x002d
            L_0x0041:
                r4 = 10
                if (r0 == r4) goto L_0x0074
                r4 = 13
                if (r0 == r4) goto L_0x0074
                r4 = 12
                if (r0 != r4) goto L_0x004e
                goto L_0x0074
            L_0x004e:
                int r4 = r8.C(r0)
                if (r4 == r3) goto L_0x007d
                r5 = r4
                r6 = 1
            L_0x0056:
                r7 = 5
                if (r6 > r7) goto L_0x006f
                java.lang.Integer r7 = r8.l()
                int r0 = r7.intValue()
                int r4 = r8.C(r0)
                if (r4 != r3) goto L_0x0068
                goto L_0x006f
            L_0x0068:
                int r7 = r5 * 16
                int r5 = r7 + r4
                int r6 = r6 + 1
                goto L_0x0056
            L_0x006f:
                char r3 = (char) r5
                r1.append(r3)
                goto L_0x002d
            L_0x0074:
                java.lang.Integer r3 = r8.l()
                int r0 = r3.intValue()
                goto L_0x002d
            L_0x007d:
                char r3 = (char) r0
                r1.append(r3)
                java.lang.Integer r3 = r8.l()
                int r0 = r3.intValue()
                goto L_0x002d
            L_0x008a:
                java.lang.String r3 = r1.toString()
                return r3
            */
            throw new UnsupportedOperationException("Method not decompiled: com.caverock.androidsvg.a.d.F():java.lang.String");
        }

        private int C(int ch) {
            if (ch >= 48 && ch <= 57) {
                return ch - 48;
            }
            if (ch >= 65 && ch <= 70) {
                return (ch - 65) + 10;
            }
            if (ch < 97 || ch > 102) {
                return -1;
            }
            return (ch - 97) + 10;
        }

        /* access modifiers changed from: package-private */
        public String N() {
            if (h()) {
                return null;
            }
            int start = this.b;
            if (!g("url(")) {
                return null;
            }
            A();
            String url = F();
            if (url == null) {
                url = I();
            }
            if (url == null) {
                this.b = start;
                return null;
            }
            A();
            if (h() || g(")")) {
                return url;
            }
            this.b = start;
            return null;
        }

        /* access modifiers changed from: package-private */
        public String I() {
            int ch;
            int hc;
            StringBuilder sb = new StringBuilder();
            while (!h() && (ch = this.a.charAt(this.b)) != 39 && ch != 34 && ch != 40 && ch != 41 && !k(ch) && !Character.isISOControl(ch)) {
                this.b++;
                if (ch == 92) {
                    if (!h()) {
                        String str = this.a;
                        int i = this.b;
                        this.b = i + 1;
                        ch = str.charAt(i);
                        if (!(ch == 10 || ch == 13 || ch == 12)) {
                            int hc2 = C(ch);
                            if (hc2 != -1) {
                                int codepoint = hc2;
                                for (int i2 = 1; i2 <= 5 && !h() && (hc = C(this.a.charAt(this.b))) != -1; i2++) {
                                    this.b++;
                                    codepoint = (codepoint * 16) + hc;
                                }
                                sb.append((char) codepoint);
                            }
                        }
                    }
                }
                sb.append((char) ch);
            }
            if (sb.length() == 0) {
                return null;
            }
            return sb.toString();
        }
    }

    /* renamed from: com.caverock.androidsvg.a$a  reason: collision with other inner class name */
    /* compiled from: CSSParser */
    public static /* synthetic */ class C0049a {
        static final /* synthetic */ int[] a;
        static final /* synthetic */ int[] b;

        static {
            int[] iArr = new int[j.values().length];
            b = iArr;
            try {
                iArr[j.first_child.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                b[j.last_child.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                b[j.only_child.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                b[j.first_of_type.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                b[j.last_of_type.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                b[j.only_of_type.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                b[j.root.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                b[j.empty.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                b[j.nth_child.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                b[j.nth_last_child.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
            try {
                b[j.nth_of_type.ordinal()] = 11;
            } catch (NoSuchFieldError e11) {
            }
            try {
                b[j.nth_last_of_type.ordinal()] = 12;
            } catch (NoSuchFieldError e12) {
            }
            try {
                b[j.not.ordinal()] = 13;
            } catch (NoSuchFieldError e13) {
            }
            try {
                b[j.target.ordinal()] = 14;
            } catch (NoSuchFieldError e14) {
            }
            try {
                b[j.lang.ordinal()] = 15;
            } catch (NoSuchFieldError e15) {
            }
            try {
                b[j.link.ordinal()] = 16;
            } catch (NoSuchFieldError e16) {
            }
            try {
                b[j.visited.ordinal()] = 17;
            } catch (NoSuchFieldError e17) {
            }
            try {
                b[j.hover.ordinal()] = 18;
            } catch (NoSuchFieldError e18) {
            }
            try {
                b[j.active.ordinal()] = 19;
            } catch (NoSuchFieldError e19) {
            }
            try {
                b[j.focus.ordinal()] = 20;
            } catch (NoSuchFieldError e20) {
            }
            try {
                b[j.enabled.ordinal()] = 21;
            } catch (NoSuchFieldError e21) {
            }
            try {
                b[j.disabled.ordinal()] = 22;
            } catch (NoSuchFieldError e22) {
            }
            try {
                b[j.checked.ordinal()] = 23;
            } catch (NoSuchFieldError e23) {
            }
            try {
                b[j.indeterminate.ordinal()] = 24;
            } catch (NoSuchFieldError e24) {
            }
            int[] iArr2 = new int[c.values().length];
            a = iArr2;
            try {
                iArr2[c.EQUALS.ordinal()] = 1;
            } catch (NoSuchFieldError e25) {
            }
            try {
                a[c.INCLUDES.ordinal()] = 2;
            } catch (NoSuchFieldError e26) {
            }
            try {
                a[c.DASHMATCH.ordinal()] = 3;
            } catch (NoSuchFieldError e27) {
            }
        }
    }

    private static boolean c(List<f> mediaList, f rendererMediaType) {
        for (f type : mediaList) {
            if (type == f.all) {
                return true;
            }
            if (type == rendererMediaType) {
                return true;
            }
        }
        return false;
    }

    private static List<f> h(d scan) {
        String type;
        ArrayList<CSSParser.MediaType> typeList = new ArrayList<>();
        while (!scan.h() && (type = scan.w()) != null) {
            try {
                typeList.add(f.valueOf(type));
            } catch (IllegalArgumentException e2) {
            }
            if (!scan.z()) {
                break;
            }
        }
        return typeList;
    }

    private void e(r ruleset, d scan) {
        String atKeyword = scan.H();
        scan.A();
        if (atKeyword != null) {
            if (!this.c && atKeyword.equals("media")) {
                List<f> h2 = h(scan);
                if (scan.f('{')) {
                    scan.A();
                    if (c(h2, this.a)) {
                        this.c = true;
                        ruleset.b(j(scan));
                        this.c = false;
                    } else {
                        j(scan);
                    }
                    if (!scan.h() && !scan.f('}')) {
                        throw new CSSParseException("Invalid @media rule: expected '}' at end of rule set");
                    }
                } else {
                    throw new CSSParseException("Invalid @media rule: missing rule set");
                }
            } else if (this.c || !atKeyword.equals("import")) {
                p("Ignoring @%s rule", atKeyword);
                o(scan);
            } else {
                String file = scan.N();
                if (file == null) {
                    file = scan.F();
                }
                if (file != null) {
                    scan.A();
                    List<f> h3 = h(scan);
                    if (!scan.h() && !scan.f(';')) {
                        throw new CSSParseException("Invalid @media rule: expected '}' at end of rule set");
                    } else if (g.k() != null && c(h3, this.a)) {
                        g.k().b(file);
                        throw null;
                    }
                } else {
                    throw new CSSParseException("Invalid @import rule: expected string or url()");
                }
            }
            scan.A();
            return;
        }
        throw new CSSParseException("Invalid '@' rule");
    }

    private void o(d scan) {
        int depth = 0;
        while (!scan.h()) {
            int ch = scan.l().intValue();
            if (ch != 59 || depth != 0) {
                if (ch == 123) {
                    depth++;
                } else if (ch == 125 && depth > 0 && depth - 1 == 0) {
                    return;
                }
            } else {
                return;
            }
        }
    }

    private r j(d scan) {
        r ruleset = new r();
        while (!scan.h()) {
            try {
                if (!scan.g("<!--")) {
                    if (!scan.g("-->")) {
                        if (!scan.f('@')) {
                            if (!i(ruleset, scan)) {
                                break;
                            }
                        } else {
                            e(ruleset, scan);
                        }
                    }
                }
            } catch (CSSParseException e2) {
                Log.e("CSSParser", "CSS parser terminated early due to error: " + e2.getMessage());
            }
        }
        return ruleset;
    }

    private boolean i(r ruleset, d scan) {
        List<CSSParser.Selector> selectors = scan.L();
        if (selectors == null || selectors.isEmpty()) {
            return false;
        }
        if (scan.f('{')) {
            scan.A();
            g.e0 ruleStyle = g(scan);
            scan.A();
            Iterator<CSSParser.Selector> it = selectors.iterator();
            while (it.hasNext()) {
                ruleset.a(new p((s) it.next(), ruleStyle, this.b));
            }
            return true;
        }
        throw new CSSParseException("Malformed rule block: expected '{'");
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x0063 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:3:0x0014  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.caverock.androidsvg.g.e0 g(com.caverock.androidsvg.a.d r6) {
        /*
            r5 = this;
            com.caverock.androidsvg.g$e0 r0 = new com.caverock.androidsvg.g$e0
            r0.<init>()
        L_0x0005:
            java.lang.String r1 = r6.H()
            r6.A()
            r2 = 58
            boolean r2 = r6.f(r2)
            if (r2 == 0) goto L_0x0063
            r6.A()
            java.lang.String r2 = r6.J()
            if (r2 == 0) goto L_0x005b
            r6.A()
            r3 = 33
            boolean r3 = r6.f(r3)
            if (r3 == 0) goto L_0x003f
            r6.A()
            java.lang.String r3 = "important"
            boolean r3 = r6.g(r3)
            if (r3 == 0) goto L_0x0037
            r6.A()
            goto L_0x003f
        L_0x0037:
            com.caverock.androidsvg.CSSParseException r3 = new com.caverock.androidsvg.CSSParseException
            java.lang.String r4 = "Malformed rule set: found unexpected '!'"
            r3.<init>(r4)
            throw r3
        L_0x003f:
            r3 = 59
            r6.f(r3)
            com.caverock.androidsvg.j.S0(r0, r1, r2)
            r6.A()
            boolean r3 = r6.h()
            if (r3 != 0) goto L_0x005a
            r3 = 125(0x7d, float:1.75E-43)
            boolean r3 = r6.f(r3)
            if (r3 == 0) goto L_0x0059
            goto L_0x005a
        L_0x0059:
            goto L_0x0005
        L_0x005a:
            return r0
        L_0x005b:
            com.caverock.androidsvg.CSSParseException r3 = new com.caverock.androidsvg.CSSParseException
            java.lang.String r4 = "Expected property value"
            r3.<init>(r4)
            throw r3
        L_0x0063:
            com.caverock.androidsvg.CSSParseException r2 = new com.caverock.androidsvg.CSSParseException
            java.lang.String r3 = "Expected ':'"
            r2.<init>(r3)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.caverock.androidsvg.a.g(com.caverock.androidsvg.a$d):com.caverock.androidsvg.g$e0");
    }

    public static List<String> f(String val) {
        d scan = new d(val);
        List<String> classNameList = null;
        while (!scan.h()) {
            String className = scan.r();
            if (className != null) {
                if (classNameList == null) {
                    classNameList = new ArrayList<>();
                }
                classNameList.add(className);
                scan.A();
            }
        }
        return classNameList;
    }

    /* compiled from: CSSParser */
    public static class q {
        g.l0 a;

        q() {
        }

        public String toString() {
            g.l0 l0Var = this.a;
            if (l0Var == null) {
                return "";
            }
            return String.format("<%s id=\"%s\">", new Object[]{l0Var.m(), this.a.c});
        }
    }

    static boolean l(q ruleMatchContext, s selector, g.l0 obj) {
        ArrayList arrayList = new ArrayList();
        for (g.j0 parent = obj.b; parent != null; parent = ((g.n0) parent).b) {
            arrayList.add(0, parent);
        }
        int ancestorsPos = arrayList.size() - 1;
        if (selector.g() == 1) {
            return n(ruleMatchContext, selector.e(0), arrayList, ancestorsPos, obj);
        }
        return k(ruleMatchContext, selector, selector.g() - 1, arrayList, ancestorsPos, obj);
    }

    private static boolean k(q ruleMatchContext, s selector, int selPartPos, List<g.j0> ancestors, int ancestorsPos, g.l0 obj) {
        t sel = selector.e(selPartPos);
        if (!n(ruleMatchContext, sel, ancestors, ancestorsPos, obj)) {
            return false;
        }
        e eVar = sel.a;
        if (eVar == e.DESCENDANT) {
            if (selPartPos == 0) {
                return true;
            }
            while (ancestorsPos >= 0) {
                if (m(ruleMatchContext, selector, selPartPos - 1, ancestors, ancestorsPos)) {
                    return true;
                }
                ancestorsPos--;
            }
            return false;
        } else if (eVar == e.CHILD) {
            return m(ruleMatchContext, selector, selPartPos - 1, ancestors, ancestorsPos);
        } else {
            int childPos = a(ancestors, ancestorsPos, obj);
            if (childPos <= 0) {
                return false;
            }
            return k(ruleMatchContext, selector, selPartPos - 1, ancestors, ancestorsPos, (g.l0) obj.b.getChildren().get(childPos - 1));
        }
    }

    private static boolean m(q ruleMatchContext, s selector, int selPartPos, List<g.j0> ancestors, int ancestorsPos) {
        t sel = selector.e(selPartPos);
        g.l0 obj = (g.l0) ancestors.get(ancestorsPos);
        if (!n(ruleMatchContext, sel, ancestors, ancestorsPos, obj)) {
            return false;
        }
        e eVar = sel.a;
        if (eVar == e.DESCENDANT) {
            if (selPartPos == 0) {
                return true;
            }
            while (ancestorsPos > 0) {
                ancestorsPos--;
                if (m(ruleMatchContext, selector, selPartPos - 1, ancestors, ancestorsPos)) {
                    return true;
                }
            }
            return false;
        } else if (eVar == e.CHILD) {
            return m(ruleMatchContext, selector, selPartPos - 1, ancestors, ancestorsPos - 1);
        } else {
            int childPos = a(ancestors, ancestorsPos, obj);
            if (childPos <= 0) {
                return false;
            }
            return k(ruleMatchContext, selector, selPartPos - 1, ancestors, ancestorsPos, (g.l0) obj.b.getChildren().get(childPos - 1));
        }
    }

    private static int a(List<g.j0> ancestors, int ancestorsPos, g.l0 obj) {
        if (ancestorsPos < 0) {
            return 0;
        }
        g.j0 j0Var = ancestors.get(ancestorsPos);
        g.j0 j0Var2 = obj.b;
        if (j0Var != j0Var2) {
            return -1;
        }
        int childPos = 0;
        for (g.n0 child : j0Var2.getChildren()) {
            if (child == obj) {
                return childPos;
            }
            childPos++;
        }
        return -1;
    }

    private static boolean n(q ruleMatchContext, t sel, List<g.j0> list, int ancestorsPos, g.l0 obj) {
        String str = sel.b;
        if (str != null && !str.equals(obj.m().toLowerCase(Locale.US))) {
            return false;
        }
        List<b> list2 = sel.c;
        if (list2 != null) {
            for (b attr : list2) {
                String str2 = attr.a;
                char c2 = 65535;
                switch (str2.hashCode()) {
                    case 3355:
                        if (str2.equals("id")) {
                            c2 = 0;
                            break;
                        }
                        break;
                    case 94742904:
                        if (str2.equals("class")) {
                            c2 = 1;
                            break;
                        }
                        break;
                }
                switch (c2) {
                    case 0:
                        if (attr.c.equals(obj.c)) {
                            break;
                        } else {
                            return false;
                        }
                    case 1:
                        List<String> list3 = obj.g;
                        if (list3 != null && list3.contains(attr.c)) {
                            break;
                        } else {
                            return false;
                        }
                        break;
                    default:
                        return false;
                }
            }
        }
        List<g> list4 = sel.d;
        if (list4 != null) {
            for (g pseudo : list4) {
                if (!pseudo.a(ruleMatchContext, obj)) {
                    return false;
                }
            }
        }
        return true;
    }

    /* compiled from: CSSParser */
    public static class h implements g {
        private int a;
        private int b;
        private boolean c;
        private boolean d;
        private String e;

        h(int a2, int b2, boolean isFromStart, boolean isOfType, String nodeName) {
            this.a = a2;
            this.b = b2;
            this.c = isFromStart;
            this.d = isOfType;
            this.e = nodeName;
        }

        /* JADX WARNING: type inference failed for: r4v5, types: [com.caverock.androidsvg.g$n0] */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean a(com.caverock.androidsvg.a.q r9, com.caverock.androidsvg.g.l0 r10) {
            /*
                r8 = this;
                boolean r0 = r8.d
                if (r0 == 0) goto L_0x000d
                java.lang.String r0 = r8.e
                if (r0 != 0) goto L_0x000d
                java.lang.String r0 = r10.m()
                goto L_0x000f
            L_0x000d:
                java.lang.String r0 = r8.e
            L_0x000f:
                r1 = 0
                r2 = 1
                com.caverock.androidsvg.g$j0 r3 = r10.b
                if (r3 == 0) goto L_0x003f
                r2 = 0
                java.util.List r3 = r3.getChildren()
                java.util.Iterator r3 = r3.iterator()
            L_0x001e:
                boolean r4 = r3.hasNext()
                if (r4 == 0) goto L_0x003f
                java.lang.Object r4 = r3.next()
                com.caverock.androidsvg.g$n0 r4 = (com.caverock.androidsvg.g.n0) r4
                r5 = r4
                com.caverock.androidsvg.g$l0 r5 = (com.caverock.androidsvg.g.l0) r5
                if (r5 != r10) goto L_0x0030
                r1 = r2
            L_0x0030:
                if (r0 == 0) goto L_0x003c
                java.lang.String r6 = r5.m()
                boolean r6 = r6.equals(r0)
                if (r6 == 0) goto L_0x003e
            L_0x003c:
                int r2 = r2 + 1
            L_0x003e:
                goto L_0x001e
            L_0x003f:
                boolean r3 = r8.c
                if (r3 == 0) goto L_0x0046
                int r3 = r1 + 1
                goto L_0x0048
            L_0x0046:
                int r3 = r2 - r1
            L_0x0048:
                r1 = r3
                int r3 = r8.a
                r4 = 0
                r5 = 1
                if (r3 != 0) goto L_0x0055
                int r3 = r8.b
                if (r1 != r3) goto L_0x0054
                r4 = r5
            L_0x0054:
                return r4
            L_0x0055:
                int r6 = r8.b
                int r7 = r1 - r6
                int r7 = r7 % r3
                if (r7 != 0) goto L_0x0076
                int r3 = r1 - r6
                int r3 = java.lang.Integer.signum(r3)
                if (r3 == 0) goto L_0x0074
                int r3 = r8.b
                int r3 = r1 - r3
                int r3 = java.lang.Integer.signum(r3)
                int r6 = r8.a
                int r6 = java.lang.Integer.signum(r6)
                if (r3 != r6) goto L_0x0076
            L_0x0074:
                r4 = r5
                goto L_0x0077
            L_0x0076:
            L_0x0077:
                return r4
            */
            throw new UnsupportedOperationException("Method not decompiled: com.caverock.androidsvg.a.h.a(com.caverock.androidsvg.a$q, com.caverock.androidsvg.g$l0):boolean");
        }

        public String toString() {
            String last = this.c ? "" : "last-";
            if (this.d) {
                return String.format("nth-%schild(%dn%+d of type <%s>)", new Object[]{last, Integer.valueOf(this.a), Integer.valueOf(this.b), this.e});
            }
            return String.format("nth-%schild(%dn%+d)", new Object[]{last, Integer.valueOf(this.a), Integer.valueOf(this.b)});
        }
    }

    /* compiled from: CSSParser */
    public static class m implements g {
        private boolean a;
        private String b;

        public m(boolean isOfType, String nodeName) {
            this.a = isOfType;
            this.b = nodeName;
        }

        public boolean a(q ruleMatchContext, g.l0 obj) {
            String nodeNameToCheck = (!this.a || this.b != null) ? this.b : obj.m();
            int childCount = 1;
            g.j0 j0Var = obj.b;
            if (j0Var != null) {
                childCount = 0;
                Iterator<g.n0> it = j0Var.getChildren().iterator();
                while (it.hasNext()) {
                    g.l0 child = (g.l0) it.next();
                    if (nodeNameToCheck == null || child.m().equals(nodeNameToCheck)) {
                        childCount++;
                    }
                }
            }
            return childCount == 1;
        }

        public String toString() {
            if (!this.a) {
                return String.format("only-child", new Object[0]);
            }
            return String.format("only-of-type <%s>", new Object[]{this.b});
        }
    }

    /* compiled from: CSSParser */
    public static class n implements g {
        private n() {
        }

        /* synthetic */ n(C0049a x0) {
            this();
        }

        public boolean a(q ruleMatchContext, g.l0 obj) {
            return obj.b == null;
        }

        public String toString() {
            return "root";
        }
    }

    /* compiled from: CSSParser */
    public static class i implements g {
        private i() {
        }

        /* synthetic */ i(C0049a x0) {
            this();
        }

        public boolean a(q ruleMatchContext, g.l0 obj) {
            if (!(obj instanceof g.j0) || ((g.j0) obj).getChildren().size() == 0) {
                return true;
            }
            return false;
        }

        public String toString() {
            return "empty";
        }
    }

    /* compiled from: CSSParser */
    public static class k implements g {
        private List<s> a;

        k(List<s> selectorGroup) {
            this.a = selectorGroup;
        }

        public boolean a(q ruleMatchContext, g.l0 obj) {
            for (s selector : this.a) {
                if (a.l(ruleMatchContext, selector, obj)) {
                    return false;
                }
            }
            return true;
        }

        /* access modifiers changed from: package-private */
        public int b() {
            int highest = Integer.MIN_VALUE;
            for (s selector : this.a) {
                if (selector.b > highest) {
                    highest = selector.b;
                }
            }
            return highest;
        }

        public String toString() {
            return "not(" + this.a + ")";
        }
    }

    /* compiled from: CSSParser */
    public static class o implements g {
        private o() {
        }

        /* synthetic */ o(C0049a x0) {
            this();
        }

        public boolean a(q ruleMatchContext, g.l0 obj) {
            if (ruleMatchContext == null || obj != ruleMatchContext.a) {
                return false;
            }
            return true;
        }

        public String toString() {
            return TypedValues.AttributesType.S_TARGET;
        }
    }

    /* compiled from: CSSParser */
    public static class l implements g {
        private String a;

        l(String clazz) {
            this.a = clazz;
        }

        public boolean a(q ruleMatchContext, g.l0 obj) {
            return false;
        }

        public String toString() {
            return this.a;
        }
    }
}
