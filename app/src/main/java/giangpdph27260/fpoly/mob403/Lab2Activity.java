package giangpdph27260.fpoly.mob403;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Lab2Activity extends AppCompatActivity {
    private ImageView imageLab2;
    private EditText edtNhap;
    private Button btnLoad;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab2);
        imageLab2 = (ImageView) findViewById(R.id.image_lab2);
        edtNhap = (EditText) findViewById(R.id.edtNhap);
        btnLoad = (Button) findViewById(R.id.btnLoad);

        btnLoad.setOnClickListener(v -> {
            String url = edtNhap.getText().toString();
            new ImageLoaderTask().execute(url);

        });
    }

    public class ImageLoaderTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            String imageUrl = params[0];
            Bitmap bitmap = null;
            try {
                URL url = new URL(imageUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                ImageView imageView = findViewById(R.id.image_lab2);
                imageView.setImageBitmap(bitmap);
            }
        }
    }
}
