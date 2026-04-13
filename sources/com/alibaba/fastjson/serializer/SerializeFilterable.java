package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public abstract class SerializeFilterable {
    protected List<AfterFilter> afterFilters = null;
    protected List<BeforeFilter> beforeFilters = null;
    protected List<ContextValueFilter> contextValueFilters = null;
    protected List<LabelFilter> labelFilters = null;
    protected List<NameFilter> nameFilters = null;
    protected List<PropertyFilter> propertyFilters = null;
    protected List<PropertyPreFilter> propertyPreFilters = null;
    protected List<ValueFilter> valueFilters = null;
    protected boolean writeDirect = true;

    public List<BeforeFilter> getBeforeFilters() {
        if (this.beforeFilters == null) {
            this.beforeFilters = new ArrayList();
            this.writeDirect = false;
        }
        return this.beforeFilters;
    }

    public List<AfterFilter> getAfterFilters() {
        if (this.afterFilters == null) {
            this.afterFilters = new ArrayList();
            this.writeDirect = false;
        }
        return this.afterFilters;
    }

    public List<NameFilter> getNameFilters() {
        if (this.nameFilters == null) {
            this.nameFilters = new ArrayList();
            this.writeDirect = false;
        }
        return this.nameFilters;
    }

    public List<PropertyPreFilter> getPropertyPreFilters() {
        if (this.propertyPreFilters == null) {
            this.propertyPreFilters = new ArrayList();
            this.writeDirect = false;
        }
        return this.propertyPreFilters;
    }

    public List<LabelFilter> getLabelFilters() {
        if (this.labelFilters == null) {
            this.labelFilters = new ArrayList();
            this.writeDirect = false;
        }
        return this.labelFilters;
    }

    public List<PropertyFilter> getPropertyFilters() {
        if (this.propertyFilters == null) {
            this.propertyFilters = new ArrayList();
            this.writeDirect = false;
        }
        return this.propertyFilters;
    }

    public List<ContextValueFilter> getContextValueFilters() {
        if (this.contextValueFilters == null) {
            this.contextValueFilters = new ArrayList();
            this.writeDirect = false;
        }
        return this.contextValueFilters;
    }

    public List<ValueFilter> getValueFilters() {
        if (this.valueFilters == null) {
            this.valueFilters = new ArrayList();
            this.writeDirect = false;
        }
        return this.valueFilters;
    }

    public void addFilter(SerializeFilter filter) {
        if (filter != null) {
            if (filter instanceof PropertyPreFilter) {
                getPropertyPreFilters().add((PropertyPreFilter) filter);
            }
            if (filter instanceof NameFilter) {
                getNameFilters().add((NameFilter) filter);
            }
            if (filter instanceof ValueFilter) {
                getValueFilters().add((ValueFilter) filter);
            }
            if (filter instanceof ContextValueFilter) {
                getContextValueFilters().add((ContextValueFilter) filter);
            }
            if (filter instanceof PropertyFilter) {
                getPropertyFilters().add((PropertyFilter) filter);
            }
            if (filter instanceof BeforeFilter) {
                getBeforeFilters().add((BeforeFilter) filter);
            }
            if (filter instanceof AfterFilter) {
                getAfterFilters().add((AfterFilter) filter);
            }
            if (filter instanceof LabelFilter) {
                getLabelFilters().add((LabelFilter) filter);
            }
        }
    }

    public boolean applyName(JSONSerializer jsonBeanDeser, Object object, String key) {
        List<PropertyPreFilter> list = jsonBeanDeser.propertyPreFilters;
        if (list != null) {
            for (PropertyPreFilter filter : list) {
                if (!filter.apply(jsonBeanDeser, object, key)) {
                    return false;
                }
            }
        }
        List<PropertyPreFilter> list2 = this.propertyPreFilters;
        if (list2 == null) {
            return true;
        }
        for (PropertyPreFilter filter2 : list2) {
            if (!filter2.apply(jsonBeanDeser, object, key)) {
                return false;
            }
        }
        return true;
    }

    public boolean apply(JSONSerializer jsonBeanDeser, Object object, String key, Object propertyValue) {
        List<PropertyFilter> list = jsonBeanDeser.propertyFilters;
        if (list != null) {
            for (PropertyFilter propertyFilter : list) {
                if (!propertyFilter.apply(object, key, propertyValue)) {
                    return false;
                }
            }
        }
        List<PropertyFilter> list2 = this.propertyFilters;
        if (list2 == null) {
            return true;
        }
        for (PropertyFilter propertyFilter2 : list2) {
            if (!propertyFilter2.apply(object, key, propertyValue)) {
                return false;
            }
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public String processKey(JSONSerializer jsonBeanDeser, Object object, String key, Object propertyValue) {
        List<NameFilter> list = jsonBeanDeser.nameFilters;
        if (list != null) {
            for (NameFilter nameFilter : list) {
                key = nameFilter.process(object, key, propertyValue);
            }
        }
        List<NameFilter> list2 = this.nameFilters;
        if (list2 != null) {
            for (NameFilter nameFilter2 : list2) {
                key = nameFilter2.process(object, key, propertyValue);
            }
        }
        return key;
    }

    /* access modifiers changed from: protected */
    public Object processValue(JSONSerializer jsonBeanDeser, BeanContext beanContext, Object object, String key, Object propertyValue) {
        return processValue(jsonBeanDeser, beanContext, object, key, propertyValue, 0);
    }

    /* access modifiers changed from: protected */
    public Object processValue(JSONSerializer jsonBeanDeser, BeanContext beanContext, Object object, String key, Object propertyValue, int features) {
        if (propertyValue != null) {
            int i = jsonBeanDeser.out.features;
            SerializerFeature serializerFeature = SerializerFeature.WriteNonStringValueAsString;
            if ((SerializerFeature.isEnabled(i, features, serializerFeature) || !(beanContext == null || (beanContext.getFeatures() & serializerFeature.mask) == 0)) && ((propertyValue instanceof Number) || (propertyValue instanceof Boolean))) {
                String format = null;
                if ((propertyValue instanceof Number) && beanContext != null) {
                    format = beanContext.getFormat();
                }
                if (format != null) {
                    propertyValue = new DecimalFormat(format).format(propertyValue);
                } else {
                    propertyValue = propertyValue.toString();
                }
            } else if (beanContext != null && beanContext.isJsonDirect()) {
                propertyValue = JSON.parse((String) propertyValue);
            }
        }
        List<ValueFilter> list = jsonBeanDeser.valueFilters;
        if (list != null) {
            for (ValueFilter valueFilter : list) {
                propertyValue = valueFilter.process(object, key, propertyValue);
            }
        }
        List<ValueFilter> valueFilters2 = this.valueFilters;
        if (valueFilters2 != null) {
            for (ValueFilter valueFilter2 : valueFilters2) {
                propertyValue = valueFilter2.process(object, key, propertyValue);
            }
        }
        List<ContextValueFilter> list2 = jsonBeanDeser.contextValueFilters;
        if (list2 != null) {
            for (ContextValueFilter valueFilter3 : list2) {
                propertyValue = valueFilter3.process(beanContext, object, key, propertyValue);
            }
        }
        List<ContextValueFilter> list3 = this.contextValueFilters;
        if (list3 != null) {
            for (ContextValueFilter valueFilter4 : list3) {
                propertyValue = valueFilter4.process(beanContext, object, key, propertyValue);
            }
        }
        return propertyValue;
    }

    /* access modifiers changed from: protected */
    public boolean writeDirect(JSONSerializer jsonBeanDeser) {
        return jsonBeanDeser.out.writeDirect && this.writeDirect && jsonBeanDeser.writeDirect;
    }
}
