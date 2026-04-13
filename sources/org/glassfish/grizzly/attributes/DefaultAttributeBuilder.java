package org.glassfish.grizzly.attributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.glassfish.grizzly.utils.NullaryFunction;

public class DefaultAttributeBuilder implements AttributeBuilder {
    protected final List<Attribute> attributes = new ArrayList();
    protected final Map<String, Attribute> name2Attribute = new HashMap();

    public synchronized <T> Attribute<T> createAttribute(String name) {
        return createAttribute(name, (Object) null);
    }

    public synchronized <T> Attribute<T> createAttribute(String name, T defaultValue) {
        Attribute<T> attribute;
        attribute = this.name2Attribute.get(name);
        if (attribute == null) {
            attribute = new Attribute<>((AttributeBuilder) this, name, this.attributes.size(), defaultValue);
            this.attributes.add(attribute);
            this.name2Attribute.put(name, attribute);
        }
        return attribute;
    }

    public synchronized <T> Attribute<T> createAttribute(String name, NullaryFunction<T> initializer) {
        Attribute<T> attribute;
        attribute = this.name2Attribute.get(name);
        if (attribute == null) {
            attribute = new Attribute<>((AttributeBuilder) this, name, this.attributes.size(), initializer);
            this.attributes.add(attribute);
            this.name2Attribute.put(name, attribute);
        }
        return attribute;
    }

    public <T> Attribute<T> createAttribute(String name, final NullaryFunction<T> initializer) {
        return createAttribute(name, initializer == null ? null : new NullaryFunction<T>() {
            public T evaluate() {
                return initializer.evaluate();
            }
        });
    }

    public AttributeHolder createSafeAttributeHolder() {
        return new IndexedAttributeHolder(this);
    }

    public AttributeHolder createUnsafeAttributeHolder() {
        return new UnsafeAttributeHolder(this);
    }

    /* access modifiers changed from: protected */
    public Attribute getAttributeByName(String name) {
        return this.name2Attribute.get(name);
    }

    /* access modifiers changed from: protected */
    public Attribute getAttributeByIndex(int index) {
        return this.attributes.get(index);
    }
}
