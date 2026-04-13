package io.netty.handler.codec;

import io.netty.util.concurrent.FastThreadLocal;
import io.netty.util.internal.MathUtil;
import io.netty.util.internal.ObjectUtil;
import java.util.AbstractList;
import java.util.RandomAccess;

public final class CodecOutputList extends AbstractList<Object> implements RandomAccess {
    private static final FastThreadLocal<CodecOutputLists> CODEC_OUTPUT_LISTS_POOL = new FastThreadLocal<CodecOutputLists>() {
        /* access modifiers changed from: protected */
        public CodecOutputLists initialValue() {
            return new CodecOutputLists(16);
        }
    };
    /* access modifiers changed from: private */
    public static final CodecOutputListRecycler NOOP_RECYCLER = new CodecOutputListRecycler() {
        public void recycle(CodecOutputList object) {
        }
    };
    private Object[] array;
    private boolean insertSinceRecycled;
    private final CodecOutputListRecycler recycler;
    private int size;

    public interface CodecOutputListRecycler {
        void recycle(CodecOutputList codecOutputList);
    }

    public static final class CodecOutputLists implements CodecOutputListRecycler {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private int count;
        private int currentIdx;
        private final CodecOutputList[] elements;
        private final int mask;

        static {
            Class<CodecOutputList> cls = CodecOutputList.class;
        }

        CodecOutputLists(int numElements) {
            this.elements = new CodecOutputList[MathUtil.safeFindNextPositivePowerOfTwo(numElements)];
            int i = 0;
            while (true) {
                CodecOutputList[] codecOutputListArr = this.elements;
                if (i < codecOutputListArr.length) {
                    codecOutputListArr[i] = new CodecOutputList(this, 16);
                    i++;
                } else {
                    this.count = codecOutputListArr.length;
                    this.currentIdx = codecOutputListArr.length;
                    this.mask = codecOutputListArr.length - 1;
                    return;
                }
            }
        }

        public CodecOutputList getOrCreate() {
            int i = this.count;
            if (i == 0) {
                return new CodecOutputList(CodecOutputList.NOOP_RECYCLER, 4);
            }
            this.count = i - 1;
            int idx = (this.currentIdx - 1) & this.mask;
            CodecOutputList list = this.elements[idx];
            this.currentIdx = idx;
            return list;
        }

        public void recycle(CodecOutputList codecOutputList) {
            int idx = this.currentIdx;
            CodecOutputList[] codecOutputListArr = this.elements;
            codecOutputListArr[idx] = codecOutputList;
            this.currentIdx = (idx + 1) & this.mask;
            int i = this.count + 1;
            this.count = i;
            if (i > codecOutputListArr.length) {
                throw new AssertionError();
            }
        }
    }

    static CodecOutputList newInstance() {
        return CODEC_OUTPUT_LISTS_POOL.get().getOrCreate();
    }

    private CodecOutputList(CodecOutputListRecycler recycler2, int size2) {
        this.recycler = recycler2;
        this.array = new Object[size2];
    }

    public Object get(int index) {
        checkIndex(index);
        return this.array[index];
    }

    public int size() {
        return this.size;
    }

    public boolean add(Object element) {
        ObjectUtil.checkNotNull(element, "element");
        try {
            insert(this.size, element);
        } catch (IndexOutOfBoundsException e) {
            expandArray();
            insert(this.size, element);
        }
        this.size++;
        return true;
    }

    public Object set(int index, Object element) {
        ObjectUtil.checkNotNull(element, "element");
        checkIndex(index);
        Object old = this.array[index];
        insert(index, element);
        return old;
    }

    public void add(int index, Object element) {
        ObjectUtil.checkNotNull(element, "element");
        checkIndex(index);
        if (this.size == this.array.length) {
            expandArray();
        }
        int i = this.size;
        if (index != i - 1) {
            Object[] objArr = this.array;
            System.arraycopy(objArr, index, objArr, index + 1, i - index);
        }
        insert(index, element);
        this.size++;
    }

    public Object remove(int index) {
        checkIndex(index);
        Object[] objArr = this.array;
        Object old = objArr[index];
        int len = (this.size - index) - 1;
        if (len > 0) {
            System.arraycopy(objArr, index + 1, objArr, index, len);
        }
        Object[] objArr2 = this.array;
        int i = this.size - 1;
        this.size = i;
        objArr2[i] = null;
        return old;
    }

    public void clear() {
        this.size = 0;
    }

    /* access modifiers changed from: package-private */
    public boolean insertSinceRecycled() {
        return this.insertSinceRecycled;
    }

    /* access modifiers changed from: package-private */
    public void recycle() {
        for (int i = 0; i < this.size; i++) {
            this.array[i] = null;
        }
        this.size = 0;
        this.insertSinceRecycled = false;
        this.recycler.recycle(this);
    }

    /* access modifiers changed from: package-private */
    public Object getUnsafe(int index) {
        return this.array[index];
    }

    private void checkIndex(int index) {
        if (index >= this.size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void insert(int index, Object element) {
        this.array[index] = element;
        this.insertSinceRecycled = true;
    }

    private void expandArray() {
        Object[] objArr = this.array;
        int newCapacity = objArr.length << 1;
        if (newCapacity >= 0) {
            Object[] newArray = new Object[newCapacity];
            System.arraycopy(objArr, 0, newArray, 0, objArr.length);
            this.array = newArray;
            return;
        }
        throw new OutOfMemoryError();
    }
}
