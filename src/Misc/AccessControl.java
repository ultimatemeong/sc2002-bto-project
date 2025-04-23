package Misc;

import Users.User;

/**
 * AccessControl is an interface that defines methods for managing access control to various objects.
 * It provides a generic way to check, add, and delete access levels for users on specific objects.
 * @param <T> The type of object for which access control is being managed.
 * 
 * @author Ang Qi Xuan Evan
 * @version 1.0
 * @since 2025-04-23
 */
public interface AccessControl<T>{
    
    /**
     * Checks if the user has access to the given object.
     * @param t The object to check access for.
     * @param user The user whose access is being checked.
     * @return A string indicating the access level of the user for the given object.
     */
    public abstract String check(T t , User user);

    /**
     * Adds a new object with the specified access level for the user.
     * @param t The object to add.
     * @param user The user to whom the access is being granted.
     * @param accessLevel The access level to be granted.
     */
    public abstract void add(T t, User user, String accessLevel);

    /**
     * Updates the access level of the specified object for the user.
     * @param t The object to update.
     * @param user The user whose access is being updated.
     * @param accessLevel The new access level to be granted.
     */
    public abstract void delete(T t, User user); 
}
