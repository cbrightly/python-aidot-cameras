package com.leedarson.serviceimpl.business.netease.LDNetDiagnoService;

import android.util.Log;
import com.leedarson.serviceimpl.business.netease.LDNetDiagnoUtils.LDPingParse;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LDNetPing {
    private static final String MATCH_PING_IP = "(?<=from ).*(?=: icmp_seq=1 ttl=)";
    public static ChangeQuickRedirect changeQuickRedirect;
    private final int _sendCount;
    LDNetPingListener listener;

    public interface LDNetPingListener {
        void OnNetPingFinished(String str);
    }

    public LDNetPing(LDNetPingListener listener2, int theSendCount) {
        this.listener = listener2;
        this._sendCount = theSendCount;
    }

    private String execPing(PingTask ping, boolean isNeedL) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{ping, new Byte(isNeedL ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 7199, new Class[]{PingTask.class, Boolean.TYPE}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        String cmd = "ping -c ";
        if (isNeedL) {
            cmd = "ping -s 8185 -c  ";
        }
        Process process = null;
        String str = "";
        BufferedReader reader = null;
        try {
            Process process2 = Runtime.getRuntime().exec(cmd + this._sendCount + " " + ping.getHost());
            BufferedReader reader2 = new BufferedReader(new InputStreamReader(process2.getInputStream()));
            while (true) {
                String readLine = reader2.readLine();
                String line = readLine;
                if (readLine == null) {
                    break;
                }
                str = str + line;
            }
            reader2.close();
            process2.waitFor();
            try {
                reader2.close();
                process2.destroy();
            } catch (Exception e) {
            }
        } catch (IOException e2) {
            e2.printStackTrace();
            if (reader != null) {
                reader.close();
            }
            process.destroy();
        } catch (InterruptedException e3) {
            e3.printStackTrace();
            if (reader != null) {
                reader.close();
            }
            process.destroy();
        } catch (Throwable th) {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e4) {
                    throw th;
                }
            }
            process.destroy();
            throw th;
        }
        return str;
    }

    public void exec(String host, boolean isNeedL) {
        if (!PatchProxy.proxy(new Object[]{host, new Byte(isNeedL ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 7200, new Class[]{String.class, Boolean.TYPE}, Void.TYPE).isSupported) {
            PingTask pingTask = new PingTask(host);
            StringBuilder log = new StringBuilder(256);
            String status = execPing(pingTask, isNeedL);
            if (Pattern.compile(MATCH_PING_IP).matcher(status).find()) {
                Log.i("LDNetPing", "status" + status);
                log.append("\t" + status);
            } else if (status.length() == 0) {
                log.append("unknown host or network error");
            } else {
                log.append("timeout");
            }
            this.listener.OnNetPingFinished(LDPingParse.getFormattingStr(host, log.toString()));
        }
    }

    public class PingTask {
        private static final String MATCH_PING_HOST_IP = "(?<=\\().*?(?=\\))";
        public static ChangeQuickRedirect changeQuickRedirect;
        private String host;

        public String getHost() {
            return this.host;
        }

        public PingTask(String host2) {
            this.host = host2;
            Matcher m = Pattern.compile(MATCH_PING_HOST_IP).matcher(host2);
            if (m.find()) {
                this.host = m.group();
            }
        }
    }
}
