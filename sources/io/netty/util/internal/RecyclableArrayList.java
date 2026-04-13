package io.netty.util.internal;

import io.netty.util.Recycler;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.RandomAccess;

public final class RecyclableArrayList extends ArrayList<Object> {
    private static final int DEFAULT_INITIAL_CAPACITY = 8;
    private static final Recycler<RecyclableArrayList> RECYCLER = new Recycler<RecyclableArrayList>() {
        /* access modifiers changed from: protected */
        public RecyclableArrayList newObject(Recycler.Handle handle) {
            return new RecyclableArrayList(handle);
        }
    };
    private static final long serialVersionUID = -8605125654176467947L;
    private final Recycler.Handle handle;
    private boolean insertSinceRecycled;

    public static RecyclableArrayList newInstance() {
        return newInstance(8);
    }

    public static RecyclableArrayList newInstance(int minCapacity) {
        RecyclableArrayList ret = RECYCLER.get();
        ret.ensureCapacity(minCapacity);
        return ret;
    }

    private RecyclableArrayList(Recycler.Handle handle2) {
        this(handle2, 8);
    }

    private RecyclableArrayList(Recycler.Handle handle2, int initialCapacity) {
        super(initialCapacity);
        this.handle = handle2;
    }

    public boolean addAll(Collection<?> c) {
        checkNullElements(c);
        if (!super.addAll(c)) {
            return false;
        }
        this.insertSinceRecycled = true;
        return true;
    }

    public boolean addAll(int index, Collection<?> c) {
        checkNullElements(c);
        if (!super.addAll(index, c)) {
            return false;
        }
        this.insertSinceRecycled = true;
        return true;
    }

    private static void checkNullElements(Collection<?> c) {
        if (!(c instanceof RandomAccess) || !(c instanceof List)) {
            for (Object element : c) {
                if (element == null) {
                    throw new IllegalArgumentException("c contains null values");
                }
            }
            return;
        }
        List<?> list = (List) c;
        int size = list.size();
        int i = 0;
        while (i < size) {
            if (list.get(i) != null) {
                i++;
            } else {
                throw new IllegalArgumentException("c contains null values");
            }
        }
    }

    public boolean add(Object element) {
        if (element == null) {
            throw new NullPointerException("element");
        } else if (!super.add(element)) {
            return false;
        } else {
            this.insertSinceRecycled = true;
            return true;
        }
    }

    public void add(int index, Object element) {
        if (element != null) {
            super.add(index, element);
            this.insertSinceRecycled = true;
            return;
        }
        throw new NullPointerException("element");
    }

    public Object set(int index, Object element) {
        if (element != null) {
            Object old = super.set(index, element);
            this.insertSinceRecycled = true;
            return old;
        }
        throw new NullPointerException("element");
    }

    public boolean insertSinceRecycled() {
        return this.insertSinceRecycled;
    }

    public boolean recycle() {
        clear();
        this.insertSinceRecycled = false;
        return RECYCLER.recycle(this, this.handle);
    }
}
