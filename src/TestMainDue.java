import Exceptions.*;

public class TestMainDue {
    private static SecureDataContainerHashMap hashTest = new SecureDataContainerHashMap();
    private static SecureDataContainerVectors vectTest = new SecureDataContainerVectors();
    private static final String LUIGGI_PSW = "ciao123";
    private static final String FULVIO_PSW = "cavallo12";
    //TEST PER LA CREATEUSER
    public static void testCreateOK(){
        try{
            System.out.println("Test creazione utente 'luigi' con HashMap:");
            hashTest.createUser("luigi",LUIGGI_PSW);
            System.out.println("Test creazione utente 'fulvio' con Vectors:");
            vectTest.createUser("fulvio",FULVIO_PSW);
        }catch(DoubleUserException e){
            e.printStackTrace();
        }
    }
    public static void testCreateWRONG(){
        try{
            System.out.println("Test creazione utente doppione con HashMap:");
            hashTest.createUser("luigi",LUIGGI_PSW);
            System.out.println("Test creazione utente null con HashMap:");
            hashTest.createUser(null,LUIGGI_PSW);
            System.out.println("Test creazione utente '' con HashMap:");
            hashTest.createUser("",LUIGGI_PSW);
            System.out.println("Test creazione utente doppione con Vectors:");
            hashTest.createUser("fulvio",FULVIO_PSW);
            System.out.println("Test creazione password '' con Vectors:");
            vectTest.createUser("fulvio","");
            System.out.println("Test creazione utente null con Vectors:");
            vectTest.createUser(null,FULVIO_PSW);
        }catch(DoubleUserException e){
            e.printStackTrace();
        }
    }
    //TEST PER LA REMOVEUSER
    public static void testRemoveOK(){
        try{
            System.out.println("Test rimozione utente 'luigi' con HashMap:");
            hashTest.RemoveUser("luigi",LUIGGI_PSW);
            System.out.println("Test rimozione utente 'fulvio' con Vectors:");
            vectTest.RemoveUser("fulvio",FULVIO_PSW);
        }catch(NoUserException e){
            e.printStackTrace();
        }
    }
    public static void testRemoveWRONG(){
        try{
            System.out.println("Test rimozione utente null con HashMap:");
            hashTest.RemoveUser(null,LUIGGI_PSW);
            System.out.println("Test rimozione utente '' con HashMap:");
            hashTest.RemoveUser("",LUIGGI_PSW);
            System.out.println("Test rimozione utente inesistente con HashMap:");
            hashTest.RemoveUser("mario",LUIGGI_PSW);
            System.out.println("Test rimozione password '' con Vectors:");
            vectTest.RemoveUser("luigi","");
            System.out.println("Test rimozione utente null con Vectors:");
            vectTest.RemoveUser(null,FULVIO_PSW);
            System.out.println("Test rimozione utente inesistente con Vectors:");
            vectTest.RemoveUser("mario",LUIGGI_PSW);
        }catch(NoUserException e){
            e.printStackTrace();
        }
    }

    public static void testGetSizeOK(){
        try{
            System.out.println("Test numero elementi utente 'luigi' con HashMap:");
            hashTest.getSize("luigi",LUIGGI_PSW);
            System.out.println("Test numero elementi utente 'fulvio' con Vectors:");
            vectTest.getSize("fulvio",FULVIO_PSW);
        }catch(NoUserException e){
            e.printStackTrace();
        }
    }
    public static void testGetSizeWRONG(){
        try{
            System.out.println("Test numero elementi utente '' con HashMap:");
            hashTest.getSize("",LUIGGI_PSW);
            System.out.println("Test numero elementi utente '' con Vectors:");
            vectTest.getSize("",FULVIO_PSW);
            System.out.println("Test numero elementi utente null con HashMap:");
            hashTest.getSize(null,LUIGGI_PSW);
            System.out.println("Test numero elementi utente null con Vectors:");
            vectTest.getSize(null,FULVIO_PSW);
            System.out.println("Test numero elementi utente inesistente con HashMap:");
            hashTest.getSize("mario",LUIGGI_PSW);
            System.out.println("Test numero elementi utente inesistente con Vectors:");
            vectTest.getSize("mario",FULVIO_PSW);
        }catch(NoUserException e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args){

    }
}
