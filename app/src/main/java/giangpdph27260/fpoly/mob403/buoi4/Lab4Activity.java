package giangpdph27260.fpoly.mob403.buoi4;

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

public class Lab4Activity extends AppCompatActivity {
    EditText txt1,txt2,txt3;
    Button btnInsert,btnGet;
    TextView tvKQ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab4);
        txt1=findViewById(R.id.demo41Txt1);
        txt2=findViewById(R.id.demo41Txt2);
        txt3=findViewById(R.id.demo41Txt3);
        btnInsert=findViewById(R.id.demo41Btn1);
        btnGet=findViewById(R.id.demo41Btn2);
        tvKQ=findViewById(R.id.demo41TvKQ);
        btnGet.setOnClickListener(v -> selectData());
        btnInsert.setOnClickListener(v -> insertData());

    }
    String kq="";//chuoi ket qua
    //tao list prd
    Product prd = new Product();//tao doi tuong prd
    void insertData()
    {
        //B0. Dua du lieu vao doi tuong
        prd.setName(txt1.getText().toString());
        prd.setPrice(txt2.getText().toString());
        prd.setDescription(txt3.getText().toString());
        //B1. Tao doi tuong retrofit
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://batdongsanabc.000webhostapp.com/mob403lab4/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //b2. Chuan bi ham va thuc thi ham
        //2.1 - goi inteface
        InterfaceInsert interfaceInsert=retrofit.create(InterfaceInsert.class);
        //2.2. chuan bi ham
        Call<SvrResponseInsert> call=
                interfaceInsert.insertPrd(prd.getName(),prd.getPrice(),prd.getDescription());
        //2.3 goi ham
        call.enqueue(new Callback<SvrResponseInsert>() {
            //thanh cong
            @Override
            public void onResponse(Call<SvrResponseInsert> call, Response<SvrResponseInsert> response) {
                SvrResponseInsert svrResponseInsert=response.body();//lay noi dung server tra ve
                tvKQ.setText(svrResponseInsert.getMessage());//in ra ket qua
            }
            //that bai
            @Override
            public void onFailure(Call<SvrResponseInsert> call, Throwable t) {
                tvKQ.setText(t.getMessage());//in ra thong bao loi
            }
        });
    }
    void selectData(){
        //b1. tao doi tuong retrofit
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://batdongsanabc.000webhostapp.com/mob403lab4/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //b2. Goi interface, chuan bi ham va goi ham
        InterfaceSelect interfaceSelect = retrofit.create(InterfaceSelect.class);
        Call<SvrResponseSelect> call =  interfaceSelect.getPrd();
        call.enqueue(new Callback<SvrResponseSelect>() {
            //thanh cong
            @Override
            public void onResponse(Call<SvrResponseSelect> call, Response<SvrResponseSelect> response) {
                SvrResponseSelect svrResponseSelect= response.body();//lay ket qua server tra ve
                ArrayList<Product> ls = new ArrayList<>(Arrays.asList(svrResponseSelect.getProducts()));//chuyen du lieu sang list
                for(Product p : ls)//cho vao vong for de doc tung doi tuong
                {
                    kq +="Name: "+p.getName()+
                            "; Price: "+p.getPrice()+
                            "; Des: "+p.getDescription()+"\n";
                }
                tvKQ.setText(kq);
            }
            //that bai
            @Override
            public void onFailure(Call<SvrResponseSelect> call, Throwable t) {
                tvKQ.setText(t.getMessage());//in ra thong bao loi
            }
        });

    }
}
