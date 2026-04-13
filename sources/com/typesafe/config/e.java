package com.typesafe.config;

import java.util.Map;

/* compiled from: ConfigObject */
public interface e extends j, Map<String, j> {
    a toConfig();

    Map<String, Object> unwrapped();
}
