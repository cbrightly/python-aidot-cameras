package com.didichuxing.doraemonkit.kit.health;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.kit.core.BaseFragment;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;

public class HealthFragmentChild1 extends BaseFragment {
    LinearLayout mLlBackTop;

    /* access modifiers changed from: protected */
    public int onRequestLayout() {
        return R.layout.dk_fragment_health_child1;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ll_back_top);
        this.mLlBackTop = linearLayout;
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @SensorsDataInstrumented
            public void onClick(View view) {
                View view2 = view;
                Fragment parentFragment = HealthFragmentChild1.this.getParentFragment();
                if (parentFragment instanceof HealthFragment) {
                    ((HealthFragment) parentFragment).scroll2theTop();
                }
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
            }
        });
    }
}
