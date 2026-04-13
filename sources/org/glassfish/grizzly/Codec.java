package org.glassfish.grizzly;

public interface Codec<K, L> {
    Transformer<K, L> getDecoder();

    Transformer<L, K> getEncoder();
}
