package android.example.easya;

public class etudiants {
    String nom, password,mail,numApogee;

    public etudiants() { }

    public etudiants(String nom, String mail, String num, String password) {
        this.nom = nom;
        this.password = password;
        this.mail = mail;
        this.numApogee= num;
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
