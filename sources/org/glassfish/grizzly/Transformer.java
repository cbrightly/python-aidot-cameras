package org.glassfish.grizzly;

import org.glassfish.grizzly.attributes.AttributeStorage;

public interface Transformer<K, L> {
    TransformationResult<K, L> getLastResult(AttributeStorage attributeStorage);

    String getName();

    boolean hasInputRemaining(AttributeStorage attributeStorage, K k);

    void release(AttributeStorage attributeStorage);

    TransformationResult<K, L> transform(AttributeStorage attributeStorage, K k);
}
