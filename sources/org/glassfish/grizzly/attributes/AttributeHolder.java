package org.glassfish.grizzly.attributes;

import java.util.Set;
import org.glassfish.grizzly.utils.NullaryFunction;

public interface AttributeHolder {
    void clear();

    void copyFrom(AttributeHolder attributeHolder);

    void copyTo(AttributeHolder attributeHolder);

    Object getAttribute(String str);

    Object getAttribute(String str, NullaryFunction nullaryFunction);

    AttributeBuilder getAttributeBuilder();

    Set<String> getAttributeNames();

    IndexedAttributeAccessor getIndexedAttributeAccessor();

    void recycle();

    Object removeAttribute(String str);

    void setAttribute(String str, Object obj);
}
