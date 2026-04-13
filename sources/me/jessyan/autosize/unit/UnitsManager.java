package me.jessyan.autosize.unit;

import me.jessyan.autosize.utils.Preconditions;

public class UnitsManager {
    private boolean isSupportDP = true;
    private boolean isSupportSP = true;
    private boolean isSupportScreenSizeDP = false;
    private float mDesignHeight;
    private float mDesignWidth;
    private Subunits mSupportSubunits = Subunits.NONE;

    public UnitsManager setDesignSize(float designWidth, float designHeight) {
        setDesignWidth(designWidth);
        setDesignHeight(designHeight);
        return this;
    }

    public float getDesignWidth() {
        return this.mDesignWidth;
    }

    public UnitsManager setDesignWidth(float designWidth) {
        Preconditions.checkArgument(designWidth > 0.0f, "designWidth must be > 0");
        this.mDesignWidth = designWidth;
        return this;
    }

    public float getDesignHeight() {
        return this.mDesignHeight;
    }

    public UnitsManager setDesignHeight(float designHeight) {
        Preconditions.checkArgument(designHeight > 0.0f, "designHeight must be > 0");
        this.mDesignHeight = designHeight;
        return this;
    }

    public boolean isSupportDP() {
        return this.isSupportDP;
    }

    public UnitsManager setSupportDP(boolean supportDP) {
        this.isSupportDP = supportDP;
        return this;
    }

    public boolean isSupportSP() {
        return this.isSupportSP;
    }

    public UnitsManager setSupportSP(boolean supportSP) {
        this.isSupportSP = supportSP;
        return this;
    }

    public Subunits getSupportSubunits() {
        return this.mSupportSubunits;
    }

    public boolean isSupportScreenSizeDP() {
        return this.isSupportScreenSizeDP;
    }

    public UnitsManager setSupportScreenSizeDP(boolean supportScreenSizeDP) {
        this.isSupportScreenSizeDP = supportScreenSizeDP;
        return this;
    }

    public UnitsManager setSupportSubunits(Subunits supportSubunits) {
        this.mSupportSubunits = (Subunits) Preconditions.checkNotNull(supportSubunits, "The supportSubunits can not be null, use Subunits.NONE instead");
        return this;
    }
}
