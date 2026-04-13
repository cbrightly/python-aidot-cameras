package org.hamcrest;

import com.google.maps.android.BuildConfig;
import com.meituan.robust.Constants;
import io.netty.util.internal.StringUtil;
import java.util.Iterator;
import org.hamcrest.internal.c;

/* compiled from: BaseDescription */
public abstract class a implements b {
    /* access modifiers changed from: protected */
    public abstract void d(char c);

    /* access modifiers changed from: protected */
    public abstract void e(String str);

    public b b(String text) {
        e(text);
        return this;
    }

    public b a(d value) {
        value.describeTo(this);
        return this;
    }

    public b c(Object value) {
        if (value == null) {
            e(BuildConfig.TRAVIS);
        } else if (value instanceof String) {
            j((String) value);
        } else if (value instanceof Character) {
            d(StringUtil.DOUBLE_QUOTE);
            i(((Character) value).charValue());
            d(StringUtil.DOUBLE_QUOTE);
        } else if (value instanceof Short) {
            d('<');
            e(h(value));
            e("s>");
        } else if (value instanceof Long) {
            d('<');
            e(h(value));
            e("L>");
        } else if (value instanceof Float) {
            d('<');
            e(h(value));
            e("F>");
        } else if (value.getClass().isArray()) {
            g(Constants.ARRAY_TYPE, ", ", "]", new org.hamcrest.internal.a(value));
        } else {
            d('<');
            e(h(value));
            d('>');
        }
        return this;
    }

    private String h(Object value) {
        try {
            return String.valueOf(value);
        } catch (Exception e) {
            return value.getClass().getName() + "@" + Integer.toHexString(value.hashCode());
        }
    }

    private <T> b g(String start, String separator, String end, Iterator<T> values) {
        return f(start, separator, end, new c(values));
    }

    private b f(String start, String separator, String end, Iterator<? extends d> i) {
        boolean separate = false;
        e(start);
        while (i.hasNext()) {
            if (separate) {
                e(separator);
            }
            a((d) i.next());
            separate = true;
        }
        e(end);
        return this;
    }

    private void j(String unformatted) {
        d(StringUtil.DOUBLE_QUOTE);
        for (int i = 0; i < unformatted.length(); i++) {
            i(unformatted.charAt(i));
        }
        d(StringUtil.DOUBLE_QUOTE);
    }

    private void i(char ch) {
        switch (ch) {
            case 9:
                e("\\t");
                return;
            case 10:
                e("\\n");
                return;
            case 13:
                e("\\r");
                return;
            case '\"':
                e("\\\"");
                return;
            default:
                d(ch);
                return;
        }
    }
}
