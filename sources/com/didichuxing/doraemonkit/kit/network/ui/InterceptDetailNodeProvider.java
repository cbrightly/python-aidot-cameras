package com.didichuxing.doraemonkit.kit.network.ui;

import android.view.ViewGroup;
import android.widget.RadioButton;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.kit.network.bean.MockApiResponseBean;
import com.didichuxing.doraemonkit.kit.network.room_db.DokitDbManager;
import com.didichuxing.doraemonkit.kit.network.room_db.MockInterceptApiBean;
import com.didichuxing.doraemonkit.widget.MultiLineRadioGroup;
import com.didichuxing.doraemonkit.widget.bravh.entity.node.BaseNode;
import com.didichuxing.doraemonkit.widget.bravh.provider.BaseNodeProvider;
import com.didichuxing.doraemonkit.widget.bravh.viewholder.BaseViewHolder;
import com.didichuxing.doraemonkit.widget.jsonviewer.JsonRecyclerView;
import org.json.JSONException;
import org.json.JSONObject;

public class InterceptDetailNodeProvider extends BaseNodeProvider {
    public int getItemViewType() {
        return 200;
    }

    public int getLayoutId() {
        return R.layout.dk_mock_intercept_content_item;
    }

    public void convert(BaseViewHolder holder, BaseNode item) {
        if (item instanceof MockInterceptApiBean) {
            final MockInterceptApiBean mockApi = (MockInterceptApiBean) item;
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
            final MultiLineRadioGroup radioGroup = (MultiLineRadioGroup) holder.getView(R.id.radio_group);
            if (mockApi.getSceneList() != null && mockApi.getSceneList().size() != 0) {
                String[] radioButtons = new String[mockApi.getSceneList().size()];
                for (int index = 0; index < mockApi.getSceneList().size(); index++) {
                    radioButtons[index] = mockApi.getSceneList().get(index).getName();
                }
                radioGroup.removeAllButtons();
                radioGroup.addButtons((CharSequence[]) radioButtons);
                radioGroup.setOnCheckedChangeListener(new MultiLineRadioGroup.OnCheckedChangeListener() {
                    public void onCheckedChanged(ViewGroup group, RadioButton button) {
                        MockApiResponseBean.DataBean.DatalistBean.SceneListBean sceneListBean = mockApi.getSceneList().get(radioGroup.getCheckedRadioButtonIndex());
                        mockApi.setSelectedSceneName(sceneListBean.getName());
                        mockApi.setSelectedSceneId(sceneListBean.get_id());
                        DokitDbManager.getInstance().updateInterceptApi(mockApi);
                    }
                });
                int index2 = 0;
                int i7 = 0;
                while (true) {
                    if (i7 >= mockApi.getSceneList().size()) {
                        break;
                    } else if (mockApi.getSceneList().get(i7).get_id().equals(mockApi.getSelectedSceneId())) {
                        index2 = i7;
                        break;
                    } else {
                        i7++;
                    }
                }
                radioGroup.checkAt(index2);
            }
        }
    }
}
