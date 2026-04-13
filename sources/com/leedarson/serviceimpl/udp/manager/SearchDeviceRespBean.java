package com.leedarson.serviceimpl.udp.manager;

import com.meituan.robust.ChangeQuickRedirect;
import java.util.List;

public class SearchDeviceRespBean {
    public static ChangeQuickRedirect changeQuickRedirect;
    private AckBean ack;
    private String method;
    private PayloadBean payload;
    private String seq;
    private String service;
    private String srcAddr;

    public String getService() {
        return this.service;
    }

    public void setService(String service2) {
        this.service = service2;
    }

    public String getMethod() {
        return this.method;
    }

    public void setMethod(String method2) {
        this.method = method2;
    }

    public String getSeq() {
        return this.seq;
    }

    public void setSeq(String seq2) {
        this.seq = seq2;
    }

    public String getSrcAddr() {
        return this.srcAddr;
    }

    public void setSrcAddr(String srcAddr2) {
        this.srcAddr = srcAddr2;
    }

    public PayloadBean getPayload() {
        return this.payload;
    }

    public void setPayload(PayloadBean payload2) {
        this.payload = payload2;
    }

    public AckBean getAck() {
        return this.ack;
    }

    public void setAck(AckBean ack2) {
        this.ack = ack2;
    }

    public static class PayloadBean {
        public static ChangeQuickRedirect changeQuickRedirect;
        private String devId;
        private String ip;
        private String mac;
        private String model;
        private int port;
        private String timestamp;
        private List<WifiListBean> wifiList;

        public int getPort() {
            return this.port;
        }

        public void setPort(int port2) {
            this.port = port2;
        }

        public String getIp() {
            return this.ip;
        }

        public void setIp(String ip2) {
            this.ip = ip2;
        }

        public String getMac() {
            return this.mac;
        }

        public void setMac(String mac2) {
            this.mac = mac2;
        }

        public String getDevId() {
            return this.devId;
        }

        public void setDevId(String devId2) {
            this.devId = devId2;
        }

        public String getModel() {
            return this.model;
        }

        public void setModel(String model2) {
            this.model = model2;
        }

        public String getTimestamp() {
            return this.timestamp;
        }

        public void setTimestamp(String timestamp2) {
            this.timestamp = timestamp2;
        }

        public List<WifiListBean> getWifiList() {
            return this.wifiList;
        }

        public void setWifiList(List<WifiListBean> wifiList2) {
            this.wifiList = wifiList2;
        }
    }

    public static class AckBean {
        public static ChangeQuickRedirect changeQuickRedirect;
        private int code;
        private String desc;

        public int getCode() {
            return this.code;
        }

        public void setCode(int code2) {
            this.code = code2;
        }

        public String getDesc() {
            return this.desc;
        }

        public void setDesc(String desc2) {
            this.desc = desc2;
        }
    }

    public static class WifiListBean {
        public static ChangeQuickRedirect changeQuickRedirect;
        private String rssi;
        private String ssid;

        public String getSsid() {
            return this.ssid;
        }

        public void setSsid(String ssid2) {
            this.ssid = ssid2;
        }

        public String getRssi() {
            return this.rssi;
        }

        public void setRssi(String rssi2) {
            this.rssi = rssi2;
        }
    }
}
