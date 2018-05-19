package drankspel;

import drankspel.game.Game;
import drankspel.interfaces.ClientInterface;

import java.util.ArrayList;

public class Main {

    public static void main(String args[]){
        System.out.println("drink");
        ClientInterface client = new ClientInterface("Tim", "Tim");
        ClientInterface client2 = new ClientInterface("Simon", "Simon");
        ClientInterface client3 = new ClientInterface("Jordy", "Jordy");
        ArrayList<ClientInterface> clients = new ArrayList<>();
        clients.add(client);
        clients.add(client2);
        clients.add(client3);
        Game game = new Game(clients);

    }
}
