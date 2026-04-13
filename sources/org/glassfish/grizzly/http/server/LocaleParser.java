package org.glassfish.grizzly.http.server;

import java.util.Locale;

public interface LocaleParser {
    Locale parseLocale(String str);
}
