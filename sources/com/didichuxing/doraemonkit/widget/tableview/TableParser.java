package com.didichuxing.doraemonkit.widget.tableview;

import com.didichuxing.doraemonkit.widget.tableview.bean.ArrayTableData;
import com.didichuxing.doraemonkit.widget.tableview.bean.Column;
import com.didichuxing.doraemonkit.widget.tableview.bean.TableData;
import com.didichuxing.doraemonkit.widget.tableview.bean.TableInfo;
import java.util.List;

public class TableParser<T> {
    public List<Column> parse(TableData<T> tableData) {
        tableData.getChildColumns().clear();
        tableData.getColumnInfos().clear();
        int maxLevel = getChildColumn(tableData);
        TableInfo tableInfo = tableData.getTableInfo();
        tableInfo.setColumnSize(tableData.getChildColumns().size());
        tableInfo.setMaxLevel(maxLevel);
        if (!(tableData instanceof ArrayTableData)) {
            for (Column column : tableData.getChildColumns()) {
                column.getDatas().clear();
            }
        }
        return tableData.getColumns();
    }

    public void addData(TableData<T> tableData, List<T> addData, boolean isFoot) {
        if (isFoot) {
            tableData.getT().addAll(addData);
        } else {
            tableData.getT().addAll(0, addData);
        }
        tableData.getTableInfo().addLine(addData.size(), isFoot);
    }

    private int getChildColumn(TableData<T> tableData) {
        int maxLevel = 0;
        for (Column column : tableData.getColumns()) {
            int level = getColumnLevel(tableData, column, 0);
            if (level > maxLevel) {
                maxLevel = level;
            }
        }
        return maxLevel;
    }

    private int getColumnLevel(TableData<T> tableData, Column column, int level) {
        int level2 = level + 1;
        tableData.getChildColumns().add(column);
        return level2;
    }
}
