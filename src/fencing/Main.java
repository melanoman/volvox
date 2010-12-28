package fencing;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JSplitPane;


public class Main
{
    /**
     * @param args
     */
    public static void main(String[] args)
    {
        JFrame win = new JFrame("Fencing");
        win.setSize(800, 400);
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        FencingClientModel cgm = new FencingClientModel(true);
        StripDisplay sd = new StripDisplay(cgm);
        HandDisplay hd = new HandDisplay(cgm);
        
        //test
        Card card1 = new Card(1);
        Card card2 = new Card(2);
        Card card3 = new Card(3);
        Card card4 = new Card(4);
        Card card5 = new Card(5);
        cgm.addCard(card1);
        cgm.addCard(card2);
        cgm.addCard(card3);
        cgm.addCard(card4);
        cgm.addCard(card5);
        //end test

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, sd, hd);
        sd.setMinimumSize(new Dimension(100,110));
        hd.setMinimumSize(new Dimension(100,200));
        win.setMinimumSize(new Dimension(500,310));
        
        win.add(split);
        win.setVisible(true);
    }

}
