package org.apache.commons.math3.exception.util;

import java.io.Serializable;
import java.util.Locale;

/* compiled from: Localizable */
public interface c extends Serializable {
    String getLocalizedString(Locale locale);

    String getSourceString();
}
