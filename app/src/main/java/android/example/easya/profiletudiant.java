package android.example.easya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class profiletudiant extends AppCompatActivity {
    private Button math;
    private Button button10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiletudiant);
        math= (Button) findViewById(R.id.math);
        button10= (Button) findViewById(R.id.button10);
        math.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openmodulemath();
            }
        });

        button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openmoduleinfo();
            }
        });

    }



    public void openmodulemath () {
        Intent intent= new Intent(this, modulemath.class);
        startActivity(intent);

    }
    public void openmoduleinfo () {
        Intent intent= new Intent(this, moduleinfo.class);
        startActivity(intent);

    }
}