package com.didichuxing.doraemonkit.kit.fileexplorer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.widget.recyclerview.AbsRecyclerAdapter;
import com.didichuxing.doraemonkit.widget.recyclerview.AbsViewBinder;

public class TextContentAdapter extends AbsRecyclerAdapter<AbsViewBinder<String>, String> {
    public TextContentAdapter(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    public AbsViewBinder<String> createViewHolder(View view, int viewType) {
        return new TextContentViewHolder(view);
    }

    /* access modifiers changed from: protected */
    public View createView(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return inflater.inflate(R.layout.dk_item_text_content, parent, false);
    }

    public class TextContentViewHolder extends AbsViewBinder<String> {
        private TextView mTextView;

        public TextContentViewHolder(View view) {
            super(view);
        }

        /* access modifiers changed from: protected */
        public void getViews() {
            this.mTextView = (TextView) getView(R.id.text);
        }

        public void bind(String s) {
            this.mTextView.setText(s);
        }
    }
}
