package com.example.laptop88.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.laptop88.R;
import com.example.laptop88.model.Account;
import com.example.laptop88.model.ChiTietDonHang;
import com.example.laptop88.ultil.Server;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DonHangAdminAdapter extends RecyclerView.Adapter<DonHangAdminAdapter.ViewHolder> {
    Context context;
    ArrayList<ChiTietDonHang> listChiTietDonHang;
    String[] strings;

    public DonHangAdminAdapter(Context context, ArrayList<ChiTietDonHang> listChiTietDonHang) {
        this.context = context;
        this.listChiTietDonHang = listChiTietDonHang;
    }

    @NonNull
    @Override
    public DonHangAdminAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dongdonhangadmin, null);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final DonHangAdminAdapter.ViewHolder holder, int position) {
        final ChiTietDonHang chiTietDonHang = listChiTietDonHang.get(position);
        if (chiTietDonHang.getXacNhan().equals("1")) {
            holder.btnXacNhanDonHang.setBackgroundResource(R.drawable.oval5dp);
            holder.btnXacNhanDonHang.setEnabled(false);
            holder.btnXacNhanDonHang.setText("Đã xác nhận");
        } else {
            holder.btnXacNhanDonHang.setBackgroundResource(R.drawable.ovalred5dp);
            holder.btnXacNhanDonHang.setEnabled(true);
            holder.btnXacNhanDonHang.setText("Xác nhận đơn hàng");
        }
        holder.tvIDDonHang.setText("Đơn hàng: " + chiTietDonHang.getIdDonHang());
        holder.tvTenSanPham.setText(chiTietDonHang.getTenSanPham());
        holder.tvSoLuongTong.setText("Số lượng: " + chiTietDonHang.getSoLuong());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.tvGiaTong.setText("Tổng giá: " + decimalFormat.format(chiTietDonHang.getGiaTong()) + "đ");
        holder.tvBaoHanh.setText("Bảo hành: " + chiTietDonHang.getBaoHanh() + " tháng");
        holder.tvHoTen.setText("-Khách hàng: " + chiTietDonHang.getHoTen());
        holder.tvSDT.setText("-Phone: " + chiTietDonHang.getSoDienThoai());
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(chiTietDonHang.getNgayDatHang());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String ngayDatHang = simpleDateFormat.format(date);
            holder.tvNgayDatHang.setText("-Ngày đặt hàng: " + ngayDatHang);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //Get All Staff Account
        final ArrayList<Account> listStaffAccount = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.urlGetStaffAccount, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                int idAccount = 0;
                String usernameGet = "";
                String passwordGet = "";
                String hoTen = "";
                String soDienThoai = "";
                String email = "";
                String diaChi = "";
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        idAccount = jsonObject.getInt("idAccount");
                        usernameGet = jsonObject.getString("username");
                        passwordGet = jsonObject.getString("password");
                        hoTen = jsonObject.getString("hoTen");
                        soDienThoai = jsonObject.getString("soDienThoai");
                        email = jsonObject.getString("email");
                        diaChi = jsonObject.getString("diaChi");
                        listStaffAccount.add(new Account(idAccount, usernameGet, passwordGet, hoTen, soDienThoai, email, diaChi));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                strings = new String[listStaffAccount.size()];
                for (int j = 0; j < listStaffAccount.size(); j++) {
                    strings[j] = listStaffAccount.get(j).getUsername();
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, R.layout.support_simple_spinner_dropdown_item, strings);
                holder.spinner.setAdapter(arrayAdapter);
                //
                for (int i = 0; i < listStaffAccount.size(); i++) {
                    if(chiTietDonHang.getShipper() == listStaffAccount.get(i).getIdAccount()){
                        holder.spinner.setSelection(i);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
        //
        if (chiTietDonHang.getNgayNhanHang().equals("0000-00-00 00:00:00")) {
            holder.tvNgayNhanHangAdmin.setText("");
        } else {
            try {
                Date date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(chiTietDonHang.getNgayNhanHang());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String ngayNhanHang = simpleDateFormat.format(date);
                holder.tvNgayNhanHangAdmin.setText("Đã nhận hàng vào " + ngayNhanHang);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        Picasso.with(context).load(chiTietDonHang.getHinhSanPham()).into(holder.imgSanPham);

        holder.btnXacNhanDonHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue requestQueue = Volley.newRequestQueue(context);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.urlUpdateXacNhanChiTietDonHang, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            holder.btnXacNhanDonHang.setBackgroundResource(R.drawable.oval5dp);
                            holder.btnXacNhanDonHang.setEnabled(false);
                            holder.btnXacNhanDonHang.setText("Đã xác nhận");
                        }
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
                        int shipper = 0;
                        for (int i = 0; i < listStaffAccount.size(); i++) {
                            if (holder.spinner.getSelectedItem().toString().equals(listStaffAccount.get(i).getUsername())) {
                                shipper = listStaffAccount.get(i).getIdAccount();
                            }
                        }
                        param.put("shipper", String.valueOf(shipper));
                        return param;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });

        /*holder.btnXacNhanDaNhanHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add to sanphamdamua db
                final RequestQueue requestQueue = Volley.newRequestQueue(context);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.urlSPDaMua, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //update daNhan = 1: true
                        RequestQueue requestQueue1 = Volley.newRequestQueue(context);
                        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, Server.urlUpdateDaNhanChiTietDonHang, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response != null) {
                                    holder.btnXacNhanDaNhanHang.setBackgroundResource(R.drawable.oval5dp);
                                    holder.btnXacNhanDaNhanHang.setEnabled(false);
                                    holder.btnXacNhanDaNhanHang.setText("Đã nhận hàng");
                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                    String ngayNhanHang = simpleDateFormat.format(Calendar.getInstance().getTime());
                                    holder.tvNgayNhanHang.setText(ngayNhanHang);
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
                                }){
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
                        param.put("idSanPham", String.valueOf(chiTietDonHang.getIdSanPham()));
                        param.put("idAccount", String.valueOf(chiTietDonHang.getIdAccount()));
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
        return listChiTietDonHang.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgSanPham;
        TextView tvIDDonHang, tvTenSanPham, tvSoLuongTong, tvGiaTong, tvBaoHanh, tvHoTen, tvSDT, tvNgayDatHang, tvNgayNhanHangAdmin;
        Button btnXacNhanDonHang;
        Spinner spinner;
        //Button btnXacNhanDaNhanHang;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIDDonHang = itemView.findViewById(R.id.tvIDDonHang);
            imgSanPham = itemView.findViewById(R.id.imgSanPham);
            tvTenSanPham = itemView.findViewById(R.id.tvTenSanPham);
            tvSoLuongTong = itemView.findViewById(R.id.tvSoLuongTong);
            tvGiaTong = itemView.findViewById(R.id.tvGiaTong);
            tvBaoHanh = itemView.findViewById(R.id.tvBaoHanh);
            tvHoTen = itemView.findViewById(R.id.tvHoTen);
            tvSDT = itemView.findViewById(R.id.tvSDT);
            tvNgayDatHang = itemView.findViewById(R.id.tvNgayDatHang);
            tvNgayNhanHangAdmin = itemView.findViewById(R.id.tvNgayNhanHangAdmin);
            btnXacNhanDonHang = itemView.findViewById(R.id.btnXacNhanDonHang);
            //btnXacNhanDaNhanHang = itemView.findViewById(R.id.btnXacNhanDaNhanHang);
            spinner = itemView.findViewById(R.id.spinnerAdmin);
        }
    }
}
