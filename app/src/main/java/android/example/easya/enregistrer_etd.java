package android.example.easya;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class enregistrer_etd extends AppCompatActivity {

    //Variables
    TextInputEditText regnom, regmail, regpassword,regnum;//reg for registration
    Button signup;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enregistrer_etd);

        regnom= findViewById(R.id.nom_etd);
        regmail= findViewById(R.id.mail_etd);
        regpassword= findViewById(R.id.password_etd);
        signup= findViewById(R.id.bouton_enregistrer);
        regnum= findViewById(R.id.num_etd);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootNode =FirebaseDatabase.getInstance();
                reference = rootNode.getReference("etd");

                //From EditText to String
                String name=regnom.getText().toString();
                String mail=regmail.getText().toString();
                String password=regpassword.getText().toString();
                String num= regnum.getText().toString();

                etudiants etudiant= new etudiants(name,mail,num,password);

                //inserer les valeurs pour chaque etudiant num est unique --> id_etd
                reference.child(num).setValue(etudiant);
            }
        });
    }

}