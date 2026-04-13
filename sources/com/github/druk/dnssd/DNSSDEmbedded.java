package com.github.druk.dnssd;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.amazonaws.kinesisvideo.internal.producer.KinesisVideoProducer;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DNSSDEmbedded extends DNSSD {
    public static final int DEFAULT_STOP_TIMER_DELAY = 5000;
    private static final String TAG = "DNSSDEmbedded";
    private final Handler handler;
    /* access modifiers changed from: private */
    public volatile boolean isStarted;
    private final long mStopTimerDelay;
    private Thread mThread;
    private int serviceCount;

    static native void nativeExit();

    static native int nativeInit();

    static native int nativeLoop();

    static native int nativeSetIpv6(String str, int i, int i2);

    public class Ipv6Info {
        public String addr;
        public int index;
        public int plen;

        Ipv6Info() {
        }
    }

    /* access modifiers changed from: private */
    public Ipv6Info getIPV6Info() {
        try {
            List<NetworkInterface> list = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : list) {
                for (InterfaceAddress addr : intf.getInterfaceAddresses()) {
                    String sAddr = addr.toString();
                    Pattern r = Pattern.compile("(/([a-z0-9A-Z]+:*)*)+");
                    boolean isWifi = true;
                    boolean isIPv6 = sAddr.indexOf(58) >= 0;
                    if (sAddr.indexOf("wlan") < 0) {
                        isWifi = false;
                    }
                    if (!isIPv6 || !isWifi) {
                        list = list;
                    } else {
                        try {
                            Ipv6Info ipv6Info = new Ipv6Info();
                            ipv6Info.index = intf.getIndex();
                            Matcher m = r.matcher(sAddr);
                            if (m.find()) {
                                ArrayList<T> arrayList = list;
                                ipv6Info.addr = m.group(0).replace("/", "");
                            } else {
                                List<NetworkInterface> interfaces = list;
                            }
                            Matcher m2 = r.matcher(sAddr.substring(sAddr.indexOf("wlan")));
                            if (m2.find()) {
                                ipv6Info.plen = Integer.parseInt(m2.group(0).replace("/", ""));
                            }
                            return ipv6Info;
                        } catch (Exception e) {
                            return null;
                        }
                    }
                }
                List<NetworkInterface> list2 = list;
            }
            List<NetworkInterface> list3 = list;
            return null;
        } catch (Exception e2) {
            return null;
        }
    }

    public DNSSDEmbedded(Context context) {
        this(context, KinesisVideoProducer.READY_TIMEOUT_IN_MILLISECONDS);
    }

    public DNSSDEmbedded(Context context, long stopTimerDelay) {
        super(context, "jdns_sd_embedded");
        this.handler = new Handler(Looper.getMainLooper());
        this.isStarted = false;
        this.serviceCount = 0;
        this.mStopTimerDelay = stopTimerDelay;
    }

    public void init() {
        this.handler.removeCallbacks(o.c);
        Thread thread = this.mThread;
        if (thread == null || !thread.isAlive()) {
            this.isStarted = false;
            InternalDNSSD.getInstance();
            AnonymousClass1 r0 = new Thread() {
                public void run() {
                    Class<DNSSDEmbedded> cls = DNSSDEmbedded.class;
                    Log.i(DNSSDEmbedded.TAG, "init");
                    Ipv6Info ipv6Info = DNSSDEmbedded.this.getIPV6Info();
                    Log.i(DNSSDEmbedded.TAG, "getIPV6Info " + ipv6Info.addr + ", " + ipv6Info.index + ", " + ipv6Info.plen);
                    DNSSDEmbedded.nativeSetIpv6(ipv6Info.addr, ipv6Info.index, ipv6Info.plen);
                    int err = DNSSDEmbedded.nativeInit();
                    synchronized (cls) {
                        boolean unused = DNSSDEmbedded.this.isStarted = true;
                        cls.notifyAll();
                    }
                    if (err != 0) {
                        Log.e(DNSSDEmbedded.TAG, "error: " + err);
                        return;
                    }
                    Log.i(DNSSDEmbedded.TAG, "start");
                    int ret = DNSSDEmbedded.nativeLoop();
                    boolean unused2 = DNSSDEmbedded.this.isStarted = false;
                    Log.i(DNSSDEmbedded.TAG, "finish with code: " + ret);
                }
            };
            this.mThread = r0;
            r0.setPriority(10);
            this.mThread.setName("DNS-SDEmbedded");
            this.mThread.start();
            waitUntilStarted();
            return;
        }
        Log.i(TAG, "already started");
        waitUntilStarted();
    }

    public void exit() {
        synchronized (DNSSDEmbedded.class) {
            Log.i(TAG, "post exit");
            this.handler.postDelayed(o.c, this.mStopTimerDelay);
        }
    }

    private void waitUntilStarted() {
        Class<DNSSDEmbedded> cls = DNSSDEmbedded.class;
        synchronized (cls) {
            while (!this.isStarted) {
                try {
                    cls.wait();
                } catch (InterruptedException e) {
                    Log.e(TAG, "waitUntilStarted exception: ", e);
                }
            }
        }
    }

    public void onServiceStarting() {
        super.onServiceStarting();
        init();
        this.serviceCount++;
    }

    public void onServiceStopped() {
        super.onServiceStopped();
        int i = this.serviceCount - 1;
        this.serviceCount = i;
        if (i == 0) {
            exit();
        }
    }
}
