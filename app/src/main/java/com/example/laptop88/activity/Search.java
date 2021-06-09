package com.example.laptop88.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.laptop88.Interface.IOnClickSanPham;
import com.example.laptop88.R;
import com.example.laptop88.adapter.HistorySearchAdapter;
import com.example.laptop88.adapter.LaptopMoiAdapter;
import com.example.laptop88.databinding.ActivitySearchBinding;
import com.example.laptop88.model.HistorySearch;
import com.example.laptop88.model.SanPham;
import com.example.laptop88.ultil.CheckConnection;
import com.example.laptop88.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Search extends AppCompatActivity {
    ActivitySearchBinding binding;
    //
    ArrayList<HistorySearch> listHistorySearch;
    HistorySearchAdapter historySearchAdapter;
    //
    ArrayList<SanPham> listSanPham;
    LaptopMoiAdapter laptopMoiAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        binding.progressBar.setVisibility(View.GONE);
        binding.listView.setVisibility(View.VISIBLE);
        ToolBar();
        CatchOnSearchIconOnKeyBoard();
        FocusEditText();
        CatchOnItemListHistorySearch();
        CatchOnItemListHistorySearchToRemove();
    }

    private void CatchOnItemListHistorySearchToRemove() {
        binding.listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Search.this);
                builder.setTitle("Xác nhận xóa lịch sử tìm kiếm!");
                builder.setMessage("Bạn có chắc muốn xóa lịch sử tìm kiếm này?");
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (DangNhap.currentAccount != null) {
                            if (listHistorySearch.size() > 0) {
                                DeleteHistorySearchWithIDAccountAndIDHistorySearch(String.valueOf(DangNhap.currentAccount.getIdAccount()), String.valueOf(listHistorySearch.get(position).getIdHistorySearch()));
                                listHistorySearch.remove(position);
                                historySearchAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CheckConnection.ShowToast_Short(getApplicationContext(), "Đã hủy xóa!");
                    }
                });
                builder.show();
                return false;
            }
        });
    }

    private void DeleteHistorySearchWithIDAccountAndIDHistorySearch(final String idAccount, final String idHistorySearch) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.urlDeleteHistorySearchWithIDAccountAndIDHistorySearch, new Response.Listener<String>() {
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
                param.put("idAccount", idAccount);
                param.put("idHistorySearch", idHistorySearch);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void CatchOnItemListHistorySearch() {
        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                binding.edtSearch.setText(listHistorySearch.get(position).getSearchContent());
                //Hide listView
                binding.listView.setVisibility(View.GONE);
                //Get all sản phẩm
                binding.progressBar.setVisibility(View.VISIBLE);
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.urlGetAllSanPham, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int idSanPham = 0;
                        String tenSanPham = "";
                        Integer gia = 0;
                        String hinhSanPham = "";
                        String moTa = "";
                        String chuyenDung = "";
                        int idLoaiSanPham = 0;
                        int baoHanh = 0;
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = response.getJSONObject(i);
                                idSanPham = jsonObject.getInt("idSanPham");
                                tenSanPham = jsonObject.getString("tenSanPham");
                                gia = jsonObject.getInt("gia");
                                hinhSanPham = jsonObject.getString("hinhSanPham");
                                moTa = jsonObject.getString("moTa");
                                chuyenDung = jsonObject.getString("chuyenDung");
                                idLoaiSanPham = jsonObject.getInt("idLoaiSanPham");
                                baoHanh = jsonObject.getInt("baoHanh");
                                if (tenSanPham.toLowerCase().contains(binding.edtSearch.getText().toString().toLowerCase().trim()))
                                    listSanPham.add(new SanPham(idSanPham, tenSanPham, gia, hinhSanPham, moTa, chuyenDung, idLoaiSanPham, baoHanh));
                                laptopMoiAdapter.notifyDataSetChanged();
                                binding.progressBar.setVisibility(View.GONE);
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
                //
                binding.recyclerView.setVisibility(View.VISIBLE);
                HienThiRecyclerView();
                CatchOnItemListSearch();
                // hide virtual keyboard
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(binding.edtSearch.getWindowToken(),
                        InputMethodManager.RESULT_UNCHANGED_SHOWN);
            }
        });
    }

    private void CatchOnItemListSearch() {
        laptopMoiAdapter.SetOnClickSanPham(new IOnClickSanPham() {
            @Override
            public void OnClickSanPham(SanPham sanPham) {
                Intent intent = new Intent(Search.this, ChiTietSanPham.class);
                intent.putExtra("chiTietSanPham", sanPham);
                startActivity(intent);
            }
        });
    }

    private void HienThiRecyclerView() {
        listSanPham = new ArrayList<>();
        laptopMoiAdapter = new LaptopMoiAdapter(getApplicationContext(), listSanPham);
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        binding.recyclerView.setAdapter(laptopMoiAdapter);
    }

    private void FocusEditText() {
        if (binding.edtSearch.requestFocus() == true) {
            if (DangNhap.currentAccount != null) {
                binding.listView.setVisibility(View.VISIBLE);
                HienThiListView();
                GetHistorySearchWithIDAccount();
            }
        }
        binding.edtSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DangNhap.currentAccount != null) {
                    binding.recyclerView.setVisibility(View.GONE);
                    binding.listView.setVisibility(View.VISIBLE);
                    HienThiListView();
                    GetHistorySearchWithIDAccount();
                }
            }
        });
    }

    private void GetHistorySearchWithIDAccount() {
        if (DangNhap.currentAccount != null) {
            binding.progressBar.setVisibility(View.VISIBLE);
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.urlGetHistorySearch, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    int idHistorySearch = 0;
                    int idAccount = 0;
                    String searchContent = "";
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            idHistorySearch = jsonObject.getInt("idHistorySearch");
                            idAccount = jsonObject.getInt("idAccount");
                            searchContent = jsonObject.getString("searchContent");
                            listHistorySearch.add(new HistorySearch(idHistorySearch, idAccount, searchContent));
                            historySearchAdapter.notifyDataSetChanged();
                            binding.progressBar.setVisibility(View.GONE);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
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
                    param.put("idAccount", String.valueOf(DangNhap.currentAccount.getIdAccount()));
                    return param;
                }
            };
            requestQueue.add(stringRequest);
        }
    }

    private void HienThiListView() {
        listHistorySearch = new ArrayList<>();
        historySearchAdapter = new HistorySearchAdapter(listHistorySearch, getApplicationContext());
        binding.listView.setAdapter(historySearchAdapter);
    }

    private void CatchOnSearchIconOnKeyBoard() {
        binding.edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // hide virtual keyboard
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(binding.edtSearch.getWindowToken(),
                            InputMethodManager.RESULT_UNCHANGED_SHOWN);
                    //Save search content
                    if (!binding.edtSearch.getText().toString().trim().equals("") && DangNhap.currentAccount != null) {
                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.urlHistorySearch, new Response.Listener<String>() {
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
                                param.put("searchContent", binding.edtSearch.getText().toString().trim());
                                return param;
                            }
                        };
                        requestQueue.add(stringRequest);
                    }
                    //Hide listView
                    binding.listView.setVisibility(View.GONE);
                    //Get all sản phẩm
                    binding.progressBar.setVisibility(View.VISIBLE);
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.urlGetAllSanPham, new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            int idSanPham = 0;
                            String tenSanPham = "";
                            Integer gia = 0;
                            String hinhSanPham = "";
                            String moTa = "";
                            String chuyenDung = "";
                            int idLoaiSanPham = 0;
                            int baoHanh = 0;
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = response.getJSONObject(i);
                                    idSanPham = jsonObject.getInt("idSanPham");
                                    tenSanPham = jsonObject.getString("tenSanPham");
                                    gia = jsonObject.getInt("gia");
                                    hinhSanPham = jsonObject.getString("hinhSanPham");
                                    moTa = jsonObject.getString("moTa");
                                    chuyenDung = jsonObject.getString("chuyenDung");
                                    idLoaiSanPham = jsonObject.getInt("idLoaiSanPham");
                                    baoHanh = jsonObject.getInt("baoHanh");
                                    if (tenSanPham.toLowerCase().contains(binding.edtSearch.getText().toString().toLowerCase().trim()))
                                        listSanPham.add(new SanPham(idSanPham, tenSanPham, gia, hinhSanPham, moTa, chuyenDung, idLoaiSanPham, baoHanh));
                                    laptopMoiAdapter.notifyDataSetChanged();
                                    binding.progressBar.setVisibility(View.GONE);
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
                    //
                    binding.recyclerView.setVisibility(View.VISIBLE);
                    HienThiRecyclerView();
                    CatchOnItemListSearch();
                    return true;
                }
                return false;
            }
        });
    }

    private void ToolBar() {
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
