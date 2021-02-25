package android.example.easya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Database;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.se.omapi.Session;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class edit_profil_etd extends AppCompatActivity {

    TextInputEditText nv_mail,nv_pass;
    Spinner nv_niveau;
    Button enregistrer;

    FirebaseDatabase rootNode;
    DatabaseReference reference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profil_etd);

        List<String> niveau_etd = new ArrayList<>();
        niveau_etd.add(0, "Seclectionnez votre niveau d'etude");
        niveau_etd.add("Licence SMI");
        niveau_etd.add("Licence SMA");
        niveau_etd.add("Master STRI");
        niveau_etd.add("Master ISI");
        niveau_etd.add("Doctorat Maths et Informatique");

        //styler le spinner
        ArrayAdapter<String> data;
        data = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, niveau_etd);
        data.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        nv_mail = findViewById(R.id.nv_mail_etd);
        nv_pass = findViewById(R.id.nv_password_etd);
        nv_niveau = findViewById(R.id.nv_niveau_etd);
        enregistrer = findViewById(R.id.enregistrer_modif);


        //Attacher data au spinnner (niveau etd)
        nv_niveau.setAdapter(data);
        nv_niveau.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Selectionnez votre niveau d'etude")) {
                } else {
                    //ON selectionne un niveau
                    String niv = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                if (nv_niveau.getSelectedItem().toString().equals("")) {
                }
            }
        });

        SessionManager user=new SessionManager(edit_profil_etd.this,SessionManager.SESSION_REMEMBERME);

        enregistrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = nv_pass.getText().toString();
                String mail = nv_mail.getText().toString();
                String niveau= nv_niveau.getSelectedItem().toString();


                    //Num Apogee d'etudiant actif :user.getUserData_Session().get(SessionManager.KEY_NUM)
                    Query CheckUser = FirebaseDatabase.getInstance().getReference("etd").orderByChild("numApogee").equalTo(user.getUserData_Session().get(SessionManager.KEY_NUM));
                    CheckUser.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot)
                        {
                                String num_user=snapshot.child(user.getUserData_Session().get(SessionManager.KEY_NUM)).child("numApogee").getValue(String.class);
                                rootNode =FirebaseDatabase.getInstance();
                                reference = rootNode.getReference("etd");
                                if(!mail.equals(""))
                                    reference.child(num_user).child("mail").setValue(mail);
                                if(!pass.equals(""))
                                    reference.child(num_user).child("password").setValue(pass);
                                if(!niveau.matches("Seclectionnez votre niveau d'etude"))
                                    reference.child(num_user).child("niveau").setValue(niveau);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error)
                        {

                        }
                    });
                startActivity(new Intent(edit_profil_etd.this,profil_etd.class));
                finish();
            }
        });
    }

    public void back_profil_etd(View view) {
        startActivity(new Intent(edit_profil_etd.this,profil_etd.class));
        finish();
    }
}