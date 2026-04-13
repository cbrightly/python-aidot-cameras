package com.leedarson.serviceimpl.matter;

import com.leedarson.serviceimpl.bean.MatterDeviceBean;
import com.meituan.robust.ChangeQuickRedirect;
import java.util.Map;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

/* compiled from: DaoSession */
public class b extends AbstractDaoSession {
    public static ChangeQuickRedirect changeQuickRedirect;
    private final DaoConfig a;
    private final MatterDeviceBeanDao b;

    public b(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig> daoConfigMap) {
        super(db);
        DaoConfig clone = daoConfigMap.get(MatterDeviceBeanDao.class).clone();
        this.a = clone;
        clone.initIdentityScope(type);
        MatterDeviceBeanDao matterDeviceBeanDao = new MatterDeviceBeanDao(clone, this);
        this.b = matterDeviceBeanDao;
        registerDao(MatterDeviceBean.class, matterDeviceBeanDao);
    }

    public MatterDeviceBeanDao a() {
        return this.b;
    }
}
