package net.sqlcipher;

public class CustomCursorWindowAllocation implements CursorWindowAllocation {
    private long growthPaddingSize = 0;
    private long initialAllocationSize = 0;
    private long maxAllocationSize = 0;

    public CustomCursorWindowAllocation(long initialSize, long growthPaddingSize2, long maxAllocationSize2) {
        this.initialAllocationSize = initialSize;
        this.growthPaddingSize = growthPaddingSize2;
        this.maxAllocationSize = maxAllocationSize2;
    }

    public long getInitialAllocationSize() {
        return this.initialAllocationSize;
    }

    public long getGrowthPaddingSize() {
        return this.growthPaddingSize;
    }

    public long getMaxAllocationSize() {
        return this.maxAllocationSize;
    }
}
