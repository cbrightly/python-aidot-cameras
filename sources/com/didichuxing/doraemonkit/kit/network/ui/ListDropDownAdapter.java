package com.didichuxing.doraemonkit.kit.network.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.didichuxing.doraemonkit.R;
import java.util.List;

public class ListDropDownAdapter extends BaseAdapter {
    private int checkItemPosition = 0;
    private Context context;
    private List<String> list;

    public List<String> getList() {
        return this.list;
    }

    public void setCheckItem(int position) {
        this.checkItemPosition = position;
        notifyDataSetChanged();
    }

    public ListDropDownAdapter(Context context2, List<String> list2) {
        this.context = context2;
        this.list = list2;
    }

    public int getCount() {
        return this.list.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(this.context).inflate(R.layout.dk_item_default_drop_down, (ViewGroup) null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        fillValue(position, viewHolder);
        return convertView;
    }

    private void fillValue(int position, ViewHolder viewHolder) {
        viewHolder.mText.setText(this.list.get(position));
        int i = this.checkItemPosition;
        if (i == -1) {
            return;
        }
        if (i == position) {
            viewHolder.mText.setTextColor(this.context.getResources().getColor(R.color.dk_drop_down_selected));
            viewHolder.mText.setBackgroundResource(R.color.dk_check_bg);
            return;
        }
        viewHolder.mText.setTextColor(this.context.getResources().getColor(R.color.dk_drop_down_unselected));
        viewHolder.mText.setBackgroundResource(R.color.dk_color_FFFFFF);
    }

    public static class ViewHolder {
        TextView mText;

        ViewHolder(View contenView) {
            this.mText = (TextView) contenView.findViewById(R.id.text);
        }
    }
}
