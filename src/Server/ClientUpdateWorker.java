package Server;

import Client.Client;

public class ClientUpdateWorker extends Thread{
    private Client client;

    public ClientUpdateWorker(Client client) {
        this.client = client;
    }

    @Override
    public void run() {

    }
}
