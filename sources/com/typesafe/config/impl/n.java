package com.typesafe.config.impl;

import com.typesafe.config.j;

/* compiled from: Container */
public interface n extends j {
    boolean hasDescendant(AbstractConfigValue abstractConfigValue);

    AbstractConfigValue replaceChild(AbstractConfigValue abstractConfigValue, AbstractConfigValue abstractConfigValue2);
}
