package org.apache.commons.math3.transform;

import java.lang.reflect.Array;
import net.sqlcipher.database.SQLiteDatabase;
import org.apache.commons.math3.complex.a;
import org.apache.commons.math3.exception.DimensionMismatchException;

/* compiled from: TransformUtils */
public class d {
    private static final int[] a = {1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072, 262144, 524288, 1048576, 2097152, 4194304, 8388608, 16777216, 33554432, 67108864, 134217728, SQLiteDatabase.CREATE_IF_NECESSARY, 536870912, 1073741824};

    public static double[][] b(a[] dataC) {
        int[] iArr = new int[2];
        iArr[1] = dataC.length;
        iArr[0] = 2;
        double[][] dataRI = (double[][]) Array.newInstance(double.class, iArr);
        double[] dataR = dataRI[0];
        double[] dataI = dataRI[1];
        for (int i = 0; i < dataC.length; i++) {
            a c = dataC[i];
            dataR[i] = c.getReal();
            dataI[i] = c.getImaginary();
        }
        return dataRI;
    }

    public static a[] a(double[][] dataRI) {
        if (dataRI.length == 2) {
            double[] dataR = dataRI[0];
            double[] dataI = dataRI[1];
            if (dataR.length == dataI.length) {
                int n = dataR.length;
                a[] c = new a[n];
                for (int i = 0; i < n; i++) {
                    c[i] = new a(dataR[i], dataI[i]);
                }
                return c;
            }
            throw new DimensionMismatchException(dataI.length, dataR.length);
        }
        throw new DimensionMismatchException(dataRI.length, 2);
    }
}
