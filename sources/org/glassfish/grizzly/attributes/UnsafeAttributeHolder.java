package org.glassfish.grizzly.attributes;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.glassfish.grizzly.utils.NullaryFunction;

public final class UnsafeAttributeHolder implements AttributeHolder {
    final DefaultAttributeBuilder attributeBuilder;
    /* access modifiers changed from: private */
    public final Holder h1 = new Holder();
    /* access modifiers changed from: private */
    public final Holder h2 = new Holder();
    /* access modifiers changed from: private */
    public final Holder h3 = new Holder();
    /* access modifiers changed from: private */
    public final Holder h4 = new Holder();
    final IndexedAttributeAccessorImpl indexedAttributeAccessor;
    /* access modifiers changed from: private */
    public boolean isSet;
    /* access modifiers changed from: private */
    public Map<Integer, Object> valueMap;

    UnsafeAttributeHolder(DefaultAttributeBuilder attributeBuilder2) {
        this.attributeBuilder = attributeBuilder2;
        this.indexedAttributeAccessor = new IndexedAttributeAccessorImpl();
    }

    public Object getAttribute(String name) {
        return getAttribute(name, (NullaryFunction) null);
    }

    public Object getAttribute(String name, NullaryFunction initializer) {
        if (!this.isSet && initializer == null) {
            return null;
        }
        Attribute attribute = this.attributeBuilder.getAttributeByName(name);
        if (attribute != null) {
            return this.indexedAttributeAccessor.getAttribute(attribute, initializer);
        }
        if (initializer != null) {
            return initializer.evaluate();
        }
        return null;
    }

    public void setAttribute(String name, Object value) {
        Attribute attribute = this.attributeBuilder.getAttributeByName(name);
        if (attribute == null) {
            attribute = this.attributeBuilder.createAttribute(name);
        }
        Object unused = this.indexedAttributeAccessor.setAttribute(attribute, value);
    }

    public Object removeAttribute(String name) {
        Attribute attribute;
        if (this.isSet && (attribute = this.attributeBuilder.getAttributeByName(name)) != null) {
            return this.indexedAttributeAccessor.removeAttribute(attribute);
        }
        return null;
    }

    public Set<String> getAttributeNames() {
        if (!this.isSet) {
            return null;
        }
        Set<String> tmpSet = new HashSet<>(4);
        Holder holder = this.h1;
        if (holder.isSet && holder.value != null) {
            tmpSet.add(this.attributeBuilder.getAttributeByIndex(holder.idx).name());
        }
        Holder holder2 = this.h2;
        if (holder2.isSet && holder2.value != null) {
            tmpSet.add(this.attributeBuilder.getAttributeByIndex(holder2.idx).name());
        }
        Holder holder3 = this.h3;
        if (holder3.isSet && holder3.value != null) {
            tmpSet.add(this.attributeBuilder.getAttributeByIndex(holder3.idx).name());
        }
        Holder holder4 = this.h4;
        if (holder4.isSet && holder4.value != null) {
            tmpSet.add(this.attributeBuilder.getAttributeByIndex(holder4.idx).name());
        }
        Map<Integer, Object> map = this.valueMap;
        if (map != null) {
            for (Integer idx : map.keySet()) {
                tmpSet.add(this.attributeBuilder.getAttributeByIndex(idx.intValue()).name());
            }
        }
        return tmpSet;
    }

    public void clear() {
        if (this.isSet) {
            this.isSet = false;
            this.h1.clear();
            this.h2.clear();
            this.h3.clear();
            this.h4.clear();
            this.valueMap = null;
        }
    }

    public void recycle() {
        clear();
    }

    public AttributeBuilder getAttributeBuilder() {
        return this.attributeBuilder;
    }

    public IndexedAttributeAccessor getIndexedAttributeAccessor() {
        return this.indexedAttributeAccessor;
    }

