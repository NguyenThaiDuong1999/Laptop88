package com.example.laptop88.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

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
import com.example.laptop88.databinding.FragmentThemSanPhamBinding;
import com.example.laptop88.ultil.CheckConnection;
import com.example.laptop88.ultil.Server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class ThemSanPham extends Fragment {
    public static String TAG = "ThemSanPham";
    FragmentThemSanPhamBinding binding;
    Bitmap bitmap;

    public static ThemSanPham newInstance() {

        Bundle args = new Bundle();

        ThemSanPham fragment = new ThemSanPham();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_them_san_pham, container, false);
        SetDefaultLayout();
        CatchOnButtonThem();
        CatchOnButtonReset();
        CatchOnLLChonAnh();
        return binding.getRoot();
    }

    private void CatchOnLLChonAnh() {
        binding.llChonAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chonFileAnh();
            }
        });
    }

    private void CatchOnButtonThem() {
        binding.btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.urlThemSanPham, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("Success")) {
                            CheckConnection.ShowToast_Short(getContext(), "Thêm sản phẩm thành công!");
                        } else {
                            CheckConnection.ShowToast_Short(getContext(), "Insert Fail!");
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
                        param.put("tenSanPham", String.valueOf(binding.edtTenSanPham.getText()));
                        param.put("gia", String.valueOf(binding.edtGia.getText()));
                        param.put("hinhSanPham", getStringImage(bitmap));
                        param.put("moTa", String.valueOf(binding.edtMoTa.getText()));
                        String hang = binding.spinnerIDHang.getSelectedItem().toString();
                        final String idHang = hang.substring(0, 1);
                        param.put("idHang", idHang);
                        param.put("chuyenDung", binding.spinnerChuyenDung.getSelectedItem().toString());
                        String loaiSP = binding.spinnerIDLoaiSP.getSelectedItem().toString();
                        final String idLoaiSanPham = loaiSP.substring(0, 1);
                        param.put("idLoaiSanPham", idLoaiSanPham);
                        param.put("baoHanh", String.valueOf(binding.edtBaoHanh.getText()));
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        String date = simpleDateFormat.format(Calendar.getInstance().getTime());
                        param.put("date", date);
                        return param;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });
    }

    private void CatchOnButtonReset() {
        binding.btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.edtBaoHanh.setText("");
                binding.edtMoTa.setText("");
                binding.edtGia.setText("");
                binding.edtTenSanPham.setText("");
            }
        });
    }

    private void SetDefaultLayout(){
        String[] mangChuyenDung = new String[]{"Sinh viên văn phòng", "Gaming đồ họa", "Doanh nhân", ""};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, mangChuyenDung);
        binding.spinnerChuyenDung.setAdapter(arrayAdapter);

        String[] mangIDHang = new String[]{"1 - Dell", "2 - HP", "3 - LG", "4 - MacBook", "5 - Asus", "6 - Acer", "7 - Lenovo", "8 - MSI", "9 - Corsair", "10 - Kingston", "11 - Apacer", "12 - WD", "13 - Intel", "14 - Gigabyte", "15 - Seagate", "16 - Logitech"};
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, mangIDHang);
        binding.spinnerIDHang.setAdapter(arrayAdapter1);

        String[] mangLoaiSP = new String[]{"1 - Laptop mới", "2 - Laptop cũ", "3 - Linh phụ kiện"};
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, mangLoaiSP);
        binding.spinnerIDLoaiSP.setAdapter(arrayAdapter2);
    }

    public void chonFileAnh() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select picture"), 2);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK && data.getData() != null) {
            Uri filepath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filepath);
                binding.imgSanPham.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //getStringImage(bitmap);
        }
    }

    public String getStringImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageByteArray = byteArrayOutputStream.toByteArray();
        String encodeImage = Base64.encodeToString(imageByteArray, Base64.DEFAULT);
        return encodeImage;
    }
}
