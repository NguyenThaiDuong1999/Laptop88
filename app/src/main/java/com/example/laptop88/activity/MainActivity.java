package com.example.laptop88.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.laptop88.Interface.IOnClickHang;
import com.example.laptop88.Interface.IOnClickSanPham;
import com.example.laptop88.R;
import com.example.laptop88.adapter.HangAdapter;
import com.example.laptop88.adapter.LoaiSanPhamAdapter;
import com.example.laptop88.adapter.SanPhamAdapter;
import com.example.laptop88.adapter.SanPhamDaMuaAdapter;
import com.example.laptop88.adapter.SanPhamDoanhNhanAdapter;
import com.example.laptop88.adapter.SanPhamGDHAdapter;
import com.example.laptop88.adapter.SanPhamSVVPAdapter;
import com.example.laptop88.databinding.ActivityMainBinding;
import com.example.laptop88.model.Account;
import com.example.laptop88.model.GioHang;
import com.example.laptop88.model.Hang;
import com.example.laptop88.model.LoaiSanPham;
import com.example.laptop88.model.SanPham;
import com.example.laptop88.ultil.CheckConnection;
import com.example.laptop88.ultil.Server;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import net.frakbot.jumpingbeans.JumpingBeans;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    ActivityMainBinding binding;
    //listView
    ArrayList<LoaiSanPham> listLoaiSP;
    LoaiSanPhamAdapter loaiSanPhamAdapter;
    int idLoaiSanPham = 0;
    String tenLoaiSanPham = "";
    String hinhLoaiSanPham = "";
    //rv hãng
    ArrayList<Hang> listHang;
    HangAdapter hangAdapter;
    //rv sp mới nhất
    ArrayList<SanPham> listSanPham;
    SanPhamAdapter sanPhamAdapter;
    //rv sp svvp
    ArrayList<SanPham> listSanPhamSVVP;
    SanPhamSVVPAdapter sanPhamSVVPAdapter;
    //rv sp gaming đồ họa
    ArrayList<SanPham> listSanPhamGDH;
    SanPhamGDHAdapter sanPhamGDHAdapter;
    //rv sp doanh nhân
    ArrayList<SanPham> listSanPhamDN;
    SanPhamDoanhNhanAdapter sanPhamDoanhNhanAdapter;

    //list sp giỏ hàng
    public static ArrayList<GioHang> listGioHang;
    static TextView tvAccount, tvSoSPGioHang;
    static Button btnDangNhap;
    static Button btnDangXuat;
    static Button btnToiTrangQuanTri;
    static DrawerLayout drawerLayout;
    static ImageView imgAccount;
    //List Đã xem
    public static ArrayList<SanPham> listDaXem;
    //List đã đặt
    public static ArrayList<SanPham> listDaDat;
    //List đã mua
    public static ArrayList<SanPham> listDaMua;
    //List yêu thích
    public static ArrayList<SanPham> listYeuThich;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        //
        if (listGioHang != null) {

        } else {
            listGioHang = new ArrayList<>();
        }
        //
        if (listDaXem != null) {

        } else {
            listDaXem = new ArrayList<>();
        }
        //
        if (listDaDat != null) {

        } else {
            listDaDat = new ArrayList<>();
        }
        //
        if (listDaMua != null) {

        } else {
            listDaMua = new ArrayList<>();
        }
        //
        if (listYeuThich != null) {

        } else {
            listYeuThich = new ArrayList<>();
        }
        //

        SetDefaultLayout();
        //Ánh xạ
        AnhXa();
        //Display listView, recyclerView
        HienThiListView();
        HienThiRecyclerViewHang();
        HienThiRecyclerView();
        HienThiRecyclerViewSVVP();
        HienThiRecyclerViewGDH();
        HienThiRecyclerViewDoanhNhan();

        if (CheckConnection.haveNetWorkConnection(getApplicationContext())) {
            ActionBar();
            ActionViewFlipper();
            CustomTextView();
            GetDuLieuLoaiSP();
            CatchOnItemListView();
            GetDuLieuHang();
            CatchOnItemListHang();
            GetDuLieuSanPhamMoiNhat();
            CatchOnItemListSPMoiNhat();
            GetDuLieuSanPhamSVVP("Sinh viên văn phòng");
            CatchOnItemListSPSVVP();
            GetDuLieuSanPhamGDH("Gaming đồ họa");
            CatchOnItemListSPGDH();
            GetDuLieuSanPhamDoanhNhan("Doanh nhân");
            CatchOnItemListSPDN();
            CatchOnButtonDangNhap();
            CatchOnButtonDangXuat();
            CatchOnButtonToiTrangQuanTri();
            CatchOnTVXemThemSPMN();
            CatchOnTVXemThemSPSVVP();
            CatchOnTVXemThemSPGDH();
            CatchOnTVXemThemSPDN();
            CatchOnTvAccountAndImgAccount();
        } else {
            CheckConnection.ShowToast_Short(getApplicationContext(), "Hãy kiểm tra lại kết nối");
            finish();
        }
    }

    private void CatchOnItemListHang() {
        hangAdapter.SetOnClickHang(new IOnClickHang() {
            @Override
            public void OnClickHang(Hang hang) {
                Intent intent = new Intent(MainActivity.this, com.example.laptop88.activity.Hang.class);
                intent.putExtra("hang", hang);
                startActivity(intent);
            }
        });
    }

    private void GetDuLieuHang() {
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.urlHang, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                int idHang = 0;
                String tenHang = "";
                String hinhHang = "";
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        idHang = jsonObject.getInt("idHang");
                        tenHang = jsonObject.getString("tenHang");
                        hinhHang = jsonObject.getString("hinhHang");
                        listHang.add(new Hang(idHang, tenHang, hinhHang));
                        hangAdapter.notifyDataSetChanged();
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

    private void HienThiRecyclerViewHang() {
        listHang = new ArrayList<>();
        hangAdapter = new HangAdapter(listHang, getApplicationContext());
        binding.recyclerViewHang.setHasFixedSize(true);
        binding.recyclerViewHang.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2, GridLayoutManager.HORIZONTAL, false));
        binding.recyclerViewHang.setAdapter(hangAdapter);
    }

    private void CatchOnTvAccountAndImgAccount() {
        binding.tvAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DangNhap.currentAccount != null) {
                    Intent intent = new Intent(MainActivity.this, Me.class);
                    startActivity(intent);
                }
            }
        });
        binding.imgAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DangNhap.currentAccount != null) {
                    Intent intent = new Intent(MainActivity.this, Me.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void CatchOnTVXemThemSPDN() {
        binding.tvXemThemDN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DoanhNhan.class);
                startActivity(intent);
            }
        });
    }

    private void CatchOnTVXemThemSPGDH() {
        binding.tvXemThemGDH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GamingDoHoa.class);
                startActivity(intent);
            }
        });
    }

    private void CatchOnTVXemThemSPMN() {
        binding.tvXemThemSPMN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TatCaSanPham.class);
                startActivity(intent);
            }
        });
    }

    private void CatchOnTVXemThemSPSVVP() {
        binding.tvXemThemSVVP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SinhVienVanPhong.class);
                startActivity(intent);
            }
        });
    }

    private void CatchOnButtonToiTrangQuanTri() {
        binding.btnToiTrangQuanTri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Admin.class);
                startActivity(intent);
            }
        });
    }

    private void SetDefaultLayout() {
        if (DangNhap.currentAccount != null) {
            binding.tvAccount.setText(DangNhap.currentAccount.getUsername() + " >");
            binding.btnDangNhap.setVisibility(View.INVISIBLE);
            binding.btnDangXuat.setVisibility(View.VISIBLE);
            binding.tvSoSPGioHang.setText(String.valueOf(listGioHang.size()));
            /*if (currentAccount.getUsername().equals("Admin")) {
                binding.btnToiTrangQuanTri.setVisibility(View.VISIBLE);
            }*/
        } else {

        }
    }

    private void AnhXa() {
        tvAccount = findViewById(R.id.tvAccount);
        tvSoSPGioHang = findViewById(R.id.tvSoSPGioHang);
        btnDangNhap = findViewById(R.id.btnDangNhap);
        btnDangXuat = findViewById(R.id.btnDangXuat);
        btnToiTrangQuanTri = findViewById(R.id.btnToiTrangQuanTri);
        drawerLayout = findViewById(R.id.drawerLayout);
        imgAccount = findViewById(R.id.imgAccount);
    }

    private void CatchOnButtonDangXuat() {
        binding.btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.imgAccount.setImageResource(R.drawable.ic_account_circle_orange_24dp);
                binding.tvAccount.setText("Click đăng nhập");
                binding.tvSoSPGioHang.setText("0");
                binding.btnDangNhap.setVisibility(View.VISIBLE);
                binding.btnDangXuat.setVisibility(View.INVISIBLE);
                binding.btnToiTrangQuanTri.setVisibility(View.GONE);
                DangNhap.checkDangNhap = false;
                DangNhap.currentAccount = null;
                listGioHang.clear();
                listDaXem.clear();
                listYeuThich.clear();
            }
        });
    }

    private void CatchOnButtonDangNhap() {
        binding.btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DangNhap.class);
                startActivity(intent);
            }
        });
    }

    private void CustomTextView() {
        JumpingBeans.with(binding.tvSPMoiNhat)
                .makeTextJump(binding.tvSPMoiNhat.getText().length() - 3, binding.tvSPMoiNhat.getText().length())
                .setIsWave(true)
                .setLoopDuration(1000)
                .build();
        JumpingBeans.with(binding.tvSVVP)
                .makeTextJump(binding.tvSVVP.getText().length() - 3, binding.tvSVVP.getText().length())
                .setIsWave(true)
                .setLoopDuration(1000)
                .build();
        JumpingBeans.with(binding.tvGamingDoHoa)
                .makeTextJump(binding.tvGamingDoHoa.getText().length() - 3, binding.tvGamingDoHoa.getText().length())
                .setIsWave(true)
                .setLoopDuration(1000)
                .build();
        JumpingBeans.with(binding.tvDoanhNhan)
                .makeTextJump(binding.tvDoanhNhan.getText().length() - 3, binding.tvDoanhNhan.getText().length())
                .setIsWave(true)
                .setLoopDuration(1000)
                .build();
    }

    private void HienThiRecyclerViewDoanhNhan() {
        listSanPhamDN = new ArrayList<>();
        sanPhamDoanhNhanAdapter = new SanPhamDoanhNhanAdapter(getApplicationContext(), listSanPhamDN);
        binding.recyclerViewDoanhNhan.setHasFixedSize(true);
        binding.recyclerViewDoanhNhan.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.recyclerViewDoanhNhan.setAdapter(sanPhamDoanhNhanAdapter);
    }

    private void HienThiRecyclerViewGDH() {
        listSanPhamGDH = new ArrayList<>();
        sanPhamGDHAdapter = new SanPhamGDHAdapter(getApplicationContext(), listSanPhamGDH);
        binding.recyclerViewGDH.setHasFixedSize(true);
        binding.recyclerViewGDH.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.recyclerViewGDH.setAdapter(sanPhamGDHAdapter);
    }

    private void HienThiRecyclerViewSVVP() {
        listSanPhamSVVP = new ArrayList<>();
        sanPhamSVVPAdapter = new SanPhamSVVPAdapter(getApplicationContext(), listSanPhamSVVP);
        binding.recyclerViewSVVP.setHasFixedSize(true);
        binding.recyclerViewSVVP.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.recyclerViewSVVP.setAdapter(sanPhamSVVPAdapter);
        binding.recyclerViewSVVP.setNestedScrollingEnabled(false);
    }

    private void HienThiRecyclerView() {
        listSanPham = new ArrayList<>();
        sanPhamAdapter = new SanPhamAdapter(getApplicationContext(), listSanPham);
        binding.recyclerView.setHasFixedSize(true);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        binding.recyclerView.setAdapter(sanPhamAdapter);
        binding.recyclerView.setNestedScrollingEnabled(false);
        //set sp ở giữa màn hình
//        final LinearSnapHelper linearSnapHelper = new LinearSnapHelper();
//        linearSnapHelper.attachToRecyclerView(binding.recyclerView);

        final Timer timer = new Timer();
        int time = 4000;
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                int position = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                if (position < (sanPhamAdapter.getItemCount() - 1)) {
                    linearLayoutManager.smoothScrollToPosition(binding.recyclerView, new RecyclerView.State(), position + 1);
                } else if (position == (sanPhamAdapter.getItemCount() - 1)) {
                    linearLayoutManager.smoothScrollToPosition(binding.recyclerView, new RecyclerView.State(), 0);
                }
            }
        }, 0, time);
    }

    private void HienThiListView() {
        listLoaiSP = new ArrayList<>();
        listLoaiSP.add(0, new LoaiSanPham(0, "Trang chủ", "https://cdn.pixabay.com/photo/2015/12/28/02/58/home-1110868_960_720.png"));
        loaiSanPhamAdapter = new LoaiSanPhamAdapter(listLoaiSP, getApplicationContext());
        binding.listView.setAdapter(loaiSanPhamAdapter);
    }

    private void GetDuLieuLoaiSP() {
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.urlLoaiSP, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            idLoaiSanPham = jsonObject.getInt("idLoaiSanPham");
                            tenLoaiSanPham = jsonObject.getString("tenLoaiSanPham");
                            hinhLoaiSanPham = jsonObject.getString("hinhLoaiSanPham");
                            listLoaiSP.add(new LoaiSanPham(idLoaiSanPham, tenLoaiSanPham, hinhLoaiSanPham));
                            loaiSanPhamAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToast_Short(getApplicationContext(), error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void GetDuLieuSanPhamMoiNhat() {
        binding.progressBar.setVisibility(View.VISIBLE);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.urlSPMoiNhat, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    int idSP = 0;
                    String tenSP = "";
                    Integer gia = 0;
                    String hinhSP = "";
                    String moTa = "";
                    String chuyenDung = "";
                    int idLoaiSP = 0;
                    int baoHanh = 0;
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            idSP = jsonObject.getInt("idSanPham");
                            tenSP = jsonObject.getString("tenSanPham");
                            gia = jsonObject.getInt("gia");
                            hinhSP = jsonObject.getString("hinhSanPham");
                            moTa = jsonObject.getString("moTa");
                            chuyenDung = jsonObject.getString("chuyenDung");
                            idLoaiSP = jsonObject.getInt("idLoaiSanPham");
                            baoHanh = jsonObject.getInt("baoHanh");
                            listSanPham.add(new SanPham(idSP, tenSP, gia, hinhSP, moTa, chuyenDung, idLoaiSP, baoHanh));
                            sanPhamAdapter.notifyDataSetChanged();
                            binding.progressBar.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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

    private void GetDuLieuSanPhamDoanhNhan(final String chuyenDung) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.urlSanPhamChuyenDung, new Response.Listener<String>() {
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
                            listSanPhamDN.add(new SanPham(idSanPham, tenSanPham, gia, hinhSanPham, moTa, chuyenDung, idLoaiSanPham, baoHanh));
                            sanPhamDoanhNhanAdapter.notifyDataSetChanged();
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
                param.put("chuyenDung", chuyenDung);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void GetDuLieuSanPhamSVVP(final String chuyenDung) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.urlSanPhamChuyenDung, new Response.Listener<String>() {
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
                            listSanPhamSVVP.add(new SanPham(idSanPham, tenSanPham, gia, hinhSanPham, moTa, chuyenDung, idLoaiSanPham, baoHanh));
                            sanPhamSVVPAdapter.notifyDataSetChanged();
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
                param.put("chuyenDung", chuyenDung);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void GetDuLieuSanPhamGDH(final String chuyenDung) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.urlSanPhamChuyenDung, new Response.Listener<String>() {
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
                            listSanPhamGDH.add(new SanPham(idSanPham, tenSanPham, gia, hinhSanPham, moTa, chuyenDung, idLoaiSanPham, baoHanh));
                            sanPhamGDHAdapter.notifyDataSetChanged();
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
                param.put("chuyenDung", chuyenDung);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void CatchOnItemListView() {
        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        if (CheckConnection.haveNetWorkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            CheckConnection.ShowToast_Short(getApplicationContext(), "Hãy kiểm tra kết nối");
                        }
                        binding.drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        Intent intent = new Intent(MainActivity.this, LaptopMoi.class);
                        int idLoaiSP = listLoaiSP.get(position).getId();
                        intent.putExtra("idLoaiSanPham", idLoaiSP);
                        startActivity(intent);
                        binding.drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        intent = new Intent(MainActivity.this, LaptopCu.class);
                        idLoaiSP = listLoaiSP.get(position).getId();
                        intent.putExtra("idLoaiSanPham", idLoaiSP);
                        startActivity(intent);
                        binding.drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        intent = new Intent(MainActivity.this, PhuKien.class);
                        idLoaiSP = listLoaiSP.get(position).getId();
                        intent.putExtra("idLoaiSanPham", idLoaiSP);
                        startActivity(intent);
                        binding.drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
            }
        });
    }

    private void CatchOnItemListSPMoiNhat() {
        sanPhamAdapter.SetOnClickSanPham(new IOnClickSanPham() {
            @Override
            public void OnClickSanPham(SanPham sanPham) {
                Intent intent = new Intent(MainActivity.this, ChiTietSanPham.class);
                intent.putExtra("chiTietSanPham", sanPham);
                startActivity(intent);
            }
        });
    }

    private void CatchOnItemListSPSVVP() {
        sanPhamSVVPAdapter.SetOnClickSanPham(new IOnClickSanPham() {
            @Override
            public void OnClickSanPham(SanPham sanPham) {
                Intent intent = new Intent(MainActivity.this, ChiTietSanPham.class);
                intent.putExtra("chiTietSanPham", sanPham);
                startActivity(intent);
            }
        });
    }

    private void CatchOnItemListSPGDH() {
        sanPhamGDHAdapter.SetOnClickSanPham(new IOnClickSanPham() {
            @Override
            public void OnClickSanPham(SanPham sanPham) {
                Intent intent = new Intent(MainActivity.this, ChiTietSanPham.class);
                intent.putExtra("chiTietSanPham", sanPham);
                startActivity(intent);
            }
        });
    }

    private void CatchOnItemListSPDN() {
        sanPhamDoanhNhanAdapter.SetOnClickSanPham(new IOnClickSanPham() {
            @Override
            public void OnClickSanPham(SanPham sanPham) {
                Intent intent = new Intent(MainActivity.this, ChiTietSanPham.class);
                intent.putExtra("chiTietSanPham", sanPham);
                startActivity(intent);
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
                Intent intent = new Intent(MainActivity.this, Search.class);
                startActivity(intent);
                break;
            case R.id.menuCart:
                intent = new Intent(MainActivity.this, com.example.laptop88.activity.GioHang.class);
                startActivity(intent);
                break;
            case R.id.menuHome:
                intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.menuBaoHanhHauMai:
                intent = new Intent(MainActivity.this, BaoHanhHauMai.class);
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

    private void GetFragment(Fragment fragment) {
        try {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "getFragment: " + e.getMessage());
        }
    }

    private void ActionBar() {
        setSupportActionBar(binding.toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolBar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
        binding.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void ActionViewFlipper() {
        final ArrayList<String> mangQuangCao = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.urlQuangCao, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        mangQuangCao.add(jsonObject.getString("hinhQuangCao"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                for (int i = 0; i < mangQuangCao.size(); i++) {
                    ImageView imageView = new ImageView(getApplicationContext());
                    Picasso.with(getApplicationContext()).load(mangQuangCao.get(i)).into(imageView);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    binding.viewFlipper.addView(imageView);
                    final int finalI = i;
                    binding.viewFlipper.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            switch (finalI) {
                                case 0:
                                    Intent intent = new Intent(MainActivity.this, SinhVienVanPhong.class);
                                    startActivity(intent);
                                    break;
                                case 1:
                                    intent = new Intent(MainActivity.this, BaoHanhHauMai.class);
                                    startActivity(intent);
                                    break;
                                case 2:
                                    intent = new Intent(MainActivity.this, GamingDoHoa.class);
                                    startActivity(intent);
                                    break;
                            }
                        }
                    });
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
        //
        binding.viewFlipper.setFlipInterval(8000);
        binding.viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        binding.viewFlipper.setInAnimation(animation_slide_in);
        binding.viewFlipper.setOutAnimation(animation_slide_out);
        //
        binding.imgPreviousViewFlipper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.viewFlipper.isAutoStart()) {
                    binding.viewFlipper.stopFlipping();
                    binding.viewFlipper.showPrevious();
                    binding.viewFlipper.startFlipping();
                    binding.viewFlipper.setAutoStart(true);
                }
            }
        });
        binding.imgNextViewFlipper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.viewFlipper.isAutoStart()) {
                    binding.viewFlipper.stopFlipping();
                    binding.viewFlipper.showNext();
                    binding.viewFlipper.startFlipping();
                    binding.viewFlipper.setAutoStart(true);
                }
            }
        });

    }
}
