package com.example.laptop88.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.RequiresApi;
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
import com.example.laptop88.databinding.ActivityThongTinKhachHangBinding;
import com.example.laptop88.ultil.CheckConnection;
import com.example.laptop88.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ThongTinKhachHang extends AppCompatActivity {
    ActivityThongTinKhachHangBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_thong_tin_khach_hang);
        if (CheckConnection.haveNetWorkConnection(getApplicationContext())) {
            SetDefaultValue();
            CatchOnButtonDatHang();
            CatchOnButtonQuayLai();
        } else {
            CheckConnection.ShowToast_Short(getApplicationContext(), "Hãy kiểm tra lại kết nối");
        }
    }

    private void ClearGioHangAndSaveWithIDAccount() {
        if (DangNhap.currentAccount != null) {
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.urlClearGioHangWithIDAccount, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (DangNhap.currentAccount != null) {
                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
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
        }
    }

    private void CatchOnButtonDatHang() {
        binding.btnDatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String hoTen = binding.edtHoTen.getText().toString().trim();
                final String soDienThoai = binding.edtSDT.getText().toString().trim();
                final String email = binding.edtEmail.getText().toString().trim();
                final String diaChi = binding.edtDiaChi.getText().toString().trim();
                if (hoTen.length() > 0 && soDienThoai.length() > 0 && email.length() > 0 && diaChi.length() > 0) {
                    //Insert into đơn hàng
                    final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.urlDonHang, new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String idDonHang) {
                            //CheckConnection.ShowToast_Short(getApplicationContext(), response);
                            //Insert into chi tiết đơn hàng
                            if (Integer.parseInt(idDonHang) > 0) {
                                RequestQueue requestQueue1 = Volley.newRequestQueue(getApplicationContext());
                                StringRequest stringRequest1 = new StringRequest(Request.Method.POST, Server.urlChiTietDonHang, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if (response.equals("Success")) {
                                            MainActivity.listGioHang.clear();
                                            CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn đã đặt hàng thành công, mời bạn tiếp tục mua hàng!");
                                            ClearGioHangAndSaveWithIDAccount();
                                            Intent intent = new Intent(ThongTinKhachHang.this, MainActivity.class);
                                            startActivity(intent);
                                        }
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
                                                jsonObject.put("idDonHang", idDonHang);
                                                jsonObject.put("idSanPham", MainActivity.listGioHang.get(i).getiDSanPham());
                                                //jsonObject.put("tenSanPham", MainActivity.listGioHang.get(i).getTenSanPham());
                                                jsonObject.put("soLuong", MainActivity.listGioHang.get(i).getSoLuong());
                                                jsonObject.put("gia", MainActivity.listGioHang.get(i).getGia());
                                                jsonObject.put("xacNhan", 0);
                                                jsonObject.put("daNhan", 0);
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
                                requestQueue1.add(stringRequest1);
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> params = new HashMap<>();
                            params.put("idAccount", String.valueOf(DangNhap.currentAccount.getIdAccount()));
                            params.put("hoTen", hoTen);
                            params.put("soDienThoai", soDienThoai);
                            params.put("email", email);
                            params.put("diaChi", diaChi);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                            String ngayDatHang = simpleDateFormat.format(Calendar.getInstance().getTime());
                            params.put("ngayDatHang", ngayDatHang);
                            return params;
                        }
                    };
                    requestQueue.add(stringRequest);
                } else {
                    CheckConnection.ShowToast_Short(getApplicationContext(), "Hãy kiểm tra lại thông tin vừa điền");
                }
                //
                //SaveToSPDaMua();
            }
        });
    }

    private void SetDefaultValue() {
        //CheckConnection.ShowToast_Short(getApplicationContext(), MainActivity.tvAccount.getText().toString());
        binding.edtHoTen.setText(DangNhap.currentAccount.getHoTen());
        binding.edtSDT.setText(DangNhap.currentAccount.getSoDienThoai());
        binding.edtEmail.setText(DangNhap.currentAccount.getEmail());
        binding.edtDiaChi.setText(DangNhap.currentAccount.getDiaChi());
    }

    private void CatchOnButtonQuayLai() {
        binding.btnQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
