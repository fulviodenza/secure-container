import java.util.TreeMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import exceptions.*;

public class TreeMapSecureDataContainer<E> implements SecureDataContainer<E> {


    private TreeMap< User, List<Element<E> > > db;

    @Override
    public void createUser(String Id, String passw) throws UserAlreadyPresentException {

    }

    @Override
    public int getSize(String Owner, String passw) throws InvalidCredentialsException {
        return 0;
    }

    @Override
    public boolean put(String Owner, String passw, E data) {
        return false;
    }

    @Override
    public E get(String Owner, String passw, E data) {
        return null;
    }

    @Override
    public E remove(String Owner, String passw, E data) {
        return null;
    }

    @Override
    public void copy(String Owner, String passw, E data) throws InvalidCredentialsException,
                                                                ElementAlreadyPresentException {

    }

    @Override
    public void share(String Owner, String passw, String Other, E data) throws  InvalidCredentialsException,
                                                                                UserNotPresentException,
                                                                                UserNotAllowedException,
                                                                                ElementAlreadyPresentException,
                                                                                UserAlreadyAllowedException {

    }

    @Override
    public Iterator<E> getIterator(String Owner, String passw) throws InvalidCredentialsException {
        return null;
    }
}
