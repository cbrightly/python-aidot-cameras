package com.didichuxing.doraemonkit.kit.fileexplorer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.widget.bottomview.BottomUpWindow;
import com.didichuxing.doraemonkit.widget.bottomview.EditSpInputView;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;

public class SpInputView extends FrameLayout {
    private static final int FLOAT = 8194;
    private static final int INTEGER = 4098;
    private static final int STRING = 1;
    private SpBean bean;
    /* access modifiers changed from: private */
    public OnDataChangeListener onDataChangeListener;
    private TextView spValue;
    private Switch switchBtn;

    public interface OnDataChangeListener {
        void onDataChanged();
    }

    public SpInputView(Context context) {
        super(context, (AttributeSet) null);
    }

    public SpInputView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SpInputView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.kd_item_sp_input, this, true);
        this.switchBtn = (Switch) inflate.findViewById(R.id.switch_btn);
        this.spValue = (TextView) inflate.findViewById(R.id.tv_sp_value);
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setInput(final com.didichuxing.doraemonkit.kit.fileexplorer.SpBean r5, final com.didichuxing.doraemonkit.kit.fileexplorer.SpInputView.OnDataChangeListener r6) {
        /*
            r4 = this;
            r4.bean = r5
            r4.onDataChangeListener = r6
            java.lang.Object r0 = r5.value
            java.lang.Class r0 = r0.getClass()
            java.lang.String r0 = r0.getSimpleName()
            int r1 = r0.hashCode()
            r2 = 1
            r3 = 0
            switch(r1) {
                case -1808118735: goto L_0x0040;
                case -672261858: goto L_0x0036;
                case 2374300: goto L_0x002c;
                case 67973692: goto L_0x0022;
                case 1729365000: goto L_0x0018;
                default: goto L_0x0017;
            }
        L_0x0017:
            goto L_0x004a
        L_0x0018:
            java.lang.String r1 = "Boolean"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0017
            r0 = r3
            goto L_0x004b
        L_0x0022:
            java.lang.String r1 = "Float"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0017
            r0 = 3
            goto L_0x004b
        L_0x002c:
            java.lang.String r1 = "Long"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0017
            r0 = 2
            goto L_0x004b
        L_0x0036:
            java.lang.String r1 = "Integer"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0017
            r0 = r2
            goto L_0x004b
        L_0x0040:
            java.lang.String r1 = "String"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0017
            r0 = 4
            goto L_0x004b
        L_0x004a:
            r0 = -1
        L_0x004b:
            switch(r0) {
                case 0: goto L_0x005f;
                case 1: goto L_0x0059;
                case 2: goto L_0x0059;
                case 3: goto L_0x0053;
                case 4: goto L_0x004f;
                default: goto L_0x004e;
            }
        L_0x004e:
            goto L_0x0083
        L_0x004f:
            r4.initEdt(r5, r2)
            goto L_0x0083
        L_0x0053:
            r0 = 8194(0x2002, float:1.1482E-41)
            r4.initEdt(r5, r0)
            goto L_0x0083
        L_0x0059:
            r0 = 4098(0x1002, float:5.743E-42)
            r4.initEdt(r5, r0)
            goto L_0x0083
        L_0x005f:
            android.widget.Switch r0 = r4.switchBtn
            java.lang.Object r1 = r5.value
            java.lang.Boolean r1 = (java.lang.Boolean) r1
            boolean r1 = r1.booleanValue()
            r0.setChecked(r1)
            android.widget.Switch r0 = r4.switchBtn
            r0.setVisibility(r3)
            android.widget.Switch r0 = r4.switchBtn
            com.didichuxing.doraemonkit.kit.fileexplorer.SpInputView$1 r1 = new com.didichuxing.doraemonkit.kit.fileexplorer.SpInputView$1
            r1.<init>(r5, r6)
            r0.setOnCheckedChangeListener(r1)
            android.widget.TextView r0 = r4.spValue
            r1 = 8
            r0.setVisibility(r1)
        L_0x0083:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.didichuxing.doraemonkit.kit.fileexplorer.SpInputView.setInput(com.didichuxing.doraemonkit.kit.fileexplorer.SpBean, com.didichuxing.doraemonkit.kit.fileexplorer.SpInputView$OnDataChangeListener):void");
    }

    private void initEdt(final SpBean spBean, final int inputType) {
        this.spValue.setVisibility(0);
        this.switchBtn.setVisibility(8);
        this.spValue.setText(spBean.value.toString());
        this.spValue.setOnClickListener(new View.OnClickListener() {
            @SensorsDataInstrumented
            public void onClick(View v) {
                SpInputView.this.showInputView(v, spBean, inputType);
                SensorsDataAutoTrackHelper.trackViewOnClick(v);
            }
        });
    }

    public void refresh() {
        SpBean spBean = this.bean;
        if (spBean != null) {
            this.spValue.setText(spBean.value.toString());
        }
    }

    /* access modifiers changed from: private */
    public void showInputView(View view, final SpBean spBean, int inputType) {
        new BottomUpWindow(getContext()).setContent(new EditSpInputView(getContext(), spBean, inputType)).show(view).setOnSubmitListener(new BottomUpWindow.OnSubmitListener() {
            public void submit(Object object) {
                spBean.value = object;
                if (SpInputView.this.onDataChangeListener != null) {
                    SpInputView.this.onDataChangeListener.onDataChanged();
                }
            }

            public void cancel() {
            }
        });
    }
}
