import java.util.ArrayList;

public class ArrayListElement<E> {

    private E data;
    private ArrayList<KeyCouple> Users = new ArrayList<>();

    public ArrayListElement(E data1, KeyCouple user){
        data = data1;
        Users.add(user);
    }

    public void addUsers(KeyCouple user){
        Users.add(user);
    }

    public E getData(){
        return data;
    }

    public String returnOwner(){
        String id = null;
        if(Users.size() > 0){
            id = Users.get(0).getIdUser();
        }
        return id;
    }
}


