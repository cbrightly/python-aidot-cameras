package com.didichuxing.doraemonkit.widget.tableview.intface;

public interface ITableTitle extends IComponent<String> {
    int getDirection();

    int getSize();

    void setDirection(int i);

    void setSize(int i);
}
