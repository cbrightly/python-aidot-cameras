package com.didichuxing.doraemonkit.kit.network.bean;

import com.didichuxing.doraemonkit.constant.DokitConstant;
import com.didichuxing.doraemonkit.kit.network.room_db.DokitDbManager;
import java.util.List;

public class MockApiResponseBean {
    private int code;
    private DataBean data;

    public int getCode() {
        return this.code;
    }

    public void setCode(int code2) {
        this.code = code2;
    }

    public DataBean getData() {
        return this.data;
    }

    public void setData(DataBean data2) {
        this.data = data2;
    }

    public static class DataBean {
        private List<DatalistBean> datalist;

        public List<DatalistBean> getDatalist() {
            return this.datalist;
        }

        public void setDatalist(List<DatalistBean> datalist2) {
            this.datalist = datalist2;
        }

        public static class DatalistBean {
            private String _id;
            private String categoryId;
            private String categoryName;
            private String createDate;
            private CurStatusBean curStatus;
            private String desc;
            private String formatType;
            private String groupId;
            private String method;
            private String name;
            private OwnerBean owner;
            private String path;
            private String projectId;
            private List<SceneListBean> sceneList;

            public static class InterOwnerBean {
            }

            public String get_id() {
                return this._id;
            }

            public void set_id(String _id2) {
                this._id = _id2;
            }

            public String getName() {
                return this.name;
            }

            public void setName(String name2) {
                this.name = name2;
            }

            public String getDesc() {
                return this.desc;
            }

            public void setDesc(String desc2) {
                this.desc = desc2;
            }

            public String getGroupId() {
                return this.groupId;
            }

            public void setGroupId(String groupId2) {
                this.groupId = groupId2;
            }

            public String getProjectId() {
                return this.projectId;
            }

            public void setProjectId(String projectId2) {
                this.projectId = projectId2;
            }

            public String getCategoryId() {
                return this.categoryId;
            }

            public void setCategoryId(String categoryId2) {
                this.categoryId = categoryId2;
            }

            public String getPath() {
                if (DokitConstant.isRpcSDK()) {
                    this.path = DokitConstant.dealDidiPlatformPath(this.path, DokitDbManager.FROM_SDK_DIDI);
                }
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

            public String getFormatType() {
                return this.formatType;
            }

            public void setFormatType(String formatType2) {
                this.formatType = formatType2;
            }

            public OwnerBean getOwner() {
                return this.owner;
            }

            public void setOwner(OwnerBean owner2) {
                this.owner = owner2;
            }

            public CurStatusBean getCurStatus() {
                return this.curStatus;
            }

            public void setCurStatus(CurStatusBean curStatus2) {
                this.curStatus = curStatus2;
            }

            public String getCreateDate() {
                return this.createDate;
            }

            public void setCreateDate(String createDate2) {
                this.createDate = createDate2;
            }

            public String getCategoryName() {
                return this.categoryName;
            }

            public void setCategoryName(String categoryName2) {
                this.categoryName = categoryName2;
            }

            public List<SceneListBean> getSceneList() {
                return this.sceneList;
            }

            public void setSceneList(List<SceneListBean> sceneList2) {
                this.sceneList = sceneList2;
            }

            public static class OwnerBean {
                String _id;
                String accountName;
                String department;
                String displayName;
                String employeeNumber;
                String name;

                public String getName() {
                    return this.name;
                }

                public String getDisplayName() {
                    return this.displayName;
                }

                public String getDepartment() {
                    return this.department;
                }

                public String getEmployeeNumber() {
                    return this.employeeNumber;
                }

                public String getAccountName() {
                    return this.accountName;
                }
            }

            public static class CurStatusBean {
                OperatorBean operator;
                String status;

                public String getStatus() {
                    return this.status;
                }

                public OperatorBean getOperator() {
                    return this.operator;
                }

                public static class OperatorBean {
                    String accountName;
                    String department;
                    String displayName;
                    String employeeNumber;
                    String name;

                    public String getName() {
                        return this.name;
                    }

                    public String getDisplayName() {
                        return this.displayName;
                    }

                    public String getDepartment() {
                        return this.department;
                    }

                    public String getEmployeeNumber() {
                        return this.employeeNumber;
                    }

                    public String getAccountName() {
                        return this.accountName;
                    }
                }
            }

            public static class QueryBean {
                private String api;

                public String getApi() {
                    return this.api;
                }

                public void setApi(String api2) {
                    this.api = api2;
                }
            }

            public static class SceneListBean {
                private String _id;
                private long createDate;
                private String desc;
                private String interfaceId;
                private String name;
                private String projectId;

                public String get_id() {
                    return this._id;
                }

                public void set_id(String _id2) {
                    this._id = _id2;
                }

                public String getName() {
                    return this.name;
                }

                public void setName(String name2) {
                    this.name = name2;
                }

                public String getDesc() {
                    return this.desc;
                }

                public void setDesc(String desc2) {
                    this.desc = desc2;
                }

                public String getProjectId() {
                    return this.projectId;
                }

                public void setProjectId(String projectId2) {
                    this.projectId = projectId2;
                }

                public String getInterfaceId() {
                    return this.interfaceId;
                }

                public void setInterfaceId(String interfaceId2) {
                    this.interfaceId = interfaceId2;
                }

                public long getCreateDate() {
                    return this.createDate;
                }

                public void setCreateDate(long createDate2) {
                    this.createDate = createDate2;
                }
            }
        }
    }
}
