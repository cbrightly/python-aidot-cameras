package com.icomon.icbodyfatalgorithms;

/* compiled from: ICBodyFatAlgorithmsType */
public enum c {
    ICBodyFatAlgorithmsTypeWLA07(6),
    ICBodyFatAlgorithmsTypeWLA24(23),
    ICBodyFatAlgorithmsTypeRev(99);
    
    private final int value;

    private c(int value2) {
        this.value = value2;
    }

    public int getValue() {
        return this.value;
    }

    public static c valueOf(int value2) {
        switch (value2) {
            case 6:
                return ICBodyFatAlgorithmsTypeWLA07;
            case 99:
                return ICBodyFatAlgorithmsTypeRev;
            default:
                return null;
        }
    }
}
