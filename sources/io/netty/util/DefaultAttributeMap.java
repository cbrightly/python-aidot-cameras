package io.netty.util;

import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class DefaultAttributeMap implements AttributeMap {
    private static final int BUCKET_SIZE = 4;
    private static final int MASK = 3;
    private static final AtomicReferenceFieldUpdater<DefaultAttributeMap, AtomicReferenceArray> updater = AtomicReferenceFieldUpdater.newUpdater(DefaultAttributeMap.class, AtomicReferenceArray.class, "attributes");
    private volatile AtomicReferenceArray<DefaultAttribute<?>> attributes;

    public <T> Attribute<T> attr(AttributeKey<T> key) {
        if (key != null) {
            AtomicReferenceArray<DefaultAttribute<?>> attributes2 = this.attributes;
            if (attributes2 == null) {
                attributes2 = new AtomicReferenceArray<>(4);
                if (!updater.compareAndSet(this, (Object) null, attributes2)) {
                    attributes2 = this.attributes;
                }
            }
            int i = index(key);
            DefaultAttribute<?> head = attributes2.get(i);
            if (head == null) {
                DefaultAttribute<?> head2 = new DefaultAttribute<>();
                DefaultAttribute<T> attr = new DefaultAttribute<>(head2, key);
                DefaultAttribute unused = head2.next = attr;
                DefaultAttribute unused2 = attr.prev = head2;
                if (attributes2.compareAndSet(i, (Object) null, head2)) {
                    return attr;
                }
                head = attributes2.get(i);
            }
            synchronized (head) {
                DefaultAttribute<?> curr = head;
                while (true) {
                    DefaultAttribute<?> next = curr.next;
                    if (next == null) {
                        DefaultAttribute<T> attr2 = new DefaultAttribute<>(head, key);
                        DefaultAttribute unused3 = curr.next = attr2;
                        DefaultAttribute unused4 = attr2.prev = curr;
                        return attr2;
                    } else if (next.key == key && !next.removed) {
                        return next;
                    } else {
                        curr = next;
                    }
                }
            }
        } else {
            throw new NullPointerException(CacheEntity.KEY);
        }
    }

    private static int index(AttributeKey<?> key) {
        return key.id() & 3;
    }

    public static final class DefaultAttribute<T> extends AtomicReference<T> implements Attribute<T> {
        private static final long serialVersionUID = -2661411462200283011L;
        private final DefaultAttribute<?> head;
        /* access modifiers changed from: private */
        public final AttributeKey<T> key;
        /* access modifiers changed from: private */
        public DefaultAttribute<?> next;
        /* access modifiers changed from: private */
        public DefaultAttribute<?> prev;
        /* access modifiers changed from: private */
        public volatile boolean removed;

        DefaultAttribute(DefaultAttribute<?> head2, AttributeKey<T> key2) {
            this.head = head2;
            this.key = key2;
        }

        DefaultAttribute() {
            this.head = this;
            this.key = null;
        }

        public AttributeKey<T> key() {
            return this.key;
        }

        public T setIfAbsent(T value) {
            while (!compareAndSet((Object) null, value)) {
                T old = get();
                if (old != null) {
                    return old;
                }
            }
            return null;
        }

        public T getAndRemove() {
            this.removed = true;
            T oldValue = getAndSet((Object) null);
            remove0();
            return oldValue;
        }

        public void remove() {
            this.removed = true;
            set((Object) null);
            remove0();
        }

        private void remove0() {
            synchronized (this.head) {
                DefaultAttribute<?> defaultAttribute = this.prev;
                if (defaultAttribute != null) {
                    defaultAttribute.next = this.next;
                    DefaultAttribute<?> defaultAttribute2 = this.next;
                    if (defaultAttribute2 != null) {
                        defaultAttribute2.prev = defaultAttribute;
                    }
                    this.prev = null;
                    this.next = null;
                }
            }
        }
    }
}
