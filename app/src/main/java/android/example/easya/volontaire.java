package android.example.easya;

public class volontaire {
    int image;
    String nom;
    String mail;
    String niveau;

    public volontaire(int image, String nom, String mail, String niveau) {
        this.image = image;
        this.nom = nom;
        this.mail = mail;
        this.niveau = niveau;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }
}
