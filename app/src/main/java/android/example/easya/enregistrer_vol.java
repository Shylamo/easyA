package android.example.easya;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
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

public class enregistrer_vol extends AppCompatActivity {

    FirebaseDatabase rootNode;
    DatabaseReference reference;
    TextInputEditText regnom_vol,regnum_vol,regmail_vol,regpassword_vol;

    Spinner regniveau_vol;
    Button mod_maths, mod_info,reglogin;
    //text des modules selectionnes par le volontaire pour chaque discipline
    TextView mod_maths_selected, mod_info_selected;

    //liste des modules en maths a partir de lesquelles le volontaire va selectionner

    //LISTE DES MODULES DE MATHS
    String[] listModMaths_lc;
    String[] listModMaths_ma;
    String[] listModMaths_doc;
    String[] listModMaths;

    //LISTE DES MODULES D'INFO
    String[] listModInfo;

    //les element selectionne considere true
    boolean[] chekedmodMaths;
    boolean[] chekedmodInfo;

    //La liste de modules selectionnes
    ArrayList<Integer> modVol_Maths = new ArrayList<>();
    ArrayList<Integer> modVol_Info = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enregistrer_vol);

        mod_maths = findViewById(R.id.modules_maths);
        mod_info = findViewById(R.id.modules_info);
        mod_maths_selected = findViewById(R.id.selected_maths_mod);
        mod_info_selected = findViewById(R.id.selected_info_mod);

        regniveau_vol = findViewById(R.id.niveau_volontaire);

        //SELECTIONNER LE NIVEAU DU VOLONTAIRE
        List<String> niveau_Vol = new ArrayList<>();
        niveau_Vol.add(0, "Seclectionnez votre niveau d'etude");
        niveau_Vol.add("Licence");
        niveau_Vol.add("Master");
        niveau_Vol.add("Doctorat");

        //styler le spinner
        ArrayAdapter<String> data;
        data = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, niveau_Vol);
        data.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //RECUPERATION DES LISTES DE MODULES SELON LE NIVEAU DU STRING.XML
        listModMaths_lc = getResources().getStringArray(R.array.Maths_LC);
        listModMaths_ma = getResources().getStringArray(R.array.Maths_MA);
        listModMaths_doc = getResources().getStringArray(R.array.Maths_DOC);

        //Attacher data au spinnner (niveau etd)
        regniveau_vol.setAdapter(data);
        regniveau_vol.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if (parent.getItemAtPosition(position).equals("Seclectionnez votre niveau d'etude")) {
                    //On ne va rien faire
                } else {
                    //ON selectionne un niveau
                    String niv = parent.getItemAtPosition(position).toString();
                    //Afficher toast message du niveau selectionne
                    Toast.makeText(parent.getContext(), "Vous etes en " + niv, Toast.LENGTH_SHORT).show();
                    if(parent.getItemAtPosition(position).toString().equals("Licence"))
                        listModMaths= getResources().getStringArray(R.array.Maths_LC);
                    else if(parent.getItemAtPosition(position).equals("Master"))
                        listModMaths= getResources().getStringArray(R.array.Maths_MA);
                    else
                        listModMaths= getResources().getStringArray(R.array.Maths_DOC);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                if (regniveau_vol.getSelectedItem().toString().equals(""))
                {

                }
            }
        });

        //Recuperer la liste des modules en maths et info du String.xml
       // listModInfo = getResources().getStringArray(R.array.Informatique);

        //ATTRIBUER A CHAQUE NIVEAU D'ETUDE LA LISTE DES MODULES EN MATHS
    //    chekedmodMaths = new boolean[listModMaths.length];//le nombre des elements coche ne va pas depasser la taille de list des modules
    //    chekedmodInfo = new boolean[listModInfo.length];

        //LES BOUTONS OK CLEAR ALL  ET CANCEL DE LISTE DES MODULES A COCHER
        mod_maths.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(enregistrer_vol.this);
                mBuilder.setTitle("Modules : Maths");
                mBuilder.setMultiChoiceItems(listModMaths, chekedmodMaths, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                        //tester si le module est selectionne
                        if (isChecked) {
                            if (!modVol_Maths.contains(position))//si l'element courant ne fait partie de laliste des modules selectionne il faut l'ajouter
                            {
                                modVol_Maths.add(position);
                            } else //Si l'element selectionne fait partie deja de la liste des modules selectionnees apr le volontaire
                            {
                                modVol_Maths.remove(position);
                            }
                        }
                    }
                });
                //Le bouton OK
                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String module = "";//Cette chaine de caractere va contenir la liste des modules qui se trouve dans la liste des modules selectionnes
                        for (int i = 0; i < modVol_Maths.size(); i++) {
                            module = module + listModMaths[modVol_Maths.get(i)];
                            if (i != modVol_Maths.size() - 1)//si l'element a la position i n'est pas le dernier on va faire un retour a la ligne
                            {
                                module = module + "\n ";
                            }
                        }
                        mod_maths_selected.setText(module);
                    }
                });
                mBuilder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                mBuilder.setNeutralButton("Clear", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = 0; i < chekedmodMaths.length; i++)//Verifier la liste des elements selectionne pour effacer la selection
                        {
                            chekedmodMaths[i] = false;
                            modVol_Maths.clear();//clear item in this list
                            mod_maths_selected.setText("");//changer le text des modules selectionnes apres avoir supprimer certains
                        }
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });

        // Donner du xml a la l'activite "enregistrer_vol"
        regnom_vol = findViewById(R.id.nom_vol);
        regnum_vol = findViewById(R.id.num_vol);
        regpassword_vol = findViewById(R.id.password_vol);
        regmail_vol = findViewById(R.id.mail_vol);
        regniveau_vol = findViewById(R.id.niveau_volontaire);

        reglogin= findViewById(R.id.bouton_enregistrer_vol);
        reglogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("vol");

                //getting all the values
                String name = regnom_vol.getText().toString();
                String mail = regmail_vol.getText().toString();
                String password = regpassword_vol.getText().toString();
                String num = regnum_vol.getText().toString();
                String niveau = regniveau_vol.getSelectedItem().toString();
                String maths= mod_maths_selected.getText().toString();
                String info= mod_info_selected.getText().toString();
                Volontaires volontaire= new Volontaires(name,num,niveau,mail,password,maths,info);
                reference.child(num).setValue(volontaire);

                //Afficher profil volontaire
                startActivity(new Intent(enregistrer_vol.this,profil_vol.class));
            }
        });
    }
}

