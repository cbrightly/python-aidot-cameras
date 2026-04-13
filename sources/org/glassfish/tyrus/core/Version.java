package org.glassfish.tyrus.core;

import org.glassfish.tyrus.spi.UpgradeRequest;

public enum Version {
    DRAFT17("13") {
        public ProtocolHandler createHandler(boolean mask, MaskingKeyGenerator maskingKeyGenerator) {
            return new ProtocolHandler(mask, maskingKeyGenerator);
        }

        public boolean validate(UpgradeRequest request) {
            return this.wireProtocolVersion.equals(request.getHeader("Sec-WebSocket-Version"));
        }
    };
    
    final String wireProtocolVersion;

    public abstract ProtocolHandler createHandler(boolean z, MaskingKeyGenerator maskingKeyGenerator);

    public abstract boolean validate(UpgradeRequest upgradeRequest);

    private Version(String wireProtocolVersion2) {
        this.wireProtocolVersion = wireProtocolVersion2;
    }

    public String toString() {
        return name();
    }

    public static String getSupportedWireProtocolVersions() {
        StringBuilder sb = new StringBuilder();
        for (Version v : values()) {
            if (v.wireProtocolVersion.length() > 0) {
                sb.append(v.wireProtocolVersion);
                sb.append(", ");
            }
        }
        return sb.substring(0, sb.length() - 2);
    }
}
