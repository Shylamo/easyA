package android.example.easya;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class enregistrer_vol extends AppCompatActivity {

    Button mod_maths,mod_info;
    TextView mod_maths_selected,mod_info_selected;//text des modules selectionnes par le volontaire pour chaque discipline

    //liste des modules en maths a partir de lesquelles le volontaire va selectionner
    String[] listModMaths;
    String[] listModInfo;
    //les element selectionne considere true
    boolean[] chekedmodMaths;
    boolean[] chekedmodInfo;
    //La liste de modules selectionnes
    ArrayList<Integer> modVol_Maths= new ArrayList<>();
    ArrayList<Integer> modVol_Info= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enregistrer_vol);

        mod_maths=findViewById(R.id.modules_maths);
        mod_info=findViewById(R.id.modules_info);
        mod_maths_selected=findViewById(R.id.selected_maths_mod);
        mod_info_selected=findViewById(R.id.selected_info_mod);

        //Recuperer la liste des modules en maths et info du String.xml
        listModMaths= getResources().getStringArray(R.array.Maths);
        listModInfo= getResources().getStringArray(R.array.Informatique);
        chekedmodMaths=new boolean[listModMaths.length];//le nombre des elements coche ne va pas depasser la taille de list des modules
        chekedmodInfo= new boolean[listModInfo.length];

        mod_maths.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder= new AlertDialog.Builder(enregistrer_vol.this);
                mBuilder.setTitle("Modules : Maths");
                mBuilder.setMultiChoiceItems(listModMaths, chekedmodMaths, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                        //tester si le module est selectionne
                        if(isChecked)
                        {
                            if(!modVol_Maths.contains(position))//si l'element courant ne fait partie de laliste des modules selectionne il faut l'ajouter
                            {
                                modVol_Maths.add(position);
                            }else //Si l'element selectionne fait partie deja de la liste des modules selectionnees apr le volontaire
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
                        String module="";//Cette chaine de caractere va contenir la liste des modules qui se trouve dans la liste des modules selectionnes
                        for(int i=0;i<modVol_Maths.size();i++)
                        {
                            module = module + listModMaths[modVol_Maths.get(i)];
                            if(i!=modVol_Maths.size()-1)//si l'element a la position i n'est pas le dernier on va faire un retour a la ligne
                            {
                                module = module +"\n ";
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
                        for(int i=0;i<chekedmodMaths.length;i++)//Verifier la liste des elements selectionne pour effacer la selection
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
    }

}


















