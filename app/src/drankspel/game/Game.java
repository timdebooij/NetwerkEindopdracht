package drankspel.game;


import java.util.ArrayList;
import java.util.Collections;

public class Game {

    private ArrayList<Card> cards;

    public Game() {

        cards = new ArrayList<>();
        Stack stack = new Stack(2);
        cards = stack.getCards();
        System.out.println(stack.toString());
        System.out.println("amount of cards: " + cards.size());

        Stack doubleStack = new Stack(3);
        cards = doubleStack.getCards();
        System.out.println(doubleStack.toString());
        System.out.println("amount of cards: " + cards.size());
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
