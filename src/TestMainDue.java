import Exceptions.*;
import java.util.*;
@SuppressWarnings("unchecked")
public class TestMainDue {

    private static SecureDataContainerHashMap hashTest = new SecureDataContainerHashMap<>();
    private static SecureDataContainerVectors vectTest = new SecureDataContainerVectors<>();
    private static final String LUIGGI_PSW = "ciao123";
    private static final String FULVIO_PSW = "cavallo12";
    private static final String GIOVANNI_PSW = "aquilone13";
    private static final String ALESSANDRO_PSW = "Peschereccio!";
    private static final Integer o1 = 4;
    private static final Double o2 = 3.56;
    private static Date o3 = new Date("20/04/2001");
    private static final String o4 = "Dato 4";
    //TEST PER LA CREATEUSER
    public static void testCreateOK(){
        try{
            System.out.println("Test creazione utente 'luigi' con HashMap:");
            hashTest.createUser("luigi",LUIGGI_PSW);
            System.out.println("Test creazione utente 'giovanni' con HashMap:");
            hashTest.createUser("giovanni",GIOVANNI_PSW);
            System.out.println("Test creazione utente 'fulvio' con Vectors:");
            vectTest.createUser("fulvio",FULVIO_PSW);
            System.out.println("Test creazione utente 'alessandro' con Vectors:");
            vectTest.createUser("alessandro",ALESSANDRO_PSW);
        }catch(DoubleUserException e){
            e.printStackTrace();
        }
    }
    public static void testCreateWRONG(){
        try{
            System.out.println("Test creazione utente doppione con HashMap:");
            hashTest.createUser("luigi",LUIGGI_PSW);
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
            System.out.println("Test rimozione utente inesistente con HashMap:");
            hashTest.RemoveUser("mario",LUIGGI_PSW);
            System.out.println("Test rimozione utente inesistente con Vectors:");
            vectTest.RemoveUser("mario",LUIGGI_PSW);
        }catch(NoUserException e){
            e.printStackTrace();
        }
    }
    //TEST PER LA DIMENSIONE
    public static void testGetSizeOK(){
        try{
            System.out.println("Test numero elementi utente 'luigi' con HashMap:");
            System.out.println(hashTest.getSize("luigi",LUIGGI_PSW));
            System.out.println("Test numero elementi utente 'fulvio' con Vectors:");
            System.out.println(vectTest.getSize("fulvio",FULVIO_PSW));
        }catch(NoUserException e){
            e.printStackTrace();
        }
    }
    public static void testGetSizeWRONG(){
        try{
            System.out.println("Test numero elementi utente inesistente con HashMap:");
            System.out.println(hashTest.getSize("mario",LUIGGI_PSW));
            System.out.println("Test numero elementi utente inesistente con Vectors:");
            System.out.println(vectTest.getSize("mario",FULVIO_PSW));
        }catch(NoUserException e){
            e.printStackTrace();
        }
    }
    //TEST PER L'INSERZIONE
    public static void testPutOK(){
        try{
            System.out.println("Test inserzione dato 1 nella collezione di 'luigi' con HashMap:");
            hashTest.put("luigi",LUIGGI_PSW,o1);
            System.out.println("Test inserzione dato 2 nella collezione di 'fulvio' con Vectors:");
            vectTest.put("fulvio",FULVIO_PSW,o2);
            System.out.println("Test inserzione dato 3 nella collezione di 'luigi' con HashMap:");
            hashTest.put("luigi",LUIGGI_PSW,o3);
            System.out.println("Test inserzione dato 4 nella collezione di 'fulvio' con Vectors:");
            vectTest.put("fulvio",FULVIO_PSW,o4);
        }catch(NoUserException e){
            e.printStackTrace();
        }
    }

    //TEST PER L'ACQUISIZIONE
    public static void testGetOK(){
        try{
            System.out.println("Test acquisizione 4 dalla collezione di 'luigi' con HashMap:");
            hashTest.get("luigi",LUIGGI_PSW,o1);
            System.out.println("Test acquisizione 3.56 dalla collezione di 'fulvio' con Vectors:");
            vectTest.get("fulvio",FULVIO_PSW,o2);
        }catch(NoUserException | DataNotFoundException e){
            e.printStackTrace();
        }
    }

