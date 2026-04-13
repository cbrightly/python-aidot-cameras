package com.zhy.view.flowlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import com.zhy.view.flowlayout.a;
import java.util.HashSet;
import java.util.Set;

public class TagFlowLayout extends FlowLayout implements a.C0236a {
    private b a1;
    private Set<Integer> p0;
    /* access modifiers changed from: private */
    public c p1;
    private a y;
    private int z;

    public interface b {
        void a(Set<Integer> set);
    }

    public interface c {
        boolean a(View view, int i, FlowLayout flowLayout);
    }

    public TagFlowLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.z = -1;
        this.p0 = new HashSet();
        TypedArray ta = context.obtainStyledAttributes(attrs, R$styleable.TagFlowLayout);
        this.z = ta.getInt(R$styleable.TagFlowLayout_max_select, -1);
        ta.recycle();
    }

    public TagFlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagFlowLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int cCount = getChildCount();
        for (int i = 0; i < cCount; i++) {
            TagView tagView = (TagView) getChildAt(i);
            if (tagView.getVisibility() != 8 && tagView.getTagView().getVisibility() == 8) {
                tagView.setVisibility(8);
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setOnSelectListener(b onSelectListener) {
        this.a1 = onSelectListener;
    }

    public void setOnTagClickListener(c onTagClickListener) {
        this.p1 = onTagClickListener;
    }

    public void setAdapter(a adapter) {
        this.y = adapter;
        adapter.setOnDataChangedListener(this);
        this.p0.clear();
        c();
    }

    private void c() {
        removeAllViews();
        a adapter = this.y;
        HashSet preCheckedList = this.y.c();
        for (int i = 0; i < adapter.a(); i++) {
            View tagView = adapter.d(this, i, adapter.b(i));
            TagView tagViewContainer = new TagView(getContext());
            tagView.setDuplicateParentStateEnabled(true);
            if (tagView.getLayoutParams() != null) {
                tagViewContainer.setLayoutParams(tagView.getLayoutParams());
            } else {
                ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(-2, -2);
                lp.setMargins(d(getContext(), 5.0f), d(getContext(), 5.0f), d(getContext(), 5.0f), d(getContext(), 5.0f));
                tagViewContainer.setLayoutParams(lp);
            }
            tagView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
            tagViewContainer.addView(tagView);
            addView(tagViewContainer);
            if (preCheckedList.contains(Integer.valueOf(i))) {
                f(i, tagViewContainer);
            }
            if (this.y.f(i, adapter.b(i))) {
                f(i, tagViewContainer);
            }
            tagView.setClickable(false);
            tagViewContainer.setOnClickListener(new a(tagViewContainer, i));
        }
        this.p0.addAll(preCheckedList);
    }

    public class a implements View.OnClickListener {
        final /* synthetic */ TagView c;
        final /* synthetic */ int d;

        a(TagView tagView, int i) {
            this.c = tagView;
            this.d = i;
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            View view2 = view;
            TagFlowLayout.this.e(this.c, this.d);
            if (TagFlowLayout.this.p1 != null) {
                TagFlowLayout.this.p1.a(this.c, this.d, TagFlowLayout.this);
            }
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public void setMaxSelectCount(int count) {
        if (this.p0.size() > count) {
            Log.w("TagFlowLayout", "you has already select more than " + count + " views , so it will be clear .");
            this.p0.clear();
        }
        this.z = count;
    }

    public Set<Integer> getSelectedList() {
        return new HashSet(this.p0);
    }

    private void f(int position, TagView view) {
        view.setChecked(true);
        this.y.e(position, view.getTagView());
    }

    private void g(int position, TagView view) {
        view.setChecked(false);
        this.y.g(position, view.getTagView());
    }

    /* access modifiers changed from: private */
    public void e(TagView child, int position) {
        if (child.isChecked()) {
            g(position, child);
            this.p0.remove(Integer.valueOf(position));
        } else if (this.z == 1 && this.p0.size() == 1) {
            Integer preIndex = this.p0.iterator().next();
            g(preIndex.intValue(), (TagView) getChildAt(preIndex.intValue()));
            f(position, child);
            this.p0.remove(preIndex);
            this.p0.add(Integer.valueOf(position));
        } else if (this.z <= 0 || this.p0.size() < this.z) {
            f(position, child);
            this.p0.add(Integer.valueOf(position));
        } else {
            return;
        }
        b bVar = this.a1;
        if (bVar != null) {
            bVar.a(new HashSet(this.p0));
        }
    }

    public a getAdapter() {
        return this.y;
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("key_default", super.onSaveInstanceState());
        String selectPos = "";
        if (this.p0.size() > 0) {
            for (Integer intValue : this.p0) {
                selectPos = selectPos + intValue.intValue() + "|";
            }
            selectPos = selectPos.substring(0, selectPos.length() - 1);
        }
        bundle.putString("key_choose_pos", selectPos);
        return bundle;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            String mSelectPos = bundle.getString("key_choose_pos");
            if (!TextUtils.isEmpty(mSelectPos)) {
                for (String pos : mSelectPos.split("\\|")) {
                    int index = Integer.parseInt(pos);
                    this.p0.add(Integer.valueOf(index));
                    TagView tagView = (TagView) getChildAt(index);
                    if (tagView != null) {
                        f(index, tagView);
                    }
                }
            }
            super.onRestoreInstanceState(bundle.getParcelable("key_default"));
            return;
        }
        super.onRestoreInstanceState(state);
    }

    public static int d(Context context, float dpValue) {
        return (int) ((dpValue * context.getResources().getDisplayMetrics().density) + 0.5f);
    }
}
