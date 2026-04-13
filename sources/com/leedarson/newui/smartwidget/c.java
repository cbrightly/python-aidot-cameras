package com.leedarson.newui.smartwidget;

import android.util.Log;
import com.leedarson.base.http.exception.ApiException;
import com.leedarson.base.http.observer.l;
import com.leedarson.base.presenters.a;
import com.leedarson.newui.cloud_play_back.repos.beans.LDSBasePageBean;
import com.leedarson.newui.cloud_play_back.repos.beans.LDSPageDataWrapBean;
import com.leedarson.newui.repos.beans.EventListRequestParamsBean;
import com.leedarson.newui.repos.o;
import com.leedarson.utils.e;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.sensorsdata.analytics.android.sdk.util.TimeUtils;
import io.reactivex.disposables.b;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/* compiled from: SecurityCamsPresenter */
public class c extends a<d, SecurityCamsActivity> {
    public static ChangeQuickRedirect changeQuickRedirect;
    private o f = new o();
    private ArrayList<b> g = new ArrayList<>();

    public c(d view, SecurityCamsActivity activity) {
        super(view, activity);
    }

    public void r(String id, int i) {
        if (!PatchProxy.proxy(new Object[]{id, new Integer(i)}, this, changeQuickRedirect, false, 4755, new Class[]{String.class, Integer.TYPE}, Void.TYPE).isSupported) {
            EventListRequestParamsBean requestParamsBean = new EventListRequestParamsBean();
            ArrayList<String> deviceIds = new ArrayList<>();
            deviceIds.add(id);
            requestParamsBean.deviceIds = deviceIds;
            requestParamsBean.areaIds = new ArrayList<>();
            requestParamsBean.eventCodes = new ArrayList<>();
            String time = new SimpleDateFormat(TimeUtils.YYYY_MM_DD).format(new Date(System.currentTimeMillis()));
            long startTime = e.b(time + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
            long endTime = e.b(time + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
            requestParamsBean.recordSta = startTime;
            requestParamsBean.recordEnd = endTime;
            requestParamsBean.pageNum = 1;
            requestParamsBean.pageSize = 10;
            this.g.add(this.f.e(requestParamsBean).c(l.c()).I(new b(this, i), a.c));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: s */
    public /* synthetic */ void t(int i, LDSBasePageBean eventListItemResponseBean) {
        if (!PatchProxy.proxy(new Object[]{new Integer(i), eventListItemResponseBean}, this, changeQuickRedirect, false, 4758, new Class[]{Integer.TYPE, LDSBasePageBean.class}, Void.TYPE).isSupported) {
            Log.d("SecurityCamsPresenter", "getEventList: " + eventListItemResponseBean.data);
            T t = eventListItemResponseBean.data;
            if (t != null && ((LDSPageDataWrapBean) t).list != null && ((LDSPageDataWrapBean) t).list.size() != 0) {
                for (M itemBean : ((LDSPageDataWrapBean) eventListItemResponseBean.data).list) {
                    itemBean.view_type = 0;
                }
                ((d) m()).y(((LDSPageDataWrapBean) eventListItemResponseBean.data).list, i);
            }
        }
    }

    static /* synthetic */ void u(Throwable throwable) {
        if (!PatchProxy.proxy(new Object[]{throwable}, (Object) null, changeQuickRedirect, true, 4757, new Class[]{Throwable.class}, Void.TYPE).isSupported && (throwable instanceof ApiException)) {
            com.leedarson.base.logger.a.b("securityPlaybackDrop", " getEventLists.exception =" + ((ApiException) throwable).getMsg());
        }
    }

    public void v() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4756, new Class[0], Void.TYPE).isSupported) {
            Iterator<b> it = this.g.iterator();
            while (it.hasNext()) {
                b disposable = it.next();
                if (disposable != null && !disposable.isDisposed()) {
                    disposable.dispose();
                }
            }
        }
    }
}
