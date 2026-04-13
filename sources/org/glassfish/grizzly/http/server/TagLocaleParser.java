package org.glassfish.grizzly.http.server;

import java.util.Locale;

public class TagLocaleParser implements LocaleParser {
    TagLocaleParser() {
    }

    public Locale parseLocale(String source) {
        Locale l = Locale.forLanguageTag(source);
        if (l.getLanguage().isEmpty()) {
            return null;
        }
        return l;
    }
}
