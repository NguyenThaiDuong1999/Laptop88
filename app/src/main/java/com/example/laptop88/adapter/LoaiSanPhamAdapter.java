package com.example.laptop88.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.laptop88.R;
import com.example.laptop88.model.LoaiSanPham;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LoaiSanPhamAdapter extends BaseAdapter {
    ArrayList<LoaiSanPham> listLoaiSP;
    Context context;

    public LoaiSanPhamAdapter(ArrayList<LoaiSanPham> listLoaiSP, Context context) {
        this.listLoaiSP = listLoaiSP;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listLoaiSP.size();
    }

    @Override
    public Object getItem(int position) {
        return listLoaiSP.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
        TextView tvTenLoaiSP;
        ImageView imgLoaiSP;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.donglistview, null);
            viewHolder.tvTenLoaiSP = convertView.findViewById(R.id.tvTenLoaiSP);
            viewHolder.imgLoaiSP = convertView.findViewById(R.id.imgLoaiSP);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        LoaiSanPham loaiSanPham = (LoaiSanPham) getItem(position);
        viewHolder.tvTenLoaiSP.setText(loaiSanPham.getTenLoaiSP());
        Picasso.with(context).load(loaiSanPham.getHinhLoaiSP()).into(viewHolder.imgLoaiSP);
        return convertView;
    }
}
