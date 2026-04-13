package org.glassfish.tyrus.core;

import java.net.URI;
import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.glassfish.tyrus.spi.UpgradeRequest;

public final class RequestContext extends UpgradeRequest {
    /* access modifiers changed from: private */
    public Map<String, List<String>> headers;
    /* access modifiers changed from: private */
    public final Object httpSession;
    /* access modifiers changed from: private */
    public final Builder.IsUserInRoleDelegate isUserInRoleDelegate;
    /* access modifiers changed from: private */
    public Map<String, List<String>> parameterMap;
    /* access modifiers changed from: private */
    public final String queryString;
    /* access modifiers changed from: private */
    public final String remoteAddr;
    /* access modifiers changed from: private */
    public final URI requestURI;
    /* access modifiers changed from: private */
    public final boolean secure;
    /* access modifiers changed from: private */
    public final Principal userPrincipal;

    private RequestContext(URI requestURI2, String queryString2, Object httpSession2, boolean secure2, Principal userPrincipal2, Builder.IsUserInRoleDelegate IsUserInRoleDelegate, String remoteAddr2, Map<String, List<String>> parameterMap2, Map<String, List<String>> headers2) {
        this.requestURI = requestURI2;
        this.queryString = queryString2;
        this.httpSession = httpSession2;
        this.secure = secure2;
        this.userPrincipal = userPrincipal2;
        this.isUserInRoleDelegate = IsUserInRoleDelegate;
        this.remoteAddr = remoteAddr2;
        this.parameterMap = parameterMap2;
        TreeMap treeMap = new TreeMap(String.CASE_INSENSITIVE_ORDER);
        this.headers = treeMap;
        if (headers2 != null) {
            treeMap.putAll(headers2);
        }
    }

    public Map<String, List<String>> getHeaders() {
        return this.headers;
    }

    public String getHeader(String name) {
        List<String> stringList = this.headers.get(name);
        if (stringList == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (String s : stringList) {
            if (first) {
                first = false;
            } else {
                sb.append(", ");
            }
            sb.append(s);
        }
        return sb.toString();
    }

    public void lock() {
        this.headers = Collections.unmodifiableMap(this.headers);
        this.parameterMap = Collections.unmodifiableMap(this.parameterMap);
    }

    public Principal getUserPrincipal() {
        return this.userPrincipal;
    }

    public URI getRequestURI() {
        return this.requestURI;
    }

    public boolean isUserInRole(String role) {
        Builder.IsUserInRoleDelegate isUserInRoleDelegate2 = this.isUserInRoleDelegate;
        if (isUserInRoleDelegate2 != null) {
            return isUserInRoleDelegate2.isUserInRole(role);
        }
        return false;
    }

    public Object getHttpSession() {
        return this.httpSession;
    }

    public Map<String, List<String>> getParameterMap() {
        return this.parameterMap;
    }

    public String getQueryString() {
        return this.queryString;
    }

    public String getRequestUri() {
        return this.requestURI.toString();
    }

    public boolean isSecure() {
        return this.secure;
    }

    public String getRemoteAddr() {
        return this.remoteAddr;
    }

    public static final class Builder {
        private Map<String, List<String>> headers;
        private Object httpSession;
        private IsUserInRoleDelegate isUserInRoleDelegate;
        private Map<String, List<String>> parameterMap;
        private String queryString;
        private String remoteAddr;
        private URI requestURI;
        private boolean secure;
        private Principal userPrincipal;

        public interface IsUserInRoleDelegate {
            boolean isUserInRole(String str);
        }

        public static Builder create() {
            return new Builder();
        }

        public static Builder create(RequestContext requestContext) {
            Builder builder = new Builder();
            builder.requestURI = requestContext.requestURI;
            builder.queryString = requestContext.queryString;
            builder.httpSession = requestContext.httpSession;
            builder.secure = requestContext.secure;
            builder.userPrincipal = requestContext.userPrincipal;
            builder.isUserInRoleDelegate = requestContext.isUserInRoleDelegate;
            builder.parameterMap = requestContext.parameterMap;
            builder.remoteAddr = requestContext.remoteAddr;
            builder.headers = requestContext.headers;
            return builder;
        }

        public Builder requestURI(URI requestURI2) {
            this.requestURI = requestURI2;
            return this;
        }

        public Builder queryString(String queryString2) {
            this.queryString = queryString2;
            return this;
        }

        public Builder httpSession(Object httpSession2) {
            this.httpSession = httpSession2;
            return this;
        }

        public Builder secure(boolean secure2) {
            this.secure = secure2;
            return this;
        }

        public Builder userPrincipal(Principal principal) {
            this.userPrincipal = principal;
            return this;
        }

        public Builder isUserInRoleDelegate(IsUserInRoleDelegate isUserInRoleDelegate2) {
            this.isUserInRoleDelegate = isUserInRoleDelegate2;
            return this;
        }

        public Builder parameterMap(Map<String, String[]> parameterMap2) {
            if (parameterMap2 != null) {
                this.parameterMap = new HashMap();
                for (Map.Entry<String, String[]> entry : parameterMap2.entrySet()) {
                    this.parameterMap.put(entry.getKey(), Arrays.asList((Object[]) entry.getValue()));
                }
            } else {
                this.parameterMap = null;
            }
            return this;
        }

        public Builder remoteAddr(String remoteAddr2) {
            this.remoteAddr = remoteAddr2;
            return this;
        }

        public RequestContext build() {
            URI uri = this.requestURI;
            String str = this.queryString;
            Object obj = this.httpSession;
            boolean z = this.secure;
            Principal principal = this.userPrincipal;
            IsUserInRoleDelegate isUserInRoleDelegate2 = this.isUserInRoleDelegate;
            String str2 = this.remoteAddr;
            Map map = this.parameterMap;
            if (map == null) {
                map = new HashMap();
            }
            return new RequestContext(uri, str, obj, z, principal, isUserInRoleDelegate2, str2, map, this.headers);
        }
    }
}
