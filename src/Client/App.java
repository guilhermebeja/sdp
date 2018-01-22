package Client;

import Server.*;

import java.io.IOException;


public class App {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Server server = new Server(8081);
        server.start();
        Client c1 = new Client("", "", "127.0.0.1", 8080);
        Server.Parameters hdrs = new Server.Parameters();
        hdrs.addParameter("username", "teste");
        hdrs.addParameter("password", "");
        hdrs.addParameter("ip", "");
        hdrs.addParameter("port", "0");

    }
}
