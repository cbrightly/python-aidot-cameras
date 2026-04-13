package org.glassfish.grizzly.localization;

public interface Localizable {
    public static final String NOT_LOCALIZABLE = "\u0000";

    Object[] getArguments();

    String getKey();

    String getResourceBundleName();
}
