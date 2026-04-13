package org.glassfish.grizzly.filterchain;

import org.glassfish.grizzly.filterchain.FilterChainContext;

public abstract class ExecutorResolver {
    private static final FilterExecutor ACCEPT_EXECUTOR = new UpstreamExecutor() {
        public NextAction execute(Filter filter, FilterChainContext context) {
            return filter.handleAccept(context);
        }
    };
    private static final FilterExecutor CLOSE_EXECUTOR = new UpstreamExecutor() {
        public NextAction execute(Filter filter, FilterChainContext context) {
            return filter.handleClose(context);
        }
    };
    private static final FilterExecutor CONNECT_EXECUTOR = new UpstreamExecutor() {
        public NextAction execute(Filter filter, FilterChainContext context) {
            return filter.handleConnect(context);
        }
    };
    public static final FilterExecutor DOWNSTREAM_EXECUTOR_SAMPLE = new DownstreamExecutor() {
        public NextAction execute(Filter filter, FilterChainContext context) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    };
    private static final FilterExecutor EVENT_DOWNSTREAM_EXECUTOR = new DownstreamExecutor() {
        public NextAction execute(Filter filter, FilterChainContext context) {
            return filter.handleEvent(context, context.event);
        }
    };
    private static final FilterExecutor EVENT_UPSTREAM_EXECUTOR = new UpstreamExecutor() {
        public NextAction execute(Filter filter, FilterChainContext context) {
            return filter.handleEvent(context, context.event);
        }
    };
    private static final FilterExecutor READ_EXECUTOR = new UpstreamExecutor() {
        public NextAction execute(Filter filter, FilterChainContext context) {
            return filter.handleRead(context);
        }
    };
    public static final FilterExecutor UPSTREAM_EXECUTOR_SAMPLE = new UpstreamExecutor() {
        public NextAction execute(Filter filter, FilterChainContext context) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    };
    private static final FilterExecutor WRITE_EXECUTOR = new DownstreamExecutor() {
        public NextAction execute(Filter filter, FilterChainContext context) {
            return filter.handleWrite(context);
        }
    };

    ExecutorResolver() {
    }

    /* renamed from: org.glassfish.grizzly.filterchain.ExecutorResolver$10  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass10 {
        static final /* synthetic */ int[] $SwitchMap$org$glassfish$grizzly$filterchain$FilterChainContext$Operation;

        static {
            int[] iArr = new int[FilterChainContext.Operation.values().length];
            $SwitchMap$org$glassfish$grizzly$filterchain$FilterChainContext$Operation = iArr;
            try {
                iArr[FilterChainContext.Operation.READ.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$org$glassfish$grizzly$filterchain$FilterChainContext$Operation[FilterChainContext.Operation.WRITE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$org$glassfish$grizzly$filterchain$FilterChainContext$Operation[FilterChainContext.Operation.ACCEPT.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$org$glassfish$grizzly$filterchain$FilterChainContext$Operation[FilterChainContext.Operation.CLOSE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$org$glassfish$grizzly$filterchain$FilterChainContext$Operation[FilterChainContext.Operation.CONNECT.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$org$glassfish$grizzly$filterchain$FilterChainContext$Operation[FilterChainContext.Operation.EVENT.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
        }
    }

    public static FilterExecutor resolve(FilterChainContext context) {
        switch (AnonymousClass10.$SwitchMap$org$glassfish$grizzly$filterchain$FilterChainContext$Operation[context.getOperation().ordinal()]) {
            case 1:
                return READ_EXECUTOR;
            case 2:
                return WRITE_EXECUTOR;
            case 3:
                return ACCEPT_EXECUTOR;
            case 4:
                return CLOSE_EXECUTOR;
            case 5:
                return CONNECT_EXECUTOR;
            case 6:
                return (context.getFilterIdx() == Integer.MIN_VALUE || context.getStartIdx() <= context.getEndIdx()) ? EVENT_UPSTREAM_EXECUTOR : EVENT_DOWNSTREAM_EXECUTOR;
            default:
                return null;
        }
    }

    public static abstract class UpstreamExecutor implements FilterExecutor {
        public final int defaultStartIdx(FilterChainContext context) {
            if (context.getFilterIdx() != Integer.MIN_VALUE) {
                return context.getFilterIdx();
            }
            context.setFilterIdx(0);
            return 0;
        }

        public final int defaultEndIdx(FilterChainContext context) {
            return context.getFilterChain().size();
        }

        public final int getNextFilter(FilterChainContext context) {
            return context.getFilterIdx() + 1;
        }

        public final int getPreviousFilter(FilterChainContext context) {
            return context.getFilterIdx() - 1;
        }

        public final boolean hasNextFilter(FilterChainContext context, int idx) {
            return idx < context.getFilterChain().size() - 1;
        }

        public final boolean hasPreviousFilter(FilterChainContext context, int idx) {
            return idx > 0;
        }

        public final void initIndexes(FilterChainContext context) {
            int startIdx = defaultStartIdx(context);
            context.setStartIdx(startIdx);
            context.setFilterIdx(startIdx);
            context.setEndIdx(defaultEndIdx(context));
        }

        public final boolean isUpstream() {
            return true;
        }

        public final boolean isDownstream() {
            return false;
        }
    }

    public static abstract class DownstreamExecutor implements FilterExecutor {
        public final int defaultStartIdx(FilterChainContext context) {
            if (context.getFilterIdx() != Integer.MIN_VALUE) {
                return context.getFilterIdx();
            }
            int idx = context.getFilterChain().size() - 1;
            context.setFilterIdx(idx);
            return idx;
        }

        public final int defaultEndIdx(FilterChainContext context) {
            return -1;
        }

        public final int getNextFilter(FilterChainContext context) {
            return context.getFilterIdx() - 1;
        }

        public final int getPreviousFilter(FilterChainContext context) {
            return context.getFilterIdx() + 1;
        }

        public final boolean hasNextFilter(FilterChainContext context, int idx) {
            return idx > 0;
        }

        public final boolean hasPreviousFilter(FilterChainContext context, int idx) {
            return idx < context.getFilterChain().size() - 1;
        }

        public final void initIndexes(FilterChainContext context) {
            int startIdx = defaultStartIdx(context);
            context.setStartIdx(startIdx);
            context.setFilterIdx(startIdx);
            context.setEndIdx(defaultEndIdx(context));
        }

        public final boolean isUpstream() {
            return false;
        }

        public final boolean isDownstream() {
            return true;
        }
    }
}
