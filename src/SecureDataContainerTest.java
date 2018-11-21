import exceptions.*;

public class SecureDataContainerTest {

    private static void abort(String msg) {
        System.out.println(msg);
        System.exit(-1);
    }

    private static void testContainer(SecureDataContainerArrayList<String> container) {
        try {
            container.createUser("Giua","comefosseantani");
        } catch (UserAlreadyPresentException e) {
            System.out.println("User already present");
        }

        try {
            container.createUser("Fruvio","GiacominoPaneEVino");
        } catch (UserAlreadyPresentException e) {
            System.out.println("User already present");
        }

        try {
            container.createUser("Luiggi","xdxd");
        } catch (UserAlreadyPresentException e) {
            System.out.println("User already present");
        }

        /*try{
            container.getIterator("Luiggi","xdxd");
        } catch (NoUserException e) {
            abort("no usergetite");
        }*/

        try{
            container.put("Luiggi","xdxd","samsung");
        }catch (NoUserException e){
            abort("no user");
        }

        try{
            boolean a = container.put("Giua","comefosseantani","apple");
            System.out.println(a);
        }catch (NoUserException e){
            abort("no user");
        }

        try {
            container.put("Fruvio", "GiacominoPaneEVino", "xiaomi");
        } catch (NoUserException e){
            abort("no user");
        }

        try {
            container.put("Fruvio", "GiacominoPaneEVino", "xiaomi2");
        } catch (NoUserException e){
            abort("no user");
        }

        try {
            container.put("Fruvio", "GiacominoPaneEVino", "xiaomi3");
        } catch (NoUserException e){
            abort("no user");
        }

        try {
            container.put("Fruvio", "GiacominoPaneEVino", "xiaomi4");
        } catch (NoUserException e){
            abort("no user");
        }
        try {
            System.out.println(container.get("Fruvio", "GiacominoPaneEVino", "xiaomi4"));
        } catch (NoUserException e){
            abort("no user");
        } catch (NoDataException e){
            abort("no data");
        }

        try {
            System.out.println(container.remove("Fruvio", "GiacominoPaneEVino", "apple"));
        } catch (NoUserException e) {
            abort("no user");
        }

        try {
            System.out.println(container.get("Fruvio", "GiacominoPaneEVino","xiaomi"));
        } catch (NoUserException e){
            abort("no user");
        } catch (NoDataException e){
            abort("no data2");
        }
        try {
            System.out.println(container.getSize("Giua","comefosseantani"));
        } catch (NoUserException e) {
            abort("no user");
        }
        try {
            String samsungRemoved = container.remove("Luiggi", "xdxd", "samsung");
            System.out.println(samsungRemoved);
        } catch (NoUserException e){
            abort("no user");
        }

        try {
            String appleRemoved = container.remove("Fruvio", "GiacominoPaneEVino", "xiaomi3");
            System.out.println(appleRemoved);
        } catch (NoUserException e){
            abort("no user");
        }

        try {
            container.removeUser("Fruvio", "GiacominoPaneEVino");
        } catch (NoUserException e) {
            abort("no user");
        }
        try {
            container.put("nomechemuore","GiacominoPaneEVino", "ciaomondo");
        } catch (NoUserException e) {
            System.out.println("User not present");
        }try {
            container.removeUser("nomechemuore","GiacominoPaneEVino");
        } catch (NoUserException e) {
            System.out.println("User not present");
        }/*try {
            container.printUsers();
        } catch (NullPointerException e) {
            System.out.println("No users");
        }*/
    }

    public static void main(String[] args) {
        SecureDataContainerArrayList<String> hashContainer = new SecureDataContainerArrayList<>();
        testContainer(hashContainer);
    }
}
