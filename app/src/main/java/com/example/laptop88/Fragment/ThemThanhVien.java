package com.example.laptop88.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.laptop88.R;
import com.example.laptop88.databinding.FragmentThemThanhVienBinding;
import com.example.laptop88.model.Account;
import com.example.laptop88.ultil.CheckConnection;
import com.example.laptop88.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ThemThanhVien extends Fragment {
    public static String TAG = "ThemThanhVien";
    FragmentThemThanhVienBinding binding;
    ArrayList<Account> listAccount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_them_thanh_vien,container, false);
        CatchOnButtonThem();
        CatchOnButtonReset();
        SetVisiblePassword();
        return binding.getRoot();
    }

    private void SetVisiblePassword() {
        binding.edtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        binding.edtPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (binding.edtPassword.getRight() - binding.edtPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        if (binding.edtPassword.getInputType() == InputType.TYPE_CLASS_TEXT) {
                            binding.edtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            binding.edtPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_vpn_key_black_24dp, 0, R.drawable.ic_visibility_black_24dp, 0);
                        }else{
                            binding.edtPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                            binding.edtPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_vpn_key_black_24dp, 0, R.drawable.ic_visibility_off_black_24dp, 0);
                        }
                        return true;
                    }
                }
                return false;
            }
        });
    }

    private void CatchOnButtonReset() {
        binding.btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.edtUsername.setText("");
                binding.edtPassword.setText("");
                binding.edtHoTen.setText("");
                binding.edtSDT.setText("");
                binding.edtEmail.setText("");
                binding.edtDiaChi.setText("");
            }
        });
    }

    private void CatchOnButtonThem() {
        binding.btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //validate fields
                if (binding.edtUsername.getText().toString().trim().length() > 0 && binding.edtPassword.getText().toString().trim().length() > 0 && binding.edtSDT.getText().toString().trim().length() > 0 && binding.edtEmail.getText().toString().trim().length() > 0) {
                    if (binding.edtUsername.getText().toString().trim().length() < 6 || binding.edtPassword.getText().toString().trim().length() < 6) {
                        CheckConnection.ShowToast_Short(getContext(), "Độ dài Username và Password phải > 6!");
                        return;
                    }
                    if (binding.edtSDT.getText().toString().trim().length() < 10) {
                        CheckConnection.ShowToast_Short(getContext(), "Độ dài Số Điện Thoại phải >= 10!");
                        return;
                    }
                    if (binding.edtEmail.getText().toString().trim().length() < 11) {
                        CheckConnection.ShowToast_Short(getContext(), "Độ dài Email phải >= 11!");
                        return;
                    }
                }
                if (binding.edtUsername.getText().toString().trim().isEmpty() || binding.edtPassword.getText().toString().trim().isEmpty() || binding.edtHoTen.getText().toString().trim().isEmpty() || binding.edtSDT.getText().toString().trim().isEmpty() || binding.edtEmail.getText().toString().trim().isEmpty() || binding.edtDiaChi.getText().toString().trim().isEmpty()) {
                    CheckConnection.ShowToast_Short(getContext(), "Hãy nhập đủ thông tin các trường!");
                    return;
                } else {
                    listAccount = new ArrayList<>();
                    RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                    JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.urlAccount, new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            int idAccount = 0;
                            String usernameGet = "";
                            String passwordGet = "";
                            String hoTenGet = "";
                            String soDienThoaiGet = "";
                            String emailGet = "";
                            String diaChiGet = "";
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    idAccount = jsonObject.getInt("idAccount");
                                    usernameGet = jsonObject.getString("username");
                                    passwordGet = jsonObject.getString("password");
                                    hoTenGet = jsonObject.getString("hoTen");
                                    soDienThoaiGet = jsonObject.getString("soDienThoai");
                                    emailGet = jsonObject.getString("email");
                                    diaChiGet = jsonObject.getString("diaChi");
                                    listAccount.add(new Account(idAccount, usernameGet, passwordGet, hoTenGet, soDienThoaiGet, emailGet, diaChiGet));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            final String username = binding.edtUsername.getText().toString().trim();
                            final String password = binding.edtPassword.getText().toString().trim();
                            final String hoTen = binding.edtHoTen.getText().toString().trim();
                            final String soDienThoai = binding.edtSDT.getText().toString().trim();
                            final String email = binding.edtEmail.getText().toString().trim();
                            final String diaChi = binding.edtDiaChi.getText().toString().trim();
                            boolean existsAccount = false;
                            for (int i = 0; i < listAccount.size(); i++) {
                                if (username.equals(listAccount.get(i).getUsername())) {
                                    CheckConnection.ShowToast_Short(getContext(), "Tài khoản đã tồn tại!");
                                    existsAccount = true;
                                }
                            }
                            if (existsAccount == false) {
                                RequestQueue requestQueue1 = Volley.newRequestQueue(getContext());
                                StringRequest stringRequest1 = new StringRequest(Request.Method.POST, Server.urlDangKiTaiKhoan, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if(response != null){
                                            CheckConnection.ShowToast_Short(getContext(), "Thêm tài khoản thành công!");
                                        }else{
                                            CheckConnection.ShowToast_Short(getContext(), "Đăng kí thất bại!");
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
                                        HashMap<String, String> param = new HashMap<>();
                                        param.put("username", username);
                                        param.put("password", password);
                                        param.put("hoTen", hoTen);
                                        param.put("soDienThoai", soDienThoai);
                                        param.put("email", email);
                                        param.put("diaChi", diaChi);
                                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                                        String ngayDangKy = simpleDateFormat.format(Calendar.getInstance().getTime());
                                        param.put("ngayDangKy", ngayDangKy);
                                        int idLoaiTaiKhoan = 0;
                                        if(binding.rbUser.isChecked()){
                                            idLoaiTaiKhoan = 2;
                                        }else if(binding.rbStaff.isChecked()){
                                            idLoaiTaiKhoan = 3;
                                        }
                                        param.put("idLoaiTaiKhoan", String.valueOf(idLoaiTaiKhoan));
                                        return param;
                                    }
                                };
                                requestQueue1.add(stringRequest1);
                            } else {
                                CheckConnection.ShowToast_Short(getContext(), "Tài khoản đã tồn tại!");
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                    requestQueue.add(jsonArrayRequest);
                }
            }
        });
    }
}
