package com.example.laptop88.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.laptop88.R;
import com.example.laptop88.databinding.FragmentSuaThanhVienBinding;
import com.example.laptop88.model.Account;
import com.example.laptop88.ultil.CheckConnection;
import com.example.laptop88.ultil.Server;

import java.util.HashMap;
import java.util.Map;

public class SuaThanhVien extends Fragment {
    FragmentSuaThanhVienBinding binding;
    int idAccount = 0;
    public static SuaThanhVien newInstance(Account account) {

        Bundle args = new Bundle();
        SuaThanhVien fragment = new SuaThanhVien();
        args.putSerializable("idAccount", account.getIdAccount());
        args.putSerializable("username", account.getUsername());
        args.putSerializable("password", account.getPassword());
        args.putSerializable("hoTen", account.getHoTen());
        args.putSerializable("soDienThoai", account.getSoDienThoai());
        args.putSerializable("email", account.getEmail());
        args.putSerializable("diaChi", account.getDiaChi());
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sua_thanh_vien, container, false);
        SetDefaultLayout();
        CatchOnButtonUpdate();
        return binding.getRoot();
    }

    private void CatchOnButtonUpdate() {
        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idAccount = (int) getArguments().getSerializable("idAccount");
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Xác nhận update thông tin thành viên!");
                builder.setMessage("Bạn có chắc muốn thay đổi thông tin thành viên này?");
                builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String username = String.valueOf(binding.edtUsername.getText());
                        final String password = String.valueOf(binding.edtPassword.getText());
                        final String hoTen = String.valueOf(binding.edtHoTen.getText());
                        final String soDienThoai = String.valueOf(binding.edtSDT.getText());
                        final String email = String.valueOf(binding.edtEmail.getText());
                        final String diaChi = String.valueOf(binding.edtDiaChi.getText());
                        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.urlUpdateThongTinCaNhan, new Response.Listener<String>() {
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
                                HashMap<String, String> param = new HashMap<>();
                                param.put("idAccount", String.valueOf(idAccount));
                                param.put("username", username);
                                param.put("password", password);
                                param.put("hoTen", hoTen);
                                param.put("soDienThoai", soDienThoai);
                                param.put("email", email);
                                param.put("diaChi", diaChi);
                                return param;
                            }
                        };
                        requestQueue.add(stringRequest);
                        CheckConnection.ShowToast_Short(getContext(), "Update thành công!");
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CheckConnection.ShowToast_Short(getContext(), "Hủy update!");
                    }
                });
                builder.show();
            }
        });
    }

    private void SetDefaultLayout() {
        String username = (String) getArguments().getSerializable("username");
        String password = (String) getArguments().getSerializable("password");
        String hoTen = (String) getArguments().getSerializable("hoTen");
        String soDienThoai = (String) getArguments().getSerializable("soDienThoai");
        String email = (String) getArguments().getSerializable("email");
        String diaChi = (String) getArguments().getSerializable("diaChi");
        binding.edtUsername.setText(username);
        binding.edtPassword.setText(password);
        binding.edtHoTen.setText(hoTen);
        binding.edtSDT.setText(soDienThoai);
        binding.edtEmail.setText(email);
        binding.edtDiaChi.setText(diaChi);
    }
}
