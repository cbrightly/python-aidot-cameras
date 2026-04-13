package org.glassfish.grizzly.attributes;

import org.glassfish.grizzly.utils.NullaryFunction;

public interface IndexedAttributeAccessor {
    Object getAttribute(int i);

    Object getAttribute(int i, NullaryFunction nullaryFunction);

    Object removeAttribute(int i);

    void setAttribute(int i, Object obj);
}