    public void copyFrom(AttributeHolder srcAttributes) {
        if (srcAttributes == null) {
            throw new NullPointerException("srcAttributes can't be null");
        } else if (srcAttributes instanceof UnsafeAttributeHolder) {
            UnsafeAttributeHolder srcUnsafe = (UnsafeAttributeHolder) srcAttributes;
            if (!srcUnsafe.isSet) {
                clear();
                return;
            }
            this.isSet = true;
            this.h1.copyFrom(srcUnsafe.h1);
            this.h2.copyFrom(srcUnsafe.h2);
            this.h3.copyFrom(srcUnsafe.h3);
            this.h4.copyFrom(srcUnsafe.h4);
            if (this.valueMap != null || srcUnsafe.valueMap != null) {
                MapperAccessor.copy(srcUnsafe, this);
            }
        } else {
            clear();
            for (String name : srcAttributes.getAttributeNames()) {
                setAttribute(name, srcAttributes.getAttribute(name));
            }
        }
    }

    public void copyTo(AttributeHolder dstAttributes) {
        if (dstAttributes == null) {
            throw new NullPointerException("dstAttributes can't be null");
        } else if (!this.isSet) {
            dstAttributes.clear();
        } else if (dstAttributes instanceof UnsafeAttributeHolder) {
            UnsafeAttributeHolder dstUnsafe = (UnsafeAttributeHolder) dstAttributes;
            dstUnsafe.isSet = true;
            dstUnsafe.h1.copyFrom(this.h1);
            dstUnsafe.h2.copyFrom(this.h2);
            dstUnsafe.h3.copyFrom(this.h3);
            dstUnsafe.h4.copyFrom(this.h4);
            if (this.valueMap != null || dstUnsafe.valueMap != null) {
                MapperAccessor.copy(this, dstUnsafe);
            }
        } else {
            dstAttributes.clear();
            for (String name : getAttributeNames()) {
                dstAttributes.setAttribute(name, getAttribute(name));
            }
        }
    }

    public final class IndexedAttributeAccessorImpl implements IndexedAttributeAccessor {
        protected IndexedAttributeAccessorImpl() {
        }

        public Object getAttribute(int index) {
            return getAttribute(index, (NullaryFunction) null);
        }

        public Object getAttribute(int index, NullaryFunction initializer) {
            if (UnsafeAttributeHolder.this.isSet || initializer != null) {
                return getAttribute(UnsafeAttributeHolder.this.attributeBuilder.getAttributeByIndex(index), initializer);
            }
            return null;
        }

        public void setAttribute(int index, Object value) {
            setAttribute(UnsafeAttributeHolder.this.attributeBuilder.getAttributeByIndex(index), value);
        }

        public Object removeAttribute(int index) {
            return removeAttribute(UnsafeAttributeHolder.this.attributeBuilder.getAttributeByIndex(index));
        }

        /* access modifiers changed from: private */
        public Object getAttribute(Attribute attribute, NullaryFunction initializer) {
            int idx = attribute.index();
            Holder h = holderByIdx(idx);
            if (h != null) {
                if (h.value == null && initializer != null) {
                    h.value = initializer.evaluate();
                }
                return h.value;
            }
            Object value = UnsafeAttributeHolder.this.valueMap != null ? MapperAccessor.getValue(UnsafeAttributeHolder.this, Integer.valueOf(idx)) : null;
            if (value != null || initializer == null) {
                return value;
            }
            Object value2 = initializer.evaluate();
            setAttribute(attribute, value2);
            return value2;
        }

        /* access modifiers changed from: private */
        public Object setAttribute(Attribute attribute, Object value) {
            if (!UnsafeAttributeHolder.this.isSet) {
                if (value != null) {
                    boolean unused = UnsafeAttributeHolder.this.isSet = true;
                    UnsafeAttributeHolder.this.h1.set(attribute.index(), value);
                }
                return null;
            }
            boolean unused2 = UnsafeAttributeHolder.this.isSet = true;
            int idx = attribute.index();
            Holder h = holderByIdx(idx);
            if (h != null) {
                return h.set(idx, value);
            }
            if (UnsafeAttributeHolder.this.valueMap != null && UnsafeAttributeHolder.this.valueMap.get(Integer.valueOf(idx)) != value) {
                return MapperAccessor.setValue(UnsafeAttributeHolder.this, Integer.valueOf(idx), value);
            }
            if (value == null) {
                return null;
            }
            Holder h2 = emptyHolder();
            if (h2 != null) {
                h2.set(idx, value);
                return null;
            }
            Holder h3 = nullHolder();
            if (h3 == null) {
                return MapperAccessor.setValue(UnsafeAttributeHolder.this, Integer.valueOf(idx), value);
            }
            h3.set(idx, value);
            return null;
        }

