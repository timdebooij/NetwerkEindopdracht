package drankspel.game;

import java.io.Serializable;
import java.util.Comparator;

public class Card implements Serializable, Comparable<Card>{
    int number;
    String type;
    String rule;

    public Card(int number, String type, String rule) {
        this.number = number;
        this.type = type;
        this.rule = rule;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    @Override
    public String toString() {
        return "Card{" +
                "number=" + number +
                ", type=" + type +
                ", rule='" + rule + '\'' +
                '}';
    }

    @Override
    public int compareTo(Card o) {
        int compareNumber = o.getNumber();
        return this.getNumber()-compareNumber;
    }

    public static Comparator<Card> cardComparator = new Comparator<Card>() {
        @Override
        public int compare(Card o1, Card o2) {
            String type1 = o1.getType().toUpperCase();
            String type2 = o2.getType().toUpperCase();
            return type1.compareTo(type2);
        }
    };

    }
