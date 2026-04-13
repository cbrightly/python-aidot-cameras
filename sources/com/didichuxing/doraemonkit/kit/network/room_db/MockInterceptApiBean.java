package com.didichuxing.doraemonkit.kit.network.room_db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import com.didichuxing.doraemonkit.kit.network.bean.MockApiResponseBean;
import java.util.List;

@Entity(tableName = "mock_intercept_api")
public class MockInterceptApiBean extends AbsMockApiBean {
    @ColumnInfo(name = "body")
    private String body;
    @Ignore
    private String createPerson;
    @ColumnInfo(name = "fromType")
    private String fromType;
    @Ignore
    private String group;
    @ColumnInfo(name = "id")
    @NonNull
    @PrimaryKey
    private String id = "";
    @ColumnInfo(name = "is_open")
    private boolean isOpen;
    @ColumnInfo(name = "method")
    private String method;
    @ColumnInfo(name = "mock_api_name")
    private String mockApiName;
    @Ignore
    private String modifyPerson;
    @ColumnInfo(name = "path")
    private String path;
    @ColumnInfo(name = "query")
    private String query;
    @Ignore
    private List<MockApiResponseBean.DataBean.DatalistBean.SceneListBean> sceneList;
    @ColumnInfo(name = "selected_scene_id")
    private String selectedSceneId;
    @ColumnInfo(name = "selected_scene_name")
    private String selectedSceneName;

    public MockInterceptApiBean() {
    }

    @Ignore
    public MockInterceptApiBean(@NonNull String id2, String mockApiName2, String path2, String method2, String fromType2, String query2, String body2, String group2, String createPerson2, String modifyPerson2, List<MockApiResponseBean.DataBean.DatalistBean.SceneListBean> sceneList2) {
        this.id = id2;
        this.mockApiName = mockApiName2;
        this.path = path2;
        this.method = method2;
        this.fromType = fromType2;
        this.query = query2;
        this.body = body2;
        this.group = group2;
        this.createPerson = createPerson2;
        this.modifyPerson = modifyPerson2;
        this.sceneList = sceneList2;
    }

    public String getId() {
        return this.id;
    }

    public String getMockApiName() {
        return this.mockApiName;
    }

    public void setMockApiName(String mockApiName2) {
        this.mockApiName = mockApiName2;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path2) {
        this.path = path2;
    }

    public String getMethod() {
        return this.method;
    }

    public void setMethod(String method2) {
        this.method = method2;
    }

    public String getQuery() {
        return this.query;
    }

    public void setQuery(String query2) {
        this.query = query2;
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(String body2) {
        this.body = body2;
    }

    public String getFromType() {
        return this.fromType;
    }

    public void setFromType(String fromType2) {
        this.fromType = fromType2;
    }

    public String getSelectedSceneName() {
        return this.selectedSceneName;
    }

    public void setSelectedSceneName(String selectedSceneName2) {
        this.selectedSceneName = selectedSceneName2;
    }

    public String getSelectedSceneId() {
        return this.selectedSceneId;
    }

    public void setSelectedSceneId(String selectedSceneId2) {
        this.selectedSceneId = selectedSceneId2;
    }

    public void setId(@NonNull String id2) {
        this.id = id2;
    }

    public String getGroup() {
        return this.group;
    }

    public void setGroup(String group2) {
        this.group = group2;
    }

    public String getCreatePerson() {
        return this.createPerson;
    }

    public void setCreatePerson(String createPerson2) {
        this.createPerson = createPerson2;
    }

    public String getModifyPerson() {
        return this.modifyPerson;
    }

    public void setModifyPerson(String modifyPerson2) {
        this.modifyPerson = modifyPerson2;
    }

    public List<MockApiResponseBean.DataBean.DatalistBean.SceneListBean> getSceneList() {
        return this.sceneList;
    }

    public void setSceneList(List<MockApiResponseBean.DataBean.DatalistBean.SceneListBean> sceneList2) {
        this.sceneList = sceneList2;
    }

    public boolean isOpen() {
        return this.isOpen;
    }

    public void setOpen(boolean open) {
        this.isOpen = open;
    }
}
