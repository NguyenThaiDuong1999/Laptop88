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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.laptop88.Interface.IOnClickSanPham;
import com.example.laptop88.R;
import com.example.laptop88.adapter.LaptopMoiAdapter;
import com.example.laptop88.databinding.ActivityTatCaSanPhamBinding;
import com.example.laptop88.model.SanPham;
import com.example.laptop88.ultil.CheckConnection;
import com.example.laptop88.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TatCaSanPham extends AppCompatActivity {
    ActivityTatCaSanPhamBinding binding;
    ArrayList<SanPham> listSanPham;
    LaptopMoiAdapter laptopMoiAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tat_ca_san_pham);
        //Hien thi rv
        HienThiRecyclerView();
        //
        if (CheckConnection.haveNetWorkConnection(getApplicationContext())) {
            ActionBar();
            GetAllSanPham();
            LocTheoBrandLapTop();
            LocTheoPrice();
            CatchOnItemListAllSP();
        } else {
            CheckConnection.ShowToast_Short(getApplicationContext(), "Hãy kiểm tra lại kết nối");
            finish();
        }
    }

    private void CatchOnItemListAllSP() {
        laptopMoiAdapter.SetOnClickSanPham(new IOnClickSanPham() {
            @Override
            public void OnClickSanPham(SanPham sanPham) {
                Intent intent = new Intent(TatCaSanPham.this, ChiTietSanPham.class);
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

    private void LocTheoBrandLapTop() {
        binding.btnBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getApplicationContext(), binding.btnBrand);
                popupMenu.getMenuInflater().inflate(R.menu.menu_popup_all_brand, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menuAll:
                                HienThiRecyclerView();
                                GetAllSanPham();
                                binding.tvThuongHieu.setText(item.getTitle());
                                break;
                            case R.id.menuDell:
                                HienThiRecyclerView();
                                GetMayTinhBrandFilter("Dell");
                                binding.tvThuongHieu.setText(item.getTitle());
                                break;
                            case R.id.menuHP:
                                HienThiRecyclerView();
                                GetMayTinhBrandFilter("HP");
                                binding.tvThuongHieu.setText(item.getTitle());
                                break;
                            case R.id.menuLG:
                                HienThiRecyclerView();
                                GetMayTinhBrandFilter("LG");
                                binding.tvThuongHieu.setText(item.getTitle());
                                break;
                            case R.id.menuMacBook:
                                HienThiRecyclerView();
                                GetMayTinhBrandFilter("Mac");
                                binding.tvThuongHieu.setText(item.getTitle());
                                break;
                            case R.id.menuAsus:
                                HienThiRecyclerView();
                                GetMayTinhBrandFilter("Asus");
                                binding.tvThuongHieu.setText(item.getTitle());
                                break;
                            case R.id.menuAcer:
                                HienThiRecyclerView();
                                GetMayTinhBrandFilter("Acer");
                                binding.tvThuongHieu.setText(item.getTitle());
                                break;
                            case R.id.menuLenovo:
                                HienThiRecyclerView();
                                GetMayTinhBrandFilter("Lenovo");
                                binding.tvThuongHieu.setText(item.getTitle());
                                break;
                            case R.id.menuMSI:
                                HienThiRecyclerView();
                                GetMayTinhBrandFilter("MSI");
                                binding.tvThuongHieu.setText(item.getTitle());
                                break;
                            case R.id.menuCorsair:
                                HienThiRecyclerView();
                                GetMayTinhBrandFilter("Corsair");
                                binding.tvThuongHieu.setText(item.getTitle());
                                break;
                            case R.id.menuKingston:
                                HienThiRecyclerView();
                                GetMayTinhBrandFilter("Kingston");
                                binding.tvThuongHieu.setText(item.getTitle());
                                break;
                            case R.id.menuApacer:
                                HienThiRecyclerView();
                                GetMayTinhBrandFilter("Apacer");
                                binding.tvThuongHieu.setText(item.getTitle());
                                break;
                            case R.id.menuWD:
                                HienThiRecyclerView();
                                GetMayTinhBrandFilter("WD");
                                binding.tvThuongHieu.setText(item.getTitle());
                                break;
                            case R.id.menuIntel:
                                HienThiRecyclerView();
                                GetMayTinhBrandFilter("Intel");
                                binding.tvThuongHieu.setText(item.getTitle());
                                break;
                            case R.id.menuGigabyte:
                                HienThiRecyclerView();
                                GetMayTinhBrandFilter("Gigabyte");
                                binding.tvThuongHieu.setText(item.getTitle());
                                break;
                            case R.id.menuSeagate:
                                HienThiRecyclerView();
                                GetMayTinhBrandFilter("Seagate");
                                binding.tvThuongHieu.setText(item.getTitle());
                                break;
                            case R.id.menuLogitech:
                                HienThiRecyclerView();
                                GetMayTinhBrandFilter("Logitech");
                                binding.tvThuongHieu.setText(item.getTitle());
                                break;
                        }
                        CatchOnItemListAllSP();
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
                                GetAllSanPham();
                                CatchOnItemListAllSP();
                                break;
                            case R.id.menu_duoi_5_trieu:
                                HienThiRecyclerView();
                                GetMayTinhPriceFilter(0, 4999999);
                                CatchOnItemListAllSP();
                                break;
                            case R.id.menu_tu_5_den_10_trieu:
                                HienThiRecyclerView();
                                GetMayTinhPriceFilter(5000000, 10000000);
                                CatchOnItemListAllSP();
                                break;
                            case R.id.menu_tu_10_trieu_den_20_trieu:
                                HienThiRecyclerView();
                                GetMayTinhPriceFilter(10000000, 20000000);
                                CatchOnItemListAllSP();
                                break;
                            case R.id.menu_tren_20_trieu:
                                HienThiRecyclerView();
                                GetMayTinhPriceFilter(20000000, 2000000000);
                                CatchOnItemListAllSP();
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }

    private void GetAllSanPham() {
        binding.progressBar.setVisibility(View.VISIBLE);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.urlGetAllSanPham, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                int idSanPham = 0;
                String tenSanPham = "";
                Integer gia = 0;
                String hinhSanPham = "";
                String moTa = "";
                String chuyenDung = "";
                int idLoaiSanPham = 0;
                int baoHanh = 0;
                for (int i = 0; i < response.length(); i++) {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = response.getJSONObject(i);
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
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void GetMayTinhBrandFilter(final String brand) {
        binding.progressBar.setVisibility(View.VISIBLE);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.urlGetAllSanPham, new Response.Listener<String>() {
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
        });
        requestQueue.add(stringRequest);
    }

    private void GetMayTinhPriceFilter(final int fPrice, final int tPrice) {
        binding.progressBar.setVisibility(View.VISIBLE);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.urlGetAllSanPham, new Response.Listener<String>() {
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
        });
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
                Intent intent = new Intent(TatCaSanPham.this, Search.class);
                startActivity(intent);
                break;
            case R.id.menuCart:
                intent = new Intent(TatCaSanPham.this, com.example.laptop88.activity.GioHang.class);
                startActivity(intent);
                break;
            case R.id.menuHome:
                intent = new Intent(TatCaSanPham.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.menuBaoHanhHauMai:
                intent = new Intent(TatCaSanPham.this, BaoHanhHauMai.class);
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
