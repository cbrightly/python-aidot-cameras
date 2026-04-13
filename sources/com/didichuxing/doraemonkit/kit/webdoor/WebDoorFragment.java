package com.didichuxing.doraemonkit.kit.webdoor;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.kit.core.BaseFragment;
import com.didichuxing.doraemonkit.kit.webdoor.WebDoorHistoryAdapter;
import com.didichuxing.doraemonkit.widget.recyclerview.DividerItemDecoration;
import com.didichuxing.doraemonkit.widget.titlebar.HomeTitleBar;
import com.didichuxing.doraemonkit.zxing.activity.CaptureActivity;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.util.List;

public class WebDoorFragment extends BaseFragment {
    private static final String[] PERMISSIONS_CAMERA = {"android.permission.CAMERA"};
    private static final int REQUEST_CAMERA = 2;
    private static final int REQUEST_QR_CODE = 3;
    private RecyclerView mHistoryList;
    /* access modifiers changed from: private */
    public TextView mUrlExplore;
    /* access modifiers changed from: private */
    public EditText mWebAddressInput;
    /* access modifiers changed from: private */
    public WebDoorHistoryAdapter mWebDoorHistoryAdapter;

    /* access modifiers changed from: protected */
    public int onRequestLayout() {
        return R.layout.dk_fragment_web_door;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((HomeTitleBar) findViewById(R.id.title_bar)).setListener(new HomeTitleBar.OnTitleBarClickListener() {
            public void onRightClick() {
                WebDoorFragment.this.finish();
            }
        });
        EditText editText = (EditText) findViewById(R.id.web_address_input);
        this.mWebAddressInput = editText;
        editText.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (WebDoorFragment.this.checkInput()) {
                    WebDoorFragment.this.mUrlExplore.setEnabled(true);
                } else {
                    WebDoorFragment.this.mUrlExplore.setEnabled(false);
                }
            }

            public void afterTextChanged(Editable s) {
            }
        });
        this.mUrlExplore = (TextView) findViewById(R.id.url_explore);
        findViewById(R.id.clear).setOnClickListener(new View.OnClickListener() {
            @SensorsDataInstrumented
            public void onClick(View view) {
                View view2 = view;
                WebDoorManager.getInstance().clearHistory();
                WebDoorFragment.this.mWebDoorHistoryAdapter.clear();
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
            }
        });
        findViewById(R.id.qr_code).setOnClickListener(new View.OnClickListener() {
            @SensorsDataInstrumented
            public void onClick(View view) {
                View view2 = view;
                WebDoorFragment.this.qrCode();
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
            }
        });
        this.mUrlExplore.setOnClickListener(new View.OnClickListener() {
            @SensorsDataInstrumented
            public void onClick(View view) {
                View view2 = view;
                WebDoorFragment webDoorFragment = WebDoorFragment.this;
                webDoorFragment.doSearch(webDoorFragment.mWebAddressInput.getText().toString());
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
            }
        });
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.history_list);
        this.mHistoryList = recyclerView;
        recyclerView.setNestedScrollingEnabled(false);
        this.mHistoryList.setLayoutManager(new LinearLayoutManager(getContext()));
        List<String> historyItems = WebDoorManager.getInstance().getHistory();
        WebDoorHistoryAdapter webDoorHistoryAdapter = new WebDoorHistoryAdapter(getContext());
        this.mWebDoorHistoryAdapter = webDoorHistoryAdapter;
        webDoorHistoryAdapter.setData(historyItems);
        this.mWebDoorHistoryAdapter.setOnItemClickListener(new WebDoorHistoryAdapter.OnItemClickListener() {
            public void onItemClick(View view, String data) {
                WebDoorFragment.this.doSearch(data);
            }
        });
        this.mHistoryList.setAdapter(this.mWebDoorHistoryAdapter);
        DividerItemDecoration decoration = new DividerItemDecoration(1);
        decoration.setDrawable(getResources().getDrawable(R.drawable.dk_divider));
        this.mHistoryList.addItemDecoration(decoration);
    }

    /* access modifiers changed from: private */
    public void doSearch(String url) {
        WebDoorManager.getInstance().saveHistory(url);
        WebDoorManager.getInstance().getWebDoorCallback().overrideUrlLoading(getContext(), url);
        this.mWebDoorHistoryAdapter.setData(WebDoorManager.getInstance().getHistory());
    }

    /* access modifiers changed from: private */
    public boolean checkInput() {
        return !TextUtils.isEmpty(this.mWebAddressInput.getText());
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1 && requestCode == 3) {
            String result = data.getExtras().getString("result");
            if (!TextUtils.isEmpty(result)) {
                doSearch(result);
            }
        }
    }

    /* access modifiers changed from: private */
    public void qrCode() {
        if (!ownPermissionCheck()) {
            requestPermissions(PERMISSIONS_CAMERA, 2);
        } else {
            startActivityForResult(new Intent(getActivity(), CaptureActivity.class), 3);
        }
    }

    private boolean ownPermissionCheck() {
        if (ContextCompat.checkSelfPermission(getActivity(), "android.permission.CAMERA") != 0) {
            return false;
        }
        return true;
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 2) {
            for (int grantResult : grantResults) {
                if (grantResult == -1) {
                    showToast(R.string.dk_error_tips_permissions_less);
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
