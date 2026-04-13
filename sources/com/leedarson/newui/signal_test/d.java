package com.leedarson.newui.signal_test;

import com.leedarson.R$drawable;
import com.leedarson.R$string;
import com.meituan.robust.ChangeQuickRedirect;

/* compiled from: SignalTestActivity.kt */
public enum d {
    None(r3, r4, -1, r6, r17),
    Great(r12, r13, r14, r15, r16),
    Medium(R$drawable.ic_wifi_medium, R$string.wifi_type_medium, r14, r15, r16),
    Poor(r21, r22, r6, r24, r8),
    VeryPoor(R$drawable.ic_wifi_very_poor, R$string.wifi_type_very_poor, r6, r8, r8);
    
    public static ChangeQuickRedirect changeQuickRedirect;
    private final int icon;
    private final int tips;
    private final int tipsColor;
    private final int type;
    private final int typeColor;

    private d(int icon2, int type2, int tips2, int typeColor2, int tipsColor2) {
        this.icon = icon2;
        this.type = type2;
        this.tips = tips2;
        this.typeColor = typeColor2;
        this.tipsColor = tipsColor2;
    }

    public final int getIcon() {
        return this.icon;
    }

    public final int getTips() {
        return this.tips;
    }

    public final int getTipsColor() {
        return this.tipsColor;
    }

    public final int getType() {
        return this.type;
    }

    public final int getTypeColor() {
        return this.typeColor;
    }
}
