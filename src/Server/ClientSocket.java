package Server;

import Server.Exceptions.RequestNotValidException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientSocket extends Thread{

    private Socket soc;
    private Server server;

    ObjectInputStream ois = null;
    ObjectOutputStream oos = null;
    public ClientSocket(Socket soc, Server server){
        this.soc = soc;
        this.server = server;

    }

    public void notifyClient(Object toSend){

    }

    @Override
    public void run() {

        try {
            ois = new ObjectInputStream(soc.getInputStream());
            oos = new ObjectOutputStream(soc.getOutputStream());
            boolean b = false;
            ServerResponse rs = new ServerResponse(StatusCode.ERROR, "Internal Server Error");
            while(true){
                if(soc.getInputStream().available()!=0){
                    try{
                        ServerRequest req = new ServerRequest((String)ois.readObject(), soc.getInetAddress().getHostAddress(), soc.getPort());
                        if(req.getRequestType().equals(RequestType.DISCONNECT)){
                            break;
                        }
                        rs = server.respond(req);
                    }
                    catch (RequestNotValidException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } finally{
                        oos.writeObject(rs);
                        oos.reset();
                    }
                }
            }

            ois.close();
            oos.close();
            soc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
