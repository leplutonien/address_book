package carnet_adresse;

public class Run {
    private static String operatingSystem = null;

   
    public static void main(String[] args) {
        
        GuiPersonne gp = new GuiPersonne();
        gp.setVisible(true);      
    }    
    
    public static String getOS()
    {
        if(operatingSystem == null)
        {
            operatingSystem = System.getProperty("os.name");
        }
        
        return operatingSystem;
    }
}
