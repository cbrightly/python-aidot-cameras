package org.glassfish.grizzly.attributes;

import org.glassfish.grizzly.utils.NullaryFunction;

public interface AttributeBuilder {
    public static final AttributeBuilder DEFAULT_ATTRIBUTE_BUILDER = AttributeBuilderInitializer.initBuilder();

    <T> Attribute<T> createAttribute(String str);

    <T> Attribute<T> createAttribute(String str, T t);

    @Deprecated
    <T> Attribute<T> createAttribute(String str, NullaryFunction<T> nullaryFunction);

    <T> Attribute<T> createAttribute(String str, NullaryFunction<T> nullaryFunction);

    AttributeHolder createSafeAttributeHolder();

    AttributeHolder createUnsafeAttributeHolder();
}
