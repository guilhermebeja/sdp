package Server;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Scanner scanner = new Scanner(System.in);

        String line = scanner.nextLine();

        while(true){
            if(line.equals("EXIT")){
                break;
            }
            Socket s  = new Socket("127.0.0.1", 8081);
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream is = new ObjectInputStream(s.getInputStream());
            oos.writeObject(line);

            ServerResponse resp = (ServerResponse) is.readObject();

            oos.close();
            is.close();

            System.out.println("Status Code: " + resp.getStatusCode());
            System.out.println("Message: " + resp.getResponse());
            line = scanner.nextLine();
            s.close();
        }



    }
}
