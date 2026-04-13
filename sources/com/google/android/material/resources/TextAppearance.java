package com.google.android.material.resources;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.text.TextPaint;
import android.util.Log;
import androidx.annotation.FontRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.StyleRes;
import androidx.annotation.VisibleForTesting;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.ViewCompat;
import com.google.android.material.R;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class TextAppearance {
    private static final String TAG = "TextAppearance";
    private static final int TYPEFACE_MONOSPACE = 3;
    private static final int TYPEFACE_SANS = 1;
    private static final int TYPEFACE_SERIF = 2;
    /* access modifiers changed from: private */
    public Typeface font;
    @Nullable
    public final String fontFamily;
    @FontRes
    private final int fontFamilyResourceId;
    /* access modifiers changed from: private */
    public boolean fontResolved = false;
    public final boolean hasLetterSpacing;
    public final float letterSpacing;
    @Nullable
    public final ColorStateList shadowColor;
    public final float shadowDx;
    public final float shadowDy;
    public final float shadowRadius;
    public final boolean textAllCaps;
    @Nullable
    public final ColorStateList textColor;
    @Nullable
    public final ColorStateList textColorHint;
    @Nullable
    public final ColorStateList textColorLink;
    public float textSize;
    public final int textStyle;
    public final int typeface;

    public TextAppearance(@NonNull Context context, @StyleRes int id) {
        TypedArray a = context.obtainStyledAttributes(id, R.styleable.TextAppearance);
        this.textSize = a.getDimension(R.styleable.TextAppearance_android_textSize, 0.0f);
        this.textColor = MaterialResources.getColorStateList(context, a, R.styleable.TextAppearance_android_textColor);
        this.textColorHint = MaterialResources.getColorStateList(context, a, R.styleable.TextAppearance_android_textColorHint);
        this.textColorLink = MaterialResources.getColorStateList(context, a, R.styleable.TextAppearance_android_textColorLink);
        this.textStyle = a.getInt(R.styleable.TextAppearance_android_textStyle, 0);
        this.typeface = a.getInt(R.styleable.TextAppearance_android_typeface, 1);
        int fontFamilyIndex = MaterialResources.getIndexWithValue(a, R.styleable.TextAppearance_fontFamily, R.styleable.TextAppearance_android_fontFamily);
        this.fontFamilyResourceId = a.getResourceId(fontFamilyIndex, 0);
        this.fontFamily = a.getString(fontFamilyIndex);
        this.textAllCaps = a.getBoolean(R.styleable.TextAppearance_textAllCaps, false);
        this.shadowColor = MaterialResources.getColorStateList(context, a, R.styleable.TextAppearance_android_shadowColor);
        this.shadowDx = a.getFloat(R.styleable.TextAppearance_android_shadowDx, 0.0f);
        this.shadowDy = a.getFloat(R.styleable.TextAppearance_android_shadowDy, 0.0f);
        this.shadowRadius = a.getFloat(R.styleable.TextAppearance_android_shadowRadius, 0.0f);
        a.recycle();
        if (Build.VERSION.SDK_INT >= 21) {
            TypedArray a2 = context.obtainStyledAttributes(id, R.styleable.MaterialTextAppearance);
            int i = R.styleable.MaterialTextAppearance_android_letterSpacing;
            this.hasLetterSpacing = a2.hasValue(i);
            this.letterSpacing = a2.getFloat(i, 0.0f);
            a2.recycle();
            return;
        }
        this.hasLetterSpacing = false;
        this.letterSpacing = 0.0f;
    }

    @VisibleForTesting
    @NonNull
    public Typeface getFont(@NonNull Context context) {
        if (this.fontResolved) {
            return this.font;
        }
        if (!context.isRestricted()) {
            try {
                Typeface font2 = ResourcesCompat.getFont(context, this.fontFamilyResourceId);
                this.font = font2;
                if (font2 != null) {
                    this.font = Typeface.create(font2, this.textStyle);
                }
            } catch (Resources.NotFoundException | UnsupportedOperationException e) {
            } catch (Exception e2) {
                Log.d(TAG, "Error loading font " + this.fontFamily, e2);
            }
        }
        createFallbackFont();
        this.fontResolved = true;
        return this.font;
    }

    public void getFontAsync(@NonNull Context context, @NonNull final TextAppearanceFontCallback callback) {
        if (shouldLoadFontSynchronously(context)) {
            getFont(context);
        } else {
            createFallbackFont();
        }
        int i = this.fontFamilyResourceId;
        if (i == 0) {
            this.fontResolved = true;
        }
        if (this.fontResolved) {
            callback.onFontRetrieved(this.font, true);
            return;
        }
        try {
            ResourcesCompat.getFont(context, i, new ResourcesCompat.FontCallback() {
                public void onFontRetrieved(@NonNull Typeface typeface) {
                    TextAppearance textAppearance = TextAppearance.this;
                    Typeface unused = textAppearance.font = Typeface.create(typeface, textAppearance.textStyle);
                    boolean unused2 = TextAppearance.this.fontResolved = true;
                    callback.onFontRetrieved(TextAppearance.this.font, false);
                }

                public void onFontRetrievalFailed(int reason) {
                    boolean unused = TextAppearance.this.fontResolved = true;
                    callback.onFontRetrievalFailed(reason);
                }
            }, (Handler) null);
        } catch (Resources.NotFoundException e) {
            this.fontResolved = true;
            callback.onFontRetrievalFailed(1);
        } catch (Exception e2) {
            Log.d(TAG, "Error loading font " + this.fontFamily, e2);
            this.fontResolved = true;
            callback.onFontRetrievalFailed(-3);
        }
    }

    public void getFontAsync(@NonNull Context context, @NonNull final TextPaint textPaint, @NonNull final TextAppearanceFontCallback callback) {
        updateTextPaintMeasureState(textPaint, getFallbackFont());
        getFontAsync(context, new TextAppearanceFontCallback() {
            public void onFontRetrieved(@NonNull Typeface typeface, boolean fontResolvedSynchronously) {
                TextAppearance.this.updateTextPaintMeasureState(textPaint, typeface);
                callback.onFontRetrieved(typeface, fontResolvedSynchronously);
            }

            public void onFontRetrievalFailed(int i) {
                callback.onFontRetrievalFailed(i);
            }
        });
    }

    public Typeface getFallbackFont() {
        createFallbackFont();
        return this.font;
    }

    private void createFallbackFont() {
        String str;
        if (this.font == null && (str = this.fontFamily) != null) {
            this.font = Typeface.create(str, this.textStyle);
        }
        if (this.font == null) {
            switch (this.typeface) {
                case 1:
                    this.font = Typeface.SANS_SERIF;
                    break;
                case 2:
                    this.font = Typeface.SERIF;
                    break;
                case 3:
                    this.font = Typeface.MONOSPACE;
                    break;
                default:
                    this.font = Typeface.DEFAULT;
                    break;
            }
            this.font = Typeface.create(this.font, this.textStyle);
        }
    }

    public void updateDrawState(@NonNull Context context, @NonNull TextPaint textPaint, @NonNull TextAppearanceFontCallback callback) {
        updateMeasureState(context, textPaint, callback);
        ColorStateList colorStateList = this.textColor;
        textPaint.setColor(colorStateList != null ? colorStateList.getColorForState(textPaint.drawableState, colorStateList.getDefaultColor()) : ViewCompat.MEASURED_STATE_MASK);
        float f = this.shadowRadius;
        float f2 = this.shadowDx;
        float f3 = this.shadowDy;
        ColorStateList colorStateList2 = this.shadowColor;
        textPaint.setShadowLayer(f, f2, f3, colorStateList2 != null ? colorStateList2.getColorForState(textPaint.drawableState, colorStateList2.getDefaultColor()) : 0);
    }

    public void updateMeasureState(@NonNull Context context, @NonNull TextPaint textPaint, @NonNull TextAppearanceFontCallback callback) {
        if (shouldLoadFontSynchronously(context)) {
            updateTextPaintMeasureState(textPaint, getFont(context));
        } else {
            getFontAsync(context, textPaint, callback);
        }
    }

    public void updateTextPaintMeasureState(@NonNull TextPaint textPaint, @NonNull Typeface typeface2) {
        textPaint.setTypeface(typeface2);
        int fake = this.textStyle & (~typeface2.getStyle());
        textPaint.setFakeBoldText((fake & 1) != 0);
        textPaint.setTextSkewX((fake & 2) != 0 ? -0.25f : 0.0f);
        textPaint.setTextSize(this.textSize);
        if (Build.VERSION.SDK_INT >= 21 && this.hasLetterSpacing) {
            textPaint.setLetterSpacing(this.letterSpacing);
        }
    }

    private boolean shouldLoadFontSynchronously(Context context) {
        if (TextAppearanceConfig.shouldLoadFontSynchronously()) {
            return true;
        }
        int i = this.fontFamilyResourceId;
        if ((i != 0 ? ResourcesCompat.getCachedFont(context, i) : null) != null) {
            return true;
        }
        return false;
    }
}
