package org.glassfish.grizzly;

import org.glassfish.grizzly.attributes.Attribute;
import org.glassfish.grizzly.attributes.AttributeBuilder;
import org.glassfish.grizzly.attributes.AttributeStorage;
import org.glassfish.grizzly.memory.MemoryManager;

public abstract class AbstractTransformer<K, L> implements Transformer<K, L> {
    protected final AttributeBuilder attributeBuilder;
    private MemoryManager memoryManager;
    protected final Attribute<LastResultAwareState<K, L>> stateAttr;

    /* access modifiers changed from: protected */
    public abstract TransformationResult<K, L> transformImpl(AttributeStorage attributeStorage, K k);

    public AbstractTransformer() {
        AttributeBuilder attributeBuilder2 = Grizzly.DEFAULT_ATTRIBUTE_BUILDER;
        this.attributeBuilder = attributeBuilder2;
        String namePrefix = getNamePrefix();
        this.stateAttr = attributeBuilder2.createAttribute(namePrefix + ".state");
    }

    /* access modifiers changed from: protected */
    public String getNamePrefix() {
        return getClass().getName();
    }

    public final TransformationResult<K, L> transform(AttributeStorage storage, K input) {
        return saveLastResult(storage, transformImpl(storage, input));
    }

    public final TransformationResult<K, L> getLastResult(AttributeStorage storage) {
        LastResultAwareState<K, L> state = this.stateAttr.get(storage);
        if (state != null) {
            return state.getLastResult();
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public final TransformationResult<K, L> saveLastResult(AttributeStorage storage, TransformationResult<K, L> result) {
        obtainStateObject(storage).setLastResult(result);
        return result;
    }

    public void release(AttributeStorage storage) {
        this.stateAttr.remove(storage);
    }

    /* access modifiers changed from: protected */
    public MemoryManager obtainMemoryManager(AttributeStorage storage) {
        MemoryManager memoryManager2 = this.memoryManager;
        if (memoryManager2 != null) {
            return memoryManager2;
        }
        if (storage instanceof Connection) {
            return ((Connection) storage).getMemoryManager();
        }
        return MemoryManager.DEFAULT_MEMORY_MANAGER;
    }

    public MemoryManager getMemoryManager() {
        return this.memoryManager;
    }

    public void setMemoryManager(MemoryManager memoryManager2) {
        this.memoryManager = memoryManager2;
    }

    public static <T> T getValue(AttributeStorage storage, Attribute<T> attribute, T defaultValue) {
        T value = attribute.get(storage);
        if (value != null) {
            return value;
        }
        return defaultValue;
    }

    /* access modifiers changed from: protected */
    public final LastResultAwareState<K, L> obtainStateObject(AttributeStorage storage) {
        LastResultAwareState<K, L> value = this.stateAttr.get(storage);
        if (value != null) {
            return value;
        }
        LastResultAwareState<K, L> value2 = createStateObject();
        this.stateAttr.set(storage, value2);
        return value2;
    }

    /* access modifiers changed from: protected */
    public LastResultAwareState<K, L> createStateObject() {
        return new LastResultAwareState<>();
    }

    public static class LastResultAwareState<K, L> {
        protected TransformationResult<K, L> lastResult;

        public TransformationResult<K, L> getLastResult() {
            return this.lastResult;
        }

        public void setLastResult(TransformationResult<K, L> lastResult2) {
            this.lastResult = lastResult2;
        }
    }
}