    public static void testGetWRONG(){
        try{
            System.out.println("Test acquisizione dato non presente dalla collezione di 'luigi' con HashMap:");
            hashTest.get("luigi",LUIGGI_PSW,o2);
            System.out.println("Test acquisizione dato non presente dalla collezione di 'fulvio' con Vectors:");
            vectTest.get("fulvio",FULVIO_PSW,o1);
        }catch(NoUserException | DataNotFoundException e){
            e.printStackTrace();
        }
    }
    //TEST PER LA RIMOZIONE DI UN DATO
    public static void testRemoveElOK(){
        try{
            System.out.println("Test rimozione 4 dalla collezione di 'luigi' con HashMap:");
            hashTest.remove("luigi",LUIGGI_PSW,o1);
            System.out.println("Test rimozione 3.56 dalla collezione di 'fulvio' con Vectors:");
            vectTest.remove("fulvio",FULVIO_PSW,o2);
        }catch(NoUserException | DataNotFoundException e){
            e.printStackTrace();
        }
    }

    public static void testRemoveElWRONG(){
        try{
            System.out.println("Test rimozione oggetto non presente dalla collezione di 'luigi' con HashMap:");
            hashTest.remove("luigi",LUIGGI_PSW,o2);
            System.out.println("Test rimozione oggetto non presente dalla collezione di 'fulvio' con Vectors:");
            vectTest.remove("fulvio",FULVIO_PSW,o1);
        }catch(NoUserException | DataNotFoundException e){
            e.printStackTrace();
        }
    }
    //TEST PER LA COPIA DI UN DATO
    public static void testCopyOK(){
        try{
            System.out.println("Test copia 4 nella collezione di 'luigi' con HashMap:");
            hashTest.copy("luigi",LUIGGI_PSW,o1);
            System.out.println("Test copia 3.56 nella collezione di 'fulvio' con Vectors:");
            vectTest.copy("fulvio",FULVIO_PSW,o2);
        }catch(NoUserException | DataNotFoundException e){
            e.printStackTrace();
        }
    }

    public static void testCopyWRONG(){
        try{
            System.out.println("Test copia elemento inesistente nella collezione di 'luigi' con HashMap:");
            hashTest.copy("luigi",LUIGGI_PSW,o2);
            System.out.println("Test copia elemento inesistente nella collezione di 'fulvio' con Vectors:");
            vectTest.copy("fulvio",FULVIO_PSW,o1);
        }catch(NoUserException | DataNotFoundException e){
            e.printStackTrace();
        }
    }
    //TEST PER LA CONDIVISIONE DI UN DATO
    public static void testShareOK(){
        try{
            System.out.println("Test condivisione "+ o3 +" nella collezione di 'giovanni' da 'luigi' con HashMap:");
            hashTest.share("luigi",LUIGGI_PSW,"giovanni",o3);
            System.out.println("Test condivisione "+o4+" nella collezione di 'alessandro' da 'fulvio' con Vectors:");
            vectTest.share("fulvio",FULVIO_PSW,"alessandro",o4);
        }catch(NoUserException | DataNotFoundException | AlreadyAuthorizedException e){
            e.printStackTrace();
        }
    }

