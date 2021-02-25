package android.example.easya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
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
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class edit_profil_vol extends AppCompatActivity {
    TextInputEditText nv_mail, nv_pass;
    Spinner nv_niveau;
    Button enregistrer;

    String  module ="";//Cette chaine de caractere va contenir la liste des modules qui se trouve dans la liste des modules selectionnes
    String module_info = "";//Cette chaine de caractere va contenir la liste des modules qui se trouve dans la liste des modules selectionnes

    Button mod_maths, mod_info;
    //text des modules selectionnes par le volontaire pour chaque discipline
    TextView mod_maths_selected, mod_info_selected;

    //liste des modules en maths a partir de lesquelles le volontaire va selectionner

    //LISTE DES MODULES DE MATHS
    String[] listModMaths_lc;
    String[] listModMaths_ma;
    String[] listModMaths_doc;
    String[] listModMaths;

    //LISTE DES MODULES D'INFO
    String[] listModInfo_lc;
    String[] listModInfo_ma;
    String[] listModInfo_doc;
    String[] listModInfo;

    //les element selectionne considere true
    boolean[] chekedmodMaths;
    boolean[] chekedmodInfo;

    //La liste de modules selectionnes
    ArrayList<Integer> modVol_Maths = new ArrayList<>();
    ArrayList<Integer> modVol_Info = new ArrayList<>();

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profil_vol);

        nv_mail = findViewById(R.id.mail_vol);
        nv_pass = findViewById(R.id.password_vol);
        nv_pass = findViewById(R.id.password_vol);
        ////////////////Bouton modules///////////
        mod_maths = findViewById(R.id.mod_maths);
        mod_info = findViewById(R.id.mod_info);
        ////////////////SPINNER/////////////////
        nv_niveau = findViewById(R.id.niveau_vol);
        enregistrer = findViewById(R.id.bouton_enregistrer_vol);

        mod_maths_selected=findViewById(R.id.selected_maths_mod);
        mod_info_selected=findViewById(R.id.selected_info_mod);

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
        //////////INFORMATIQUE///////////////////////////////////////////////
        listModInfo_lc = getResources().getStringArray(R.array.Info_LC);
        listModInfo_ma = getResources().getStringArray(R.array.Info_MA);
        listModInfo_doc = getResources().getStringArray(R.array.Info_DOC);

        //Attacher data au spinnner (niveau etd)
        nv_niveau.setAdapter(data);
        nv_niveau.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Seclectionnez votre niveau d'etude")) {
                    //On ne va rien faire
                } else {
                    //ON selectionne un niveau
                    String niv = parent.getItemAtPosition(position).toString();
                    //Afficher toast message du niveau selectionne
                    Toast.makeText(parent.getContext(), "Vous etes en " + niv, Toast.LENGTH_SHORT).show();
                    if (parent.getItemAtPosition(position).toString().equals("Licence")) {
                        listModMaths = listModMaths_lc;
                        listModInfo = listModInfo_lc;
                    } else if (parent.getItemAtPosition(position).equals("Master")) {
                        listModMaths = listModMaths_ma;
                        listModInfo = listModInfo_ma;
                    } else {
                        listModMaths = listModMaths_doc;
                        listModInfo = listModInfo_doc;
                    }
                    chekedmodMaths= new boolean[listModMaths.length];
                    chekedmodInfo= new boolean[listModInfo.length];

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                if (nv_niveau.getSelectedItem().toString().equals("")) {

                }
            }
        });


        //initialisation
      //  chekedmodMaths= new boolean[listModMaths.length];
     //   chekedmodInfo= new boolean[listModInfo.length];

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
       // mod_maths_selected=findViewById(R.id.selected_maths_mod);
        // mod_info_selected=findViewById(R.id.selected_info_mod);

        //initialisation
      // chekedmodMaths= new boolean[listModMaths.length];
      //  chekedmodInfo= new boolean[listModInfo.length];

    /*
        selected_maths.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //initialiser le dialog alert
                AlertDialog.Builder builder= new AlertDialog.Builder(edit_profil_vol.this);
                //Titre de l'alert dialog
                builder.setTitle("Selectionne tes modules");
                builder.setCancelable(false);
                builder.setMultiChoiceItems(listModMaths, chekedmodMaths, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        //Check condition
                        if(isChecked)
                        {
                            //Ajouter le module selectionne a la liste des modules
                            modVol_Maths.add(which);
                            Collections.sort(modVol_Maths);
                        }
                        else
                        {
                            //Pour le module deselectionne on va le supprimer de la liste
                            modVol_Maths.remove(which);
                        }
                    }
                });

                builder.setPositiveButton("Confirmer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //initialiser le string builder
                        //StringBuilder stringBuilder= new StringBuilder();
                        String module="";

                        TextView test_txt=findViewById(R.id.test);
                        for(int j=0;j>modVol_Maths.size();j++)
                        {//Pour concatener les modules selectionne
                            //stringBuilder.append(listModMaths[modVol_Maths.get(j)]);
                           module= module + listModMaths[modVol_Maths.get(j)];

                            //Verifier si j est le dernier element
                            if(j!=modVol_Maths.size()-1)
                            {
                                //stringBuilder.append(" ,");
                               module = module + "\n";
                            }
                            //selected_maths.setText("-> "+stringBuilder.length());
                        }

                        selected_maths.setText(module);
                        // SET TEXTVIEW
                    }
                });

                builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setNeutralButton("Effacer tout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for(int j=0;j<chekedmodMaths.length;j++)
                        {
                            //EFFFACER TOUTE LA SELECTION
                            chekedmodMaths[j]= false;
                            //EFFACER LA LISE DES MODULES
                            modVol_Maths.clear();
                            //EFFACER L'AFFICHAGE SUR LE TEXT VIEW
                            selected_maths.setText("");
                        }
                    }
                });
                //Affiche  le Dialog
                builder.show();
            }
        });
*/

        //LES BOUTONS OK CLEAR ALL  ET CANCEL DE LISTE DES MODULES A COCHER
        mod_maths.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(edit_profil_vol.this);
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
                        //String module = "";//Cette chaine de caractere va contenir la liste des modules qui se trouve dans la liste des modules selectionnes
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
        //LES BOUTONS OK CLEAR ALL  ET CANCEL DE LISTE DES MODULES A COCHER
        mod_info.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(edit_profil_vol.this);
                mBuilder.setTitle("Modules : Informatique");
                mBuilder.setMultiChoiceItems(listModInfo, chekedmodInfo, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                        //tester si le module est selectionne
                        if (isChecked) {
                            if (!modVol_Info.contains(position))//si l'element courant ne fait partie de la liste des modules selectionne il faut l'ajouter
                            {
                                modVol_Info.add(position);
                            } else //Si l'element selectionne fait partie deja de la liste des modules selectionnees apr le volontaire
                            {
                                modVol_Info.remove(position);
                            }
                        }
                    }
                });
                //Le bouton OK
                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //String module_info = "";//Cette chaine de caractere va contenir la liste des modules qui se trouve dans la liste des modules selectionnes
                        for (int i = 0; i < modVol_Info.size(); i++) {
                            module_info = module_info + listModInfo[modVol_Info.get(i)];
                            if (i != modVol_Info.size() - 1)//si l'element a la position i n'est pas le dernier on va faire un retour a la ligne
                            {
                                module_info = module_info + "\n ";
                            }
                        }
                        mod_info_selected.setText(module_info);
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
                        for (int i = 0; i < chekedmodInfo.length; i++)//Verifier la liste des elements selectionne pour effacer la selection
                        {
                            chekedmodInfo[i] = false;
                            modVol_Info.clear();//clear item in this list
                            mod_info_selected.setText("");//changer le text des modules selectionnes apres avoir supprimer certains
                        }
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        SessionManager user = new SessionManager(edit_profil_vol.this, SessionManager.SESSION_REMEMBERME);
                enregistrer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String pass = nv_pass.getText().toString();
                        String mail = nv_mail.getText().toString();
                        String niveau = nv_niveau.getSelectedItem().toString();

                        //Num Apogee d'etudiant actif :user.getUserData_Session().get(SessionManager.KEY_NUM)
                        Query CheckUser = FirebaseDatabase.getInstance().getReference("vol").orderByChild("num").equalTo(user.getUserData_Session().get(SessionManager.KEY_NUM));
                        CheckUser.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String num_user = snapshot.child(user.getUserData_Session().get(SessionManager.KEY_NUM)).child("num").getValue(String.class);
                                rootNode = FirebaseDatabase.getInstance();
                                reference = rootNode.getReference("vol");
                                if (!mail.equals(""))
                                    reference.child(num_user).child("mail").setValue(mail);
                                if (!pass.equals(""))
                                    reference.child(num_user).child("password").setValue(pass);
                                if (!niveau.matches("Seclectionnez votre niveau d'etude"))
                                    reference.child(num_user).child("niveau").setValue(niveau);
                                if (!mod_info_selected.equals(""))
                                {
                                    if(reference.child(num_user).child("modules_info").toString().contains(module_info))
                                        return;
                                    else
                                    {
                                        String mod=snapshot.child(num_user).child("modules_info").getValue(String.class);
                                        String nouveau_mod=mod+(module_info);
                                        reference.child(num_user).child("modules_info").setValue(nouveau_mod);
                                    }

                                }
                                if (!mod_maths_selected.equals(""))
                                {
                                    if(reference.child(num_user).child("modules_maths").toString().contains(module))
                                        return;
                                    else
                                    {
                                        String mod_ma=snapshot.child(num_user).child("modules_maths").getValue(String.class);
                                        String nouveau_maths=mod_ma+(module);
                                        reference.child(num_user).child("modules_maths").setValue(nouveau_maths);
                                    }
                                }

                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                        startActivity(new Intent(edit_profil_vol.this, profil_vol.class));
                        finish();
                    }
                });
            }

    public void go_profil(View view) {
        startActivity(new Intent(edit_profil_vol.this,profil_vol.class));
        finish();
    }

    public void back_profil_vol(View view) {
        startActivity(new Intent(this,profil_vol.class));
        finish();
    }
}

