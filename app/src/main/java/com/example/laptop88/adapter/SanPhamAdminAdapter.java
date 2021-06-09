package com.example.laptop88.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.laptop88.Fragment.SuaSanPham;
import com.example.laptop88.Fragment.ThanhVien;
import com.example.laptop88.Interface.IOnClickSanPham;
import com.example.laptop88.R;
import com.example.laptop88.model.SanPham;
import com.example.laptop88.ultil.CheckConnection;
import com.example.laptop88.ultil.Server;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class SanPhamAdminAdapter extends RecyclerView.Adapter<SanPhamAdminAdapter.ViewHolder> {
    ArrayList<SanPham> listSanPham;
    Context context;
    IOnClickSanPham iOnClickSanPham;

    public SanPhamAdminAdapter(ArrayList<com.example.laptop88.model.SanPham> listSanPham, Context context) {
        this.listSanPham = listSanPham;
        this.context = context;
    }

    public void SetIonClickSanPham(IOnClickSanPham iOnClickSanPham) {
        this.iOnClickSanPham = iOnClickSanPham;
    }

    @NonNull
    @Override
    public SanPhamAdminAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dongsanphamadmin, null);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamAdminAdapter.ViewHolder holder, final int position) {
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
        holder.btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(String.valueOf(sanPham.getTenSanPham()));
                builder.setMessage("Bạn có chắc muốn xóa sản phẩm này?");
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        com.example.laptop88.Fragment.SanPham.listSanPham.remove(position);
                        com.example.laptop88.Fragment.SanPham.sanPhamAdminAdapter.notifyDataSetChanged();
                        com.example.laptop88.Fragment.SanPham.edtTimKiem.setHint("Tìm kiếm trong " + com.example.laptop88.Fragment.SanPham.listSanPham.size() + " sản phẩm...");
                        //Delete in db
                        RequestQueue requestQueue = Volley.newRequestQueue(context);
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.urlDeleteSanPhamWithIDSanPham, new Response.Listener<String>() {
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
                                param.put("idSanPham", String.valueOf(sanPham.getIdSanPham()));
                                return param;
                            }
                        };
                        requestQueue.add(stringRequest);
                        //Clear Cart With IDSanPham
                        RequestQueue requestQueue1 = Volley.newRequestQueue(context);
                        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, Server.getUrlClearGioHangWithIDSanPham, new Response.Listener<String>() {
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
                                param.put("idSanPham", String.valueOf(sanPham.getIdSanPham()));
                                return param;
                            }
                        };
                        requestQueue1.add(stringRequest1);
                        CheckConnection.ShowToast_Short(context, "Xóa thành công!");
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CheckConnection.ShowToast_Short(context, "Hủy xóa!");
                    }
                });
                builder.show();
            }
        });
        holder.btnSua.setOnClickListener(new View.OnClickListener() {
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
        ImageView imgSanPham;
        TextView tvTenSanPham, tvGia, tvMoTa;
        ImageView btnXoa, btnSua;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSanPham = itemView.findViewById(R.id.imgSanPham);
            tvTenSanPham = itemView.findViewById(R.id.tvTenSanPham);
            tvGia = itemView.findViewById(R.id.tvGia);
            tvMoTa = itemView.findViewById(R.id.tvMoTa);
            btnXoa = itemView.findViewById(R.id.btnXoa);
            btnSua = itemView.findViewById(R.id.btnSua);
        }
    }
}
