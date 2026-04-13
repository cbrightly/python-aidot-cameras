package com.leedarson.bean;

import android.net.Uri;
import com.chad.library.adapter.base.entity.a;
import com.leedarson.serviceinterface.utils.PubUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.io.Serializable;
import timber.log.a;

public class LocalMedia extends a implements Serializable {
    public static ChangeQuickRedirect changeQuickRedirect;
    private Uri contentUri;
    private String dateStr = "";
    private int duration;
    private boolean isChecked = false;
    private boolean isHeader = false;
    private long modifyTime;
    private String path;
    private int size;

    public String getPath() {
        return this.path;
    }

    public void setPath(String path2) {
        this.path = path2;
    }

    public long getModifyTime() {
        return this.modifyTime;
    }

    public void setModifyTime(long modifyTime2) {
        this.modifyTime = modifyTime2;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration2) {
        this.duration = duration2;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size2) {
        this.size = size2;
    }

    public boolean isChecked() {
        return this.isChecked;
    }

    public void setChecked(boolean checked) {
        this.isChecked = checked;
    }

    public String getDateStr() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1048, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        try {
            if (this.dateStr.split(" ").length <= 2) {
                return this.dateStr;
            }
            return this.dateStr.split(" ")[0] + " " + this.dateStr.split(" ")[1].substring(0, 3) + " " + this.dateStr.split(" ")[2];
        } catch (Exception ex) {
            a.b g = timber.log.a.g("LocalMedia");
            g.h("getDateStr  exception:=" + ex.toString(), new Object[0]);
            return this.dateStr;
        }
    }

    public String getFormaDate() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1049, new Class[0], String.class);
        return proxy.isSupported ? (String) proxy.result : PubUtils.getDateForMMMTime(this.modifyTime * 1000);
    }

    public void setDateStr(String dateStr2) {
        this.dateStr = dateStr2;
    }

    public void setHeader(boolean header) {
        this.isHeader = header;
    }

    public boolean isHeader() {
        return this.isHeader;
    }

    public Uri getContentUri() {
        return this.contentUri;
    }

    public void setContentUri(Uri contentUri2) {
        this.contentUri = contentUri2;
    }
}
