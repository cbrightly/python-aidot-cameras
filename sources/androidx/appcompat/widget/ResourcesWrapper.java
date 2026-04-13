package androidx.appcompat.widget;

import android.content.res.AssetFileDescriptor;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Movie;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import androidx.annotation.RequiresApi;
import java.io.InputStream;

public class ResourcesWrapper extends Resources {
    private final Resources mResources;

    public ResourcesWrapper(Resources resources) {
        super(resources.getAssets(), resources.getDisplayMetrics(), resources.getConfiguration());
        this.mResources = resources;
    }

    public CharSequence getText(int id) {
        return this.mResources.getText(id);
    }

    public CharSequence getQuantityText(int id, int quantity) {
        return this.mResources.getQuantityText(id, quantity);
    }

    public String getString(int id) {
        return this.mResources.getString(id);
    }

    public String getString(int id, Object... formatArgs) {
        return this.mResources.getString(id, formatArgs);
    }

    public String getQuantityString(int id, int quantity, Object... formatArgs) {
        return this.mResources.getQuantityString(id, quantity, formatArgs);
    }

    public String getQuantityString(int id, int quantity) {
        return this.mResources.getQuantityString(id, quantity);
    }

    public CharSequence getText(int id, CharSequence def) {
        return this.mResources.getText(id, def);
    }

    public CharSequence[] getTextArray(int id) {
        return this.mResources.getTextArray(id);
    }

    public String[] getStringArray(int id) {
        return this.mResources.getStringArray(id);
    }

    public int[] getIntArray(int id) {
        return this.mResources.getIntArray(id);
    }

    public TypedArray obtainTypedArray(int id) {
        return this.mResources.obtainTypedArray(id);
    }

    public float getDimension(int id) {
        return this.mResources.getDimension(id);
    }

    public int getDimensionPixelOffset(int id) {
        return this.mResources.getDimensionPixelOffset(id);
    }

    public int getDimensionPixelSize(int id) {
        return this.mResources.getDimensionPixelSize(id);
    }

    public float getFraction(int id, int base, int pbase) {
        return this.mResources.getFraction(id, base, pbase);
    }

    public Drawable getDrawable(int id) {
        return this.mResources.getDrawable(id);
    }

    @RequiresApi(21)
    public Drawable getDrawable(int id, Resources.Theme theme) {
        return this.mResources.getDrawable(id, theme);
    }

    @RequiresApi(15)
    public Drawable getDrawableForDensity(int id, int density) {
        return this.mResources.getDrawableForDensity(id, density);
    }

    @RequiresApi(21)
    public Drawable getDrawableForDensity(int id, int density, Resources.Theme theme) {
        return this.mResources.getDrawableForDensity(id, density, theme);
    }

    public Movie getMovie(int id) {
        return this.mResources.getMovie(id);
    }

    public int getColor(int id) {
        return this.mResources.getColor(id);
    }

    public ColorStateList getColorStateList(int id) {
        return this.mResources.getColorStateList(id);
    }

    public boolean getBoolean(int id) {
        return this.mResources.getBoolean(id);
    }

    public int getInteger(int id) {
        return this.mResources.getInteger(id);
    }

    public XmlResourceParser getLayout(int id) {
        return this.mResources.getLayout(id);
    }

    public XmlResourceParser getAnimation(int id) {
        return this.mResources.getAnimation(id);
    }

    public XmlResourceParser getXml(int id) {
        return this.mResources.getXml(id);
    }

    public InputStream openRawResource(int id) {
        return this.mResources.openRawResource(id);
    }

    public InputStream openRawResource(int id, TypedValue value) {
        return this.mResources.openRawResource(id, value);
    }

    public AssetFileDescriptor openRawResourceFd(int id) {
        return this.mResources.openRawResourceFd(id);
    }

    public void getValue(int id, TypedValue outValue, boolean resolveRefs) {
        this.mResources.getValue(id, outValue, resolveRefs);
    }

    @RequiresApi(15)
    public void getValueForDensity(int id, int density, TypedValue outValue, boolean resolveRefs) {
        this.mResources.getValueForDensity(id, density, outValue, resolveRefs);
    }

    public void getValue(String name, TypedValue outValue, boolean resolveRefs) {
        this.mResources.getValue(name, outValue, resolveRefs);
    }

    public TypedArray obtainAttributes(AttributeSet set, int[] attrs) {
        return this.mResources.obtainAttributes(set, attrs);
    }

    public void updateConfiguration(Configuration config, DisplayMetrics metrics) {
        super.updateConfiguration(config, metrics);
        Resources resources = this.mResources;
        if (resources != null) {
            resources.updateConfiguration(config, metrics);
        }
    }

    public DisplayMetrics getDisplayMetrics() {
        return this.mResources.getDisplayMetrics();
    }

    public Configuration getConfiguration() {
        return this.mResources.getConfiguration();
    }

    public int getIdentifier(String name, String defType, String defPackage) {
        return this.mResources.getIdentifier(name, defType, defPackage);
    }

    public String getResourceName(int resid) {
        return this.mResources.getResourceName(resid);
    }

    public String getResourcePackageName(int resid) {
        return this.mResources.getResourcePackageName(resid);
    }

    public String getResourceTypeName(int resid) {
        return this.mResources.getResourceTypeName(resid);
    }

    public String getResourceEntryName(int resid) {
        return this.mResources.getResourceEntryName(resid);
    }

    public void parseBundleExtras(XmlResourceParser parser, Bundle outBundle) {
        this.mResources.parseBundleExtras(parser, outBundle);
    }

    public void parseBundleExtra(String tagName, AttributeSet attrs, Bundle outBundle) {
        this.mResources.parseBundleExtra(tagName, attrs, outBundle);
    }
}
