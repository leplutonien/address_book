package carnet_adresse;

/**
 * <p>
 * <span style="font-weight:bold;text-align: center;color:green">
 * Titre: EmailException</span><br>
 * <span style="font-weight:bold">
 * Description:</span><br>
 * Classe pour les exceptions de mails incorrects</p>
 * @author OGOULOLA Marlin Yannick 
 * @version  1.08.14
 */
public class EmailException extends RuntimeException{
    
    public EmailException()
    {
        super();
    }
    
    public EmailException(String message)
    {
        super(message);
    }
    
}
