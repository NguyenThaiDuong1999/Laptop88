package com.example.laptop88.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.laptop88.Fragment.ThanhVien;
import com.example.laptop88.Interface.IOnClickSuaAccount;
import com.example.laptop88.R;
import com.example.laptop88.model.Account;
import com.example.laptop88.ultil.CheckConnection;
import com.example.laptop88.ultil.Server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ThanhVienAdminAdapter extends RecyclerView.Adapter<ThanhVienAdminAdapter.ViewHolder> {
    ArrayList<Account> listAccount;
    Context context;
    IOnClickSuaAccount iOnClickSuaAccount;

    public ThanhVienAdminAdapter(ArrayList<Account> listAccount, Context context) {
        this.listAccount = listAccount;
        this.context = context;
    }

    public void SetOnClickSuaAccount(IOnClickSuaAccount iOnClickSuaAccount){
        this.iOnClickSuaAccount = iOnClickSuaAccount;
    }

    @NonNull
    @Override
    public ThanhVienAdminAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dongthanhvienadmin, null);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ThanhVienAdminAdapter.ViewHolder holder, final int position) {
        final Account account = listAccount.get(position);
        holder.tvIDAccount.setText(String.valueOf(account.getIdAccount()));
        holder.tvUsername.setText(account.getUsername());
        holder.btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!account.getUsername().equals("Admin")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle(String.valueOf(account.getUsername()));
                    builder.setMessage("Bạn có chắc muốn xóa thành viên này?");
                    builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ThanhVien.listAccount.remove(position);
                            ThanhVien.thanhVienAdminAdapter.notifyDataSetChanged();
                            ThanhVien.edtTimKiem.setHint("Tìm kiếm trong " + ThanhVien.listAccount.size() + " thành viên...");
                            //Delete in db
                            RequestQueue requestQueue = Volley.newRequestQueue(context);
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.urlDeleteAccountWithIDAccount, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }){
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    HashMap<String,String> param = new HashMap<>();
                                    param.put("idAccount", String.valueOf(account.getIdAccount()));
                                    return param;
                                }
                            };
                            requestQueue.add(stringRequest);
                            //Clear Cart With IDAccount
                            RequestQueue requestQueue1 = Volley.newRequestQueue(context);
                            StringRequest stringRequest1 = new StringRequest(Request.Method.POST, Server.urlClearGioHangWithIDAccount, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }){
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    HashMap<String,String> param = new HashMap<>();
                                    param.put("idAccount", String.valueOf(account.getIdAccount()));
                                    return param;
                                }
                            };
                            requestQueue1.add(stringRequest1);
                            CheckConnection.ShowToast_Short(context, "Xóa thành công!");
                        }
                    });
                    builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            CheckConnection.ShowToast_Short(context, "Hủy xóa!");
                        }
                    });
                    builder.show();
                } else {
                    CheckConnection.ShowToast_Short(context, "Tài khoản Admin không thể xóa!");
                }
            }
        });
        holder.btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!account.getUsername().equals("Admin"))
                    iOnClickSuaAccount.OnClickAccount(account);
                else{
                    CheckConnection.ShowToast_Short(context, "Tài khoản Admin không thể sửa!");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listAccount.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvIDAccount, tvUsername;
        ImageView btnXoa, btnSua;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIDAccount = itemView.findViewById(R.id.tvIDAccount);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            btnSua = itemView.findViewById(R.id.btnSua);
            btnXoa = itemView.findViewById(R.id.btnXoa);
        }
    }
}
