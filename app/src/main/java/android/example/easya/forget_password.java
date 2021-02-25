package android.example.easya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class forget_password extends AppCompatActivity {

    private EditText Email, num;
    private Button suivant;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        Intent getvol = getIntent();
        int type_compte = getvol.getIntExtra("type_compte", 0);

        Email = findViewById(R.id.reset_email);
        num = findViewById(R.id.reset_num);
        suivant = findViewById(R.id.reset_btn);
        mAuth = FirebaseAuth.getInstance();

        TextView test = findViewById(R.id.test_reset);

        suivant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /////// LES DONNEES ENTRE PQR LE USER DANS LES FIELDS  :  MOT DE PASSE OUBLIE /////
                String numero = num.getText().toString();
                String mail = Email.getText().toString();
                if (type_compte == 1) {
                    Query CheckUser = FirebaseDatabase.getInstance().getReference("etd").orderByChild("numApogee").equalTo(numero);
                    CheckUser.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String Mail = snapshot.child(numero).child("mail").getValue().toString();
                            test.setText("mail de BD    " + Mail);
                            if (snapshot.exists()) {
                                num.setError(null);
                                String sys_Email = snapshot.child(numero).child("mail").getValue().toString();
                                if (sys_Email.equals(mail)) {
                                    Email.setError(null);
                                    String mail = snapshot.child(numero).child("mail").getValue().toString();
                                    mAuth.sendPasswordResetEmail(Email.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(forget_password.this, "Vérifie ta boîte mail", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(forget_password.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                } else
                                    Toast.makeText(forget_password.this, "Adresse mail incorrecte!", Toast.LENGTH_SHORT).show();
                            } else
                                Toast.makeText(forget_password.this, "Ce compte n'existe pas!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else {
                    Query CheckUser = FirebaseDatabase.getInstance().getReference("vol").orderByChild("num").equalTo(numero);
                    CheckUser.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                num.setError(null);
                                String sys_Email = snapshot.child(numero).child("mail").getValue().toString();
                                if (sys_Email.equals(mail)) {
                                    Email.setError(null);
                                    mAuth.sendPasswordResetEmail(Email.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(forget_password.this, "Vérifie ta boite mail", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(forget_password.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                } else
                                    Toast.makeText(forget_password.this, "email incorrect!", Toast.LENGTH_SHORT).show();
                            } else
                                Toast.makeText(forget_password.this, "Ce compte n'existe pas", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

            }

        });
    }

    public void get_back(View view) {
        startActivity(new Intent(this,identification_fragment.class));
        finish();
    }
}


