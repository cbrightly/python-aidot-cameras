package com.didichuxing.doraemonkit.kit.fileexplorer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.didichuxing.doraemonkit.R;
import java.util.List;

public class DBListAdapter extends BaseAdapter {
    private Context context;
    private List data;

    public DBListAdapter(Context context2, List data2) {
        this.data = data2;
        this.context = context2;
    }

    public int getCount() {
        return this.data.size();
    }

    public Object getItem(int position) {
        return this.data.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(R.layout.dk_item_file_info, (ViewGroup) null);
            convertView.setTag(new ViewHolder(convertView));
        }
        ((ViewHolder) convertView.getTag()).setData((String) this.data.get(position));
        return convertView;
    }

    public class ViewHolder {
        public TextView textView;

        public ViewHolder(View convertView) {
            this.textView = (TextView) convertView.findViewById(R.id.name);
            convertView.findViewById(R.id.icon).setVisibility(8);
        }

        public void setData(String data) {
            this.textView.setText(data);
        }
    }
}
