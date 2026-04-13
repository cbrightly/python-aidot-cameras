package com.leedarson.newui.repos.beans;

import java.util.ArrayList;
import java.util.List;

public class EventListRequestParamsBean {
    public List<String> areaIds = new ArrayList();
    public List<String> deviceIds = new ArrayList();
    public List<String> eventCodes = new ArrayList();
    public int pageNum = 0;
    public int pageSize = 10;
    public long recordEnd = 0;
    public long recordSta = 0;
}
