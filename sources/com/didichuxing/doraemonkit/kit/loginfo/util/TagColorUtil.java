package com.didichuxing.doraemonkit.kit.loginfo.util;

import android.content.Context;
import android.util.SparseIntArray;
import androidx.core.content.ContextCompat;
import com.didichuxing.doraemonkit.R;

public class TagColorUtil {
    private static final SparseIntArray LEVEL_BG_COLOR;
    private static final SparseIntArray LEVEL_COLOR;
    private static final SparseIntArray TEXT_COLOR;
    private static final SparseIntArray TEXT_COLOR_EXPAND;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray(6);
        TEXT_COLOR = sparseIntArray;
        SparseIntArray sparseIntArray2 = new SparseIntArray(6);
        TEXT_COLOR_EXPAND = sparseIntArray2;
        SparseIntArray sparseIntArray3 = new SparseIntArray(6);
        LEVEL_COLOR = sparseIntArray3;
        SparseIntArray sparseIntArray4 = new SparseIntArray(6);
        LEVEL_BG_COLOR = sparseIntArray4;
        int i = R.color.dk_color_000000;
        sparseIntArray.put(3, i);
        sparseIntArray.put(4, i);
        sparseIntArray.put(2, i);
        int i2 = R.color.dk_color_8F0005;
        sparseIntArray.put(7, i2);
        int i3 = R.color.dk_color_FF0006;
        sparseIntArray.put(6, i3);
        int i4 = R.color.dk_color_0099dd;
        sparseIntArray.put(5, i4);
        int i5 = R.color.dk_color_FFFFFF;
        sparseIntArray2.put(3, i5);
        sparseIntArray2.put(4, i5);
        sparseIntArray2.put(2, i5);
        sparseIntArray2.put(7, i2);
        sparseIntArray2.put(6, i3);
        sparseIntArray2.put(5, i4);
        sparseIntArray4.put(3, R.color.background_debug);
        sparseIntArray4.put(6, R.color.background_error);
        sparseIntArray4.put(4, R.color.background_info);
        sparseIntArray4.put(2, R.color.background_verbose);
        sparseIntArray4.put(5, R.color.background_warn);
        sparseIntArray4.put(7, R.color.background_wtf);
        sparseIntArray3.put(3, R.color.foreground_debug);
        sparseIntArray3.put(6, R.color.foreground_error);
        sparseIntArray3.put(4, R.color.foreground_info);
        sparseIntArray3.put(2, R.color.foreground_verbose);
        sparseIntArray3.put(5, R.color.foreground_warn);
        sparseIntArray3.put(7, R.color.foreground_wtf);
    }

    public static int getTextColor(Context context, int level, boolean expand) {
        SparseIntArray map = expand ? TEXT_COLOR_EXPAND : TEXT_COLOR;
        Integer result = Integer.valueOf(map.get(level));
        if (result == null) {
            result = Integer.valueOf(map.get(2));
        }
        return ContextCompat.getColor(context, result.intValue());
    }

    public static int getLevelBgColor(Context context, int level) {
        SparseIntArray sparseIntArray = LEVEL_BG_COLOR;
        Integer result = Integer.valueOf(sparseIntArray.get(level));
        if (result == null) {
            result = Integer.valueOf(sparseIntArray.get(2));
        }
        return ContextCompat.getColor(context, result.intValue());
    }

    public static int getLevelColor(Context context, int level) {
        SparseIntArray sparseIntArray = LEVEL_COLOR;
        Integer result = Integer.valueOf(sparseIntArray.get(level));
        if (result == null) {
            result = Integer.valueOf(sparseIntArray.get(2));
        }
        return ContextCompat.getColor(context, result.intValue());
    }
}
