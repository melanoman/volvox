/**
 * Copyright 2010 Richard Nicholson
 */
package fencing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;

/**
 * @author Charidan
 *
 */
public class HandDisplay extends JPanel implements FencingClientModelListener
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    public static final int NO_HIGHLIGHT = 0;
    public static final int SELECTED_HIGHLIGHT = 1;
    private FencingClientModel cgm;
    private int slotWidth;
    private int cardWidth;
    private int cardTop;
    private int cardHeight;
    private HandController hc = new HandController();
    private int[] highlight = new int[5];
    
    public HandDisplay(FencingClientModel cgm)
    {
        this.cgm = cgm;
        for(int i = 0; i < 5; i++) highlight[i] = NO_HIGHLIGHT;
        addMouseListener(hc);
        cgm.addClientGameModelListener(this);
    }
    
    public FencingClientModel getClientGameModel() { return cgm; }
    public void setHandModel(FencingClientModel cgm) { this.cgm = cgm; }
    
    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        slotWidth = getSize().width/18;
        cardWidth = slotWidth*2;
        int panelHeight = getSize().height;
        if(panelHeight < cardWidth*7/3)
        {
            cardTop = panelHeight/7;
            cardHeight = panelHeight*5/7;
        }
        else
        {
            cardHeight = cardWidth*5/3;
            cardTop = cardWidth/3;
        }
        int buttonHeight = cardTop*3/5;
        
        g.setColor(Color.BLACK);
        for(int i = 0; i < 5; i++)
        {
            int cardLeft = (3*i+1)*slotWidth;
            Card c = cgm.getCard(i);
            if(c.isNull()) continue; //do not draw NULL_CARDs
            //draw real cards
            if(highlight[i] == SELECTED_HIGHLIGHT) g.setColor(Color.BLUE);
            g.drawRect(cardLeft, cardTop, cardWidth, cardHeight);
            g.drawString(""+c.getValue(), cardLeft+cardWidth*3/7, cardTop+cardHeight*2/5);
            g.setColor(Color.BLACK);
        }
        //g.drawRect(x, y, width, height)
    }
    
    class HandController implements MouseListener
    {
        private int pressedCard = -1;
        
        public void clickCard(int index)
        {
            if(highlight[index] == NO_HIGHLIGHT) highlight[index] = SELECTED_HIGHLIGHT;
            else if(highlight[index] == SELECTED_HIGHLIGHT) highlight[index] = NO_HIGHLIGHT;
            if(index == 0) cgm.loseWhiteHP();
            if(index == 2) cgm.loseBlackHP();
            if(index == 3) cgm.setBlackHP(cgm.getBlackHP()+1);
            if(index == 1) cgm.setWhiteHP(cgm.getWhiteHP()+1);
            cgm.fireStripChange();
            repaint();
        }

        @Override
        public void mouseClicked(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}

        @Override
        public void mousePressed(MouseEvent e)
        {
            int x = e.getX();
            int y = e.getY();
            for(int i = 0; i < 5; i++)
            {
                int cardLeft = (3*i+1)*slotWidth;
                if(x > cardLeft && x < cardLeft+cardWidth && y > cardTop && y < cardTop+cardWidth*5/3)
                {
                    pressedCard=i;
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent e)
        {
            int cardLeft = (3*pressedCard+1)*slotWidth;
            if(pressedCard < 0) return;
            int x = e.getX();
            int y = e.getY();
            if(x > cardLeft && x < cardLeft+cardWidth && y > cardTop && y < cardTop+cardWidth*5/3)
            {
                clickCard(pressedCard);
            }
            pressedCard = -1;
        }
        
    }

    @Override
    public void handModelChanged()
    {
        repaint();
    }

    @Override
    public void stripModelChanged() {}
}
