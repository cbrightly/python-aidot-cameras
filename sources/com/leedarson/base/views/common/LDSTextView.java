package com.leedarson.base.views.common;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.module_base.R$drawable;
import com.leedarson.module_base.R$styleable;
import com.leedarson.serviceinterface.utils.PubUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.tutk.IOTC.AVIOCTRLDEFs;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class LDSTextView extends AppCompatTextView {
    public static ChangeQuickRedirect changeQuickRedirect;
    private Context c;
    private final String d;
    private final String f;
    private final String q;
    private final String x;

    public LDSTextView(Context context) {
        this(context, (AttributeSet) null);
    }

    public LDSTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LDSTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.c = null;
        this.d = "fonts/aidot/Bold.ttf";
        this.f = "fonts/aidot/Light.ttf";
        this.q = "fonts/aidot/Medium.ttf";
        this.x = "fonts/aidot/Regular.ttf";
        this.c = context;
        a(attrs, defStyleAttr);
    }

    private void a(@Nullable AttributeSet attrs, int defStyleAttr) {
        Object[] objArr = {attrs, new Integer(defStyleAttr)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 829, new Class[]{AttributeSet.class, Integer.TYPE}, Void.TYPE).isSupported) {
            String repositoryName = BaseApplication.b().y;
            HashSet<String> needChangeFontChannels = new HashSet<>();
            needChangeFontChannels.add("M071-AiDot");
            if (needChangeFontChannels.contains(repositoryName)) {
                switch (this.c.obtainStyledAttributes(attrs, R$styleable.LDSTextView, defStyleAttr, 0).getInt(R$styleable.LDSTextView_lds_fontType, -1)) {
                    case 1:
                        setTypeface(Typeface.createFromAsset(this.c.getAssets(), "fonts/aidot/Medium.ttf"));
                        break;
                    case 2:
                        setTypeface(Typeface.createFromAsset(this.c.getAssets(), "fonts/aidot/Light.ttf"));
                        break;
                    case 3:
                        setTypeface(Typeface.createFromAsset(this.c.getAssets(), "fonts/aidot/Bold.ttf"));
                        break;
                    default:
                        setTypeface(Typeface.createFromAsset(this.c.getAssets(), "fonts/aidot/Regular.ttf"));
                        break;
                }
            }
            b(attrs, defStyleAttr);
        }
    }

    private void b(AttributeSet attrs, int defStyleAttr) {
        Object[] objArr = {attrs, new Integer(defStyleAttr)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 830, new Class[]{AttributeSet.class, Integer.TYPE}, Void.TYPE).isSupported) {
            Integer ldsStrResId = Integer.valueOf(this.c.obtainStyledAttributes(attrs, R$styleable.LDSTextView, defStyleAttr, 0).getInteger(R$styleable.LDSTextView_lds_textLang, -1));
            if (ldsStrResId.intValue() != -1) {
                setText(PubUtils.getString(this.c, ldsStrResId.intValue()));
            }
        }
    }

    public void setTxtByResIdWithLan(Integer ldsStrResId) {
        if (!PatchProxy.proxy(new Object[]{ldsStrResId}, this, changeQuickRedirect, false, 831, new Class[]{Integer.class}, Void.TYPE).isSupported) {
            setText(PubUtils.getString(this.c, ldsStrResId.intValue()));
        }
    }

    public void setLDSTypeface(int mFontType) {
        Object[] objArr = {new Integer(mFontType)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_LISTWIFIAP_REQ, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            switch (mFontType) {
                case 1:
                    setTypeface(Typeface.createFromAsset(this.c.getAssets(), "fonts/aidot/Medium.ttf"));
                    return;
                case 2:
                    setTypeface(Typeface.createFromAsset(this.c.getAssets(), "fonts/aidot/Light.ttf"));
                    return;
                case 3:
                    setTypeface(Typeface.createFromAsset(this.c.getAssets(), "fonts/aidot/Bold.ttf"));
                    return;
                default:
                    setTypeface(Typeface.createFromAsset(this.c.getAssets(), "fonts/aidot/Regular.ttf"));
                    return;
            }
        }
    }

    public void c(String str, int i, int i2) {
        Object[] objArr = {str, new Integer(i), new Integer(i2)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_LISTWIFIAP_RESP, new Class[]{String.class, cls, cls}, Void.TYPE).isSupported) {
            int paragraphSpacing = i;
            String content = str;
            int lineSpacingExtra = i2;
            if (!content.contains("\n")) {
                setText(content);
                return;
            }
            String content2 = content.replace("\n", "\n\r");
            int previousIndex = content2.indexOf("\n\r");
            List<Integer> nextParagraphBeginIndexes = new ArrayList<>();
            nextParagraphBeginIndexes.add(Integer.valueOf(previousIndex));
            while (previousIndex != -1) {
                previousIndex = content2.indexOf("\n\r", previousIndex + 2);
                if (previousIndex != -1) {
                    nextParagraphBeginIndexes.add(Integer.valueOf(previousIndex));
                }
            }
            SpannableString spanString = new SpannableString(content2);
            Drawable d2 = ContextCompat.getDrawable(this.c, R$drawable.paragraph_space);
            float density = this.c.getResources().getDisplayMetrics().density;
            d2.setBounds(0, 0, 1, (int) ((((double) (((float) getLineHeight()) - (((float) lineSpacingExtra) * density))) / 1.2d) + ((double) (((float) (paragraphSpacing - lineSpacingExtra)) * density))));
            for (Integer intValue : nextParagraphBeginIndexes) {
                int index = intValue.intValue();
                spanString.setSpan(new ImageSpan(d2), index + 1, index + 2, 33);
            }
            setText(spanString);
        }
    }
}
