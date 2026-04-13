package meshsdk.sql;

import java.util.Map;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

public class DaoSession extends AbstractDaoSession {
    private final MeshDictBeanDao meshDictBeanDao;
    private final DaoConfig meshDictBeanDaoConfig;
    private final RandomOffsetBeanDao randomOffsetBeanDao;
    private final DaoConfig randomOffsetBeanDaoConfig;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig> daoConfigMap) {
        super(db);
        DaoConfig clone = daoConfigMap.get(MeshDictBeanDao.class).clone();
        this.meshDictBeanDaoConfig = clone;
        clone.initIdentityScope(type);
        DaoConfig clone2 = daoConfigMap.get(RandomOffsetBeanDao.class).clone();
        this.randomOffsetBeanDaoConfig = clone2;
        clone2.initIdentityScope(type);
        MeshDictBeanDao meshDictBeanDao2 = new MeshDictBeanDao(clone, this);
        this.meshDictBeanDao = meshDictBeanDao2;
        RandomOffsetBeanDao randomOffsetBeanDao2 = new RandomOffsetBeanDao(clone2, this);
        this.randomOffsetBeanDao = randomOffsetBeanDao2;
        registerDao(MeshDictBean.class, meshDictBeanDao2);
        registerDao(RandomOffsetBean.class, randomOffsetBeanDao2);
    }

    public void clear() {
        this.meshDictBeanDaoConfig.clearIdentityScope();
        this.randomOffsetBeanDaoConfig.clearIdentityScope();
    }

    public MeshDictBeanDao getMeshDictBeanDao() {
        return this.meshDictBeanDao;
    }

    public RandomOffsetBeanDao getRandomOffsetBeanDao() {
        return this.randomOffsetBeanDao;
    }
}
