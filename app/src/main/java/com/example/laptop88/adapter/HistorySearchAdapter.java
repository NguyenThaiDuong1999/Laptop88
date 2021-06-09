package com.example.laptop88.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laptop88.R;
import com.example.laptop88.model.HistorySearch;

import java.util.ArrayList;

public class HistorySearchAdapter extends BaseAdapter {
    ArrayList<HistorySearch> listHistorySearch;
    Context context;

    public HistorySearchAdapter(ArrayList<HistorySearch> listHistorySearch, Context context) {
        this.listHistorySearch = listHistorySearch;
        this.context = context;
    }


    @Override
    public int getCount() {
        return listHistorySearch.size();
    }

    @Override
    public Object getItem(int position) {
        return listHistorySearch.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
        TextView tvSearchContent;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new HistorySearchAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.donghistorysearch, null);
            viewHolder.tvSearchContent = convertView.findViewById(R.id.tvSearchContent);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (HistorySearchAdapter.ViewHolder) convertView.getTag();
        }
        HistorySearch historySearch = (HistorySearch) getItem(position);
        viewHolder.tvSearchContent.setText(historySearch.getSearchContent());
        return convertView;
    }
}
