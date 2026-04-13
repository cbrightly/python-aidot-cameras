package com.didichuxing.doraemonkit.kit.network.ui;

import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.e0;
import com.blankj.utilcode.util.f;
import com.blankj.utilcode.util.k;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.constant.DokitConstant;
import com.didichuxing.doraemonkit.kit.core.BaseFragment;
import com.didichuxing.doraemonkit.kit.network.bean.MockApiResponseBean;
import com.didichuxing.doraemonkit.kit.network.bean.MockInterceptTitleBean;
import com.didichuxing.doraemonkit.kit.network.bean.MockTemplateTitleBean;
import com.didichuxing.doraemonkit.kit.network.room_db.DokitDbManager;
import com.didichuxing.doraemonkit.kit.network.room_db.MockInterceptApiBean;
import com.didichuxing.doraemonkit.kit.network.room_db.MockTemplateApiBean;
import com.didichuxing.doraemonkit.okgo.DokitOkGo;
import com.didichuxing.doraemonkit.okgo.callback.StringCallback;
import com.didichuxing.doraemonkit.okgo.model.Response;
import com.didichuxing.doraemonkit.okgo.request.GetRequest;
import com.didichuxing.doraemonkit.util.DokitUtil;
import com.didichuxing.doraemonkit.util.LogHelper;
import com.didichuxing.doraemonkit.widget.bravh.entity.node.BaseNode;
import com.didichuxing.doraemonkit.widget.bravh.listener.OnLoadMoreListener;
import com.didichuxing.doraemonkit.widget.bravh.module.BaseLoadMoreModule;
import com.didichuxing.doraemonkit.widget.dropdown.DkDropDownMenu;
import com.didichuxing.doraemonkit.widget.easyrefresh.EasyRefreshLayout;
import com.didichuxing.doraemonkit.widget.easyrefresh.LoadModel;
import com.didichuxing.doraemonkit.widget.titlebar.HomeTitleBar;
import com.google.android.gms.actions.SearchIntents;
import com.google.maps.android.BuildConfig;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class NetWorkMockFragment extends BaseFragment {
    /* access modifiers changed from: private */
    public static int BOTTOM_TAB_INDEX_0 = 0;
    /* access modifiers changed from: private */
    public static int BOTTOM_TAB_INDEX_1 = 1;
    /* access modifiers changed from: private */
    public DkDropDownMenu mDropDownMenu;
    /* access modifiers changed from: private */
    public EditText mEditText;
    private String mFormatApiUrl = "https://mock.dokit.cn/api/app/interface?projectId=%s&isfull=1&curPage=%s&pageSize=%s";
    /* access modifiers changed from: private */
    public ListDropDownAdapter mGroupMenuAdapter;
    private InterceptMockAdapter mInterceptApiAdapter;
    /* access modifiers changed from: private */
    public FilterConditionBean mInterceptFilterBean;
    /* access modifiers changed from: private */
    public BaseLoadMoreModule mInterceptLoadMoreModule;
    /* access modifiers changed from: private */
    public int mInterceptOpenStatus = 0;
    /* access modifiers changed from: private */
    public EasyRefreshLayout mInterceptRefreshLayout;
    private List<MockInterceptTitleBean> mInterceptTitleBeans = new ArrayList();
    private ImageView mIvMock;
    private ImageView mIvTemplate;
    private String[] mMenuHeaders = {DokitUtil.getString(R.string.dk_data_mock_group), DokitUtil.getString(R.string.dk_data_mock_switch_status)};
    private RecyclerView mRvIntercept;
    private RecyclerView mRvTemplate;
    private FrameLayout mRvWrap;
    /* access modifiers changed from: private */
    public int mSelectedTableIndex = BOTTOM_TAB_INDEX_0;
    /* access modifiers changed from: private */
    public String mStrInterceptGroup = "";
    /* access modifiers changed from: private */
    public String mStrTemplateGroup = "";
    /* access modifiers changed from: private */
    public ListDropDownAdapter mSwitchMenuAdapter;
    /* access modifiers changed from: private */
    public String[] mSwitchMenus = {DokitUtil.getString(R.string.dk_data_mock_switch_all), DokitUtil.getString(R.string.dk_data_mock_switch_opened), DokitUtil.getString(R.string.dk_data_mock_switch_closed)};
    private TemplateMockAdapter mTemplateApiAdapter;
    /* access modifiers changed from: private */
    public FilterConditionBean mTemplateFilterBean;
    /* access modifiers changed from: private */
    public BaseLoadMoreModule mTemplateLoadMoreModule;
    /* access modifiers changed from: private */
    public int mTemplateOpenStatus = 0;
    /* access modifiers changed from: private */
    public EasyRefreshLayout mTemplateRefreshLayout;
    private List<MockTemplateTitleBean> mTemplateTitleBeans = new ArrayList();
    private TextView mTvMock;
    private TextView mTvTemplate;
    private int pageSize = 100;
    private List<View> popupViews = new ArrayList();
    private String projectId = DokitConstant.PRODUCT_ID;

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    /* access modifiers changed from: protected */
    public int onRequestLayout() {
        return R.layout.dk_fragment_net_mock;
    }

    private void initView() {
        if (getActivity() != null) {
            ((HomeTitleBar) findViewById(R.id.title_bar)).setListener(new HomeTitleBar.OnTitleBarClickListener() {
                public void onRightClick() {
                    NetWorkMockFragment.this.finish();
                }
            });
            if (TextUtils.isEmpty(this.projectId)) {
                e0.m(DokitUtil.getString(R.string.dk_data_mock_plugin_toast));
                return;
            }
            this.mEditText = (EditText) findViewById(R.id.edittext);
            ((TextView) findViewById(R.id.tv_search)).setOnClickListener(new View.OnClickListener() {
                @SensorsDataInstrumented
                public void onClick(View view) {
                    View view2 = view;
                    if (NetWorkMockFragment.this.mSelectedTableIndex == NetWorkMockFragment.BOTTOM_TAB_INDEX_0) {
                        NetWorkMockFragment.this.mInterceptFilterBean.setFilterText(NetWorkMockFragment.this.mEditText.getText().toString());
                    } else if (NetWorkMockFragment.this.mSelectedTableIndex == NetWorkMockFragment.BOTTOM_TAB_INDEX_1) {
                        NetWorkMockFragment.this.mTemplateFilterBean.setFilterText(NetWorkMockFragment.this.mEditText.getText().toString());
                    }
                    NetWorkMockFragment.this.filterAndNotifyData();
                    SensorsDataAutoTrackHelper.trackViewOnClick(view);
                }
            });
            ((LinearLayout) findViewById(R.id.ll_bottom_tab_mock)).setOnClickListener(new View.OnClickListener() {
                @SensorsDataInstrumented
                public void onClick(View view) {
                    View view2 = view;
                    NetWorkMockFragment.this.switchBottomTabStatus(NetWorkMockFragment.BOTTOM_TAB_INDEX_0);
                    SensorsDataAutoTrackHelper.trackViewOnClick(view);
                }
            });
            ((LinearLayout) findViewById(R.id.ll_bottom_tab_template)).setOnClickListener(new View.OnClickListener() {
                @SensorsDataInstrumented
                public void onClick(View view) {
                    View view2 = view;
                    NetWorkMockFragment.this.switchBottomTabStatus(NetWorkMockFragment.BOTTOM_TAB_INDEX_1);
                    SensorsDataAutoTrackHelper.trackViewOnClick(view);
                }
            });
            this.mTvMock = (TextView) findViewById(R.id.tv_mock);
            this.mTvTemplate = (TextView) findViewById(R.id.tv_template);
            this.mIvMock = (ImageView) findViewById(R.id.iv_mock);
            this.mIvTemplate = (ImageView) findViewById(R.id.iv_template);
            this.mDropDownMenu = (DkDropDownMenu) findViewById(R.id.drop_down_menu);
            this.mRvWrap = new FrameLayout(getActivity());
            EasyRefreshLayout easyRefreshLayout = new EasyRefreshLayout(getActivity());
            this.mInterceptRefreshLayout = easyRefreshLayout;
            Resources resources = getResources();
            int i = R.color.dk_color_FFFFFF;
            easyRefreshLayout.setBackgroundColor(resources.getColor(i));
            RecyclerView recyclerView = new RecyclerView(getActivity());
            this.mRvIntercept = recyclerView;
            this.mInterceptRefreshLayout.addView(recyclerView);
            EasyRefreshLayout easyRefreshLayout2 = this.mInterceptRefreshLayout;
            LoadModel loadModel = LoadModel.NONE;
            easyRefreshLayout2.setLoadMoreModel(loadModel);
            this.mInterceptRefreshLayout.setEnablePullToRefresh(false);
            this.mInterceptRefreshLayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
                public void onLoadMore() {
                }

                public void onRefreshing() {
                    NetWorkMockFragment.this.initResponseApis();
                }
            });
            EasyRefreshLayout easyRefreshLayout3 = new EasyRefreshLayout(getActivity());
            this.mTemplateRefreshLayout = easyRefreshLayout3;
            easyRefreshLayout3.setBackgroundColor(getResources().getColor(i));
            RecyclerView recyclerView2 = new RecyclerView(getActivity());
            this.mRvTemplate = recyclerView2;
            this.mTemplateRefreshLayout.addView(recyclerView2);
            this.mTemplateRefreshLayout.setLoadMoreModel(loadModel);
            this.mTemplateRefreshLayout.setEnablePullToRefresh(false);
            this.mTemplateRefreshLayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
                public void onLoadMore() {
                }

                public void onRefreshing() {
                    NetWorkMockFragment.this.initResponseApis();
                }
            });
            this.mRvWrap.setBackgroundColor(getResources().getColor(R.color.dk_color_F5F6F7));
            this.mRvWrap.setPadding(0, f.e(4.0f), 0, 0);
            this.mRvWrap.addView(this.mInterceptRefreshLayout);
            this.mRvWrap.addView(this.mTemplateRefreshLayout);
            this.mRvIntercept.setLayoutManager(new LinearLayoutManager(getActivity()));
            this.mRvTemplate.setLayoutManager(new LinearLayoutManager(getActivity()));
            initResponseApis();
        }
    }

    /* access modifiers changed from: private */
    public void filterAndNotifyData() {
        boolean boolGroupMatched;
        boolean boolSwitchOpenMatched;
        boolean boolStrFilterMatched;
        boolean boolGroupMatched2;
        boolean boolSwitchOpenMatched2;
        boolean boolStrFilterMatched2;
        String strFilter = this.mEditText.getText().toString();
        int i = this.mSelectedTableIndex;
        if (i == BOTTOM_TAB_INDEX_0) {
            List<MockInterceptTitleBean> interceptTitleBeans = new ArrayList<>();
            for (MockInterceptTitleBean interceptTitleBean : this.mInterceptTitleBeans) {
                MockInterceptApiBean interceptApiBean = (MockInterceptApiBean) interceptTitleBean.getChildNode().get(0);
                if (TextUtils.isEmpty(this.mStrInterceptGroup)) {
                    boolGroupMatched2 = true;
                } else {
                    boolGroupMatched2 = interceptApiBean.getGroup().equals(this.mStrInterceptGroup);
                }
                int i2 = this.mInterceptOpenStatus;
                if (i2 == 0) {
                    boolSwitchOpenMatched2 = true;
                } else if (i2 == 1) {
                    if (interceptApiBean.isOpen()) {
                        boolSwitchOpenMatched2 = true;
                    } else {
                        boolSwitchOpenMatched2 = false;
                    }
                } else if (i2 != 2) {
                    boolSwitchOpenMatched2 = false;
                } else if (interceptApiBean.isOpen()) {
                    boolSwitchOpenMatched2 = false;
                } else {
                    boolSwitchOpenMatched2 = true;
                }
                if (TextUtils.isEmpty(strFilter)) {
                    boolStrFilterMatched2 = true;
                } else if (interceptApiBean.getMockApiName().contains(strFilter)) {
                    boolStrFilterMatched2 = true;
                } else {
                    boolStrFilterMatched2 = false;
                }
                if (boolGroupMatched2 && boolSwitchOpenMatched2 && boolStrFilterMatched2) {
                    interceptTitleBeans.add(interceptTitleBean);
                }
            }
            this.mInterceptApiAdapter.setNewInstance(interceptTitleBeans);
            this.mInterceptLoadMoreModule.loadMoreEnd();
            if (interceptTitleBeans.isEmpty()) {
                this.mInterceptApiAdapter.setEmptyView(R.layout.dk_rv_empty_layout2);
            }
        } else if (i == BOTTOM_TAB_INDEX_1) {
            List<MockTemplateTitleBean> templateTitleBeans = new ArrayList<>();
            for (MockTemplateTitleBean templateTitleBean : this.mTemplateTitleBeans) {
                MockTemplateApiBean templateApiBean = (MockTemplateApiBean) templateTitleBean.getChildNode().get(0);
                if (TextUtils.isEmpty(this.mStrTemplateGroup)) {
                    boolGroupMatched = true;
                } else {
                    boolGroupMatched = templateApiBean.getGroup().equals(this.mStrTemplateGroup);
                }
                int i3 = this.mTemplateOpenStatus;
                if (i3 == 0) {
                    boolSwitchOpenMatched = true;
                } else if (i3 == 1) {
                    if (templateApiBean.isOpen()) {
                        boolSwitchOpenMatched = true;
                    } else {
                        boolSwitchOpenMatched = false;
                    }
                } else if (i3 != 2) {
                    boolSwitchOpenMatched = false;
                } else if (templateApiBean.isOpen()) {
                    boolSwitchOpenMatched = false;
                } else {
                    boolSwitchOpenMatched = true;
                }
                if (TextUtils.isEmpty(strFilter)) {
                    boolStrFilterMatched = true;
                } else if (templateApiBean.getMockApiName().contains(strFilter)) {
                    boolStrFilterMatched = true;
                } else {
                    boolStrFilterMatched = false;
                }
                if (boolGroupMatched && boolSwitchOpenMatched && boolStrFilterMatched) {
                    templateTitleBeans.add(templateTitleBean);
                }
            }
            this.mTemplateApiAdapter.setNewInstance(templateTitleBeans);
            this.mTemplateLoadMoreModule.loadMoreEnd();
            if (templateTitleBeans.isEmpty()) {
                this.mTemplateApiAdapter.setEmptyView(R.layout.dk_rv_empty_layout2);
            }
        }
    }

    /* access modifiers changed from: private */
    public void attachInterceptRv(@NonNull List<MockInterceptTitleBean> mockTitleBeans) {
        this.mInterceptTitleBeans.addAll(mockTitleBeans);
        this.mInterceptRefreshLayout.refreshComplete();
        if (this.mInterceptApiAdapter == null) {
            InterceptMockAdapter interceptMockAdapter = new InterceptMockAdapter((List<BaseNode>) null);
            this.mInterceptApiAdapter = interceptMockAdapter;
            this.mRvIntercept.setAdapter(interceptMockAdapter);
            BaseLoadMoreModule loadMoreModule = this.mInterceptApiAdapter.getLoadMoreModule();
            this.mInterceptLoadMoreModule = loadMoreModule;
            loadMoreModule.setEnableLoadMore(false);
            this.mInterceptLoadMoreModule.setOnLoadMoreListener(new OnLoadMoreListener() {
                public void onLoadMore() {
                    NetWorkMockFragment.this.loadMoreResponseApis();
                }
            });
            this.mInterceptLoadMoreModule.setEnableLoadMoreIfNotFullPage(false);
        }
        if (mockTitleBeans.isEmpty()) {
            this.mInterceptApiAdapter.setEmptyView(R.layout.dk_rv_empty_layout);
            return;
        }
        this.mInterceptApiAdapter.setNewInstance(mockTitleBeans);
        if (mockTitleBeans.size() < this.pageSize) {
            this.mInterceptLoadMoreModule.loadMoreEnd();
        }
    }

    /* access modifiers changed from: private */
    public void attachTemplateRv(@NonNull List<MockTemplateTitleBean> mockTitleBeans) {
        this.mTemplateTitleBeans.addAll(mockTitleBeans);
        this.mTemplateRefreshLayout.refreshComplete();
        if (this.mTemplateApiAdapter == null) {
            TemplateMockAdapter templateMockAdapter = new TemplateMockAdapter((List<BaseNode>) null);
            this.mTemplateApiAdapter = templateMockAdapter;
            this.mRvTemplate.setAdapter(templateMockAdapter);
            BaseLoadMoreModule loadMoreModule = this.mTemplateApiAdapter.getLoadMoreModule();
            this.mTemplateLoadMoreModule = loadMoreModule;
            loadMoreModule.setEnableLoadMore(false);
            this.mTemplateLoadMoreModule.setOnLoadMoreListener(new OnLoadMoreListener() {
                public void onLoadMore() {
                    NetWorkMockFragment.this.loadMoreResponseApis();
                }
            });
            this.mTemplateLoadMoreModule.setEnableLoadMoreIfNotFullPage(false);
        }
        if (mockTitleBeans.isEmpty()) {
            this.mTemplateApiAdapter.setEmptyView(R.layout.dk_rv_empty_layout);
            return;
        }
        this.mTemplateApiAdapter.setNewInstance(mockTitleBeans);
        if (mockTitleBeans.size() < this.pageSize) {
            this.mTemplateLoadMoreModule.loadMoreEnd();
        }
    }

    /* access modifiers changed from: private */
    public void loadMoreInterceptDates(List<MockInterceptTitleBean> mockTitleBeans) {
        this.mInterceptApiAdapter.addData((Collection<? extends BaseNode>) mockTitleBeans);
        if (mockTitleBeans.size() < this.pageSize) {
            this.mInterceptLoadMoreModule.loadMoreEnd();
        } else {
            this.mInterceptLoadMoreModule.loadMoreComplete();
        }
    }

    /* access modifiers changed from: private */
    public void loadMoreTemplateDates(List<MockTemplateTitleBean> mockTitleBeans) {
        this.mTemplateApiAdapter.addData((Collection<? extends BaseNode>) mockTitleBeans);
        if (mockTitleBeans.size() < this.pageSize) {
            this.mTemplateLoadMoreModule.loadMoreEnd();
        } else {
            this.mTemplateLoadMoreModule.loadMoreComplete();
        }
    }

    /* access modifiers changed from: private */
    public void loadMoreResponseApis() {
        int curPage = 1;
        int i = this.mSelectedTableIndex;
        if (i == BOTTOM_TAB_INDEX_0) {
            curPage = (this.mInterceptApiAdapter.getData().size() / this.pageSize) + 1;
        } else if (i == BOTTOM_TAB_INDEX_1) {
            curPage = (this.mTemplateApiAdapter.getData().size() / this.pageSize) + 1;
        }
        ((GetRequest) DokitOkGo.get(String.format(this.mFormatApiUrl, new Object[]{this.projectId, Integer.valueOf(curPage), Integer.valueOf(this.pageSize)})).tag(this)).execute(new StringCallback() {
            public void onSuccess(Response<String> response) {
                try {
                    if (NetWorkMockFragment.this.mSelectedTableIndex == NetWorkMockFragment.BOTTOM_TAB_INDEX_0) {
                        NetWorkMockFragment.this.loadMoreInterceptDates(NetWorkMockFragment.this.dealInterceptResponseData(response.body()));
                    } else if (NetWorkMockFragment.this.mSelectedTableIndex == NetWorkMockFragment.BOTTOM_TAB_INDEX_1) {
                        NetWorkMockFragment.this.loadMoreTemplateDates(NetWorkMockFragment.this.dealTemplateResponseData(response.body()));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (NetWorkMockFragment.this.mSelectedTableIndex == NetWorkMockFragment.BOTTOM_TAB_INDEX_0) {
                        NetWorkMockFragment.this.mInterceptLoadMoreModule.loadMoreEnd();
                    } else if (NetWorkMockFragment.this.mSelectedTableIndex == NetWorkMockFragment.BOTTOM_TAB_INDEX_1) {
                        NetWorkMockFragment.this.mTemplateLoadMoreModule.loadMoreEnd();
                    }
                }
            }

            public void onError(Response<String> response) {
                super.onError(response);
                if (NetWorkMockFragment.this.mSelectedTableIndex == NetWorkMockFragment.BOTTOM_TAB_INDEX_0) {
                    NetWorkMockFragment.this.mInterceptLoadMoreModule.loadMoreEnd();
                } else if (NetWorkMockFragment.this.mSelectedTableIndex == NetWorkMockFragment.BOTTOM_TAB_INDEX_1) {
                    NetWorkMockFragment.this.mTemplateLoadMoreModule.loadMoreEnd();
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void initMenus(List<MockInterceptTitleBean> mockInterceptTitleBeans) {
        final List<String> groups = new ArrayList<>();
        groups.add(DokitUtil.getString(R.string.dk_data_mock_group));
        for (MockInterceptTitleBean mockInterceptTitleBean : mockInterceptTitleBeans) {
            MockInterceptApiBean mockInterceptApiBean = (MockInterceptApiBean) mockInterceptTitleBean.getChildNode().get(0);
            if (!groups.contains(mockInterceptApiBean.getGroup())) {
                groups.add(mockInterceptApiBean.getGroup());
            }
        }
        ListView mGroupListView = new ListView(getActivity());
        mGroupListView.setDividerHeight(0);
        ListDropDownAdapter listDropDownAdapter = new ListDropDownAdapter(getActivity(), groups);
        this.mGroupMenuAdapter = listDropDownAdapter;
        mGroupListView.setAdapter(listDropDownAdapter);
        mGroupListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v10, resolved type: java.lang.Object} */
            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v2, resolved type: java.lang.String} */
            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v16, resolved type: java.lang.Object} */
            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v5, resolved type: java.lang.String} */
            /* JADX WARNING: Multi-variable type inference failed */
            @com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void onItemClick(android.widget.AdapterView<?> r8, android.view.View r9, int r10, long r11) {
                /*
                    r7 = this;
                    r0 = r9
                    r1 = r8
                    r2 = r10
                    com.didichuxing.doraemonkit.kit.network.ui.NetWorkMockFragment r3 = com.didichuxing.doraemonkit.kit.network.ui.NetWorkMockFragment.this
                    com.didichuxing.doraemonkit.kit.network.ui.ListDropDownAdapter r3 = r3.mGroupMenuAdapter
                    r3.setCheckItem(r2)
                    com.didichuxing.doraemonkit.kit.network.ui.NetWorkMockFragment r3 = com.didichuxing.doraemonkit.kit.network.ui.NetWorkMockFragment.this
                    com.didichuxing.doraemonkit.widget.dropdown.DkDropDownMenu r3 = r3.mDropDownMenu
                    java.util.List r4 = r0
                    java.lang.Object r4 = r4.get(r2)
                    java.lang.String r4 = (java.lang.String) r4
                    r3.setTabText(r4)
                    com.didichuxing.doraemonkit.kit.network.ui.NetWorkMockFragment r3 = com.didichuxing.doraemonkit.kit.network.ui.NetWorkMockFragment.this
                    com.didichuxing.doraemonkit.widget.dropdown.DkDropDownMenu r3 = r3.mDropDownMenu
                    r3.closeMenu()
                    com.didichuxing.doraemonkit.kit.network.ui.NetWorkMockFragment r3 = com.didichuxing.doraemonkit.kit.network.ui.NetWorkMockFragment.this
                    int r3 = r3.mSelectedTableIndex
                    int r4 = com.didichuxing.doraemonkit.kit.network.ui.NetWorkMockFragment.BOTTOM_TAB_INDEX_0
                    java.lang.String r5 = ""
                    if (r3 != r4) goto L_0x0061
                    com.didichuxing.doraemonkit.kit.network.ui.NetWorkMockFragment r3 = com.didichuxing.doraemonkit.kit.network.ui.NetWorkMockFragment.this
                    com.didichuxing.doraemonkit.kit.network.ui.NetWorkMockFragment$FilterConditionBean r3 = r3.mInterceptFilterBean
                    r3.setGroupIndex(r2)
                    com.didichuxing.doraemonkit.kit.network.ui.NetWorkMockFragment r3 = com.didichuxing.doraemonkit.kit.network.ui.NetWorkMockFragment.this
                    java.util.List r4 = r0
                    java.lang.Object r4 = r4.get(r2)
                    java.lang.String r4 = (java.lang.String) r4
                    int r6 = com.didichuxing.doraemonkit.R.string.dk_data_mock_group
                    java.lang.String r6 = com.didichuxing.doraemonkit.util.DokitUtil.getString(r6)
                    boolean r4 = r4.equals(r6)
                    if (r4 == 0) goto L_0x0054
                    goto L_0x005d
                L_0x0054:
                    java.util.List r4 = r0
                    java.lang.Object r4 = r4.get(r2)
                    r5 = r4
                    java.lang.String r5 = (java.lang.String) r5
                L_0x005d:
                    java.lang.String unused = r3.mStrInterceptGroup = r5
                    goto L_0x0099
                L_0x0061:
                    com.didichuxing.doraemonkit.kit.network.ui.NetWorkMockFragment r3 = com.didichuxing.doraemonkit.kit.network.ui.NetWorkMockFragment.this
                    int r3 = r3.mSelectedTableIndex
                    int r4 = com.didichuxing.doraemonkit.kit.network.ui.NetWorkMockFragment.BOTTOM_TAB_INDEX_1
                    if (r3 != r4) goto L_0x0099
                    com.didichuxing.doraemonkit.kit.network.ui.NetWorkMockFragment r3 = com.didichuxing.doraemonkit.kit.network.ui.NetWorkMockFragment.this
                    com.didichuxing.doraemonkit.kit.network.ui.NetWorkMockFragment$FilterConditionBean r3 = r3.mTemplateFilterBean
                    r3.setGroupIndex(r2)
                    com.didichuxing.doraemonkit.kit.network.ui.NetWorkMockFragment r3 = com.didichuxing.doraemonkit.kit.network.ui.NetWorkMockFragment.this
                    java.util.List r4 = r0
                    java.lang.Object r4 = r4.get(r2)
                    java.lang.String r4 = (java.lang.String) r4
                    int r6 = com.didichuxing.doraemonkit.R.string.dk_data_mock_group
                    java.lang.String r6 = com.didichuxing.doraemonkit.util.DokitUtil.getString(r6)
                    boolean r4 = r4.equals(r6)
                    if (r4 == 0) goto L_0x008d
                    goto L_0x0096
                L_0x008d:
                    java.util.List r4 = r0
                    java.lang.Object r4 = r4.get(r2)
                    r5 = r4
                    java.lang.String r5 = (java.lang.String) r5
                L_0x0096:
                    java.lang.String unused = r3.mStrTemplateGroup = r5
                L_0x0099:
                    com.didichuxing.doraemonkit.kit.network.ui.NetWorkMockFragment r3 = com.didichuxing.doraemonkit.kit.network.ui.NetWorkMockFragment.this
                    r3.filterAndNotifyData()
                    com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper.trackListView(r8, r9, r10)
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.didichuxing.doraemonkit.kit.network.ui.NetWorkMockFragment.AnonymousClass10.onItemClick(android.widget.AdapterView, android.view.View, int, long):void");
            }
        });
        ListView mSwitchListView = new ListView(getActivity());
        mSwitchListView.setDividerHeight(0);
        ListDropDownAdapter listDropDownAdapter2 = new ListDropDownAdapter(getActivity(), Arrays.asList(this.mSwitchMenus));
        this.mSwitchMenuAdapter = listDropDownAdapter2;
        mSwitchListView.setAdapter(listDropDownAdapter2);
        mSwitchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SensorsDataInstrumented
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                View view2 = view;
                AdapterView<?> adapterView2 = adapterView;
                int position = i;
                NetWorkMockFragment.this.mSwitchMenuAdapter.setCheckItem(position);
                NetWorkMockFragment.this.mDropDownMenu.setTabText(NetWorkMockFragment.this.mSwitchMenus[position]);
                NetWorkMockFragment.this.mDropDownMenu.closeMenu();
                if (NetWorkMockFragment.this.mSelectedTableIndex == NetWorkMockFragment.BOTTOM_TAB_INDEX_0) {
                    NetWorkMockFragment.this.mInterceptFilterBean.setSwitchIndex(position);
                    int unused = NetWorkMockFragment.this.mInterceptOpenStatus = position;
                } else if (NetWorkMockFragment.this.mSelectedTableIndex == NetWorkMockFragment.BOTTOM_TAB_INDEX_1) {
                    NetWorkMockFragment.this.mTemplateFilterBean.setSwitchIndex(position);
                    int unused2 = NetWorkMockFragment.this.mTemplateOpenStatus = position;
                }
                NetWorkMockFragment.this.filterAndNotifyData();
                SensorsDataAutoTrackHelper.trackListView(adapterView, view, i);
            }
        });
        this.popupViews.add(mGroupListView);
        this.popupViews.add(mSwitchListView);
        this.mDropDownMenu.setDropDownMenu(Arrays.asList(this.mMenuHeaders), this.popupViews, this.mRvWrap);
        FilterConditionBean filterConditionBean = new FilterConditionBean();
        this.mInterceptFilterBean = filterConditionBean;
        filterConditionBean.setFilterText("");
        this.mInterceptFilterBean.setGroupIndex(0);
        this.mInterceptFilterBean.setSwitchIndex(0);
        FilterConditionBean filterConditionBean2 = new FilterConditionBean();
        this.mTemplateFilterBean = filterConditionBean2;
        filterConditionBean2.setFilterText("");
        this.mTemplateFilterBean.setGroupIndex(0);
        this.mTemplateFilterBean.setSwitchIndex(0);
        switchBottomTabStatus(BOTTOM_TAB_INDEX_0);
    }

    /* access modifiers changed from: private */
    public void initResponseApis() {
        String apiUrl = String.format(this.mFormatApiUrl, new Object[]{this.projectId, 1, Integer.valueOf(this.pageSize)});
        String str = this.TAG;
        LogHelper.i(str, "apiUrl===>" + apiUrl);
        ((GetRequest) DokitOkGo.get(apiUrl).tag(this)).execute(new StringCallback() {
            public void onSuccess(Response<String> response) {
                try {
                    if (NetWorkMockFragment.this.mSelectedTableIndex == NetWorkMockFragment.BOTTOM_TAB_INDEX_0) {
                        List<MockInterceptTitleBean> mockInterceptTitleBeans = NetWorkMockFragment.this.dealInterceptResponseData(response.body());
                        NetWorkMockFragment.this.initMenus(mockInterceptTitleBeans);
                        NetWorkMockFragment.this.attachInterceptRv(mockInterceptTitleBeans);
                    } else if (NetWorkMockFragment.this.mSelectedTableIndex == NetWorkMockFragment.BOTTOM_TAB_INDEX_1) {
                        NetWorkMockFragment.this.attachTemplateRv(NetWorkMockFragment.this.dealTemplateResponseData(response.body()));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (NetWorkMockFragment.this.mSelectedTableIndex == NetWorkMockFragment.BOTTOM_TAB_INDEX_0) {
                        NetWorkMockFragment.this.mInterceptRefreshLayout.refreshComplete();
                    } else if (NetWorkMockFragment.this.mSelectedTableIndex == NetWorkMockFragment.BOTTOM_TAB_INDEX_1) {
                        NetWorkMockFragment.this.mTemplateRefreshLayout.refreshComplete();
                    }
                }
            }

            public void onError(Response<String> response) {
                super.onError(response);
                String str = NetWorkMockFragment.this.TAG;
                LogHelper.e(str, "error====>" + response.getException().getMessage());
                e0.n(response.getException().getMessage());
                if (NetWorkMockFragment.this.mSelectedTableIndex == NetWorkMockFragment.BOTTOM_TAB_INDEX_0) {
                    NetWorkMockFragment.this.mInterceptRefreshLayout.refreshComplete();
                } else if (NetWorkMockFragment.this.mSelectedTableIndex == NetWorkMockFragment.BOTTOM_TAB_INDEX_1) {
                    NetWorkMockFragment.this.mTemplateRefreshLayout.refreshComplete();
                }
            }
        });
    }

    /* access modifiers changed from: private */
    @NonNull
    public List<MockInterceptTitleBean> dealInterceptResponseData(String strResponse) {
        JSONObject queryJsonObject;
        JSONObject bodyJsonObject;
        String str = strResponse;
        JSONObject responseJsonObject = new JSONObject(str);
        JSONArray jsonArray = responseJsonObject.getJSONObject("data").getJSONArray("datalist");
        List<MockApiResponseBean.DataBean.DatalistBean> lists = ((MockApiResponseBean) k.d(str, MockApiResponseBean.class)).getData().getDatalist();
        ArrayList<MockInterceptTitleBean> mockInterceptTitleBeans = new ArrayList<>();
        int index = 0;
        while (index < lists.size()) {
            MockApiResponseBean.DataBean.DatalistBean datalistBean = lists.get(index);
            JSONObject mockJsonObject = jsonArray.getJSONObject(index);
            if (mockJsonObject.has(SearchIntents.EXTRA_QUERY)) {
                queryJsonObject = mockJsonObject.getJSONObject(SearchIntents.EXTRA_QUERY);
            } else {
                queryJsonObject = new JSONObject();
            }
            if (mockJsonObject.has("body")) {
                bodyJsonObject = mockJsonObject.getJSONObject("body");
            } else {
                bodyJsonObject = new JSONObject();
            }
            String modifyName = BuildConfig.TRAVIS;
            if (!(datalistBean.getCurStatus() == null || datalistBean.getCurStatus().getOperator() == null)) {
                modifyName = datalistBean.getCurStatus().getOperator().getName();
            }
            List<MockInterceptApiBean> mockInterceptApiBeans = new ArrayList<>();
            MockInterceptApiBean mockInterceptApiBean = r12;
            JSONObject responseJsonObject2 = responseJsonObject;
            List<MockInterceptApiBean> mockInterceptApiBeans2 = mockInterceptApiBeans;
            MockInterceptApiBean mockInterceptApiBean2 = new MockInterceptApiBean(datalistBean.get_id(), datalistBean.getName(), datalistBean.getPath(), datalistBean.getMethod(), datalistBean.getFormatType(), queryJsonObject.toString(), bodyJsonObject.toString(), datalistBean.getCategoryName(), datalistBean.getOwner().getName(), modifyName, datalistBean.getSceneList());
            mockInterceptApiBeans2.add(mockInterceptApiBean);
            mockInterceptTitleBeans.add(new MockInterceptTitleBean(datalistBean.getName(), mockInterceptApiBeans2));
            index++;
            String str2 = strResponse;
            responseJsonObject = responseJsonObject2;
        }
        insertAllInterceptApis(mockInterceptTitleBeans);
        return mockInterceptTitleBeans;
    }

    /* access modifiers changed from: private */
    @NonNull
    public List<MockTemplateTitleBean> dealTemplateResponseData(String strResponse) {
        JSONObject queryJsonObject;
        JSONObject bodyJsonObject;
        String str = strResponse;
        JSONObject responseJsonObject = new JSONObject(str);
        JSONArray jsonArray = responseJsonObject.getJSONObject("data").getJSONArray("datalist");
        List<MockApiResponseBean.DataBean.DatalistBean> lists = ((MockApiResponseBean) k.d(str, MockApiResponseBean.class)).getData().getDatalist();
        ArrayList<MockTemplateTitleBean> mockTemplateTitleBeans = new ArrayList<>();
        int index = 0;
        while (index < lists.size()) {
            MockApiResponseBean.DataBean.DatalistBean datalistBean = lists.get(index);
            JSONObject mockJsonObject = jsonArray.getJSONObject(index);
            if (mockJsonObject.has(SearchIntents.EXTRA_QUERY)) {
                queryJsonObject = mockJsonObject.getJSONObject(SearchIntents.EXTRA_QUERY);
            } else {
                queryJsonObject = new JSONObject();
            }
            if (mockJsonObject.has("body")) {
                bodyJsonObject = mockJsonObject.getJSONObject("body");
            } else {
                bodyJsonObject = new JSONObject();
            }
            String modifyName = BuildConfig.TRAVIS;
            if (!(datalistBean.getCurStatus() == null || datalistBean.getCurStatus().getOperator() == null)) {
                modifyName = datalistBean.getCurStatus().getOperator().getName();
            }
            List<MockTemplateApiBean> mockTemplateApiBeans = new ArrayList<>();
            MockTemplateApiBean mockTemplateApiBean = r12;
            JSONObject responseJsonObject2 = responseJsonObject;
            List<MockTemplateApiBean> mockTemplateApiBeans2 = mockTemplateApiBeans;
            MockTemplateApiBean mockTemplateApiBean2 = new MockTemplateApiBean(datalistBean.get_id(), datalistBean.getName(), datalistBean.getPath(), datalistBean.getMethod(), datalistBean.getFormatType(), queryJsonObject.toString(), bodyJsonObject.toString(), datalistBean.getCategoryName(), datalistBean.getOwner().getName(), modifyName, datalistBean.getProjectId());
            mockTemplateApiBeans2.add(mockTemplateApiBean);
            mockTemplateTitleBeans.add(new MockTemplateTitleBean(datalistBean.getName(), mockTemplateApiBeans2));
            index++;
            String str2 = strResponse;
            responseJsonObject = responseJsonObject2;
        }
        insertAllTemplateApis(mockTemplateTitleBeans);
        return mockTemplateTitleBeans;
    }

    private void insertAllInterceptApis(ArrayList<MockInterceptTitleBean> mockTitleBeans) {
        List<MockInterceptApiBean> mockApis = new ArrayList<>();
        Iterator<MockInterceptTitleBean> it = mockTitleBeans.iterator();
        while (it.hasNext()) {
            MockInterceptApiBean mockApi = (MockInterceptApiBean) it.next().getChildNode().get(0);
            if (!hasInterceptApiInDb(mockApi.getPath(), mockApi.getId())) {
                mockApis.add(mockApi);
            } else {
                updateInterceptApi(mockApi);
            }
        }
        DokitDbManager.getInstance().insertAllInterceptApi(mockApis);
    }

    private void insertAllTemplateApis(ArrayList<MockTemplateTitleBean> mockTitleBeans) {
        List<MockTemplateApiBean> mockApis = new ArrayList<>();
        Iterator<MockTemplateTitleBean> it = mockTitleBeans.iterator();
        while (it.hasNext()) {
            MockTemplateApiBean mockApi = (MockTemplateApiBean) it.next().getChildNode().get(0);
            if (!hasTemplateApiInDb(mockApi.getPath(), mockApi.getId())) {
                mockApis.add(mockApi);
            } else {
                updateTemplateApi(mockApi);
            }
        }
        DokitDbManager.getInstance().insertAllTemplateApi(mockApis);
    }

    private void updateInterceptApi(MockInterceptApiBean mockApi) {
        List<MockInterceptApiBean> localInterceptApis = (List) DokitDbManager.getInstance().getGlobalInterceptApiMaps().get(mockApi.getPath());
        if (localInterceptApis != null) {
            for (MockInterceptApiBean localMockApi : localInterceptApis) {
                if (localMockApi.getId().equals(mockApi.getId())) {
                    mockApi.setOpen(localMockApi.isOpen());
                    mockApi.setSelectedSceneId(localMockApi.getSelectedSceneId());
                    mockApi.setSelectedSceneName(localMockApi.getSelectedSceneName());
                    return;
                }
            }
        }
    }

    private void updateTemplateApi(MockTemplateApiBean mockApi) {
        List<MockTemplateApiBean> localTemplateApis = (List) DokitDbManager.getInstance().getGlobalTemplateApiMaps().get(mockApi.getPath());
        if (localTemplateApis != null) {
            for (MockTemplateApiBean localMockApi : localTemplateApis) {
                if (localMockApi.getId().equals(mockApi.getId())) {
                    mockApi.setOpen(localMockApi.isOpen());
                    mockApi.setResponseFrom(localMockApi.getResponseFrom());
                    mockApi.setStrResponse(localMockApi.getStrResponse());
                    return;
                }
            }
        }
    }

    private boolean hasInterceptApiInDb(String path, String id) {
        return ((MockInterceptApiBean) DokitDbManager.getInstance().getInterceptApiByIdInMap(path, id, DokitDbManager.FROM_SDK_OTHER)) != null;
    }

    private boolean hasTemplateApiInDb(String path, String id) {
        return ((MockTemplateApiBean) DokitDbManager.getInstance().getTemplateApiByIdInMap(path, id, DokitDbManager.FROM_SDK_OTHER)) != null;
    }

    /* access modifiers changed from: private */
    public void switchBottomTabStatus(int tabIndex) {
        switch (tabIndex) {
            case 0:
                this.mTvMock.setTextColor(getResources().getColor(R.color.dk_color_337CC4));
                this.mTvTemplate.setTextColor(getResources().getColor(R.color.dk_color_333333));
                this.mIvMock.setImageResource(R.mipmap.dk_mock_highlight);
                this.mIvTemplate.setImageResource(R.mipmap.dk_template_normal);
                this.mInterceptRefreshLayout.setVisibility(0);
                this.mTemplateRefreshLayout.setVisibility(8);
                this.mSelectedTableIndex = BOTTOM_TAB_INDEX_0;
                break;
            case 1:
                this.mTvMock.setTextColor(getResources().getColor(R.color.dk_color_333333));
                this.mTvTemplate.setTextColor(getResources().getColor(R.color.dk_color_337CC4));
                this.mIvMock.setImageResource(R.mipmap.dk_mock_normal);
                this.mIvTemplate.setImageResource(R.mipmap.dk_template_highlight);
                this.mInterceptRefreshLayout.setVisibility(8);
                this.mTemplateRefreshLayout.setVisibility(0);
                this.mSelectedTableIndex = BOTTOM_TAB_INDEX_1;
                if (this.mTemplateApiAdapter == null) {
                    initResponseApis();
                    break;
                }
                break;
        }
        resetMenuStatus();
    }

    private void resetMenuStatus() {
        FilterConditionBean filterConditionBean;
        int i = this.mSelectedTableIndex;
        if (i == BOTTOM_TAB_INDEX_0) {
            FilterConditionBean filterConditionBean2 = this.mInterceptFilterBean;
            if (filterConditionBean2 != null) {
                this.mGroupMenuAdapter.setCheckItem(filterConditionBean2.getGroupIndex());
                this.mSwitchMenuAdapter.setCheckItem(this.mInterceptFilterBean.getSwitchIndex());
                this.mDropDownMenu.resetTabText(new String[]{this.mGroupMenuAdapter.getList().get(this.mInterceptFilterBean.getGroupIndex()), this.mSwitchMenuAdapter.getList().get(this.mInterceptFilterBean.getSwitchIndex())});
                EditText editText = this.mEditText;
                editText.setText("" + this.mInterceptFilterBean.getFilterText());
            }
        } else if (i == BOTTOM_TAB_INDEX_1 && (filterConditionBean = this.mTemplateFilterBean) != null) {
            this.mGroupMenuAdapter.setCheckItem(filterConditionBean.getGroupIndex());
            this.mSwitchMenuAdapter.setCheckItem(this.mTemplateFilterBean.getSwitchIndex());
            this.mDropDownMenu.resetTabText(new String[]{this.mGroupMenuAdapter.getList().get(this.mTemplateFilterBean.getGroupIndex()), this.mSwitchMenuAdapter.getList().get(this.mTemplateFilterBean.getSwitchIndex())});
            EditText editText2 = this.mEditText;
            editText2.setText("" + this.mTemplateFilterBean.getFilterText());
        }
        this.mDropDownMenu.closeMenu();
    }

    public static class FilterConditionBean {
        String filterText;
        int groupIndex;
        int switchIndex;

        private FilterConditionBean() {
        }

        public int getGroupIndex() {
            return this.groupIndex;
        }

        public void setGroupIndex(int groupIndex2) {
            this.groupIndex = groupIndex2;
        }

        public int getSwitchIndex() {
            return this.switchIndex;
        }

        public void setSwitchIndex(int switchIndex2) {
            this.switchIndex = switchIndex2;
        }

        public String getFilterText() {
            return this.filterText;
        }

        public void setFilterText(String filterText2) {
            this.filterText = filterText2;
        }

        public String toString() {
            return "FilterConditionBean{groupIndex=" + this.groupIndex + ", switchIndex=" + this.switchIndex + ", filterText='" + this.filterText + '\'' + '}';
        }
    }
}
