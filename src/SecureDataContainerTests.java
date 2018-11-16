import exceptions.*;
import java.util.Iterator;

public class SecureDataContainerTests {

  private final static String CRAX_PASSWORD = "antaniadestra";

  public static void abort(String msg) {
    System.out.println(msg);
    System.exit(-1);
  }

  public static void testContainer( SecureDataContainer container) {
    try {
       container.createUser("crax", CRAX_PASSWORD);
     } catch (UserAlreadyPresentException e ){
       abort("createUser(crax, " + CRAX_PASSWORD + ")");
     }
     System.out.println("Utonto crax creato");

    try {
      container.createUser("fruvio", "aq32543y");
     } catch (UserAlreadyPresentException e ){
        abort("createUser(fruvio, aq32543y)");
     }
     System.out.println("Utonto fruvio creato");

    try {
      container.createUser("luigi", "asfg2354aga");
     } catch (UserAlreadyPresentException e ){
         abort("createUser(luigi, asfg2354aga)");
     }
     System.out.println("Utonto luigi creato");

    try {
        container.createUser("crax", CRAX_PASSWORD);
     } catch (UserAlreadyPresentException e ){
        System.out.println("Eccezione generata correttamente");
     }
     System.out.println("Utente luigi creato");

    container.put("crax", CRAX_PASSWORD, new String("Meizu"));

    try {
       container.copy("crax", CRAX_PASSWORD, new String("Meizu"));
     } catch (InvalidCredentialsException e) {
       abort("2 copy(..., meizu): Utente non presente");
     } catch (ElementAlreadyPresentException e) {
       System.out.println("Copia fallita PORCAPUTTANA WHY");
     }

     System.out.println("Crax ha un due Meizu ora. Riccone.");
     System.out.println("------------------------------Telefoni di Crax----------------------------------");
     Iterator<String> phoneIt;
     try {
       phoneIt = container.getIterator("crax", CRAX_PASSWORD);
       while(phoneIt.hasNext()) {
         String phone = phoneIt.next();
         System.out.println(phone);
       }
     } catch (InvalidCredentialsException ex) {
       abort("Login fallito con Crax, e che cazz però");
     }

     container.put("crax", CRAX_PASSWORD, new String("Huawei"));


    try {
      container.share("crax", CRAX_PASSWORD, "luigi", new String("Meizu"));
    } catch (UserNotAllowedException ex) {
      abort ("Quel pezzente di crax non ha un Meizu");
    } catch (InvalidCredentialsException e) {
      abort("Login fallito a container.share()");
    } catch (UserNotPresentException e) {
      abort("A quanto pare luigi è morto");
    } catch (ElementAlreadyPresentException e) {
      abort("Luigi ha un Meizu, e chi lo sapeva?");
    } catch (UserAlreadyAllowedException e) {
      abort("L'utente è già autorizzato");
    }

    String huawei = (String)container.get("crax", CRAX_PASSWORD, new String("Huawei"));
    if(huawei == null) {
      abort("Huawei è fallita");
    } else {
      System.out.println("Ora crax possiede " + huawei);
    }

    System.out.println("------------------------------Prima di cancellare Huawei dal mondo----------------------------------");
    try {
      phoneIt = container.getIterator("crax", CRAX_PASSWORD);
      while(phoneIt.hasNext()) {
        String phone = phoneIt.next();
        System.out.println(phone);
      }
    } catch (InvalidCredentialsException ex) {
      abort("Login fallito con Crax, e che cazz però");
    }

    String huaweiRemoved = (String)container.remove("crax", CRAX_PASSWORD, "Huawei");
    if(!huaweiRemoved.equals("Huawei")) {
      abort ("WTF DUDE");
    }
    huaweiRemoved = (String)container.remove("crax", CRAX_PASSWORD, "Huawei");
    if(huaweiRemoved != null && huaweiRemoved.equals("Huawei")) {
      abort ( "EVEN MORE WTF DUDE" );
    }

    System.out.println("------------------------------Dopo aver cancellato Huawei dal mondo MUHUHAUHAUAHA----------------------------------");
    try {
      phoneIt = container.getIterator("crax", CRAX_PASSWORD);
      while(phoneIt.hasNext()) {
        String phone = phoneIt.next();
        System.out.println(phone);
      }
    } catch (InvalidCredentialsException ex) {
      abort("Login fallito con Crax, e mannaggia chella scurnacchiata zuccolona zombapereta di tua madre");
    }

    System.out.println("------------------------------Telefoni di Luigi----------------------------------");
    try {
      phoneIt = container.getIterator("luigi", "asfg2354aga");
      while(phoneIt.hasNext()) {
        String phone = phoneIt.next();
        System.out.println(phone);
      }
    } catch (InvalidCredentialsException ex) {
      abort("Login fallito con luigi, e che cazz però");
    }

    System.out.println("------------------------------Rimuovo Crax-----------------------------------");
    try {
        container.removeUser("crax", CRAX_PASSWORD);
    } catch (InvalidCredentialsException ex) {
        abort("Sono scappato in Messico");
    }

  System.out.println("------------------------------Telefoni di Luigi----------------------------------");
  try {
      phoneIt = container.getIterator("luigi", "asfg2354aga");
      while(phoneIt.hasNext()) {
          String phone = phoneIt.next();
          System.out.println(phone);
      }
  } catch (InvalidCredentialsException ex) {
      abort("Login fallito con luigi, e che cazz però");
  }


  }

  public static void main(String[] args) {
    SecureDataContainer<String> hashContainer = new ListSecureDataContainer<>();
    testContainer(hashContainer);
    System.out.println("Primo round vinto");
  }

}
