package org.glassfish.grizzly.filterchain;

public final class ForkAction extends AbstractNextAction {
    static final int TYPE = 5;
    private final FilterChainContext context;

    ForkAction(FilterChainContext context2) {
        super(5);
        this.context = context2;
    }

    /* access modifiers changed from: package-private */
    public FilterChainContext getContext() {
        return this.context;
    }
}
