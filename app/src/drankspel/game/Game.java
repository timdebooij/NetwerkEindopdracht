package drankspel.game;

import java.util.*;

public class Game {

    private ArrayList<Card> cards;
    private int playerNumber = 1;

    public Game(int amount) {

        cards = new ArrayList<>();

        Stack stack = new Stack(amount);
        cards = stack.getCards();
        System.out.println(stack.toString());
        System.out.println("amount of cards: " + cards.size());
        System.out.println("cardnumbers before sorting by comparable: ");
        for (Card c : cards){
            //System.out.println("");
            System.out.print(c.getNumber() + ", ");
        }
        System.out.println("");

        System.out.println("cardnumbers after sorting by comparable: ");
        Collections.sort(cards);
        for (Card c : cards){
            //System.out.println("");
            System.out.print(c.getNumber() + ", ");
        }
        System.out.println("");

        System.out.println("cardtypes before sorting by comparator: ");
        for(Card c : cards){
            //System.out.println("");
            System.out.print(c.getType() + ", ");
        }
        System.out.println("");

        System.out.println("cartypes after sorting by comparator: ");
        Collections.sort(cards, Card.cardComparator);
        for(Card c : cards){
            //System.out.println("");
            System.out.print(c.getType() + ", ");
        }
        System.out.println("");
        shuffleCards();
        System.out.println(cards.toString());
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

    public static void main(String[] args) {
        Game game = new Game(1);
    }

}
