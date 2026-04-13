package com.didichuxing.doraemonkit.kit.fileexplorer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.constant.BundleKey;
import com.didichuxing.doraemonkit.kit.core.BaseFragment;
import com.didichuxing.doraemonkit.kit.fileexplorer.SpAdapter;
import com.didichuxing.doraemonkit.util.FileUtil;
import com.didichuxing.doraemonkit.util.SharedPrefsUtil;
import com.didichuxing.doraemonkit.widget.titlebar.TitleBar;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SpFragment extends BaseFragment {
    private SharedPreferences.Editor edit;
    private String spTableName;

    /* access modifiers changed from: protected */
    public int onRequestLayout() {
        return R.layout.dk_fragment_sp_show;
    }

    private List<SpBean> getSpBeans() {
        ArrayList<SpBean> spBeans = new ArrayList<>();
        File mFile = (File) getArguments().getSerializable(BundleKey.FILE_KEY);
        if (mFile == null) {
            return spBeans;
        }
        String replace = mFile.getName().replace(FileUtil.XML, "");
        this.spTableName = replace;
        SharedPreferences sp = SharedPrefsUtil.getSharedPrefs(replace);
        this.edit = sp.edit();
        Map<String, ?> all = sp.getAll();
        if (all.isEmpty()) {
            return spBeans;
        }
        for (Map.Entry<String, ?> entry : all.entrySet()) {
            spBeans.add(new SpBean(entry.getKey(), entry.getValue()));
        }
        return spBeans;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<SpBean> spBeans = getSpBeans();
        if (spBeans.isEmpty()) {
            finish();
            return;
        }
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_sp);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), 1, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), 1));
        SpAdapter spAdapter = new SpAdapter(getActivity());
        spAdapter.setOnSpDataChangerListener(new SpAdapter.OnSpDataChangerListener() {
            public void onDataChanged(SpBean bean) {
                SpFragment.this.spUpData(bean);
            }
        });
        spAdapter.append(spBeans);
        recyclerView.setAdapter(spAdapter);
        if (this.spTableName != null) {
            TitleBar mTitleBar = (TitleBar) findViewById(R.id.title_bar);
            mTitleBar.setTitle(this.spTableName);
            mTitleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
                public void onLeftClick() {
                    SpFragment.this.finish();
                }

                public void onRightClick() {
                }
            });
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void spUpData(com.didichuxing.doraemonkit.kit.fileexplorer.SpBean r4) {
        /*
            r3 = this;
            java.lang.String r0 = r4.key
            java.lang.Object r1 = r4.value
            java.lang.Class r1 = r1.getClass()
            java.lang.String r1 = r1.getSimpleName()
            int r2 = r1.hashCode()
            switch(r2) {
                case -1808118735: goto L_0x003c;
                case -672261858: goto L_0x0032;
                case 2374300: goto L_0x0028;
                case 67973692: goto L_0x001e;
                case 1729365000: goto L_0x0014;
                default: goto L_0x0013;
            }
        L_0x0013:
            goto L_0x0046
        L_0x0014:
            java.lang.String r2 = "Boolean"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0013
            r1 = 1
            goto L_0x0047
        L_0x001e:
            java.lang.String r2 = "Float"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0013
            r1 = 3
            goto L_0x0047
        L_0x0028:
            java.lang.String r2 = "Long"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0013
            r1 = 4
            goto L_0x0047
        L_0x0032:
            java.lang.String r2 = "Integer"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0013
            r1 = 2
            goto L_0x0047
        L_0x003c:
            java.lang.String r2 = "String"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0013
            r1 = 0
            goto L_0x0047
        L_0x0046:
            r1 = -1
        L_0x0047:
            switch(r1) {
                case 0: goto L_0x0077;
                case 1: goto L_0x0069;
                case 2: goto L_0x005f;
                case 3: goto L_0x0055;
                case 4: goto L_0x004b;
                default: goto L_0x004a;
            }
        L_0x004a:
            goto L_0x0081
        L_0x004b:
            java.lang.String r1 = r3.spTableName
            java.lang.Object r2 = r4.value
            java.lang.Long r2 = (java.lang.Long) r2
            com.didichuxing.doraemonkit.util.SharedPrefsUtil.putLong(r1, r0, r2)
            goto L_0x0081
        L_0x0055:
            java.lang.String r1 = r3.spTableName
            java.lang.Object r2 = r4.value
            java.lang.Float r2 = (java.lang.Float) r2
            com.didichuxing.doraemonkit.util.SharedPrefsUtil.putFloat(r1, r0, r2)
            goto L_0x0081
        L_0x005f:
            java.lang.String r1 = r3.spTableName
            java.lang.Object r2 = r4.value
            java.lang.Integer r2 = (java.lang.Integer) r2
            com.didichuxing.doraemonkit.util.SharedPrefsUtil.putInt(r1, r0, r2)
            goto L_0x0081
        L_0x0069:
            java.lang.String r1 = r3.spTableName
            java.lang.Object r2 = r4.value
            java.lang.Boolean r2 = (java.lang.Boolean) r2
            boolean r2 = r2.booleanValue()
            com.didichuxing.doraemonkit.util.SharedPrefsUtil.putBoolean(r1, r0, r2)
            goto L_0x0081
        L_0x0077:
            java.lang.Object r1 = r4.value
            java.lang.String r1 = r1.toString()
            com.didichuxing.doraemonkit.util.SharedPrefsUtil.putString(r0, r1)
        L_0x0081:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.didichuxing.doraemonkit.kit.fileexplorer.SpFragment.spUpData(com.didichuxing.doraemonkit.kit.fileexplorer.SpBean):void");
    }
}
