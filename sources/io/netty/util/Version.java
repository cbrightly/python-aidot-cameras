package io.netty.util;

import io.netty.util.internal.PlatformDependent;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;

public final class Version {
    private static final String PROP_BUILD_DATE = ".buildDate";
    private static final String PROP_COMMIT_DATE = ".commitDate";
    private static final String PROP_LONG_COMMIT_HASH = ".longCommitHash";
    private static final String PROP_REPO_STATUS = ".repoStatus";
    private static final String PROP_SHORT_COMMIT_HASH = ".shortCommitHash";
    private static final String PROP_VERSION = ".version";
    private final String artifactId;
    private final String artifactVersion;
    private final long buildTimeMillis;
    private final long commitTimeMillis;
    private final String longCommitHash;
    private final String repositoryStatus;
    private final String shortCommitHash;

    public static Map<String, Version> identify() {
        return identify((ClassLoader) null);
    }

    public static Map<String, Version> identify(ClassLoader classLoader) {
        ClassLoader classLoader2;
        InputStream in;
        if (classLoader == null) {
            classLoader2 = PlatformDependent.getContextClassLoader();
        } else {
            classLoader2 = classLoader;
        }
        Properties props = new Properties();
        try {
            Enumeration<URL> resources = classLoader2.getResources("META-INF/io.netty.versions.properties");
            while (resources.hasMoreElements()) {
                in = resources.nextElement().openStream();
                props.load(in);
                try {
                    in.close();
                } catch (Exception e) {
                }
            }
        } catch (Exception e2) {
        } catch (Throwable th) {
            Throwable th2 = th;
            try {
                in.close();
            } catch (Exception e3) {
            }
            throw th2;
        }
        Set<String> artifactIds = new HashSet<>();
        for (String k : props.keySet()) {
            int dotIndex = k.indexOf(46);
            if (dotIndex > 0) {
                String artifactId2 = k.substring(0, dotIndex);
                if (props.containsKey(artifactId2 + PROP_VERSION)) {
                    if (props.containsKey(artifactId2 + PROP_BUILD_DATE)) {
                        if (props.containsKey(artifactId2 + PROP_COMMIT_DATE)) {
                            if (props.containsKey(artifactId2 + PROP_SHORT_COMMIT_HASH)) {
                                if (props.containsKey(artifactId2 + PROP_LONG_COMMIT_HASH)) {
                                    if (props.containsKey(artifactId2 + PROP_REPO_STATUS)) {
                                        artifactIds.add(artifactId2);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        Map<String, Version> versions = new TreeMap<>();
        for (String artifactId3 : artifactIds) {
            String property = props.getProperty(artifactId3 + PROP_VERSION);
            long parseIso8601 = parseIso8601(props.getProperty(artifactId3 + PROP_BUILD_DATE));
            long parseIso86012 = parseIso8601(props.getProperty(artifactId3 + PROP_COMMIT_DATE));
            String property2 = props.getProperty(artifactId3 + PROP_SHORT_COMMIT_HASH);
            String property3 = props.getProperty(artifactId3 + PROP_LONG_COMMIT_HASH);
            Set<String> artifactIds2 = artifactIds;
            Version version = r12;
            Version version2 = new Version(artifactId3, property, parseIso8601, parseIso86012, property2, property3, props.getProperty(artifactId3 + PROP_REPO_STATUS));
            versions.put(artifactId3, version);
            artifactIds = artifactIds2;
        }
        return versions;
    }

    private static long parseIso8601(String value) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z").parse(value).getTime();
        } catch (ParseException e) {
            return 0;
        }
    }

    public static void main(String[] args) {
        for (Version v : identify().values()) {
            System.err.println(v);
        }
    }

    private Version(String artifactId2, String artifactVersion2, long buildTimeMillis2, long commitTimeMillis2, String shortCommitHash2, String longCommitHash2, String repositoryStatus2) {
        this.artifactId = artifactId2;
        this.artifactVersion = artifactVersion2;
        this.buildTimeMillis = buildTimeMillis2;
        this.commitTimeMillis = commitTimeMillis2;
        this.shortCommitHash = shortCommitHash2;
        this.longCommitHash = longCommitHash2;
        this.repositoryStatus = repositoryStatus2;
    }

    public String artifactId() {
        return this.artifactId;
    }

    public String artifactVersion() {
        return this.artifactVersion;
    }

    public long buildTimeMillis() {
        return this.buildTimeMillis;
    }

    public long commitTimeMillis() {
        return this.commitTimeMillis;
    }

    public String shortCommitHash() {
        return this.shortCommitHash;
    }

    public String longCommitHash() {
        return this.longCommitHash;
    }

    public String repositoryStatus() {
        return this.repositoryStatus;
    }

    public String toString() {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append(this.artifactId);
        sb.append('-');
        sb.append(this.artifactVersion);
        sb.append('.');
        sb.append(this.shortCommitHash);
        if ("clean".equals(this.repositoryStatus)) {
            str = "";
        } else {
            str = " (repository: " + this.repositoryStatus + ')';
        }
        sb.append(str);
        return sb.toString();
    }
}
