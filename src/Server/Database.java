package Server;

import Entities.User;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Database {
    private static ArrayList<User> users=new ArrayList<>();

    public static ArrayList<User> getUser(Predicate<User> search){
        return (ArrayList<User>) users.stream().filter(search).collect(Collectors.toList());
    }

    public static void addUser(User u){
        users.add(u);
    }

    public static boolean containsUser(Predicate<User> user){
        return users.stream().anyMatch(user);
    }
}
