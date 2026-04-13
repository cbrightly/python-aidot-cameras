package com.amazonaws.auth;

import com.amazonaws.ClientConfiguration;
import java.util.HashMap;
import java.util.Map;

public class SessionCredentialsProviderFactory {
    private static final Map<Key, STSSessionCredentialsProvider> cache = new HashMap();

    public static final class Key {
        private final String awsAccessKeyId;
        private final String serviceEndpoint;

        public Key(String awsAccessKeyId2, String serviceEndpoint2) {
            this.awsAccessKeyId = awsAccessKeyId2;
            this.serviceEndpoint = serviceEndpoint2;
        }

        public int hashCode() {
            int i = 1 * 31;
            String str = this.awsAccessKeyId;
            int i2 = 0;
            int result = (i + (str == null ? 0 : str.hashCode())) * 31;
            String str2 = this.serviceEndpoint;
            if (str2 != null) {
                i2 = str2.hashCode();
            }
            return result + i2;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Key other = (Key) obj;
            String str = this.awsAccessKeyId;
            if (str == null) {
                if (other.awsAccessKeyId != null) {
                    return false;
                }
            } else if (!str.equals(other.awsAccessKeyId)) {
                return false;
            }
            String str2 = this.serviceEndpoint;
            if (str2 == null) {
                if (other.serviceEndpoint != null) {
                    return false;
                }
            } else if (!str2.equals(other.serviceEndpoint)) {
                return false;
            }
            return true;
        }
    }

    public static synchronized STSSessionCredentialsProvider getSessionCredentialsProvider(AWSCredentials longTermCredentials, String serviceEndpoint, ClientConfiguration stsClientConfiguration) {
        STSSessionCredentialsProvider sTSSessionCredentialsProvider;
        synchronized (SessionCredentialsProviderFactory.class) {
            Key key = new Key(longTermCredentials.getAWSAccessKeyId(), serviceEndpoint);
            Map<Key, STSSessionCredentialsProvider> map = cache;
            if (!map.containsKey(key)) {
                map.put(key, new STSSessionCredentialsProvider(longTermCredentials, stsClientConfiguration));
            }
            sTSSessionCredentialsProvider = map.get(key);
        }
        return sTSSessionCredentialsProvider;
    }
}
