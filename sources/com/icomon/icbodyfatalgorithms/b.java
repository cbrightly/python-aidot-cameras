package com.icomon.icbodyfatalgorithms;

/* compiled from: ICBodyFatAlgorithmsSex */
public enum b {
    Male(1),
    Female(2);
    
    private final int value;

    private b(int value2) {
        this.value = value2;
    }

    public int getValue() {
        return this.value;
    }

    public static b valueOf(int value2) {
        switch (value2) {
            case 2:
                return Female;
            default:
                return Male;
        }
    }
}
