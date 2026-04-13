package com.didichuxing.doraemonkit.widget.bravh.entity;

import kotlin.l;

/* compiled from: SectionEntity.kt */
public interface SectionEntity extends MultiItemEntity {
    public static final Companion Companion = Companion.$$INSTANCE;
    public static final int HEADER_TYPE = -99;
    public static final int NORMAL_TYPE = -100;

    int getItemType();

    boolean isHeader();

    @l(d1 = {}, d2 = {}, k = 3, mv = {1, 4, 0})
    /* compiled from: SectionEntity.kt */
    public static final class DefaultImpls {
        public static int getItemType(SectionEntity $this) {
            return $this.isHeader() ? -99 : -100;
        }
    }

    /* compiled from: SectionEntity.kt */
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        public static final int HEADER_TYPE = -99;
        public static final int NORMAL_TYPE = -100;

        private Companion() {
        }
    }
}
