package com.leedarson.serviceimpl.business.netease.LDNetDiagnoService;

import android.util.Log;
import com.google.android.gms.wearable.WearableStatusCodes;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.List;

public class LDNetSocket {
    private static final int CONN_TIMES = 4;
    private static final String HOSTERR = "DNS解析失败,主机地址不可达";
    private static final String IOERR = "DNS解析正常,IO异常,TCP建立失败";
    private static final int PORT = 80;
    private static final String TIMEOUT = "DNS解析正常,连接超时,TCP建立失败";
    public static ChangeQuickRedirect changeQuickRedirect;
    private static LDNetSocket instance = null;
    static boolean loaded = true;
    private final long[] RttTimes = new long[4];
    public InetAddress[] _remoteInet;
    public List<String> _remoteIpList;
    public boolean isCConn = true;
    private boolean[] isConnnected;
    private LDNetSocketListener listener;
    private int timeOut = 6000;

    public interface LDNetSocketListener {
        void OnNetSocketFinished(String str);

        void OnNetSocketUpdated(String str);
    }

    public native void startJNITelnet(String str, String str2);

    static {
        try {
            System.loadLibrary("tracepath");
        } catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private LDNetSocket() {
    }

    public static LDNetSocket getInstance() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 7201, new Class[0], LDNetSocket.class);
        if (proxy.isSupported) {
            return (LDNetSocket) proxy.result;
        }
        if (instance == null) {
            instance = new LDNetSocket();
        }
        return instance;
    }

    public void initListener(LDNetSocketListener listener2) {
        this.listener = listener2;
    }

    public boolean exec(String host, int port) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{host, new Integer(port)}, this, changeQuickRedirect, false, 7202, new Class[]{String.class, Integer.TYPE}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (!this.isCConn || !loaded) {
            return execUseJava(host);
        }
        try {
            startJNITelnet(host, String.valueOf(port));
            return true;
        } catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
            Log.i("LDNetSocket", "call jni failed, call execUseJava");
            return execUseJava(host);
        }
    }

    private boolean execUseJava(String str) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 7203, new Class[]{String.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        InetAddress[] inetAddressArr = this._remoteInet;
        if (inetAddressArr == null || this._remoteIpList == null) {
            this.listener.OnNetSocketFinished(HOSTERR);
        } else {
            int len = inetAddressArr.length;
            this.isConnnected = new boolean[len];
            for (int i = 0; i < len; i++) {
                if (i != 0) {
                    this.listener.OnNetSocketUpdated("\n");
                }
                if (this._remoteIpList.size() > i) {
                    boolean[] zArr = this.isConnnected;
                    if (zArr.length > i) {
                        InetAddress[] inetAddressArr2 = this._remoteInet;
                        if (inetAddressArr2.length > i) {
                            zArr[i] = execIP(inetAddressArr2[i], this._remoteIpList.get(i));
                        }
                    }
                }
            }
            for (boolean valueOf : this.isConnnected) {
                if (Boolean.valueOf(valueOf).booleanValue()) {
                    this.listener.OnNetSocketFinished("\n");
                    return true;
                }
            }
        }
        this.listener.OnNetSocketFinished("\n");
        return false;
    }

    private boolean execIP(InetAddress inetAddress, String str) {
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{inetAddress, str}, this, changeQuickRedirect2, false, 7204, new Class[]{InetAddress.class, String.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        String ip = str;
        InetAddress inetAddress2 = inetAddress;
        boolean isConnected = true;
        StringBuilder log = new StringBuilder();
        if (inetAddress2 == null || ip == null) {
            isConnected = false;
        } else {
            InetSocketAddress socketAddress = new InetSocketAddress(inetAddress2, 80);
            int flag = 0;
            LDNetSocketListener lDNetSocketListener = this.listener;
            lDNetSocketListener.OnNetSocketUpdated("Connect to host: " + ip + "...\n");
            int i = 0;
            while (true) {
                if (i >= 4) {
                    break;
                }
                execSocket(socketAddress, this.timeOut, i);
                long[] jArr = this.RttTimes;
                if (jArr[i] == -1) {
                    LDNetSocketListener lDNetSocketListener2 = this.listener;
                    lDNetSocketListener2.OnNetSocketUpdated((i + 1) + "'s time=TimeOut,  ");
                    this.timeOut = this.timeOut + WearableStatusCodes.TARGET_NODE_NOT_CONNECTED;
                    if (i > 0 && this.RttTimes[i - 1] == -1) {
                        flag = -1;
                        break;
                    }
                } else if (jArr[i] == -2) {
                    LDNetSocketListener lDNetSocketListener3 = this.listener;
                    lDNetSocketListener3.OnNetSocketUpdated((i + 1) + "'s time=IOException");
                    if (i > 0 && this.RttTimes[i - 1] == -2) {
                        flag = -2;
                        break;
                    }
                } else {
                    LDNetSocketListener lDNetSocketListener4 = this.listener;
                    lDNetSocketListener4.OnNetSocketUpdated((i + 1) + "'s time=" + this.RttTimes[i] + "ms,  ");
                }
                i++;
            }
            long time = 0;
            int count = 0;
            if (flag == -1) {
                isConnected = false;
            } else if (flag == -2) {
                isConnected = false;
            } else {
                for (int i2 = 0; i2 < 4; i2++) {
                    long[] jArr2 = this.RttTimes;
                    if (jArr2[i2] > 0) {
                        time += jArr2[i2];
                        count++;
                    }
                }
                if (count > 0) {
                    log.append("average=" + (time / ((long) count)) + "ms");
                }
            }
        }
        this.listener.OnNetSocketUpdated(log.toString());
        return isConnected;
    }

    private void execSocket(InetSocketAddress socketAddress, int timeOut2, int index) {
        Object[] objArr = {socketAddress, new Integer(timeOut2), new Integer(index)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7205, new Class[]{InetSocketAddress.class, cls, cls}, Void.TYPE).isSupported) {
            Socket socket = null;
            try {
                socket = new Socket();
                long start = System.currentTimeMillis();
                socket.connect(socketAddress, timeOut2);
                this.RttTimes[index] = System.currentTimeMillis() - start;
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (SocketTimeoutException e2) {
                this.RttTimes[index] = -1;
                e2.printStackTrace();
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e3) {
                this.RttTimes[index] = -2;
                e3.printStackTrace();
                if (socket != null) {
                    socket.close();
                }
            } catch (Throwable th) {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e4) {
                        e4.printStackTrace();
                    }
                }
                throw th;
            }
        }
    }

    public void resetInstance() {
        if (instance != null) {
            instance = null;
        }
    }

    public void printSocketInfo(String log) {
        if (!PatchProxy.proxy(new Object[]{log}, this, changeQuickRedirect, false, 7206, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.listener.OnNetSocketUpdated(log);
        }
    }
}
