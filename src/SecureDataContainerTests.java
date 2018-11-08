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

    try {
      container.createUser("fruvio", "aq32543y");
     } catch (UserAlreadyPresentException e ){
        abort("createUser(fruvio, aq32543y)");
     }

    try {
      container.createUser("luigi", "asfg2354aga");
     } catch (UserAlreadyPresentException e ){
         abort("createUser(luigi, asfg2354aga)");
     }
    try {
        container.createUser("crax", CRAX_PASSWORD);
     } catch (UserAlreadyPresentException e ){
        System.out.println("Eccezione generata correttamente");
     }

     try {
      container.copy("crax", CRAX_PASSWORD, new String("Meizu"));
      } catch (InvalidCredentialsException e) {
        abort("1 copy(..., meizu): Utente non presente");
      } catch (ElementAlreadyPresentException e) {
          abort("copy(..., meizu): Elemento già presente");
      }

      try {
         container.copy("crax", CRAX_PASSWORD, new String("Meizu"));
       } catch (InvalidCredentialsException e) {
         abort("2 copy(..., meizu): Utente non presente");
       } catch (ElementAlreadyPresentException e) {
         System.out.println("Copia fallita correttamente");
       }

       try {
         container.copy("crax", CRAX_PASSWORD, new String("Huawei"));
        } catch (InvalidCredentialsException e) {
          abort("1 copy(..., Huawei): Utente non presente");
        } catch (ElementAlreadyPresentException e) {
            abort("copy(..., Huawei): Elemento già presente");
        }

        try {
          container.share("crax", "antaniadestra", "luigi", new String("Meizu"));
        } catch (InvalidCredentialsException e) {
          abort("Login fallito a container.share()");
        } catch (UserNotPresentException e) {
          abort("A quanto pare luigi è morto");
        } catch (ElementAlreadyPresentException e) {
          abort("Luigi ha un Meizu, e chi lo sapeva?");
        }

        String huawei = (String)container.get("crax", CRAX_PASSWORD, "data");
        if(huawei == null) {
          abort("Huawei è fallita");
        } else {
          System.out.println(huawei + " esiste ancora");
        }

        String huaweiRemoved = (String)container.remove("crax", CRAX_PASSWORD, "Huawei");
        if(!huaweiRemoved.equals("Huawei")) {
          abort ("WTF DUDE");
        }
        huaweiRemoved = (String)container.remove("crax", CRAX_PASSWORD, "Huawei");
        if(huaweiRemoved.equals("Huawei")) {
          abort ( "EVEN MORE WTF DUDE" );
        }
  }

  public static void main(String[] args) {
    SecureDataContainer<String> hashContainer = new HashMapSecureDataContainer<>();
    testContainer(hashContainer);
  }

}
