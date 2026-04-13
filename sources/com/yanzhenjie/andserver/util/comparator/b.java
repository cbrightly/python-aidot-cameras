package com.yanzhenjie.andserver.util.comparator;

import com.yanzhenjie.andserver.util.a;
import java.io.Serializable;
import java.util.Comparator;

/* compiled from: InvertibleComparator */
public class b<T> implements Comparator<T>, Serializable {
    private boolean ascending = true;
    private final Comparator<T> comparator;

    public b(Comparator<T> comparator2) {
        a.c(comparator2, "Comparator must not be null.");
        this.comparator = comparator2;
    }

    public b(Comparator<T> comparator2, boolean ascending2) {
        a.c(comparator2, "Comparator must not be null.");
        this.comparator = comparator2;
        setAscending(ascending2);
    }

    public void setAscending(boolean ascending2) {
        this.ascending = ascending2;
    }

    public boolean isAscending() {
        return this.ascending;
    }

    public void invertOrder() {
        this.ascending = !this.ascending;
    }

    public int compare(T o1, T o2) {
        int result = this.comparator.compare(o1, o2);
        if (result == 0) {
            return 0;
        }
        if (this.ascending) {
            return result;
        }
        if (Integer.MIN_VALUE == result) {
            return Integer.MAX_VALUE;
        }
        return result * -1;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof b)) {
            return false;
        }
        InvertibleComparator<T> other = (b) obj;
        if (!this.comparator.equals(other.comparator) || this.ascending != other.ascending) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return this.comparator.hashCode();
    }

    public String toString() {
        return "InvertibleComparator: [" + this.comparator + "]; ascending=" + this.ascending;
    }
}
