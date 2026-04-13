package com.didichuxing.doraemonkit.kit.health.model;

import com.google.gson.annotations.Expose;
import java.util.List;

public class AppHealthInfo {
    private BaseInfoBean baseInfo;
    private DataBean data;

    public BaseInfoBean getBaseInfo() {
        return this.baseInfo;
    }

    public void setBaseInfo(BaseInfoBean baseInfo2) {
        this.baseInfo = baseInfo2;
    }

    public DataBean getData() {
        return this.data;
    }

    public void setData(DataBean data2) {
        this.data = data2;
    }

    public static class BaseInfoBean {
        private String appName;
        private String appVersion;
        private String caseName;
        private String dokitVersion;
        private String pId;
        private String phoneMode;
        private String platform;
        private String systemVersion;
        private String testPerson;
        private String time;

        public String getpId() {
            return this.pId;
        }

        public void setpId(String pId2) {
            this.pId = pId2;
        }

        public String getCaseName() {
            return this.caseName;
        }

        public void setCaseName(String caseName2) {
            this.caseName = caseName2;
        }

        public String getTestPerson() {
            return this.testPerson;
        }

        public void setTestPerson(String testPerson2) {
            this.testPerson = testPerson2;
        }

        public String getPlatform() {
            return this.platform;
        }

        public void setPlatform(String platform2) {
            this.platform = platform2;
        }

        public String getTime() {
            return this.time;
        }

        public void setTime(String time2) {
            this.time = time2;
        }

        public String getPhoneMode() {
            return this.phoneMode;
        }

        public void setPhoneMode(String phoneMode2) {
            this.phoneMode = phoneMode2;
        }

        public String getSystemVersion() {
            return this.systemVersion;
        }

        public void setSystemVersion(String systemVersion2) {
            this.systemVersion = systemVersion2;
        }

        public String getAppName() {
            return this.appName;
        }

        public void setAppName(String appName2) {
            this.appName = appName2;
        }

        public String getAppVersion() {
            return this.appVersion;
        }

        public void setAppVersion(String appVersion2) {
            this.appVersion = appVersion2;
        }

        public String getDokitVersion() {
            return this.dokitVersion;
        }

        public void setDokitVersion(String dokitVersion2) {
            this.dokitVersion = dokitVersion2;
        }
    }

    public static class DataBean {
        private AppStartBean appStart;
        private List<BigFileBean> bigFile;
        private List<BlockBean> block;
        private List<PerformanceBean> cpu;
        private List<PerformanceBean> fps;
        private List<LeakBean> leak;
        private List<PerformanceBean> memory;
        private List<NetworkBean> network;
        private List<PageLoadBean> pageLoad;
        private List<SubThreadUIBean> subThreadUI;
        private List<UiLevelBean> uiLevel;

        public AppStartBean getAppStart() {
            return this.appStart;
        }

        public void setAppStart(AppStartBean appStart2) {
            this.appStart = appStart2;
        }

        public List<PerformanceBean> getCpu() {
            return this.cpu;
        }

        public void setCpu(List<PerformanceBean> cpu2) {
            this.cpu = cpu2;
        }

        public List<PerformanceBean> getMemory() {
            return this.memory;
        }

        public void setMemory(List<PerformanceBean> memory2) {
            this.memory = memory2;
        }

        public List<PerformanceBean> getFps() {
            return this.fps;
        }

        public void setFps(List<PerformanceBean> fps2) {
            this.fps = fps2;
        }

        public List<NetworkBean> getNetwork() {
            return this.network;
        }

        public void setNetwork(List<NetworkBean> network2) {
            this.network = network2;
        }

        public List<BlockBean> getBlock() {
            return this.block;
        }

        public void setBlock(List<BlockBean> block2) {
            this.block = block2;
        }

        public List<SubThreadUIBean> getSubThreadUI() {
            return this.subThreadUI;
        }

        public void setSubThreadUI(List<SubThreadUIBean> subThreadUI2) {
            this.subThreadUI = subThreadUI2;
        }

        public List<UiLevelBean> getUiLevel() {
            return this.uiLevel;
        }

        public void setUiLevel(List<UiLevelBean> uiLevel2) {
            this.uiLevel = uiLevel2;
        }

        public List<LeakBean> getLeak() {
            return this.leak;
        }

        public void setLeak(List<LeakBean> leak2) {
            this.leak = leak2;
        }

        public List<PageLoadBean> getPageLoad() {
            return this.pageLoad;
        }

        public void setPageLoad(List<PageLoadBean> pageLoad2) {
            this.pageLoad = pageLoad2;
        }

        public List<BigFileBean> getBigFile() {
            return this.bigFile;
        }

        public void setBigFile(List<BigFileBean> bigFile2) {
            this.bigFile = bigFile2;
        }

        public static class AppStartBean {
            private String costDetail;
            private long costTime;
            private List<LoadFuncBean> loadFunc;

            public long getCostTime() {
                return this.costTime;
            }

            public void setCostTime(long costTime2) {
                this.costTime = costTime2;
            }

            public String getCostDetail() {
                return this.costDetail;
            }

            public void setCostDetail(String costDetail2) {
                this.costDetail = costDetail2;
            }

