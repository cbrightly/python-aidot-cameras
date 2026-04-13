package org.spongycastle.i18n;

import java.util.Locale;
import java.util.TimeZone;

public class TextBundle extends LocalizedMessage {
    public String e(Locale loc) {
        return d("text", loc, TimeZone.getDefault());
    }
}
