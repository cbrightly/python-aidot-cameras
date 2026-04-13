package androidx.work;

import androidx.annotation.NonNull;
import androidx.work.Data;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class ArrayCreatingInputMerger extends InputMerger {
    @NonNull
    public Data merge(@NonNull List<Data> inputs) {
        Object mergedValue;
        Data.Builder output = new Data.Builder();
        Map<String, Object> mergedValues = new HashMap<>();
        loop0:
        for (Data input : inputs) {
            Iterator<Map.Entry<String, Object>> it = input.getKeyValueMap().entrySet().iterator();
            while (true) {
                if (it.hasNext()) {
                    Map.Entry<String, Object> entry = it.next();
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    Class<?> valueClass = value.getClass();
                    Object existingValue = mergedValues.get(key);
                    if (existingValue != null) {
                        Class<?> existingValueClass = existingValue.getClass();
                        if (existingValueClass.equals(valueClass)) {
                            if (existingValueClass.isArray()) {
                                mergedValue = concatenateArrays(existingValue, value);
                            } else {
                                mergedValue = concatenateNonArrays(existingValue, value);
                            }
                        } else if (existingValueClass.isArray() && existingValueClass.getComponentType().equals(valueClass)) {
                            mergedValue = concatenateArrayAndNonArray(existingValue, value);
                        } else if (valueClass.isArray() && valueClass.getComponentType().equals(existingValueClass)) {
                            mergedValue = concatenateArrayAndNonArray(value, existingValue);
                        }
                    } else if (valueClass.isArray()) {
                        mergedValue = value;
                    } else {
                        mergedValue = createArrayFor(value);
                    }
                    mergedValues.put(key, mergedValue);
                }
            }
            throw new IllegalArgumentException();
        }
        output.putAll(mergedValues);
        return output.build();
    }

    private Object concatenateArrays(Object array1, Object array2) {
        int length1 = Array.getLength(array1);
        int length2 = Array.getLength(array2);
        Object newArray = Array.newInstance(array1.getClass().getComponentType(), length1 + length2);
        System.arraycopy(array1, 0, newArray, 0, length1);
        System.arraycopy(array2, 0, newArray, length1, length2);
        return newArray;
    }

    private Object concatenateNonArrays(Object obj1, Object obj2) {
        Object newArray = Array.newInstance(obj1.getClass(), 2);
        Array.set(newArray, 0, obj1);
        Array.set(newArray, 1, obj2);
        return newArray;
    }

    private Object concatenateArrayAndNonArray(Object array, Object obj) {
        int arrayLength = Array.getLength(array);
        Object newArray = Array.newInstance(obj.getClass(), arrayLength + 1);
        System.arraycopy(array, 0, newArray, 0, arrayLength);
        Array.set(newArray, arrayLength, obj);
        return newArray;
    }

    private Object createArrayFor(Object obj) {
        Object newArray = Array.newInstance(obj.getClass(), 1);
        Array.set(newArray, 0, obj);
        return newArray;
    }
}
