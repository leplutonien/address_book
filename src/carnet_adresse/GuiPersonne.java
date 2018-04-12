package carnet_adresse;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 * <p>
 * <span style="font-weight:bold;text-align: center;color:green">
 * Titre: GuiPersonne</span><br>
 * <span style="font-weight:bold">
 * Description:</span><br>
 * L'interface graphique principale</p>
 * @author OGOULOLA Marlin Yannick 
 * @version  1.08.14
 */
public final class GuiPersonne extends JFrame implements KeyListener,ListSelectionListener,ActionListener
{
    //déclaration des variables
    private final Font police = new Font("Dialog",Font.PLAIN,13);
    private static final String TITRE="Carnet d'adresse";
    private JToolBar baroutils;
    private JButton nouveau,modifier,supprimer,visualiser;
    private JPanel container;
    private JLabel labRech,baretat;
    private JTextField rech;
    private JTable jtab;
    private DefaultTableModel model ;
    private JScrollPane jsp;    
    private Personne [] listePersonne;   
    private Personne personneSelected;
    private DAO<Personne> personneDao ;
    public final String NOUVEAU = "Nouveau";
    public final String MODIFIER = "Modifier";
    public final String SUPRIMER = "Suprimer";
    public final String VISUALISER = "Visualiser";
    
    public GuiPersonne()
    {
        initComponents();         
    }

    private void initComponents() 
    {
       this.setTitle(TITRE);
       //on applique le look and Feel de l'OS
       lookAndFeel();
       //fermeture par clique sur le bouton fermeture de la femetre
       this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       
       //on met un logo pour la fenetre
       this.setIconImage(Toolkit.getDefaultToolkit().getImage("images/logo.png"));
       //on definit la dimension de la fenetre par rapport à L'os, à cause du LookandFeel 
       if(Run.getOS().startsWith("Windows"))
           this.setSize(705, 500);
       else
           this.setSize(700, 500);
       //on centre la fenetre       
       this.setLocationRelativeTo(null);
       //on bloque le redimensionnement de la fenetre
       this.setResizable(false);      
       container = new JPanel();
       container.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
       this.setContentPane(container);
       //###############"gestion du toolbar###################       
       baroutils = new JToolBar(JToolBar.HORIZONTAL); 
       baroutils.setBorder(null);
       baroutils.setFloatable(false);
       baroutils.setRollover(true);       
       nouveau = new JButton(NOUVEAU, new ImageIcon("images/ajouter.gif"));
       nouveau.setFont(police);
       nouveau.setToolTipText(NOUVEAU);
       nouveau.setMnemonic('N');
       baroutils.add(nouveau);
       modifier = new JButton(MODIFIER,new ImageIcon("images/edit.png"));
       modifier.setFont(police);
       modifier.setToolTipText(MODIFIER);
       modifier.setMnemonic('M');
       baroutils.add(modifier);
       supprimer = new JButton(SUPRIMER,new ImageIcon("images/corb.gif"));
       supprimer.setFont(police);
       supprimer.setToolTipText(SUPRIMER);
       supprimer.setMnemonic('S');
       baroutils.add(supprimer);
       visualiser = new JButton(VISUALISER,new ImageIcon("images/visible.gif"));
       visualiser.setFont(police);
       supprimer.setToolTipText(VISUALISER);
       supprimer.setMnemonic('V');
       baroutils.add(visualiser);
       baroutils.add(new JToolBar.Separator());       
       labRech = new JLabel(new ImageIcon("images/iconeLoupe.png"));
       labRech.setText("Rechercher");
       labRech.setFont(police);       
       baroutils.add(labRech);
       rech = new JTextField();
        if(Run.getOS().startsWith("Windows"))
            rech.setColumns(27);
        else
            rech.setColumns(21);
       rech.setFont(police);
       rech.setToolTipText("Rechercher une personne dans le carnet d'adresse");
       baroutils.add(rech);
       container.add(baroutils);
       //#############################################################
       //###########gestion du JTable#######################"
       jtab = new JTable();
       String titres[]=new String[] {"Identité","Portable","Email"};
       //on rend inéditatble les cellules
       model = new DefaultTableModel(null,titres)
       {
           boolean[] canEdit = new boolean [] {false, false,false};
           @Override
           public boolean isCellEditable(int rowIndex, int columnIndex) 
           {
               return canEdit [columnIndex];
           }
       };
       
       jtab.setFont(police);
       jtab.setModel(model);       
       jsp = new JScrollPane();
       jsp.setViewportView(jtab);      
       jsp.setPreferredSize(new Dimension(696, 420));
       //on empeche la selection multiple des cellules
       jtab.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);       
       jtab.getTableHeader().setReorderingAllowed(false);        
       jtab.getColumnModel().getColumn(0).setPreferredWidth(100);
       jtab.getColumnModel().getColumn(1).setPreferredWidth(15);   
       container.add(jsp);
       //###########gestion de la barre d'etat############
       baretat = new JLabel("By OGOULOLA Marlin Yannick");
       baretat.setFont(new Font("Dialog",Font.BOLD,12));
       baretat.setPreferredSize(new Dimension(696, 25));
       baretat.setBorder(BorderFactory.createEtchedBorder());
       container.add(baretat);
       //####### chargement des données de la table personne dans la Jtable ######  
       personneDao = DAOFactory.getPersonneDAO();
       chargerTable();
       
