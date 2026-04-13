package org.greenrobot.greendao.test;

import org.greenrobot.greendao.AbstractDaoMaster;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;

public abstract class AbstractDaoSessionTest<T extends AbstractDaoMaster, S extends AbstractDaoSession> extends DbTest {
    protected T daoMaster;
    private final Class<T> daoMasterClass;
    protected S daoSession;

    public AbstractDaoSessionTest(Class<T> daoMasterClass2) {
        this(daoMasterClass2, true);
    }

    public AbstractDaoSessionTest(Class<T> daoMasterClass2, boolean inMemory) {
        super(inMemory);
        this.daoMasterClass = daoMasterClass2;
    }

    /* access modifiers changed from: protected */
    public void setUp() {
        Class<Database> cls = Database.class;
        super.setUp();
        try {
            this.daoMaster = (AbstractDaoMaster) this.daoMasterClass.getConstructor(new Class[]{cls}).newInstance(new Object[]{this.db});
            this.daoMasterClass.getMethod("createAllTables", new Class[]{cls, Boolean.TYPE}).invoke((Object) null, new Object[]{this.db, false});
            this.daoSession = this.daoMaster.newSession();
        } catch (Exception e) {
            throw new RuntimeException("Could not prepare DAO session test", e);
        }
    }
}
