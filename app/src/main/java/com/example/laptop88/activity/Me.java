package com.example.laptop88.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.laptop88.R;
import com.example.laptop88.databinding.ActivityMeBinding;
import com.example.laptop88.model.SanPham;
import com.example.laptop88.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Me extends AppCompatActivity {
    ActivityMeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_me);
        Toolbar();
        SetDefaultLayout();
        CatchOnMeInfor();
        CatchOnSPDaXem();
        CatchOnSPDaDat();
        CatchOnSPDaMua();
        CatchOnSPYeuThich();
        CatchOnHoTro();
    }

    private void CatchOnHoTro() {
        binding.HoTro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Me.this, BaoHanhHauMai.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        MainActivity.listDaMua.clear();
        GetAllSPDaMuaWithIDAccount();
        super.onStart();
    }

    private void GetAllSPDaMuaWithIDAccount() {
        if (DangNhap.currentAccount != null) {
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.urlGetAllSPDaMuaWithIDAccount, new Response.Listener<String>() {
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
                    String ngayNhanHang = "";
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
                            ngayNhanHang = jsonObject.getString("ngayNhanHang");
                            MainActivity.listDaMua.add(new SanPham(idSanPham, tenSanPham, gia, hinhSanPham, moTa, chuyenDung, idLoaiSanPham, baoHanh, ngayNhanHang));
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
    }

    private void CatchOnSPYeuThich() {
        binding.SPYeuThich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Me.this, SanPhamYeuThich.class);
                startActivity(intent);
            }
        });
    }

    private void CatchOnSPDaMua() {
        binding.SPDaMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Me.this, SanPhamDaMua.class);
                startActivity(intent);
            }
        });
    }

    private void CatchOnSPDaDat() {
        binding.SPDaDat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Me.this, SanPhamDaDat.class);
                startActivity(intent);
            }
        });
    }

    private void CatchOnSPDaXem() {
        binding.SPDaXem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Me.this, SanPhamDaXem.class);
                startActivity(intent);
            }
        });
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

    private void CatchOnMeInfor() {
        binding.rlSuaThongTinCaNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DangNhap.currentAccount != null) {
                    Intent intent = new Intent(Me.this, ThongTinCaNhan.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void SetDefaultLayout() {
        if (DangNhap.currentAccount != null) {
            binding.tvAccount.setText(DangNhap.currentAccount.getUsername());
            binding.tvSDT.setText(DangNhap.currentAccount.getSoDienThoai());
            try {
                Date date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(DangNhap.currentAccount.getNgayDangKy());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String ngayDangKy = simpleDateFormat.format(date);
                binding.tvNgayDangKy.setText("Thành viên từ " + ngayDangKy);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
