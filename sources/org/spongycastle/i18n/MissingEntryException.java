package org.spongycastle.i18n;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Locale;

public class MissingEntryException extends RuntimeException {
    private String debugMsg;
    protected final String key;
    protected final ClassLoader loader;
    protected final Locale locale;
    protected final String resource;

    public MissingEntryException(String message, String resource2, String key2, Locale locale2, ClassLoader loader2) {
        super(message);
        this.resource = resource2;
        this.key = key2;
        this.locale = locale2;
        this.loader = loader2;
    }

    public MissingEntryException(String message, Throwable cause, String resource2, String key2, Locale locale2, ClassLoader loader2) {
        super(message, cause);
        this.resource = resource2;
        this.key = key2;
        this.locale = locale2;
        this.loader = loader2;
    }

    public String getKey() {
        return this.key;
    }

    public String getResource() {
        return this.resource;
    }

    public ClassLoader getClassLoader() {
        return this.loader;
    }

    public Locale getLocale() {
        return this.locale;
    }

    public String getDebugMsg() {
        if (this.debugMsg == null) {
            this.debugMsg = "Can not find entry " + this.key + " in resource file " + this.resource + " for the locale " + this.locale + ".";
            ClassLoader classLoader = this.loader;
            if (classLoader instanceof URLClassLoader) {
                URL[] urls = ((URLClassLoader) classLoader).getURLs();
                this.debugMsg += " The following entries in the classpath were searched: ";
                for (int i = 0; i != urls.length; i++) {
                    this.debugMsg += urls[i] + " ";
                }
            }
        }
        return this.debugMsg;
    }
}
