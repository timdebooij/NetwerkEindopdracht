package drankspel.game;

import java.util.ArrayList;
import java.util.Collections;

public class Stack {

    private ArrayList<drankspel.game.Card> cards;
    private ArrayList<String> rules;
    private ArrayList<String> types;

    /**
     * constructor that creates a stack filled with cards
     * @param stacks is the amount of cards in the game (must be between 1 and 3)
     */
    public Stack(int stacks) {
        cards = new ArrayList<>();
        rules = new ArrayList<>();
        types = new ArrayList<>();
        fillRules();
        fillTypes();
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

    /**
     * Fills the arraylist rules with the actaul game rules
     */
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

    public void fillTypes(){
        types.add("Klaver");
        types.add("Ruiten");
        types.add("Harten");
        types.add("Schoppen");
    }

    public void createHalfStack(){
        //createCards(1);
        createCard(26);
    }

    public void createStack(){
        createCard(52);
    }

    public void createDoubleStack() {
        createCard(104);
    }

    /**
     * creates cards for in the deck
     * @param size is the size of the deck (between 0 and 3)
     */
    public void createCards(int size){
        for(int i = 2; i<=14; i++){
            String rule = rules.get(i-2);
            for(int t = 0; t<=size; t++){
                String type = types.get(t);
                cards.add(new drankspel.game.Card(i, type, rule));
            }
        }
    }

    int number = 2;
    int typeCard = 0;
    public int createCard(int amount){
        if(amount == 0){
            return 0;
        }
        else {
            String rule = rules.get(number - 2);
            String type = types.get(typeCard);
            cards.add(new Card(number, type, rule));
            if(number == 14) {
                number = 2;
                if (typeCard == 3)
                    typeCard = 0;
                else
                    typeCard++;
            }
            else number++;

            return createCard(amount - 1);
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
