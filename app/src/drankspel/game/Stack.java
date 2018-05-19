package drankspel.game;

import java.util.ArrayList;
import java.util.Collections;

public class Stack {

    private ArrayList<drankspel.game.Card> cards;
    private ArrayList<String> rules;

    public Stack(int stacks) {
        cards = new ArrayList<>();
        rules = new ArrayList<>();
        fillRules();
        switch (stacks){
            case 1: createHalfStack();
                    break;
            case 2: createStack();
                    break;
            case 3: createDoubleStack();
                    break;
        }
        Collections.shuffle(cards);
    }

    public void fillRules(){
        rules.add("take four sips");
        rules.add("give four sips away");
        rules.add("take 2 sips");
        rules.add("give four sips away");
        rules.add("everybody drinks 4 sips");
        rules.add("Choose somebody who will drink when you drink for the rest of the game");
        rules.add("everybody except you drinks 4 sips");
        rules.add("down your drink");
        rules.add("make someone down their drink");
        rules.add("new rule");
        rules.add("new rule");
        rules.add("new rule");
        rules.add("new rule");
        rules.add("change direction");
    }

    public void createHalfStack(){
        createCards(1);
    }

    public void createStack(){
        createCards(3);
    }

    public void createDoubleStack() {
        createCards(3);
        createCards(3);
    }

    /**
     * creates cards for in the deck
     * @param size is the size of the deck (between 0 and 3)
     */
    public void createCards(int size){
        for(int i = 2; i<=14; i++){
            String rule = rules.get(i-2);
            for(int t = 0; t<=size; t++){
                cards.add(new drankspel.game.Card(i, t, rule));
            }
        }
    }

    public ArrayList<drankspel.game.Card> getCards() {
        return cards;
    }

    @Override
    public String toString() {
        return "Stack{" +
                "cards=" + cards +
                '}';
    }
}
