package android.example.easya;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class inscription_fragment extends Fragment {

    Button btn_etd;
    Button btn_vol;
    View view;

    public inscription_fragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.inscription_fragment, container, false);
        btn_etd = view.findViewById(R.id.btn_choix_etd);
        btn_vol = view.findViewById(R.id.btn_choix_vol);

        //Taper sur le boutton volontaire pour aller sur l activite d'enregistrer volontaire
        btn_vol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), enregistrer_vol.class));
            }
        });

        //Taper sur le bouton etudiant pour aller sur l activite d'enregistrer etd
        btn_etd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), enregistrer_etd.class));
            }
        });

        return view;
    }
}
