package com.example.laptop88.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.laptop88.R;
import com.example.laptop88.databinding.ActivityChiTietSanPhamBinding;
import com.example.laptop88.model.GioHang;
import com.example.laptop88.model.GioHangWithIDAccount;
import com.example.laptop88.model.SanPham;
import com.example.laptop88.ultil.CheckConnection;
import com.example.laptop88.ultil.Server;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChiTietSanPham extends AppCompatActivity{
    ActivityChiTietSanPhamBinding binding;
    SanPham sanPham;
    //
    int iDSanPham;
    String tenSanPham = "";
    String hinhSanPham = "";
    int gia = 0;
    String moTa = "";
    int soLuong = 0;
    int baoHanh = 0;
    //
    int red = Color.parseColor("#ff0000");
    int black = Color.parseColor("#000000");
    //
    public static boolean change = false;
    //
    /*private static final String TAG = "Touch";
    @SuppressWarnings("unused")
    private static final float MIN_ZOOM = 1f,MAX_ZOOM = 1f;

    // These matrices will be used to scale points of the image
    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();

    // The 3 states (events) which the user is trying to perform
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    int mode = NONE;

    // these PointF objects are used to record the point(s) the user is touching
    PointF start = new PointF();
    PointF mid = new PointF();
    float oldDist = 1f;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chi_tiet_san_pham);

        if (CheckConnection.haveNetWorkConnection(getApplicationContext())) {
            ActionBar();
            GetChiTietSanPham();
            SetDefaultLayout();
            CatchSpinnerEvent();
            CatchOnButtonMuaNgay();
            CatchOnButtonThemVaoGioHang();
            CatchOnButtonLike();
            //binding.imgSanPham.setOnTouchListener(this);
        } else {
            CheckConnection.ShowToast_Short(getApplicationContext(), "Hãy kiểm tra lại kết nối");
        }
    }

    private void InsertSPDaXemIntoCSDL() {
        if (DangNhap.currentAccount != null) {
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.urlSPDaXem, new Response.Listener<String>() {
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
                    param.put("idSanPham", String.valueOf(iDSanPham));
                    return param;
                }
            };
            requestQueue.add(stringRequest);
        }
    }

    private void SetDefaultLayout() {
        binding.imgLike.setColorFilter(R.color.colorPrimary);
        //add into list đã xem
        if (DangNhap.currentAccount != null) {
            if (MainActivity.listDaXem.size() > 0) {
                boolean exists = false;
                for (int i = 0; i < MainActivity.listDaXem.size(); i++) {
                    if (MainActivity.listDaXem.get(i).getIdSanPham() == sanPham.getIdSanPham()) {
                        exists = true;
                    }
                }
                if (exists == false) {
                    MainActivity.listDaXem.add(0, sanPham);
                    InsertSPDaXemIntoCSDL();
                }
            } else {
                MainActivity.listDaXem.add(0, sanPham);
                InsertSPDaXemIntoCSDL();
            }
        }
        //SP đã có trong list đã xem thì nút like màu đỏ
        for (int i = 0; i < MainActivity.listYeuThich.size(); i++) {
            if (MainActivity.listYeuThich.get(i).getIdSanPham() == iDSanPham) {
                binding.imgLike.setColorFilter(red);
            }
        }
    }

    /*@Override
    protected void onRestart() {
        binding.imgLike.setColorFilter(R.color.colorPrimary);
        //SP đã có trong list đã xem thì nút like màu đỏ
        for (int i = 0; i < MainActivity.listYeuThich.size(); i++) {
            if (MainActivity.listYeuThich.get(i).getIdSanPham() == iDSanPham) {
                binding.imgLike.setColorFilter(red);
            }
        }
        super.onRestart();
    }*/

    private void DeleteSPYeuThichFromDB(){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.urlDeleteSPYeuThichWithIDSPAndIDAccount, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response!=null)
                    CheckConnection.ShowToast_Short(getApplicationContext(), "Đã xóa khỏi mục yêu thích!");
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
                param.put("idSanPham", String.valueOf(iDSanPham));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void InsertListYeuThichToDB() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.urlSPYeuThich, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response!=null)
                    CheckConnection.ShowToast_Short(getApplicationContext(), "Đã thêm vào mục yêu thích!");
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
                param.put("idSanPham", String.valueOf(iDSanPham));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void CatchOnButtonLike() {
        binding.imgLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DangNhap.currentAccount != null) {
                    boolean exists = false;
                    for (int i = 0; i < MainActivity.listYeuThich.size(); i++) {
                        if (MainActivity.listYeuThich.get(i).getIdSanPham() == iDSanPham) {
                            exists = true;
                            MainActivity.listYeuThich.remove(i);
                            change = true;
                            DeleteSPYeuThichFromDB();
                            binding.imgLike.setColorFilter(black);
                        }
                    }
                    if (exists == false) {
                        MainActivity.listYeuThich.add(0, sanPham);
                        InsertListYeuThichToDB();
                        binding.imgLike.setColorFilter(red);
                    }else{

                    }
                }else{
                    Intent intent = new Intent(ChiTietSanPham.this, DangNhap.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void CatchOnButtonThemVaoGioHang(){
        binding.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DangNhap.currentAccount != null) {
                    if (MainActivity.listGioHang.size() > 0) {
                        soLuong = Integer.parseInt(binding.spinner.getSelectedItem().toString());
                        boolean exists = false;
                        for (int i = 0; i < MainActivity.listGioHang.size(); i++) {
                            if (MainActivity.listGioHang.get(i).getiDSanPham() == iDSanPham) {
                                MainActivity.listGioHang.get(i).setSoLuong(MainActivity.listGioHang.get(i).getSoLuong() + soLuong);
                                MainActivity.listGioHang.get(i).setGia(MainActivity.listGioHang.get(i).getSoLuong() * gia);
                                exists = true;
                            }
                        }
                        if (exists == false) {
                            soLuong = Integer.parseInt(binding.spinner.getSelectedItem().toString());
                            long giaMoi = soLuong * gia;
                            MainActivity.listGioHang.add(new GioHang(iDSanPham, tenSanPham, giaMoi, hinhSanPham, soLuong));
                        }
                    } else {
                        soLuong = Integer.parseInt(binding.spinner.getSelectedItem().toString());
                        long giaMoi = soLuong * gia;
                        MainActivity.listGioHang.add(new GioHang(iDSanPham, tenSanPham, giaMoi, hinhSanPham, soLuong));
                    }
                    MainActivity.tvSoSPGioHang.setText(String.valueOf(MainActivity.listGioHang.size()));
                    CheckConnection.ShowToast_Short(getApplicationContext(), "Thêm vào giỏ hàng thành công!");
                } else {
                    Intent intent = new Intent(ChiTietSanPham.this, DangNhap.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void CatchOnButtonMuaNgay() {
        binding.btnMuaNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DangNhap.currentAccount != null) {
                    if (MainActivity.listGioHang.size() > 0) {
                        soLuong = Integer.parseInt(binding.spinner.getSelectedItem().toString());
                        boolean exists = false;
                        for (int i = 0; i < MainActivity.listGioHang.size(); i++) {
                            if (MainActivity.listGioHang.get(i).getiDSanPham() == iDSanPham) {
                                MainActivity.listGioHang.get(i).setSoLuong(MainActivity.listGioHang.get(i).getSoLuong() + soLuong);
                                MainActivity.listGioHang.get(i).setGia(MainActivity.listGioHang.get(i).getSoLuong() * gia);
                                exists = true;
                            }
                        }
                        if (exists == false) {
                            soLuong = Integer.parseInt(binding.spinner.getSelectedItem().toString());
                            long giaMoi = soLuong * gia;
                            MainActivity.listGioHang.add(new GioHang(iDSanPham, tenSanPham, giaMoi, hinhSanPham, soLuong));
                        }
                    } else {
                        soLuong = Integer.parseInt(binding.spinner.getSelectedItem().toString());
                        long giaMoi = soLuong * gia;
                        MainActivity.listGioHang.add(new GioHang(iDSanPham, tenSanPham, giaMoi, hinhSanPham, soLuong));
                    }
                    MainActivity.tvSoSPGioHang.setText(String.valueOf(MainActivity.listGioHang.size()));
                    Intent intent = new Intent(getApplicationContext(), com.example.laptop88.activity.GioHang.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(ChiTietSanPham.this, DangNhap.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void CatchSpinnerEvent() {
        Integer[] soLuong = new Integer[20];
        for (int i = 1; i <= 20; i++) {
            soLuong[i - 1] = i;
        }
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, soLuong);
        binding.spinner.setAdapter(arrayAdapter);
    }

    private void GetChiTietSanPham() {
        Intent intent = getIntent();
        sanPham = (SanPham) intent.getSerializableExtra("chiTietSanPham");

        iDSanPham = sanPham.getIdSanPham();
        tenSanPham = sanPham.getTenSanPham();
        hinhSanPham = sanPham.getHinhSanPham();
        gia = sanPham.getGia();
        moTa = sanPham.getMoTa();
        baoHanh = sanPham.getBaoHanh();

        binding.tvTenSanPham.setText(tenSanPham);
        //
        final ArrayList<String> mangHinhAnh = new ArrayList<>();
        mangHinhAnh.add(hinhSanPham);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.urlGetAnhChiTietSanPham, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String hinhAnh = "";
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i=0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        hinhAnh = jsonObject.getString("hinhAnh");
                        mangHinhAnh.add(hinhAnh);
                    }
                    for (int i = 0; i < mangHinhAnh.size(); i++) {
                        ImageView imageView = new ImageView(getApplicationContext());
                        Picasso.with(getApplicationContext()).load(mangHinhAnh.get(i)).into(imageView);
                        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        binding.viewFlipper.addView(imageView);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<>();
                param.put("idSanPham", String.valueOf(iDSanPham));
                return param;
            }
        };
        requestQueue.add(stringRequest);
        //
        binding.viewFlipper.setFlipInterval(10000);
        binding.viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        binding.viewFlipper.setInAnimation(animation_slide_in);
        binding.viewFlipper.setOutAnimation(animation_slide_out);
        //
        binding.imgPreviousViewFlipper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.viewFlipper.isAutoStart()) {
                    binding.viewFlipper.stopFlipping();
                    binding.viewFlipper.showPrevious();
                    binding.viewFlipper.startFlipping();
                    binding.viewFlipper.setAutoStart(true);
                }
            }
        });
        binding.imgNextViewFlipper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.viewFlipper.isAutoStart()) {
                    binding.viewFlipper.stopFlipping();
                    binding.viewFlipper.showNext();
                    binding.viewFlipper.startFlipping();
                    binding.viewFlipper.setAutoStart(true);
                }
            }
        });
        //
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        binding.tvGia.setText("Giá: " + decimalFormat.format(gia) + "đ");
        binding.tvMoTa.setText(moTa);
        binding.tvBaoHanh.setText("Bảo hành: " + baoHanh + " tháng");
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

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_top_right, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuSearch:
                Intent intent = new Intent(ChiTietSanPham.this, Search.class);
                startActivity(intent);
                break;
            case R.id.menuHome:
                intent = new Intent(ChiTietSanPham.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.menuCart:
                intent = new Intent(ChiTietSanPham.this, com.example.laptop88.activity.GioHang.class);
                startActivity(intent);
                break;
            case R.id.menuBaoHanhHauMai:
                intent = new Intent(ChiTietSanPham.this, BaoHanhHauMai.class);
                startActivity(intent);
                break;
            case R.id.menuBanHangOnline:
            case R.id.menuChamSocKhachHang:
                intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:02471069999"));
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /*@Override
    public boolean onTouch(View v, MotionEvent event) {
        ImageView view = (ImageView) v;
        view.setScaleType(ImageView.ScaleType.MATRIX);
        float scale;

        dumpEvent(event);
        // Handle touch events here...

        switch (event.getAction() & MotionEvent.ACTION_MASK)
        {
            case MotionEvent.ACTION_DOWN:   // first finger down only
                savedMatrix.set(matrix);
                start.set(event.getX(), event.getY());
                Log.d(TAG, "mode=DRAG"); // write to LogCat
                mode = DRAG;
                break;

            case MotionEvent.ACTION_UP: // first finger lifted

            case MotionEvent.ACTION_POINTER_UP: // second finger lifted

                mode = NONE;
                Log.d(TAG, "mode=NONE");
                break;

            case MotionEvent.ACTION_POINTER_DOWN: // first and second finger down

                oldDist = spacing(event);
                Log.d(TAG, "oldDist=" + oldDist);
                if (oldDist > 5f) {
                    savedMatrix.set(matrix);
                    midPoint(mid, event);
                    mode = ZOOM;
                    Log.d(TAG, "mode=ZOOM");
                }
                break;

            case MotionEvent.ACTION_MOVE:

                if (mode == DRAG)
                {
                    matrix.set(savedMatrix);
                    matrix.postTranslate(event.getX() - start.x, event.getY() - start.y); // create the transformation in the matrix  of points
                }
                else if (mode == ZOOM)
                {
                    // pinch zooming
                    float newDist = spacing(event);
                    Log.d(TAG, "newDist=" + newDist);
                    if (newDist > 5f)
                    {
                        matrix.set(savedMatrix);
                        scale = newDist / oldDist; // setting the scaling of the
                        // matrix...if scale > 1 means
                        // zoom in...if scale < 1 means
                        // zoom out
                        matrix.postScale(scale, scale, mid.x, mid.y);
                    }
                }
                break;
        }

        view.setImageMatrix(matrix); // display the transformation on screen

        return true; // indicate event was handled
    }

    private float spacing(MotionEvent event)
    {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    private void midPoint(PointF point, MotionEvent event)
    {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    *//** Show an event in the LogCat view, for debugging *//*
    private void dumpEvent(MotionEvent event)
    {
        String names[] = { "DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE","POINTER_DOWN", "POINTER_UP", "7?", "8?", "9?" };
        StringBuilder sb = new StringBuilder();
        int action = event.getAction();
        int actionCode = action & MotionEvent.ACTION_MASK;
        sb.append("event ACTION_").append(names[actionCode]);

        if (actionCode == MotionEvent.ACTION_POINTER_DOWN || actionCode == MotionEvent.ACTION_POINTER_UP)
        {
            sb.append("(pid ").append(action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
            sb.append(")");
        }

        sb.append("[");
        for (int i = 0; i < event.getPointerCount(); i++)
        {
            sb.append("#").append(i);
            sb.append("(pid ").append(event.getPointerId(i));
            sb.append(")=").append((int) event.getX(i));
            sb.append(",").append((int) event.getY(i));
            if (i + 1 < event.getPointerCount())
                sb.append(";");
        }

        sb.append("]");
        Log.d("Touch Events ---------", sb.toString());
    }*/
}
