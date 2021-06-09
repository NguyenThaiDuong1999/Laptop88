package com.example.laptop88.Fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.laptop88.R;
import com.example.laptop88.adapter.DonHangAdminAdapter;
import com.example.laptop88.databinding.FragmentDonHangBinding;
import com.example.laptop88.model.ChiTietDonHang;
import com.example.laptop88.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DonHang extends Fragment {
    FragmentDonHangBinding binding;
    ArrayList<ChiTietDonHang> listChiTietDonHang;
    DonHangAdminAdapter donHangAdminAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_don_hang, container, false);
        GetAllChiTietDonHang();
        HienThiRecyclerView();
        CatchOnEdtTimKiem();
        return binding.getRoot();
    }

    private void CatchOnEdtTimKiem() {
        binding.edtTimKiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding.edtTimKiem.getText().toString().trim().length() <= 0) {
                    listChiTietDonHang.clear();
                    HienThiRecyclerView();
                    final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                    JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.urlGetAllChiTietDonHangAdmin, new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            int idChiTietDonHang = 0;
                            int idDonHang = 0;
                            int idSanPham = 0;
                            String tenSanPham = "";
                            String hinhSanPham = "";
                            int baoHanh = 0;
                            int soLuong = 0;
                            int giaTong = 0;
                            String xacNhan = "";
                            String daNhan = "";
                            int idAccount = 0;
                            String hoTen = "";
                            String soDienThoai = "";
                            String ngayDatHang = "";
                            String ngayNhanHang = "";
                            int shipper = 0;
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    idChiTietDonHang = jsonObject.getInt("idChiTietDonHang");
                                    idDonHang = jsonObject.getInt("idDonHang");
                                    idSanPham = jsonObject.getInt("idSanPham");
                                    tenSanPham = jsonObject.getString("tenSanPham");
                                    hinhSanPham = jsonObject.getString("hinhSanPham");
                                    baoHanh = jsonObject.getInt("baoHanh");
                                    soLuong = jsonObject.getInt("soLuong");
                                    giaTong = jsonObject.getInt("gia");
                                    xacNhan = jsonObject.getString("xacNhan");
                                    daNhan = jsonObject.getString("daNhan");
                                    idAccount = jsonObject.getInt("idAccount");
                                    hoTen = jsonObject.getString("hoTen");
                                    soDienThoai = jsonObject.getString("soDienThoai");
                                    ngayDatHang = jsonObject.getString("ngayDatHang");
                                    ngayNhanHang = jsonObject.getString("ngayNhanHang");
                                    shipper = jsonObject.getInt("shipper");
                                    listChiTietDonHang.add(new ChiTietDonHang(idChiTietDonHang, idDonHang, idSanPham, tenSanPham, hinhSanPham, baoHanh, soLuong, giaTong, xacNhan, daNhan, idAccount, hoTen, soDienThoai, ngayDatHang, ngayNhanHang, shipper));
                                    donHangAdminAdapter.notifyDataSetChanged();
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
                } else {
                    for (int i = 0; i < listChiTietDonHang.size(); i++) {
                        if (!listChiTietDonHang.get(i).getSoDienThoai().contains(binding.edtTimKiem.getText().toString().toLowerCase().trim())) {
                            listChiTietDonHang.remove(i);
                        }
                    }
                    donHangAdminAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void GetAllChiTietDonHang() {
        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.urlGetAllChiTietDonHangAdmin, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                int idChiTietDonHang = 0;
                int idDonHang = 0;
                int idSanPham = 0;
                String tenSanPham = "";
                String hinhSanPham = "";
                int baoHanh = 0;
                int soLuong = 0;
                int giaTong = 0;
                String xacNhan = "";
                String daNhan = "";
                int idAccount = 0;
                String hoTen = "";
                String soDienThoai = "";
                String ngayDatHang = "";
                String ngayNhanHang = "";
                int shipper = 0;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        idChiTietDonHang = jsonObject.getInt("idChiTietDonHang");
                        idDonHang = jsonObject.getInt("idDonHang");
                        idSanPham = jsonObject.getInt("idSanPham");
                        tenSanPham = jsonObject.getString("tenSanPham");
                        hinhSanPham = jsonObject.getString("hinhSanPham");
                        baoHanh = jsonObject.getInt("baoHanh");
                        soLuong = jsonObject.getInt("soLuong");
                        giaTong = jsonObject.getInt("gia");
                        xacNhan = jsonObject.getString("xacNhan");
                        daNhan = jsonObject.getString("daNhan");
                        idAccount = jsonObject.getInt("idAccount");
                        hoTen = jsonObject.getString("hoTen");
                        soDienThoai = jsonObject.getString("soDienThoai");
                        ngayDatHang = jsonObject.getString("ngayDatHang");
                        ngayNhanHang = jsonObject.getString("ngayNhanHang");
                        shipper = jsonObject.getInt("shipper");
                        listChiTietDonHang.add(new ChiTietDonHang(idChiTietDonHang, idDonHang, idSanPham, tenSanPham, hinhSanPham, baoHanh, soLuong, giaTong, xacNhan, daNhan, idAccount, hoTen, soDienThoai, ngayDatHang, ngayNhanHang, shipper));
                        donHangAdminAdapter.notifyDataSetChanged();
                        binding.progressBar.setVisibility(View.GONE);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (listChiTietDonHang.size() <= 0)
                    binding.progressBar.setVisibility(View.GONE);
                if (listChiTietDonHang.size() <= 0)
                    binding.tvEmpty.setVisibility(View.VISIBLE);
                else
                    binding.progressBar.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void HienThiRecyclerView() {
        listChiTietDonHang = new ArrayList<>();
        donHangAdminAdapter = new DonHangAdminAdapter(getContext(), listChiTietDonHang);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1, RecyclerView.VERTICAL, false));
        binding.recyclerView.setAdapter(donHangAdminAdapter);
    }
}
