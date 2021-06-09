package com.example.laptop88.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
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
import com.example.laptop88.databinding.ActivityThongTinCaNhanBinding;
import com.example.laptop88.model.Account;
import com.example.laptop88.ultil.CheckConnection;
import com.example.laptop88.ultil.Server;

import java.util.HashMap;
import java.util.Map;

public class ThongTinCaNhan extends AppCompatActivity {
    ActivityThongTinCaNhanBinding binding;
    int idAccount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_thong_tin_ca_nhan);
        SetDefaultLayout();
        CatchOnButtonUpdate();
        CatchOnButtonQuayLai();
        SetVisiblePassword();
    }

    private void SetVisiblePassword() {
        binding.edtOldPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (binding.edtOldPassword.getRight() - binding.edtOldPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        if (binding.edtOldPassword.getInputType() == InputType.TYPE_CLASS_TEXT) {
                            binding.edtOldPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            binding.edtOldPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_vpn_key_black_24dp, 0, R.drawable.ic_visibility_black_24dp, 0);
                        }else{
                            binding.edtOldPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                            binding.edtOldPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_vpn_key_black_24dp, 0, R.drawable.ic_visibility_off_black_24dp, 0);
                        }
                        return true;
                    }
                }
                return false;
            }
        });
    }

    private void CatchOnButtonQuayLai() {
        binding.btnQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void UpdateThongTinCaNhan() {
        final String username = String.valueOf(binding.edtUsername.getText());
        final String password = String.valueOf(binding.edtPassword.getText());
        final String hoTen = String.valueOf(binding.edtHoTen.getText());
        final String soDienThoai = String.valueOf(binding.edtSDT.getText());
        final String email = String.valueOf(binding.edtEmail.getText());
        final String diaChi = String.valueOf(binding.edtDiaChi.getText());
        //update
        if (DangNhap.currentAccount != null) {
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
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
                    param.put("idAccount", String.valueOf(DangNhap.currentAccount.getIdAccount()));
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
        }
    }

    private void CatchOnButtonUpdate() {
        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.edtOldPassword.setVisibility(View.VISIBLE);
                if (binding.edtOldPassword.getText().toString().trim().equals("")) {
                    CheckConnection.ShowToast_Short(getApplicationContext(), "Nhập lại mật khẩu cũ!");
                    binding.edtOldPassword.requestFocus();
                    return;
                }
                if (!binding.edtOldPassword.getText().toString().trim().equals(DangNhap.currentAccount.getPassword())) {
                    CheckConnection.ShowToast_Short(getApplicationContext(), "Mật khẩu cũ không đúng!");
                    binding.edtOldPassword.requestFocus();
                    return;
                }
                if (DangNhap.currentAccount != null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ThongTinCaNhan.this);
                    builder.setTitle("Xác nhận update thông tin cá nhân");
                    builder.setMessage("Bạn có chắc muốn update thông tin cá nhân?");
                    builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //final int idAccount = DangNhap.currentAccount.getIdAccount();
                            final String username = String.valueOf(binding.edtUsername.getText());
                            final String password = String.valueOf(binding.edtPassword.getText());
                            final String hoTen = String.valueOf(binding.edtHoTen.getText());
                            final String soDienThoai = String.valueOf(binding.edtSDT.getText());
                            final String email = String.valueOf(binding.edtEmail.getText());
                            final String diaChi = String.valueOf(binding.edtDiaChi.getText());
                            //update
                            if (DangNhap.currentAccount != null) {
                                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
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
                                DangNhap.currentAccount = null;
                                DangNhap.checkDangNhap = false;
                                MainActivity.tvAccount.setText("Chưa đăng nhập");
                                MainActivity.btnDangXuat.setVisibility(View.INVISIBLE);
                                MainActivity.btnDangNhap.setVisibility(View.VISIBLE);
                                MainActivity.btnToiTrangQuanTri.setVisibility(View.GONE);
                                MainActivity.listGioHang.clear();
                                MainActivity.tvSoSPGioHang.setText("0");
                                MainActivity.listDaXem.clear();
                                MainActivity.listYeuThich.clear();
                                CheckConnection.ShowToast_Short(getApplicationContext(), "Update thành công. Vui lòng đăng nhập lại để sử dụng các chức năng!");
                                Intent intent = new Intent(ThongTinCaNhan.this, MainActivity.class);
                                startActivity(intent);
                            }
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            CheckConnection.ShowToast_Short(getApplicationContext(), "Đã hủy update!");
                        }
                    });
                    builder.show();
                }
            }
        });
    }

    private void SetDefaultLayout() {
        idAccount = DangNhap.currentAccount.getIdAccount();
        binding.edtUsername.setText(DangNhap.currentAccount.getUsername());
        binding.edtPassword.setText(DangNhap.currentAccount.getPassword());
        binding.edtHoTen.setText(DangNhap.currentAccount.getHoTen());
        binding.edtSDT.setText(DangNhap.currentAccount.getSoDienThoai());
        binding.edtEmail.setText(DangNhap.currentAccount.getEmail());
        binding.edtDiaChi.setText(DangNhap.currentAccount.getDiaChi());
        binding.edtOldPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        if(DangNhap.currentAccount.getUsername().equals("Admin")){
            binding.edtUsername.setVisibility(View.GONE);
        }
    }
}
