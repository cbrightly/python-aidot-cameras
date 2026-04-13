package com.didichuxing.doraemonkit.kit.network.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.kit.network.bean.NetworkRecord;
import com.didichuxing.doraemonkit.kit.network.bean.Request;
import com.didichuxing.doraemonkit.kit.network.bean.Response;
import com.didichuxing.doraemonkit.kit.network.utils.ByteUtil;
import com.didichuxing.doraemonkit.widget.recyclerview.AbsRecyclerAdapter;
import com.didichuxing.doraemonkit.widget.recyclerview.AbsViewBinder;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class NetworkListAdapter extends AbsRecyclerAdapter<AbsViewBinder<NetworkRecord>, NetworkRecord> implements Filterable {
    private Filter mFilter = new Filter() {
        /* access modifiers changed from: protected */
        public Filter.FilterResults performFiltering(CharSequence constraint) {
            String charString = constraint.toString();
            List<NetworkRecord> filteredList = new ArrayList<>();
            if (charString.isEmpty()) {
                filteredList = NetworkListAdapter.this.mSourceList;
            } else {
                for (NetworkRecord record : NetworkListAdapter.this.mSourceList) {
                    if (record.filter(charString)) {
                        filteredList.add(record);
                    }
                }
            }
            Filter.FilterResults filterResults = new Filter.FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        /* access modifiers changed from: protected */
        public void publishResults(CharSequence constraint, Filter.FilterResults results) {
            List<NetworkRecord> filteredList = (List) results.values;
            if (filteredList == null || filteredList.size() == 0) {
                NetworkListAdapter.this.clear();
            } else {
                NetworkListAdapter.super.setData(filteredList);
            }
            NetworkListAdapter.this.notifyDataSetChanged();
        }
    };
    /* access modifiers changed from: private */
    public OnItemClickListener mListener;
    /* access modifiers changed from: private */
    public List<NetworkRecord> mSourceList = new ArrayList();

    public interface OnItemClickListener {
        void onClick(NetworkRecord networkRecord);
    }

    public NetworkListAdapter(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    public AbsViewBinder<NetworkRecord> createViewHolder(View view, int viewType) {
        return new ItemViewHolder(view);
    }

    /* access modifiers changed from: protected */
    public View createView(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return inflater.inflate(R.layout.dk_item_network_list, parent, false);
    }

    public Filter getFilter() {
        return this.mFilter;
    }

    public class ItemViewHolder extends AbsViewBinder<NetworkRecord> {
        private static final String CODE_FORMAT = "[%d]";
        private static final String FLOW_FORMAT = "↑ %s ↓%s";
        private static final String METHOD_FORMAT = "%s>%s";
        private static final String UNKNOWN = "unknown";
        private TextView code;
        private TextView flow;
        private SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss:SSS");
        private TextView method;
        private TextView time;
        private TextView url;

        public ItemViewHolder(View view) {
            super(view);
        }

        /* access modifiers changed from: protected */
        public void getViews() {
            this.url = (TextView) getView(R.id.network_list_url);
            this.method = (TextView) getView(R.id.network_list_method);
            this.code = (TextView) getView(R.id.network_list_code);
            this.time = (TextView) getView(R.id.network_list_time_and_cost);
            this.flow = (TextView) getView(R.id.network_list_flow);
        }

        public void bind(final NetworkRecord record) {
            String cost;
            if (record.mRequest != null) {
                this.url.setText(record.mRequest.url);
                if (record.endTime < record.startTime) {
                    cost = "unknown";
                } else {
                    cost = (((float) (record.endTime - record.startTime)) / 1000.0f) + "s";
                }
                this.time.setText(getContext().getString(R.string.dk_kit_network_time_format, new Object[]{this.mDateFormat.format(new Date(record.startTime)), cost}));
            } else {
                this.url.setText("unknown");
                this.time.setText(getContext().getString(R.string.dk_kit_network_time_format, new Object[]{"unknown", "unknown"}));
            }
            if (record.mResponse == null || record.mRequest == null) {
                this.code.setText("unknown");
                this.method.setText("unknown");
            } else {
                Request request = record.mRequest;
                Response response = record.mResponse;
                this.method.setText(String.format(METHOD_FORMAT, new Object[]{request.method, response.mimeType}));
                this.code.setText(String.format(CODE_FORMAT, new Object[]{Integer.valueOf(response.status)}));
            }
            this.flow.setText(String.format(FLOW_FORMAT, new Object[]{ByteUtil.getPrintSize(record.requestLength), ByteUtil.getPrintSize(record.responseLength)}));
            this.itemView.setOnClickListener(new View.OnClickListener() {
                @SensorsDataInstrumented
                public void onClick(View view) {
                    View view2 = view;
                    if (NetworkListAdapter.this.mListener != null) {
                        NetworkListAdapter.this.mListener.onClick(record);
                    }
                    SensorsDataAutoTrackHelper.trackViewOnClick(view);
                }
            });
        }
    }

    public void setData(Collection<NetworkRecord> items) {
        this.mSourceList.clear();
        this.mSourceList.addAll(items);
        super.setData(items);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }
}
