package com.leedarson.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.ArrayList;
import java.util.List;

/* compiled from: WeekCalendarAdapter */
public abstract class g<T> extends BaseAdapter {
    public static ChangeQuickRedirect changeQuickRedirect;
    protected Context c;
    protected List d;

    public abstract int a();

    public abstract View b(int i, View view, g<T>.defpackage.a aVar);

    public g(Context context, List<T> data) {
        ArrayList arrayList;
        this.c = context;
        if (data != null) {
            arrayList = new ArrayList(data);
        }
        this.d = arrayList;
    }

    public int getCount() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 112, new Class[0], Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        List list = this.d;
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public Object getItem(int position) {
        Object[] objArr = {new Integer(position)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 113, new Class[]{Integer.TYPE}, Object.class);
        if (proxy.isSupported) {
            return proxy.result;
        }
        if (position >= this.d.size()) {
            return null;
        }
        return this.d.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View view, ViewGroup viewGroup) {
        WeekCalendarAdapter<T>.ViewHolder holder;
        Object[] objArr = {new Integer(position), view, viewGroup};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 114, new Class[]{Integer.TYPE, View.class, ViewGroup.class}, View.class);
        if (proxy.isSupported) {
            return (View) proxy.result;
        }
        if (view == null) {
            view = View.inflate(this.c, a(), (ViewGroup) null);
            holder = new a(view);
            view.setTag(holder);
        } else if (view.getTag() != null) {
            holder = (a) view.getTag();
        } else {
            view = View.inflate(this.c, a(), (ViewGroup) null);
            holder = new a(view);
            view.setTag(holder);
        }
        return b(position, view, holder);
    }

    /* compiled from: WeekCalendarAdapter */
    public class a {
        public static ChangeQuickRedirect changeQuickRedirect;
        private SparseArray<View> a = new SparseArray<>();
        private View b;

        public a(View convertView) {
            this.b = convertView;
        }

        public <T extends View> T a(int resId) {
            Object[] objArr = {new Integer(resId)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
            PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 120, new Class[]{Integer.TYPE}, View.class);
            if (proxy.isSupported) {
                return (View) proxy.result;
            }
            View v = this.a.get(resId);
            if (v != null) {
                return v;
            }
            View v2 = this.b.findViewById(resId);
            this.a.put(resId, v2);
            return v2;
        }
    }
}
