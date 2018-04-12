package carnet_adresse;

import java.sql.Connection;

/**
 * <p>
 * <span style="font-weight:bold;text-align: center;color:green">
 * Titre: DAOFactory</span><br>
 * <span style="font-weight:bold">
 * Description:</span><br>
 * Classe pour la gestion des DAO</p>
 * @author OGOULOLA Marlin Yannick 
 * @version  1.08.14
 */

public class DAOFactory {
    
    private static final Connection con = ConnecteurMySQL.getInstance();
    private static PersonneDAO personne_dao = null;
    
    /**
     * Méthode retournant un objet Personne interagissant avec la BDD
     * @return 
     */
    public static DAO getPersonneDAO()
    {
        if(personne_dao == null)
            personne_dao = new PersonneDAO(con);
        return personne_dao;
    }
    
}
