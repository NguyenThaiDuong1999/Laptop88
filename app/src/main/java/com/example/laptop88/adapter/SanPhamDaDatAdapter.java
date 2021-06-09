package com.example.laptop88.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laptop88.Interface.IOnClickSanPham;
import com.example.laptop88.R;
import com.example.laptop88.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class SanPhamDaDatAdapter extends RecyclerView.Adapter<SanPhamDaDatAdapter.ViewHolder> {
    Context context;
    ArrayList<SanPham> listSanPham;
    IOnClickSanPham iOnClickSanPham;

    public SanPhamDaDatAdapter(Context context, ArrayList<SanPham> listSanPham) {
        this.context = context;
        this.listSanPham = listSanPham;
    }

    public void SetOnClickSanPham(IOnClickSanPham iOnClickSanPham) {
        this.iOnClickSanPham = iOnClickSanPham;
    }

    @NonNull
    @Override
    public SanPhamDaDatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dongsanphamdadat, null);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final SanPhamDaDatAdapter.ViewHolder holder, int position) {
        final SanPham sanPham = listSanPham.get(position);
        holder.tvTenSanPham.setMaxLines(2);
        holder.tvTenSanPham.setEllipsize(TextUtils.TruncateAt.END);
        holder.tvTenSanPham.setText(sanPham.getTenSanPham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.tvGia.setText(decimalFormat.format(sanPham.getGia()) + "đ");
        holder.tvMoTa.setMaxLines(2);
        holder.tvMoTa.setEllipsize(TextUtils.TruncateAt.END);
        holder.tvMoTa.setText(sanPham.getMoTa());
        holder.tvBaoHanh.setText("Bảo hành: " + sanPham.getBaoHanh() + " tháng");
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(sanPham.getNgayDatHang());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String ngayDatHang = simpleDateFormat.format(date);
            holder.tvNgayDatHang.setText("Ngày đặt hàng: " + ngayDatHang);
            //Ngày dự kiến nhận hàng
            Calendar calendar = Calendar.getInstance();
            try {
                calendar.setTime(simpleDateFormat.parse(ngayDatHang));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //Get province (city)
            int lastCommaIndex = sanPham.getDiaChi().lastIndexOf(',');
            String diaChi = sanPham.getDiaChi();
            String province = diaChi.substring(lastCommaIndex + 2, diaChi.length());
            int dayNumber = 0;
            if (province.toLowerCase().equals("hà nội")) {
                dayNumber = 3;
            } else {
                dayNumber = 5;
            }
            calendar.add(Calendar.DATE, dayNumber);
            String ngayDuKien = simpleDateFormat.format(calendar.getTime());
            holder.tvNgayDuKien.setText("Dự kiến giao hàng: " + ngayDuKien);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //
        if (sanPham.getNgayNhanHang().equals("0000-00-00 00:00:00")) {
            holder.tvNgayNhanHangDaDat.setText("");
        } else {
            try {
                Date date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(sanPham.getNgayNhanHang());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String ngayNhanHang = simpleDateFormat.format(date);
                holder.tvNgayNhanHangDaDat.setText("Ngày nhận hàng: " + ngayNhanHang);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
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
        holder.tvBaoHanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iOnClickSanPham.OnClickSanPham(sanPham);
            }
        });
        holder.tvNgayDatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iOnClickSanPham.OnClickSanPham(sanPham);
            }
        });
        holder.tvNgayDuKien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iOnClickSanPham.OnClickSanPham(sanPham);
            }
        });
        holder.tvNgayNhanHangDaDat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iOnClickSanPham.OnClickSanPham(sanPham);
            }
        });
        if (sanPham.getDaNhan().equals("1")) {
            holder.btnDaNhanHang.setBackgroundResource(R.drawable.oval5dp);
            holder.btnDaNhanHang.setEnabled(false);
            holder.btnDaNhanHang.setText("Đã nhận được hàng");
        }
        if (sanPham.getXacNhan().equals("0")) {
            holder.btnDaNhanHang.setBackgroundResource(R.drawable.oval5dp);
            holder.btnDaNhanHang.setEnabled(false);
            holder.btnDaNhanHang.setText("Chờ cửa hàng xác nhận");
        }
        if (sanPham.getXacNhan().equals("1") && sanPham.getDaNhan().equals("0")) {
            holder.btnDaNhanHang.setBackgroundResource(R.drawable.oval5dp);
            holder.btnDaNhanHang.setEnabled(false);
            holder.btnDaNhanHang.setText("Chờ giao hàng");
        }
        /*holder.btnDaNhanHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.btnDaNhanHang.setBackgroundResource(R.drawable.oval5dp);
                holder.btnDaNhanHang.setEnabled(false);
                holder.btnDaNhanHang.setText("Đã nhận được hàng");
                //Add to sanphamdamua db
                RequestQueue requestQueue = Volley.newRequestQueue(context);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.urlSPDaMua, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //update daNhan = 1: true
                        RequestQueue requestQueue1 = Volley.newRequestQueue(context);
                        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, Server.urlUpdateDaNhanChiTietDonHang, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //Update ngayNhanHang(chitietdonhang):
                                RequestQueue requestQueue2 = Volley.newRequestQueue(context);
                                StringRequest stringRequest2 = new StringRequest(Request.Method.POST, Server.urlUpdateNgayNhanHangChiTietDonHang, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }){
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        HashMap<String, String> param = new HashMap<>();
                                        param.put("idChiTietDonHang", String.valueOf(sanPham.getIdChiTietDonHang()));
                                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                                        String ngayNhanHang = simpleDateFormat.format(Calendar.getInstance().getTime());
                                        param.put("ngayNhanHang", ngayNhanHang);
                                        return param;
                                    }
                                };
                                requestQueue2.add(stringRequest2);
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                HashMap<String, String> param = new HashMap<>();
                                param.put("idChiTietDonHang", String.valueOf(sanPham.getIdChiTietDonHang()));
                                return param;
                            }
                        };
                        requestQueue1.add(stringRequest1);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> param = new HashMap<>();
                        param.put("idChiTietDonHang", String.valueOf(sanPham.getIdChiTietDonHang()));
                        param.put("idSanPham", String.valueOf(sanPham.getIdSanPham()));
                        param.put("idAccount", String.valueOf(DangNhap.currentAccount.getIdAccount()));
                        *//*SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        String ngayNhanHang = simpleDateFormat.format(Calendar.getInstance().getTime());
                        param.put("ngayNhanHang", ngayNhanHang);*//*
                        return param;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return listSanPham.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgSanPham;
        public TextView tvTenSanPham, tvGia, tvMoTa, tvBaoHanh, tvNgayDatHang, tvNgayDuKien, tvNgayNhanHangDaDat;
        public Button btnDaNhanHang;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSanPham = itemView.findViewById(R.id.imgSanPham);
            tvTenSanPham = itemView.findViewById(R.id.tvTenSanPham);
            tvGia = itemView.findViewById(R.id.tvGia);
            tvMoTa = itemView.findViewById(R.id.tvMoTa);
            tvBaoHanh = itemView.findViewById(R.id.tvBaoHanh);
            tvNgayDatHang = itemView.findViewById(R.id.tvNgayDatHang);
            tvNgayDuKien = itemView.findViewById(R.id.tvNgayDuKien);
            tvNgayNhanHangDaDat = itemView.findViewById(R.id.tvNgayNhanHangDaDat);
            btnDaNhanHang = itemView.findViewById(R.id.btnDaNhanHang);
        }
    }
}
