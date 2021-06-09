package com.example.laptop88.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.laptop88.Interface.IOnClickSanPham;
import com.example.laptop88.R;
import com.example.laptop88.adapter.LaptopMoiAdapter;
import com.example.laptop88.databinding.ActivitySanPhamDaXemBinding;
import com.example.laptop88.model.SanPham;

public class SanPhamDaXem extends AppCompatActivity {
    ActivitySanPhamDaXemBinding binding;
    LaptopMoiAdapter laptopMoiAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_san_pham_da_xem);
        //
        SetDefaultLayout();
        HienThiRecyclerView();
        Toolbar();
        CatchOnItemListSPDaXem();
    }

    private void SetDefaultLayout() {
        if(MainActivity.listDaXem.size() <= 0){
            binding.tvEmpty.setVisibility(View.VISIBLE);
        }else{
            binding.tvEmpty.setVisibility(View.GONE);
        }
    }

    private void CatchOnItemListSPDaXem() {
        laptopMoiAdapter.SetOnClickSanPham(new IOnClickSanPham() {
            @Override
            public void OnClickSanPham(SanPham sanPham) {
                Intent intent = new Intent(SanPhamDaXem.this, ChiTietSanPham.class);
                intent.putExtra("chiTietSanPham", sanPham);
                startActivity(intent);
            }
        });
    }

    private void HienThiRecyclerView() {
        laptopMoiAdapter = new LaptopMoiAdapter(getApplicationContext(),MainActivity.listDaXem);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1, RecyclerView.VERTICAL, false));
        binding.recyclerView.setAdapter(laptopMoiAdapter);
    }

    private void Toolbar() {
        setSupportActionBar(binding.toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
