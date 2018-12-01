import java.util.ArrayList;
import java.util.List;

import exceptions.*;

/*Overview: Tipo contenitore del generico con aggiunge due informazioni: Il possessore dell'elemento
                e una lista di utenti autorizzati ad accedere al dato
                IR: owner != null, el != null e allowedUsers != null
                FA: a(el, owner) = {owner, el, allowedUsers} dove owner è la stringa che indica
                                  il proprietario dell'elemento el, e allowUser indica gli utenti autorizzati
                                  ad accedere al dato
    */
class Element<E> {
    private String owner;
    private E el;
    private List<String> allowedUsers;


    /*
    REQUIRES: el, owner != null, altrimenti lancia NullPointerException (unchecked)
    EFFECTS: inizializza this.el, this.owner a (el, owner) e allowedUsers a una lista vuota
    */
    public Element(E el, String owner) {
        if (el == null || owner == null) throw new NullPointerException();
        this.el = el;
        this.owner = owner;
        allowedUsers = new ArrayList<>();
    }

    /*EFFECTS: Restituisce l'elemento contenuto in this*/
    public E getEl() {
        return el;
    }

    /*EFFECTS: Restituisce true se who è il proprietario di this
      REQUIRES: who != null, altrimenti lancia NullPointerException (unchecked) */
    public boolean ownedBy(String who) {
        return who.equals(owner);
    }

    /*EFFECTS: Restituisce true se il possessore di other e this sono uguali e se
               l'elemento in other è lo stesso di this (secondo la concezione di uguaglianza
               di E)
      REQUIRES: other deve essere una istanza di Element, altrimenti lancia IllegalArgumentException (checked)
                other non dev'essere null, altrimenti lancia NullPointerException (unchecked)
    */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Element)) throw new IllegalArgumentException();
        Element o = (Element) other;
        return (o.el.equals(this.el) && o.owner.equals(this.owner));
    }

    /*EFFECTS: Permette all'utente other di accedere ad El
      REQUIRES: other != null, altrimenti lancia NullPointerException (unchecked)
                other non in allowedUsers, altrimenti lancia UserAlreadyAllowedException (checked)
      MODIFIES: this */
    public void allowUser(String other) throws UserAlreadyAllowedException {
        if (other == null) throw new NullPointerException();
        if (allowedUsers.contains(other)) throw new UserAlreadyAllowedException();
        allowedUsers.add(other);
    }

    /*EFFECTS: Blocca l'accesso al dato a other
      REQUIRES: other != null, altrimenti lancia NullPointerException (unchecked)
                other in allowedUsers, altrimenti lancia UserNotAllowedException (checked)
      MODIFIES: this */
    public void denyUser(String other) throws UserNotAllowedException {
        if (other == null) throw new NullPointerException();
        if (!allowedUsers.contains(other)) throw new UserNotAllowedException();
        allowedUsers.remove(other);
    }

    /*EFFECTS: Restituisce true who può accedere a this
      REQUIRES: who != null, altrimenti lancia (da parte di ownedBy) NullPointerException (unchecked)
    */
    public boolean canBeAccessedBy(String who) {
        return ownedBy(who) || allowedUsers.contains(who);
    }
}