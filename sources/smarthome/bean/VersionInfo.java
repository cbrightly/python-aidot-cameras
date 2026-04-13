package smarthome.bean;

import android.text.TextUtils;
import com.leedarson.base.utils.w;
import io.netty.util.internal.StringUtil;

public class VersionInfo {
    private String version = "";
    private String way = "";

    public VersionInfo(String version2, String way2) {
        this.version = version2;
        this.way = way2;
    }

    public String getVersion() {
        return this.version;
    }

    public String getWay() {
        return this.way;
    }

    public boolean greatThan(VersionInfo skipInfo) {
        long cur = 0;
        long skip = 0;
        try {
            cur = w.G(getVersion());
        } catch (Exception e) {
        }
        try {
            skip = w.G(skipInfo.getVersion());
        } catch (Exception e2) {
        }
        if (cur > skip) {
            return true;
        }
        if (skip == cur) {
            return !getWay().equals(skipInfo.getWay());
        }
        return false;
    }

    public boolean isEmpty() {
        return TextUtils.isEmpty(getVersion()) && (TextUtils.isEmpty(getWay()) || "none".equals(getWay()));
    }

    public String toString() {
        return "{" + "\"version\":\"" + this.version + StringUtil.DOUBLE_QUOTE + ",\"way\":\"" + this.way + StringUtil.DOUBLE_QUOTE + '}';
    }
}
