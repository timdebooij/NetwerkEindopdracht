package drankspel.game;


import drankspel.interfaces.ClientInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Game {

    private ArrayList<Card> cards;
    private ArrayList<ClientInterface> player;
    private int playerNumber = 1;

    public Game() {

        cards = new ArrayList<>();
        player = new ArrayList<>();
        //player.addAll(clients);


        Stack stack = new Stack(1);
        cards = stack.getCards();
        System.out.println(stack.toString());
        System.out.println("amount of cards: " + cards.size());
        //playGame();


    }

    public boolean gameStillGoing(){
        if(cards.isEmpty())
            return false;
        else
            return true;
    }

    public void removeCard(Card card){
        System.out.println("amount of cards before: " + cards.size());
        cards.remove(card);
        System.out.println("amount of cards after: " + cards.size());
    }

    /**
     * select next player to get cards
     */
    public void selectNextPlayer(){
        if(playerNumber == player.size()-1)
            playerNumber = 0;
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

    public ArrayList<ClientInterface> getPlayer() {
        return player;
    }

    public void setPlayer(ArrayList<ClientInterface> player) {
        this.player = player;
    }
}
