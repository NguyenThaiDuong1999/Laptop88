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
import com.example.laptop88.adapter.SanPhamDaMuaAdapter;
import com.example.laptop88.databinding.ActivitySanPhamDaMuaBinding;
import com.example.laptop88.model.SanPham;

public class SanPhamDaMua extends AppCompatActivity {
    ActivitySanPhamDaMuaBinding binding;
    SanPhamDaMuaAdapter sanPhamDaMuaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_san_pham_da_mua);
        SetDefaultLayout();
        HienThiRecyclerView();
        Toolbar();
        CatchOnItemListSPDaMua();
    }

    private void SetDefaultLayout() {
        if(MainActivity.listDaMua.size() <= 0){
            binding.tvEmpty.setVisibility(View.VISIBLE);
        }else{
            binding.tvEmpty.setVisibility(View.GONE);
        }
    }

    private void CatchOnItemListSPDaMua() {
        sanPhamDaMuaAdapter.SetOnClickSanPham(new IOnClickSanPham() {
            @Override
            public void OnClickSanPham(SanPham sanPham) {
                Intent intent = new Intent(SanPhamDaMua.this, ChiTietSanPham.class);
                intent.putExtra("chiTietSanPham", sanPham);
                startActivity(intent);
            }
        });
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

    private void HienThiRecyclerView() {
        sanPhamDaMuaAdapter = new SanPhamDaMuaAdapter(getApplicationContext(), MainActivity.listDaMua);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1, RecyclerView.VERTICAL, false));
        binding.recyclerView.setAdapter(sanPhamDaMuaAdapter);
    }
}
