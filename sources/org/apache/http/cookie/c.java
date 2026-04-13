package org.apache.http.cookie;

import java.util.Date;

/* compiled from: Cookie */
public interface c {
    String getDomain();

    Date getExpiryDate();

    String getName();

    String getPath();

    int[] getPorts();

    String getValue();

    int getVersion();

    boolean isExpired(Date date);

    boolean isSecure();
}
