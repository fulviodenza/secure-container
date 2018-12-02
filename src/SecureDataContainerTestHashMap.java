import exceptions.*;
import java.util.Iterator;

public class SecureDataContainerTestHashMap {

    private static void testContainer(SecureDataContainerHashMap<String> container) {
        try {
            container.createUser("User1", "passUser1");
        } catch (UserAlreadyPresentException e) {
            System.out.println("User already present");
        }

        try {
            container.createUser("User2", "passUser2");
        } catch (UserAlreadyPresentException e) {
            System.out.println("User already present");
        }

        try {
            container.createUser("User3", "passUser3");
        } catch (UserAlreadyPresentException e) {
            System.out.println("User already present");
        }
        try {
            container.createUser("User2", "xdxd");
        } catch (UserAlreadyPresentException e) {
            System.out.println("User already present");
        }

        //Insert brand mobile phone
        try {
            container.put("User3", "passUser3", "samsung");
        } catch (NoUserException e) {
            System.out.println("no user");
        }

        try {
            container.share("User3","passUser3","User2","samsung");
        } catch (NoUserException e) {
            System.out.println("no user");
        } catch (NoDataException e) {
            System.out.println(("no data"));
        } catch (DataAlreadyPresentException e) {
            System.out.println("data already present");
        }

        System.out.println("Stampa Elementi User2");
        try {
            Iterator it = container.getIterator("User2", "passUser2");
            while (it.hasNext()) {
                System.out.println(it.next());
            }
        } catch (NoUserException e) {
            System.out.println("no usergetite");
        }

        try {
            boolean a = container.put("User1", "passUser1", "apple");
            System.out.println(a);
        } catch (NoUserException e) {
            System.out.println("no user");
        }

        try {
            container.put("User2", "passUser2", "xiaomi");
        } catch (NoUserException e) {
            System.out.println("no user");
        }


        try {
            container.put("User2", "passUser2", "huawei");
        } catch (NoUserException e) {
            System.out.println("no user");
        }

        try {
            container.put("User1", "passUser1", "blackberry");
        } catch (NoUserException e) {
            System.out.println("no user");
        }

        try {
            container.put("User3","passUser3", "htc");
        } catch (NoUserException e) {
            System.out.println("no user");
        }
        try {
            System.out.println(container.get("User2", "passUser2", "xiaomi"));
        } catch (NoUserException e) {
            System.out.println("no user");
        } catch (NoDataException e) {
            System.out.println("no data");
        }

        //Should throw "no data" exception
        try {
            System.out.println(container.remove("User2", "passUser2", "apple"));
        } catch (NoUserException e) {
            System.out.println("no user");
        } catch (NoDataException e) {
            System.out.println("no data");
        }

        try {
            container.copy("User2", "passUser2", "motorola");
        } catch (NoUserException e) {
            System.out.println("no user");
        } catch (DataAlreadyPresentException e) {
            System.out.println("data already present");
        }

        try {
            container.share("User3", "passUser3","User2", "htc");
        } catch (NoUserException e) {
            System.out.println("no user");
        } catch (NoDataException e) {
            System.out.println(("no data"));
        } catch (DataAlreadyPresentException e) {
            System.out.println("data already present");
        }

        try {
            System.out.println(container.getSize("User2", "passUser2"));
        } catch (NoUserException e) {
            System.out.println("no user");
        }

        try {
            System.out.println(container.getSize("User1", "passUser1"));
        } catch (NoUserException e) {
            System.out.println("no user");
        }

        //should throw "no user" exception
        try {
            System.out.println(container.getSize("User3", "passUser2"));
        } catch (NoUserException e) {
            System.out.println("no user");
        }

        try {
            String samsungRemoved = container.remove("User3", "passUser3", "samsung");
            System.out.println(samsungRemoved);
        } catch (NoUserException e) {
            System.out.println("no user");
        } catch (NoDataException e) {
            System.out.println("no data");
        }

        try {
            String xiaomiRemoved = container.remove("User2", "passUser2", "xiaomi");
            System.out.println(xiaomiRemoved);
        } catch (NoUserException e) {
            System.out.println("no user");
        } catch (NoDataException e) {
            System.out.println("no data");
        }

        System.out.println("Stampa Elementi User2");
        try {
            Iterator it = container.getIterator("User2", "passUser2");
            while (it.hasNext()) {
                System.out.println(it.next());
            }
        } catch (NoUserException e) {
            System.out.println("no usergetite");
        }

        System.out.println("Stampa Elementi User1");
        try {
            Iterator it = container.getIterator("User1", "passUser1");
            while (it.hasNext()) {
                System.out.println(it.next());
            }
        } catch (NoUserException e) {
            System.out.println("no usergetite");
        }

        try {
            container.removeUser("User2", "passUser2");
        } catch (NoUserException e) {
            System.out.println("no user");
        }

        //Should throw "User not present" exception
        try {
            container.put("UserNotPrensent", "passUserNotPresent", "helloworld");
        } catch (NoUserException e) {
            System.out.println("User not present");
        }

        //Should throw "User not present" exception
        try {
            container.removeUser("UserNotPrensent", "passUserNotPresent");
        } catch (NoUserException e) {
            System.out.println("User not present");
        }

        System.out.println("Stampa Elementi User3");
        try {
            Iterator it = container.getIterator("User3", "passUser3");
            while (it.hasNext()) {
                System.out.println(it.next());
            }
        } catch (NoUserException e) {
            System.out.println("no usergetite");
        }
    }

    public static void main(String[] args) {
        SecureDataContainerHashMap<String> hashContainer = new SecureDataContainerHashMap<>();
        testContainer(hashContainer);
    }
}
