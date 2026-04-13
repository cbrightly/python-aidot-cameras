package org.glassfish.grizzly.http.server.util;

import java.util.List;
import org.glassfish.grizzly.http.server.naming.DirContext;

public class AlternateDocBase {
    private String basePath;
    private String docBase;
    private String pattern;
    private int patternSlashCount;
    private String patternSuffix;
    private UrlPatternType patternType;
    private DirContext resources;
    private DirContext webappResources;
    private String wildcardPath;

    public enum UrlPatternType {
        EXACT,
        WILDCARD,
        EXTENSION
    }

    public void setUrlPattern(String urlPattern) {
        this.pattern = urlPattern;
        this.patternSlashCount = getSlashCount(urlPattern);
        if (urlPattern.endsWith("/*")) {
            this.patternType = UrlPatternType.WILDCARD;
            this.wildcardPath = urlPattern.substring(0, urlPattern.length() - 1);
        } else if (urlPattern.startsWith("*.")) {
            this.patternType = UrlPatternType.EXTENSION;
            this.patternSuffix = urlPattern.substring(1);
        } else {
            this.patternType = UrlPatternType.EXACT;
        }
    }

    public String getUrlPattern() {
        return this.pattern;
    }

    public UrlPatternType getUrlPatternType() {
        return this.patternType;
    }

    public int getUrlPatternSlashCount() {
        return this.patternSlashCount;
    }

    public String getUrlPatternSuffix() {
        return this.patternSuffix;
    }

    public String getUrlPatternWildcardPath() {
        return this.wildcardPath;
    }

    public void setDocBase(String docBase2) {
        this.docBase = docBase2;
    }

    public String getDocBase() {
        return this.docBase;
    }

    public void setBasePath(String basePath2) {
        this.basePath = basePath2;
    }

    public String getBasePath() {
        return this.basePath;
    }

    public void setResources(DirContext resources2) {
        this.resources = resources2;
    }

    public DirContext getResources() {
        return this.resources;
    }

    public void setWebappResources(DirContext webappResources2) {
        this.webappResources = webappResources2;
    }

    public DirContext getWebappResources() {
        return this.webappResources;
    }

    public static AlternateDocBase findMatch(String path, List<AlternateDocBase> alternateDocBases) {
        if (alternateDocBases == null) {
            return null;
        }
        AlternateDocBase match = null;
        AlternateDocBase wildcardMatch = null;
        AlternateDocBase extensionMatch = null;
        int maxSlashCountMatch = 0;
        int pathSlashCount = getSlashCount(path);
        int i = 0;
        while (true) {
            if (i >= alternateDocBases.size()) {
                break;
            }
            AlternateDocBase alternateDocBase = alternateDocBases.get(i);
            String pattern2 = alternateDocBase.getUrlPattern();
            int patternSlashCount2 = alternateDocBase.getUrlPatternSlashCount();
            UrlPatternType type = alternateDocBase.getUrlPatternType();
            String wildcardPath2 = alternateDocBase.getUrlPatternWildcardPath();
            if (type == UrlPatternType.EXACT && path.equals(pattern2)) {
                match = alternateDocBase;
                break;
            }
            if (type == UrlPatternType.WILDCARD && pathSlashCount >= patternSlashCount2 && patternSlashCount2 > maxSlashCountMatch && path.startsWith(wildcardPath2)) {
                wildcardMatch = alternateDocBase;
                maxSlashCountMatch = patternSlashCount2;
            } else if (type == UrlPatternType.EXTENSION && path.endsWith(alternateDocBase.getUrlPatternSuffix())) {
                extensionMatch = alternateDocBase;
            }
            i++;
        }
        if (match != null) {
            return match;
        }
        if (wildcardMatch != null) {
            return wildcardMatch;
        }
        return extensionMatch;
    }

    private static int getSlashCount(String s) {
        int count = 0;
        if (s != null) {
            int index = s.indexOf(47);
            while (index >= 0) {
                count++;
                index = s.indexOf(47, index + 1);
            }
        }
        return count;
    }
}
