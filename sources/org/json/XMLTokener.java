package org.json;

import java.io.Reader;
import java.util.HashMap;

public class XMLTokener extends JSONTokener {
    public static final HashMap<String, Character> entity;

    static {
        HashMap<String, Character> hashMap = new HashMap<>(8);
        entity = hashMap;
        hashMap.put("amp", XML.AMP);
        hashMap.put("apos", XML.APOS);
        hashMap.put("gt", XML.GT);
        hashMap.put("lt", XML.LT);
        hashMap.put("quot", XML.QUOT);
    }

    public XMLTokener(Reader r) {
        super(r);
    }

    public XMLTokener(String s) {
        super(s);
    }

    public String nextCDATA() {
        StringBuilder sb = new StringBuilder();
        while (more()) {
            sb.append(next());
            int i = sb.length() - 3;
            if (i >= 0 && sb.charAt(i) == ']' && sb.charAt(i + 1) == ']' && sb.charAt(i + 2) == '>') {
                sb.setLength(i);
                return sb.toString();
            }
        }
        throw syntaxError("Unclosed CDATA");
    }

    public Object nextContent() {
        char c;
        do {
            c = next();
        } while (Character.isWhitespace(c));
        if (c == 0) {
            return null;
        }
        if (c == '<') {
            return XML.LT;
        }
        StringBuilder sb = new StringBuilder();
        while (c != 0) {
            if (c == '<') {
                back();
                return sb.toString().trim();
            }
            if (c == '&') {
                sb.append(nextEntity(c));
            } else {
                sb.append(c);
            }
            c = next();
        }
        return sb.toString().trim();
    }

    public Object nextEntity(char ampersand) {
        char c;
        StringBuilder sb = new StringBuilder();
        while (true) {
            c = next();
            if (!Character.isLetterOrDigit(c) && c != '#') {
                break;
            }
            sb.append(Character.toLowerCase(c));
        }
        if (c == ';') {
            return unescapeEntity(sb.toString());
        }
        throw syntaxError("Missing ';' in XML entity: &" + sb);
    }

