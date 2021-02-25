package android.example.easya;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;


public class profil_vol extends AppCompatActivity {

    CircleImageView circleImageView;
    LinearLayout param, messages;
  //  SessionManager logOut;
    LinearLayout log_vol;


    ///////FIREBASE///////////////
    private String num;
    DatabaseReference reference;

    private static final int GalleryPick = 1;
    private StorageReference circleImageViewRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil__volontaire);
        log_vol=findViewById(R.id.lougout_vol);
        log_vol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(profil_vol.this,MainActivity.class));
                finish();
            }
        });

        TextView Bonjour_msg = findViewById(R.id.bonjour_vol);//nom du volontaire
        TextView nom_bjr=findViewById(R.id.bonjour);

        //GETTING VALUES FROM LAYOUTS
        param = findViewById(R.id.parametres_vol);
        messages=findViewById(R.id.message_vol);
        messages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(profil_vol.this,Envoie_mail.class);
                startActivity(intent);
                finish();
            }
        });
        SessionManager current_user = new SessionManager(profil_vol.this, SessionManager.SESSION_REMEMBERME);
        nom_bjr.setText("Bonjour "+current_user.getUserData_Session().get(SessionManager.KEY_NOM));

        num = current_user.getUserData_Session().get(SessionManager.KEY_NUM);
        Bonjour_msg.setText(current_user.getUserData_Session().get(SessionManager.KEY_NOM));

        circleImageView = findViewById(R.id.circleimageid);

        /////// IMAGE UPLOAD
        reference = FirebaseDatabase.getInstance().getReference();
        circleImageViewRef = FirebaseStorage.getInstance().getReference().child("Profile Images vol");


        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GalleryPick);
            }
        });
        get_profile_pic();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GalleryPick && resultCode==RESULT_OK && data !=null && data!=null){
            Uri ImageUri =data.getData();
            CropImage.activity()
                    .setGuidelines ( CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start (this);
        }
        if ( requestCode ==  CropImage . CROP_IMAGE_ACTIVITY_REQUEST_CODE ) {
            CropImage.ActivityResult result =CropImage.getActivityResult (data);

            if (resultCode ==RESULT_OK)
            {
                Uri resultUri = result.getUri();

                StorageReference filePath = circleImageViewRef.child(num + ".jpg");
                filePath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                        if (task.isSuccessful())
                        {
                            Toast.makeText(profil_vol.this, "téléchargement de l'image photo avec succès", Toast.LENGTH_SHORT).show();
                            final String downloadUrl= task.getResult().getStorage().toString();

                            reference.child("vol").child(num).child("image")
                                    .setValue(downloadUrl).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(profil_vol.this, "Image sauvegarde", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {// It will get the message of the error
                                        String message = task.getException().toString();
                                        Toast.makeText(profil_vol.this, "Error: "+message, Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                        }
                        else
                        {
                            String message = task.getException().toString();
                            Toast.makeText(profil_vol.this, "Erreur" + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }


    private void get_profile_pic() {

        reference = FirebaseDatabase.getInstance().getReference();
        SessionManager user= new SessionManager(profil_vol.this,SessionManager.SESSION_REMEMBERME);
        String num = user.getUserData_Session().get(SessionManager.KEY_NUM);

        circleImageViewRef = FirebaseStorage.getInstance().getReference().child("Profile Images vol").child(num+".jpg");
        circleImageViewRef.getBytes(1024*1024)
                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                        circleImageView.setImageBitmap(bitmap);
                    }
                });

    }

    public void edit_vol(View view) {
        startActivity(new Intent(profil_vol.this, edit_profil_vol.class));
        finish();
    }

   /* public void Log_Out_Vol(View view)
    {
        logOut.logoutSession();
    }*/

}