package com.didichuxing.doraemonkit.widget.bravh.entity;

public abstract class JSectionEntity implements SectionEntity {
    public int getItemType() {
        if (isHeader()) {
            return -99;
        }
        return -100;
    }
}
