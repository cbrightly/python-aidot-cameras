package org.glassfish.tyrus.client.auth;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import org.glassfish.tyrus.core.Beta;

@Beta
public class AuthConfig {
    static final String BASIC = "Basic";
    static final Charset CHARACTER_SET = Charset.forName("iso-8859-1");
    static final String DIGEST = "Digest";
    private final Map<String, Authenticator> authenticators;

    private AuthConfig(Map<String, Authenticator> authenticators2) {
        TreeMap<String, Authenticator> map = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        map.putAll(authenticators2);
        this.authenticators = Collections.unmodifiableMap(map);
    }

    public Map<String, Authenticator> getAuthenticators() {
        return this.authenticators;
    }

    public static Builder builder() {
        return Builder.create();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AuthConfig{");
        boolean first = true;
        for (Map.Entry<String, Authenticator> authenticator : this.authenticators.entrySet()) {
            if (first) {
                first = false;
            } else {
                sb.append(", ");
            }
            sb.append(authenticator.getKey());
            sb.append("->");
            sb.append(authenticator.getValue().getClass().getName());
        }
        sb.append("}");
        return sb.toString();
    }

    public static final class Builder {
        private final Map<String, Authenticator> authenticators;

        private Builder() {
            TreeMap treeMap = new TreeMap(String.CASE_INSENSITIVE_ORDER);
            this.authenticators = treeMap;
            treeMap.put(AuthConfig.BASIC, new BasicAuthenticator());
            treeMap.put(AuthConfig.DIGEST, new DigestAuthenticator());
        }

        public static Builder create() {
            return new Builder();
        }

        public final Builder registerAuthProvider(String scheme, Authenticator authenticator) {
            this.authenticators.put(scheme, authenticator);
            return this;
        }

        public final Builder disableProvidedBasicAuth() {
            if (this.authenticators.get(AuthConfig.BASIC) != null && (this.authenticators.get(AuthConfig.BASIC) instanceof BasicAuthenticator)) {
                this.authenticators.remove(AuthConfig.BASIC);
            }
            return this;
        }

        public final Builder disableProvidedDigestAuth() {
            if (this.authenticators.get(AuthConfig.DIGEST) != null && (this.authenticators.get(AuthConfig.DIGEST) instanceof DigestAuthenticator)) {
                this.authenticators.remove(AuthConfig.DIGEST);
            }
            return this;
        }

        public AuthConfig build() {
            return new AuthConfig(this.authenticators);
        }
    }
}
