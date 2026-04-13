package me.jessyan.autosize;

import android.app.Activity;

public interface onAdaptListener {
    void onAdaptAfter(Object obj, Activity activity);

    void onAdaptBefore(Object obj, Activity activity);
}
