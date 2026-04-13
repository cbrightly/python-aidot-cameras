package org.glassfish.grizzly.attributes;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.glassfish.grizzly.utils.NullaryFunction;

public final class IndexedAttributeHolder implements AttributeHolder {
    protected final DefaultAttributeBuilder attributeBuilder;
    /* access modifiers changed from: private */
    public volatile int count;
    protected final IndexedAttributeAccessor indexedAttributeAccessor;
    /* access modifiers changed from: private */
    public Snapshot state;
    /* access modifiers changed from: private */
    public final Object sync = new Object();

    static /* synthetic */ int access$408(IndexedAttributeHolder x0) {
        int i = x0.count;
        x0.count = i + 1;
        return i;
    }

    @Deprecated
    public IndexedAttributeHolder(AttributeBuilder attributeBuilder2) {
        this.attributeBuilder = (DefaultAttributeBuilder) attributeBuilder2;
        this.state = new Snapshot(new Object[4], new int[]{-1, -1, -1, -1}, 0);
        this.indexedAttributeAccessor = new IndexedAttributeAccessorImpl();
    }

    public Object getAttribute(String name) {
        return getAttribute(name, (NullaryFunction) null);
    }

    public Object getAttribute(String name, NullaryFunction initializer) {
        Attribute attribute = this.attributeBuilder.getAttributeByName(name);
        if (attribute != null) {
            return this.indexedAttributeAccessor.getAttribute(attribute.index(), initializer);
        }
        if (initializer != null) {
            return initializer;
        }
        return null;
    }

    public void setAttribute(String name, Object value) {
        Attribute attribute = this.attributeBuilder.getAttributeByName(name);
        if (attribute == null) {
            attribute = this.attributeBuilder.createAttribute(name);
        }
        this.indexedAttributeAccessor.setAttribute(attribute.index(), value);
    }

    public Object removeAttribute(String name) {
        Attribute attribute = this.attributeBuilder.getAttributeByName(name);
        if (attribute != null) {
            return this.indexedAttributeAccessor.removeAttribute(attribute.index());
        }
        return null;
    }

    public Set<String> getAttributeNames() {
        if (this.count == 0) {
            return Collections.emptySet();
        }
        Set<String> result = new HashSet<>();
        Snapshot stateNow = this.state;
        int localSize = stateNow.size;
        Object[] localAttributeValues = stateNow.values;
        for (int i = 0; i < localSize; i++) {
            if (localAttributeValues[i] != null) {
                result.add(this.attributeBuilder.getAttributeByIndex(i).name());
            }
        }
        return result;
    }

    public void copyFrom(AttributeHolder srcAttributes) {
        if (srcAttributes instanceof IndexedAttributeHolder) {
            Snapshot stateNow = this.state;
            Snapshot srcState = ((IndexedAttributeHolder) srcAttributes).state;
            int[] newI2v = stateNow.i2v;
            if (newI2v.length < srcState.i2v.length) {
                newI2v = Arrays.copyOf(srcState.i2v, srcState.i2v.length);
            } else {
                System.arraycopy(srcState.i2v, 0, newI2v, 0, srcState.i2v.length);
                for (int i = srcState.i2v.length; i < newI2v.length; i++) {
                    newI2v[i] = -1;
                }
            }
            Object[] newValues = stateNow.values;
            if (newValues.length < srcState.size) {
                newValues = Arrays.copyOf(srcState.values, srcState.size);
            } else {
                System.arraycopy(srcState.values, 0, newValues, 0, srcState.size);
                for (int i2 = srcState.size; i2 < stateNow.size; i2++) {
                    newValues[i2] = null;
                }
            }
            this.state = new Snapshot(newValues, newI2v, srcState.size);
            this.count++;
            return;
        }
        clear();
        Set<String> names = srcAttributes.getAttributeNames();
        if (!names.isEmpty()) {
            for (String name : names) {
                setAttribute(name, srcAttributes.getAttribute(name));
            }
        }
    }

    public void copyTo(AttributeHolder dstAttributes) {
        if (this.count == 0) {
            dstAttributes.clear();
        } else if (dstAttributes instanceof IndexedAttributeHolder) {
            IndexedAttributeHolder iah = (IndexedAttributeHolder) dstAttributes;
            Snapshot stateNow = this.state;
            Snapshot dstState = iah.state;
            int[] newI2v = dstState.i2v;
            if (newI2v.length < stateNow.i2v.length) {
                newI2v = Arrays.copyOf(stateNow.i2v, stateNow.i2v.length);
            } else {
                System.arraycopy(stateNow.i2v, 0, newI2v, 0, stateNow.i2v.length);
                for (int i = stateNow.i2v.length; i < newI2v.length; i++) {
                    newI2v[i] = -1;
                }
            }
            Object[] newValues = dstState.values;
            if (newValues.length < stateNow.size) {
                newValues = Arrays.copyOf(stateNow.values, stateNow.size);
            } else {
                System.arraycopy(stateNow.values, 0, newValues, 0, stateNow.size);
                for (int i2 = stateNow.size; i2 < dstState.size; i2++) {
                    newValues[i2] = null;
                }
            }
            iah.state = new Snapshot(newValues, newI2v, stateNow.size);
            iah.count++;
        } else {
            dstAttributes.clear();
            Snapshot stateNow2 = this.state;
            int localSize = stateNow2.size;
            Object[] localAttributeValues = stateNow2.values;
            for (int i3 = 0; i3 < localSize; i3++) {
                Object value = localAttributeValues[i3];
                if (value != null) {
                    dstAttributes.setAttribute(this.attributeBuilder.getAttributeByIndex(i3).name(), value);
                }
            }
        }
    }

