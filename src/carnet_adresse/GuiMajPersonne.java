package carnet_adresse;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * <p>
 * <span style="font-weight:bold;text-align: center;color:green">
 * Titre: GuiMajPersonne</span><br>
 * <span style="font-weight:bold">
 * Description:</span><br>
 * L'interface graphique pour la mise à jour dans la table personne</p>
 * @author OGOULOLA Marlin Yannick 
 * @version  1.08.14
 */
public final class GuiMajPersonne extends JDialog implements ActionListener,KeyListener{
    // déclaration des variables
    private final Font police = new Font("Dialog",Font.PLAIN,14);
    private GuiPersonne parent = null;
    private String action = null;
    private JPanel container;
    /**tableau stockant le libellé des champs*/
    private String [] libString;
    private JLabel [] libs;
    private JLabel libRemarq;
    private JTextField [] textField;
    private JTextArea remarque;
    private JScrollPane jsp;
    private JButton enregistrer,annuler;
    private Personne pers;
    private DAO<Personne> personneDao ;
    
    public GuiMajPersonne(GuiPersonne parent,String action,Personne pers)
    {
        super(parent,true);
        this.parent = parent;
        this.action = action;  
        this.pers = pers;
        initComponents();        
    }

    private void initComponents() 
    {
        this.setTitle(parent.getTitle() + " - " + action);
        if(Run.getOS().startsWith("Windows"))
           this.setSize(389, 312);  
       else
           this.setSize(389, 305);                    
        this.setLocationRelativeTo(parent);        
        this.setResizable(false);        
        container = new JPanel(new FlowLayout(FlowLayout.LEFT,2,3));
        JPanel pan = new JPanel();               
        pan.setLayout(new GridLayout(6, 2,0,3));
        this.setContentPane(container);
        //############
        libString = new String [] {"Nom","Prénom","Adresse Email","Portable","Domicile","Bureau"};
        libs = new JLabel[libString.length];        
        textField = new JTextField[libString.length];
        
        for (int i = 0; i < libString.length; i++) 
        {
           libs[i] = new JLabel(libString[i]);
           libs[i].setFont(police);
            if(Run.getOS().startsWith("Windows"))
                textField[i] = new JTextField(23);
            else
                textField[i] = new JTextField(17);
        }      
        
        for (int i = 0; i < libString.length; i++) 
        {
            pan.add(libs[i]);
            pan.add(textField[i]);
        }       
        container.add(pan);
        
        libRemarq = new JLabel("Remarque");
        libRemarq.setFont(new Font("Dialog",Font.BOLD,14));
        container.add(libRemarq);
        
        jsp = new JScrollPane();
        remarque = new JTextArea(5, 10);
        jsp.setViewportView(remarque);      
        jsp.setPreferredSize(new Dimension(380,90));
        container.add(jsp);
        
        JPanel pan2 = new JPanel(new FlowLayout(FlowLayout.RIGHT,35,0));
        enregistrer = new JButton("Enregistrer",new ImageIcon("images/save.png"));
        enregistrer.setFont(police);
        enregistrer.setToolTipText("Enregistrer");
        enregistrer.setMnemonic('E');
        annuler = new JButton("Annuler",new ImageIcon("images/check.gif"));
        annuler.setFont(police);
        annuler.setToolTipText("Annuler");
        annuler.setMnemonic('A');
        pan2.add(enregistrer);
        pan2.add(annuler);
        container.add(pan2);
        //on oblige la saisie des chiffers dans certains champs        
        for (int i = 3; i < libString.length; i++) 
        {
           textField[i].setDocument(new JTextFieldController(JTextFieldController.ALL_TO_NUMBER));
        }
        textField[0].setDocument(new JTextFieldController(JTextFieldController.ALL_TO_UPPERCASE));
        
        //#### gestion des actions      
        
        if(action.equals(parent.VISUALISER) || action.equals(parent.MODIFIER))
        {
            //on remplit les champs avec les données de pers
            if(pers != null)
            {
                textField[0].setText(pers.getNom());
                textField[1].setText(pers.getPrenom());
                textField[2].setText(pers.getEmail());
                textField[3].setText(pers.getTel_portable());
                textField[4].setText(pers.getTel_domicile());
                textField[5].setText(pers.getTel_bureau());
                remarque.setText(pers.getRemarque());                 
            }
        }
        
        if(action.equals(parent.VISUALISER))
        {
            if(Run.getOS().startsWith("Windows"))
                this.setSize(389, 285); 
            else
                this.setSize(389, 275); 
            for (JTextField txt : textField) 
            {
                txt.setEditable(false);
            }
            remarque.setEditable(false);
        }
        
        if(action.equals(parent.NOUVEAU) || action.equals(parent.MODIFIER))
        {
            gestionBouton();
        }
        //##### ajout des écouteurs
        enregistrer.addActionListener(this);
        annuler.addActionListener(this);  
        for (int i = 0; i <= 3; i++) 
        {
            textField[i].addKeyListener(this);
        }
        
        personneDao = DAOFactory.getPersonneDAO();
    }    

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        Object src = e.getSource();
        
        if(src == annuler)
        {
            for (JTextField txt : textField) 
            {
                txt.setText(null);
            }
            remarque.setText(null);
            gestionBouton();
        }
        
        if(src == enregistrer)
        {
            int id = 0;            
            if(pers != null)
                id = pers.getId();
            Personne p = null;
            try
            {
                 p = new Personne(id,textField[0].getText().trim(),textField[1].getText().trim(),
                    textField[3].getText().trim(),textField[2].getText().trim(),
                    textField[4].getText().trim(),textField[5].getText().trim(),
                    remarque.getText().trim());
                 
                 if(action.equals(parent.NOUVEAU))
                 {
                    if(personneDao.enregistrer(p))
                    {
                        parent.chargerTable();
                        annuler.doClick();
                        textField[0].requestFocus();
                    }                    
                 }
                 
                 if(action.equals(parent.MODIFIER))
                 {
                    if(personneDao.modifier(p))
                    {
                        parent.chargerTable();
                        this.setVisible(false);
                    } 
                 }
            }
            catch(EmailException | NullPointerException ex)
            {
               JOptionPane.showMessageDialog(this,ex.getMessage(),this.getTitle(),JOptionPane.ERROR_MESSAGE); 
            }     
            
        }
    }

    private void gestionBouton() 
    {
        if(action.equals(parent.NOUVEAU) || action.equals(parent.MODIFIER))
        {
            boolean trouve = false;
            for (int i = 0; i <= 3; i++) 
            {
                if(textField[i].getText().trim().isEmpty())
                {
                    trouve = true;
                    break;
                }                
            }
            enregistrer.setEnabled(!trouve);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) 
    { }

    @Override
    public void keyPressed(KeyEvent e) {}
    
    @Override
    public void keyReleased(KeyEvent e) 
    {
        gestionBouton();
    }
    
}
