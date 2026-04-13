package com.leedarson.secret;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public class JNIUtil {
    public static ChangeQuickRedirect changeQuickRedirect;
    private String str1;
    private String str10;
    private String str11;
    private String str12;
    private String str2;
    private String str3;
    private String str4;
    private String str5;
    private String str6;
    private String str7;
    private String str8;
    private String str9;

    public native String _getStr();

    public native String _getStr10(String str, String str13);

    public native String _getStr11();

    public native String _getStr12();

    public native String _getStr2();

    public native String _getStr3();

    public native String _getStr4();

    public native String _getStr5();

    public native String _getStr6();

    public native String _getStr7();

    public native String _getStr8();

    public native String _getStr9();

    public String getStr() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5668, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if (this.str1 == null) {
            synchronized (this) {
                this.str1 = _getStr();
            }
        }
        return this.str1;
    }

    public String getStr2() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5669, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if (this.str2 == null) {
            synchronized (this) {
                this.str2 = _getStr2();
            }
        }
        return this.str2;
    }

    public String getStr3() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5670, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if (this.str3 == null) {
            synchronized (this) {
                this.str3 = _getStr3();
            }
        }
        return this.str3;
    }

    public String getStr4() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5671, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if (this.str4 == null) {
            synchronized (this) {
                this.str4 = _getStr4();
            }
        }
        return this.str4;
    }

    public String getStr5() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5672, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if (this.str5 == null) {
            synchronized (this) {
                this.str5 = _getStr5();
            }
        }
        return this.str5;
    }

    public String getStr6() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5673, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if (this.str6 == null) {
            synchronized (this) {
                this.str6 = _getStr6();
            }
        }
        return this.str6;
    }

    public String getStr7() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5674, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if (this.str7 == null) {
            synchronized (this) {
                this.str7 = _getStr7();
            }
        }
        return this.str7;
    }

    public String getStr8() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5675, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if (this.str8 == null) {
            synchronized (this) {
                this.str8 = _getStr8();
            }
        }
        return this.str8;
    }

    public String getStr9() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5676, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if (this.str9 == null) {
            synchronized (this) {
                this.str9 = _getStr9();
            }
        }
        return this.str9;
    }

    public String getStr10(String str13, String str22) {
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{str13, str22}, this, changeQuickRedirect, false, 5677, new Class[]{cls, cls}, String.class);
        return proxy.isSupported ? (String) proxy.result : _getStr10(str13, str22);
    }

    public String getStr11() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5678, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if (this.str11 == null) {
            synchronized (this) {
                this.str11 = _getStr11();
            }
        }
        return this.str11;
    }

    public String getStr12() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5679, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if (this.str12 == null) {
            synchronized (this) {
                this.str12 = _getStr12();
            }
        }
        return this.str12;
    }

    static {
        System.loadLibrary("native");
    }

    private JNIUtil() {
    }

    public static class SingletonHolder {
        public static ChangeQuickRedirect changeQuickRedirect;
        /* access modifiers changed from: private */
        public static final JNIUtil instance = new JNIUtil();

        private SingletonHolder() {
        }
    }

    public static JNIUtil getInstance() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 5680, new Class[0], JNIUtil.class);
        return proxy.isSupported ? (JNIUtil) proxy.result : SingletonHolder.instance;
    }
}
