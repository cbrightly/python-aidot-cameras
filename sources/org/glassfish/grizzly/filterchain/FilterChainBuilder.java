package org.glassfish.grizzly.filterchain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class FilterChainBuilder {
    protected final List<Filter> patternFilterChain;

    public abstract FilterChain build();

    private FilterChainBuilder() {
        this.patternFilterChain = new ArrayList();
    }

    public static FilterChainBuilder stateless() {
        return new StatelessFilterChainBuilder();
    }

    public static FilterChainBuilder stateful() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public FilterChainBuilder add(Filter filter) {
        return addLast(filter);
    }

    public FilterChainBuilder addFirst(Filter filter) {
        this.patternFilterChain.add(0, filter);
        return this;
    }

    public FilterChainBuilder addLast(Filter filter) {
        this.patternFilterChain.add(filter);
        return this;
    }

    public FilterChainBuilder add(int index, Filter filter) {
        this.patternFilterChain.add(index, filter);
        return this;
    }

    public FilterChainBuilder set(int index, Filter filter) {
        this.patternFilterChain.set(index, filter);
        return this;
    }

    public Filter get(int index) {
        return this.patternFilterChain.get(index);
    }

    public FilterChainBuilder remove(int index) {
        this.patternFilterChain.remove(index);
        return this;
    }

    public FilterChainBuilder remove(Filter filter) {
        this.patternFilterChain.remove(filter);
        return this;
    }

    public FilterChainBuilder addAll(Filter[] array) {
        List<Filter> list = this.patternFilterChain;
        list.addAll(list.size(), Arrays.asList(array));
        return this;
    }

    public FilterChainBuilder addAll(int filterIndex, Filter[] array) {
        this.patternFilterChain.addAll(filterIndex, Arrays.asList(array));
        return this;
    }

    public FilterChainBuilder addAll(List<Filter> list) {
        return addAll(this.patternFilterChain.size(), list);
    }

    public FilterChainBuilder addAll(int filterIndex, List<Filter> list) {
        this.patternFilterChain.addAll(filterIndex, list);
        return this;
    }

    public FilterChainBuilder addAll(FilterChainBuilder source) {
        this.patternFilterChain.addAll(source.patternFilterChain);
        return this;
    }

    public int indexOf(Filter filter) {
        return this.patternFilterChain.indexOf(filter);
    }

    public int indexOfType(Class<? extends Filter> filterType) {
        int size = this.patternFilterChain.size();
        for (int i = 0; i < size; i++) {
            if (filterType.isAssignableFrom(get(i).getClass())) {
                return i;
            }
        }
        return -1;
    }

    public static class StatelessFilterChainBuilder extends FilterChainBuilder {
        public StatelessFilterChainBuilder() {
            super();
        }

        public FilterChain build() {
            FilterChain fc = new DefaultFilterChain();
            fc.addAll(this.patternFilterChain);
            return fc;
        }
    }
}
