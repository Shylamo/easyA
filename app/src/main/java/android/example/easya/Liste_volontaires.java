package android.example.easya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class Liste_volontaires extends AppCompatActivity {
    int numero_mod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_volontaires);

        Intent volontaires = getIntent();

        List<String> liste_vol_ma = (List<String>) volontaires.getSerializableExtra("volontaire_ma");
        List<String> liste_vol_info = (List<String>) volontaires.getSerializableExtra("volontaire_info");
        numero_mod = volontaires.getIntExtra("module", 0);

        List<String> liste = new ArrayList<>();

        ListView listView = findViewById(R.id.trouver_volontaire);


        ArrayList<volontaire> list_vol = new ArrayList<>();
        if (numero_mod == 1) {
            liste = liste_vol_ma;
        } else
            liste = liste_vol_info;

        if(liste.isEmpty())
        {
                startActivity(new Intent(Liste_volontaires.this,pas_de_vol.class));
                finish();
        }
        else
        {

            DatabaseReference root = FirebaseDatabase.getInstance().getReference();
            DatabaseReference vol_ref = root.child("vol");
            List<String> finalListe = liste;
            vol_ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        boolean exists = finalListe.contains(dataSnapshot.child("num").getValue(String.class));
                        if (exists) {
                            String nom=dataSnapshot.child("nom").getValue(String.class);
                            String niveau=dataSnapshot.child("niveau").getValue(String.class);
                            String mail=dataSnapshot.child("mail").getValue(String.class);
                            list_vol.add(new volontaire(R.drawable.user, nom, mail, niveau));

                            volontaire_adap vol_adapter = new volontaire_adap(Liste_volontaires.this, R.layout.vol_desplay, list_vol);
                            listView.setAdapter(vol_adapter);
                        }


                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        }


    public void back_profil_etd(View view) {
        startActivity(new Intent(this,profil_etd.class));
        finish();
    }
}

