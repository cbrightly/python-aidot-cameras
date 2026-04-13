package com.alibaba.fastjson.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

public class ParameterizedTypeImpl implements ParameterizedType {
    private final Type[] actualTypeArguments;
    private final Type ownerType;
    private final Type rawType;

    public ParameterizedTypeImpl(Type[] actualTypeArguments2, Type ownerType2, Type rawType2) {
        this.actualTypeArguments = actualTypeArguments2;
        this.ownerType = ownerType2;
        this.rawType = rawType2;
    }

    public Type[] getActualTypeArguments() {
        return this.actualTypeArguments;
    }

    public Type getOwnerType() {
        return this.ownerType;
    }

    public Type getRawType() {
        return this.rawType;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ParameterizedTypeImpl that = (ParameterizedTypeImpl) o;
        if (!Arrays.equals(this.actualTypeArguments, that.actualTypeArguments)) {
            return false;
        }
        Type type = this.ownerType;
        if (type == null ? that.ownerType != null : !type.equals(that.ownerType)) {
            return false;
        }
        Type type2 = this.rawType;
        if (type2 != null) {
            return type2.equals(that.rawType);
        }
        if (that.rawType == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        Type[] typeArr = this.actualTypeArguments;
        int i = 0;
        int hashCode = (typeArr != null ? Arrays.hashCode(typeArr) : 0) * 31;
        Type type = this.ownerType;
        int result = (hashCode + (type != null ? type.hashCode() : 0)) * 31;
        Type type2 = this.rawType;
        if (type2 != null) {
            i = type2.hashCode();
        }
        return result + i;
    }
}
