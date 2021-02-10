package android.example.easya;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class identification_fragment extends Fragment {
    Button btn_forgotten_pass;
    View view;

    EditText mail,password;
    Button login;
    FirebaseAuth fAuth;

    public identification_fragment() {}
    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.identification_fragment, container, false);
        btn_forgotten_pass= view.findViewById(R.id.mot_de_passe_oublie);
        btn_forgotten_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),forget_password.class));
            }
        });

     mail= view.findViewById(R.id.mail_user);
     password= view.findViewById(R.id.password_user);
     fAuth= FirebaseAuth.getInstance();
     login= view.findViewById(R.id.button_connecter);
     login.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             String email=mail.getText().toString();
             String pass=password.getText().toString();

             if(TextUtils.isEmpty(email))
             {
                 mail.setError("Champs obligatoire");
                 return;
             }
             if(TextUtils.isEmpty(pass))
             {
                 password.setError("Champs obligatoire");
                 return;
             }

             //Authentification d'utilisateur
             fAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                 @Override
                 public void onComplete(@NonNull Task<AuthResult> task) {
                    //Check if the login is successful
                     if(task.isSuccessful())
                     {
                         //verifier si l'utilisateur est etudiant ou volontaire pour le diriger
                         startActivity(new Intent(getActivity(), profil_etd.class));
                     }
                 }
             });

         }
     });



        return view;
    }
}