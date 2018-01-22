package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        String line = scanner.nextLine();
        Socket s  = null;
        try {
            s = new Socket("192.168.1.70", 8081);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(s.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        ObjectInputStream is = null;
        try {
            is = new ObjectInputStream(s.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(true){
            if(line.equals("EXIT")){
                break;
            }

            try {
                oos.writeObject(line);
            } catch (IOException e) {
                e.printStackTrace();
            }

            ServerResponse resp = null;
            try {
                resp = (ServerResponse) is.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            System.out.println("Status Code: " + resp.getStatusCode());
            System.out.println("Message: " + resp.getResponse());
            line = scanner.nextLine();

        }

        try {
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
