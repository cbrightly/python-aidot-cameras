package io.netty.util;

import io.netty.util.internal.ObjectUtil;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

@Deprecated
public class UniqueName implements Comparable<UniqueName> {
    private static final AtomicInteger nextId = new AtomicInteger();
    private final int id;
    private final String name;

    public UniqueName(ConcurrentMap<String, Boolean> map, String name2, Object... args) {
        ObjectUtil.checkNotNull(map, "map");
        if (args != null && args.length > 0) {
            validateArgs(args);
        }
        if (map.putIfAbsent(name2, Boolean.TRUE) == null) {
            this.name = (String) ObjectUtil.checkNotNull(name2, "name");
            this.id = nextId.incrementAndGet();
            return;
        }
        throw new IllegalArgumentException(String.format("'%s' is already in use", new Object[]{name2}));
    }

    protected UniqueName(String name2) {
        this.name = (String) ObjectUtil.checkNotNull(name2, "name");
        this.id = nextId.incrementAndGet();
    }

    /* access modifiers changed from: protected */
    public void validateArgs(Object... args) {
    }

    public final String name() {
        return this.name;
    }

    public final int id() {
        return this.id;
    }

    public final int hashCode() {
        return super.hashCode();
    }

    public final boolean equals(Object o) {
        return super.equals(o);
    }

    public int compareTo(UniqueName other) {
        if (this == other) {
            return 0;
        }
        int returnCode = this.name.compareTo(other.name);
        if (returnCode != 0) {
            return returnCode;
        }
        return Integer.valueOf(this.id).compareTo(Integer.valueOf(other.id));
    }

    public String toString() {
        return name();
    }
}
