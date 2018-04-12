package carnet_adresse;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 * <p>
 * <span style="font-weight:bold;text-align: center;color:green">
 Titre: PersonneDAO</span><br>
 * <span style="font-weight:bold">
 * Description:</span><br>
 * Classe qui asssure la manipulation de la table personne à travers les méthode de la classe DAO
 * </p>
 * @author OGOULOLA Marlin Yannick 
 * @version  1.08.14
 */

public class PersonneDAO  extends DAO<Personne>{

    public PersonneDAO(Connection con) {
        super(con);
    }
    
    private int idActu()
    {
        int ret = 0;
        try 
        {
            Statement stm = this.con.createStatement();
            ResultSet res = stm.executeQuery("SELECT MAX(id) as nb FROM personne");
            res.next();
            ret = res.getInt(1) + 1;            
        } 
        catch (SQLException ex) 
        {
            JOptionPane.showMessageDialog(null,ex.getMessage(),"Carnet d'adresse",JOptionPane.ERROR_MESSAGE);
        }        
        return  ret;        
    }
    
    @Override
    public boolean enregistrer(Personne obj) 
    {
        boolean ret = false;
        String req = "INSERT INTO personne(id,nom,prenom,tel_domicile,tel_bureau,tel_portable,email,remarque)";
        req += " VALUES(?,?,?,?,?,?,?,?)";
        try 
        {
            PreparedStatement  ps = this.con.prepareStatement(req);
            ps.setInt(1, idActu());
            ps.setString(2, obj.getNom());
            ps.setString(3, obj.getPrenom());
            ps.setString(4, obj.getTel_domicile());
            ps.setString(5, obj.getTel_bureau());
            ps.setString(6, obj.getTel_portable());
            ps.setString(7, obj.getEmail());
            ps.setString(8, obj.getRemarque());   
            ps.executeUpdate();
            
            ret = true;
        }
        catch (SQLException ex) 
        {
            JOptionPane.showMessageDialog(null,ex.getMessage(),"Carnet d'adresse",JOptionPane.ERROR_MESSAGE);
        }
        return ret;
    }

    @Override
    public boolean modifier(Personne obj) 
    {
        boolean ret = false;
        String req = "UPDATE personne set nom=?,prenom=?,tel_domicile=?,tel_bureau=?,tel_portable=?,";
        req +="email=?,remarque=? WHERE id=?";
        try 
        {
            PreparedStatement  ps = this.con.prepareStatement(req);            
            ps.setString(1, obj.getNom());
            ps.setString(2, obj.getPrenom());
            ps.setString(3, obj.getTel_domicile());
            ps.setString(4, obj.getTel_bureau());
            ps.setString(5, obj.getTel_portable());
            ps.setString(6, obj.getEmail());
            ps.setString(7, obj.getRemarque());   
            ps.setInt(8, obj.getId());
            ps.executeUpdate();
            ret = true;
        } 
        catch (SQLException ex) 
        {
            JOptionPane.showMessageDialog(null,ex.getMessage(),"Carnet d'adresse",JOptionPane.ERROR_MESSAGE);
        }
        return ret;
    }

    @Override
    public boolean supprimer(Personne obj) 
    {
        boolean ret = false;
        String req = "DELETE FROM personne where id=?";        
        try 
        {
            PreparedStatement  ps = this.con.prepareStatement(req);
            ps.setInt(1, obj.getId());
            ps.executeUpdate();
            ret = true;
        }
        catch (SQLException ex) 
        {
            JOptionPane.showMessageDialog(null,ex.getMessage(),"Carnet d'adresse",JOptionPane.ERROR_MESSAGE);
        }
        return ret;
    }

    @Override
    public Personne trouve(int id) 
    {
        Personne ret = null;
        String req = "SELECT * FROM personne where id=?";          
        try 
        {
            PreparedStatement  ps = this.con.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet res = ps.executeQuery();
            if(res.next())
            {
                
               ret = new Personne(res.getInt("id"),res.getString("nom"),res.getString("prenom"),
               res.getString("tel_portable"),res.getString("email"),res.getString("tel_domicile"),
               res.getString("tel_bureau"),res.getString("remarque"));
            }            
        } 
        catch (SQLException | NullPointerException | EmailException e) 
        {
            JOptionPane.showMessageDialog(null,e.getMessage(),"Carnet d'adresse",JOptionPane.ERROR_MESSAGE);
        }  
        return ret;
    }    

    @Override
    public Personne[] afficher(String tri) 
    {
        if(tri == null)
            tri = "";
        tri = tri.trim();
        Personne [] ret = null;
        Vector <Personne> vect = new Vector<>();
        String req = "{call rechercher(?)}"; 
        try 
        {
            CallableStatement call = con.prepareCall(req);
            call.setString(1,tri);
            ResultSet res = call.executeQuery();
            while(res.next())
            {
                vect.add(new Personne(res.getInt("id"),res.getString("nom"),res.getString("prenom"),
               res.getString("tel_portable"),res.getString("email"),res.getString("tel_domicile"),
               res.getString("tel_bureau"),res.getString("remarque")));
            }
            
            ret = new Personne[vect.size()];
            int i= 0;
            Enumeration<Personne> E=vect.elements();
            while (E.hasMoreElements()) 
            {
                ret[i]=E.nextElement();            
                i++;
            }            
        } 
        catch (SQLException | NullPointerException | EmailException ex) 
        {
            JOptionPane.showMessageDialog(null,ex.getMessage(),"Carnet d'adresse",JOptionPane.ERROR_MESSAGE);
        }
        return ret;
    }
}
