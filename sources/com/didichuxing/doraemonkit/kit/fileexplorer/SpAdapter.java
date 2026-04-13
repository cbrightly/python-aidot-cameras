package com.didichuxing.doraemonkit.kit.fileexplorer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.constant.SpInputType;
import com.didichuxing.doraemonkit.kit.fileexplorer.SpInputView;
import com.didichuxing.doraemonkit.widget.recyclerview.AbsRecyclerAdapter;
import com.didichuxing.doraemonkit.widget.recyclerview.AbsViewBinder;

public class SpAdapter extends AbsRecyclerAdapter<AbsViewBinder<SpBean>, SpBean> {
    /* access modifiers changed from: private */
    public OnSpDataChangerListener onSpDataChangerListener;

    public interface OnSpDataChangerListener {
        void onDataChanged(SpBean spBean);
    }

    public SpAdapter(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    public AbsViewBinder<SpBean> createViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    /* access modifiers changed from: protected */
    public View createView(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return inflater.inflate(R.layout.dk_item_sp_input, parent, false);
    }

    public class ViewHolder extends AbsViewBinder<SpBean> {
        /* access modifiers changed from: private */
        public SpInputView inputView;
        private TextView key;
        private TextView type;

        public ViewHolder(View view) {
            super(view);
        }

        /* access modifiers changed from: protected */
        public void getViews() {
            this.key = (TextView) getView(R.id.tv_sp_key);
            this.type = (TextView) getView(R.id.tv_sp_type);
            this.inputView = (SpInputView) getView(R.id.input_sp_value);
        }

        public void bind(final SpBean spBean) {
            if (!spBean.value.getClass().getSimpleName().equals(SpInputType.HASHSET)) {
                this.key.setText(spBean.key);
                this.type.setText(spBean.value.getClass().getSimpleName());
                this.inputView.setInput(spBean, new SpInputView.OnDataChangeListener() {
                    public void onDataChanged() {
                        ViewHolder.this.inputView.refresh();
                        if (SpAdapter.this.onSpDataChangerListener != null) {
                            SpAdapter.this.onSpDataChangerListener.onDataChanged(spBean);
                        }
                    }
                });
            }
        }
    }

    public void setOnSpDataChangerListener(OnSpDataChangerListener onSpDataChangerListener2) {
        this.onSpDataChangerListener = onSpDataChangerListener2;
    }
}
