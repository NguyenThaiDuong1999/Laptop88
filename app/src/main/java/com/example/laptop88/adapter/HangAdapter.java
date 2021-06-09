package com.example.laptop88.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laptop88.Interface.IOnClickHang;
import com.example.laptop88.R;
import com.example.laptop88.model.Hang;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class HangAdapter extends RecyclerView.Adapter<HangAdapter.ViewHolder> {
    ArrayList<Hang> listHang;
    Context context;
    IOnClickHang iOnClickHang;

    public HangAdapter(ArrayList<Hang> listHang, Context context) {
        this.listHang = listHang;
        this.context = context;
    }

    public void SetOnClickHang(IOnClickHang iOnClickHang){
        this.iOnClickHang = iOnClickHang;
    }

    @NonNull
    @Override
    public HangAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.donghang, null);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HangAdapter.ViewHolder holder, int position) {
        final Hang hang = listHang.get(position);
        Picasso.with(context).load(hang.getHinhHang()).into(holder.imgHang);
        holder.tvTenHang.setText(hang.getTenHang());
        holder.imgHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iOnClickHang.OnClickHang(hang);
            }
        });
        holder.tvTenHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iOnClickHang.OnClickHang(hang);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listHang.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imgHang;
        TextView tvTenHang;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgHang = itemView.findViewById(R.id.imgHang);
            tvTenHang = itemView.findViewById(R.id.tvTenHang);
        }
    }
}
