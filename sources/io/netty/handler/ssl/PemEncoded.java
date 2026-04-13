package io.netty.handler.ssl;

import io.netty.buffer.ByteBufHolder;

public interface PemEncoded extends ByteBufHolder {
    PemEncoded copy();

    PemEncoded duplicate();

    boolean isSensitive();

    PemEncoded retain();

    PemEncoded retain(int i);
}
