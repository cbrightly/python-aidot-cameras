package org.glassfish.grizzly.filterchain;

import org.glassfish.grizzly.Codec;

public interface CodecFilter<K, L> extends Filter, Codec<K, L> {
}
