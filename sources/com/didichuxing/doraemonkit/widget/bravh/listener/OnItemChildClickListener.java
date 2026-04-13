package com.didichuxing.doraemonkit.widget.bravh.listener;

import android.view.View;
import androidx.annotation.NonNull;
import com.didichuxing.doraemonkit.widget.bravh.BaseQuickAdapter;

public interface OnItemChildClickListener {
    void onItemChildClick(@NonNull BaseQuickAdapter baseQuickAdapter, @NonNull View view, int i);
}
