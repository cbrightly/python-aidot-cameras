package org.apache.http.cookie;

import java.util.Date;

/* compiled from: SetCookie */
public interface n extends c {
    void setComment(String str);

    void setDomain(String str);

    void setExpiryDate(Date date);

    void setPath(String str);

    void setSecure(boolean z);

    void setVersion(int i);
}
