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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.laptop88.R;
import com.example.laptop88.model.ChiTietDonHang;
import com.example.laptop88.ultil.Server;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DonHangStaffAdapter extends RecyclerView.Adapter<DonHangStaffAdapter.ViewHolder> {
    Context context;
    ArrayList<ChiTietDonHang> listChiTietDonHang;

    public DonHangStaffAdapter(Context context, ArrayList<ChiTietDonHang> listChiTietDonHang) {
        this.context = context;
        this.listChiTietDonHang = listChiTietDonHang;
    }

    @NonNull
    @Override
    public DonHangStaffAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dongdonhangstaff, null);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final DonHangStaffAdapter.ViewHolder holder, int position) {
        final ChiTietDonHang chiTietDonHang = listChiTietDonHang.get(position);
        if (chiTietDonHang.getDaNhan().equals("0") && chiTietDonHang.getXacNhan().equals("1")) {
            holder.btnDaGiaoHang.setBackgroundResource(R.drawable.ovalred5dp);
            holder.btnDaGiaoHang.setEnabled(true);
            holder.btnDaGiaoHang.setText("Đã giao hàng");
        } else if(chiTietDonHang.getDaNhan().equals("0") && chiTietDonHang.getXacNhan().equals("0")){
            holder.btnDaGiaoHang.setBackgroundResource(R.drawable.oval5dp);
            holder.btnDaGiaoHang.setEnabled(false);
            holder.btnDaGiaoHang.setText("Chờ cửa hàng xác nhận");
        }else if(chiTietDonHang.getDaNhan().equals("1") && chiTietDonHang.getXacNhan().equals("1")){
            holder.btnDaGiaoHang.setBackgroundResource(R.drawable.oval5dp);
            holder.btnDaGiaoHang.setEnabled(false);
            holder.btnDaGiaoHang.setText("Đã giao hàng");
        }else{

        }
        holder.tvIDDonHang.setText("Đơn hàng: " + chiTietDonHang.getIdDonHang());
        holder.tvTenSanPham.setText(chiTietDonHang.getTenSanPham());
        holder.tvSoLuongTong.setText("Số lượng: " + chiTietDonHang.getSoLuong());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.tvGiaTong.setText("Tổng giá: " + decimalFormat.format(chiTietDonHang.getGiaTong()) + "đ");
        holder.tvHoTen.setText("-Khách hàng: " + chiTietDonHang.getHoTen());
        holder.tvSDT.setText("-Phone: " + chiTietDonHang.getSoDienThoai());
        holder.tvEmail.setText("-Email: " + chiTietDonHang.getEmail());
        holder.tvDiaChi.setText("-Địa chỉ nhận: " + chiTietDonHang.getDiaChi());
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(chiTietDonHang.getNgayDatHang());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String ngayDatHang = simpleDateFormat.format(date);
            holder.tvNgayDatHang.setText("-Ngày đặt hàng: " + ngayDatHang);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(chiTietDonHang.getNgayNhanHang().equals("0000-00-00 00:00:00")){
            holder.tvNgayXacNhanNhanHang.setText("");
        }else{
            try {
                Date date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(chiTietDonHang.getNgayNhanHang());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String ngayNhanHang = simpleDateFormat.format(date);
                holder.tvNgayXacNhanNhanHang.setText(ngayNhanHang);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        Picasso.with(context).load(chiTietDonHang.getHinhSanPham()).into(holder.imgSanPham);
        //
        holder.btnDaGiaoHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add to sanphamdamua db
                /*final RequestQueue requestQueue = Volley.newRequestQueue(context);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.urlSPDaMua, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {*/
                        //update daNhan = 1: true
                        RequestQueue requestQueue1 = Volley.newRequestQueue(context);
                        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, Server.urlUpdateDaNhanChiTietDonHang, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response != null) {
                                    holder.btnDaGiaoHang.setBackgroundResource(R.drawable.oval5dp);
                                    holder.btnDaGiaoHang.setEnabled(false);
                                    holder.btnDaGiaoHang.setText("Đã giao hàng");
                                    holder.tvNgayXacNhanNhanHang.setVisibility(View.VISIBLE);
                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                    String ngayNhanHang = simpleDateFormat.format(Calendar.getInstance().getTime());
                                    holder.tvNgayXacNhanNhanHang.setText(ngayNhanHang);
                                }
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
                                }) {
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        HashMap<String, String> param = new HashMap<>();
                                        param.put("idChiTietDonHang", String.valueOf(chiTietDonHang.getIdChiTietDonHang()));
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
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                HashMap<String, String> param = new HashMap<>();
                                param.put("idChiTietDonHang", String.valueOf(chiTietDonHang.getIdChiTietDonHang()));
                                return param;
                            }
                        };
                        requestQueue1.add(stringRequest1);
                    /*}
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> param = new HashMap<>();
                        param.put("idChiTietDonHang", String.valueOf(chiTietDonHang.getIdChiTietDonHang()));
                        param.put("idSanPham", String.valueOf(chiTietDonHang.getIdSanPham()));
                        param.put("idAccount", String.valueOf(chiTietDonHang.getIdAccount()));
                        *//*SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        String ngayNhanHang = simpleDateFormat.format(Calendar.getInstance().getTime());
                        param.put("ngayNhanHang", ngayNhanHang);*//*
                        return param;
                    }
                };
                requestQueue.add(stringRequest);*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return listChiTietDonHang.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgSanPham;
        TextView tvIDDonHang, tvTenSanPham, tvSoLuongTong, tvGiaTong, tvHoTen, tvSDT, tvEmail, tvDiaChi, tvNgayDatHang, tvNgayXacNhanNhanHang;
        Button btnDaGiaoHang;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIDDonHang = itemView.findViewById(R.id.tvIDDonHang);
            imgSanPham = itemView.findViewById(R.id.imgSanPham);
            tvTenSanPham = itemView.findViewById(R.id.tvTenSanPham);
            tvSoLuongTong = itemView.findViewById(R.id.tvSoLuongTong);
            tvGiaTong = itemView.findViewById(R.id.tvGiaTong);
            tvHoTen = itemView.findViewById(R.id.tvHoTen);
            tvSDT = itemView.findViewById(R.id.tvSDT);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvDiaChi = itemView.findViewById(R.id.tvDiaChi);
            tvNgayDatHang = itemView.findViewById(R.id.tvNgayDatHang);
            tvNgayXacNhanNhanHang = itemView.findViewById(R.id.tvNgayXacNhanNhanHang);
            btnDaGiaoHang = itemView.findViewById(R.id.btnDaGiaoHang);
        }
    }
}
