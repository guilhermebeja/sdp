package Client;

import DataStructures.Conversation;
import DataStructures.Message;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {

        Client c1 = new Client("Ze", "qwerty", "127.0.0.1", 8080);
        Client c2 = new Client("Gui", "qwerty", "127.0.0.1", 8081);

        Conversation conv = new Conversation();

        Scanner s = new Scanner(System.in);

        while (true){
            c1.sendMessage("127.0.0.1", 8081, s.nextLine());
        }
    }
}
