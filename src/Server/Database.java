package Server;

import DataStructures.Conversation;
import Entities.User;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Database {
    private static ArrayList<User> users = new ArrayList<>();
    private static ArrayList<Conversation> conversations = new ArrayList<>();

    public static ArrayList<User> getUsers(Predicate<User> search){
        return (ArrayList<User>) users.stream().filter(search).collect(Collectors.toList());
    }

    public static Optional<User> getUserByUsername(String username){
        return users.stream().filter(user -> user.getUsername().equals(username)).findFirst();
    }

    public static  ArrayList<Conversation> getConversations(Predicate<Conversation> search){
        return (ArrayList<Conversation>) conversations.stream().filter(search).collect(Collectors.toList());
    }


    public static void addUser(User u){
        users.add(u);
    }

    public static boolean containsUser(Predicate<User> user){
        return users.stream().anyMatch(user);
    }

    public static Optional<Conversation> getConversationByID(String id){
        return conversations.stream().filter(c -> c.getId().equals(id)).findFirst();
    }

    public static boolean containsConversation(Predicate<Conversation> conversation){
        return conversations.stream().anyMatch(conversation);
    }

    public static void addConversation(Conversation conv){
        conversations.add(conv);
    }
}
