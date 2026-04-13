package com.alibaba.fastjson;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.NotActiveException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.RandomAccess;

public class JSONArray extends JSON implements List<Object>, Cloneable, RandomAccess, Serializable {
    private static final long serialVersionUID = 1;
    protected transient Type componentType;
    private final List<Object> list;
    protected transient Object relatedArray;

    public JSONArray() {
        this.list = new ArrayList();
    }

    public JSONArray(List<Object> list2) {
        if (list2 != null) {
            this.list = list2;
            return;
        }
        throw new IllegalArgumentException("list is null.");
    }

    public JSONArray(int initialCapacity) {
        this.list = new ArrayList(initialCapacity);
    }

    public Object getRelatedArray() {
        return this.relatedArray;
    }

    public void setRelatedArray(Object relatedArray2) {
        this.relatedArray = relatedArray2;
    }

    public Type getComponentType() {
        return this.componentType;
    }

    public void setComponentType(Type componentType2) {
        this.componentType = componentType2;
    }

    public int size() {
        return this.list.size();
    }

    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    public boolean contains(Object o) {
        return this.list.contains(o);
    }

    public Iterator<Object> iterator() {
        return this.list.iterator();
    }

    public Object[] toArray() {
        return this.list.toArray();
    }

    public <T> T[] toArray(T[] a) {
        return this.list.toArray(a);
    }

    public boolean add(Object e) {
        return this.list.add(e);
    }

    public JSONArray fluentAdd(Object e) {
        this.list.add(e);
        return this;
    }

    public boolean remove(Object o) {
        return this.list.remove(o);
    }

    public JSONArray fluentRemove(Object o) {
        this.list.remove(o);
        return this;
    }

    public boolean containsAll(Collection<?> c) {
        return this.list.containsAll(c);
    }

    public boolean addAll(Collection<?> c) {
        return this.list.addAll(c);
    }

    public JSONArray fluentAddAll(Collection<?> c) {
        this.list.addAll(c);
        return this;
    }

    public boolean addAll(int index, Collection<?> c) {
        return this.list.addAll(index, c);
    }

    public JSONArray fluentAddAll(int index, Collection<?> c) {
        this.list.addAll(index, c);
        return this;
    }

    public boolean removeAll(Collection<?> c) {
        return this.list.removeAll(c);
    }

    public JSONArray fluentRemoveAll(Collection<?> c) {
        this.list.removeAll(c);
        return this;
    }

    public boolean retainAll(Collection<?> c) {
        return this.list.retainAll(c);
    }

    public JSONArray fluentRetainAll(Collection<?> c) {
        this.list.retainAll(c);
        return this;
    }

    public void clear() {
        this.list.clear();
    }

    public JSONArray fluentClear() {
        this.list.clear();
        return this;
    }

    public Object set(int index, Object element) {
        if (index == -1) {
            this.list.add(element);
            return null;
        } else if (this.list.size() > index) {
            return this.list.set(index, element);
        } else {
            for (int i = this.list.size(); i < index; i++) {
                this.list.add((Object) null);
            }
            this.list.add(element);
            return null;
        }
    }

    public JSONArray fluentSet(int index, Object element) {
        set(index, element);
        return this;
    }

    public void add(int index, Object element) {
        this.list.add(index, element);
    }

    public JSONArray fluentAdd(int index, Object element) {
        this.list.add(index, element);
        return this;
    }

    public Object remove(int index) {
        return this.list.remove(index);
    }

    public JSONArray fluentRemove(int index) {
        this.list.remove(index);
        return this;
    }

    public int indexOf(Object o) {
        return this.list.indexOf(o);
    }

    public int lastIndexOf(Object o) {
        return this.list.lastIndexOf(o);
    }

    public ListIterator<Object> listIterator() {
        return this.list.listIterator();
    }

    public ListIterator<Object> listIterator(int index) {
        return this.list.listIterator(index);
    }

    public List<Object> subList(int fromIndex, int toIndex) {
        return this.list.subList(fromIndex, toIndex);
    }

    public Object get(int index) {
        return this.list.get(index);
    }

    public JSONObject getJSONObject(int index) {
        Object value = this.list.get(index);
        if (value instanceof JSONObject) {
            return (JSONObject) value;
        }
        if (value instanceof Map) {
            return new JSONObject((Map<String, Object>) (Map) value);
        }
        return (JSONObject) JSON.toJSON(value);
    }

    public JSONArray getJSONArray(int index) {
        Object value = this.list.get(index);
        if (value instanceof JSONArray) {
            return (JSONArray) value;
        }
        if (value instanceof List) {
            return new JSONArray((List<Object>) (List) value);
        }
        return (JSONArray) JSON.toJSON(value);
    }

