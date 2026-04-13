package org.junit.runner;

public final class FilterFactoryParams {
    private final String args;
    private final a topLevelDescription;

    public FilterFactoryParams(a topLevelDescription2, String args2) {
        if (args2 == null || topLevelDescription2 == null) {
            throw new NullPointerException();
        }
        this.topLevelDescription = topLevelDescription2;
        this.args = args2;
    }

    public String getArgs() {
        return this.args;
    }

    public a getTopLevelDescription() {
        return this.topLevelDescription;
    }
}
