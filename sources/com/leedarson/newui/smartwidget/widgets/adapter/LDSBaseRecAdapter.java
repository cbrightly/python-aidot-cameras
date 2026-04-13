package com.leedarson.newui.smartwidget.widgets.adapter;

import android.content.Context;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.meituan.robust.ChangeQuickRedirect;
import java.util.ArrayList;

public class LDSBaseRecAdapter<T> extends RecyclerView.Adapter {
    public static ChangeQuickRedirect changeQuickRedirect;
    ArrayList<T> a;
    Context b;

    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    }

    public int getItemCount() {
        return 0;
    }
}
