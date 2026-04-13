package com.typesafe.config;

import com.typesafe.config.impl.ConfigBeanImpl;

public class ConfigBeanFactory {
    public static <T> T create(a config, Class<T> clazz) {
        return ConfigBeanImpl.createInternal(config, clazz);
    }
}
