package org.osgi.framework;

import java.net.URL;
import java.util.Enumeration;
import org.osgi.annotation.versioning.ProviderType;

@ProviderType
public interface Bundle extends Comparable<Bundle> {
    BundleContext g();

    int getState();

    URL i(String str);

    String t();

    Enumeration<URL> v(String str, String str2, boolean z);

    Class<?> x(String str);

    long z();
}
