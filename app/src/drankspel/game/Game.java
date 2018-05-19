package drankspel.game;


import drankspel.interfaces.ClientInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Game {

    private ArrayList<Card> cards;
    private ArrayList<ClientInterface> player;
    private int playerNumber = 1;

    public Game(ClientInterface client) {

        cards = new ArrayList<>();
        player = new ArrayList<>();
        player.add(client);


        Stack stack = new Stack(1);
        cards = stack.getCards();
        System.out.println(stack.toString());
        System.out.println("amount of cards: " + cards.size());
        playGame();


    }

    /**
     * the main method of the game
     * all game logic will be in this method
     */
    public void playGame(){
        Scanner reader = new Scanner(System.in);
        while(!cards.isEmpty()){
            //sent cards to selected player
            ArrayList<Card> sentCards = selectFiveCards();
            player.get(0).setCards(sentCards);
            //System.out.println(sentCards);

            //read data, input should be between 0 and the amount of cards -1
//            System.out.println("Choose card : ");
//            int n = reader.nextInt();

            //show rule and remove used card
            //System.out.println(sentCards.get(n).getRule());
            //sentCards.remove(n);
            //get four back
            returnFourCards(player.get(0).chooseCard());
            //go to next player
            selectNextPlayer();

        }
        reader.close();
    }

    /**
     * select next player to get cards
     */
    public void selectNextPlayer(){
        if(playerNumber == player.size())
            playerNumber = 1;
        else
            playerNumber++;
    }

    /**
     * selects the first five cards in the stack
     * @return a arraylist of the five cards to display in the interface
     */
    public ArrayList<Card> selectFiveCards(){
        ArrayList<Card> fiveCards = new ArrayList<>();
        for(int i = 0; i<=4; i++){
            if(!cards.isEmpty()) {
                fiveCards.add(cards.get(0));
                cards.remove(0);
            }
        }
        return fiveCards;
    }

    /**
     * Sets the cards returned from the interface back in the stack
     * @param fourCards the cards which where not selected by the player
     */
    public void returnFourCards(ArrayList<Card> fourCards){
        cards.addAll(fourCards);
        shuffleCards();
    }

    public void shuffleCards(){
        Collections.shuffle(cards);
    }



    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

}