    public static void testShareWRONG(){
        try{
            System.out.println("Test condivisione elemento non presente nella collezione di 'fulvio' da 'luigi' con HashMap:");
            hashTest.share("luigi",LUIGGI_PSW,"fulvio",o2);
            System.out.println("Test condivisione elemento non presente nella collezione di 'luigi' da 'fulvio' con Vectors:");
            vectTest.share("fulvio",FULVIO_PSW,"luigi",o1);
            System.out.println("Test condivisione con utente inesistente con HashMap:");
            hashTest.share("luigi",LUIGGI_PSW,"pippo",o2);
            System.out.println("Test condivisione con utente inesistente con Vectors:");
            vectTest.share("fulvio",FULVIO_PSW,"pippo",o1);
            System.out.println("Test condivisione con utente già autorizzato con Vectors:");
            vectTest.share("fulvio",FULVIO_PSW,"luigi",o4);
        }catch(NoUserException | DataNotFoundException | AlreadyAuthorizedException e){
            e.printStackTrace();
        }
    }
    //TEST PER L'ITERATORE
    //Metodo che stampa l'iterator
    private static void stampaIt(Iterator a){
        while (a.hasNext()){
            System.out.println(a.next());
        }
    }
    public static void testGetIteratorOK(){
        try{
            System.out.println("Test stampa iteratore della collezione di 'luigi' con HashMap:");
            System.out.println("Elementi di luigi:");
            stampaIt(hashTest.getIterator("luigi",LUIGGI_PSW));
            System.out.println("Test stampa iteratore della collezione di 'fulvio' con Vectors:");
            System.out.println("Elementi di fulvio:");
            stampaIt(vectTest.getIterator("fulvio",FULVIO_PSW));
            System.out.println("Test stampa iteratore della collezione di 'giovanni' con HashMap:");
            System.out.println("Elementi di giovanni:");
            stampaIt(hashTest.getIterator("luigi",LUIGGI_PSW));
            System.out.println("Test stampa iteratore della collezione di 'alessandro' con Vectors:");
            System.out.println("Elementi di alessandro:");
            stampaIt(vectTest.getIterator("fulvio",FULVIO_PSW));
        }catch(NoUserException  e){
            e.printStackTrace();
        }
    }

    public static void testGetIteratorWRONG(){
        try{
            System.out.println("Test stampa iteratore della collezione di utente inesistente con HashMap:");
            System.out.println("Elementi di luigi:");
            stampaIt(hashTest.getIterator("mario",LUIGGI_PSW));
            System.out.println("Test stampa iteratore della collezione di utente inesistente con Vectors:");
            System.out.println("Elementi di fulvio:");
            stampaIt(vectTest.getIterator("pippo",FULVIO_PSW));
        }catch(NoUserException  e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args){
        Scanner t = new Scanner(System.in);
        System.out.println("Creazione utenti senza errori:");
        testCreateOK();
        t.nextInt();
        System.out.println("Creazione utenti con errori:");
        testCreateWRONG();
        t.nextInt();

        System.out.println("Aggiunta elementi senza errori:");
        testPutOK();
        t.nextInt();

        System.out.println("Stampa elementi 1 senza errori:");
        testGetIteratorOK();
        t.nextInt();
        System.out.println("Stampa elementi 1 con errori:");
        testGetIteratorWRONG();
        t.nextInt();

        System.out.println("Numero degli elementi senza errori:");
        testGetSizeOK();
        t.nextInt();
        System.out.println("Numero degli elementi con errori:");
        testGetSizeWRONG();
        t.nextInt();

        System.out.println("Ottiene elementi senza errori:");
        testGetOK();
        t.nextInt();
        System.out.println("Ottiene elementi con errori:");
        testGetWRONG();
        t.nextInt();

        System.out.println("Copia elementi senza errori:");
        testCopyOK();
        t.nextInt();
        System.out.println("Copia elementi con errori:");
        testCopyWRONG();
        t.nextInt();

        System.out.println("Stampa elementi 2 senza errori:");
        testGetIteratorOK();
        t.nextInt();
        System.out.println("Stampa elementi 2 con errori:");
        testGetIteratorWRONG();
        t.nextInt();

        System.out.println("Condivisione elementi senza errori:");
        testShareOK();
        t.nextInt();
        System.out.println("Condivisione elementi con errori:");
        testShareWRONG();
        t.nextInt();

        System.out.println("Stampa elementi 3 senza errori:");
        testGetIteratorOK();
        t.nextInt();
        System.out.println("Stampa elementi 3 con errori:");
        testGetIteratorWRONG();
        t.nextInt();

        System.out.println("Rimozione elementi senza errori:");
        testRemoveElOK();
        t.nextInt();
        System.out.println("Rimozione elementi con errori:");
        testRemoveElWRONG();
        t.nextInt();

        System.out.println("Stampa elementi 4 senza errori:");
        testGetIteratorOK();
        t.nextInt();
        System.out.println("Stampa elementi 4 con errori:");
        testGetIteratorWRONG();
        t.nextInt();

        System.out.println("Rimozione utenti senza errori:");
        testRemoveOK();
        t.nextInt();
        System.out.println("Rimozione utenti con errori:");
        testRemoveWRONG();
        t.nextInt();

    }
}
