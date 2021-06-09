package com.example.laptop88.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.laptop88.Interface.IOnClickSanPham;
import com.example.laptop88.R;
import com.example.laptop88.adapter.LaptopMoiAdapter;
import com.example.laptop88.databinding.ActivityHangBinding;
import com.example.laptop88.model.SanPham;
import com.example.laptop88.ultil.CheckConnection;
import com.example.laptop88.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Hang extends AppCompatActivity {
    ActivityHangBinding binding;
    Intent intent;
    com.example.laptop88.model.Hang hang = null;
    ArrayList<SanPham> listSanPham;
    LaptopMoiAdapter laptopMoiAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_hang);
        ActionBar();
        GetHangByClicking();
        SetDefaultLayout();
        HienThiRecyclerView();
        GetDuLieuSanPhamWithTenHang();
        CatchOnItemListSanPham();
        LocTheoPrice();
    }

    private void LocTheoPrice() {
        binding.btnGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getApplicationContext(), binding.btnGia);
                popupMenu.getMenuInflater().inflate(R.menu.menu_popup_gia_laptop, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menuAll:
                                HienThiRecyclerView();
                                GetDuLieuSanPhamWithTenHang();
                                break;
                            case R.id.menu_duoi_5_trieu:
                                HienThiRecyclerView();
                                GetLaptopPriceFilter(0, 4999999);
                                break;
                            case R.id.menu_tu_5_den_10_trieu:
                                HienThiRecyclerView();
                                GetLaptopPriceFilter(5000000, 10000000);
                                break;
                            case R.id.menu_tu_10_trieu_den_20_trieu:
                                HienThiRecyclerView();
                                GetLaptopPriceFilter(10000000, 20000000);
                                break;
                            case R.id.menu_tren_20_trieu:
                                HienThiRecyclerView();
                                GetLaptopPriceFilter(20000000, 2000000000);
                                break;
                        }
                        CatchOnItemListSanPham();
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }

    private void GetLaptopPriceFilter(final int fPrice, final int tPrice) {
        binding.progressBar.setVisibility(View.VISIBLE);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.urlGetSanPhamWithTenHang, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int idSanPham = 0;
                String tenSanPham = "";
                Integer gia = 0;
                String hinhSanPham = "";
                String moTa = "";
                String chuyenDung = "";
                int idLoaiSanPham = 0;
                int baoHanh = 0;
                if (response != null) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            idSanPham = jsonObject.getInt("idSanPham");
                            tenSanPham = jsonObject.getString("tenSanPham");
                            gia = jsonObject.getInt("gia");
                            hinhSanPham = jsonObject.getString("hinhSanPham");
                            moTa = jsonObject.getString("moTa");
                            chuyenDung = jsonObject.getString("chuyenDung");
                            idLoaiSanPham = jsonObject.getInt("idLoaiSanPham");
                            baoHanh = jsonObject.getInt("baoHanh");
                            if (fPrice <= gia && gia <= tPrice)
                                listSanPham.add(new SanPham(idSanPham, tenSanPham, gia, hinhSanPham, moTa, chuyenDung, idLoaiSanPham, baoHanh));
                            laptopMoiAdapter.notifyDataSetChanged();
                            binding.progressBar.setVisibility(View.GONE);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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
                param.put("tenHang", hang.getTenHang());
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void CatchOnItemListSanPham() {
        laptopMoiAdapter.SetOnClickSanPham(new IOnClickSanPham() {
            @Override
            public void OnClickSanPham(SanPham sanPham) {
                intent = new Intent(Hang.this, ChiTietSanPham.class);
                intent.putExtra("chiTietSanPham", sanPham);
                startActivity(intent);
            }
        });
    }

    private void HienThiRecyclerView() {
        listSanPham = new ArrayList<>();
        laptopMoiAdapter = new LaptopMoiAdapter(getApplicationContext(), listSanPham);
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1, RecyclerView.VERTICAL, false));
        binding.recyclerView.setAdapter(laptopMoiAdapter);
    }

    private void GetDuLieuSanPhamWithTenHang() {
        binding.progressBar.setVisibility(View.VISIBLE);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.urlGetSanPhamWithTenHang, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int idSanPham = 0;
                String tenSanPham = "";
                Integer gia = 0;
                String hinhSanPham = "";
                String moTa = "";
                String chuyenDung = "";
                int idLoaiSanPham = 0;
                int baoHanh = 0;
                if (response != null) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            idSanPham = jsonObject.getInt("idSanPham");
                            tenSanPham = jsonObject.getString("tenSanPham");
                            gia = jsonObject.getInt("gia");
                            hinhSanPham = jsonObject.getString("hinhSanPham");
                            moTa = jsonObject.getString("moTa");
                            chuyenDung = jsonObject.getString("chuyenDung");
                            idLoaiSanPham = jsonObject.getInt("idLoaiSanPham");
                            baoHanh = jsonObject.getInt("baoHanh");
                            listSanPham.add(new SanPham(idSanPham, tenSanPham, gia, hinhSanPham, moTa, chuyenDung, idLoaiSanPham, baoHanh));
                            laptopMoiAdapter.notifyDataSetChanged();
                            binding.progressBar.setVisibility(View.GONE);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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
                param.put("tenHang", hang.getTenHang());
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void GetHangByClicking() {
        intent = getIntent();
        hang = (com.example.laptop88.model.Hang) intent.getSerializableExtra("hang");
    }

    private void SetDefaultLayout() {
        binding.toolBar.setTitle(hang.getTenHang());
    }

    private void ActionBar() {
        setSupportActionBar(binding.toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_top_right, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuSearch:
                Intent intent = new Intent(Hang.this, Search.class);
                startActivity(intent);
                break;
            case R.id.menuHome:
                intent = new Intent(Hang.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.menuCart:
                intent = new Intent(Hang.this, com.example.laptop88.activity.GioHang.class);
                startActivity(intent);
                break;
            case R.id.menuBaoHanhHauMai:
                intent = new Intent(Hang.this, BaoHanhHauMai.class);
                startActivity(intent);
                break;
            case R.id.menuBanHangOnline:
            case R.id.menuChamSocKhachHang:
                intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:02471069999"));
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
