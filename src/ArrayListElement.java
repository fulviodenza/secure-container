import java.util.ArrayList;
import java.util.Objects;

public class ArrayListElement<E> {

    private E data;
    private ArrayList<KeyCouple> Users = new ArrayList<>();

    public ArrayListElement(E data1, KeyCouple user){
        data = data1;
        Users.add(user);
    }

    public boolean isOfOwner(String Owner){
        for(KeyCouple k: Users){
            if(k.getIdUser().equals(Owner)){
                return true;
            }
        }
        return false;
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

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArrayListElement arrayListElement = (ArrayListElement) o;
        return Objects.equals(data, arrayListElement.getData()) &&
                Objects.equals(Users.get(0).getIdUser(), arrayListElement.returnOwner());
    }

    public void remove(KeyCouple user) {
        Users.remove(user);
    }
}


