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
    private int side;
    private int whiteHP;
    private int blackHP;
    private ArrayList<FencingClientModelListener> listeners = new ArrayList<FencingClientModelListener>();
    private Card[] card = new Card[5];
    private int whitePos;
    private int blackPos;
    private int mode;
    private int turn;
    public static final int STRIP_LENGTH = 23;
    public static final int NO_SIDE = 0;
    public static final int BLACK_SIDE = 0;
    public static final int WHITE_SIDE = 1;
    public static final int FREE_MODE = 0;
    public static final int ATTACK_MODE = 1;
    public static final int PAT_MODE = 2;
    public static final int ENDATTACK_MODE = 3;
    public static final int ENDPAT_MODE = 4;
    public static final int GAMEOVER_MODE = 5;
    
    public FencingClientModel()
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
    
    // TODO fire changes for setters
    public int getMode() { return mode; }
    public int getSide() { return side; }
    public int getTurn() { return turn; }
    public void setMode(int mode) { this.mode = mode; }
    public void setSide(int side) { this.side = side; }
    public void setTurn(int turn) { this.turn = turn; }
    
    //HandModel
    public Card getCard(int index) { return card[index]; }
    public Card[] getCards() { return card; }
    public void setCards(Card[] card) { this.card = card; }
    
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
