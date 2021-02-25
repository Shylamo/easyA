package android.example.easya;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class profil_etd extends AppCompatActivity {
   CircleImageView circleImageView;
    SessionManager logOut;
    ImageButton signOut;
    ImageButton parametres,message;
    int mod=0;
    ///////FIREBASE///////////////
    private String num;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    private FirebaseAuth mAuth,mfirebase;

    private static final int GalleryPick = 1;
    private StorageReference circleImageViewRef;

    //////EXPANDABLE LISTS////////
    private expandableListAdapter listAdapter;
    private ExpandableListView listView;
    private List<String> data_en_tete;
    private HashMap<String,List<String>> list_hash;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil__etudiant);

        listView= (ExpandableListView) findViewById(R.id.modules_list);
        initdata();
        listAdapter=new expandableListAdapter(this,data_en_tete,list_hash);
        listView.setAdapter(listAdapter);

        parametres=findViewById(R.id.parametres_etd);

        message=findViewById(R.id.message_etd);
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(profil_etd.this,Envoie_mail_etd.class);
                startActivity(intent);
                finish();
            }
        });
        circleImageView=findViewById(R.id.circleimageid);

/////////////////////////////////////OPen a new activity by clicking on the child of the expandable LIst
        SessionManager user= new SessionManager(profil_etd.this,SessionManager.SESSION_REMEMBERME);

        TextView txt=findViewById(R.id.texto_liste);
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
               //MODULE SELECTIONNE PAR L'ETUDIANT
                final String selected_module= (String) listAdapter.getChild(groupPosition,childPosition);//Le module selectionne/////CONFIRME-->OK

                DatabaseReference root =FirebaseDatabase.getInstance().getReference();
                DatabaseReference vol_ref = root.child("vol");

                List<String> volontaires_ma= new ArrayList<>();
                List<String> volontaires_info= new ArrayList<>();


                vol_ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot: snapshot.getChildren())
                        {
                            if(dataSnapshot.child("modules_maths").toString().contains(selected_module))
                            {
                                volontaires_ma.add(dataSnapshot.child("num").getValue().toString());

                                mod=1;
                            }
                            else
                            {
                                startActivity(new Intent(profil_etd.this,pas_de_vol.class));
                                finish();
                            }


                            if(dataSnapshot.child("modules_info").toString().contains(selected_module))
                            {
                                volontaires_info.add(dataSnapshot.child("num").getValue().toString());
                                mod=2;
                            }
                            else
                            {
                                startActivity(new Intent(profil_etd.this,pas_de_vol.class));
                                finish();
                            }

                        }

                        /////TRANSMISSION DE LA LISTE DE VOLONTAIRE A L'ACTIVITE SUIVANTE
                        Intent intent_vol= new Intent(profil_etd.this,Liste_volontaires.class);
                        intent_vol.putExtra("volontaire_ma", (Serializable) volontaires_ma);
                        intent_vol.putExtra("volontaire_info", (Serializable) volontaires_info);
                        intent_vol.putExtra("module",mod);
                        startActivity(intent_vol);
                        finish();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                return true;
            }
        });
