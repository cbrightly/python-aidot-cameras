package com.leedarson.serviceinterface.event;

import com.meituan.robust.ChangeQuickRedirect;
import java.util.ArrayList;
import java.util.List;

public class OnPermissionsDeniedEvent {
    public static ChangeQuickRedirect changeQuickRedirect;
    private List<String> perms = new ArrayList();
    private int requestCode;

    public OnPermissionsDeniedEvent(int requestCode2, List<String> perms2) {
        this.requestCode = requestCode2;
        this.perms = perms2;
    }

    public int getRequestCode() {
        return this.requestCode;
    }

    public List<String> getPerms() {
        return this.perms;
    }
}
