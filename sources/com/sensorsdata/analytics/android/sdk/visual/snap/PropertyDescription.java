package com.sensorsdata.analytics.android.sdk.visual.snap;

public class PropertyDescription {
    public final Caller accessor;
    private final String mMutatorName;
    public final String name;
    public final Class<?> targetClass;

    public PropertyDescription(String name2, Class<?> targetClass2, Caller accessor2, String mutatorName) {
        this.name = name2;
        this.targetClass = targetClass2;
        this.accessor = accessor2;
        this.mMutatorName = mutatorName;
    }

    public String toString() {
        return "[PropertyDescription " + this.name + "," + this.targetClass + ", " + this.accessor + "/" + this.mMutatorName + "]";
    }
}
