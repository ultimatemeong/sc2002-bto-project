package Misc;

import Users.User;

public interface AccessControl<T>{
    
    public abstract String check(T t , User user);

    public abstract void add(T t, User user, String accessLevel);

    public abstract void delete(T t, User user); 
}
