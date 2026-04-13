package com.didichuxing.doraemonkit.kit.network.room_db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity(tableName = "mock_template_api")
public class MockTemplateApiBean extends AbsMockApiBean implements Serializable {
    public static final int RESPONSE_FROM_MOCK = 0;
    public static final int RESPONSE_FROM_REAL = 1;
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
    @Ignore
    private String projectId;
    @ColumnInfo(name = "query")
    private String query;
    @ColumnInfo(name = "response_from")
    private int responseFrom;
    @ColumnInfo(name = "str_response")
    private String strResponse;

    public MockTemplateApiBean() {
    }

    @Ignore
    public MockTemplateApiBean(@NonNull String id2, String mockApiName2, String path2, String method2, String fromType2, String query2, String body2, String group2, String createPerson2, String modifyPerson2, String projectId2) {
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
        this.projectId = projectId2;
    }

    public String getId() {
        return this.id;
    }

    public String getSelectedSceneId() {
        return "";
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

    public boolean isOpen() {
        return this.isOpen;
    }

    public void setOpen(boolean open) {
        this.isOpen = open;
    }

    public String getStrResponse() {
        return this.strResponse;
    }

    public void setStrResponse(String strResponse2) {
        this.strResponse = strResponse2;
    }

    public int getResponseFrom() {
        return this.responseFrom;
    }

    public void setResponseFrom(int responseFrom2) {
        this.responseFrom = responseFrom2;
    }

    public String getProjectId() {
        return this.projectId;
    }
}
