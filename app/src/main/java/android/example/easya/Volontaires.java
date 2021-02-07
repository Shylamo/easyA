package android.example.easya;

public class Volontaires {

    String nom,num,niveau,mail,password,modules_maths,modules_info;

    public Volontaires() { }

    public Volontaires(String nom, String num, String niveau, String mail, String password,String maths,String info) {
        this.nom = nom;
        this.num = num;
        this.niveau = niveau;
        this.mail = mail;
        this.password = password;
        this.modules_info=info;
        this.modules_maths=maths;
    }

    public String getModules_maths() {
        return modules_maths;
    }

    public void setModules_maths(String modules_maths) {
        this.modules_maths = modules_maths;
    }

    public String getModules_info() {
        return modules_info;
    }

    public void setModules_info(String modules_info) {
        this.modules_info = modules_info;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
