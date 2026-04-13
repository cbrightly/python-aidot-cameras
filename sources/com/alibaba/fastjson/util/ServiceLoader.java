package com.alibaba.fastjson.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

public class ServiceLoader {
    private static final String PREFIX = "META-INF/services/";
    private static final Set<String> loadedUrls = new HashSet();

    public static <T> Set<T> load(Class<T> clazz, ClassLoader classLoader) {
        if (classLoader == null) {
            return Collections.emptySet();
        }
        Set<T> services = new HashSet<>();
        String path = PREFIX + clazz.getName();
        Set<String> serviceNames = new HashSet<>();
        try {
            Enumeration<URL> urls = classLoader.getResources(path);
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                Set<String> set = loadedUrls;
                if (!set.contains(url.toString())) {
                    load(url, serviceNames);
                    set.add(url.toString());
                }
            }
        } catch (Throwable th) {
        }
        for (String serviceName : serviceNames) {
            try {
                services.add(classLoader.loadClass(serviceName).newInstance());
            } catch (Exception e) {
            }
        }
        return services;
    }

    public static void load(URL url, Set<String> set) {
        InputStream is = null;
        BufferedReader reader = null;
        try {
            is = url.openStream();
            reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
            while (true) {
                String line = reader.readLine();
                if (line != null) {
                    int ci = line.indexOf(35);
                    if (ci >= 0) {
                        line = line.substring(0, ci);
                    }
                    String line2 = line.trim();
                    if (line2.length() != 0) {
                        set.add(line2);
                    }
                } else {
                    return;
                }
            }
        } finally {
            IOUtils.close(reader);
            IOUtils.close(is);
        }
    }
}
