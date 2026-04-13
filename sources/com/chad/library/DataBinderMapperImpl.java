package com.chad.library;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBinderMapperImpl extends DataBinderMapper {
    private static final SparseIntArray a = new SparseIntArray(0);

    public static class b {
        static final HashMap<String, Integer> a = new HashMap<>(0);
    }

    public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
        if (a.get(layoutId) <= 0 || view.getTag() != null) {
            return null;
        }
        throw new RuntimeException("view must have a tag");
    }

    public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
        if (views == null || views.length == 0 || a.get(layoutId) <= 0 || views[0].getTag() != null) {
            return null;
        }
        throw new RuntimeException("view must have a tag");
    }

    public int getLayoutId(String tag) {
        Integer tmpVal;
        if (tag == null || (tmpVal = b.a.get(tag)) == null) {
            return 0;
        }
        return tmpVal.intValue();
    }

    public String convertBrIdToString(int localId) {
        return a.a.get(localId);
    }

    public List<DataBinderMapper> collectDependencies() {
        ArrayList<DataBinderMapper> result = new ArrayList<>(1);
        result.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
        return result;
    }

    public static class a {
        static final SparseArray<String> a;

        static {
            SparseArray<String> sparseArray = new SparseArray<>(1);
            a = sparseArray;
            sparseArray.put(0, "_all");
        }
    }
}
