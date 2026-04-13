package com.leedarson.serviceimpl.business.bean;

import java.util.ArrayList;
import java.util.List;

public class NetDiagnosisDomain {
    public List<String> actions;
    public String category;
    public String domain;
    public String type;

    public NetDiagnosisDomain(String category2, String type2, String domain2) {
        ArrayList arrayList = new ArrayList();
        this.actions = arrayList;
        this.category = category2;
        this.type = type2;
        this.domain = domain2;
        arrayList.add("traceroute");
        this.actions.add("ping");
        this.actions.add("nslookup");
    }
}
