import exceptions.*;
import java.util.Iterator;

public class SecureDataContainerTest {

    private static void testContainer(SecureDataContainerArrayList<String> container) {
        try {
            container.createUser("Giua", "comefosseantani");
        } catch (UserAlreadyPresentException e) {
            System.out.println("User already present");
        }

        try {
            container.createUser("Fruvio", "GiacominoPaneEVino");
        } catch (UserAlreadyPresentException e) {
            System.out.println("User already present");
        }

        try {
            container.createUser("Luiggi", "xdxd");
        } catch (UserAlreadyPresentException e) {
            System.out.println("User already present");
        }
        try {
            container.createUser("Luiggi", "xdxd");
        } catch (UserAlreadyPresentException e) {
            System.out.println("User already present");
        }

        try {
            container.put("Luiggi", "xdxd", "samsung");
        } catch (NoUserException e) {
            System.out.println("no user");
        }

        try {
            container.share("Luiggi","xdxr","Fruvio","samsung");
        } catch (NoUserException e) {
            System.out.println("no user");
        }

        System.out.println("Stampa Elementi Fruvio");
        try {
            Iterator cacca = container.getIterator("Fruvio", "GiacominoPaneEVino");
            while (cacca.hasNext()) {
                System.out.println(cacca.next());
            }
        } catch (NoUserException e) {
            System.out.println("no usergetite");
        }
        try {
            boolean a = container.put("Giua", "comefosseantani", "apple");
            System.out.println(a);
        } catch (NoUserException e) {
            System.out.println("no user");
        }

        try {
            container.put("Fruvio", "GiacominoPaneEVino", "xiaomi");
        } catch (NoUserException e) {
            System.out.println("no user");
        }

        try {
            container.put("Fruvio", "GiacominoPaneEVino", "xiaomi2");
        } catch (NoUserException e) {
            System.out.println("no user");
        }

        try {
            container.put("Fruvio", "GiacominoPaneEVino", "xiaomi3");
        } catch (NoUserException e) {
            System.out.println("no user");
        }

        try {
            container.put("Fruvio", "GiacominoPaneEVino", "xiaomi4");
        } catch (NoUserException e) {
            System.out.println("no user");
        }
        try {
            System.out.println(container.get("Fruvio", "GiacominoPaneEVino", "xiaomi4"));
        } catch (NoUserException e) {
            System.out.println("no user");
        } catch (NoDataException e) {
            System.out.println("no data");
        }

        try {
            System.out.println(container.remove("Fruvio", "GiacominoPaneEVino", "apple"));
        } catch (NoUserException e) {
            System.out.println("no user");
        } catch (NoDataException e) {
            System.out.println("no data");
        }

        try {
            System.out.println(container.get("Fruvio", "GiacominoPaneEVino", "xiaomi"));
        } catch (NoUserException e) {
            System.out.println("no user");
        } catch (NoDataException e) {
            System.out.println("no data2");
        }
        try {
            container.copy("Fruvio", "GiacominoPaneEVino", "xiaomi5");
        } catch (NoUserException e) {
            System.out.println("no user");
        } catch (DataAlreadyPresentException e) {
            System.out.println("data already present");
        }

        try {
            container.share("Fruvio", "GiacominoPaneEVino","Giua", "xiaomi5");
        } catch (NoUserException e){
            System.out.println("no user");
        }
        try {
            System.out.println(container.getSize("Giua", "comefosseantani"));
        } catch (NoUserException e) {
            System.out.println("no user");
        }

        try {
            System.out.println(container.getSize("Fruvio", "GiacominoPaneEVino"));
        } catch (NoUserException e) {
            System.out.println("no user");
        }

        try {
            String samsungRemoved = container.remove("Luiggi", "xdxd", "samsung");
            System.out.println(samsungRemoved);
        } catch (NoUserException e) {
            System.out.println("no user");
        } catch (NoDataException e) {
            System.out.println("no data");
        }

        try {
            String xiaomi3Removed = container.remove("Fruvio", "GiacominoPaneEVino", "xiaomi3");
            System.out.println(xiaomi3Removed);
        } catch (NoUserException e) {
            System.out.println("no user");
        } catch (NoDataException e) {
            System.out.println("no data");
        }

        System.out.println("Stampa Elementi Fruvio");
        try {
            Iterator cacca = container.getIterator("Fruvio", "GiacominoPaneEVino");
            while (cacca.hasNext()) {
                System.out.println(cacca.next());
            }
        } catch (NoUserException e) {
            System.out.println("no usergetite");
        }

        System.out.println("Stampa Elementi Giua");
        try {
            Iterator iter = container.getIterator("Giua", "comefosseantani");
            while (iter.hasNext()) {
                System.out.println(iter.next());
            }
        } catch (NoUserException e) {
            System.out.println("no usergetite");
        }

        try {
            Iterator iter = container.getIterator("Fruvio", "GiacominoPaneEVino");
            while (iter.hasNext()) {
                System.out.println(iter.next());
            }
        } catch (NoUserException e) {
            System.out.println("no usergetite");
        }

        try {
            container.removeUser("Fruvio", "GiacominoPaneEVino");
        } catch (NoUserException e) {
            System.out.println("no user");
        }
        try {
            container.put("nomechemuore", "GiacominoPaneEVino", "ciaomondo");
        } catch (NoUserException e) {
            System.out.println("User not present");
        }

        try {
            container.removeUser("nomechemuore", "GiacominoPaneEVino");
        } catch (NoUserException e) {
            System.out.println("User not present");
        }
        try {
            container.unshare("Luiggi","xdxr","Fruvio","samsung");
        } catch (NoUserException e){
            System.out.println("no user");
        }

        System.out.println("Stampa Elementi Fulvio");
        try {
            Iterator iter = container.getIterator("Fruvio", "GiacominoPaneEVino");
            while (iter.hasNext()) {
                System.out.println(iter.next());
            }
        } catch (NoUserException e) {
            System.out.println("no usergetite");
        }
    }

    public static void main(String[] args) {
        SecureDataContainerArrayList<String> hashContainer = new SecureDataContainerArrayList<>();
        testContainer(hashContainer);
    }
}
