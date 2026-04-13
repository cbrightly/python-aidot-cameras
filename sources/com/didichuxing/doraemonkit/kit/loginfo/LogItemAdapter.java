package com.didichuxing.doraemonkit.kit.loginfo;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import com.blankj.utilcode.util.e0;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.kit.loginfo.util.SearchCriteria;
import com.didichuxing.doraemonkit.kit.loginfo.util.TagColorUtil;
import com.didichuxing.doraemonkit.widget.recyclerview.AbsRecyclerAdapter;
import com.didichuxing.doraemonkit.widget.recyclerview.AbsViewBinder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class LogItemAdapter extends AbsRecyclerAdapter<AbsViewBinder<LogLine>, LogLine> implements Filterable {
    /* access modifiers changed from: private */
    public int logLevelLimit = 2;
    /* access modifiers changed from: private */
    public ClipboardManager mClipboard;
    private ArrayFilter mFilter = new ArrayFilter();
    /* access modifiers changed from: private */
    public ArrayList<LogLine> mOriginalValues = new ArrayList<>();

    public LogItemAdapter(Context context) {
        super(context);
        this.mClipboard = (ClipboardManager) context.getSystemService("clipboard");
    }

    public void clearLog() {
        ArrayList<LogLine> arrayList = this.mOriginalValues;
        if (arrayList != null && arrayList.size() > 0) {
            this.mOriginalValues.clear();
        }
        clear();
        notifyDataSetChanged();
    }

    /* access modifiers changed from: protected */
    public AbsViewBinder<LogLine> createViewHolder(View view, int viewType) {
        return new LogInfoViewHolder(view);
    }

    /* access modifiers changed from: protected */
    public View createView(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return inflater.inflate(R.layout.dk_item_log, parent, false);
    }

    public Filter getFilter() {
        return this.mFilter;
    }

    public int getLogLevelLimit() {
        return this.logLevelLimit;
    }

    public void setLogLevelLimit(int logLevelLimit2) {
        this.logLevelLimit = logLevelLimit2;
    }

    public List<LogLine> getTrueValues() {
        ArrayList<LogLine> arrayList = this.mOriginalValues;
        return arrayList != null ? arrayList : this.mList;
    }

    public void removeFirst(int n) {
        ArrayList<LogLine> arrayList = this.mOriginalValues;
        if (arrayList != null) {
            List<LogLine> subList = arrayList.subList(n, arrayList.size());
            for (int i = 0; i < n; i++) {
                this.mList.remove(this.mOriginalValues.get(i));
            }
            this.mOriginalValues = new ArrayList<>(subList);
        }
        notifyDataSetChanged();
    }

    public class LogInfoViewHolder extends AbsViewBinder<LogLine> {
        private TextView mLevel;
        private TextView mLogText;
        private TextView mPid;
        private TextView mTag;
        private TextView mTime;

        public LogInfoViewHolder(View view) {
            super(view);
        }

        /* access modifiers changed from: protected */
        public void getViews() {
            this.mLogText = (TextView) getView(R.id.log_output_text);
            this.mLevel = (TextView) getView(R.id.log_level_text);
            this.mPid = (TextView) getView(R.id.pid_text);
            this.mTime = (TextView) getView(R.id.timestamp_text);
            this.mTag = (TextView) getView(R.id.tag_text);
        }

        /* access modifiers changed from: protected */
        public void onViewClick(View view, final LogLine data) {
            super.onViewClick(view, data);
            data.setExpanded(!data.isExpanded());
            if (!data.isExpanded() || data.getProcessId() == -1) {
                this.mLogText.setSingleLine(true);
                this.mTime.setVisibility(8);
                this.mPid.setVisibility(8);
                view.setBackgroundColor(-1);
                this.mLogText.setTextColor(TagColorUtil.getTextColor(getContext(), data.getLogLevel(), false));
                this.mTag.setTextColor(TagColorUtil.getTextColor(getContext(), data.getLogLevel(), false));
            } else {
                this.mLogText.setSingleLine(false);
                this.mTime.setVisibility(0);
                this.mPid.setVisibility(0);
                view.setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
                this.mLogText.setTextColor(TagColorUtil.getTextColor(getContext(), data.getLogLevel(), true));
                this.mTag.setTextColor(TagColorUtil.getTextColor(getContext(), data.getLogLevel(), true));
            }
            this.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                public boolean onLongClick(View v) {
                    LogItemAdapter.this.mClipboard.setPrimaryClip(ClipData.newPlainText("Label", data.getOriginalLine()));
                    e0.n("copy success");
                    return true;
                }
            });
        }

        public void bind(LogLine item) {
            this.mLevel.setText(item.getLogLevelText());
            this.mLevel.setTextColor(TagColorUtil.getLevelColor(getContext(), item.getLogLevel()));
            this.mLevel.setBackgroundColor(TagColorUtil.getLevelBgColor(getContext(), item.getLogLevel()));
            this.mPid.setText(String.valueOf(item.getProcessId()));
            this.mTime.setText(item.getTimestamp());
            this.mLogText.setText(item.getLogOutput());
            this.mTag.setText(item.getTag());
            if (!item.isExpanded() || item.getProcessId() == -1) {
                this.mLogText.setSingleLine(true);
                this.mTime.setVisibility(8);
                this.mPid.setVisibility(8);
                this.itemView.setBackgroundColor(-1);
                this.mLogText.setTextColor(TagColorUtil.getTextColor(getContext(), item.getLogLevel(), false));
                this.mTag.setTextColor(TagColorUtil.getTextColor(getContext(), item.getLogLevel(), false));
                return;
            }
            this.mLogText.setSingleLine(false);
            this.mTime.setVisibility(0);
            this.mPid.setVisibility(0);
            this.mLogText.setTextColor(TagColorUtil.getTextColor(getContext(), item.getLogLevel(), true));
            this.mTag.setTextColor(TagColorUtil.getTextColor(getContext(), item.getLogLevel(), true));
            this.itemView.setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
        }
    }

    public void addWithFilter(LogLine logObj, CharSequence text, boolean notify) {
        if (this.mOriginalValues != null) {
            List<LogLine> filteredObjects = this.mFilter.performFilteringOnList(Collections.singletonList(logObj), text);
            this.mOriginalValues.add(logObj);
            this.mList.addAll(filteredObjects);
            if (notify) {
                notifyItemRangeInserted(this.mList.size() - filteredObjects.size(), filteredObjects.size());
                return;
            }
            return;
        }
        this.mList.add(logObj);
        if (notify) {
            notifyItemInserted(this.mList.size());
        }
    }

    public class ArrayFilter extends Filter {
        private ArrayFilter() {
        }

        /* access modifiers changed from: protected */
        public Filter.FilterResults performFiltering(CharSequence prefix) {
            Filter.FilterResults results = new Filter.FilterResults();
            ArrayList<LogLine> allValues = performFilteringOnList(LogItemAdapter.this.mOriginalValues, prefix);
            results.values = allValues;
            results.count = allValues.size();
            return results;
        }

        public ArrayList<LogLine> performFilteringOnList(List<LogLine> inputList, CharSequence query) {
            SearchCriteria searchCriteria = new SearchCriteria(query);
            ArrayList<LogLine> allValues = new ArrayList<>();
            Iterator<LogLine> it = new ArrayList<>(inputList).iterator();
            while (it.hasNext()) {
                LogLine logLine = it.next();
                if (logLine != null && logLine.getLogLevel() >= LogItemAdapter.this.logLevelLimit) {
                    allValues.add(logLine);
                }
            }
            ArrayList<LogLine> finalValues = allValues;
            if (searchCriteria.isEmpty()) {
                return finalValues;
            }
            ArrayList<LogLine> values = allValues;
            int count = values.size();
            ArrayList<LogLine> newValues = new ArrayList<>(count);
            for (int i = 0; i < count; i++) {
                LogLine value = values.get(i);
                if (searchCriteria.matches(value)) {
                    newValues.add(value);
                }
            }
            return newValues;
        }

        /* access modifiers changed from: protected */
        public void publishResults(CharSequence constraint, Filter.FilterResults results) {
            List unused = LogItemAdapter.this.mList = (List) results.values;
            if (results.count > 0) {
                LogItemAdapter.this.notifyDataSetChanged();
            } else {
                LogItemAdapter.this.notifyDataSetChanged();
            }
        }
    }
}
