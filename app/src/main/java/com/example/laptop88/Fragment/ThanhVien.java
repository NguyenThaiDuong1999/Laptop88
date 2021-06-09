package com.example.laptop88.Fragment;

import android.content.Context;
import android.content.Intent;
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
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.laptop88.Interface.IOnClickSuaAccount;
import com.example.laptop88.R;
import com.example.laptop88.activity.DangNhap;
import com.example.laptop88.adapter.ThanhVienAdminAdapter;
import com.example.laptop88.databinding.FragmentThanhVienBinding;
import com.example.laptop88.model.Account;
import com.example.laptop88.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ThanhVien extends Fragment {
    FragmentThanhVienBinding binding;
    public static EditText edtTimKiem;
    public static ArrayList<Account> listAccount;
    public static ThanhVienAdminAdapter thanhVienAdminAdapter;
    private IOnClickSuaAccount iOnClickSuaAccount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_thanh_vien, container, false);
        edtTimKiem = binding.edtTimKiem;
        HienThiListAccount();
        GetAllAccount();
        CatchOnButtonThemThanhVien();
        CatchOnButtonSua();
        return binding.getRoot();
    }

    private void CatchOnButtonSua() {
        thanhVienAdminAdapter.SetOnClickSuaAccount(new IOnClickSuaAccount() {
            @Override
            public void OnClickAccount(Account account) {
                iOnClickSuaAccount.OnClickAccount(account);
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof IOnClickSuaAccount){
            iOnClickSuaAccount = (IOnClickSuaAccount) context;
        }else{
            throw new RuntimeException(context.toString() + " must implement");
        }
    }

    private void CatchOnButtonThemThanhVien() {
        binding.btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                ThemThanhVien themThanhVien = new ThemThanhVien();
                fragmentTransaction.replace(R.id.container, themThanhVien);
                fragmentTransaction.addToBackStack("ThemThanhVien");
                fragmentTransaction.commit();
            }
        });
    }

    private void GetAllAccount() {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.urlAccount, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                int idAccount = 0;
                String usernameGet = "";
                String passwordGet = "";
                String hoTen = "";
                String soDienThoai = "";
                String email = "";
                String diaChi = "";
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        idAccount = jsonObject.getInt("idAccount");
                        usernameGet = jsonObject.getString("username");
                        passwordGet = jsonObject.getString("password");
                        hoTen = jsonObject.getString("hoTen");
                        soDienThoai = jsonObject.getString("soDienThoai");
                        email = jsonObject.getString("email");
                        diaChi = jsonObject.getString("diaChi");
                        listAccount.add(new Account(idAccount, usernameGet, passwordGet, hoTen, soDienThoai, email, diaChi));
                        thanhVienAdminAdapter.notifyDataSetChanged();
                        binding.progressBar.setVisibility(View.GONE);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                binding.edtTimKiem.setHint("Tìm kiếm trong " + listAccount.size() + " thành viên...");
                binding.edtTimKiem.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (binding.edtTimKiem.getText().toString().trim().length() <= 0) {
                            listAccount.clear();
                            HienThiListAccount();
                            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.urlAccount, new Response.Listener<JSONArray>() {
                                @Override
                                public void onResponse(JSONArray response) {
                                    int idAccount = 0;
                                    String usernameGet = "";
                                    String passwordGet = "";
                                    String hoTen = "";
                                    String soDienThoai = "";
                                    String email = "";
                                    String diaChi = "";
                                    for (int i = 0; i < response.length(); i++) {
                                        try {
                                            JSONObject jsonObject = response.getJSONObject(i);
                                            idAccount = jsonObject.getInt("idAccount");
                                            usernameGet = jsonObject.getString("username");
                                            passwordGet = jsonObject.getString("password");
                                            hoTen = jsonObject.getString("hoTen");
                                            soDienThoai = jsonObject.getString("soDienThoai");
                                            email = jsonObject.getString("email");
                                            diaChi = jsonObject.getString("diaChi");
                                            listAccount.add(new Account(idAccount, usernameGet, passwordGet, hoTen, soDienThoai, email, diaChi));
                                            thanhVienAdminAdapter.notifyDataSetChanged();
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
                            for (int i = 0; i < listAccount.size(); i++) {
                                if (!listAccount.get(i).getUsername().toLowerCase().contains(binding.edtTimKiem.getText().toString().toLowerCase().trim())) {
                                    listAccount.remove(i);
                                }
                            }
                            thanhVienAdminAdapter.notifyDataSetChanged();
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

    private void HienThiListAccount() {
        listAccount = new ArrayList<>();
        thanhVienAdminAdapter = new ThanhVienAdminAdapter(listAccount, getContext());
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1, RecyclerView.VERTICAL, false));
        binding.recyclerView.setAdapter(thanhVienAdminAdapter);
    }
}
