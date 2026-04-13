package org.apache.http.impl.cookie;

import java.util.Date;
import org.apache.http.cookie.m;

/* compiled from: BasicClientCookie2 */
public class c extends d implements m {
    private static final long serialVersionUID = -7744598295706617057L;
    private String commentURL;
    private boolean discard;
    private int[] ports;

    public c(String name, String value) {
        super(name, value);
    }

    public int[] getPorts() {
        return this.ports;
    }

    public void setPorts(int[] ports2) {
        this.ports = ports2;
    }

    public String getCommentURL() {
        return this.commentURL;
    }

    public void setCommentURL(String commentURL2) {
        this.commentURL = commentURL2;
    }

    public void setDiscard(boolean discard2) {
        this.discard = discard2;
    }

    public boolean isPersistent() {
        return !this.discard && super.isPersistent();
    }

    public boolean isExpired(Date date) {
        return this.discard || super.isExpired(date);
    }

    public Object clone() {
        c clone = (c) super.clone();
        int[] iArr = this.ports;
        if (iArr != null) {
            clone.ports = (int[]) iArr.clone();
        }
        return clone;
    }
}
