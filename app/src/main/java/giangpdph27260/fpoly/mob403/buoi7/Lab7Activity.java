package giangpdph27260.fpoly.mob403.buoi7;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import giangpdph27260.fpoly.mob403.R;

public class Lab7Activity extends AppCompatActivity {
    private EditText edtPid;
    private EditText edtName;
    private EditText edtPrice;
    private EditText edtDescription;
    private Button btnAdd;
    private Button btnUpdate;
    private Button btnDelete;
    private Button btnSelect;
    private TextView tvKetQua;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab7);


        edtPid = (EditText) findViewById(R.id.edt_pid);
        edtName = (EditText) findViewById(R.id.edt_name);
        edtPrice = (EditText) findViewById(R.id.edt_price);
        edtDescription = (EditText) findViewById(R.id.edt_description);
        btnAdd = (Button) findViewById(R.id.btn_add);
        btnUpdate = (Button) findViewById(R.id.btn_update);
        btnDelete = (Button) findViewById(R.id.btn_delete);
        btnSelect = (Button) findViewById(R.id.btn_select);
        tvKetQua = (TextView) findViewById(R.id.tv_ketQua);

        btnAdd.setOnClickListener(v -> {
            addVolley();
        });
        btnUpdate.setOnClickListener(v -> {
            updateVolley();
        });
        btnDelete.setOnClickListener(v -> {
            deleteVolley();
        });
        btnSelect.setOnClickListener(v -> {
            selectVolley();
        });
    }

    private void selectVolley() {
        RequestQueue queue=Volley.newRequestQueue(this);
        //b3. url
        String url="https://batdongsanabc.000webhostapp.com/mob403lab7/get_all_product.php";
        //b4. Xac dinh loai request
        JsonObjectRequest request=new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray products=response.getJSONArray("products");
                    String strKQ ="";
                    for (int i=0;i<products.length();i++)
                    {
                        JSONObject product=products.getJSONObject(i);
                        String pid=product.getString("pid");
                        String name=product.getString("name");
                        String price=product.getString("price");
                        String description=product.getString("description");
                        strKQ +="pid: "+pid +"\n";
                        strKQ +="name: "+name +"\n";
                        strKQ +="price: "+price +"\n";
                        strKQ +="description: "+description +"\n";
                    }
                    tvKetQua.setText(strKQ);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tvKetQua.setText(error.getMessage());
            }
        });
        //b5. truyen tham so (neu co)
        //b6. thuc thi
        queue.add(request);
    }

    private void deleteVolley() {
        RequestQueue queue= Volley.newRequestQueue(this);
        //b3. url
        String url="https://batdongsanabc.000webhostapp.com/mob403lab7/delete_product.php";
        //b4. Xac dinh loai request
        //StringRequest(method,url,thanhCong,thatBai){thamso};
        StringRequest request=new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        tvKetQua.setText(response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tvKetQua.setText(error.getMessage());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> mydata=new HashMap<>();
                mydata.put("pid",edtPid.getText().toString());
                return mydata;
            }
        };
        //b5. truyen tham so (neu co)
        //b6. thuc thi
        queue.add(request);
    }

    private void updateVolley() {
        RequestQueue queue=Volley.newRequestQueue(this);
        //b3. url
        String url="https://batdongsanabc.000webhostapp.com/mob403lab7/update_product.php";
        //b4. Xac dinh loai request
        //StringRequest(method,url,thanhCong,thatBai){thamso};
        StringRequest request=new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        tvKetQua.setText(response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tvKetQua.setText(error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> mydata=new HashMap<>();
                mydata.put("pid",edtPid.getText().toString());
                mydata.put("name",edtName.getText().toString());
                mydata.put("price",edtPrice.getText().toString());
                mydata.put("description",edtDescription.getText().toString());
                return mydata;
            }
        };
        //b5. truyen tham so (neu co)
        //b6. thuc thi
        queue.add(request);
    }

    private void addVolley() {
        RequestQueue queue= Volley.newRequestQueue(this);
        //b3. url
        String url="https://batdongsanabc.000webhostapp.com/mob403lab7/create_product.php";
        //b4. Xac dinh loai request
        //StringRequest(method,url,thanhCong,thatBai){thamso};
        StringRequest request= new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        tvKetQua.setText(response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tvKetQua.setText(error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> mydata=new HashMap<>();
                mydata.put("name",edtName.getText().toString());
                mydata.put("price",edtPrice.getText().toString());
                mydata.put("description",edtDescription.getText().toString());
                return mydata;
            }
        };
        //b5. truyen tham so (neu co)
        //b6. thuc thi
        queue.add(request);
    }
}
