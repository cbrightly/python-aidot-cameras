package com.didichuxing.doraemonkit.kit.fileexplorer;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.constant.BundleKey;
import com.didichuxing.doraemonkit.kit.core.BaseFragment;
import com.didichuxing.doraemonkit.util.DatabaseUtil;
import com.didichuxing.doraemonkit.widget.tableview.TableConfig;
import com.didichuxing.doraemonkit.widget.tableview.bean.ArrayTableData;
import com.didichuxing.doraemonkit.widget.tableview.component.SmartTable;
import com.didichuxing.doraemonkit.widget.tableview.format.FastTextDrawFormat;
import com.didichuxing.doraemonkit.widget.tableview.style.FontStyle;
import com.didichuxing.doraemonkit.widget.titlebar.TitleBar;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DatabaseDetailFragment extends BaseFragment {
    /* access modifiers changed from: private */
    public SmartTable table;
    /* access modifiers changed from: private */
    public ListView tableListView;

    /* access modifiers changed from: protected */
    public int onRequestLayout() {
        return R.layout.dk_fragment_db_detail;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle data = getArguments();
        SQLiteDatabase sqLiteDatabase = null;
        List<String> tableNames = new ArrayList<>();
        if (data != null) {
            sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(((File) data.getSerializable(BundleKey.FILE_KEY)).getPath(), (SQLiteDatabase.CursorFactory) null);
            tableNames = DatabaseUtil.queryTableName(sqLiteDatabase);
        }
        this.table = (SmartTable) findViewById(R.id.table);
        FontStyle fontStyle = new FontStyle(getContext(), 15, ContextCompat.getColor(getContext(), R.color.dk_color_000000));
        TableConfig.getInstance().setVerticalPadding(10).setHorizontalPadding(10);
        TableConfig.getInstance().columnTitleStyle = fontStyle;
        this.table.setZoom(true, 2.0f, 0.4f);
        ListView listView = (ListView) findViewById(R.id.lv_table_name);
        this.tableListView = listView;
        listView.setAdapter(new DBListAdapter(getContext(), tableNames));
        final List<String> finalStrings = tableNames;
        final SQLiteDatabase finalSqLiteDatabase = sqLiteDatabase;
        ((TitleBar) findViewById(R.id.title_bar)).setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            public void onLeftClick() {
                DatabaseDetailFragment.this.onBackPressed();
            }

            public void onRightClick() {
            }
        });
        this.tableListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SensorsDataInstrumented
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                View view2 = view;
                AdapterView<?> adapterView2 = adapterView;
                int position = i;
                String selectTableName = (String) finalStrings.get(position);
                String[][] data = DatabaseUtil.queryAll(finalSqLiteDatabase, (String) finalStrings.get(position));
                String[] titleName = DatabaseUtil.queryTableColumnName(finalSqLiteDatabase, selectTableName);
                if (DatabaseDetailFragment.this.table.getTableData() != null) {
                    DatabaseDetailFragment.this.table.getTableData().clear();
                }
                DatabaseDetailFragment.this.table.setTableData(ArrayTableData.create(selectTableName, titleName, data, new FastTextDrawFormat()));
                DatabaseDetailFragment.this.table.getMatrixHelper().reset();
                DatabaseDetailFragment.this.tableListView.setVisibility(8);
                DatabaseDetailFragment.this.table.setVisibility(0);
                SensorsDataAutoTrackHelper.trackListView(adapterView, view, i);
            }
        });
    }

    /* access modifiers changed from: protected */
    public boolean onBackPressed() {
        if (this.table.getVisibility() == 0) {
            this.table.setVisibility(8);
            this.tableListView.setVisibility(0);
            return true;
        }
        finish();
        return true;
    }
}