            public List<LoadFuncBean> getLoadFunc() {
                return this.loadFunc;
            }

            public void setLoadFunc(List<LoadFuncBean> loadFunc2) {
                this.loadFunc = loadFunc2;
            }

            public static class LoadFuncBean {
                private String className;
                private String costTime;

                public String getClassName() {
                    return this.className;
                }

                public void setClassName(String className2) {
                    this.className = className2;
                }

                public String getCostTime() {
                    return this.costTime;
                }

                public void setCostTime(String costTime2) {
                    this.costTime = costTime2;
                }
            }
        }

        public static class PerformanceBean {
            private String page;
            @Expose
            private String pageKey;
            private List<ValuesBean> values;

            public String getPageKey() {
                return this.pageKey;
            }

            public void setPageKey(String pageKey2) {
                this.pageKey = pageKey2;
            }

            public String getPage() {
                return this.page;
            }

            public void setPage(String page2) {
                this.page = page2;
            }

            public List<ValuesBean> getValues() {
                return this.values;
            }

            public void setValues(List<ValuesBean> values2) {
                this.values = values2;
            }

            public static class ValuesBean {
                private String time;
                private String value;

                public ValuesBean(String time2, String value2) {
                    this.time = time2;
                    this.value = value2;
                }

                public String getTime() {
                    return this.time;
                }

                public void setTime(String time2) {
                    this.time = time2;
                }

                public String getValue() {
                    return this.value;
                }

                public void setValue(String value2) {
                    this.value = value2;
                }
            }
        }

        public static class NetworkBean {
            private String page;
            private List<NetworkValuesBean> values;

            public String getPage() {
                return this.page;
            }

            public void setPage(String page2) {
                this.page = page2;
            }

            public List<NetworkValuesBean> getValues() {
                return this.values;
            }

            public void setValues(List<NetworkValuesBean> values2) {
                this.values = values2;
            }

            public static class NetworkValuesBean {
                private String code;
                private String down;
                private String method;
                private String time;
                private String up;
                private String url;

                public String getTime() {
                    return this.time;
                }

                public void setTime(String time2) {
                    this.time = time2;
                }

                public String getUrl() {
                    return this.url;
                }

                public void setUrl(String url2) {
                    this.url = url2;
                }

                public String getUp() {
                    return this.up;
                }

                public void setUp(String up2) {
                    this.up = up2;
                }

                public String getDown() {
                    return this.down;
                }

                public void setDown(String down2) {
                    this.down = down2;
                }

                public String getCode() {
                    return this.code;
                }

                public void setCode(String code2) {
                    this.code = code2;
                }

                public String getMethod() {
                    return this.method;
                }

                public void setMethod(String method2) {
                    this.method = method2;
                }
            }
        }

        public static class BlockBean {
            private long blockTime;
            private String detail;
            private String page;

            public String getPage() {
                return this.page;
            }

            public void setPage(String page2) {
                this.page = page2;
            }

            public long getBlockTime() {
                return this.blockTime;
            }

            public void setBlockTime(long blockTime2) {
                this.blockTime = blockTime2;
            }

            public String getDetail() {
                return this.detail;
            }

            public void setDetail(String detail2) {
                this.detail = detail2;
            }
        }

        public static class SubThreadUIBean {
            private String detail;
            private String page;

            public String getPage() {
                return this.page;
            }

            public void setPage(String page2) {
                this.page = page2;
            }

            public String getDetail() {
                return this.detail;
            }

            public void setDetail(String detail2) {
                this.detail = detail2;
            }
        }

        public static class UiLevelBean {
            private String detail;
            private String level;
            private String page;

            public String getPage() {
                return this.page;
            }

            public void setPage(String page2) {
                this.page = page2;
            }

            public String getLevel() {
                return this.level;
            }

            public void setLevel(String level2) {
                this.level = level2;
            }

            public String getDetail() {
                return this.detail;
            }

            public void setDetail(String detail2) {
                this.detail = detail2;
            }
        }

        public static class LeakBean {
            private String detail;
            private String page;

            public String getPage() {
                return this.page;
            }

            public void setPage(String page2) {
                this.page = page2;
            }

            public String getDetail() {
                return this.detail;
            }

            public void setDetail(String detail2) {
                this.detail = detail2;
            }
        }

        public static class PageLoadBean {
            private String page;
            private String time;
            private String trace;

            public String getPage() {
                return this.page;
            }

            public void setPage(String page2) {
                this.page = page2;
            }

            public String getTime() {
                return this.time;
            }

            public void setTime(String time2) {
                this.time = time2;
            }

            public String getTrace() {
                return this.trace;
            }

            public void setTrace(String trace2) {
                this.trace = trace2;
            }
        }

        public static class BigFileBean {
            private String fileName;
            private String filePath;
            private String fileSize;

            public String getFileName() {
                return this.fileName;
            }

            public void setFileName(String fileName2) {
                this.fileName = fileName2;
            }

            public String getFileSize() {
                return this.fileSize;
            }

            public void setFileSize(String fileSize2) {
                this.fileSize = fileSize2;
            }

            public String getFilePath() {
                return this.filePath;
            }

            public void setFilePath(String filePath2) {
                this.filePath = filePath2;
            }
        }
    }
}
