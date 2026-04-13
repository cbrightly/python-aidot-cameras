package androidx.work;

import android.annotation.SuppressLint;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import androidx.room.TypeConverter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class Data {
    public static final Data EMPTY = new Builder().build();
    @SuppressLint({"MinMaxConstant"})
    public static final int MAX_DATA_BYTES = 10240;
    private static final String TAG = Logger.tagWithPrefix("Data");
    Map<String, Object> mValues;

    Data() {
    }

    public Data(@NonNull Data other) {
        this.mValues = new HashMap(other.mValues);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public Data(@NonNull Map<String, ?> values) {
        this.mValues = new HashMap(values);
    }

    public boolean getBoolean(@NonNull String key, boolean defaultValue) {
        Object value = this.mValues.get(key);
        if (value instanceof Boolean) {
            return ((Boolean) value).booleanValue();
        }
        return defaultValue;
    }

    @Nullable
    public boolean[] getBooleanArray(@NonNull String key) {
        Object value = this.mValues.get(key);
        if (value instanceof Boolean[]) {
            return convertToPrimitiveArray((Boolean[]) value);
        }
        return null;
    }

    public byte getByte(@NonNull String key, byte defaultValue) {
        Object value = this.mValues.get(key);
        if (value instanceof Byte) {
            return ((Byte) value).byteValue();
        }
        return defaultValue;
    }

    @Nullable
    public byte[] getByteArray(@NonNull String key) {
        Object value = this.mValues.get(key);
        if (value instanceof Byte[]) {
            return convertToPrimitiveArray((Byte[]) value);
        }
        return null;
    }

    public int getInt(@NonNull String key, int defaultValue) {
        Object value = this.mValues.get(key);
        if (value instanceof Integer) {
            return ((Integer) value).intValue();
        }
        return defaultValue;
    }

    @Nullable
    public int[] getIntArray(@NonNull String key) {
        Object value = this.mValues.get(key);
        if (value instanceof Integer[]) {
            return convertToPrimitiveArray((Integer[]) value);
        }
        return null;
    }

    public long getLong(@NonNull String key, long defaultValue) {
        Object value = this.mValues.get(key);
        if (value instanceof Long) {
            return ((Long) value).longValue();
        }
        return defaultValue;
    }

    @Nullable
    public long[] getLongArray(@NonNull String key) {
        Object value = this.mValues.get(key);
        if (value instanceof Long[]) {
            return convertToPrimitiveArray((Long[]) value);
        }
        return null;
    }

    public float getFloat(@NonNull String key, float defaultValue) {
        Object value = this.mValues.get(key);
        if (value instanceof Float) {
            return ((Float) value).floatValue();
        }
        return defaultValue;
    }

    @Nullable
    public float[] getFloatArray(@NonNull String key) {
        Object value = this.mValues.get(key);
        if (value instanceof Float[]) {
            return convertToPrimitiveArray((Float[]) value);
        }
        return null;
    }

    public double getDouble(@NonNull String key, double defaultValue) {
        Object value = this.mValues.get(key);
        if (value instanceof Double) {
            return ((Double) value).doubleValue();
        }
        return defaultValue;
    }

    @Nullable
    public double[] getDoubleArray(@NonNull String key) {
        Object value = this.mValues.get(key);
        if (value instanceof Double[]) {
            return convertToPrimitiveArray((Double[]) value);
        }
        return null;
    }

    @Nullable
    public String getString(@NonNull String key) {
        Object value = this.mValues.get(key);
        if (value instanceof String) {
            return (String) value;
        }
        return null;
    }

    @Nullable
    public String[] getStringArray(@NonNull String key) {
        Object value = this.mValues.get(key);
        if (value instanceof String[]) {
            return (String[]) value;
        }
        return null;
    }

    @NonNull
    public Map<String, Object> getKeyValueMap() {
        return Collections.unmodifiableMap(this.mValues);
    }

    @NonNull
    public byte[] toByteArray() {
        return toByteArrayInternal(this);
    }

    public <T> boolean hasKeyWithValueOfType(@NonNull String key, @NonNull Class<T> klass) {
        Object value = this.mValues.get(key);
        return value != null && klass.isAssignableFrom(value.getClass());
    }

    @VisibleForTesting
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public int size() {
        return this.mValues.size();
    }

    @TypeConverter
    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static byte[] toByteArrayInternal(@NonNull Data data) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = null;
        try {
            ObjectOutputStream objectOutputStream2 = new ObjectOutputStream(outputStream);
            objectOutputStream2.writeInt(data.size());
            for (Map.Entry<String, Object> entry : data.mValues.entrySet()) {
                objectOutputStream2.writeUTF(entry.getKey());
                objectOutputStream2.writeObject(entry.getValue());
            }
            try {
                objectOutputStream2.close();
            } catch (IOException e) {
                Log.e(TAG, "Error in Data#toByteArray: ", e);
            }
            try {
                outputStream.close();
            } catch (IOException e2) {
                Log.e(TAG, "Error in Data#toByteArray: ", e2);
            }
            if (outputStream.size() <= 10240) {
                return outputStream.toByteArray();
            }
            throw new IllegalStateException("Data cannot occupy more than 10240 bytes when serialized");
        } catch (IOException e3) {
            Log.e(TAG, "Error in Data#toByteArray: ", e3);
            byte[] byteArray = outputStream.toByteArray();
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                } catch (IOException e4) {
                    Log.e(TAG, "Error in Data#toByteArray: ", e4);
                }
            }
            try {
                outputStream.close();
            } catch (IOException e5) {
                Log.e(TAG, "Error in Data#toByteArray: ", e5);
            }
            return byteArray;
        } catch (Throwable th) {
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                } catch (IOException e6) {
                    Log.e(TAG, "Error in Data#toByteArray: ", e6);
                }
            }
            try {
                outputStream.close();
            } catch (IOException e7) {
                Log.e(TAG, "Error in Data#toByteArray: ", e7);
            }
            throw th;
        }
    }

    /* JADX WARNING: Unknown top exception splitter block from list: {B:12:0x0037=Splitter:B:12:0x0037, B:25:0x0059=Splitter:B:25:0x0059} */
    @androidx.room.TypeConverter
    @androidx.annotation.NonNull
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static androidx.work.Data fromByteArray(@androidx.annotation.NonNull byte[] r7) {
        /*
            java.lang.String r0 = "Error in Data#fromByteArray: "
            int r1 = r7.length
            r2 = 10240(0x2800, float:1.4349E-41)
            if (r1 > r2) goto L_0x007a
            java.util.HashMap r1 = new java.util.HashMap
            r1.<init>()
            java.io.ByteArrayInputStream r2 = new java.io.ByteArrayInputStream
            r2.<init>(r7)
            r3 = 0
            java.io.ObjectInputStream r4 = new java.io.ObjectInputStream     // Catch:{ IOException -> 0x0046, ClassNotFoundException -> 0x0044 }
            r4.<init>(r2)     // Catch:{ IOException -> 0x0046, ClassNotFoundException -> 0x0044 }
            r3 = r4
            int r4 = r3.readInt()     // Catch:{ IOException -> 0x0046, ClassNotFoundException -> 0x0044 }
        L_0x001c:
            if (r4 <= 0) goto L_0x002c
            java.lang.String r5 = r3.readUTF()     // Catch:{ IOException -> 0x0046, ClassNotFoundException -> 0x0044 }
            java.lang.Object r6 = r3.readObject()     // Catch:{ IOException -> 0x0046, ClassNotFoundException -> 0x0044 }
            r1.put(r5, r6)     // Catch:{ IOException -> 0x0046, ClassNotFoundException -> 0x0044 }
            int r4 = r4 + -1
            goto L_0x001c
        L_0x002c:
            r3.close()     // Catch:{ IOException -> 0x0031 }
            goto L_0x0037
        L_0x0031:
            r4 = move-exception
            java.lang.String r5 = TAG
            android.util.Log.e(r5, r0, r4)
        L_0x0037:
            r2.close()     // Catch:{ IOException -> 0x003b }
        L_0x003a:
            goto L_0x005d
        L_0x003b:
            r4 = move-exception
            java.lang.String r5 = TAG
            android.util.Log.e(r5, r0, r4)
            goto L_0x005d
        L_0x0042:
            r4 = move-exception
            goto L_0x0063
        L_0x0044:
            r4 = move-exception
            goto L_0x0047
        L_0x0046:
            r4 = move-exception
        L_0x0047:
            java.lang.String r5 = TAG     // Catch:{ all -> 0x0042 }
            android.util.Log.e(r5, r0, r4)     // Catch:{ all -> 0x0042 }
            if (r3 == 0) goto L_0x0059
            r3.close()     // Catch:{ IOException -> 0x0053 }
            goto L_0x0059
        L_0x0053:
            r4 = move-exception
            java.lang.String r5 = TAG
            android.util.Log.e(r5, r0, r4)
        L_0x0059:
            r2.close()     // Catch:{ IOException -> 0x003b }
            goto L_0x003a
        L_0x005d:
            androidx.work.Data r0 = new androidx.work.Data
            r0.<init>((java.util.Map<java.lang.String, ?>) r1)
            return r0
        L_0x0063:
            if (r3 == 0) goto L_0x006f
            r3.close()     // Catch:{ IOException -> 0x0069 }
            goto L_0x006f
        L_0x0069:
            r5 = move-exception
            java.lang.String r6 = TAG
            android.util.Log.e(r6, r0, r5)
        L_0x006f:
            r2.close()     // Catch:{ IOException -> 0x0073 }
            goto L_0x0079
        L_0x0073:
            r5 = move-exception
            java.lang.String r6 = TAG
            android.util.Log.e(r6, r0, r5)
        L_0x0079:
            throw r4
        L_0x007a:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "Data cannot occupy more than 10240 bytes when serialized"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.work.Data.fromByteArray(byte[]):androidx.work.Data");
    }

    public boolean equals(Object o) {
        boolean equal;
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Data other = (Data) o;
        Set<String> keys = this.mValues.keySet();
        if (!keys.equals(other.mValues.keySet())) {
            return false;
        }
        for (String key : keys) {
            Object value = this.mValues.get(key);
            Object otherValue = other.mValues.get(key);
            if (value == null || otherValue == null) {
                if (value == otherValue) {
                    equal = true;
                    continue;
                } else {
                    equal = false;
                    continue;
                }
            } else if (!(value instanceof Object[]) || !(otherValue instanceof Object[])) {
                equal = value.equals(otherValue);
                continue;
            } else {
                equal = Arrays.deepEquals((Object[]) value, (Object[]) otherValue);
                continue;
            }
            if (!equal) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        return this.mValues.hashCode() * 31;
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder("Data {");
        if (!this.mValues.isEmpty()) {
            for (String key : this.mValues.keySet()) {
                sb.append(key);
                sb.append(" : ");
                Object value = this.mValues.get(key);
                if (value instanceof Object[]) {
                    sb.append(Arrays.toString((Object[]) value));
                } else {
                    sb.append(value);
                }
                sb.append(", ");
            }
        }
        sb.append("}");
        return sb.toString();
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static Boolean[] convertPrimitiveBooleanArray(@NonNull boolean[] value) {
        Boolean[] returnValue = new Boolean[value.length];
        for (int i = 0; i < value.length; i++) {
            returnValue[i] = Boolean.valueOf(value[i]);
        }
        return returnValue;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static Byte[] convertPrimitiveByteArray(@NonNull byte[] value) {
        Byte[] returnValue = new Byte[value.length];
        for (int i = 0; i < value.length; i++) {
            returnValue[i] = Byte.valueOf(value[i]);
        }
        return returnValue;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static Integer[] convertPrimitiveIntArray(@NonNull int[] value) {
        Integer[] returnValue = new Integer[value.length];
        for (int i = 0; i < value.length; i++) {
            returnValue[i] = Integer.valueOf(value[i]);
        }
        return returnValue;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static Long[] convertPrimitiveLongArray(@NonNull long[] value) {
        Long[] returnValue = new Long[value.length];
        for (int i = 0; i < value.length; i++) {
            returnValue[i] = Long.valueOf(value[i]);
        }
        return returnValue;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static Float[] convertPrimitiveFloatArray(@NonNull float[] value) {
        Float[] returnValue = new Float[value.length];
        for (int i = 0; i < value.length; i++) {
            returnValue[i] = Float.valueOf(value[i]);
        }
        return returnValue;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static Double[] convertPrimitiveDoubleArray(@NonNull double[] value) {
        Double[] returnValue = new Double[value.length];
        for (int i = 0; i < value.length; i++) {
            returnValue[i] = Double.valueOf(value[i]);
        }
        return returnValue;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static boolean[] convertToPrimitiveArray(@NonNull Boolean[] array) {
        boolean[] returnArray = new boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            returnArray[i] = array[i].booleanValue();
        }
        return returnArray;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static byte[] convertToPrimitiveArray(@NonNull Byte[] array) {
        byte[] returnArray = new byte[array.length];
        for (int i = 0; i < array.length; i++) {
            returnArray[i] = array[i].byteValue();
        }
        return returnArray;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static int[] convertToPrimitiveArray(@NonNull Integer[] array) {
        int[] returnArray = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            returnArray[i] = array[i].intValue();
        }
        return returnArray;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static long[] convertToPrimitiveArray(@NonNull Long[] array) {
        long[] returnArray = new long[array.length];
        for (int i = 0; i < array.length; i++) {
            returnArray[i] = array[i].longValue();
        }
        return returnArray;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static float[] convertToPrimitiveArray(@NonNull Float[] array) {
        float[] returnArray = new float[array.length];
        for (int i = 0; i < array.length; i++) {
            returnArray[i] = array[i].floatValue();
        }
        return returnArray;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static double[] convertToPrimitiveArray(@NonNull Double[] array) {
        double[] returnArray = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            returnArray[i] = array[i].doubleValue();
        }
        return returnArray;
    }

    public static final class Builder {
        private Map<String, Object> mValues = new HashMap();

        @NonNull
        public Builder putBoolean(@NonNull String key, boolean value) {
            this.mValues.put(key, Boolean.valueOf(value));
            return this;
        }

        @NonNull
        public Builder putBooleanArray(@NonNull String key, @NonNull boolean[] value) {
            this.mValues.put(key, Data.convertPrimitiveBooleanArray(value));
            return this;
        }

        @NonNull
        public Builder putByte(@NonNull String key, byte value) {
            this.mValues.put(key, Byte.valueOf(value));
            return this;
        }

        @NonNull
        public Builder putByteArray(@NonNull String key, @NonNull byte[] value) {
            this.mValues.put(key, Data.convertPrimitiveByteArray(value));
            return this;
        }

        @NonNull
        public Builder putInt(@NonNull String key, int value) {
            this.mValues.put(key, Integer.valueOf(value));
            return this;
        }

        @NonNull
        public Builder putIntArray(@NonNull String key, @NonNull int[] value) {
            this.mValues.put(key, Data.convertPrimitiveIntArray(value));
            return this;
        }

        @NonNull
        public Builder putLong(@NonNull String key, long value) {
            this.mValues.put(key, Long.valueOf(value));
            return this;
        }

        @NonNull
        public Builder putLongArray(@NonNull String key, @NonNull long[] value) {
            this.mValues.put(key, Data.convertPrimitiveLongArray(value));
            return this;
        }

        @NonNull
        public Builder putFloat(@NonNull String key, float value) {
            this.mValues.put(key, Float.valueOf(value));
            return this;
        }

        @NonNull
        public Builder putFloatArray(@NonNull String key, @NonNull float[] value) {
            this.mValues.put(key, Data.convertPrimitiveFloatArray(value));
            return this;
        }

        @NonNull
        public Builder putDouble(@NonNull String key, double value) {
            this.mValues.put(key, Double.valueOf(value));
            return this;
        }

        @NonNull
        public Builder putDoubleArray(@NonNull String key, @NonNull double[] value) {
            this.mValues.put(key, Data.convertPrimitiveDoubleArray(value));
            return this;
        }

        @NonNull
        public Builder putString(@NonNull String key, @Nullable String value) {
            this.mValues.put(key, value);
            return this;
        }

        @NonNull
        public Builder putStringArray(@NonNull String key, @NonNull String[] value) {
            this.mValues.put(key, value);
            return this;
        }

        @NonNull
        public Builder putAll(@NonNull Data data) {
            putAll(data.mValues);
            return this;
        }

        @NonNull
        public Builder putAll(@NonNull Map<String, Object> values) {
            for (Map.Entry<String, Object> entry : values.entrySet()) {
                put(entry.getKey(), entry.getValue());
            }
            return this;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder put(@NonNull String key, @Nullable Object value) {
            if (value == null) {
                this.mValues.put(key, (Object) null);
            } else {
                Class<?> valueType = value.getClass();
                if (valueType == Boolean.class || valueType == Byte.class || valueType == Integer.class || valueType == Long.class || valueType == Float.class || valueType == Double.class || valueType == String.class || valueType == Boolean[].class || valueType == Byte[].class || valueType == Integer[].class || valueType == Long[].class || valueType == Float[].class || valueType == Double[].class || valueType == String[].class) {
                    this.mValues.put(key, value);
                } else if (valueType == boolean[].class) {
                    this.mValues.put(key, Data.convertPrimitiveBooleanArray((boolean[]) value));
                } else if (valueType == byte[].class) {
                    this.mValues.put(key, Data.convertPrimitiveByteArray((byte[]) value));
                } else if (valueType == int[].class) {
                    this.mValues.put(key, Data.convertPrimitiveIntArray((int[]) value));
                } else if (valueType == long[].class) {
                    this.mValues.put(key, Data.convertPrimitiveLongArray((long[]) value));
                } else if (valueType == float[].class) {
                    this.mValues.put(key, Data.convertPrimitiveFloatArray((float[]) value));
                } else if (valueType == double[].class) {
                    this.mValues.put(key, Data.convertPrimitiveDoubleArray((double[]) value));
                } else {
                    throw new IllegalArgumentException(String.format("Key %s has invalid type %s", new Object[]{key, valueType}));
                }
            }
            return this;
        }

        @NonNull
        public Data build() {
            Data data = new Data((Map<String, ?>) this.mValues);
            Data.toByteArrayInternal(data);
            return data;
        }
    }
}
