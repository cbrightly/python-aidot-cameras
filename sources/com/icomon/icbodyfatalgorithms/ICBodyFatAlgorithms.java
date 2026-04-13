package com.icomon.icbodyfatalgorithms;

public class ICBodyFatAlgorithms {
    static final Integer a = 0;
    static boolean b = false;

    private static native double native_getBMI(double d, int i, int i2, int i3);

    private static native int native_getBMR(double d, int i, int i2, double d2, double d3, int i3, int i4, int i5);

    private static native double native_getBodyFatPercent(double d, int i, int i2, double d2, double d3, int i3, int i4, int i5);

    private static native double native_getBoneMass(double d, int i, int i2, double d2, double d3, int i3, int i4, int i5);

    private static native double native_getMoisturePercent(double d, int i, int i2, double d2, double d3, int i3, int i4, int i5);

    private static native double native_getMusclePercent(double d, int i, int i2, double d2, double d3, int i3, int i4, int i5);

    private static native int native_getPhysicalAge(double d, int i, int i2, double d2, double d3, int i3, int i4, int i5);

    private static native double native_getProtein(double d, int i, int i2, double d2, double d3, int i3, int i4, int i5);

    private static native double native_getSkeletalMuscle(double d, int i, int i2, double d2, double d3, int i3, int i4, int i5);

    private static native double native_getSubcutaneousFatPercent(double d, int i, int i2, double d2, double d3, int i3, int i4, int i5);

    private static native double native_getVisceralFat(double d, int i, int i2, double d2, double d3, int i3, int i4, int i5);

    private static void l() {
        synchronized (a) {
            if (!b) {
                System.loadLibrary("ICBodyFatAlgorithms");
                b = true;
            }
        }
    }

    public static double a(double weight, int height, c type, a peopleType) {
        l();
        return native_getBMI(weight, height, type.getValue(), peopleType.getValue());
    }

    public static double c(double weight, int height, int age, double adc, double adc2, b sex, c type, a peopleType) {
        l();
        return native_getBodyFatPercent(weight, height, age, adc, adc2, sex.getValue(), type.getValue(), peopleType.getValue());
    }

    public static double j(double weight, int height, int age, double adc, double adc2, b sex, c type, a peopleType) {
        l();
        return native_getSubcutaneousFatPercent(weight, height, age, adc, adc2, sex.getValue(), type.getValue(), peopleType.getValue());
    }

    public static double k(double weight, int height, int age, double adc, double adc2, b sex, c type, a peopleType) {
        l();
        return native_getVisceralFat(weight, height, age, adc, adc2, sex.getValue(), type.getValue(), peopleType.getValue());
    }

    public static double f(double weight, int height, int age, double adc, double adc2, b sex, c type, a peopleType) {
        l();
        return native_getMusclePercent(weight, height, age, adc, adc2, sex.getValue(), type.getValue(), peopleType.getValue());
    }

    public static int b(double weight, int height, int age, double adc, double adc2, b sex, c type, a peopleType) {
        l();
        return native_getBMR(weight, height, age, adc, adc2, sex.getValue(), type.getValue(), peopleType.getValue());
    }

    public static double d(double weight, int height, int age, double adc, double adc2, b sex, c type, a peopleType) {
        l();
        return native_getBoneMass(weight, height, age, adc, adc2, sex.getValue(), type.getValue(), peopleType.getValue());
    }

    public static double e(double weight, int height, int age, double adc, double adc2, b sex, c type, a peopleType) {
        l();
        return native_getMoisturePercent(weight, height, age, adc, adc2, sex.getValue(), type.getValue(), peopleType.getValue());
    }

    public static int g(double weight, int height, int age, double adc, double adc2, b sex, c type, a peopleType) {
        l();
        return native_getPhysicalAge(weight, height, age, adc, adc2, sex.getValue(), type.getValue(), peopleType.getValue());
    }

    public static double h(double weight, int height, int age, double adc, double adc2, b sex, c type, a peopleType) {
        l();
        return native_getProtein(weight, height, age, adc, adc2, sex.getValue(), type.getValue(), peopleType.getValue());
    }

    public static double i(double weight, int height, int age, double adc, double adc2, b sex, c type, a peopleType) {
        l();
        return native_getSkeletalMuscle(weight, height, age, adc, adc2, sex.getValue(), type.getValue(), peopleType.getValue());
    }
}