    static String unescapeEntity(String e) {
        int cp;
        if (e == null || e.isEmpty()) {
            return "";
        }
        if (e.charAt(0) == '#') {
            if (e.charAt(1) == 'x') {
                cp = Integer.parseInt(e.substring(2), 16);
            } else {
                cp = Integer.parseInt(e.substring(1));
            }
            return new String(new int[]{cp}, 0, 1);
        }
        Character knownEntity = entity.get(e);
        if (knownEntity != null) {
            return knownEntity.toString();
        }
        return '&' + e + ';';
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x003a A[LOOP_START] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object nextMeta() {
        /*
            r3 = this;
        L_0x0000:
            char r0 = r3.next()
            boolean r1 = java.lang.Character.isWhitespace(r0)
            if (r1 != 0) goto L_0x0056
            java.lang.String r1 = "Unterminated string"
            switch(r0) {
                case 0: goto L_0x0033;
                case 33: goto L_0x0030;
                case 34: goto L_0x001f;
                case 39: goto L_0x001f;
                case 47: goto L_0x001c;
                case 60: goto L_0x0019;
                case 61: goto L_0x0016;
                case 62: goto L_0x0013;
                case 63: goto L_0x0010;
                default: goto L_0x000f;
            }
        L_0x000f:
            goto L_0x003a
        L_0x0010:
            java.lang.Character r1 = org.json.XML.QUEST
            return r1
        L_0x0013:
            java.lang.Character r1 = org.json.XML.GT
            return r1
        L_0x0016:
            java.lang.Character r1 = org.json.XML.EQ
            return r1
        L_0x0019:
            java.lang.Character r1 = org.json.XML.LT
            return r1
        L_0x001c:
            java.lang.Character r1 = org.json.XML.SLASH
            return r1
        L_0x001f:
            r2 = r0
        L_0x0020:
            char r0 = r3.next()
            if (r0 == 0) goto L_0x002b
            if (r0 != r2) goto L_0x0020
            java.lang.Boolean r1 = java.lang.Boolean.TRUE
            return r1
        L_0x002b:
            org.json.JSONException r1 = r3.syntaxError(r1)
            throw r1
        L_0x0030:
            java.lang.Character r1 = org.json.XML.BANG
            return r1
        L_0x0033:
            java.lang.String r1 = "Misshaped meta tag"
            org.json.JSONException r1 = r3.syntaxError(r1)
            throw r1
        L_0x003a:
            char r0 = r3.next()
            boolean r2 = java.lang.Character.isWhitespace(r0)
            if (r2 == 0) goto L_0x0047
            java.lang.Boolean r1 = java.lang.Boolean.TRUE
            return r1
        L_0x0047:
            switch(r0) {
                case 0: goto L_0x0051;
                case 33: goto L_0x004b;
                case 34: goto L_0x004b;
                case 39: goto L_0x004b;
                case 47: goto L_0x004b;
                case 60: goto L_0x004b;
                case 61: goto L_0x004b;
                case 62: goto L_0x004b;
                case 63: goto L_0x004b;
                default: goto L_0x004a;
            }
        L_0x004a:
            goto L_0x003a
        L_0x004b:
            r3.back()
            java.lang.Boolean r1 = java.lang.Boolean.TRUE
            return r1
        L_0x0051:
            org.json.JSONException r1 = r3.syntaxError(r1)
            throw r1
        L_0x0056:
            goto L_0x0000
        */
        throw new UnsupportedOperationException("Method not decompiled: org.json.XMLTokener.nextMeta():java.lang.Object");
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x005a A[LOOP_START, PHI: r0 
      PHI: (r0v2 'c' char) = (r0v0 'c' char), (r0v3 'c' char) binds: [B:3:0x000d, B:34:0x006c] A[DONT_GENERATE, DONT_INLINE]] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object nextToken() {
        /*
            r4 = this;
        L_0x0000:
            char r0 = r4.next()
            boolean r1 = java.lang.Character.isWhitespace(r0)
            if (r1 != 0) goto L_0x0084
            switch(r0) {
                case 0: goto L_0x0053;
                case 33: goto L_0x0050;
                case 34: goto L_0x0026;
                case 39: goto L_0x0026;
                case 47: goto L_0x0023;
                case 60: goto L_0x001c;
                case 61: goto L_0x0019;
                case 62: goto L_0x0016;
                case 63: goto L_0x0013;
                default: goto L_0x000d;
            }
        L_0x000d:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            goto L_0x005a
        L_0x0013:
            java.lang.Character r1 = org.json.XML.QUEST
            return r1
        L_0x0016:
            java.lang.Character r1 = org.json.XML.GT
            return r1
        L_0x0019:
            java.lang.Character r1 = org.json.XML.EQ
            return r1
        L_0x001c:
            java.lang.String r1 = "Misplaced '<'"
            org.json.JSONException r1 = r4.syntaxError(r1)
            throw r1
        L_0x0023:
            java.lang.Character r1 = org.json.XML.SLASH
            return r1
        L_0x0026:
            r1 = r0
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
        L_0x002c:
            char r0 = r4.next()
            if (r0 == 0) goto L_0x0049
            if (r0 != r1) goto L_0x0039
            java.lang.String r3 = r2.toString()
            return r3
        L_0x0039:
            r3 = 38
            if (r0 != r3) goto L_0x0045
            java.lang.Object r3 = r4.nextEntity(r0)
            r2.append(r3)
            goto L_0x002c
        L_0x0045:
            r2.append(r0)
            goto L_0x002c
        L_0x0049:
            java.lang.String r3 = "Unterminated string"
            org.json.JSONException r3 = r4.syntaxError(r3)
            throw r3
        L_0x0050:
            java.lang.Character r1 = org.json.XML.BANG
            return r1
        L_0x0053:
            java.lang.String r1 = "Misshaped element"
            org.json.JSONException r1 = r4.syntaxError(r1)
            throw r1
        L_0x005a:
            r1.append(r0)
            char r0 = r4.next()
            boolean r2 = java.lang.Character.isWhitespace(r0)
            if (r2 == 0) goto L_0x006c
            java.lang.String r2 = r1.toString()
            return r2
        L_0x006c:
            switch(r0) {
                case 0: goto L_0x007f;
                case 33: goto L_0x0077;
                case 34: goto L_0x0070;
                case 39: goto L_0x0070;
                case 47: goto L_0x0077;
                case 60: goto L_0x0070;
                case 61: goto L_0x0077;
                case 62: goto L_0x0077;
                case 63: goto L_0x0077;
                case 91: goto L_0x0077;
                case 93: goto L_0x0077;
                default: goto L_0x006f;
            }
        L_0x006f:
            goto L_0x005a
        L_0x0070:
            java.lang.String r2 = "Bad character in a name"
            org.json.JSONException r2 = r4.syntaxError(r2)
            throw r2
        L_0x0077:
            r4.back()
            java.lang.String r2 = r1.toString()
            return r2
        L_0x007f:
            java.lang.String r2 = r1.toString()
            return r2
        L_0x0084:
            goto L_0x0000
        */
        throw new UnsupportedOperationException("Method not decompiled: org.json.XMLTokener.nextToken():java.lang.Object");
    }

    public void skipPast(String to) {
        char c;
        int offset = 0;
        int length = to.length();
        char[] circle = new char[length];
        int i = 0;
        while (i < length) {
            char c2 = next();
            if (c2 != 0) {
                circle[i] = c2;
                i++;
            } else {
                return;
            }
        }
        while (true) {
            int j = offset;
            boolean b = true;
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    break;
                } else if (circle[j] != to.charAt(i2)) {
                    b = false;
                    break;
                } else {
                    j++;
                    if (j >= length) {
                        j -= length;
                    }
                    i2++;
                }
            }
            if (!b && (c = next()) != 0) {
                circle[offset] = c;
                offset++;
                if (offset >= length) {
                    offset -= length;
                }
            } else {
                return;
            }
        }
    }
}
