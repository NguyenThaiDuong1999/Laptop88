package com.example.laptop88.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.laptop88.Interface.IOnLongClick;
import com.example.laptop88.R;
import com.example.laptop88.adapter.GioHangAdapter;
import com.example.laptop88.databinding.ActivityGioHangBinding;
import com.example.laptop88.ultil.CheckConnection;
import com.example.laptop88.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class GioHang extends AppCompatActivity {
    static ActivityGioHangBinding binding;
    GioHangAdapter gioHangAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_gio_hang);
        //
        ClearGioHangAndSaveWithIDAccount(getApplicationContext());
        HienThiListGioHang();
        if (CheckConnection.haveNetWorkConnection(getApplicationContext())) {
            SetDefaultLayout();
            ToolBar();
            CheckDataGioHang();
            SetTextViewTongTienGiaTri();
            CatchOnItemListGioHangToRemove();
            CatchOnButtonTiepTucXemHang();
            CatchOnButtonMuaHang();
            CatchOnButtonDangNhap();
        } else {
            CheckConnection.ShowToast_Short(getApplicationContext(), "H??y ki???m tra l???i k???t n???i");
        }
    }

    private void CatchOnButtonDangNhap() {
        binding.btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GioHang.this, DangNhap.class);
                startActivity(intent);
            }
        });
    }

    private void SetDefaultLayout() {
        if (DangNhap.currentAccount == null) {
            binding.tvTongTien.setVisibility(View.GONE);
            binding.tvTongTienGiaTri.setVisibility(View.GONE);
            binding.btnMuaHang.setVisibility(View.GONE);
            binding.btnTiepTucXemHang.setVisibility(View.GONE);
            //binding.btnDangNhap.setVisibility(View.VISIBLE);
        }else{
            binding.tvTongTien.setVisibility(View.VISIBLE);
            binding.tvTongTienGiaTri.setVisibility(View.VISIBLE);
            binding.btnMuaHang.setVisibility(View.VISIBLE);
            binding.btnTiepTucXemHang.setVisibility(View.VISIBLE);
            //binding.btnDangNhap.setVisibility(View.GONE);
        }
    }

    public static void ClearGioHangAndSaveWithIDAccount(final Context context) {
        if (DangNhap.currentAccount != null) {
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.urlClearGioHangWithIDAccount, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (DangNhap.currentAccount != null) {
                        RequestQueue requestQueue = Volley.newRequestQueue(context);
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.urlGioHang, new Response.Listener<String>() {
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
                                JSONArray jsonArray = new JSONArray();
                                for (int i = 0; i < MainActivity.listGioHang.size(); i++) {
                                    JSONObject jsonObject = new JSONObject();
                                    try {
                                        jsonObject.put("idAccount", DangNhap.currentAccount.getIdAccount());
                                        jsonObject.put("idSanPham", MainActivity.listGioHang.get(i).getiDSanPham());
                                        //jsonObject.put("tenSanPham", MainActivity.listGioHang.get(i).getTenSanPham());
                                        //jsonObject.put("hinhSanPham", MainActivity.listGioHang.get(i).getHinhSanPham());
                                        jsonObject.put("soLuong", MainActivity.listGioHang.get(i).getSoLuong());
                                        jsonObject.put("gia", MainActivity.listGioHang.get(i).getGia());
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    jsonArray.put(jsonObject);
                                }
                                HashMap<String, String> param = new HashMap<>();
                                param.put("json", String.valueOf(jsonArray));
                                return param;
                            }
                        };
                        requestQueue.add(stringRequest);
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
        }else{
            CheckConnection.ShowToast_Short(context, "Vui l??ng ????ng nh???p ????? xem th??ng tin gi??? h??ng!");
        }
    }

    private void CatchOnButtonMuaHang() {
        binding.btnMuaHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DangNhap.checkDangNhap == true) {
                    if (MainActivity.listGioHang.size() >= 1) {
                        Intent intent = new Intent(GioHang.this, ThongTinKhachHang.class);
                        startActivity(intent);
                    } else {
                        CheckConnection.ShowToast_Short(getApplicationContext(), "B???n h??y th??m s???n ph???m v??o gi??? h??ng ????? ?????t h??ng");
                    }
                } else {
                    CheckConnection.ShowToast_Short(getApplicationContext(), "Vui l??ng ????ng nh???p ????? ?????t h??ng");
                    Intent intent = new Intent(GioHang.this, DangNhap.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void CatchOnButtonTiepTucXemHang() {
        binding.btnTiepTucXemHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GioHang.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void CatchOnItemListGioHangToRemove() {
        gioHangAdapter.SetIonLongClick(new IOnLongClick() {
            @Override
            public void OnLongClick(final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GioHang.this);
                builder.setTitle(String.valueOf(MainActivity.listGioHang.get(position).getTenSanPham()));
                builder.setMessage("B???n c?? ch???c mu???n x??a s???n ph???m n??y kh???i gi??? h??ng?");
                builder.setPositiveButton("X??a", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (MainActivity.listGioHang.size() <= 0) {
                            binding.tvGioHangTrong.setVisibility(View.VISIBLE);
                        } else {
                            //XoaSanPhamGioHangWithIDSanPhamAndIDAccount
                            XoaSanPhamGioHangWithIDSanPhamAndIDAccount(String.valueOf(DangNhap.currentAccount.getIdAccount()), String.valueOf(MainActivity.listGioHang.get(position).getiDSanPham()));
                            //
                            MainActivity.listGioHang.remove(position);
                            gioHangAdapter.notifyDataSetChanged();
                            SetTextViewTongTienGiaTri();
                            if (MainActivity.listGioHang.size() <= 0) {
                                binding.tvGioHangTrong.setVisibility(View.VISIBLE);
                            } else {
                                binding.tvGioHangTrong.setVisibility(View.INVISIBLE);
                                gioHangAdapter.notifyDataSetChanged();
                                SetTextViewTongTienGiaTri();
                            }
                        }
                        //Reset tv s??? sp trong gi???
                        MainActivity.tvSoSPGioHang.setText(String.valueOf(MainActivity.listGioHang.size()));
                    }
                });
                builder.setNegativeButton("H???y", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gioHangAdapter.notifyDataSetChanged();
                        SetTextViewTongTienGiaTri();
                    }
                });
                builder.show();
            }
        });
    }

    private void XoaSanPhamGioHangWithIDSanPhamAndIDAccount(final String idAccount, final String idSanPham) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.urlXoaSanPhamGioHangWithIDSanPhamAndIDAccount, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("Success")) {
                    CheckConnection.ShowToast_Short(getApplicationContext(), "X??a s???n ph???m th??nh c??ng!");
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
                param.put("idAccount", idAccount);
                param.put("idSanPham", idSanPham);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void HienThiListGioHang() {
        gioHangAdapter = new GioHangAdapter(getApplicationContext(), MainActivity.listGioHang);
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        binding.recyclerView.setAdapter(gioHangAdapter);
    }

    public static void SetTextViewTongTienGiaTri() {
        int tongTien = 0;
        for (int i = 0; i < MainActivity.listGioHang.size(); i++) {
            tongTien += MainActivity.listGioHang.get(i).getGia();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        binding.tvTongTienGiaTri.setText(decimalFormat.format(tongTien) + "??");
    }

    private void CheckDataGioHang() {
        if (MainActivity.listGioHang.size() <= 0) {
            gioHangAdapter.notifyDataSetChanged();
            binding.tvGioHangTrong.setVisibility(View.VISIBLE);
            binding.recyclerView.setVisibility(View.INVISIBLE);
        } else {
            gioHangAdapter.notifyDataSetChanged();
            binding.tvGioHangTrong.setVisibility(View.INVISIBLE);
            binding.recyclerView.setVisibility(View.VISIBLE);
        }
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

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_top_right, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuSearch:
                Intent intent = new Intent(GioHang.this, Search.class);
                startActivity(intent);
                break;
            case R.id.menuHome:
                intent = new Intent(GioHang.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.menuCart:
                intent = new Intent(GioHang.this, com.example.laptop88.activity.GioHang.class);
                startActivity(intent);
                break;
            case R.id.menuBaoHanhHauMai:
                intent = new Intent(GioHang.this, BaoHanhHauMai.class);
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
