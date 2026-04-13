package com.leedarson.serviceimpl.database.manager;

import com.meituan.robust.ChangeQuickRedirect;
import java.util.Map;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

/* compiled from: DaoSession */
public class c extends AbstractDaoSession {
    public static ChangeQuickRedirect changeQuickRedirect;
    private final DaoConfig a;
    private final NoteDao b;

    public c(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig> daoConfigMap) {
        super(db);
        DaoConfig clone = daoConfigMap.get(NoteDao.class).clone();
        this.a = clone;
        clone.initIdentityScope(type);
        NoteDao noteDao = new NoteDao(clone, this);
        this.b = noteDao;
        registerDao(e.class, noteDao);
    }
}
