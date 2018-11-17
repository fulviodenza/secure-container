import exceptions.*;

public class SecureDataContainerTest {

    private static void abort(String msg) {
        System.out.println(msg);
        System.exit(-1);
    }

    private static void testContainer(SecureDataContainerHashMap<String> container) {
        try {
            container.createUser("Giua","comefosseantani");
        } catch (UserAlreadyPresent e) {
            System.out.println("User already present");
        }

        try {
            container.createUser("Fruvio","GiacominoPaneEVino");
        } catch (UserAlreadyPresent e) {
            System.out.println("User already present");
        }

        try {
            container.createUser("Luiggi","xdxd");
        } catch (UserAlreadyPresent e) {
            System.out.println("User already present");
        }

        try{
            container.getIterator("Luiggi","xdxd");
        } catch (NoUserException e){
            abort("no usergetite");
        }
        try{
            container.put("Luiggi","xdxd","samsung");
        }catch (NoUserException e){
            abort("no user");
        }

        try{
            container.put("Giua","comefosseantani","apple");
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

        System.out.println(container.getSize("Giua","comefosseantani"));
        try {
            String samsungRemoved = container.remove("Luiggi", "xdxd", "samsung");
            System.out.println(samsungRemoved);
        } catch (NoUserException e){
            abort("no user");
        }

        try {
            String huaweiRemoved = container.remove("Giua", "comefosseantani", "apple");
            System.out.println(huaweiRemoved);
        } catch (NoUserException e){
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
        }try {
            container.printUsers();
        } catch (NullPointerException e){
            System.out.println("No users");
        }
    }

    public static void main(String[] args) {
        SecureDataContainerHashMap<String> hashContainer = new SecureDataContainerHashMap<>();
        testContainer(hashContainer);
    }
}
