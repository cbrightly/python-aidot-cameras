package kotlin.reflect.jvm.internal.impl.types.model;

import java.util.ArrayList;

/* compiled from: TypeSystemContext.kt */
public final class a extends ArrayList<j> implements i {
    public a(int initialSize) {
        super(initialSize);
    }

    public final /* bridge */ boolean contains(Object obj) {
        if (obj instanceof j) {
            return contains((j) obj);
        }
        return false;
    }

    public /* bridge */ boolean contains(j jVar) {
        return super.contains(jVar);
    }

    public /* bridge */ int getSize() {
        return super.size();
    }

    public final /* bridge */ int indexOf(Object obj) {
        if (obj instanceof j) {
            return indexOf((j) obj);
        }
        return -1;
    }

    public /* bridge */ int indexOf(j jVar) {
        return super.indexOf(jVar);
    }

    public final /* bridge */ int lastIndexOf(Object obj) {
        if (obj instanceof j) {
            return lastIndexOf((j) obj);
        }
        return -1;
    }

    public /* bridge */ int lastIndexOf(j jVar) {
        return super.lastIndexOf(jVar);
    }

    public final /* bridge */ boolean remove(Object obj) {
        if (obj instanceof j) {
            return remove((j) obj);
        }
        return false;
    }

    public /* bridge */ boolean remove(j jVar) {
        return super.remove(jVar);
    }

    public final /* bridge */ int size() {
        return getSize();
    }
}
