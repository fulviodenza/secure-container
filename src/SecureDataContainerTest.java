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
        /**
         * ******************************************************************************************************
         * ******************************************************************************************************
         * *GROSSO PROBLEMA: TUTTI I METODI ESCONO CON ECCEZIONI DI MERDA, SISTEMA QUESTA SCHIFEZZA APPENA PUOI**
         * ******************************************************************************************************
         * ******************************************************************************************************
         * ******************************************************************************************************
         * */
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
        System.out.println(container.getSize("Giua","comefosseantani"));
        String huaweiRemoved = container.remove("Luiggi", "xdxd", "samsung");
        System.out.println(huaweiRemoved);
        huaweiRemoved = container.remove("Giua", "comefosseantani", "huawei");
        System.out.println(huaweiRemoved);
        /*try {
            container.put("nomechemuore","GiacominoPaneEVino", "ciaomondo");
        } catch (NoUserException e) {
            System.out.println("User not present");
        }*/
    }

    public static void main(String[] args) {
        SecureDataContainerHashMap<String> hashContainer = new SecureDataContainerHashMap<>();
        testContainer(hashContainer);
    }
}
