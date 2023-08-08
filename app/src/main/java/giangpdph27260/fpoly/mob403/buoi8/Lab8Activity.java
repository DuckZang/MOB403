package giangpdph27260.fpoly.mob403.buoi8;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import giangpdph27260.fpoly.mob403.R;

public class Lab8Activity extends AppCompatActivity {
    FirebaseDatabase database;
    private EditText edtTitle, edtId;
    private EditText edtContent;
    private Button btnInsert;
    private Button btnUpdate;
    private Button btnDelete;
    private Button btnShow;
    private TextView tvResult;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab8);


        edtTitle = (EditText) findViewById(R.id.edt_title);
        edtId = (EditText) findViewById(R.id.edt_id);
        edtContent = (EditText) findViewById(R.id.edt_content);
        btnInsert = (Button) findViewById(R.id.btn_insert);
        btnUpdate = (Button) findViewById(R.id.btn_update);
        btnDelete = (Button) findViewById(R.id.btn_delete);
        tvResult = (TextView) findViewById(R.id.tv_result);

        // ket noi firebase va dat ten cho Collection
        database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
        myRef.setValue("Giang dep trai vai o`");
        btnDelete.setOnClickListener(v -> {
            deleteFirebase();
        });
        btnInsert.setOnClickListener(v -> {
            insertFirebase();
        });
        btnUpdate.setOnClickListener(v -> {
            updateFirebase();
        });
        


    }

    public void insertFirebase(){

        Model model = new Model();
        model.setId(edtId.getText().toString());
        model.setTitle(edtTitle.getText().toString());
        model.setContent(edtContent.getText().toString());
        HashMap<String,Object> map = model.hashMap();
        String key = database.getReference("model").getKey();
        database.getReference("model").child(edtId.getText().toString()).setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(Lab8Activity.this, "Them thanh cong", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Lab8Activity.this, "Them that bai", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void updateFirebase(){
        Model model = new Model();
        model.setId(edtId.getText().toString());
        model.setTitle(edtTitle.getText().toString());
        model.setContent(edtContent.getText().toString());
        HashMap<String,Object> map = model.hashMap();
        database.getReference("model").child(edtId.getText().toString()).updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(Lab8Activity.this, "Sua thanh cong", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Lab8Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void deleteFirebase(){
        database.getReference("model").child(edtId.getText().toString()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(Lab8Activity.this, "Xoaaa", Toast.LENGTH_SHORT).show();
            }
        });
    }
    String strKQ="";
    public ArrayList<Model> listFirebase(){
        ArrayList<Model> list = new ArrayList<>();

        database.getReference("model").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    Model model = snapshot1.getValue(Model.class);
                    strKQ += "id: "+ model.getId()+"\n"
                                + "title:" + model.getTitle() +"\n"
                                + "content: "+ model.getContent() +"\n"+"\n" ;
                    list.add(model);
                }
                tvResult.setText(strKQ);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return list;
    }
}
