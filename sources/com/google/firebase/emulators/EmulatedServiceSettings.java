package com.google.firebase.emulators;

import androidx.annotation.NonNull;

public final class EmulatedServiceSettings {
    private final String host;
    private final int port;

    public EmulatedServiceSettings(@NonNull String host2, int port2) {
        this.host = host2;
        this.port = port2;
    }

    public String getHost() {
        return this.host;
    }

    public int getPort() {
        return this.port;
    }
}
