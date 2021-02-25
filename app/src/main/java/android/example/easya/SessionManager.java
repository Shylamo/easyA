package android.example.easya;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;
import java.util.HashMap;

public class SessionManager {

    //LES VARIABLES
    SharedPreferences usersession;
    SharedPreferences.Editor editor;

    //Quelle activite est en train d'appeller cette classe
    Context context;


    //Session names
    public static final String SESSION_USERSESSION = "userLoginSession";
    public static final String SESSION_REMEMBERME = "rememberMe";

    private static final String IS_LOGIN = "connecte";//static final car l'utilisateur ne doit pas y avoir acces et faire des modifications
    public static final String KEY_NOM = "nom";
    public static final String KEY_NUM = "numApogee";
    public static final String KEY_MAIL = "mail";
    public static final String KEY_PASS = "password";
    public static final String KEY_NIVEAU = "niveau";


    // Remember me variables
    private static final String IS_REMEMBERME = "IsRememberMe";
    public static final String KEY_SESSIONNUM = "numApogee";
    public static final String KEY_SESSIONPASS = "password";


    public SessionManager(Context _context,String sessionName) {
        context = _context;
        usersession = context.getSharedPreferences(sessionName, Context.MODE_PRIVATE);
        editor = usersession.edit();//Allow to this usersession to update.....
    }

    public void createLoginSession(String nom, String numApogee, String mail, String pass, String niveau) {
        editor.putBoolean(IS_LOGIN, true);
        //ENREGISTREMENT DES DONNES SOUS FORME CLE VALEUR
        editor.putString(KEY_NOM, nom);
        editor.putString(KEY_NUM, numApogee);
        editor.putString(KEY_MAIL, mail);
        editor.putString(KEY_PASS, pass);
        editor.putString(KEY_NIVEAU, niveau);
        editor.commit();//TO STORE DATA IN THE SESSION
    }

    public HashMap<String, String> getUserData_Session() {
        HashMap<String, String> userData = new HashMap<String, String>();
        userData.put(KEY_NOM, usersession.getString(KEY_NOM, null));
        userData.put(KEY_NUM, usersession.getString(KEY_NUM, null));
        userData.put(KEY_MAIL, usersession.getString(KEY_MAIL, null));
        userData.put(KEY_PASS, usersession.getString(KEY_PASS, null));
        userData.put(KEY_NIVEAU, usersession.getString(KEY_NIVEAU, null));
        return userData;
    }

    //UPDATE USER DATA
    public void setKeyNom(String nom) {
        editor = usersession.edit();
        editor.putString(KEY_NOM, nom);
        editor.apply();
    }

    public void setKeyPass(String pass) {
        editor = usersession.edit();
        editor.putString(KEY_PASS, pass);
        editor.commit();
    }

    public void setKeyMail(String mail) {
        editor = usersession.edit();
        editor.putString(KEY_MAIL, mail);

        editor.apply();
    }

    public void setKeyNiveau(String niveau) {
        editor = usersession.edit();
        editor.putString(KEY_NIVEAU, niveau);
        editor.commit();
    }


    //CONNECTE OU NON
    public boolean checklogin() {
        if (usersession.getBoolean(IS_LOGIN, true)) {
            return true;
        } else
            return false;
    }


    public void logoutSession() {
        editor.clear();
        editor.commit();
    }

    /// Remember Me session Functions

    public void createRememberMeSession(String numApogee,String pass)
    {
        editor.putBoolean(IS_REMEMBERME,true);
        //ENREGISTREMENT DES DONNES SOUS FORME CLE VALEUR

        editor.putString(KEY_SESSIONNUM,numApogee);
        editor.putString(KEY_SESSIONPASS,pass);
        editor.commit();//TO STORE DATA IN THE SESSION
    }

    public HashMap<String, String> getRememeberMeData_Session() {
        HashMap<String, String> userData = new HashMap<String, String>();

        userData.put(KEY_SESSIONNUM, usersession.getString(KEY_SESSIONNUM, null));
        userData.put(KEY_SESSIONPASS, usersession.getString(KEY_SESSIONPASS, null));

        return userData;
    }

    public boolean checkRememeberMe() {
        if (usersession.getBoolean(IS_REMEMBERME, false)) {
            return true;
        } else
            return false;
    }


}