    public void recycle() {
        if (this.count != 0) {
            Snapshot stateNow = this.state;
            for (int i = 0; i < stateNow.size; i++) {
                stateNow.values[i] = null;
            }
            return;
        }
        this.count = 0;
    }

    public void clear() {
        if (this.count != 0) {
            this.count = 0;
            for (int i = 0; i < this.state.size; i++) {
                this.state.values[i] = null;
            }
        }
    }

    public AttributeBuilder getAttributeBuilder() {
        return this.attributeBuilder;
    }

    public IndexedAttributeAccessor getIndexedAttributeAccessor() {
        return this.indexedAttributeAccessor;
    }

    public final class IndexedAttributeAccessorImpl implements IndexedAttributeAccessor {
        protected IndexedAttributeAccessorImpl() {
        }

        public Object getAttribute(int index) {
            return getAttribute(index, (NullaryFunction) null);
        }

        public Object getAttribute(int index, NullaryFunction initializer) {
            Object value = weakGet(index);
            if (value == null && initializer != null) {
                synchronized (IndexedAttributeHolder.this.sync) {
                    value = weakGet(index);
                    if (value == null) {
                        value = initializer.evaluate();
                        setAttribute(index, value);
                    }
                }
            }
            return value;
        }

        private Object weakGet(int index) {
            int idx;
            if (IndexedAttributeHolder.this.count == 0) {
                return null;
            }
            Snapshot stateNow = IndexedAttributeHolder.this.state;
            if (index >= stateNow.i2v.length || (idx = stateNow.i2v[index]) == -1 || idx >= stateNow.size) {
                return null;
            }
            return stateNow.values[idx];
        }

        public void setAttribute(int index, Object value) {
            Snapshot stateNow = IndexedAttributeHolder.this.state;
            if (index < stateNow.i2v.length) {
                int i = stateNow.i2v[index];
                int mappedIdx = i;
                if (i != -1) {
                    stateNow.values[mappedIdx] = value;
                    IndexedAttributeHolder.access$408(IndexedAttributeHolder.this);
                    return;
                }
            }
            if (value != null) {
                setSync(index, value);
            }
        }

        public Object removeAttribute(int index) {
            Snapshot stateNow = IndexedAttributeHolder.this.state;
            if (index >= stateNow.i2v.length) {
                return null;
            }
            int i = stateNow.i2v[index];
            int mappedIdx = i;
            if (i == -1) {
                return null;
            }
            Object oldValue = stateNow.values[mappedIdx];
            stateNow.values[mappedIdx] = null;
            IndexedAttributeHolder.access$408(IndexedAttributeHolder.this);
            return oldValue;
        }

        private void setSync(int index, Object value) {
            int[] newI2v;
            synchronized (IndexedAttributeHolder.this.sync) {
                Snapshot stateNow = IndexedAttributeHolder.this.state;
                if (index < stateNow.i2v.length) {
                    int i = stateNow.i2v[index];
                    int mappedIdx = i;
                    if (i == -1 || mappedIdx >= stateNow.size) {
                        newI2v = stateNow.i2v;
                    } else {
                        stateNow.values[mappedIdx] = value;
                        IndexedAttributeHolder.access$408(IndexedAttributeHolder.this);
                        return;
                    }
                } else {
                    newI2v = IndexedAttributeHolder.ensureSize(stateNow.i2v, index + 1);
                }
                int mappedIdx2 = stateNow.size;
                int newSize = mappedIdx2 + 1;
                Object[] newValues = mappedIdx2 < stateNow.values.length ? stateNow.values : IndexedAttributeHolder.ensureSize(stateNow.values, newSize);
                newValues[mappedIdx2] = value;
                newI2v[index] = mappedIdx2;
                Snapshot unused = IndexedAttributeHolder.this.state = new Snapshot(newValues, newI2v, newSize);
                IndexedAttributeHolder.access$408(IndexedAttributeHolder.this);
            }
        }
    }

    /* access modifiers changed from: private */
    public static Object[] ensureSize(Object[] array, int size) {
        int arrayLength = array.length;
        return Arrays.copyOf(array, Math.max(arrayLength + (size - arrayLength), ((arrayLength * 3) / 2) + 1));
    }

    /* access modifiers changed from: private */
    public static int[] ensureSize(int[] array, int size) {
        int arrayLength = array.length;
        int newLength = Math.max(arrayLength + (size - arrayLength), ((arrayLength * 3) / 2) + 1);
        int[] newArray = Arrays.copyOf(array, newLength);
        Arrays.fill(newArray, array.length, newLength, -1);
        return newArray;
    }

    public static class Snapshot {
        /* access modifiers changed from: private */
        public final int[] i2v;
        /* access modifiers changed from: private */
        public final int size;
        /* access modifiers changed from: private */
        public final Object[] values;

        public Snapshot(Object[] values2, int[] i2v2, int size2) {
            this.values = values2;
            this.i2v = i2v2;
            this.size = size2;
        }
    }
}
