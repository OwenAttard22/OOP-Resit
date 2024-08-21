package oatt;
public class User {
    private String _name;
    private String _membership;

    public User(String name, String membership){
        set_name(name);
        set_membership(membership);
    }

    void set_name(String name){
        _name = name;
    }

    void set_membership(String membership){
        _membership = membership;
    }
    
    String get_name(){
        return _name;
    }

    String get_membership(){
        return _membership;
    }

}