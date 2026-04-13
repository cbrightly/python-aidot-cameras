package com.zhy.view.flowlayout;

import android.util.Log;
import android.view.View;
import java.util.HashSet;
import java.util.List;

/* compiled from: TagAdapter */
public abstract class a<T> {
    private List<T> a;
    private C0236a b;
    @Deprecated
    private HashSet<Integer> c = new HashSet<>();

    /* renamed from: com.zhy.view.flowlayout.a$a  reason: collision with other inner class name */
    /* compiled from: TagAdapter */
    public interface C0236a {
    }

    public abstract View d(FlowLayout flowLayout, int i, T t);

    public a(List<T> datas) {
        this.a = datas;
    }

    /* access modifiers changed from: package-private */
    public void setOnDataChangedListener(C0236a listener) {
        this.b = listener;
    }

    /* access modifiers changed from: package-private */
    @Deprecated
    public HashSet<Integer> c() {
        return this.c;
    }

    public int a() {
        List<T> list = this.a;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public T b(int position) {
        return this.a.get(position);
    }

    public void e(int position, View view) {
        Log.d("zhy", "onSelected " + position);
    }

    public void g(int position, View view) {
        Log.d("zhy", "unSelected " + position);
    }

    public boolean f(int position, T t) {
        return false;
    }
}
