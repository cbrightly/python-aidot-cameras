package org.glassfish.grizzly.localization;

public final class LocalizableMessage implements Localizable {
    private final Object[] _args;
    private final String _bundlename;
    private final String _key;

    public LocalizableMessage(String bundlename, String key, Object... args) {
        this._bundlename = bundlename;
        this._key = key;
        this._args = args == null ? new Object[0] : args;
    }

    public String getKey() {
        return this._key;
    }

    public Object[] getArguments() {
        return this._args;
    }

    public String getResourceBundleName() {
        return this._bundlename;
    }
}
