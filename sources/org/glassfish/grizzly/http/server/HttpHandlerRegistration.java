package org.glassfish.grizzly.http.server;

import org.slf4j.e;

public class HttpHandlerRegistration {
    public static final HttpHandlerRegistration ROOT = builder().contextPath("").urlPattern("/").build();
    private final String contextPath;
    private final String urlPattern;

    public static Builder builder() {
        return new Builder();
    }

    @Deprecated
    public static Builder bulder() {
        return builder();
    }

    public static HttpHandlerRegistration fromString(String mapping) {
        if (mapping == null) {
            return ROOT;
        }
        String contextPath2 = getContextPath(mapping);
        return new HttpHandlerRegistration(contextPath2, getWrapperPath(contextPath2, mapping));
    }

    private HttpHandlerRegistration(String contextPath2, String urlPattern2) {
        this.contextPath = contextPath2;
        this.urlPattern = urlPattern2;
    }

    public String getContextPath() {
        return this.contextPath;
    }

    public String getUrlPattern() {
        return this.urlPattern;
    }

    public int hashCode() {
        return (((5 * 41) + this.contextPath.hashCode()) * 41) + this.urlPattern.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        HttpHandlerRegistration other = (HttpHandlerRegistration) obj;
        if (!this.contextPath.equals(other.contextPath) || !this.urlPattern.equals(other.urlPattern)) {
            return false;
        }
        return true;
    }

    private static String getWrapperPath(String ctx, String mapping) {
        if (mapping.indexOf("*.") > 0) {
            return mapping.substring(mapping.lastIndexOf("/") + 1);
        }
        if (ctx.length() != 0) {
            return mapping.substring(ctx.length());
        }
        if (mapping.startsWith("//")) {
            return mapping.substring(1);
        }
        return mapping;
    }

    private static String getContextPath(String mapping) {
        String ctx;
        int slash = mapping.indexOf("/", 1);
        if (slash != -1) {
            ctx = mapping.substring(0, slash);
        } else {
            ctx = mapping;
        }
        if (ctx.startsWith("/*") || ctx.startsWith(e.ANY_MARKER)) {
            ctx = "";
        }
        if (ctx.equals("/")) {
            return "";
        }
        return ctx;
    }

    public static class Builder {
        private String contextPath;
        private String urlPattern;

        public Builder contextPath(String contextPath2) {
            this.contextPath = contextPath2;
            return this;
        }

        public Builder urlPattern(String urlPattern2) {
            this.urlPattern = urlPattern2;
            return this;
        }

        public HttpHandlerRegistration build() {
            if (this.contextPath == null) {
                this.contextPath = "";
            }
            if (this.urlPattern == null) {
                this.urlPattern = "/";
            }
            return new HttpHandlerRegistration(this.contextPath, this.urlPattern);
        }
    }
}