       //######gestion des écouteurs########
       rech.addKeyListener(this);
       nouveau.addActionListener(this);
       modifier.addActionListener(this);
       supprimer.addActionListener(this);
       visualiser.addActionListener(this);
       //on ajoute un écouteur sur la jtable
       ListSelectionModel selectionModel=jtab.getSelectionModel();
       selectionModel.addListSelectionListener(this);      
    }

    private void lookAndFeel() 
    {
        try 
        { 
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);
        } 
	catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) 
        {
            System.out.println("impossible d'appliquer le théme du systéme");
	}
    }   

    public void chargerTable() 
    {
        //effacement des ligne du modele
        int i,tot=model.getRowCount();        
        for(i=0;i<tot;i++)
            model.removeRow(0); 
        listePersonne = personneDao.afficher(rech.getText().trim());
        for(Personne p : listePersonne)
        {
            model.addRow(new String []{p.getNom()+" "+p.getPrenom(),p.getTel_portable(),
            p.getEmail()});
        }        
        //gestion des boutons
        modifier.setEnabled(false);
        supprimer.setEnabled(false);
        visualiser.setEnabled(false);
        personneSelected = null;
    }

    @Override
    public void keyTyped(KeyEvent e) 
    {
        
    }

    @Override
    public void keyPressed(KeyEvent e) 
    {
        
    }

    @Override
    public void keyReleased(KeyEvent e) 
    {
        chargerTable();       
    }

    @Override
    public void valueChanged(ListSelectionEvent event) 
    {
        if( event.getSource() == jtab.getSelectionModel() & event.getFirstIndex() >= 0 )
        {
            if(jtab.getSelectedRow() != -1)
            {
               this.personneSelected = listePersonne[jtab.getSelectedRow()];
               //gestion des boutons
               modifier.setEnabled(true);
               supprimer.setEnabled(true);
               visualiser.setEnabled(true);
            }
        }
        
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        Object src = e.getSource();
        
        if(src == supprimer & personneSelected != null)
        {
            int rep=JOptionPane.showConfirmDialog(this,"Voullez-vous vraiment supprimer ce contact ? ",TITRE,
                    JOptionPane.YES_NO_OPTION);            
            if(rep == JOptionPane.YES_OPTION)
            {
                personneDao.supprimer(personneSelected);
                chargerTable();
            }                
        }
        
        if(src == nouveau)
        {
           new GuiMajPersonne(this,NOUVEAU,null).setVisible(true);
        }
        
        if(src == visualiser  & personneSelected != null)
        {
            new GuiMajPersonne(this,VISUALISER,personneSelected).setVisible(true);
        }
        
        if(src == modifier  & personneSelected != null)
        {
            new GuiMajPersonne(this,MODIFIER,personneSelected).setVisible(true);
        }
    }
    
    
}
