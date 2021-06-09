package com.example.laptop88.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;

import com.example.laptop88.R;
import com.example.laptop88.databinding.ActivityBaoHanhHauMaiBinding;
import com.example.laptop88.ultil.CheckConnection;

public class BaoHanhHauMai extends AppCompatActivity {
    ActivityBaoHanhHauMaiBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_bao_hanh_hau_mai);
        if (CheckConnection.haveNetWorkConnection(getApplicationContext())) {
            ActionBar();
            //SetTextLink();
            CatchOnLLMuaHangTuXa();
            CatchOnLLDatHangSP();
            CatchOnLLChinhSachBaoHanh();
            CatchOnLLHuongDanBaoHanh();
            CatchOnLLChinhSachDoiTra();
            CatchOnLLHauMaiSauBanHang();
        } else {
            CheckConnection.ShowToast_Short(getApplicationContext(), "Hãy kiểm tra lại kết nối");
        }
    }

    private void CatchOnLLMuaHangTuXa() {
        binding.llMuaHangTuXa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.imgArrowDown.getVisibility() == View.VISIBLE) {
                    binding.tvNDMuaHangTuXa.setVisibility(View.VISIBLE);
                    binding.imgArrowDown.setVisibility(View.GONE);
                    binding.imgArrowUp.setVisibility(View.VISIBLE);
                } else if (binding.imgArrowUp.getVisibility() == View.VISIBLE) {
                    binding.tvNDMuaHangTuXa.setVisibility(View.GONE);
                    binding.imgArrowDown.setVisibility(View.VISIBLE);
                    binding.imgArrowUp.setVisibility(View.GONE);
                }
            }
        });
    }

    private void CatchOnLLDatHangSP() {
        binding.llDatHangSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.imgArrowDown1.getVisibility() == View.VISIBLE) {
                    binding.tvNDDatHangSP.setVisibility(View.VISIBLE);
                    binding.imgArrowDown1.setVisibility(View.GONE);
                    binding.imgArrowUp1.setVisibility(View.VISIBLE);
                } else if (binding.imgArrowUp1.getVisibility() == View.VISIBLE) {
                    binding.tvNDDatHangSP.setVisibility(View.GONE);
                    binding.imgArrowDown1.setVisibility(View.VISIBLE);
                    binding.imgArrowUp1.setVisibility(View.GONE);
                }
            }
        });
    }

    private void CatchOnLLChinhSachBaoHanh() {
        binding.llChinhSachBaoHanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.imgArrowDown2.getVisibility() == View.VISIBLE) {
                    binding.tvNDChinhSachBaoHanh.setVisibility(View.VISIBLE);
                    binding.imgArrowDown2.setVisibility(View.GONE);
                    binding.imgArrowUp2.setVisibility(View.VISIBLE);
                } else if (binding.imgArrowUp2.getVisibility() == View.VISIBLE) {
                    binding.tvNDChinhSachBaoHanh.setVisibility(View.GONE);
                    binding.imgArrowDown2.setVisibility(View.VISIBLE);
                    binding.imgArrowUp2.setVisibility(View.GONE);
                }
            }
        });
    }

    private void CatchOnLLHuongDanBaoHanh() {
        binding.llHuongDanBaoHanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.imgArrowDown3.getVisibility() == View.VISIBLE) {
                    binding.tvNDHuongDanBaoHanh.setVisibility(View.VISIBLE);
                    binding.imgArrowDown3.setVisibility(View.GONE);
                    binding.imgArrowUp3.setVisibility(View.VISIBLE);
                } else if (binding.imgArrowUp3.getVisibility() == View.VISIBLE) {
                    binding.tvNDHuongDanBaoHanh.setVisibility(View.GONE);
                    binding.imgArrowDown3.setVisibility(View.VISIBLE);
                    binding.imgArrowUp3.setVisibility(View.GONE);
                }
            }
        });
    }

    private void CatchOnLLChinhSachDoiTra() {
        binding.llChinhSachDoiTra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.imgArrowDown4.getVisibility() == View.VISIBLE) {
                    binding.tvNDChinhSachDoiTra.setVisibility(View.VISIBLE);
                    binding.imgArrowDown4.setVisibility(View.GONE);
                    binding.imgArrowUp4.setVisibility(View.VISIBLE);
                } else if (binding.imgArrowUp4.getVisibility() == View.VISIBLE) {
                    binding.tvNDChinhSachDoiTra.setVisibility(View.GONE);
                    binding.imgArrowDown4.setVisibility(View.VISIBLE);
                    binding.imgArrowUp4.setVisibility(View.GONE);
                }
            }
        });
    }

    private void CatchOnLLHauMaiSauBanHang() {
        binding.llHauMaiSauBanHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.imgArrowDown5.getVisibility() == View.VISIBLE) {
                    binding.tvNDHauMaiSauBanHang.setVisibility(View.VISIBLE);
                    binding.imgArrowDown5.setVisibility(View.GONE);
                    binding.imgArrowUp5.setVisibility(View.VISIBLE);
                } else if (binding.imgArrowUp5.getVisibility() == View.VISIBLE) {
                    binding.tvNDHauMaiSauBanHang.setVisibility(View.GONE);
                    binding.imgArrowDown5.setVisibility(View.VISIBLE);
                    binding.imgArrowUp5.setVisibility(View.GONE);
                }
            }
        });
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

    private void SetTextLink() {
        //binding.tvMuaHangTuXaCOD.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
