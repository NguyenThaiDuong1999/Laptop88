package com.example.laptop88.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laptop88.Interface.IOnClickSanPham;
import com.example.laptop88.R;
import com.example.laptop88.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class LaptopMoiAdapter extends RecyclerView.Adapter<LaptopMoiAdapter.ViewHolder> {
    Context context;
    ArrayList<SanPham> listSanPham;
    IOnClickSanPham iOnClickSanPham;

    public LaptopMoiAdapter(Context context, ArrayList<SanPham> listSanPham) {
        this.context = context;
        this.listSanPham = listSanPham;
    }

    public void SetOnClickSanPham(IOnClickSanPham iOnClickSanPham){
        this.iOnClickSanPham = iOnClickSanPham;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.donglaptopmoi, null);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final SanPham sanPham = listSanPham.get(position);
        holder.tvTenSanPham.setMaxLines(2);
        holder.tvTenSanPham.setEllipsize(TextUtils.TruncateAt.END);
        holder.tvTenSanPham.setText(sanPham.getTenSanPham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.tvGia.setText(decimalFormat.format(sanPham.getGia()) + "đ");
        holder.tvMoTa.setMaxLines(4);
        holder.tvMoTa.setEllipsize(TextUtils.TruncateAt.END);
        holder.tvMoTa.setText(sanPham.getMoTa());
        Picasso.with(context).load(sanPham.getHinhSanPham()).into(holder.imgSanPham);
        //onClick
        holder.tvTenSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iOnClickSanPham.OnClickSanPham(sanPham);
            }
        });
        holder.imgSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iOnClickSanPham.OnClickSanPham(sanPham);
            }
        });
        holder.tvGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iOnClickSanPham.OnClickSanPham(sanPham);
            }
        });
        holder.tvMoTa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iOnClickSanPham.OnClickSanPham(sanPham);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listSanPham.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgSanPham;
        public TextView tvTenSanPham, tvGia, tvMoTa;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSanPham = itemView.findViewById(R.id.imgSanPham);
            tvTenSanPham = itemView.findViewById(R.id.tvTenSanPham);
            tvGia = itemView.findViewById(R.id.tvGia);
            tvMoTa = itemView.findViewById(R.id.tvMoTa);
        }
    }
}
