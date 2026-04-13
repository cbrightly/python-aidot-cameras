package com.didichuxing.doraemonkit.kit.performance;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.constant.BundleKey;
import com.didichuxing.doraemonkit.kit.core.BaseFragment;
import com.didichuxing.doraemonkit.kit.performance.PerformanceDataAdapter;
import com.didichuxing.doraemonkit.kit.performance.PolyLineAdapter;
import com.didichuxing.doraemonkit.widget.titlebar.TitleBar;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PerformanceFragment extends BaseFragment {
    public static final int CPU = 0;
    public static final int FPS = 2;
    public static final int RAM = 1;
    /* access modifiers changed from: private */
    public PolyLineAdapter adapter;
    private TextView date;
    private TextView parameter;
    /* access modifiers changed from: private */
    public PerformanceDataAdapter performanceDataAdapter;
    private TextView time;

    /* access modifiers changed from: protected */
    public int onRequestLayout() {
        return R.layout.dk_fragment_cpu_cache_log;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PerformanceDataManager.getInstance().init();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initview();
    }

    private void initview() {
        ((TitleBar) findViewById(R.id.title_bar)).setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            public void onLeftClick() {
                PerformanceFragment.this.getActivity().onBackPressed();
            }

            public void onRightClick() {
            }
        });
        RecyclerView dataShow = (RecyclerView) findViewById(R.id.data_show);
        PolyLineAdapter.Builder builder = new PolyLineAdapter.Builder(getActivity(), 10);
        dataShow.setLayoutManager(new LinearLayoutManager(getActivity(), 0, false));
        RecyclerView detail = (RecyclerView) findViewById(R.id.data_detail);
        detail.setLayoutManager(new LinearLayoutManager(getActivity()));
        PerformanceDataAdapter performanceDataAdapter2 = new PerformanceDataAdapter(getActivity());
        this.performanceDataAdapter = performanceDataAdapter2;
        detail.setAdapter(performanceDataAdapter2);
        TextView model = (TextView) findViewById(R.id.model);
        Bundle arguments = getArguments();
        if (arguments != null) {
            int type = arguments.getInt(BundleKey.PERFORMANCE_TYPE, 0);
            if (type == 1) {
                builder.setMaxValue((int) PerformanceDataManager.getInstance().getMaxMemory()).setMinValue(0);
                model.setText(R.string.dk_frameinfo_ram);
                new LoadDataTask().execute(new String[]{PerformanceDataManager.getInstance().getMemoryFilePath()});
            } else if (type == 2) {
                builder.setMaxValue(100).setMinValue(0);
                model.setText(R.string.dk_frameinfo_fps);
                new LoadDataTask().execute(new String[]{PerformanceDataManager.getInstance().getFpsFilePath()});
            } else {
                builder.setMaxValue(100).setMinValue(0);
                model.setText(R.string.dk_frameinfo_cpu);
                new LoadDataTask().execute(new String[]{PerformanceDataManager.getInstance().getCpuFilePath()});
            }
        }
        PolyLineAdapter build = builder.build();
        this.adapter = build;
        dataShow.setAdapter(build);
        this.parameter = (TextView) findViewById(R.id.parameter);
        this.time = (TextView) findViewById(R.id.time);
        this.date = (TextView) findViewById(R.id.date);
        this.performanceDataAdapter.setOnViewClickListener(new PerformanceDataAdapter.OnViewClickListener() {
            public void onViewClick(View v, PerformanceData data) {
                PerformanceFragment.this.updateTips(data);
            }
        });
        this.adapter.setOnViewClickListener(new PolyLineAdapter.OnViewClickListener() {
            public void onViewClick(int position, PerformanceData data) {
                PerformanceFragment.this.updateTips(data);
            }
        });
    }

    /* access modifiers changed from: private */
    public void updateTips(PerformanceData data) {
        this.parameter.setText(String.valueOf(data.parameter));
        this.time.setText(data.time);
        this.date.setText(data.date);
    }

    public class LoadDataTask extends AsyncTask<String, Integer, List<PerformanceData>> {
        private LoadDataTask() {
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(List<PerformanceData> result) {
            PerformanceFragment.this.performanceDataAdapter.append(result);
            PerformanceFragment.this.adapter.setData(result);
            if (result.size() > 1) {
                PerformanceFragment.this.updateTips(result.get(1));
            }
        }

        /* access modifiers changed from: protected */
        public List doInBackground(String... strings) {
            File file = new File(strings[0]);
            ArrayList<PerformanceData> datas = new ArrayList<>();
            if (file.exists()) {
                BufferedReader reader = null;
                try {
                    BufferedReader reader2 = new BufferedReader(new FileReader(file));
                    while (true) {
                        String readLine = reader2.readLine();
                        String tempString = readLine;
                        if (readLine == null) {
                            break;
                        }
                        String[] split = tempString.split(" ");
                        datas.add(new PerformanceData(split[1], split[2], Float.valueOf(split[0]).floatValue()));
                    }
                    reader2.close();
                    try {
                        reader2.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (reader != null) {
                        reader.close();
                    }
                } catch (Throwable th) {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e12) {
                            e12.printStackTrace();
                        }
                    }
                    throw th;
                }
            }
            return datas;
        }
    }
}