        /* access modifiers changed from: private */
        public Object removeAttribute(Attribute attribute) {
            return setAttribute(attribute, (Object) null);
        }

        private Holder holderByIdx(int idx) {
            if (UnsafeAttributeHolder.this.h1.is(idx)) {
                return UnsafeAttributeHolder.this.h1;
            }
            if (UnsafeAttributeHolder.this.h2.is(idx)) {
                return UnsafeAttributeHolder.this.h2;
            }
            if (UnsafeAttributeHolder.this.h3.is(idx)) {
                return UnsafeAttributeHolder.this.h3;
            }
            if (UnsafeAttributeHolder.this.h4.is(idx)) {
                return UnsafeAttributeHolder.this.h4;
            }
            return null;
        }

        private Holder emptyHolder() {
            if (!UnsafeAttributeHolder.this.h1.isSet) {
                return UnsafeAttributeHolder.this.h1;
            }
            if (!UnsafeAttributeHolder.this.h2.isSet) {
                return UnsafeAttributeHolder.this.h2;
            }
            if (!UnsafeAttributeHolder.this.h3.isSet) {
                return UnsafeAttributeHolder.this.h3;
            }
            if (!UnsafeAttributeHolder.this.h4.isSet) {
                return UnsafeAttributeHolder.this.h4;
            }
            return null;
        }

        private Holder nullHolder() {
            if (UnsafeAttributeHolder.this.h1.value == null) {
                return UnsafeAttributeHolder.this.h1;
            }
            if (UnsafeAttributeHolder.this.h2.value == null) {
                return UnsafeAttributeHolder.this.h2;
            }
            if (UnsafeAttributeHolder.this.h3.value == null) {
                return UnsafeAttributeHolder.this.h3;
            }
            if (UnsafeAttributeHolder.this.h4.value == null) {
                return UnsafeAttributeHolder.this.h4;
            }
            return null;
        }
    }

    public static final class Holder {
        int idx;
        boolean isSet;
        Object value;

        private Holder() {
        }

        /* access modifiers changed from: package-private */
        public Object set(int idx2, Object value2) {
            Object oldValue = this.value;
            this.idx = idx2;
            this.value = value2;
            this.isSet = true;
            return oldValue;
        }

        /* access modifiers changed from: package-private */
        public void clear() {
            if (this.isSet) {
                this.idx = -1;
                this.value = null;
                this.isSet = false;
            }
        }

        /* access modifiers changed from: private */
        public boolean is(int idx2) {
            return this.isSet && this.idx == idx2;
        }

        /* access modifiers changed from: private */
        public void copyFrom(Holder src) {
            this.isSet = src.isSet;
            this.idx = src.idx;
            this.value = src.value;
        }
    }

    public static final class MapperAccessor {
        private MapperAccessor() {
        }

        /* access modifiers changed from: private */
        public static Object getValue(UnsafeAttributeHolder holder, Integer idx) {
            return holder.valueMap.get(idx);
        }

        /* access modifiers changed from: private */
        public static Object setValue(UnsafeAttributeHolder holder, Integer idx, Object value) {
            if (value != null) {
                if (holder.valueMap == null) {
                    Map unused = holder.valueMap = new HashMap(4);
                }
                return holder.valueMap.put(idx, value);
            } else if (holder.valueMap != null) {
                return holder.valueMap.remove(idx);
            } else {
                return null;
            }
        }

        /* access modifiers changed from: private */
        public static void copy(UnsafeAttributeHolder src, UnsafeAttributeHolder dst) {
            if (src.valueMap != null) {
                if (dst.valueMap == null) {
                    Map unused = dst.valueMap = new HashMap(src.valueMap.size());
                } else {
                    dst.valueMap.clear();
                }
                dst.valueMap.putAll(src.valueMap);
                return;
            }
            Map unused2 = dst.valueMap = null;
        }
    }
}
