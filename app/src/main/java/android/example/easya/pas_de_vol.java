package android.example.easya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class pas_de_vol extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pas_de_vol);
}

    public void back_profil_etd(View view) {
        startActivity(new Intent(this,profil_etd.class));
        finish();
    }
}