package kotlin.jvm.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

/* compiled from: SpreadBuilder */
public class c0 {
    private final ArrayList<Object> a;

    public c0(int size) {
        this.a = new ArrayList<>(size);
    }

    public void b(Object container) {
        if (container != null) {
            if (container instanceof Object[]) {
                Object[] array = (Object[]) container;
                if (array.length > 0) {
                    ArrayList<Object> arrayList = this.a;
                    arrayList.ensureCapacity(arrayList.size() + array.length);
                    Collections.addAll(this.a, array);
                }
            } else if (container instanceof Collection) {
                this.a.addAll((Collection) container);
            } else if (container instanceof Iterable) {
                for (Object element : (Iterable) container) {
                    this.a.add(element);
                }
            } else if (container instanceof Iterator) {
                Iterator iterator = (Iterator) container;
                while (iterator.hasNext()) {
                    this.a.add(iterator.next());
                }
            } else {
                throw new UnsupportedOperationException("Don't know how to spread " + container.getClass());
            }
        }
    }

    public int c() {
        return this.a.size();
    }

    public void a(Object element) {
        this.a.add(element);
    }

    public Object[] d(Object[] a2) {
        return this.a.toArray(a2);
    }
}
