package drankspel.interfaces;

import com.sun.xml.internal.messaging.saaj.soap.JpegDataContentHandler;
import drankspel.game.Card;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ClientInterface {

    String playerID;
    String name;
    ArrayList<Card> cards;
    ArrayList<String> players;

    JPanel playersPanel;
    JPanel cardsPanel;

    public ClientInterface(String playerID, String name){
        this.name = name;
        this.playerID = playerID;

        cards = new ArrayList<>();
        players = new ArrayList<>();

        JFrame clientFrame = new JFrame("player name");
        clientFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        clientFrame.setSize(500,250);
        clientFrame.setLayout(new BorderLayout());

        JLabel nameLabel = new JLabel("player: " + name);
        nameLabel.setPreferredSize(new Dimension(500,50));
        nameLabel.setFont(new Font(nameLabel.getFont().toString(), Font.PLAIN, 25));
        nameLabel.setHorizontalAlignment(JLabel.CENTER);


        createCardsList();
        createPlayerList();
        clientFrame.add(cardsPanel, BorderLayout.CENTER);
        clientFrame.add(playersPanel, BorderLayout.WEST);
        clientFrame.add(nameLabel, BorderLayout.NORTH);
        clientFrame.setVisible(true);
    }

    public void createCardsList(){

        cardsPanel = new JPanel();
        cardsPanel.setLayout(new BorderLayout());
        JPanel cardsPanel1 = new JPanel();
        cardsPanel1.setLayout(new FlowLayout());
        JLabel card = new JLabel("klaver 3, ");
        card.setFont(new Font(card.getFont().toString(), Font.PLAIN, 20));
        JLabel card2 = new JLabel("harten 5, ");
        card2.setFont(new Font(card2.getFont().toString(), Font.PLAIN, 20));
        JLabel card3 = new JLabel("ruiten boer");
        card3.setFont(new Font(card3.getFont().toString(), Font.PLAIN, 20));
        cardsPanel1.add(card);
        cardsPanel1.add(card2);
        cardsPanel1.add(card3);

        JPanel rulePanel = new JPanel(new FlowLayout());
        JLabel rule = new JLabel("The rule will be displayed here");
        rule.setFont(new Font(rule.getFont().toString(), Font.PLAIN, 20));
        JButton confirm = new JButton("Confirm task");
        rulePanel.add(rule);
        rulePanel.add(confirm);

        cardsPanel.add(cardsPanel1, BorderLayout.CENTER);
        cardsPanel.add(rulePanel, BorderLayout.SOUTH);
    }

    public void createPlayerList(){
        playersPanel = new JPanel();
        String[] data = {"player 1", "player 2", "player 3", "player 4", "player 5"};
        JList<String> players = new JList<>();
        players.setListData(data);
        playersPanel.add(players);
    }

    public ArrayList<Card> chooseCard(){
        System.out.println(cards.get(0).getRule());
        cards.remove(0);
        return cards;
    }

    public String getPlayerID() {
        return playerID;
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

    public JPanel getCardsPanel() {
        return cardsPanel;
    }

    public void setCardsPanel(JPanel cardsPanel) {
        this.cardsPanel = cardsPanel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public ArrayList<String> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<String> players) {
        this.players = players;
    }

    public JPanel getPlayersPanel() {
        return playersPanel;
    }

    public void setPlayersPanel(JPanel playersPanel) {
        this.playersPanel = playersPanel;
    }
}
