package com.example.laptop88.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.laptop88.Fragment.DonHang;
import com.example.laptop88.Fragment.SanPham;
import com.example.laptop88.Fragment.SuaSanPham;
import com.example.laptop88.Fragment.SuaThanhVien;
import com.example.laptop88.Fragment.ThanhVien;
import com.example.laptop88.Interface.IOnClickSanPham;
import com.example.laptop88.Interface.IOnClickSuaAccount;
import com.example.laptop88.R;
import com.example.laptop88.databinding.ActivityAdminBinding;
import com.example.laptop88.model.Account;
import com.example.laptop88.ultil.CheckConnection;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Admin extends AppCompatActivity implements IOnClickSuaAccount, IOnClickSanPham {
    private static final String TAG = "Admin";
    ActivityAdminBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_admin);
        //ActionBar();
        CatchOnButtonDangXuat();
        getFragment(new ThanhVien());
        binding.bottomNavigation.setOnNavigationItemSelectedListener(navListener);
    }

    private void CatchOnButtonDangXuat() {
        binding.tvDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void getFragment(Fragment fragment) {
        try {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "getFragment: " + e.getMessage());
        }
    }

    private void ActionBar() {
        setSupportActionBar(binding.toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.menuThanhVien:
                    getFragment(new ThanhVien());
                    break;
                case R.id.menuSanPham:
                    getFragment(new SanPham());
                    break;
                case R.id.menuDonHang:
                    getFragment(new DonHang());
                    break;
            }
            return true;
        }
    };

    @Override
    public void OnClickAccount(Account account) {
        getFragment(SuaThanhVien.newInstance(account));
    }

    @Override
    public void OnClickSanPham(com.example.laptop88.model.SanPham sanPham) {
        getFragment(SuaSanPham.newInstance(sanPham));
    }
}