///////////////////////////////////////////////////////////////////
        /////// IMAGE UPLOAD
        mAuth=FirebaseAuth.getInstance();
       reference=FirebaseDatabase.getInstance().getReference();
        circleImageViewRef = FirebaseStorage.getInstance().getReference().child("Profile Images");


        SessionManager current_user= new SessionManager(profil_etd.this,SessionManager.SESSION_REMEMBERME);
        num = current_user.getUserData_Session().get(SessionManager.KEY_NUM);

        TextView text=findViewById(R.id.bonjour_vol);
        text.setText(current_user.getUserData_Session().get(SessionManager.KEY_NOM));// GET NOM USER CURRENTLY CONNECTED

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,GalleryPick);
            }
        });

       get_profile_pic();

       //LOGOUT
        signOut=findViewById(R.id.logout);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(profil_etd.this,MainActivity.class));
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            // do your stuff
        } else {
            signInAnonymously();
        }
    }

    private void signInAnonymously() {

        mAuth.signInAnonymously().addOnSuccessListener(this, new  OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                // do your stuff
            }
        })
                .addOnFailureListener(this, new OnFailureListener() {
                    private static final String TAG = "tag";

                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Log.e(TAG, "signInAnonymously:FAILURE", exception);
                    }
                });
    }

    private void initdata()  {
        data_en_tete= new ArrayList<>();
        list_hash = new HashMap<>();

        data_en_tete.add("MATHS");
        data_en_tete.add("INFORMATIQUE");

        List<String>maths_lc= new ArrayList<>();
        String [] mod_maths_lc =getResources().getStringArray(R.array.Maths_LC);
        for(int i=0;i<mod_maths_lc.length;i++)
        {
            maths_lc.add(mod_maths_lc[i]);
        }
        List<String>maths_ma= new ArrayList<>();
        String [] mod_maths_ma =getResources().getStringArray(R.array.Maths_MA);
        for(int i=0;i<mod_maths_ma.length;i++)
        {
            maths_ma.add(mod_maths_ma[i]);
        }
        List<String>maths_doc= new ArrayList<>();
        String [] mod_maths_doc =getResources().getStringArray(R.array.Maths_DOC);
        for(int i=0;i<mod_maths_doc.length;i++)
        {
            maths_doc.add(mod_maths_doc[i]);
        }

        List<String>info_lc= new ArrayList<>();
        String [] mod_info_lc =getResources().getStringArray(R.array.Info_LC);
        for(int i=0;i<mod_info_lc.length;i++)
        {
            info_lc.add(mod_info_lc[i]);
        }
        List<String>info_ma= new ArrayList<>();
        String [] mod_info_ma =getResources().getStringArray(R.array.Info_MA);
        for(int i=0;i<mod_info_ma.length;i++)
        {
            info_ma.add(mod_info_ma[i]);
        }
        List<String> info_doc= new ArrayList<>();
        String [] mod_info_doc =getResources().getStringArray(R.array.Info_DOC);
        for(int i=0;i<mod_info_doc.length;i++)
        {
            info_doc.add(mod_info_doc[i]);
        }

        SessionManager user= new SessionManager(profil_etd.this,SessionManager.SESSION_REMEMBERME);

        Query CheckUser = FirebaseDatabase.getInstance().getReference("etd").orderByChild("numApogee").equalTo(user.getUserData_Session().get(SessionManager.KEY_NUM));
        CheckUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                String niveau_user=snapshot.child(user.getUserData_Session().get(SessionManager.KEY_NUM)).child("niveau").getValue(String.class);
                rootNode =FirebaseDatabase.getInstance();
                reference = rootNode.getReference("etd");

                if(niveau_user.contains("Licence"))
                {
                    list_hash.put(data_en_tete.get(0),maths_lc);
                    list_hash.put(data_en_tete.get(1),info_lc);
                }
                else if(niveau_user.contains("Master"))
                {
                    list_hash.put(data_en_tete.get(0),maths_ma);
                    list_hash.put(data_en_tete.get(1),info_ma);
                }
                else
                {
                    list_hash.put(data_en_tete.get(0),maths_doc);
                    list_hash.put(data_en_tete.get(1),info_doc);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });
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

                StorageReference filePath = circleImageViewRef.child(num +".jpg");

                filePath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        
                        if (task.isSuccessful())
                        {
                            Toast.makeText(profil_etd.this, "téléchargement de l'image photo avec succès", Toast.LENGTH_SHORT).show();
                            final String downloadUrl= task.getResult().getStorage().toString();
                            reference.child(num).child("image")
                                    .setValue(downloadUrl).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(profil_etd.this, "Image sauvegarde", Toast.LENGTH_SHORT).show();


                                    }
                                    else
                                    {// It will get the message of the error
                                        String message = task.getException().toString();
                                        Toast.makeText(profil_etd.this, "Error: "+message, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                        else
                        {
                            String message = task.getException().toString();
                            Toast.makeText(profil_etd.this, "Erreur" + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }

   private void get_profile_pic() {

        reference = FirebaseDatabase.getInstance().getReference();
        SessionManager user= new SessionManager(profil_etd.this,SessionManager.SESSION_REMEMBERME);
        String num = user.getUserData_Session().get(SessionManager.KEY_NUM);

        circleImageViewRef = FirebaseStorage.getInstance().getReference().child("Profile Images").child(num+".jpg");
        circleImageViewRef.getBytes(1024*1024)
                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                        circleImageView.setImageBitmap(bitmap);
                    }
                });

    }

    //PASSER AUX PARAMETRES --> FAIRE DES MODIFS SUR LES DONNES/INFOS
    public void edit_etd(View view) {
        startActivity(new Intent(profil_etd.this,edit_profil_etd.class));
        finish();
    }
/*
    public void Log_Out_etd(View view)
    {
        //logOut.logoutSession();

    }*/
}
