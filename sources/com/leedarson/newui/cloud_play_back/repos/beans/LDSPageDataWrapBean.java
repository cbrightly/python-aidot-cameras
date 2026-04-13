package com.leedarson.newui.cloud_play_back.repos.beans;

import java.util.List;

public class LDSPageDataWrapBean<M> {
    public int endRow = 0;
    public List<M> list;
    public int pageNum = 0;
    public int pageSize = 0;
    public int pages = 0;
    public int size = 0;
    public int startRow = 0;
    public int total = 0;
}
