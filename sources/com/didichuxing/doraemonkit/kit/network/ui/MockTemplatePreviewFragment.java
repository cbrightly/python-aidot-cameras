package com.didichuxing.doraemonkit.kit.network.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.blankj.utilcode.util.e0;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.kit.core.BaseFragment;
import com.didichuxing.doraemonkit.kit.network.room_db.DokitDbManager;
import com.didichuxing.doraemonkit.kit.network.room_db.MockTemplateApiBean;
import com.didichuxing.doraemonkit.okgo.DokitOkGo;
import com.didichuxing.doraemonkit.okgo.callback.StringCallback;
import com.didichuxing.doraemonkit.okgo.model.Response;
import com.didichuxing.doraemonkit.okgo.request.PatchRequest;
import com.didichuxing.doraemonkit.util.LogHelper;
import com.didichuxing.doraemonkit.widget.jsonviewer.JsonRecyclerView;
import com.didichuxing.doraemonkit.widget.titlebar.HomeTitleBar;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import org.json.JSONException;
import org.json.JSONObject;

public class MockTemplatePreviewFragment extends BaseFragment {
    /* access modifiers changed from: protected */
    public int onRequestLayout() {
        return R.layout.dk_fragment_mock_template_preview;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        if (getActivity() != null && DokitDbManager.getInstance().getGlobalTemplateApiBean() != null) {
            ((HomeTitleBar) findViewById(R.id.title_bar)).setListener(new HomeTitleBar.OnTitleBarClickListener() {
                public void onRightClick() {
                    MockTemplatePreviewFragment.this.finish();
                }
            });
            ((TextView) findViewById(R.id.tv_name)).setText(String.format("mock api name:%s", new Object[]{DokitDbManager.getInstance().getGlobalTemplateApiBean().getMockApiName()}));
            ((TextView) findViewById(R.id.tv_path)).setText(String.format("mock api path:%s", new Object[]{DokitDbManager.getInstance().getGlobalTemplateApiBean().getPath()}));
            JsonRecyclerView jsonViewQuery = (JsonRecyclerView) findViewById(R.id.json_query);
            JsonRecyclerView jsonRecycleView = (JsonRecyclerView) findViewById(R.id.jsonviewer);
            ((TextView) findViewById(R.id.tv_upload)).setOnClickListener(new View.OnClickListener() {
                @SensorsDataInstrumented
                public void onClick(View view) {
                    View view2 = view;
                    if (DokitDbManager.getInstance().getGlobalTemplateApiBean() == null) {
                        e0.n("no mock template data");
                        SensorsDataAutoTrackHelper.trackViewOnClick(view);
                        return;
                    }
                    MockTemplateApiBean mockApi = DokitDbManager.getInstance().getGlobalTemplateApiBean();
                    ((PatchRequest) ((PatchRequest) ((PatchRequest) DokitOkGo.patch(TemplateMockAdapter.TEMPLATER_UPLOAD_URL).params("projectId", mockApi.getProjectId(), new boolean[0])).params("id", mockApi.getId(), new boolean[0])).params("tempData", mockApi.getStrResponse(), new boolean[0])).execute(new StringCallback() {
                        public void onSuccess(Response<String> response) {
                            e0.n("upload template succeed");
                            String str = MockTemplatePreviewFragment.this.TAG;
                            LogHelper.i(str, "上传模板===>" + response.body());
                        }

                        public void onError(Response<String> response) {
                            super.onError(response);
                            e0.n("upload template failed");
                            String str = MockTemplatePreviewFragment.this.TAG;
                            LogHelper.e(str, "error===>" + response.body());
                        }
                    });
                    SensorsDataAutoTrackHelper.trackViewOnClick(view);
                }
            });
            if (DokitDbManager.getInstance().getGlobalTemplateApiBean() == null) {
                e0.n("no mock template data");
                return;
            }
            try {
                JSONObject jsonQuery = new JSONObject(DokitDbManager.getInstance().getGlobalTemplateApiBean().getQuery());
                if (jsonQuery.length() == 0) {
                    jsonViewQuery.setVisibility(8);
                } else {
                    jsonViewQuery.setVisibility(0);
                    jsonViewQuery.bindJson(jsonQuery);
                }
                new JSONObject(DokitDbManager.getInstance().getGlobalTemplateApiBean().getStrResponse());
                jsonRecycleView.setTextSize(16.0f);
                jsonRecycleView.bindJson(DokitDbManager.getInstance().getGlobalTemplateApiBean().getStrResponse());
            } catch (JSONException e) {
                e.printStackTrace();
                e0.n("the data is not json");
            }
        }
    }
}
