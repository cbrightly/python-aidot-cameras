package org.greenrobot.greendao.query;

import org.greenrobot.greendao.AbstractDao;

public abstract class AbstractQueryWithLimit<T> extends AbstractQuery<T> {
    protected final int limitPosition;
    protected final int offsetPosition;

    protected AbstractQueryWithLimit(AbstractDao<T, ?> dao, String sql, String[] initialValues, int limitPosition2, int offsetPosition2) {
        super(dao, sql, initialValues);
        this.limitPosition = limitPosition2;
        this.offsetPosition = offsetPosition2;
    }

    public AbstractQueryWithLimit<T> setParameter(int index, Object parameter) {
        if (index < 0 || (index != this.limitPosition && index != this.offsetPosition)) {
            return (AbstractQueryWithLimit) super.setParameter(index, parameter);
        }
        throw new IllegalArgumentException("Illegal parameter index: " + index);
    }

    public void setLimit(int limit) {
        checkThread();
        int i = this.limitPosition;
        if (i != -1) {
            this.parameters[i] = Integer.toString(limit);
            return;
        }
        throw new IllegalStateException("Limit must be set with QueryBuilder before it can be used here");
    }

    public void setOffset(int offset) {
        checkThread();
        int i = this.offsetPosition;
        if (i != -1) {
            this.parameters[i] = Integer.toString(offset);
            return;
        }
        throw new IllegalStateException("Offset must be set with QueryBuilder before it can be used here");
    }
}
