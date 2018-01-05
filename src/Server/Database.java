package Server;

import DataStructures.Conversation;
import Entities.User;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Database {
    private static ArrayList<User> users = new ArrayList<>();
    private static ArrayList<Conversation> conversations = new ArrayList<>();
    private static ArrayList<User> friends = new ArrayList<>();

    //region Getters and Setters
    public static ArrayList<User> getUser(Predicate<User> search){
        return (ArrayList<User>) users.stream().filter(search).collect(Collectors.toList());
    }


    public static  ArrayList<Conversation> getConversations(Predicate<Conversation> search){
        return (ArrayList<Conversation>) conversations.stream().filter(search).collect(Collectors.toList());
    }

    public static ArrayList<User> getFriends(Predicate<User> search){
        return (ArrayList<User>) users.stream().filter(search).collect(Collectors.toList());
    }

    //endregion

    //region Methods
    public static void addUser(User u){
        users.add(u);
    }



    public static boolean containsUser(Predicate<User> user){
        return users.stream().anyMatch(user);
    }
    //endregion
}
