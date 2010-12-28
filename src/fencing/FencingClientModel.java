/**
 * Copyright 2010 Richard Nicholson
 */
package fencing;

import java.util.ArrayList;

/**
 * @author Charidan
 *
 */
public class FencingClientModel
{
    private boolean side;
    private int whiteHP;
    private int blackHP;
    private ArrayList<FencingClientModelListener> listeners = new ArrayList<FencingClientModelListener>();
    private Card[] card = new Card[5];
    private int whitePos;
    private int blackPos;
    public static final int STRIP_LENGTH = 23;
    public static final boolean BLACK_SIDE = true;
    public static final boolean WHITE_SIDE = false;
    
    public FencingClientModel(boolean side)
    {
        whiteHP=5;
        blackHP=5;
        whitePos = 1;
        blackPos = STRIP_LENGTH;
        for(int i = 0; i < 5; i++)
        {
            card[i] = Card.NULL_CARD;
        }
    }
    
    public FencingClientModel(boolean side, int whitePosition, int blackPosition, int whiteHitPoints, int blackHitPoints)
    {
        whitePos = whitePosition;
        blackPos = blackPosition;
        whiteHP = whiteHitPoints;
        blackHP = blackHitPoints;
        for(int i = 0; i < 5; i++)
        {
            card[i] = Card.NULL_CARD;
        }
    }
    
    //Server Connectivity Code
    /**
     * Receives a message from the server, using the following encoding to store information.
     * 
     * bp+wp+bhp+whp+cards
     * side = which side player is; black,white
     * bp = Black Position; two-digit
     * wp = White Position; two-digit
     * bhp = Black Hit Points; single-digit
     * whp = White Hit Points; single-digit
     * cards = Cards in Hand; five digit (1 per card, 0=NULL_CARD)
     * 
     * Commands:
     * r = reset
     * r:<bp>:<wp>:<bhp>:<whp>:<side>:<cards>
     * s = set
     * s:<key>:<value>
     */
    public void recieve(String message)
    {
        String[] str = message.split(":");
        if(str[0].equals("s")) set(str);
        if(str[0].equals("r")) reset(str);
    }
    
    public void set(String[] str)
    {
        if(str[1].equals("bp")) blackPos = new Integer(str[2]).intValue();
        if(str[1].equals("wp")) whitePos = new Integer(str[2]).intValue();
        if(str[1].equals("bhp")) blackHP = new Integer(str[2]).intValue();
        if(str[1].equals("whp")) whiteHP = new Integer(str[2]).intValue();
        if(str[1].equals("side")) if(str[2].equals("white")) side=WHITE_SIDE; else if(str[2].equals("black")) side=BLACK_SIDE;
        if(str[1].equals("cards"))
        {
            char[] c = str[2].toCharArray();
            for(int i = 0; i < 5; i++)
            {
                removeCard(i);
                addCard(new Card(new Integer(c[i]).intValue()));
            }
        }
    }

    public boolean getSide()
    {
        return side;
    }

    public void reset(String[] str)
    {
        blackPos = new Integer(str[1]).intValue();
        whitePos = new Integer(str[2]).intValue();
        blackHP = new Integer(str[3]).intValue();
        whiteHP = new Integer(str[4]).intValue();
        if(str[5].equals("white")) side=WHITE_SIDE; else if(str[5].equals("black")) side=BLACK_SIDE;
        char[] c = str[6].toCharArray();
        for(int i = 0; i < 5; i++)
        {
            removeCard(i);
            addCard(new Card(new Integer(c[i]).intValue()));
        }
    }
    
    //HandModel
    public Card getCard(int index) { return card[index]; }
    public Card[] getCards() { return card; }
    
    public void addCard(Card newCard) throws Error
    {
        for(int i = 0; i < 5; i++)
        {
            if(card[i].isNull())
            {
                card[i] = newCard;
                fireHandChange();
                return;
            }
        }
        throw new Error("Cannot add card - Hand full.");
    }
    
    public Card removeCard(int index)
    {
        Card c = card[index];
        card[index] = Card.NULL_CARD;
        fireHandChange();
        return c;
    }
    
    public void printCards()
    {
        for(Card c : card)
        {
            System.out.println(""+c.getValue());
        }
    }
    
    //StripModel
    public int getWhitePos() { return whitePos; }
    public int getBlackPos() { return blackPos; }
    
    public void setWhitePos(int whitePosition)
    {
        whitePos = whitePosition;
        fireStripChange();
    }
    
    public void setBlackPos(int blackPosition)
    {
        blackPos = blackPosition;
        fireStripChange();
    }
    
    //HitPointModel
    public int getWhiteHP() { return whiteHP; }
    public int getBlackHP() { return blackHP; }
    public void setWhiteHP(int newHP) { whiteHP=newHP; }
    public void setBlackHP(int newHP) { blackHP=newHP; }
    public void loseWhiteHP() { whiteHP--; }
    public void loseBlackHP() { blackHP--; }
    
    public void addClientGameModelListener(FencingClientModelListener cgml)
    {
        listeners.add(cgml);
    }
    
    public void fireHandChange()
    {
        for(FencingClientModelListener gcml : listeners)
        {
            gcml.handModelChanged();
        }
    }
    
    public void fireStripChange()
    {
        for(FencingClientModelListener gcml : listeners)
        {
            gcml.stripModelChanged();
        }
    }
}
