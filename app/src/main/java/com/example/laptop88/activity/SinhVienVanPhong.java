package com.example.laptop88.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

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
import com.example.laptop88.databinding.ActivitySinhVienVanPhongBinding;
import com.example.laptop88.model.SanPham;
import com.example.laptop88.ultil.CheckConnection;
import com.example.laptop88.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SinhVienVanPhong extends AppCompatActivity {
    ActivitySinhVienVanPhongBinding binding;
    ArrayList<SanPham> listSanPham;
    LaptopMoiAdapter laptopMoiAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sinh_vien_van_phong);
        //Hien thi rv
        HienThiRecyclerView();
        //
        if (CheckConnection.haveNetWorkConnection(getApplicationContext())) {
            ActionBar();
            GetMayTinhSVVP();
            LocTheoBrand();
            LocTheoPrice();
            CatchOnItemListSVVP();
        } else {
            CheckConnection.ShowToast_Short(getApplicationContext(), "Hãy kiểm tra lại kết nối");
            finish();
        }
    }
    private void CatchOnItemListSVVP() {
        laptopMoiAdapter.SetOnClickSanPham(new IOnClickSanPham() {
            @Override
            public void OnClickSanPham(SanPham sanPham) {
                Intent intent = new Intent(SinhVienVanPhong.this, ChiTietSanPham.class);
                intent.putExtra("chiTietSanPham", sanPham);
                startActivity(intent);
            }
        });
    }

    private void HienThiRecyclerView() {
        listSanPham = new ArrayList<>();
        laptopMoiAdapter = new LaptopMoiAdapter(getApplicationContext(), listSanPham);
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        binding.recyclerView.setAdapter(laptopMoiAdapter);
    }

    private void LocTheoBrand() {
        binding.btnBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getApplicationContext(), binding.btnBrand);
                popupMenu.getMenuInflater().inflate(R.menu.menu_popup_brand_laptop, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menuAll:
                                HienThiRecyclerView();
                                GetMayTinhSVVP();
                                CatchOnItemListSVVP();
                                binding.tvThuongHieu.setText(item.getTitle());
                                break;
                            case R.id.menuDell:
                                HienThiRecyclerView();
                                GetMayTinhMoiBrandFilter("Dell");
                                CatchOnItemListSVVP();
                                binding.tvThuongHieu.setText(item.getTitle());
                                break;
                            case R.id.menuHP:
                                HienThiRecyclerView();
                                GetMayTinhMoiBrandFilter("HP");
                                CatchOnItemListSVVP();
                                binding.tvThuongHieu.setText(item.getTitle());
                                break;
                            case R.id.menuLG:
                                HienThiRecyclerView();
                                GetMayTinhMoiBrandFilter("LG");
                                CatchOnItemListSVVP();
                                binding.tvThuongHieu.setText(item.getTitle());
                                break;
                            case R.id.menuMacBook:
                                HienThiRecyclerView();
                                GetMayTinhMoiBrandFilter("Mac");
                                CatchOnItemListSVVP();
                                binding.tvThuongHieu.setText(item.getTitle());
                                break;
                            case R.id.menuAsus:
                                HienThiRecyclerView();
                                GetMayTinhMoiBrandFilter("Asus");
                                CatchOnItemListSVVP();
                                binding.tvThuongHieu.setText(item.getTitle());
                                break;
                            case R.id.menuAcer:
                                HienThiRecyclerView();
                                GetMayTinhMoiBrandFilter("Acer");
                                CatchOnItemListSVVP();
                                binding.tvThuongHieu.setText(item.getTitle());
                                break;
                            case R.id.menuLenovo:
                                HienThiRecyclerView();
                                GetMayTinhMoiBrandFilter("Lenovo");
                                CatchOnItemListSVVP();
                                binding.tvThuongHieu.setText(item.getTitle());
                                break;
                            case R.id.menuMSI:
                                HienThiRecyclerView();
                                GetMayTinhMoiBrandFilter("MSI");
                                CatchOnItemListSVVP();
                                binding.tvThuongHieu.setText(item.getTitle());
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
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
                                GetMayTinhSVVP();
                                CatchOnItemListSVVP();
                                break;
                            case R.id.menu_duoi_5_trieu:
                                HienThiRecyclerView();
                                GetMayTinhMoiPriceFilter(0, 4999999);
                                CatchOnItemListSVVP();
                                break;
                            case R.id.menu_tu_5_den_10_trieu:
                                HienThiRecyclerView();
                                GetMayTinhMoiPriceFilter(5000000, 10000000);
                                CatchOnItemListSVVP();
                                break;
                            case R.id.menu_tu_10_trieu_den_20_trieu:
                                HienThiRecyclerView();
                                GetMayTinhMoiPriceFilter(10000000, 20000000);
                                CatchOnItemListSVVP();
                                break;
                            case R.id.menu_tren_20_trieu:
                                HienThiRecyclerView();
                                GetMayTinhMoiPriceFilter(20000000, 2000000000);
                                CatchOnItemListSVVP();
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }

    private void GetMayTinhSVVP() {
        binding.progressBar.setVisibility(View.VISIBLE);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.urlGetAllSanPhamChuyenDung, new Response.Listener<String>() {
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
                param.put("chuyenDung", "Sinh viên văn phòng");
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void GetMayTinhMoiBrandFilter(final String brand) {
        binding.progressBar.setVisibility(View.VISIBLE);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.urlGetAllSanPhamChuyenDung, new Response.Listener<String>() {
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
                            if (tenSanPham.toLowerCase().contains(brand.toLowerCase()))
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
                param.put("chuyenDung", "Sinh viên văn phòng");
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void GetMayTinhMoiPriceFilter(final int fPrice, final int tPrice) {
        binding.progressBar.setVisibility(View.VISIBLE);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.urlGetAllSanPhamChuyenDung, new Response.Listener<String>() {
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
                param.put("chuyenDung", "Sinh viên văn phòng");
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void ActionBar() {
        setSupportActionBar(binding.toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolBar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        binding.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_top_right, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuSearch:
                Intent intent = new Intent(SinhVienVanPhong.this, Search.class);
                startActivity(intent);
                break;
            case R.id.menuCart:
                intent = new Intent(SinhVienVanPhong.this, com.example.laptop88.activity.GioHang.class);
                startActivity(intent);
                break;
            case R.id.menuHome:
                intent = new Intent(SinhVienVanPhong.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.menuBaoHanhHauMai:
                intent = new Intent(SinhVienVanPhong.this, BaoHanhHauMai.class);
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
