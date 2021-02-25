package android.example.easya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Envoie_mail extends AppCompatActivity {

    EditText Destina, Obj, Msg;
    Button btnenvoie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_envoie_mail);
        Destina = findViewById(R.id.destinataire);
        Obj = findViewById(R.id.objet_exp);
        Msg = findViewById(R.id.message_exp);
        btnenvoie = findViewById(R.id.btn_envoyer);

        btnenvoie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();




            }
        });
    }

    private void sendMail()
    {
        String recipientsList= Destina.getText().toString();
        String[] recipients = recipientsList.split(",");
        String objet = Obj.getText().toString();
        String message = Obj.getText().toString();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL,recipients);
        intent.putExtra(Intent.EXTRA_SUBJECT,objet);
        intent.putExtra(Intent.EXTRA_TEXT,message);

        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent,"choisir un mail client"));

    }

    public void go_profil_vol(View view) {
        startActivity(new Intent(this,profil_vol.class));
        finish();
    }
}
