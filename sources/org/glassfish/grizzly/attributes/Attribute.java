package org.glassfish.grizzly.attributes;

import org.glassfish.grizzly.utils.NullaryFunction;

public final class Attribute<T> {
    private final int attributeIndex;
    private final AttributeBuilder builder;
    private final NullaryFunction<T> initializer;
    private final String name;

    public String toString() {
        return "Attribute[" + this.name + ':' + this.attributeIndex + ']';
    }

    protected Attribute(AttributeBuilder builder2, String name2, int index, final T defaultValue) {
        this(builder2, name2, index, new NullaryFunction<T>() {
            public T evaluate() {
                return defaultValue;
            }
        });
    }

    protected Attribute(AttributeBuilder builder2, String name2, int index, NullaryFunction<T> initializer2) {
        this.builder = builder2;
        this.name = name2;
        this.attributeIndex = index;
        this.initializer = initializer2;
    }

    public T peek(AttributeHolder attributeHolder) {
        return get0(attributeHolder, (NullaryFunction) null);
    }

    public T peek(AttributeStorage storage) {
        AttributeHolder holder = storage.getAttributes();
        if (holder != null) {
            return peek(holder);
        }
        return null;
    }

    public T get(AttributeHolder attributeHolder) {
        return get0(attributeHolder, this.initializer);
    }

    public T get(AttributeStorage storage) {
        return get(storage.getAttributes());
    }

    public void set(AttributeHolder attributeHolder, T value) {
        IndexedAttributeAccessor indexedAccessor = attributeHolder.getIndexedAttributeAccessor();
        if (indexedAccessor != null) {
            indexedAccessor.setAttribute(this.attributeIndex, value);
        } else {
            attributeHolder.setAttribute(this.name, value);
        }
    }

    public void set(AttributeStorage storage, T value) {
        set(storage.getAttributes(), value);
    }

    public T remove(AttributeHolder attributeHolder) {
        IndexedAttributeAccessor indexedAccessor = attributeHolder.getIndexedAttributeAccessor();
        return indexedAccessor != null ? indexedAccessor.removeAttribute(this.attributeIndex) : attributeHolder.removeAttribute(this.name);
    }

    public T remove(AttributeStorage storage) {
        AttributeHolder holder = storage.getAttributes();
        if (holder != null) {
            return remove(holder);
        }
        return null;
    }

    public boolean isSet(AttributeHolder attributeHolder) {
        return get0(attributeHolder, (NullaryFunction) null) != null;
    }

    public boolean isSet(AttributeStorage storage) {
        AttributeHolder holder = storage.getAttributes();
        return holder != null && isSet(holder);
    }

    public String name() {
        return this.name;
    }

    public int index() {
        return this.attributeIndex;
    }

    private T get0(AttributeHolder attributeHolder, NullaryFunction<T> initializer2) {
        IndexedAttributeAccessor indexedAccessor = attributeHolder.getIndexedAttributeAccessor();
        return indexedAccessor != null ? indexedAccessor.getAttribute(this.attributeIndex, initializer2) : attributeHolder.getAttribute(this.name, initializer2);
    }
}
