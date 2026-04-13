package org.glassfish.grizzly.filterchain;

public abstract class AbstractNextAction implements NextAction {
    protected final int type;

    protected AbstractNextAction(int type2) {
        this.type = type2;
    }

    public final int type() {
        return this.type;
    }
}
