package com.example.laptop88.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.laptop88.R;
import com.example.laptop88.adapter.DonHangStaffAdapter;
import com.example.laptop88.databinding.ActivityStaffBinding;
import com.example.laptop88.model.ChiTietDonHang;
import com.example.laptop88.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Staff extends AppCompatActivity {
    ActivityStaffBinding binding;
    ArrayList<ChiTietDonHang> listChiTietDonHang;
    DonHangStaffAdapter donHangStaffAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_staff);
        GetChiTietDonHang();
        HienThiRecyclerView();
        CatchOnButtonDangXuat();
    }

    private void CatchOnButtonDangXuat() {
        binding.tvDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void GetChiTietDonHang() {
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.urlGetAllChiTietDonHangStaff, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int idChiTietDonHang = 0;
                int idDonHang = 0;
                int idSanPham = 0;
                String tenSanPham = "";
                String hinhSanPham = "";
                int soLuong = 0;
                int giaTong = 0;
                String xacNhan = "";
                String daNhan = "";
                int idAccount = 0;
                String hoTen = "";
                String soDienThoai = "";
                String email = "";
                String diaChi = "";
                String ngayDatHang = "";
                String ngayNhanHang = "";
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i=0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        idChiTietDonHang = jsonObject.getInt("idChiTietDonHang");
                        idDonHang = jsonObject.getInt("idDonHang");
                        idSanPham = jsonObject.getInt("idSanPham");
                        tenSanPham = jsonObject.getString("tenSanPham");
                        hinhSanPham = jsonObject.getString("hinhSanPham");
                        soLuong = jsonObject.getInt("soLuong");
                        giaTong = jsonObject.getInt("gia");
                        xacNhan = jsonObject.getString("xacNhan");
                        daNhan = jsonObject.getString("daNhan");
                        idAccount = jsonObject.getInt("idAccount");
                        hoTen = jsonObject.getString("hoTen");
                        soDienThoai = jsonObject.getString("soDienThoai");
                        email = jsonObject.getString("email");
                        diaChi = jsonObject.getString("diaChi");
                        ngayDatHang = jsonObject.getString("ngayDatHang");
                        ngayNhanHang = jsonObject.getString("ngayNhanHang");
                        listChiTietDonHang.add(new ChiTietDonHang(idChiTietDonHang, idDonHang, idSanPham, tenSanPham, hinhSanPham, soLuong, giaTong, xacNhan, daNhan, idAccount, hoTen, soDienThoai, email, diaChi, ngayDatHang, ngayNhanHang));
                        donHangStaffAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();
                Intent intent = getIntent();
                int idAccount = intent.getIntExtra("idAccount", -1);
                param.put("shipper", String.valueOf(idAccount));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void HienThiRecyclerView() {
        listChiTietDonHang = new ArrayList<>();
        donHangStaffAdapter = new DonHangStaffAdapter(getApplicationContext(), listChiTietDonHang);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1, RecyclerView.VERTICAL, false));
        binding.recyclerView.setAdapter(donHangStaffAdapter);
    }
}
