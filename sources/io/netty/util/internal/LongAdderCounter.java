package io.netty.util.internal;

import java.util.concurrent.atomic.LongAdder;

public final class LongAdderCounter extends LongAdder implements LongCounter {
    LongAdderCounter() {
    }

    public long value() {
        return longValue();
    }
}
