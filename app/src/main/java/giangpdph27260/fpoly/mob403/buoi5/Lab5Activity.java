package giangpdph27260.fpoly.mob403.buoi5;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

import giangpdph27260.fpoly.mob403.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Lab5Activity extends AppCompatActivity {
    private EditText edtNhapId;
    private EditText edtNhapName;
    private EditText edtNhapPrice;
    private EditText edtNhapDescription;
    private Button btnSelect;
    private Button btnUpdate;
    private Button btnDelete;
    private TextView tvKetQuaLab5;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab5);

        edtNhapId = (EditText) findViewById(R.id.edtNhapId);
        edtNhapName = (EditText) findViewById(R.id.edtNhapName);
        edtNhapPrice = (EditText) findViewById(R.id.edtNhapPrice);
        edtNhapDescription = (EditText) findViewById(R.id.edtNhapDescription);
        btnSelect = (Button) findViewById(R.id.btnSelect);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        tvKetQuaLab5 = (TextView) findViewById(R.id.tvKetQuaLab5);

        btnDelete.setOnClickListener(v -> {
            deleteRetrofit();
        });
        btnSelect.setOnClickListener(v -> {
            selectRetrofit();
        });
        btnUpdate.setOnClickListener(v -> {
            updateRetrofit();
        });
    }
    private void deleteRetrofit() {
        //B0. Chuan bi du lieu
        Product p=new Product();
        p.setPid(edtNhapId.getText().toString());
        //b1. Tao doi tuong Retrofit
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://batdongsanabc.000webhostapp.com/mob403lab5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //b2. Goi interface + chuan bi ham + thuc thi ham
        InterfaceDelete interDelete=retrofit.create(InterfaceDelete.class);
        Call<SvrResponseDelete> call=interDelete.deleteDB(p.getPid());
        call.enqueue(new Callback<SvrResponseDelete>() {
            //thanh cong
            @Override
            public void onResponse(Call<SvrResponseDelete> call, Response<SvrResponseDelete> response) {
                //lay ve ket qua
                SvrResponseDelete svrResponseDelete=response.body();
                //dua ket qua len man hinh
                tvKetQuaLab5.setText(svrResponseDelete.getMessage());
            }
            //that bai
            @Override
            public void onFailure(Call<SvrResponseDelete> call, Throwable t) {
                tvKetQuaLab5.setText(t.getMessage());
            }
        });
    }

    private void updateRetrofit() {
        //B0. Chuan bi du lieu
        Product p=new Product();
        p.setPid(edtNhapId.getText().toString());
        p.setName(edtNhapName.getText().toString());
        p.setPrice(edtNhapPrice.getText().toString());
        p.setDescription(edtNhapDescription.getText().toString());
        //b1. Tao doi tuong Retrofit
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://batdongsanabc.000webhostapp.com/mob403lab5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //b2. Goi interface + chuan bi ham + thuc thi ham
        InterfaceUpdate interUpdate=retrofit.create(InterfaceUpdate.class);
        Call<SvrResponseUpdate> call=interUpdate.updateDB(p.getPid(),
                p.getName(),p.getPrice(),p.getDescription());
        call.enqueue(new Callback<SvrResponseUpdate>() {
            @Override
            public void onResponse(Call<SvrResponseUpdate> call, Response<SvrResponseUpdate> response) {
                //lay ve ket qua
                SvrResponseUpdate svrResponseUpdate=response.body();
                //dua ket qua len man hinh
                tvKetQuaLab5.setText(svrResponseUpdate.getMessage());
            }

            @Override
            public void onFailure(Call<SvrResponseUpdate> call, Throwable t) {
                tvKetQuaLab5.setText(t.getMessage());//ghi ra loi
            }
        });
    }
    ArrayList<Product> ls;
    String strKQ="";//chuoi chua ket qua
    private void selectRetrofit() {
        //B0. Chuan bi du lieu
        //b1. Tao doi tuong Retrofit
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://batdongsanabc.000webhostapp.com/mob403lab4/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //b2. Goi interface + chuan bi ham + thuc thi ham
        InterfaceSelect interSelect=retrofit.create(InterfaceSelect.class);
        Call<SvrResponseSelect> call=interSelect.selectDB();
        call.enqueue(new Callback<SvrResponseSelect>() {
            @Override
            public void onResponse(Call<SvrResponseSelect> call, Response<SvrResponseSelect> response) {
                //lay ve key qua
                SvrResponseSelect svrResponseSelect=response.body();
                //chuyen mang sang list
                ls=new ArrayList<>(Arrays.asList(svrResponseSelect.getProducts()));
                //for
                strKQ="";
                for(Product p: ls){
                    strKQ += "Pid: "+p.getPid()
                            +" - "+p.getName()
                            +" - "+p.getPrice()
                            +" - "+p.getDescription()+"\n";
                }
                //dua ket qua len man hinh
                tvKetQuaLab5.setText(strKQ);
            }

            @Override
            public void onFailure(Call<SvrResponseSelect> call, Throwable t) {
                tvKetQuaLab5.setText(t.getMessage());//dua ra thong bao loi
            }
        });
    }
}
