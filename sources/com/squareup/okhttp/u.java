package com.squareup.okhttp;

import io.netty.handler.ssl.ApplicationProtocolNames;
import java.io.IOException;

/* compiled from: Protocol */
public enum u {
    HTTP_1_0("http/1.0"),
    HTTP_1_1(ApplicationProtocolNames.HTTP_1_1),
    SPDY_3(ApplicationProtocolNames.SPDY_3_1),
    HTTP_2(ApplicationProtocolNames.HTTP_2);
    
    private final String protocol;

    private u(String protocol2) {
        this.protocol = protocol2;
    }

    public static u get(String protocol2) {
        u uVar = HTTP_1_0;
        if (protocol2.equals(uVar.protocol)) {
            return uVar;
        }
        u uVar2 = HTTP_1_1;
        if (protocol2.equals(uVar2.protocol)) {
            return uVar2;
        }
        u uVar3 = HTTP_2;
        if (protocol2.equals(uVar3.protocol)) {
            return uVar3;
        }
        u uVar4 = SPDY_3;
        if (protocol2.equals(uVar4.protocol)) {
            return uVar4;
        }
        throw new IOException("Unexpected protocol: " + protocol2);
    }

    public String toString() {
        return this.protocol;
    }
}
