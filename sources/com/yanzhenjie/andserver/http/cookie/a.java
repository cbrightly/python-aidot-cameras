package com.yanzhenjie.andserver.http.cookie;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.amazonaws.auth.policy.internal.JsonDocumentFields;
import com.didichuxing.doraemonkit.okgo.model.HttpHeaders;
import java.io.Serializable;
import java.util.Locale;

/* compiled from: Cookie */
public class a implements Cloneable, Serializable {
    private String comment;
    private String domain;
    private boolean isHttpOnly = false;
    private int maxAge = -1;
    private String name;
    private String path;
    private boolean secure;
    private String value;
    private int version = 0;

    public a(@NonNull String name2, @Nullable String value2) {
        if (TextUtils.isEmpty(name2)) {
            throw new IllegalArgumentException("The name of the cookie cannot be empty or null.");
        } else if (!a(name2) || name2.equalsIgnoreCase("Comment") || name2.equalsIgnoreCase("Discard") || name2.equalsIgnoreCase("Domain") || name2.equalsIgnoreCase(HttpHeaders.HEAD_KEY_EXPIRES) || name2.equalsIgnoreCase("Max-Age") || name2.equalsIgnoreCase("Path") || name2.equalsIgnoreCase("Secure") || name2.equalsIgnoreCase(JsonDocumentFields.VERSION) || name2.startsWith("$")) {
            throw new IllegalArgumentException(String.format("This name [%1$s] is a reserved character.", new Object[]{name2}));
        } else {
            this.name = name2;
            this.value = value2;
        }
    }

    public void setComment(@Nullable String purpose) {
        this.comment = purpose;
    }

    @Nullable
    public String getComment() {
        return this.comment;
    }

    public void setDomain(@Nullable String domain2) {
        if (!TextUtils.isEmpty(domain2)) {
            this.domain = domain2.toLowerCase(Locale.ENGLISH);
        } else {
            this.domain = null;
        }
    }

    @Nullable
    public String getDomain() {
        return this.domain;
    }

    public void setMaxAge(int expiry) {
        this.maxAge = expiry;
    }

    public int getMaxAge() {
        return this.maxAge;
    }

    public void setPath(@Nullable String path2) {
        this.path = path2;
    }

    @Nullable
    public String getPath() {
        return this.path;
    }

    public void setSecure(boolean flag) {
        this.secure = flag;
    }

    public boolean getSecure() {
        return this.secure;
    }

    @NonNull
    public String getName() {
        return this.name;
    }

    public void setValue(@Nullable String newValue) {
        this.value = newValue;
    }

    @Nullable
    public String getValue() {
        return this.value;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(int v) {
        this.version = v;
    }

    private boolean a(String value2) {
        int len = value2.length();
        for (int i = 0; i < len; i++) {
            char c = value2.charAt(i);
            if (c < ' ' || c >= 127 || "/()<>@,;:\\\"[]?={} \t".indexOf(c) != -1) {
                return false;
            }
        }
        return true;
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void setHttpOnly(boolean isHttpOnly2) {
        this.isHttpOnly = isHttpOnly2;
    }

    public boolean isHttpOnly() {
        return this.isHttpOnly;
    }
}
