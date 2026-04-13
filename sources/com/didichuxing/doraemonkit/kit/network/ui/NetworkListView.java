package com.didichuxing.doraemonkit.kit.network.ui;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.kit.core.BaseActivity;
import com.didichuxing.doraemonkit.kit.network.NetworkManager;
import com.didichuxing.doraemonkit.kit.network.OnNetworkInfoUpdateListener;
import com.didichuxing.doraemonkit.kit.network.bean.NetworkRecord;
import com.didichuxing.doraemonkit.kit.network.ui.NetworkListAdapter;
import com.didichuxing.doraemonkit.widget.recyclerview.DividerItemDecoration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NetworkListView extends LinearLayout implements OnNetworkInfoUpdateListener {
    public static final String KEY_RECORD = "record";
    private static final String TAG = "NetworkListFragment";
    private RecyclerView mNetworkList;
    /* access modifiers changed from: private */
    public NetworkListAdapter mNetworkListAdapter;
    private TextWatcher mTextWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void afterTextChanged(Editable s) {
            NetworkListView.this.mNetworkListAdapter.getFilter().filter(s);
        }
    };

    public NetworkListView(Context context) {
        super(context);
        LinearLayout.inflate(context, R.layout.dk_fragment_network_monitor_list, this);
        initView();
        initData();
    }

    public NetworkListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LinearLayout.inflate(context, R.layout.dk_fragment_network_monitor_list, this);
        initView();
        initData();
    }

    private void initView() {
        this.mNetworkList = (RecyclerView) findViewById(R.id.network_list);
        this.mNetworkList.setLayoutManager(new LinearLayoutManager(getContext()));
        NetworkListAdapter networkListAdapter = new NetworkListAdapter(getContext());
        this.mNetworkListAdapter = networkListAdapter;
        this.mNetworkList.setAdapter(networkListAdapter);
        DividerItemDecoration decoration = new DividerItemDecoration(1);
        decoration.setDrawable(getResources().getDrawable(R.drawable.dk_divider));
        decoration.showHeaderDivider(true);
        this.mNetworkList.addItemDecoration(decoration);
        this.mNetworkListAdapter.setOnItemClickListener(new NetworkListAdapter.OnItemClickListener() {
            public void onClick(NetworkRecord record) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(NetworkListView.KEY_RECORD, record);
                ((BaseActivity) NetworkListView.this.getContext()).showContent(NetworkDetailFragment.class, bundle);
            }
        });
        ((EditText) findViewById(R.id.network_list_filter)).addTextChangedListener(this.mTextWatcher);
    }

    private void initData() {
        synchronized (this) {
            List<NetworkRecord> records = new ArrayList<>(NetworkManager.get().getRecords());
            Collections.reverse(records);
            this.mNetworkListAdapter.setData(records);
        }
    }

    public void registerNetworkListener() {
        NetworkManager.get().setOnNetworkInfoUpdateListener(this);
    }

    public void unRegisterNetworkListener() {
        NetworkManager.get().setOnNetworkInfoUpdateListener((OnNetworkInfoUpdateListener) null);
    }

    public void onNetworkInfoUpdate(NetworkRecord record, boolean add) {
        synchronized (this) {
            if (add) {
                this.mNetworkListAdapter.append(record, 0);
            }
            this.mNetworkListAdapter.notifyDataSetChanged();
        }
    }
}
