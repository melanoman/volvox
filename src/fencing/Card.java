/**
 * Copyright 2010 Richard Nicholson
 */
package fencing;

/**
 * @author Charidan
 *
 */
public class Card
{
    public static final Card NULL_CARD = new Card(0);
    private int value;
    
    public Card(int value)
    {
        this.value = value;
    }
    
    public int getValue() { return value; }
    public boolean isNull() { return value==0; }
}
