package com.example.laptop88.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.PopupMenu;

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
import com.example.laptop88.databinding.FragmentSuaSanPhamBinding;
import com.example.laptop88.model.SanPham;
import com.example.laptop88.ultil.CheckConnection;
import com.example.laptop88.ultil.Server;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class SuaSanPham extends Fragment {
    FragmentSuaSanPhamBinding binding;
    int idSanPham = 0;
    String[] mangChuyenDung = new String[]{"Sinh viên văn phòng", "Gaming đồ họa", "Doanh nhân", ""};
    String[] mangLoaiSP = new String[]{"1 - Laptop mới", "2 - Laptop cũ", "3 - Linh phụ kiện"};
    Bitmap bitmap;

    public static SuaSanPham newInstance(SanPham sanPham) {

        Bundle args = new Bundle();
        SuaSanPham fragment = new SuaSanPham();
        args.putSerializable("idSanPham", sanPham.getIdSanPham());
        args.putSerializable("tenSanPham", sanPham.getTenSanPham());
        args.putSerializable("gia", sanPham.getGia());
        args.putSerializable("hinhSanPham", sanPham.getHinhSanPham());
        args.putSerializable("moTa", sanPham.getMoTa());
        args.putSerializable("chuyenDung", sanPham.getChuyenDung());
        args.putSerializable("idLoaiSanPham", sanPham.getIdLoaiSanPham());
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sua_san_pham, container, false);
        SetDefaultLayout();
        CatchOnButtonUpdate();
        CatchOnImgSanPham();
        return binding.getRoot();
    }

    private void CatchOnImgSanPham() {
        binding.imgSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chonFileAnh();
            }
        });
    }

    private void CatchOnButtonUpdate() {
        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idSanPham = (int) getArguments().getSerializable("idSanPham");
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Xác nhận update thông tin sản phẩm!");
                builder.setMessage("Bạn có chắc muốn thay đổi thông tin sản phẩm này?");
                builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String tenSanPham = String.valueOf(binding.edtTenSanPham.getText());
                        final String gia = String.valueOf(binding.edtGia.getText());
                        //final String hinhSanPham = String.valueOf(binding.edtLinkAnh.getText());
                        final String moTa = String.valueOf(binding.edtMoTa.getText());
                        final String chuyenDung = binding.spinnerChuyenDung.getSelectedItem().toString();
                        //
                        String loaiSP = binding.spinnerLoaiSP.getSelectedItem().toString();
                        final String idLoaiSanPham = loaiSP.substring(0, 1);
                        //
                        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.urlUpdateThongTinSanPham, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(response.equals("Success")){
                                    CheckConnection.ShowToast_Short(getContext(), "Update thành công!");
                                }else{
                                    CheckConnection.ShowToast_Short(getContext(), "Update thất bại!");
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
                                param.put("idSanPham", String.valueOf(idSanPham));
                                param.put("tenSanPham", tenSanPham);
                                param.put("gia", gia);
                                param.put("hinhSanPham", getStringImage(bitmap));
                                param.put("moTa", moTa);
                                param.put("chuyenDung", chuyenDung);
                                param.put("idLoaiSanPham", idLoaiSanPham);
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                                String date = simpleDateFormat.format(Calendar.getInstance().getTime());
                                param.put("date", date);
                                return param;
                            }
                        };
                        requestQueue.add(stringRequest);
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
        String tenSanPham = (String) getArguments().getSerializable("tenSanPham");
        int gia = (int) getArguments().getSerializable("gia");
        String hinhSanPham = (String) getArguments().getSerializable("hinhSanPham");
        String moTa = (String) getArguments().getSerializable("moTa");
        String chuyenDung = (String) getArguments().getSerializable("chuyenDung");
        int idLoaiSanPham = (int) getArguments().getSerializable("idLoaiSanPham");
        binding.edtTenSanPham.setText(tenSanPham);
        binding.edtGia.setText(String.valueOf(gia));
        //binding.edtLinkAnh.setText(hinhSanPham);
        Picasso.with(getContext()).load(hinhSanPham).into(binding.imgSanPham);
        binding.edtMoTa.setText(moTa);
        //binding.edtChuyenDung.setText(chuyenDung);
        //binding.edtIDLoaiSanPham.setText(String.valueOf(idLoaiSanPham));
        //spinner
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, mangChuyenDung);
        binding.spinnerChuyenDung.setAdapter(arrayAdapter);
        //
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, mangLoaiSP);
        binding.spinnerLoaiSP.setAdapter(arrayAdapter1);
        //
        for (int i = 0; i < mangChuyenDung.length; i++) {
            if (mangChuyenDung[i].equals(chuyenDung)) {
                binding.spinnerChuyenDung.setSelection(i);
            }
        }

        for (int i = 0; i < mangLoaiSP.length; i++) {
            if (mangLoaiSP[i].contains(String.valueOf(idLoaiSanPham))) {
                binding.spinnerLoaiSP.setSelection(i);
            }
        }
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
