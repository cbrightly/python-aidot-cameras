package com.didichuxing.doraemonkit.kit.core;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

public class SettingItem {
    public boolean canCheck;
    @StringRes
    public final int desc;
    @DrawableRes
    public int icon;
    public boolean isChecked;
    public String rightDesc;

    public SettingItem(@StringRes int desc2) {
        this.desc = desc2;
    }

    public SettingItem(@StringRes int desc2, boolean isChecked2) {
        this.desc = desc2;
        this.isChecked = isChecked2;
        this.canCheck = true;
    }

    public SettingItem(@StringRes int desc2, @DrawableRes int icon2) {
        this.desc = desc2;
        this.icon = icon2;
    }
}
