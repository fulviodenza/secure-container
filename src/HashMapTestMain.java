import java.util.Iterator;
import java.util.Scanner;
public class HashMapTestMain{
    public static void stampaIt(Iterator t){
        System.out.println("I tuoi dati sono:");
        while(t.hasNext()){
            System.out.println(t.next());
        }
    }
    public static void main(String [] args){
        SecureDataContainerHashMap<String> database = new SecureDataContainerHashMap<>();
        Scanner tast = new Scanner(System.in);
        int scelta = 7;
        String name="",passw="",dato="",altro="";
        System.out.println("Benvenuto! Scegli cosa vuoi fare:");
        System.out.println("1 - Registrati\n2 - Aggiungi dati\n3 - Copia dato\n4 - Visualizza numero elementi\n5 - Ottieni copia valore\n6 - Cancella dato\n7 - Condividi dato\n8 - Stampa con iteratore\n9 - Autorizza un utente\n10 - Banna un utente\n0 - Esci");
        scelta = tast.nextInt();
        while(scelta >= 0){
            switch (scelta){
                case 0:
                    System.out.println("A presto!");
                    scelta = -1;
                    break;
                case 1:
                    System.out.println("Inserisci nome utente");
                    name = tast.next();
                    System.out.println("Inserisci password");
                    passw = tast.next();
                    try {
                        database.createUser(name, passw);
                        System.out.println("Utente registrato con successo! Cosa vuoi fare ora?");
                    }catch(DoubleUserException e){
                       e.printStackTrace();
                    }
                    scelta = tast.nextInt();
                    break;
                case 2:
                    System.out.println("Inserisci nome utente");
                    name = tast.next();
                    System.out.println("Inserisci password");
                    passw = tast.next();
                    System.out.println("Inserisci dato da aggiungere");
                    dato = tast.next();
                    try {
                        database.put(name,passw,dato);
                        System.out.println("Dato aggiunto con successo! Cosa vuoi fare ora?");
                    } catch (NoUserException e) {
                        e.printStackTrace();
                    }
                    scelta = tast.nextInt();
                    break;
                case 3:
                    System.out.println("Inserisci nome utente");
                    name = tast.next();
                    System.out.println("Inserisci password");
                    passw = tast.next();
                    System.out.println("Inserisci dato da copiare");
                    dato = tast.next();
                    try {
                        database.copy(name,passw,dato);
                        System.out.println("Dato copiato con successo! Cosa vuoi fare ora?");
                    } catch (NoUserException e) {
                        e.printStackTrace();
                    } catch (DataNotFoundException e) {
                        e.printStackTrace();
                    }
                    scelta = tast.nextInt();
                    break;
                case 4:
                    System.out.println("Inserisci nome utente");
                    name = tast.next();
                    System.out.println("Inserisci password");
                    passw = tast.next();

                    try {
                        System.out.println("Hai "+database.getSize(name,passw)+" elementi! Cosa vuoi fare ora?");
                    } catch (NoUserException e) {
                        e.printStackTrace();
                    }
                    scelta = tast.nextInt();
                    break;
                case 5:
                    System.out.println("Inserisci nome utente");
                    name = tast.next();
                    System.out.println("Inserisci password");
                    passw = tast.next();
                    System.out.println("Inserisci dato da visualizzare");
                    dato = tast.next();
                    try {
                        System.out.println("Il dato che hai copiato è: " + database.get(name, passw, dato) + " Cosa vuoi fare ora?");
                    }catch(NoUserException e){
                        e.printStackTrace();
                    }catch(DataNotFoundException e){
                        e.printStackTrace();
                    }catch(NotAuthorizedUserException e){
                        e.printStackTrace();
                    }
                    scelta = tast.nextInt();
                    break;
                case 6:
                    System.out.println("Inserisci nome utente");
                    name = tast.next();
                    System.out.println("Inserisci password");
                    passw = tast.next();
                    System.out.println("Inserisci dato da rimuovere");
                    dato = tast.next();
                    try {
                        database.remove(name,passw,dato);
                        System.out.println("Dato rimosso con successo! Cosa vuoi fare ora?");
                    } catch (NoUserException e) {
                        e.printStackTrace();
                    } catch (DataNotFoundException e) {
                        e.printStackTrace();
                    }
                    scelta = tast.nextInt();
                    break;
                case 7:
                    System.out.println("Inserisci nome utente");
                    name = tast.next();
                    System.out.println("Inserisci password");
                    passw = tast.next();
                    System.out.println("Inserisci dato da condividere");
                    dato = tast.next();
                    System.out.println("Inserisci utente con cui condividere");
                    altro = tast.next();
                    try {
                        database.share(name,passw,altro,dato);
                        System.out.println("Dato condiviso con "+altro+" con successo! Cosa vuoi fare ora?");
                    } catch (NoUserException e) {
                        e.printStackTrace();
                    }
                    scelta = tast.nextInt();
                    break;
                case 8:
                    System.out.println("Inserisci nome utente");
                    name = tast.next();
                    System.out.println("Inserisci password");
                    passw = tast.next();
                    try {
                        stampaIt(database.getIterator(name,passw));
                        System.out.println("Cosa vuoi fare ora?");
                    } catch (NoUserException e) {
                        e.printStackTrace();
                    }
                    scelta = tast.nextInt();
                    break;
                case 9:
                    System.out.println("Inserisci nome utente");
                    name = tast.next();
                    System.out.println("Inserisci password");
                    passw = tast.next();
                    System.out.println("Inserisci utente da autorizzare");
                    altro = tast.next();
                    try {
                        database.empowerUser(name,passw,altro);
                        System.out.println(altro+" autorizzato con successo! Cosa vuoi fare ora?");
                    } catch (NoUserException e) {
                        e.printStackTrace();
                    } catch (AlreadyPoweredException e) {
                        e.printStackTrace();
                    }
                    scelta = tast.nextInt();
                    break;
                case 10:
                    System.out.println("Inserisci nome utente");
                    name = tast.next();
                    System.out.println("Inserisci password");
                    passw = tast.next();
                    System.out.println("Inserisci utente da autorizzare");
                    altro = tast.next();
                    try {
                        database.depowerUser(name,passw,altro);
                        System.out.println(altro+" bannato con successo! Cosa vuoi fare ora?");
                    } catch (NoUserException e) {
                        e.printStackTrace();
                    } catch (AlreadyWeakException e) {
                        e.printStackTrace();
                    }
                    scelta = tast.nextInt();
                    break;
            }
        }


    }
}
