package org.glassfish.grizzly.http.server;

import java.util.Locale;

public class LegacyLocaleParser implements LocaleParser {
    LegacyLocaleParser() {
    }

    public Locale parseLocale(String source) {
        String language;
        String country;
        String language2;
        int dash = source.indexOf(45);
        if (dash < 0) {
            language2 = source;
            country = "";
            language = "";
        } else {
            String language3 = source.substring(0, dash);
            String country2 = source.substring(dash + 1);
            int vDash = country2.indexOf(45);
            if (vDash > 0) {
                country = country2.substring(0, vDash);
                language2 = language3;
                language = country2.substring(vDash + 1);
            } else {
                language2 = language3;
                language = "";
                country = country2;
            }
        }
        if (!isAlpha(language2) || !isAlpha(country) || !isAlpha(language)) {
            return null;
        }
        return new Locale(language2, country, language);
    }

    private static boolean isAlpha(String value) {
        if (value == null) {
            return false;
        }
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if ((c < 'a' || c > 'z') && (c < 'A' || c > 'Z')) {
                return false;
            }
        }
        return true;
    }
}
