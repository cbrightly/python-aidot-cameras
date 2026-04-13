package com.leedarson.serviceimpl.sql;

import android.content.Context;
import com.leedarson.serviceimpl.bean.MatterDeviceBean;
import com.leedarson.serviceimpl.matter.MatterDeviceBeanDao;
import com.leedarson.serviceimpl.matter.a;
import com.leedarson.serviceimpl.matter.b;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.List;
import kotlin.jvm.internal.k;
import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: MatterSql.kt */
public final class a {
    @NotNull
    public static final a a = new a();
    @NotNull
    private static String b = "matter.db";
    @Nullable
    private static b c;
    public static ChangeQuickRedirect changeQuickRedirect;

    private a() {
    }

    public final void c(@NotNull Context context) {
        if (!PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, 8764, new Class[]{Context.class}, Void.TYPE).isSupported) {
            k.e(context, "context");
            c = new com.leedarson.serviceimpl.matter.a(new a.C0151a(context, b).getWritableDb()).a();
        }
    }

    public final void d(@NotNull MatterDeviceBean dev) {
        MatterDeviceBeanDao a2;
        if (!PatchProxy.proxy(new Object[]{dev}, this, changeQuickRedirect, false, 8765, new Class[]{MatterDeviceBean.class}, Void.TYPE).isSupported) {
            k.e(dev, "dev");
            b bVar = c;
            if (bVar != null && (a2 = bVar.a()) != null) {
                a2.insertOrReplace(dev);
            }
        }
    }

    public final void a(@NotNull MatterDeviceBean dev) {
        MatterDeviceBeanDao a2;
        if (!PatchProxy.proxy(new Object[]{dev}, this, changeQuickRedirect, false, 8766, new Class[]{MatterDeviceBean.class}, Void.TYPE).isSupported) {
            k.e(dev, "dev");
            b bVar = c;
            if (bVar != null && (a2 = bVar.a()) != null) {
                a2.delete(dev);
            }
        }
    }

    @Nullable
    public final List<MatterDeviceBean> b(@Nullable String houseId) {
        MatterDeviceBeanDao a2;
        QueryBuilder queryBuilder;
        QueryBuilder where;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{houseId}, this, changeQuickRedirect, false, 8767, new Class[]{String.class}, List.class);
        if (proxy.isSupported) {
            return (List) proxy.result;
        }
        b bVar = c;
        if (bVar == null || (a2 = bVar.a()) == null || (queryBuilder = a2.queryBuilder()) == null || (where = queryBuilder.where(MatterDeviceBeanDao.Properties.HouseId.eq(houseId), new WhereCondition[0])) == null) {
            return null;
        }
        return where.list();
    }
}
