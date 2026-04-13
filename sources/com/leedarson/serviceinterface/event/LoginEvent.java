package com.leedarson.serviceinterface.event;

import com.meituan.robust.ChangeQuickRedirect;

public class LoginEvent {
    public static ChangeQuickRedirect changeQuickRedirect;
    private int loginType;

    public interface Type {
        public static final int LOCAL_PHONE = 1;
        public static final int QQ = 3;
        public static final int WECHAT = 2;
    }

    public LoginEvent(int loginType2) {
        this.loginType = loginType2;
    }

    public int getLoginType() {
        return this.loginType;
    }

    public void setLoginType(int loginType2) {
        this.loginType = loginType2;
    }
}
