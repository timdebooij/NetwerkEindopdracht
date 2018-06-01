package drankspel.game;

import java.io.Serializable;

public class Card implements Serializable{
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
}
