package com.didichuxing.doraemonkit.kit.network.ui;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import com.blankj.utilcode.util.e0;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.constant.BundleKey;
import com.didichuxing.doraemonkit.kit.core.UniversalActivity;
import com.didichuxing.doraemonkit.kit.network.room_db.DokitDbManager;
import com.didichuxing.doraemonkit.kit.network.room_db.MockTemplateApiBean;
import com.didichuxing.doraemonkit.okgo.DokitOkGo;
import com.didichuxing.doraemonkit.okgo.callback.StringCallback;
import com.didichuxing.doraemonkit.okgo.model.Response;
import com.didichuxing.doraemonkit.okgo.request.PatchRequest;
import com.didichuxing.doraemonkit.util.DokitUtil;
import com.didichuxing.doraemonkit.util.LogHelper;
import com.didichuxing.doraemonkit.widget.bravh.entity.node.BaseNode;
import com.didichuxing.doraemonkit.widget.bravh.provider.BaseNodeProvider;
import com.didichuxing.doraemonkit.widget.bravh.viewholder.BaseViewHolder;
import com.didichuxing.doraemonkit.widget.jsonviewer.JsonRecyclerView;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import net.sqlcipher.database.SQLiteDatabase;
import org.json.JSONException;
import org.json.JSONObject;

public class TemplateDetailNodeProvider extends BaseNodeProvider {
    private static final String TAG = "TemplateDetailNodeProvider";

    public int getItemViewType() {
        return 200;
    }

    public int getLayoutId() {
        return R.layout.dk_mock_template_content_item;
    }

    public void convert(BaseViewHolder holder, BaseNode item) {
        String hasLocalMockData;
        if (item instanceof MockTemplateApiBean) {
            final MockTemplateApiBean mockApi = (MockTemplateApiBean) item;
            int i = R.id.tv_path;
            holder.setText(i, (CharSequence) "path:" + mockApi.getPath());
            JsonRecyclerView jsonQuery = (JsonRecyclerView) holder.getView(R.id.jsonviewer_query);
            JsonRecyclerView jsonBody = (JsonRecyclerView) holder.getView(R.id.jsonviewer_body);
            try {
                int i2 = R.id.rl_query;
                holder.getView(i2).setVisibility(0);
                if (new JSONObject(mockApi.getQuery()).length() == 0) {
                    holder.getView(i2).setVisibility(8);
                } else {
                    jsonQuery.bindJson(mockApi.getQuery());
                }
            } catch (JSONException e) {
                e.printStackTrace();
                holder.getView(R.id.rl_query).setVisibility(8);
            }
            try {
                int i3 = R.id.rl_body;
                holder.getView(i3).setVisibility(0);
                if (new JSONObject(mockApi.getBody()).length() == 0) {
                    holder.getView(i3).setVisibility(8);
                } else {
                    jsonBody.bindJson(mockApi.getBody());
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
                holder.getView(R.id.rl_body).setVisibility(8);
            }
            int i4 = R.id.tv_group;
            holder.setText(i4, (CharSequence) "group:" + mockApi.getGroup());
            int i5 = R.id.tv_create;
            holder.setText(i5, (CharSequence) "create person:" + mockApi.getCreatePerson());
            int i6 = R.id.tv_modify;
            holder.setText(i6, (CharSequence) "modify person:" + mockApi.getModifyPerson());
            final TextView tvView = (TextView) holder.getView(R.id.tv_view);
            tvView.setOnClickListener(new View.OnClickListener() {
                @SensorsDataInstrumented
                public void onClick(View view) {
                    View view2 = view;
                    if (TextUtils.isEmpty(mockApi.getStrResponse())) {
                        e0.n("no mock template data");
                        SensorsDataAutoTrackHelper.trackViewOnClick(view);
                        return;
                    }
                    DokitDbManager.getInstance().setGlobalTemplateApiBean(mockApi);
                    Intent intent = new Intent(tvView.getContext(), UniversalActivity.class);
                    intent.setFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
                    intent.putExtra(BundleKey.FRAGMENT_INDEX, 26);
                    tvView.getContext().startActivity(intent);
                    SensorsDataAutoTrackHelper.trackViewOnClick(view);
                }
            });
            TextView tvUpload = (TextView) holder.getView(R.id.tv_upload);
            tvUpload.setOnClickListener(new View.OnClickListener() {
                @SensorsDataInstrumented
                public void onClick(View view) {
                    View view2 = view;
                    ((PatchRequest) ((PatchRequest) ((PatchRequest) DokitOkGo.patch(TemplateMockAdapter.TEMPLATER_UPLOAD_URL).params("projectId", mockApi.getProjectId(), new boolean[0])).params("id", mockApi.getId(), new boolean[0])).params("tempData", mockApi.getStrResponse(), new boolean[0])).execute(new StringCallback() {
                        public void onSuccess(Response<String> response) {
                            LogHelper.i(TemplateDetailNodeProvider.TAG, "上传模板===>" + response.body());
                            e0.n("upload template succeed");
                        }

                        public void onError(Response<String> response) {
                            super.onError(response);
                            e0.n("upload template failed");
                            LogHelper.e(TemplateDetailNodeProvider.TAG, "上传模板失败===>" + response.body());
                        }
                    });
                    SensorsDataAutoTrackHelper.trackViewOnClick(view);
                }
            });
            TextView tvHasLocalMockData = (TextView) holder.getView(R.id.tv_local_has_mock_template);
            if (!TextUtils.isEmpty(mockApi.getStrResponse())) {
                hasLocalMockData = "Y";
                tvUpload.setClickable(true);
                tvUpload.setTextColor(tvUpload.getContext().getResources().getColor(R.color.dk_color_337CC4));
            } else {
                hasLocalMockData = "N";
                tvUpload.setClickable(false);
                tvUpload.setTextColor(tvUpload.getContext().getResources().getColor(R.color.dk_color_999999));
            }
            tvHasLocalMockData.setText(String.format(DokitUtil.getString(R.string.dk_data_mock_template_tip), new Object[]{hasLocalMockData}));
        }
    }
}
