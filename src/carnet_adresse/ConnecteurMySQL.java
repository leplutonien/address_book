package carnet_adresse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 * <p>
 * <span style="font-weight:bold;text-align: center;color:green">
 * Titre: ConnecteurMySQL</span><br>
 * <span style="font-weight:bold">
 * Description:</span><br>
 * Classe pour réaliser la connection à MySQL via JDBC</p>
 * @author OGOULOLA Marlin Yannick 
 * @version  1.08.10
 */
public class ConnecteurMySQL {
    /** l'objet Connection*/
    private static Connection con = null;
    /** Le driver MySQL*/
    private final String DRIVER = "com.mysql.jdbc.Driver";
    /**URL de connection */
    private final String URL = "jdbc:mysql://localhost:3306/carnet_adresse";
    /** Nom de d'un utlisateur d ela base de données */
    private final String USER = "root";
    /**Mot de passe de l'utilisateur*/
    private final String PASSWORD = "";
    /**
     * Constructeur de cette claase.
     * On met sa visibilité à private pour empêcher d'un objet de cette classe
     * soit crée en dehors d'ici
     */
    private ConnecteurMySQL()
    {
        try 
        {
            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL,USER,PASSWORD);  
        } 
        catch (ClassNotFoundException | SQLException e) 
        {
            JOptionPane.showMessageDialog(null,e.getMessage(),"Carnet d'adresse",JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }
    
    /**
     * Pour avoir L'objet Connexion lié à notre base de données
     * @return l'objet Connection.
     */
    public static Connection getInstance()
    {
        if(con == null)
            new ConnecteurMySQL();
        return con;
    }
    
}
