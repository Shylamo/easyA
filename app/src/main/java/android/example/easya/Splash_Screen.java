package android.example.easya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class Splash_Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen);

        //Splash screen for 3s
        Thread thread = new Thread()
        {
            public void run()
            {
                try {
                    sleep(2000);//in milliseconds
                    //Intent pour ouvrir l'activite de l'inscription
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);//getApplicationContext get l'activite courante
                    startActivity(intent);
                    //On ajoute finish pour fermer le splashscreen
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
        //Direction Android.Manifest pour ouvrir la premiere activite> On ajoute le main activite

    }
}