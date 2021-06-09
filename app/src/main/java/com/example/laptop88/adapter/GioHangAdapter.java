package com.example.laptop88.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laptop88.Interface.IOnLongClick;
import com.example.laptop88.R;
import com.example.laptop88.activity.MainActivity;
import com.example.laptop88.model.GioHang;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.ViewHolder> {
    Context context;
    ArrayList<GioHang> listGioHang;
    IOnLongClick iOnLongClick;

    public GioHangAdapter(Context context, ArrayList<GioHang> listGioHang) {
        this.context = context;
        this.listGioHang = listGioHang;
    }

    public void SetIonLongClick(IOnLongClick iOnLongClick) {
        this.iOnLongClick = iOnLongClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.donggiohang, null);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        GioHang gioHang = listGioHang.get(position);
        holder.tvTenSanPham.setText(gioHang.getTenSanPham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.tvGia.setText("Giá: " + decimalFormat.format(gioHang.getGia()) + "đ");
        holder.tvSoLuong.setText(String.valueOf(gioHang.getSoLuong()));
        Picasso.with(context).load(gioHang.getHinhSanPham()).into(holder.imgSanPham);
        //
        if (MainActivity.listGioHang.get(position).getSoLuong() <= 1) {
            holder.btnTru.setVisibility(View.INVISIBLE);
        } else {
            holder.btnTru.setVisibility(View.VISIBLE);
        }
        holder.btnCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soLuongMoi = Integer.parseInt(holder.tvSoLuong.getText().toString()) + 1;
                int soLuongHienTai = MainActivity.listGioHang.get(position).getSoLuong();
                long giaHienTai = MainActivity.listGioHang.get(position).getGia();
                long giaMoi = (giaHienTai / soLuongHienTai) * soLuongMoi;
                MainActivity.listGioHang.get(position).setSoLuong(soLuongMoi);
                MainActivity.listGioHang.get(position).setGia(giaMoi);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                holder.tvGia.setText("Giá: " + decimalFormat.format(giaMoi) + " đ");
                holder.tvSoLuong.setText(String.valueOf(soLuongMoi));
                if (soLuongMoi > 1) {
                    holder.btnTru.setVisibility(View.VISIBLE);
                } else {
                    holder.btnTru.setVisibility(View.INVISIBLE);
                }
                com.example.laptop88.activity.GioHang.SetTextViewTongTienGiaTri();
                com.example.laptop88.activity.GioHang.ClearGioHangAndSaveWithIDAccount(context);

            }
        });
        holder.btnTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soLuongMoi = Integer.parseInt(holder.tvSoLuong.getText().toString()) - 1;
                int soLuongHienTai = MainActivity.listGioHang.get(position).getSoLuong();
                long giaHienTai = MainActivity.listGioHang.get(position).getGia();
                long giaMoi = (giaHienTai / soLuongHienTai) * soLuongMoi;
                MainActivity.listGioHang.get(position).setSoLuong(soLuongMoi);
                MainActivity.listGioHang.get(position).setGia(giaMoi);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                holder.tvGia.setText("Giá: " + decimalFormat.format(giaMoi) + "đ");
                holder.tvSoLuong.setText(String.valueOf(soLuongMoi));
                if (soLuongMoi <= 1) {
                    holder.btnTru.setVisibility(View.INVISIBLE);
                } else {
                    holder.btnTru.setVisibility(View.VISIBLE);
                }
                com.example.laptop88.activity.GioHang.SetTextViewTongTienGiaTri();
                com.example.laptop88.activity.GioHang.ClearGioHangAndSaveWithIDAccount(context);
            }
        });
        //long click
        holder.imgSanPham.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                iOnLongClick.OnLongClick(position);
                return true;
            }
        });
        holder.tvTenSanPham.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                iOnLongClick.OnLongClick(position);
                return true;
            }
        });
        holder.tvGia.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                iOnLongClick.OnLongClick(position);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return listGioHang.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTenSanPham, tvGia, tvSoLuong;
        public ImageView imgSanPham;
        public Button btnCong, btnTru;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenSanPham = itemView.findViewById(R.id.tvTenSanPham);
            tvGia = itemView.findViewById(R.id.tvGia);
            tvSoLuong = itemView.findViewById(R.id.tvSoLuong);
            imgSanPham = itemView.findViewById(R.id.imgSanPham);
            btnCong = itemView.findViewById(R.id.btnCong);
            btnTru = itemView.findViewById(R.id.btnTru);
        }
    }
}
