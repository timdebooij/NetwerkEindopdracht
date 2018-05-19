package drankspel;

import drankspel.game.Game;
import drankspel.interfaces.ClientInterface;

public class Main {

    public static void main(String args[]){
        System.out.println("drink");
        ClientInterface client = new ClientInterface("Tim", "Tim");
        Game game = new Game(client);

    }
}
