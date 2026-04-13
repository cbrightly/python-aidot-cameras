package kotlin.reflect.jvm.internal.impl.utils;

import java.lang.reflect.Array;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;
import org.jetbrains.annotations.NotNull;

/* compiled from: SmartList */
public class i<E> extends AbstractList<E> implements RandomAccess {
    private int c;
    /* access modifiers changed from: private */
    public Object d;

    private static /* synthetic */ void a(int i) {
        String str;
        int i2;
        Throwable th;
        switch (i) {
            case 2:
            case 3:
            case 5:
            case 6:
            case 7:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 2:
            case 3:
            case 5:
            case 6:
            case 7:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 2:
            case 3:
            case 5:
            case 6:
            case 7:
                objArr[0] = "kotlin/reflect/jvm/internal/impl/utils/SmartList";
                break;
            case 4:
                objArr[0] = "a";
                break;
            default:
                objArr[0] = "elements";
                break;
        }
        switch (i) {
            case 2:
            case 3:
                objArr[1] = "iterator";
                break;
            case 5:
            case 6:
            case 7:
                objArr[1] = "toArray";
                break;
            default:
                objArr[1] = "kotlin/reflect/jvm/internal/impl/utils/SmartList";
                break;
        }
        switch (i) {
            case 2:
            case 3:
            case 5:
            case 6:
            case 7:
                break;
            case 4:
                objArr[2] = "toArray";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String format = String.format(str, objArr);
        switch (i) {
            case 2:
            case 3:
            case 5:
            case 6:
            case 7:
                th = new IllegalStateException(format);
                break;
            default:
                th = new IllegalArgumentException(format);
                break;
        }
        throw th;
    }

    public E get(int index) {
        int i;
        if (index < 0 || index >= (i = this.c)) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + this.c);
        } else if (i == 1) {
            return this.d;
        } else {
            return ((Object[]) this.d)[index];
        }
    }

    public boolean add(E e) {
        int i = this.c;
        if (i == 0) {
            this.d = e;
        } else if (i == 1) {
            this.d = new Object[]{this.d, e};
        } else {
            Object[] array = (Object[]) this.d;
            int oldCapacity = array.length;
            if (i >= oldCapacity) {
                int newCapacity = ((oldCapacity * 3) / 2) + 1;
                int minCapacity = i + 1;
                if (newCapacity < minCapacity) {
                    newCapacity = minCapacity;
                }
                Object[] oldArray = array;
                Object[] objArr = new Object[newCapacity];
                array = objArr;
                this.d = objArr;
                System.arraycopy(oldArray, 0, array, 0, oldCapacity);
            }
            array[this.c] = e;
        }
        this.c++;
        this.modCount++;
        return true;
    }

    public void add(int index, E e) {
        int i;
        if (index < 0 || index > (i = this.c)) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + this.c);
        }
        if (i == 0) {
            this.d = e;
        } else if (i == 1 && index == 0) {
            this.d = new Object[]{e, this.d};
        } else {
            Object[] array = new Object[(i + 1)];
            if (i == 1) {
                array[0] = this.d;
            } else {
                Object[] oldArray = (Object[]) this.d;
                System.arraycopy(oldArray, 0, array, 0, index);
                System.arraycopy(oldArray, index, array, index + 1, this.c - index);
            }
            array[index] = e;
            this.d = array;
        }
        this.c++;
        this.modCount++;
    }

    public int size() {
        return this.c;
    }

    public void clear() {
        this.d = null;
        this.c = 0;
        this.modCount++;
    }

    public E set(int index, E element) {
        int i;
        if (index < 0 || index >= (i = this.c)) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + this.c);
        } else if (i == 1) {
            E oldValue = this.d;
            this.d = element;
            return oldValue;
        } else {
            E[] array = (Object[]) this.d;
            E oldValue2 = array[index];
            array[index] = element;
            return oldValue2;
        }
    }

    public E remove(int index) {
        int i;
        E oldValue;
        if (index < 0 || index >= (i = this.c)) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + this.c);
        }
        if (i == 1) {
            oldValue = this.d;
            this.d = null;
        } else {
            E[] array = (Object[]) this.d;
            E oldValue2 = array[index];
            if (i == 2) {
                this.d = array[1 - index];
            } else {
                int numMoved = (i - index) - 1;
                if (numMoved > 0) {
                    System.arraycopy(array, index + 1, array, index, numMoved);
                }
                array[this.c - 1] = null;
            }
            oldValue = oldValue2;
        }
        this.c--;
        this.modCount++;
        return oldValue;
    }

    /* compiled from: SmartList */
    public static class b<T> implements Iterator<T> {
        private static final b c = new b();

        private b() {
        }

        public static <T> b<T> b() {
            return c;
        }

        public boolean hasNext() {
            return false;
        }

        public T next() {
            throw new NoSuchElementException();
        }

        public void remove() {
            throw new IllegalStateException();
        }
    }

    @NotNull
    public Iterator<E> iterator() {
        int i = this.c;
        if (i == 0) {
            b b2 = b.b();
            if (b2 == null) {
                a(2);
            }
            return b2;
        } else if (i == 1) {
            return new c();
        } else {
            Iterator<E> it = super.iterator();
            if (it == null) {
                a(3);
            }
            return it;
        }
    }

    /* compiled from: SmartList */
    public static abstract class d<T> implements Iterator<T> {
        private boolean c;

        /* access modifiers changed from: protected */
        public abstract void b();

        /* access modifiers changed from: protected */
        public abstract T c();

        private d() {
        }

        public final boolean hasNext() {
            return !this.c;
        }

        public final T next() {
            if (!this.c) {
                this.c = true;
                b();
                return c();
            }
            throw new NoSuchElementException();
        }
    }

    /* compiled from: SmartList */
    public class c extends d<E> {
        private final int d;

        public c() {
            super();
            this.d = i.this.modCount;
        }

        /* access modifiers changed from: protected */
        public E c() {
            return i.this.d;
        }

        /* access modifiers changed from: protected */
        public void b() {
            if (i.this.modCount != this.d) {
                throw new ConcurrentModificationException("ModCount: " + i.this.modCount + "; expected: " + this.d);
            }
        }

        public void remove() {
            b();
            i.this.clear();
        }
    }

    @NotNull
    public <T> T[] toArray(@NotNull T[] a2) {
        if (a2 == null) {
            a(4);
        }
        int aLength = a2.length;
        int i = this.c;
        if (i == 1) {
            if (aLength != 0) {
                a2[0] = this.d;
            } else {
                T[] r = (Object[]) Array.newInstance(a2.getClass().getComponentType(), 1);
                r[0] = this.d;
                return r;
            }
        } else if (aLength < i) {
            T[] copyOf = Arrays.copyOf((Object[]) this.d, i, a2.getClass());
            if (copyOf == null) {
                a(6);
            }
            return copyOf;
        } else if (i != 0) {
            System.arraycopy(this.d, 0, a2, 0, i);
        }
        int i2 = this.c;
        if (aLength > i2) {
            a2[i2] = null;
        }
        return a2;
    }
}
