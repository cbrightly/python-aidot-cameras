package org.greenrobot.greendao.test;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.DaoLog;
import org.greenrobot.greendao.InternalUnitTestDaoAccess;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScope;

public abstract class AbstractDaoTest<D extends AbstractDao<T, K>, T, K> extends DbTest {
    protected D dao;
    protected InternalUnitTestDaoAccess<T, K> daoAccess;
    protected final Class<D> daoClass;
    protected IdentityScope<K, T> identityScopeForDao;
    protected Property pkColumn;

    public AbstractDaoTest(Class<D> daoClass2) {
        this(daoClass2, true);
    }

    public AbstractDaoTest(Class<D> daoClass2, boolean inMemory) {
        super(inMemory);
        this.daoClass = daoClass2;
    }

    public void setIdentityScopeBeforeSetUp(IdentityScope<K, T> identityScope) {
        this.identityScopeForDao = identityScope;
    }

    /* access modifiers changed from: protected */
    public void setUp() {
        super.setUp();
        try {
            setUpTableForDao();
            InternalUnitTestDaoAccess<T, K> internalUnitTestDaoAccess = new InternalUnitTestDaoAccess<>(this.db, this.daoClass, this.identityScopeForDao);
            this.daoAccess = internalUnitTestDaoAccess;
            this.dao = internalUnitTestDaoAccess.getDao();
        } catch (Exception e) {
            throw new RuntimeException("Could not prepare DAO Test", e);
        }
    }

    /* access modifiers changed from: protected */
    public void setUpTableForDao() {
        try {
            this.daoClass.getMethod("createTable", new Class[]{Database.class, Boolean.TYPE}).invoke((Object) null, new Object[]{this.db, false});
        } catch (NoSuchMethodException e) {
            DaoLog.i("No createTable method");
        }
    }

    /* access modifiers changed from: protected */
    public void clearIdentityScopeIfAny() {
        IdentityScope<K, T> identityScope = this.identityScopeForDao;
        if (identityScope != null) {
            identityScope.clear();
            DaoLog.d("Identity scope cleared");
            return;
        }
        DaoLog.d("No identity scope to clear");
    }

    /* access modifiers changed from: protected */
    public void logTableDump() {
        logTableDump(this.dao.getTablename());
    }
}
