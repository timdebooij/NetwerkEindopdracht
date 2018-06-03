package drankspel.game;

import java.util.ArrayList;
import java.util.Collections;

public class Stack {

    private ArrayList<drankspel.game.Card> cards;
    private ArrayList<String> rules;
    private ArrayList<String> types;
    private ArrayList<String> images;

    /**
     * constructor that creates a stack filled with cards
     * @param stacks is the amount of cards in the game (must be between 1 and 3)
     */
    public Stack(int stacks) {
        cards = new ArrayList<>();
        rules = new ArrayList<>();
        types = new ArrayList<>();
        images = new ArrayList<>();
        fillRules();
        fillTypes();
        fillImages();
        switch (stacks){
            case 1: createCard(18);
                    break;
            case 2: createCard(36);
                    break;
            case 3: createCard(72);
                    break;
        }
        Collections.shuffle(cards);
    }

    /**
     * Fills the arraylist rules with the actaul game rules
     */
    public void fillRules(){
        rules.add("player takes four sips");
        rules.add("player can give four sips away");
        rules.add("player takes two sips");
        rules.add("player can give two sips away");
        rules.add("everybody drinks two sips");
        rules.add("everybody drinks four sips");
        rules.add("everybody except the player drinks four sips");
        rules.add("player has to down their drink");
        rules.add("player can make someone down their drink");

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

    public void fillImages(){
        images.add("res/2_of_clubs.png");
        images.add("res/3_of_clubs.png");
        images.add("res/4_of_clubs.png");
        images.add("res/5_of_clubs.png");
        images.add("res/6_of_clubs.png");
        images.add("res/7_of_clubs.png");
        images.add("res/8_of_clubs.png");
        images.add("res/9_of_clubs.png");
        images.add("res/10_of_clubs.png");

        images.add("res/2_of_diamonds.png");
        images.add("res/3_of_diamonds.png");
        images.add("res/4_of_diamonds.png");
        images.add("res/5_of_diamonds.png");
        images.add("res/6_of_diamonds.png");
        images.add("res/7_of_diamonds.png");
        images.add("res/8_of_diamonds.png");
        images.add("res/9_of_diamonds.png");
        images.add("res/10_of_diamonds.png");
    }


    int number = 2;
    int typeCard = 0;
    //int imageCard = 0;
    int imageType = 0;
    public int createCard(int amount){
        if(amount == 0){
            return 0;
        }
        else {
            String rule = rules.get(number - 2);
            String type = types.get(typeCard);
            String image = images.get((number - 2) + imageType);
            cards.add(new Card(number, type, rule, image));
            if(number == 10) {
                number = 2;
                if (typeCard == 3)
                    typeCard = 0;
                else
                    typeCard++;
                    imageType =+ 9;
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
