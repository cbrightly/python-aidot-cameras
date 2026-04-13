package org.spongycastle.i18n.filter;

public class HTMLFilter implements Filter {
    public String a(String input) {
        StringBuffer buf = new StringBuffer(input);
        int i = 0;
        while (i < buf.length()) {
            switch (buf.charAt(i)) {
                case '\"':
                    buf.replace(i, i + 1, "&#34");
                    break;
                case '#':
                    buf.replace(i, i + 1, "&#35");
                    break;
                case '%':
                    buf.replace(i, i + 1, "&#37");
                    break;
                case '&':
                    buf.replace(i, i + 1, "&#38");
                    break;
                case '\'':
                    buf.replace(i, i + 1, "&#39");
                    break;
                case '(':
                    buf.replace(i, i + 1, "&#40");
                    break;
                case ')':
                    buf.replace(i, i + 1, "&#41");
                    break;
                case '+':
                    buf.replace(i, i + 1, "&#43");
                    break;
                case '-':
                    buf.replace(i, i + 1, "&#45");
                    break;
                case ';':
                    buf.replace(i, i + 1, "&#59");
                    break;
                case '<':
                    buf.replace(i, i + 1, "&#60");
                    break;
                case '>':
                    buf.replace(i, i + 1, "&#62");
                    break;
                default:
                    i -= 3;
                    break;
            }
            i += 4;
        }
        return buf.toString();
    }

    public String b(String input) {
        return a(input);
    }
}
