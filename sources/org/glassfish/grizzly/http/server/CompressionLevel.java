package org.glassfish.grizzly.http.server;

import org.glassfish.grizzly.http.CompressionConfig;

public enum CompressionLevel implements CompressionConfig.CompressionModeI {
    OFF(CompressionConfig.CompressionMode.OFF),
    ON(CompressionConfig.CompressionMode.ON),
    FORCE(CompressionConfig.CompressionMode.FORCE);
    
    private final CompressionConfig.CompressionMode normalizedLevel;

    private CompressionLevel(CompressionConfig.CompressionMode normalizedLevel2) {
        this.normalizedLevel = normalizedLevel2;
    }

    public CompressionConfig.CompressionMode normalize() {
        return this.normalizedLevel;
    }

    public static CompressionLevel getCompressionLevel(String compression) {
        if ("on".equalsIgnoreCase(compression)) {
            return ON;
        }
        if ("force".equalsIgnoreCase(compression)) {
            return FORCE;
        }
        if ("off".equalsIgnoreCase(compression)) {
            return OFF;
        }
        throw new IllegalArgumentException();
    }
}
