package Client;

import Server.*;

import java.io.IOException;


public class App {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Server server = new Server(8081);
        server.start();
        Client c1 = new Client("", "", "127.0.0.1", 8080);
        Headers hdrs = new Headers();
        hdrs.addHeader("username", "teste");
        hdrs.addHeader("password", "");
        hdrs.addHeader("ip", "");
        hdrs.addHeader("port", "0");

        while(true){
            ServerRequest sr = new ServerRequest(
                    RequestType.POST, "user",
                    hdrs,
                    new IPConnection("", "0.0.0.0", 0, 8081));

            ServerResponse rsp = c1.requestServer(sr);

            System.out.println(rsp.getResponse().toString());



            long tStart = System.currentTimeMillis();
            while(System.currentTimeMillis() - tStart < 1000){}
        }
    }



}
