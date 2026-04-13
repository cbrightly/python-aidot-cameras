package com.yanzhenjie.andserver.util.comparator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/* compiled from: CompoundComparator */
public class a<T> implements Comparator<T>, Serializable {
    private final List<b> comparators;

    public a() {
        this.comparators = new ArrayList();
    }

    public a(Comparator... comparators2) {
        com.yanzhenjie.andserver.util.a.c(comparators2, "Comparators must not be null");
        this.comparators = new ArrayList(comparators2.length);
        for (Comparator comparator : comparators2) {
            addComparator(comparator);
        }
    }

    public void addComparator(Comparator<? extends T> comparator) {
        if (comparator instanceof b) {
            this.comparators.add((b) comparator);
        } else {
            this.comparators.add(new b(comparator));
        }
    }

    public void addComparator(Comparator<? extends T> comparator, boolean ascending) {
        this.comparators.add(new b(comparator, ascending));
    }

    public void setComparator(int index, Comparator<? extends T> comparator) {
        if (comparator instanceof b) {
            this.comparators.set(index, (b) comparator);
        } else {
            this.comparators.set(index, new b(comparator));
        }
    }

    public void setComparator(int index, Comparator<T> comparator, boolean ascending) {
        this.comparators.set(index, new b(comparator, ascending));
    }

    public void invertOrder() {
        for (b comparator : this.comparators) {
            comparator.invertOrder();
        }
    }

    public void invertOrder(int index) {
        this.comparators.get(index).invertOrder();
    }

    public void setAscendingOrder(int index) {
        this.comparators.get(index).setAscending(true);
    }

    public void setDescendingOrder(int index) {
        this.comparators.get(index).setAscending(false);
    }

    public int getComparatorCount() {
        return this.comparators.size();
    }

    public int compare(T o1, T o2) {
        com.yanzhenjie.andserver.util.a.d(this.comparators.size() > 0, "No sort definitions have been added to this CompoundComparator to compare");
        for (b comparator : this.comparators) {
            int result = comparator.compare(o1, o2);
            if (result != 0) {
                return result;
            }
        }
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof a)) {
            return false;
        }
        return this.comparators.equals(((a) obj).comparators);
    }

    public int hashCode() {
        return this.comparators.hashCode();
    }

    public String toString() {
        return "CompoundComparator: " + this.comparators;
    }
}
