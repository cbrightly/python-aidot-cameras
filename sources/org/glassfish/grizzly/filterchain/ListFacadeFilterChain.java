package org.glassfish.grizzly.filterchain;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public abstract class ListFacadeFilterChain extends AbstractFilterChain {
    protected final List<Filter> filters;

    public ListFacadeFilterChain(List<Filter> filtersImpl) {
        this.filters = filtersImpl;
    }

    public boolean add(Filter filter) {
        if (!this.filters.add(filter)) {
            return false;
        }
        filter.onAdded(this);
        notifyChangedExcept(filter);
        return true;
    }

    public void add(int index, Filter filter) {
        this.filters.add(index, filter);
        filter.onAdded(this);
        notifyChangedExcept(filter);
    }

    public boolean addAll(Collection<? extends Filter> c) {
        for (Filter filter : c) {
            this.filters.add(filter);
            filter.onAdded(this);
        }
        notifyChangedExcept((Filter) null);
        return true;
    }

    public boolean addAll(int index, Collection<? extends Filter> c) {
        int i = 0;
        for (Filter filter : c) {
            this.filters.add(i + index, filter);
            filter.onAdded(this);
            i++;
        }
        notifyChangedExcept((Filter) null);
        return true;
    }

    public Filter set(int index, Filter filter) {
        Filter oldFilter = this.filters.set(index, filter);
        if (oldFilter != filter) {
            if (oldFilter != null) {
                oldFilter.onRemoved(this);
            }
            filter.onAdded(this);
            notifyChangedExcept(filter);
        }
        return oldFilter;
    }

    public final Filter get(int index) {
        return this.filters.get(index);
    }

    public int indexOf(Object object) {
        return this.filters.indexOf(object);
    }

    public int lastIndexOf(Object filter) {
        return this.filters.lastIndexOf(filter);
    }

    public boolean contains(Object filter) {
        return this.filters.contains(filter);
    }

    public boolean containsAll(Collection<?> c) {
        return this.filters.containsAll(c);
    }

    public Object[] toArray() {
        return this.filters.toArray();
    }

    public <T> T[] toArray(T[] a) {
        return this.filters.toArray(a);
    }

    public boolean retainAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    public boolean remove(Object object) {
        Filter filter = (Filter) object;
        if (!this.filters.remove(filter)) {
            return false;
        }
        filter.onRemoved(this);
        notifyChangedExcept(filter);
        return true;
    }

    public Filter remove(int index) {
        Filter filter = this.filters.remove(index);
        if (filter == null) {
            return null;
        }
        filter.onRemoved(this);
        notifyChangedExcept(filter);
        return filter;
    }

    public boolean removeAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    public boolean isEmpty() {
        List<Filter> list = this.filters;
        return list == null || list.isEmpty();
    }

    public int size() {
        return this.filters.size();
    }

    public void clear() {
        Object[] localFilters = this.filters.toArray();
        this.filters.clear();
        for (Object filter : localFilters) {
            ((Filter) filter).onRemoved(this);
        }
    }

    public Iterator<Filter> iterator() {
        return this.filters.iterator();
    }

    public ListIterator<Filter> listIterator() {
        return this.filters.listIterator();
    }

    public ListIterator<Filter> listIterator(int index) {
        return this.filters.listIterator(index);
    }

    /* access modifiers changed from: protected */
    public void notifyChangedExcept(Filter filter) {
        for (Filter currentFilter : this.filters) {
            if (currentFilter != filter) {
                currentFilter.onFilterChainChanged(this);
            }
        }
    }
}
