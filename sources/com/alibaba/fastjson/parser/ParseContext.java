package com.alibaba.fastjson.parser;

import com.meituan.robust.Constants;
import java.lang.reflect.Type;

public class ParseContext {
    public final Object fieldName;
    public final int level;
    public Object object;
    public final ParseContext parent;
    private transient String path;
    public Type type;

    public ParseContext(ParseContext parent2, Object object2, Object fieldName2) {
        this.parent = parent2;
        this.object = object2;
        this.fieldName = fieldName2;
        this.level = parent2 == null ? 0 : parent2.level + 1;
    }

    public String toString() {
        if (this.path == null) {
            if (this.parent == null) {
                this.path = "$";
            } else if (this.fieldName instanceof Integer) {
                this.path = this.parent.toString() + Constants.ARRAY_TYPE + this.fieldName + "]";
            } else {
                this.path = this.parent.toString() + "." + this.fieldName;
            }
        }
        return this.path;
    }
}
