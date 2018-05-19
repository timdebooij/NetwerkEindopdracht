package drankspel.game;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Game {

    private ArrayList<Card> cards;
    private ArrayList<Integer> player;
    private int playerNumber = 1;

    public Game() {

        cards = new ArrayList<>();
        player = new ArrayList<>();
        player.add(1);
        player.add(2);
        player.add(3);

        Stack stack = new Stack(1);
        cards = stack.getCards();
        System.out.println(stack.toString());
        System.out.println("amount of cards: " + cards.size());
        playGame();

    }

    public void playGame(){
        Scanner reader = new Scanner(System.in);
        while(!cards.isEmpty()){
            //sent cards to selected player
            ArrayList<Card> sentCards = selectFiveCards();

            System.out.println(sentCards);

            //read data
            System.out.println("Choose card : ");
            int n = reader.nextInt();

            //show rule and remove used card
            System.out.println(sentCards.get(n).getRule());
            sentCards.remove(n);
            //get four back
            returnFourCards(sentCards);
            //go to next player
            selectNextPlayer();

        }
        reader.close();
    }

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
