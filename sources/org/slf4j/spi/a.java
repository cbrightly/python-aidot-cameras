package org.slf4j.spi;

import java.util.Map;

/* compiled from: MDCAdapter */
public interface a {
    void a(String str, String str2);

    Map<String, String> b();

    void clear();

    void remove(String str);
}
