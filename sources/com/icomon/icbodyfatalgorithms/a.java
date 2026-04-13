package com.icomon.icbodyfatalgorithms;

/* compiled from: ICBodyFatAlgorithmsPeopleType */
public enum a {
    ICBodyFatAlgorithmsPeopleTypeNormal(0),
    ICBodyFatAlgorithmsPeopleTypeSportsMan(1);
    
    private final int value;

    private a(int value2) {
        this.value = value2;
    }

    public int getValue() {
        return this.value;
    }

    public static a valueOf(int value2) {
        switch (value2) {
            case 0:
                return ICBodyFatAlgorithmsPeopleTypeNormal;
            case 1:
                return ICBodyFatAlgorithmsPeopleTypeSportsMan;
            default:
                return null;
        }
    }
}
