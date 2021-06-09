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
import com.example.laptop88.databinding.ActivityPhuKienBinding;
import com.example.laptop88.model.SanPham;
import com.example.laptop88.ultil.CheckConnection;
import com.example.laptop88.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PhuKien extends AppCompatActivity {
    ActivityPhuKienBinding binding;
    ArrayList<SanPham> listSanPham;
    LaptopMoiAdapter laptopMoiAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_phu_kien);
        //
        HienThiRecyclerView();
        //
        if (CheckConnection.haveNetWorkConnection(getApplicationContext())) {
            ToolBar();
            GetPhuKien();
            LocTheoLoaiPhuKien();
            LocTheoPrice();
            LocTheoBrand();
            CatchOnItemListPK();
        } else {
            CheckConnection.ShowToast_Short(getApplicationContext(), "Hãy kiểm tra lại kết nối");
        }
    }

    private void LocTheoPrice() {
        binding.btnGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getApplicationContext(), binding.btnGia);
                popupMenu.getMenuInflater().inflate(R.menu.menu_popup_gia_phu_kien, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menuAll:
                                HienThiRecyclerView();
                                GetPhuKien();
                                break;
                            case R.id.menu_duoi_500:
                                HienThiRecyclerView();
                                GetPhuKienPriceFilter(0, 499999);
                                break;
                            case R.id.menu_tu_500_den_1_trieu:
                                HienThiRecyclerView();
                                GetPhuKienPriceFilter(500000, 1000000);
                                break;
                            case R.id.menu_tu_1_trieu_den_2_trieu:
                                HienThiRecyclerView();
                                GetPhuKienPriceFilter(1000000, 2000000);
                                break;
                            case R.id.menu_tren_2_trieu:
                                HienThiRecyclerView();
                                GetPhuKienPriceFilter(2000000, 2000000000);
                                break;
                        }
                        CatchOnItemListPK();
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }

    private void GetPhuKienPriceFilter(final int fPrice, final int tPrice) {
        binding.progressBar.setVisibility(View.VISIBLE);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.urlMayTinhMoi, new Response.Listener<String>() {
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
                param.put("idLoaiSanPham", String.valueOf(GetIDLoaiSanPham()));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void GetLoaiPhuKienFilter(final String loai) {
        binding.progressBar.setVisibility(View.VISIBLE);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.urlMayTinhMoi, new Response.Listener<String>() {
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
                            if (tenSanPham.toLowerCase().contains(loai.toLowerCase()))
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
                param.put("idLoaiSanPham", String.valueOf(GetIDLoaiSanPham()));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void LocTheoBrand() {
        binding.btnBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getApplicationContext(), binding.btnBrand);
                popupMenu.getMenuInflater().inflate(R.menu.menu_popup_brand_phu_kien, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menuAll:
                                HienThiRecyclerView();
                                GetPhuKien();
                                binding.tvThuongHieu.setText(item.getTitle());
                                break;
                            case R.id.menuCorsair:
                                HienThiRecyclerView();
                                GetPhuKienBrandFilter("Corsair");
                                binding.tvThuongHieu.setText(item.getTitle());
                                break;
                            case R.id.menuKingston:
                                HienThiRecyclerView();
                                GetPhuKienBrandFilter("Kingston");
                                binding.tvThuongHieu.setText(item.getTitle());
                                break;
                            case R.id.menuApacer:
                                HienThiRecyclerView();
                                GetPhuKienBrandFilter("Apacer");
                                binding.tvThuongHieu.setText(item.getTitle());
                                break;
                            case R.id.menuWD:
                                HienThiRecyclerView();
                                GetPhuKienBrandFilter("WD");
                                binding.tvThuongHieu.setText(item.getTitle());
                                break;
                            case R.id.menuIntel:
                                HienThiRecyclerView();
                                GetPhuKienBrandFilter("Intel");
                                binding.tvThuongHieu.setText(item.getTitle());
                                break;
                            case R.id.menuGigabyte:
                                HienThiRecyclerView();
                                GetPhuKienBrandFilter("Gigabyte");
                                binding.tvThuongHieu.setText(item.getTitle());
                                break;
                            case R.id.menuSeagate:
                                HienThiRecyclerView();
                                GetPhuKienBrandFilter("Seagate");
                                binding.tvThuongHieu.setText(item.getTitle());
                                break;
                            case R.id.menuLogitech:
                                HienThiRecyclerView();
                                GetPhuKienBrandFilter("Logitech");
                                binding.tvThuongHieu.setText(item.getTitle());
                                break;
                        }
                        CatchOnItemListPK();
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }

    private void GetPhuKienBrandFilter(final String brand){
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
                param.put("tenHang", brand);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void LocTheoLoaiPhuKien() {
        binding.btnLoaiPhuKien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getApplicationContext(), binding.btnLoaiPhuKien);
                popupMenu.getMenuInflater().inflate(R.menu.menu_popup_accessory_type, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_All:
                                HienThiRecyclerView();
                                GetPhuKien();
                                break;
                            case R.id.menu_chuot:
                                HienThiRecyclerView();
                                GetLoaiPhuKienFilter("Chuột");
                                break;
                            case R.id.menu_ram:
                                HienThiRecyclerView();
                                GetLoaiPhuKienFilter("Ram");
                                break;
                            case R.id.menu_HDD:
                                HienThiRecyclerView();
                                GetLoaiPhuKienFilter("HDD");
                                break;
                            case R.id.menu_SSD:
                                HienThiRecyclerView();
                                GetLoaiPhuKienFilter("SSD");
                                break;
                            case R.id.menu_BanPhim:
                                HienThiRecyclerView();
                                GetLoaiPhuKienFilter("Phím");
                                break;
                            case R.id.menu_Card:
                                HienThiRecyclerView();
                                GetLoaiPhuKienFilter("Card");
                                break;
                        }
                        CatchOnItemListPK();
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }

    private void CatchOnItemListPK() {
        laptopMoiAdapter.SetOnClickSanPham(new IOnClickSanPham() {
            @Override
            public void OnClickSanPham(SanPham sanPham) {
                Intent intent = new Intent(PhuKien.this, ChiTietSanPham.class);
                intent.putExtra("chiTietSanPham", sanPham);
                startActivity(intent);
            }
        });
    }

    private void ToolBar() {
        setSupportActionBar(binding.toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void GetPhuKien() {
        binding.progressBar.setVisibility(View.VISIBLE);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.urlMayTinhMoi, new Response.Listener<String>() {
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
                param.put("idLoaiSanPham", String.valueOf(GetIDLoaiSanPham()));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private int GetIDLoaiSanPham() {
        int idLoaiSP = 0;
        Intent intent = getIntent();
        idLoaiSP = intent.getIntExtra("idLoaiSanPham", -1);
        return idLoaiSP;
    }

    private void HienThiRecyclerView() {
        listSanPham = new ArrayList<>();
        laptopMoiAdapter = new LaptopMoiAdapter(getApplicationContext(), listSanPham);
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        binding.recyclerView.setAdapter(laptopMoiAdapter);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_top_right, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuSearch:
                Intent intent = new Intent(PhuKien.this, Search.class);
                startActivity(intent);
                break;
            case R.id.menuCart:
                intent = new Intent(PhuKien.this, com.example.laptop88.activity.GioHang.class);
                startActivity(intent);
                break;
            case R.id.menuBaoHanhHauMai:
                intent = new Intent(PhuKien.this, BaoHanhHauMai.class);
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
