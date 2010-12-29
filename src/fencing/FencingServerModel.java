package fencing;

import mel.dice.DiceUtil;

public class FencingServerModel extends FencingBaseModel
{
    private Card[] whiteCard = new Card[5];
    private Card[] blackCard = new Card[5];
    private Card[] deck = new Card[25];
    
    public FencingServerModel()
    {
        whiteHP=5;
        blackHP=5;
        whitePos = 1;
        blackPos = STRIP_LENGTH;
        for(int i = 0; i < 5; i++)
        {
            blackCard[i] = Card.NULL_CARD;
            whiteCard[i] = Card.NULL_CARD;
            deck[i] = new Card(1);
            deck[5+i] = new Card(2);
            deck[10+i] = new Card(3);
            deck[15+i] = new Card(4);
            deck[20+i] = new Card(5);
        }
        for(int i = 0; i < 25; i++)
        {
            int index = DiceUtil.rollDie(25-i)-1;
            Card c = deck[i];
            deck[i] = deck[index];
            deck[index] = c;
        }
    }

    public String getBlackCardValues()
    {
        String values = "";
        for(Card c : blackCard)
        {
            values += c.getValue();
        }
System.out.println(values);
        return values;
    }
    
    public String getWhiteCardValues()
    {
        String values = "";
        for(Card c : whiteCard)
        {
            values += c.getValue();
        }
System.out.println(values);
        return values;
    }
}
