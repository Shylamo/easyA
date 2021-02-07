package android.example.easya;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class enregistrer_etd extends AppCompatActivity {

    //Variables
    TextInputEditText regnom, regmail, regpassword, regnum;//reg for registration
    Button signup;
    Spinner regniveau_etd;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enregistrer_etd);

        regnom = findViewById(R.id.nom_etd);
        regmail = findViewById(R.id.mail_etd);
        regpassword = findViewById(R.id.password_etd);
        regnum = findViewById(R.id.num_etd);

        regniveau_etd= findViewById(R.id.niveau_etd);

        List<String> niveau_etd=new ArrayList<>();
        niveau_etd.add(0,"Seclectionnez votre niveau d'etude");
        niveau_etd.add("Licence SMI");
        niveau_etd.add("Licence SMA");
        niveau_etd.add("Master STRI");
        niveau_etd.add("Master ISI");
        niveau_etd.add("Master IEREE");
        niveau_etd.add("Doctorat");

        //styler le spinner
        ArrayAdapter<String> data;
        data=new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,niveau_etd);
        data.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Attacher data au spinnner (niveau etd)
        regniveau_etd.setAdapter(data);
        regniveau_etd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if(parent.getItemAtPosition(position).equals("Seclectionnez votre niveau d'etude"))
                {
                    //On ne va rien faire
                }
                else {    //ON selectionne un niveau
                    String niv = parent.getItemAtPosition(position).toString();
                    //Afficher tost message du niveau selectionne
                    Toast.makeText(parent.getContext(),"Vous etes "+niv, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                if(regniveau_etd.getSelectedItem().toString().equals(""))
                {
 //                   ((TextView)regniveau.getChildAt(0)).setError("Champs obligatoire");
                }
            }
        });



    }

    //Validation des donnees etd
    private Boolean validate_name() {
        String val = regnom.getText().toString();
        if (val.isEmpty()) {
            regnom.setError("Champs obligatoire");//affiche le message d'erreur si le champs est vide
            return false;
        } else {
            regnom.setError(null);
            return true;
        }
    }
    private Boolean validate_mail() {
        String val = regmail.getText().toString();
        String mail_syntaxe = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            regmail.setError("Champs obligatoire");//affiche le message d'erreur si le champs est vide
            return false;
        } else if (!val.matches(mail_syntaxe)) {
            regmail.setError("Adresse mail non valide");//affiche le message d'erreur si la syntaxe du mail est fausse
            return false;
        } else {
            regmail.setError(null);
            //        nom.setErrorEnabled(false);// remove the error message after
            return true;
        }
    }
    private Boolean validate_password() {
        String val = regpassword.getText().toString();
        String password_syntaxe = ".{4,}"; //au moins 4 caracteres n'impote lesquels

        if (val.isEmpty()) {
            regpassword.setError("Champs obligatoire");//affiche le message d'erreur si le champs est vide
            return false;
        } else if (!val.matches(password_syntaxe)) {
            regpassword.setError("Mot de passe faible");//affiche le message d'erreur si la syntaxe du mail est fausse
            return false;
        } else {
            regpassword.setError(null);
            //        nom.setErrorEnabled(false);// remove the error message after
            return true;
        }
    }
    private Boolean validate_num() {
        String val = regnum.getText().toString();
        if (val.isEmpty()) {
            regnum.setError("Champs obligatoire");//affiche le message d'erreur si le champs est vide
            return false;
        } else {
            regnum.setError(null);
            return true;
        }
    }
    //Enregistrer les donnees etd dans le FireBase en cliquant sur "enregistrer"
    public void register_etd(View view) {
        if (!validate_name() | !validate_mail() | !validate_password() | !validate_num())
            return;
            //From EditText to String

            String name = regnom.getText().toString();
            String mail = regmail.getText().toString();
            String password = regpassword.getText().toString();
            String num = regnum.getText().toString();
            String niveau = regniveau_etd.getSelectedItem().toString();
            etudiants etudiant = new etudiants(name, mail, num, password,niveau);

                rootNode =FirebaseDatabase.getInstance();
                reference = rootNode.getReference("etd");

            //inserer les valeurs pour chaque etudiant num est unique --> id_etd
            reference.child(num).setValue(etudiant);

    }
}