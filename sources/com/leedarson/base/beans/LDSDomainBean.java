package com.leedarson.base.beans;

public class LDSDomainBean {
    public String[] actions = {"nslookup", "ping", "traceroute"};
    public String category = "public";
    public String domain = "www.bing.com";
    public String type = "apiDomain";

    public LDSDomainBean(String domain2) {
        this.domain = domain2;
    }
}
