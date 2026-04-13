package org.greenrobot.greendao.internal;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScope;
import org.greenrobot.greendao.identityscope.IdentityScopeLong;
import org.greenrobot.greendao.identityscope.IdentityScopeObject;
import org.greenrobot.greendao.identityscope.IdentityScopeType;

public final class DaoConfig implements Cloneable {
    public final String[] allColumns;
    public final Database db;
    private IdentityScope<?, ?> identityScope;
    public final boolean keyIsNumeric;
    public final String[] nonPkColumns;
    public final String[] pkColumns;
    public final Property pkProperty;
    public final Property[] properties;
    public final TableStatements statements;
    public final String tablename;

    public DaoConfig(Database db2, Class<? extends AbstractDao<?, ?>> daoClass) {
        this.db = db2;
        try {
            Property property = null;
            this.tablename = (String) daoClass.getField("TABLENAME").get((Object) null);
            Property[] properties2 = reflectProperties(daoClass);
            this.properties = properties2;
            this.allColumns = new String[properties2.length];
            List<String> pkColumnList = new ArrayList<>();
            List<String> nonPkColumnList = new ArrayList<>();
            Property lastPkProperty = null;
            for (int i = 0; i < properties2.length; i++) {
                Property property2 = properties2[i];
                String name = property2.columnName;
                this.allColumns[i] = name;
                if (property2.primaryKey) {
                    pkColumnList.add(name);
                    lastPkProperty = property2;
                } else {
                    nonPkColumnList.add(name);
                }
            }
            this.nonPkColumns = (String[]) nonPkColumnList.toArray(new String[nonPkColumnList.size()]);
            String[] strArr = (String[]) pkColumnList.toArray(new String[pkColumnList.size()]);
            this.pkColumns = strArr;
            boolean z = true;
            property = strArr.length == 1 ? lastPkProperty : property;
            this.pkProperty = property;
            this.statements = new TableStatements(db2, this.tablename, this.allColumns, strArr);
            if (property != null) {
                Class<?> type = property.type;
                if (!type.equals(Long.TYPE) && !type.equals(Long.class) && !type.equals(Integer.TYPE) && !type.equals(Integer.class) && !type.equals(Short.TYPE) && !type.equals(Short.class) && !type.equals(Byte.TYPE)) {
                    if (!type.equals(Byte.class)) {
                        z = false;
                    }
                }
                this.keyIsNumeric = z;
                return;
            }
            this.keyIsNumeric = false;
        } catch (Exception e) {
            throw new DaoException("Could not init DAOConfig", e);
        }
    }

    private static Property[] reflectProperties(Class<? extends AbstractDao<?, ?>> daoClass) {
        Field[] fields = Class.forName(daoClass.getName() + "$Properties").getDeclaredFields();
        ArrayList<Property> propertyList = new ArrayList<>();
        for (Field field : fields) {
            if ((field.getModifiers() & 9) == 9) {
                Object fieldValue = field.get((Object) null);
                if (fieldValue instanceof Property) {
                    propertyList.add((Property) fieldValue);
                }
            }
        }
        Property[] properties2 = new Property[propertyList.size()];
        Iterator<Property> it = propertyList.iterator();
        while (it.hasNext()) {
            Property property = it.next();
            int i = property.ordinal;
            if (properties2[i] == null) {
                properties2[i] = property;
            } else {
                throw new DaoException("Duplicate property ordinals");
            }
        }
        return properties2;
    }

    public DaoConfig(DaoConfig source) {
        this.db = source.db;
        this.tablename = source.tablename;
        this.properties = source.properties;
        this.allColumns = source.allColumns;
        this.pkColumns = source.pkColumns;
        this.nonPkColumns = source.nonPkColumns;
        this.pkProperty = source.pkProperty;
        this.statements = source.statements;
        this.keyIsNumeric = source.keyIsNumeric;
    }

    public DaoConfig clone() {
        return new DaoConfig(this);
    }

    public IdentityScope<?, ?> getIdentityScope() {
        return this.identityScope;
    }

    public void clearIdentityScope() {
        IdentityScope<?, ?> identityScope2 = this.identityScope;
        if (identityScope2 != null) {
            identityScope2.clear();
        }
    }

    public void setIdentityScope(IdentityScope<?, ?> identityScope2) {
        this.identityScope = identityScope2;
    }

    public void initIdentityScope(IdentityScopeType type) {
        if (type == IdentityScopeType.None) {
            this.identityScope = null;
        } else if (type != IdentityScopeType.Session) {
            throw new IllegalArgumentException("Unsupported type: " + type);
        } else if (this.keyIsNumeric) {
            this.identityScope = new IdentityScopeLong();
        } else {
            this.identityScope = new IdentityScopeObject();
        }
    }
}
