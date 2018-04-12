package carnet_adresse;

import java.sql.Connection;

/**
 * <p>
 * <span style="font-weight:bold;text-align: center;color:green">
 * Titre: DAO</span><br>
 * <span style="font-weight:bold">
 * Description:</span><br>
 * Classe abstraite,générique qui regroupe des méthodes de manipulation
 * de la base de données</p>
 * @author OGOULOLA Marlin Yannick 
 * @version  1.08.14
 * @param <T>
 */

public abstract class DAO<T>{
    protected Connection con = null;
    
    /**
     * Constructeur
     * @param con 
     */
    public DAO(Connection con)
    {
        this.con = con;
    }
    
    /**
     * Méthode pour enregistrer un objet de T.
     * @param obj
     * @return true si l'objet est bien enregistrer ou false sinon
     */
    public abstract boolean enregistrer(T obj);
    /**
     * Méthode pour modifier un objet de T
     * @param obj
     * @return true si l'objet est bien enregistrer ou false sinon
     */
    public abstract boolean modifier(T obj);
    /**
     * Méthode pour supprimer de la BDD un objet de T
     * @param obj
     * @return  true si l'objet est bien enregistrer ou false sinon
     */
    public abstract boolean supprimer(T obj);
    /**
     * Méthode pour recherchere dans la BDD un objet de T connaissant son id
     * @param id
     * @return 
     */
    public abstract T trouve(int id);
    /**
     * Affiche tous les objets T de BDD
     * @param tri
     * @return un tableau d'objets de T peut être null
     */
    public abstract T[] afficher(String tri);  
            
}
