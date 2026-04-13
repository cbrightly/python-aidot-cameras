package com.chad.library.adapter.base.entity;

/* compiled from: JSectionEntity */
public abstract class a implements c {
    public int getItemType() {
        if (isHeader()) {
            return -99;
        }
        return -100;
    }
}
