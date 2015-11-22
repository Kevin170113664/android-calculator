package com.tw.lhli.calculator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CalculatorListAdapter extends BaseAdapter {

    private List<String> data;

    public CalculatorListAdapter(List<String> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public String getItem(int position) {
        return data == null ? null : data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.calculate_result_item, parent, false);
            holder.calculateText = (TextView) convertView.findViewById(R.id.calculate_text);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.calculateText.setText(getItem(position));

        return convertView;
    }

    class ViewHolder {
        public TextView calculateText;
    }
}
