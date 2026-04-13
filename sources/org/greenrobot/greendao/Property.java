package org.greenrobot.greendao;

import java.util.Collection;
import org.greenrobot.greendao.internal.SqlUtils;
import org.greenrobot.greendao.query.WhereCondition;

public class Property {
    public final String columnName;
    public final String name;
    public final int ordinal;
    public final boolean primaryKey;
    public final Class<?> type;

    public Property(int ordinal2, Class<?> type2, String name2, boolean primaryKey2, String columnName2) {
        this.ordinal = ordinal2;
        this.type = type2;
        this.name = name2;
        this.primaryKey = primaryKey2;
        this.columnName = columnName2;
    }

    public WhereCondition eq(Object value) {
        return new WhereCondition.PropertyCondition(this, "=?", value);
    }

    public WhereCondition notEq(Object value) {
        return new WhereCondition.PropertyCondition(this, "<>?", value);
    }

    public WhereCondition like(String value) {
        return new WhereCondition.PropertyCondition(this, " LIKE ?", (Object) value);
    }

    public WhereCondition between(Object value1, Object value2) {
        return new WhereCondition.PropertyCondition(this, " BETWEEN ? AND ?", new Object[]{value1, value2});
    }

    public WhereCondition in(Object... inValues) {
        StringBuilder condition = new StringBuilder(" IN (");
        SqlUtils.appendPlaceholders(condition, inValues.length).append(')');
        return new WhereCondition.PropertyCondition(this, condition.toString(), inValues);
    }

    public WhereCondition in(Collection<?> inValues) {
        return in(inValues.toArray());
    }

    public WhereCondition notIn(Object... notInValues) {
        StringBuilder condition = new StringBuilder(" NOT IN (");
        SqlUtils.appendPlaceholders(condition, notInValues.length).append(')');
        return new WhereCondition.PropertyCondition(this, condition.toString(), notInValues);
    }

    public WhereCondition notIn(Collection<?> notInValues) {
        return notIn(notInValues.toArray());
    }

    public WhereCondition gt(Object value) {
        return new WhereCondition.PropertyCondition(this, ">?", value);
    }

    public WhereCondition lt(Object value) {
        return new WhereCondition.PropertyCondition(this, "<?", value);
    }

    public WhereCondition ge(Object value) {
        return new WhereCondition.PropertyCondition(this, ">=?", value);
    }

    public WhereCondition le(Object value) {
        return new WhereCondition.PropertyCondition(this, "<=?", value);
    }

    public WhereCondition isNull() {
        return new WhereCondition.PropertyCondition(this, " IS NULL");
    }

    public WhereCondition isNotNull() {
        return new WhereCondition.PropertyCondition(this, " IS NOT NULL");
    }
}
