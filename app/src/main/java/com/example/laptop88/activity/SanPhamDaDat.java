package com.example.laptop88.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.laptop88.Interface.IOnClickSanPham;
import com.example.laptop88.R;
import com.example.laptop88.adapter.SanPhamDaDatAdapter;
import com.example.laptop88.databinding.ActivitySanPhamDaDatBinding;
import com.example.laptop88.model.SanPham;
import com.example.laptop88.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SanPhamDaDat extends AppCompatActivity {
    ActivitySanPhamDaDatBinding binding;
    SanPhamDaDatAdapter sanPhamDaDatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_san_pham_da_dat);
        //SetDefaultLayout();
        HienThiRecyclerView();
        MainActivity.listDaDat.clear();
        GetAllSPDaDatWithIDAccount();
        Toolbar();
        CatchOnItemListSPDaDat();
    }

    private void SetDefaultLayout() {
        if(MainActivity.listDaDat.size() <= 0){
            binding.tvEmpty.setVisibility(View.VISIBLE);
        }else{
            binding.tvEmpty.setVisibility(View.GONE);
        }
    }

    private void GetAllSPDaDatWithIDAccount() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.urlGetAllSPDaDatWithIDAccount, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int idSanPham = 0;
                String tenSanPham = "";
                String hinhSanPham = "";
                int gia = 0;
                String moTa = "";
                String chuyenDung = "";
                int idLoaiSanPham = 0;
                int baoHanh = 0;
                String diaChi = "";
                String ngayDatHang = "";
                String ngayNhanHang = "";
                int idChiTietDonHang = 0;
                String xacNhan = "";
                String daNhan = "";
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        idSanPham = jsonObject.getInt("idSanPham");
                        tenSanPham = jsonObject.getString("tenSanPham");
                        hinhSanPham = jsonObject.getString("hinhSanPham");
                        gia = jsonObject.getInt("gia");
                        moTa = jsonObject.getString("moTa");
                        chuyenDung = jsonObject.getString("chuyenDung");
                        idLoaiSanPham = jsonObject.getInt("idLoaiSanPham");
                        baoHanh = jsonObject.getInt("baoHanh");
                        diaChi = jsonObject.getString("diaChi");
                        ngayDatHang = jsonObject.getString("ngayDatHang");
                        ngayNhanHang = jsonObject.getString("ngayNhanHang");
                        idChiTietDonHang = jsonObject.getInt("idChiTietDonHang");
                        xacNhan = jsonObject.getString("xacNhan");
                        daNhan = jsonObject.getString("daNhan");
                        MainActivity.listDaDat.add(new SanPham(idSanPham, tenSanPham, gia, hinhSanPham, moTa, chuyenDung, idLoaiSanPham, baoHanh, diaChi, ngayDatHang, ngayNhanHang, idChiTietDonHang, xacNhan, daNhan));
                        sanPhamDaDatAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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
                param.put("idAccount", String.valueOf(DangNhap.currentAccount.getIdAccount()));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void HienThiRecyclerView() {
        sanPhamDaDatAdapter = new SanPhamDaDatAdapter(getApplicationContext(), MainActivity.listDaDat);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1, RecyclerView.VERTICAL, false));
        binding.recyclerView.setAdapter(sanPhamDaDatAdapter);
    }

    private void Toolbar() {
        setSupportActionBar(binding.toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void CatchOnItemListSPDaDat() {
        sanPhamDaDatAdapter.SetOnClickSanPham(new IOnClickSanPham() {
            @Override
            public void OnClickSanPham(SanPham sanPham) {
                Intent intent = new Intent(SanPhamDaDat.this, ChiTietSanPham.class);
                intent.putExtra("chiTietSanPham", sanPham);
                startActivity(intent);
            }
        });
    }
}
