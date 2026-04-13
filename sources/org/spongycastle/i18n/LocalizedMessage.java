package org.spongycastle.i18n;

import com.google.maps.android.BuildConfig;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.Format;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.TimeZone;
import org.spongycastle.i18n.filter.Filter;

public class LocalizedMessage {
    protected final String a;
    protected final String b;
    protected String c;
    protected FilteredArguments d;
    protected FilteredArguments e;
    protected ClassLoader f;

    public String d(String key, Locale loc, TimeZone timezone) {
        ClassLoader classLoader;
        ResourceBundle bundle;
        String entry = this.a;
        if (key != null) {
            entry = entry + "." + key;
        }
        try {
            ClassLoader classLoader2 = this.f;
            if (classLoader2 == null) {
                bundle = ResourceBundle.getBundle(this.b, loc);
            } else {
                bundle = ResourceBundle.getBundle(this.b, loc, classLoader2);
            }
            String result = bundle.getString(entry);
            if (!this.c.equals("ISO-8859-1")) {
                result = new String(result.getBytes("ISO-8859-1"), this.c);
            }
            if (!this.d.d()) {
                result = b(result, this.d.c(loc), loc, timezone);
            }
            return a(result, loc);
        } catch (MissingResourceException e2) {
            String str = "Can't find entry " + entry + " in resource file " + this.b + ".";
            String str2 = this.b;
            ClassLoader classLoader3 = this.f;
            if (classLoader3 != null) {
                classLoader = classLoader3;
            } else {
                classLoader = c();
            }
            throw new MissingEntryException(str, str2, entry, loc, classLoader);
        } catch (UnsupportedEncodingException use) {
            throw new RuntimeException(use);
        }
    }

    /* access modifiers changed from: protected */
    public String b(String template, Object[] arguments, Locale locale, TimeZone timezone) {
        MessageFormat mf = new MessageFormat(" ");
        mf.setLocale(locale);
        mf.applyPattern(template);
        if (!timezone.equals(TimeZone.getDefault())) {
            Format[] formats = mf.getFormats();
            for (int i = 0; i < formats.length; i++) {
                if (formats[i] instanceof DateFormat) {
                    DateFormat temp = (DateFormat) formats[i];
                    temp.setTimeZone(timezone);
                    mf.setFormat(i, temp);
                }
            }
        }
        return mf.format(arguments);
    }

    /* access modifiers changed from: protected */
    public String a(String msg, Locale locale) {
        if (this.e == null) {
            return msg;
        }
        StringBuffer sb = new StringBuffer(msg);
        Object[] filteredArgs = this.e.c(locale);
        for (Object append : filteredArgs) {
            sb.append(append);
        }
        return sb.toString();
    }

    public ClassLoader c() {
        return this.f;
    }

    public class FilteredArguments {
        protected Filter a;
        protected boolean[] b;
        protected int[] c;
        protected Object[] d;
        protected Object[] e;
        protected Object[] f;

        public boolean d() {
            return this.e.length == 0;
        }

        public Object[] b() {
            return this.d;
        }

        public Object[] c(Locale locale) {
            Object arg;
            Object[] result = new Object[this.e.length];
            int i = 0;
            while (true) {
                Object[] objArr = this.e;
                if (i >= objArr.length) {
                    return result;
                }
                Object[] objArr2 = this.f;
                if (objArr2[i] != null) {
                    arg = objArr2[i];
                } else {
                    Object arg2 = objArr[i];
                    if (this.b[i]) {
                        arg = a(this.c[i], ((LocaleString) arg2).e(locale));
                    } else {
                        arg = a(this.c[i], arg2);
                        this.f[i] = arg;
                    }
                }
                result[i] = arg;
                i++;
            }
        }

        private Object a(int type, Object obj) {
            Filter filter = this.a;
            if (filter == null) {
                return obj;
            }
            Object o = obj == null ? BuildConfig.TRAVIS : obj;
            switch (type) {
                case 0:
                    return o;
                case 1:
                    return filter.a(o.toString());
                case 2:
                    return filter.b(o.toString());
                default:
                    return null;
            }
        }
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Resource: \"");
        sb.append(this.b);
        sb.append("\" Id: \"");
        sb.append(this.a);
        sb.append("\"");
        sb.append(" Arguments: ");
        sb.append(this.d.b().length);
        sb.append(" normal");
        FilteredArguments filteredArguments = this.e;
        if (filteredArguments != null && filteredArguments.b().length > 0) {
            sb.append(", ");
            sb.append(this.e.b().length);
            sb.append(" extra");
        }
        sb.append(" Encoding: ");
        sb.append(this.c);
        sb.append(" ClassLoader: ");
        sb.append(this.f);
        return sb.toString();
    }
}
