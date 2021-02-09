package android.example.easya;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import de.hdodenhof.circleimageview.CircleImageView;

public class profiletudiant extends AppCompatActivity {
    private Button math;
    private Button button10;
    CircleImageView circleImageView;
    Button button;
    Uri imageuri;
    public static final int IMAGE_CODE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiletudiant);
        circleImageView=findViewById(R.id.circleimageid);
        button=findViewById(R.id.loadimageId);
        math= (Button) findViewById(R.id.math);
        button10= (Button) findViewById(R.id.button10);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openimageform();
            }
        });
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

    private void openimageform() {
        Intent intent= new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_CODE );
    }


    public void openmodulemath () {
        Intent intent= new Intent(this, modulemath.class);
        startActivity(intent);

    }
    public void openmoduleinfo () {
        Intent intent= new Intent(this, moduleinfo.class);
        startActivity(intent);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==IMAGE_CODE && resultCode==RESULT_OK && data !=null && data.getData()!=null){
            imageuri=data.getData();
            circleImageView.setImageURI(imageuri);
        }
    }
}