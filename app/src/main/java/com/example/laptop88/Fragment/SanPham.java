package com.example.laptop88.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.laptop88.Interface.IOnClickSanPham;
import com.example.laptop88.R;
import com.example.laptop88.adapter.SanPhamAdminAdapter;
import com.example.laptop88.databinding.FragmentSanPhamBinding;
import com.example.laptop88.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SanPham extends Fragment {
    FragmentSanPhamBinding binding;
    public static ArrayList<com.example.laptop88.model.SanPham> listSanPham;
    public static SanPhamAdminAdapter sanPhamAdminAdapter;
    public static EditText edtTimKiem;
    private IOnClickSanPham iOnClickSanPham;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_san_pham, container, false);
        edtTimKiem = binding.edtTimKiem;
        HienThiRecyclerView();
        GetAllSanPham();
        CatchOnButtonSua();
        CatchOnButtonThemSanPham();
        return binding.getRoot();
    }

    private void CatchOnButtonThemSanPham() {
        binding.btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container, new ThemSanPham());
                fragmentTransaction.addToBackStack("ThemSanPham");
                fragmentTransaction.commit();
            }
        });
    }

    private void CatchOnButtonSua() {
        sanPhamAdminAdapter.SetIonClickSanPham(new IOnClickSanPham() {
            @Override
            public void OnClickSanPham(com.example.laptop88.model.SanPham sanPham) {
                iOnClickSanPham.OnClickSanPham(sanPham);
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof IOnClickSanPham){
            iOnClickSanPham = (IOnClickSanPham) context;
        }else{
            throw new RuntimeException(context.toString() + " must implement");
        }
    }

    private void HienThiRecyclerView() {
        listSanPham = new ArrayList<>();
        sanPhamAdminAdapter = new SanPhamAdminAdapter(listSanPham, getContext());
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        binding.recyclerView.setAdapter(sanPhamAdminAdapter);
    }

    private void GetAllSanPham() {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
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
                        listSanPham.add(new com.example.laptop88.model.SanPham(idSanPham, tenSanPham, gia, hinhSanPham, moTa, chuyenDung, idLoaiSanPham, baoHanh));
                        sanPhamAdminAdapter.notifyDataSetChanged();
                        binding.progressBar.setVisibility(View.GONE);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                binding.edtTimKiem.setHint("Tìm kiếm trong " + listSanPham.size() + " sản phẩm...");
                binding.edtTimKiem.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (binding.edtTimKiem.getText().toString().trim().length() <= 0) {
                            listSanPham.clear();
                            HienThiRecyclerView();
                            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
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
                                            listSanPham.add(new com.example.laptop88.model.SanPham(idSanPham, tenSanPham, gia, hinhSanPham, moTa, chuyenDung, idLoaiSanPham, baoHanh));
                                            sanPhamAdminAdapter.notifyDataSetChanged();
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
                            for (int i = 0; i < listSanPham.size(); i++) {
                                if (!listSanPham.get(i).getTenSanPham().toLowerCase().contains(binding.edtTimKiem.getText().toString().toLowerCase().trim())) {
                                    listSanPham.remove(listSanPham.get(i));
                                }
                            }
                            sanPhamAdminAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }
}
