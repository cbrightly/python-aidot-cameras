package com.didichuxing.doraemonkit.kit.health;

import android.app.Activity;
import android.content.Context;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.blankj.utilcode.util.a;
import com.blankj.utilcode.util.f;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.kit.core.AbsDokitView;
import com.didichuxing.doraemonkit.kit.core.DokitViewLayoutParams;
import com.didichuxing.doraemonkit.kit.core.DokitViewManager;
import com.google.firebase.FirebaseError;

public class CountDownDokitView extends AbsDokitView {
    /* access modifiers changed from: private */
    public static int COUNT_DOWN_INTERVAL = 1700;
    /* access modifiers changed from: private */
    public static int COUNT_DOWN_TOTAL = FirebaseError.ERROR_INVALID_CUSTOM_TOKEN;
    private static final String TAG = "CountDownDokitView";
    /* access modifiers changed from: private */
    public Activity activity;
    /* access modifiers changed from: private */
    public CountDownTimer mCountDownTimer;
    /* access modifiers changed from: private */
    public TextView mNum;

    public void onCreate(Context context) {
        this.activity = a.b();
    }

    public View onCreateView(Context context, FrameLayout rootView) {
        return LayoutInflater.from(context).inflate(R.layout.dk_float_count_down, rootView, false);
    }

    public void onViewCreated(FrameLayout rootView) {
        this.mNum = (TextView) findViewById(R.id.tv_number);
        postDelayed(new Runnable() {
            public void run() {
                CountDownTimer unused = CountDownDokitView.this.mCountDownTimer = new CountDownTimer((long) CountDownDokitView.COUNT_DOWN_TOTAL, (long) CountDownDokitView.COUNT_DOWN_INTERVAL) {
                    public void onTick(long millisUntilFinished) {
                        String value = String.valueOf((int) (millisUntilFinished / ((long) CountDownDokitView.COUNT_DOWN_INTERVAL)));
                        TextView access$300 = CountDownDokitView.this.mNum;
                        access$300.setText("" + value);
                    }

                    public void onFinish() {
                        CountDownDokitView.this.mNum.setText("0");
                        if (CountDownDokitView.this.isNormalMode()) {
                            DokitViewManager.getInstance().detach(CountDownDokitView.this.activity, (AbsDokitView) CountDownDokitView.this);
                        } else {
                            DokitViewManager.getInstance().detach((AbsDokitView) CountDownDokitView.this);
                        }
                    }
                };
                CountDownDokitView.this.mCountDownTimer.start();
            }
        }, 1000);
    }

    public void resetTime() {
        postDelayed(new Runnable() {
            public void run() {
                if (CountDownDokitView.this.mCountDownTimer != null) {
                    CountDownDokitView.this.mCountDownTimer.start();
                }
            }
        }, 500);
    }

    public void initDokitViewLayoutParams(DokitViewLayoutParams params) {
        int i = DokitViewLayoutParams.WRAP_CONTENT;
        params.height = i;
        params.width = i;
        params.gravity = 51;
        params.x = f.e(280.0f);
        params.y = f.e(25.0f);
    }

    public void onDestroy() {
        super.onDestroy();
        CountDownTimer countDownTimer = this.mCountDownTimer;
        if (countDownTimer != null) {
            countDownTimer.cancel();
            this.mCountDownTimer = null;
        }
        if (this.activity != null) {
            this.activity = null;
        }
    }

    public void onPause() {
        super.onPause();
    }
}
