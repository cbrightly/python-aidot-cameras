package com.didichuxing.doraemonkit.widget.jsonviewer.adapter;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

public abstract class BaseJsonViewerAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    public static int BOOLEAN_COLOR = -425344;
    public static int BRACES_COLOR = -11905697;
    public static int KEY_COLOR = -7198823;
    public static int NULL_COLOR = -1091275;
    public static int NUMBER_COLOR = -14308638;
    public static int TEXT_COLOR = -12929718;
    public static float TEXT_SIZE_DP = 12.0f;
    public static int URL_COLOR = -10038571;
}
