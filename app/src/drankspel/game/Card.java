package drankspel.game;

public class Card {
    int number;
    int type;
    String rule;

    public Card(int number, int type, String rule) {
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
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
