/**
 * Copyright 2010 Richard Nicholson
 */
package fencing;

import java.util.ArrayList;

/**
 * @author Charidan
 *
 */
public class FencingClientModel extends FencingBaseModel
{
    private ArrayList<FencingClientModelListener> listeners = new ArrayList<FencingClientModelListener>();
    private Card[] card = new Card[5];
    
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
    
    //Strip Model
    @Override
    public void setWhitePos(int whitePosition)
    {
        whitePos = whitePosition;
        fireStripChange();
    }
    
    @Override
    public void setBlackPos(int blackPosition)
    {
        blackPos = blackPosition;
        fireStripChange();
    }

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
