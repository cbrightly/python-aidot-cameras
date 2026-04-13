package com.typesafe.config.impl;

import com.typesafe.config.ConfigException;
import com.typesafe.config.ConfigValue;
import com.typesafe.config.a;
import com.typesafe.config.b;
import com.typesafe.config.c;
import com.typesafe.config.e;
import com.typesafe.config.j;
import com.typesafe.config.k;
import com.typesafe.config.l;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigBeanImpl {
    public static <T> T createInternal(a config, Class<T> clazz) {
        a aVar = config;
        Class<T> cls = clazz;
        if (((c0) aVar).root().resolveStatus() == a0.RESOLVED) {
            Map<String, AbstractConfigValue> configProps = new HashMap<>();
            Map<String, String> originalNames = new HashMap<>();
            for (Map.Entry<String, ConfigValue> configProp : config.root().entrySet()) {
                String originalName = configProp.getKey();
                String camelName = g.h(originalName);
                if (!originalNames.containsKey(camelName) || originalName.equals(camelName)) {
                    configProps.put(camelName, (AbstractConfigValue) configProp.getValue());
                    originalNames.put(camelName, originalName);
                }
            }
            try {
                BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
                try {
                    List<PropertyDescriptor> beanProps = new ArrayList<>();
                    char c = 0;
                    for (PropertyDescriptor beanProp : beanInfo.getPropertyDescriptors()) {
                        if (beanProp.getReadMethod() != null) {
                            if (beanProp.getWriteMethod() != null) {
                                beanProps.add(beanProp);
                            }
                        }
                    }
                    List<ConfigException.ValidationProblem> problems = new ArrayList<>();
                    for (PropertyDescriptor beanProp2 : beanProps) {
                        k expectedType = getValueTypeOrNull(beanProp2.getWriteMethod().getParameterTypes()[c]);
                        if (expectedType != null) {
                            String name = originalNames.get(beanProp2.getName());
                            if (name == null) {
                                name = beanProp2.getName();
                            }
                            s path = s.f(name);
                            AbstractConfigValue configValue = configProps.get(beanProp2.getName());
                            if (configValue != null) {
                                c0.checkValid(path, expectedType, configValue, problems);
                            } else if (!isOptionalProperty(cls, beanProp2)) {
                                c0.addMissing(problems, expectedType, path, config.origin());
                            }
                        }
                        c = 0;
                    }
                    if (problems.isEmpty()) {
                        T bean = clazz.newInstance();
                        for (PropertyDescriptor beanProp3 : beanProps) {
                            Method setter = beanProp3.getWriteMethod();
                            Type parameterType = setter.getGenericParameterTypes()[0];
                            Class<?> parameterClass = setter.getParameterTypes()[0];
                            String configPropName = originalNames.get(beanProp3.getName());
                            if (configPropName != null) {
                                setter.invoke(bean, new Object[]{getValue(cls, parameterType, parameterClass, aVar, configPropName)});
                            } else if (!isOptionalProperty(cls, beanProp3)) {
                                throw new ConfigException.Missing(beanProp3.getName());
                            }
                        }
                        return bean;
                    }
                    throw new ConfigException.ValidationFailed(problems);
                } catch (InstantiationException e) {
                    throw new ConfigException.BadBean(clazz.getName() + " needs a public no-args constructor to be used as a bean", e);
                } catch (IllegalAccessException e2) {
                    throw new ConfigException.BadBean(clazz.getName() + " getters and setters are not accessible, they must be for use as a bean", e2);
                } catch (InvocationTargetException e3) {
                    throw new ConfigException.BadBean("Calling bean method on " + clazz.getName() + " caused an exception", e3);
                }
            } catch (IntrospectionException e4) {
                throw new ConfigException.BadBean("Could not get bean information for class " + clazz.getName(), e4);
            }
        } else {
            throw new ConfigException.NotResolved("need to Config#resolve() a config before using it to initialize a bean, see the API docs for Config#resolve()");
        }
    }

    private static Object getValue(Class<?> beanClass, Type parameterType, Class<?> parameterClass, a config, String configPropName) {
        Class<Object> cls = Object.class;
        Class<String> cls2 = String.class;
        if (parameterClass == Boolean.class || parameterClass == Boolean.TYPE) {
            return Boolean.valueOf(config.getBoolean(configPropName));
        }
        if (parameterClass == Integer.class || parameterClass == Integer.TYPE) {
            return Integer.valueOf(config.getInt(configPropName));
        }
        if (parameterClass == Double.class || parameterClass == Double.TYPE) {
            return Double.valueOf(config.getDouble(configPropName));
        }
        if (parameterClass == Long.class || parameterClass == Long.TYPE) {
            return Long.valueOf(config.getLong(configPropName));
        }
        if (parameterClass == cls2) {
            return config.getString(configPropName);
        }
        if (parameterClass == Duration.class) {
            return config.getDuration(configPropName);
        }
        if (parameterClass == c.class) {
            return config.getMemorySize(configPropName);
        }
        if (parameterClass == cls) {
            return config.getAnyRef(configPropName);
        }
        if (parameterClass == List.class) {
            return getListValue(beanClass, parameterType, parameterClass, config, configPropName);
        }
        if (parameterClass == Map.class) {
            Type[] typeArgs = ((ParameterizedType) parameterType).getActualTypeArguments();
            if (typeArgs[0] == cls2 && typeArgs[1] == cls) {
                return config.getObject(configPropName).unwrapped();
            }
            throw new ConfigException.BadBean("Bean property '" + configPropName + "' of class " + beanClass.getName() + " has unsupported Map<" + typeArgs[0] + "," + typeArgs[1] + ">, only Map<String,Object> is supported right now");
        } else if (parameterClass == a.class) {
            return config.getConfig(configPropName);
        } else {
            if (parameterClass == e.class) {
                return config.getObject(configPropName);
            }
            if (parameterClass == j.class) {
                return config.getValue(configPropName);
            }
            if (parameterClass == b.class) {
                return config.getList(configPropName);
            }
            if (parameterClass.isEnum()) {
                return config.getEnum(parameterClass, configPropName);
            }
            if (hasAtLeastOneBeanProperty(parameterClass)) {
                return createInternal(config.getConfig(configPropName), parameterClass);
            }
            throw new ConfigException.BadBean("Bean property " + configPropName + " of class " + beanClass.getName() + " has unsupported type " + parameterType);
        }
    }

    private static Object getListValue(Class<?> beanClass, Type parameterType, Class<?> cls, a config, String configPropName) {
        Type elementType = ((ParameterizedType) parameterType).getActualTypeArguments()[0];
        if (elementType == Boolean.class) {
            return config.getBooleanList(configPropName);
        }
        if (elementType == Integer.class) {
            return config.getIntList(configPropName);
        }
        if (elementType == Double.class) {
            return config.getDoubleList(configPropName);
        }
        if (elementType == Long.class) {
            return config.getLongList(configPropName);
        }
        if (elementType == String.class) {
            return config.getStringList(configPropName);
        }
        if (elementType == Duration.class) {
            return config.getDurationList(configPropName);
        }
        if (elementType == c.class) {
            return config.getMemorySizeList(configPropName);
        }
        if (elementType == Object.class) {
            return config.getAnyRefList(configPropName);
        }
        if (elementType == a.class) {
            return config.getConfigList(configPropName);
        }
        if (elementType == e.class) {
            return config.getObjectList(configPropName);
        }
        if (elementType == j.class) {
            return config.getList(configPropName);
        }
        if (((Class) elementType).isEnum()) {
            return config.getEnumList((Class) elementType, configPropName);
        }
        if (hasAtLeastOneBeanProperty((Class) elementType)) {
            List<Object> beanList = new ArrayList<>();
            for (a listMember : config.getConfigList(configPropName)) {
                beanList.add(createInternal(listMember, (Class) elementType));
            }
            return beanList;
        }
        throw new ConfigException.BadBean("Bean property '" + configPropName + "' of class " + beanClass.getName() + " has unsupported list element type " + elementType);
    }

    private static k getValueTypeOrNull(Class<?> parameterClass) {
        if (parameterClass == Boolean.class || parameterClass == Boolean.TYPE) {
            return k.BOOLEAN;
        }
        if (parameterClass == Integer.class || parameterClass == Integer.TYPE) {
            return k.NUMBER;
        }
        if (parameterClass == Double.class || parameterClass == Double.TYPE) {
            return k.NUMBER;
        }
        if (parameterClass == Long.class || parameterClass == Long.TYPE) {
            return k.NUMBER;
        }
        if (parameterClass == String.class) {
            return k.STRING;
        }
        if (parameterClass == Duration.class || parameterClass == c.class) {
            return null;
        }
        if (parameterClass == List.class) {
            return k.LIST;
        }
        if (parameterClass == Map.class) {
            return k.OBJECT;
        }
        if (parameterClass == a.class) {
            return k.OBJECT;
        }
        if (parameterClass == e.class) {
            return k.OBJECT;
        }
        if (parameterClass == b.class) {
            return k.LIST;
        }
        return null;
    }

    private static boolean hasAtLeastOneBeanProperty(Class<?> clazz) {
        try {
            for (PropertyDescriptor beanProp : Introspector.getBeanInfo(clazz).getPropertyDescriptors()) {
                if (beanProp.getReadMethod() != null && beanProp.getWriteMethod() != null) {
                    return true;
                }
            }
            return false;
        } catch (IntrospectionException e) {
            return false;
        }
    }

    private static boolean isOptionalProperty(Class beanClass, PropertyDescriptor beanProp) {
        return ((l[]) getField(beanClass, beanProp.getName()).getAnnotationsByType(l.class)).length > 0;
    }

    private static Field getField(Class beanClass, String fieldName) {
        try {
            Field field = beanClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field;
        } catch (NoSuchFieldException e) {
            Class beanClass2 = beanClass.getSuperclass();
            if (beanClass2 == null) {
                return null;
            }
            return getField(beanClass2, fieldName);
        }
    }
}
