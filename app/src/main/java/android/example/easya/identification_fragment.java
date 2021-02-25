package android.example.easya;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.se.omapi.Session;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class identification_fragment extends Fragment {
    ///ETUDIANT OU VOLONTAIRE
    boolean etd_checked;
    boolean vol_checked;

    Button btn_forgotten_pass;
    View view;

    int compte=0;

    EditText num, pass;
    Button login;
    RadioGroup type_compte;
    CheckBox rememberMe;
    FirebaseAuth auth;

    public identification_fragment() {
    }

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.identification_fragment, container, false);

        btn_forgotten_pass = view.findViewById(R.id.mot_de_passe_oublie);
        btn_forgotten_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), forget_password.class));

            }
        });

        num = view.findViewById(R.id.num_user);
        pass = view.findViewById(R.id.password_user);
        login = view.findViewById(R.id.button_connecter);
        type_compte= view.findViewById(R.id.radio_grp);
        rememberMe= view.findViewById(R.id.remember_me);
        auth= FirebaseAuth.getInstance();

        type_compte.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.etd)
                {
                    etd_checked=true;
                    vol_checked=false;
                    compte=1;
                }
                else if(checkedId==R.id.vol)
                {
                    vol_checked=true;
                    etd_checked=false;
                    compte=2;
                }

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validate_num() | !validate_password())
                    return;

                //get donnees
                String numero = num.getText().toString();
                final String password = pass.getText().toString();


                if(rememberMe.isChecked()) {
                    SessionManager sessionmanager = new SessionManager(getActivity(),SessionManager.SESSION_REMEMBERME);
                    sessionmanager.createRememberMeSession(numero,password);
                }
                //DATA BASE
                if(etd_checked==true)
                {
                    Query CheckUser = FirebaseDatabase.getInstance().getReference("etd").orderByChild("numApogee").equalTo(numero);
                    CheckUser.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                num.setError(null);
                                String sys_password = snapshot.child(numero).child("password").getValue(String.class);
                                if (sys_password.equals(password)) {
                                    pass.setError(null);
                                    //GET USER NAME/NUM
                                    String nom=snapshot.child(numero).child("nom").getValue(String.class);
                                    String num=snapshot.child(numero).child("numApogee").getValue(String.class);
                                    String mail=snapshot.child(numero).child("mail").getValue(String.class);
                                    String pass=snapshot.child(numero).child("password").getValue(String.class);
                                    String niveau=snapshot.child(numero).child("niveau").getValue(String.class);
                                    //CREATE A SESSION ------ STORE VALUE ----- UPDATE DATA
                                    SessionManager sessionmanager=new SessionManager(getActivity(),SessionManager.SESSION_REMEMBERME);
                                    sessionmanager.createLoginSession(nom,num,mail,pass,niveau);

                                    auth.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if(task.isSuccessful()) {
                                                Toast.makeText(getActivity(), "Successful", Toast.LENGTH_LONG).show();
                                            }else
                                                Toast.makeText(getActivity(),"Echec de s'authentifier", Toast.LENGTH_LONG).show();
                                        }
                                    });

                                    Intent vol=new Intent(getActivity(),forget_password.class);
                                    vol.putExtra("type_compte",compte);
                                    startActivity(vol);

                                    startActivity(new Intent(getActivity(),profil_etd.class));
                                    Toast.makeText(getActivity(),"nom : "+nom+"\n"+num,Toast.LENGTH_SHORT).show();

                                } else
                                    Toast.makeText(getActivity(), "mot de passe incorrecte!", Toast.LENGTH_SHORT).show();
                            } else
                                {
                                Toast.makeText(getActivity(), "Etudiant n'existe pas!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else if(vol_checked==true)
                {
                    Query CheckUser = FirebaseDatabase.getInstance().getReference("vol").orderByChild("num").equalTo(numero);
                    CheckUser.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                num.setError(null);
                                String sys_password = snapshot.child(numero).child("password").getValue(String.class);
                                if (sys_password.equals(password)) {
                                    pass.setError(null);
                                    String nom=snapshot.child(numero).child("nom").getValue(String.class);
                                    String num=snapshot.child(numero).child("num").getValue(String.class);
                                    String mail=snapshot.child(numero).child("mail").getValue(String.class);
                                    String pass=snapshot.child(numero).child("password").getValue(String.class);
                                    String niveau=snapshot.child(numero).child("niveau").getValue(String.class);
                                    //CREATE A SESSION ------ STORE VALUE ----- UPDATE DATA
                                    SessionManager sessionmanager=new SessionManager(getActivity(),SessionManager.SESSION_REMEMBERME);
                                    sessionmanager.createLoginSession(nom,num,mail,pass,niveau);

                                auth.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if(task.isSuccessful()) {
                                                Toast.makeText(getActivity(), "Successful", Toast.LENGTH_LONG).show();
                                            }else
                                                Toast.makeText(getActivity(),"Echec de s'authentifier", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                    Intent vol=new Intent(getActivity(),forget_password.class);
                                    vol.putExtra("type_compte",compte);
                                    startActivity(vol);

                                    startActivity(new Intent(getActivity(),profil_vol.class));
                                } else
                                    Toast.makeText(getActivity(), "mot de passe incorrecte!", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(getActivity(), "Ce volontaire n'existe pas!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            }
        });
       return view;
    }

    private Boolean validate_password() {
        String val = pass.getText().toString();
        if (val.isEmpty()) {
            pass.setError("Champs obligatoire");//affiche le message d'erreur si le champs est vide
            return false;
        } else {
            pass.setError(null);
            return true;
        }
    }

    private Boolean validate_num() {
        String val = num.getText().toString();
        if (val.isEmpty()) {
            num.setError("Champs obligatoire");//affiche le message d'erreur si le champs est vide
            return false;
        } else {
            num.setError(null);
            return true;
        }
    }

}