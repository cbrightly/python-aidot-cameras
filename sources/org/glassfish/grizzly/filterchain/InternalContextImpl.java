package org.glassfish.grizzly.filterchain;

import org.glassfish.grizzly.Context;

public class InternalContextImpl extends Context {
    final FilterChainContext filterChainContext;

    public InternalContextImpl(FilterChainContext context) {
        this.filterChainContext = context;
    }

    public void recycle() {
        this.filterChainContext.completeAndRecycle();
    }

    /* access modifiers changed from: protected */
    public void release() {
        this.filterChainContext.completeAndRelease();
    }

    /* access modifiers changed from: package-private */
    public void softCopyTo(InternalContextImpl targetContext) {
        targetContext.lifeCycleListeners.copyFrom(this.lifeCycleListeners);
        targetContext.ioEvent = this.ioEvent;
        targetContext.wasSuspended = this.wasSuspended;
        targetContext.isManualIOEventControl = this.isManualIOEventControl;
    }
}