    public <T> T getObject(int index, Class<T> clazz) {
        return TypeUtils.castToJavaBean(this.list.get(index), clazz);
    }

    public <T> T getObject(int index, Type type) {
        Object obj = this.list.get(index);
        if (type instanceof Class) {
            return TypeUtils.castToJavaBean(obj, (Class) type);
        }
        return JSON.parseObject(JSON.toJSONString(obj), type, new Feature[0]);
    }

    public Boolean getBoolean(int index) {
        Object value = get(index);
        if (value == null) {
            return null;
        }
        return TypeUtils.castToBoolean(value);
    }

    public boolean getBooleanValue(int index) {
        Object value = get(index);
        if (value == null) {
            return false;
        }
        return TypeUtils.castToBoolean(value).booleanValue();
    }

    public Byte getByte(int index) {
        return TypeUtils.castToByte(get(index));
    }

    public byte getByteValue(int index) {
        Byte byteVal = TypeUtils.castToByte(get(index));
        if (byteVal == null) {
            return 0;
        }
        return byteVal.byteValue();
    }

    public Short getShort(int index) {
        return TypeUtils.castToShort(get(index));
    }

    public short getShortValue(int index) {
        Short shortVal = TypeUtils.castToShort(get(index));
        if (shortVal == null) {
            return 0;
        }
        return shortVal.shortValue();
    }

    public Integer getInteger(int index) {
        return TypeUtils.castToInt(get(index));
    }

    public int getIntValue(int index) {
        Integer intVal = TypeUtils.castToInt(get(index));
        if (intVal == null) {
            return 0;
        }
        return intVal.intValue();
    }

    public Long getLong(int index) {
        return TypeUtils.castToLong(get(index));
    }

    public long getLongValue(int index) {
        Long longVal = TypeUtils.castToLong(get(index));
        if (longVal == null) {
            return 0;
        }
        return longVal.longValue();
    }

    public Float getFloat(int index) {
        return TypeUtils.castToFloat(get(index));
    }

    public float getFloatValue(int index) {
        Float floatValue = TypeUtils.castToFloat(get(index));
        if (floatValue == null) {
            return 0.0f;
        }
        return floatValue.floatValue();
    }

    public Double getDouble(int index) {
        return TypeUtils.castToDouble(get(index));
    }

    public double getDoubleValue(int index) {
        Double doubleValue = TypeUtils.castToDouble(get(index));
        if (doubleValue == null) {
            return 0.0d;
        }
        return doubleValue.doubleValue();
    }

    public BigDecimal getBigDecimal(int index) {
        return TypeUtils.castToBigDecimal(get(index));
    }

    public BigInteger getBigInteger(int index) {
        return TypeUtils.castToBigInteger(get(index));
    }

    public String getString(int index) {
        return TypeUtils.castToString(get(index));
    }

    public Date getDate(int index) {
        return TypeUtils.castToDate(get(index));
    }

    public java.sql.Date getSqlDate(int index) {
        return TypeUtils.castToSqlDate(get(index));
    }

    public Timestamp getTimestamp(int index) {
        return TypeUtils.castToTimestamp(get(index));
    }

    public <T> List<T> toJavaList(Class<T> clazz) {
        List<T> list2 = new ArrayList<>(size());
        ParserConfig config = ParserConfig.getGlobalInstance();
        Iterator<Object> it = iterator();
        while (it.hasNext()) {
            list2.add(TypeUtils.cast(it.next(), clazz, config));
        }
        return list2;
    }

    public Object clone() {
        return new JSONArray((List<Object>) new ArrayList(this.list));
    }

    public boolean equals(Object obj) {
        return this.list.equals(obj);
    }

    public int hashCode() {
        return this.list.hashCode();
    }

    private void readObject(ObjectInputStream in) {
        JSONObject.SecureObjectInputStream.ensureFields();
        if (JSONObject.SecureObjectInputStream.fields != null && !JSONObject.SecureObjectInputStream.fields_error) {
            try {
                new JSONObject.SecureObjectInputStream(in).defaultReadObject();
                return;
            } catch (NotActiveException e) {
            }
        }
        in.defaultReadObject();
        for (Object item : this.list) {
            if (item != null) {
                String typeName = item.getClass().getName();
                if (TypeUtils.getClassFromMapping(typeName) == null) {
                    ParserConfig.global.checkAutoType(typeName, (Class<?>) null);
                }
            }
        }
    }
}
