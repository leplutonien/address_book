package carnet_adresse;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * <span style="font-weight:bold;text-align: center;color:green">
 * Titre: Personne</span><br>
 * <span style="font-weight:bold">
 * Description:</span><br>
 * Classe pour créer les objets du carnet d'adresses</p>
 * @author OGOULOLA Marlin Yannick 
 * @version  1.08.14
 */
public class Personne {
    private int id;
    private String nom;
    private String prenom;
    private String tel_domicile;
    private String tel_bureau;
    private String tel_portable;
    private String email; 
    private String remarque;

    /**
     * Contructeur permettant de créer les objets de types Personne.
     * @param id l'ordre d'enregistrement
     * @param nom nom de la personne. Ne peut être null.
     * @param prenom prenom de la personne. Ne peut être null.
     * @param tel_domicile Numéro de téléphone du domicile de la personne. Peut être null.
     * @param tel_bureau   Numéro de téléphone du bureau de la personne. Peut être null.
     * @param tel_portable Numéro de téléphone de portable de la personne. Ne peut être null.
     * @param email Email de la personne. Ne peut être null.
     * @param remarque Remarque sur la personne. Peut être null.
     */
    public Personne(int id, String nom, String prenom,String tel_portable, String email, String tel_domicile, String tel_bureau, String remarque) throws NullPointerException,EmailException
    {
        if(nom == null || prenom == null || tel_portable == null || email == null)
        {
            throw new NullPointerException("L'une des variables nom, prenom, tel_portable et email ne peut être null");
           
        }
        else
        {
            Pattern pattern = Pattern.compile("^\\b[a-zA-Z0-9._-]+@[a-z0-9._-]{2,}\\.[a-z]{2,4}\\b$");
            Matcher matcher = pattern.matcher(email);
            if(!matcher.find())
            {
              throw new EmailException("Email incorrect");
            }
            else
            {
                this.id = id;
                this.nom = nom;
                this.prenom = prenom;
                this.tel_domicile = tel_domicile;
                this.tel_bureau = tel_bureau;
                this.tel_portable = tel_portable;
                this.email = email;
                this.remarque = remarque;                
            }
        }        
    }
/**
 * Méthode pour avoir l'id d'enregistrement 
 * d'un objet Personne
 * @return id
 */
    public int getId() {
        return id;
    }
/**
 * Méthode pour modifier l'id d'enregistrement 
 * d'un objet Personne
 * @param id le nouveau id
 */
    public void setId(int id) {
        this.id = id;
    }
/**
 * Méthode pour avoir le nom d'un objet Personne
 * @return nom
 */
    public String getNom() {
        return nom;
    }
/**
 * Méthode pour modifier le nom d'un objet Personne
 * @param nom le nouveau nom
 */
    public void setNom(String nom) {
        this.nom = nom;
    }
/**
 * Méthode pour avoir le prenom d'un objet Personne
 * @return prenom
 */
    public String getPrenom() {
        return prenom;
    }
/**
 * Méthode pour modifier le prenom d'un objet Personne
 * @param prenom le nouveau prenom
 */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
/**
 * Méthode pour avoir le Tel_domicile d'un objet Personne
 * @return tel_domicile
 */
    public String getTel_domicile() {
        return tel_domicile;
    }
/**
 * Méthode pour modifier le tel_domicile d'un objet Personne
 * @param tel_domicile le nouveau tel_domicile
 */
    public void setTel_domicile(String tel_domicile) {
        this.tel_domicile = tel_domicile;
    }
/**
 * Méthode pour avoir le tel_bureau d'un objet Personne
 * @return tel_bureau
 */
    public String getTel_bureau() {
        return tel_bureau;
    }
/**
 * Méthode pour modifier le tel_bureau d'un objet Personne
 * @param tel_bureau le nouveau tel_bureau
 */
    public void setTel_bureau(String tel_bureau) {
        this.tel_bureau = tel_bureau;
    }
/**
 * Méthode pour avoir le tel_portable d'un objet Personne
 * @return tel_portable
 */
    public String getTel_portable() {
        return tel_portable;
    }
/**
 * Méthode pour modifier le tel_portable d'un objet Personne
 * @param  tel_portable
 */
    public void setTel_portable(String tel_portable) {
        this.tel_portable = tel_portable;
    }
/**
 * Méthode pour avoir le email d'un objet Personne
 * @return email
 */
    public String getEmail() {
        return email;
    }
/**
 * Méthode pour modifier le email d'un objet Personne
 * @param  email
 */
    public void setEmail(String email) {
        this.email = email;
    }
/**
 * Méthode pour avoir la remarque sur l' objet Personne
 * @return remarque
 */
    public String getRemarque() {
        return remarque;
    }
/**
 * Méthode pour modifier la remarque sur l'objet Personne
 * @param  remarque
 */
    public void setRemarque(String remarque) {
        this.remarque = remarque;
    }    
}
