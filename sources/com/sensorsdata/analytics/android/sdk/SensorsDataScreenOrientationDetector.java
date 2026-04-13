package com.sensorsdata.analytics.android.sdk;

import android.content.Context;
import android.view.OrientationEventListener;
import com.luck.picture.lib.widget.longimage.SubsamplingScaleImageView;

public class SensorsDataScreenOrientationDetector extends OrientationEventListener {
    private int mCurrentOrientation;

    public SensorsDataScreenOrientationDetector(Context context, int rate) {
        super(context, rate);
    }

    public String getOrientation() {
        int i = this.mCurrentOrientation;
        if (i == 0 || i == 180) {
            return "portrait";
        }
        if (i == 90 || i == 270) {
            return "landscape";
        }
        return null;
    }

    public void onOrientationChanged(int orientation) {
        if (orientation != -1) {
            if (orientation < 45 || orientation > 315) {
                this.mCurrentOrientation = 0;
            } else if (orientation > 45 && orientation < 135) {
                this.mCurrentOrientation = 90;
            } else if (orientation > 135 && orientation < 225) {
                this.mCurrentOrientation = 180;
            } else if (orientation > 225 && orientation < 315) {
                this.mCurrentOrientation = SubsamplingScaleImageView.ORIENTATION_270;
            }
        }
    }
}
