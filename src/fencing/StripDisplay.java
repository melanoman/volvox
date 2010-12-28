/**
 * 
 */
package fencing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * @author Charidan
 *
 */
public class StripDisplay extends JPanel implements FencingClientModelListener
{
    private FencingClientModel cgm;
    
    public StripDisplay(FencingClientModel cgm)
    {
        this.cgm = cgm;
        cgm.addClientGameModelListener(this);
    }
    
    public FencingClientModel getClientGameModel() { return cgm; }
    public void setClientGameModel(FencingClientModel cgm) { this.cgm = cgm; }
    
    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        Dimension size = this.getSize();
        int squareWidth = size.width/25;
        int stripTop;
        int stripHeight;
        //set the size of strip squares
        if(size.height < squareWidth*16/7)
        {
            stripTop = size.height/7;
            stripHeight = size.height*5/7;
        }
        else
        {
            stripTop = size.height/2-squareWidth;
            stripHeight = squareWidth*2;
        }
        g.setColor(Color.BLACK);
        for(int i = 1; i <= FencingClientModel.STRIP_LENGTH; i++)
        {
            g.drawRect(squareWidth*i, stripTop, squareWidth, stripHeight);
            if(i==12) g.setColor(Color.RED);
            g.drawString(""+i, squareWidth*i+squareWidth/3, stripTop+stripHeight*5/6);
            g.setColor(Color.BLACK);
        }
        //draw the dudes
        //TODO replace swordsman squares with images
        g.setColor(Color.WHITE);
        g.fillRect(cgm.getWhitePos()*squareWidth+squareWidth/6, stripTop+squareWidth/3, squareWidth*4/6, squareWidth*4/6);
        g.setColor(Color.BLACK);
        g.drawRect(cgm.getWhitePos()*squareWidth+squareWidth/6, stripTop+squareWidth/3, squareWidth*4/6, squareWidth*4/6);
        g.fillRect(cgm.getBlackPos()*squareWidth+squareWidth/6, stripTop+squareWidth/3, squareWidth*4/6, squareWidth*4/6);
        //draw the HP tokens
        int stripBottom = stripTop+stripHeight;
        int circleHeight = stripHeight*2/15;
        for(int i = cgm.getWhiteHP(); i > 0; i--)
        {
            g.setColor(Color.WHITE);
            g.fillArc(squareWidth/2-circleHeight/2, stripBottom-circleHeight*i-circleHeight*i/2, circleHeight, circleHeight, 0, 360);
            g.setColor(Color.BLACK);
            g.drawArc(squareWidth/2-circleHeight/2, stripBottom-circleHeight*i-circleHeight*i/2, circleHeight, circleHeight, 0, 360);
        }
        for(int i = cgm.getBlackHP(); i > 0; i--)
        {
            g.setColor(Color.BLACK);
            g.fillArc(squareWidth*24+squareWidth/2-circleHeight/2, stripBottom-circleHeight*i-circleHeight*i/2, circleHeight, circleHeight, 0, 360);
        }
    }
    
    @Override
    public void handModelChanged() {}

    @Override
    public void stripModelChanged()
    {
       repaint();
    }
}
