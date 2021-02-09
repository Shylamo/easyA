package android.example.easya;

import android.widget.Spinner;
import android.widget.TextView;

public class etudiants {

    Spinner niveau_etd;
    String nom, password,mail,numApogee,niveau;


    public etudiants() { }

    public Spinner getNiveau_etd() {
        return niveau_etd;
    }

    public void setNiveau_etd(Spinner niveau_etd) {
        this.niveau_etd = niveau_etd;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public etudiants(String nom, String mail, String num, String password, String niveau) {
        this.nom = nom;
        this.password = password;
        this.mail = mail;
        this.numApogee= num;
        this.niveau=niveau;
    }



    public String getNumApogee() {
        return numApogee;
    }

    public void setNumApogee(String numApogee) {
        this.numApogee = numApogee;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getNom() {
        return nom;
    }

    public String getPassword() {
        return password;
    }

    public String getMail() {
        return mail;
    }
}
