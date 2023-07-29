package giangpdph27260.fpoly.mob403.buoi6;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import giangpdph27260.fpoly.mob403.R;

public class Lab6Activity extends AppCompatActivity {
    private Button btnGetString;
    private Button btn;
    private TextView tvKqLab6;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab6);
        btnGetString = (Button) findViewById(R.id.btnGetString);
        btn = (Button) findViewById(R.id.btn);
        tvKqLab6 = findViewById(R.id.tv_kq_lab6);


        btnGetString.setOnClickListener(v -> {
            getString();
        });
        btn.setOnClickListener(v -> {
            getDataJson();
        });
    }

    private void getString() {
        // tao request

        RequestQueue queue = Volley.newRequestQueue(this);
        // truyen url

        String url = "https://www.google.com/";
        // truyen tham so

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                tvKqLab6.setText("ket qua :" + response.substring(0, 1000));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tvKqLab6.setText(error.getMessage());
            }
        });
        queue.add(stringRequest);
    }
    String ketQua;
    private void getDataJson(){
        RequestQueue queue = Volley.newRequestQueue(this);

        String url = "https://batdongsanabc.000webhostapp.com/mob403lab6/array_json_new.json";

        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ketQua = "";
                for (int i = 0; i < response.length(); i++){
                    try {
                        JSONObject data = response.getJSONObject(i);
                        String id = data.getString("id");
                        String name = data.getString("name");
                        String email = data.getString("email");
                        String address = data.getString("address");
                        JSONObject phone = data.getJSONObject("phone");
                        String mobile = phone.getString("mobile");
                        String home = phone.getString("home");

                        // noi chuoi
                        ketQua += " id : "+ id + "\n";
                        ketQua += " name : "+ name + "\n";
                        ketQua += " email : "+ email + "\n";
                        ketQua += " address : "+ address + "\n";
                        ketQua += " mobile : "+ mobile + "\n";
                        ketQua += " home : "+ home + "\n";
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    tvKqLab6.setText(ketQua);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tvKqLab6.setText(error.getMessage());
            }
        });
        queue.add(request);
    }
}
