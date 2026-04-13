package org.spongycastle.i18n;

import java.util.Locale;
import java.util.TimeZone;

public class LocaleString extends LocalizedMessage {
    public String e(Locale locale) {
        return d((String) null, locale, (TimeZone) null);
    }
}
