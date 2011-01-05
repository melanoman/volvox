package fencing;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import mel.client.ClientSession;
import mel.client.ClientWindow;

public class FencingClientWindow extends JFrame implements ClientWindow
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private final FencingClientModel model = new FencingClientModel();
    private final String convName;
    
    private FencingClientWindow(String name)
    {
        super(name);
        convName = name;
    }
    
    public static FencingClientWindow makeWindow(String conversationName)
    {
        FencingClientWindow win = new FencingClientWindow("Fencing - "+conversationName);
        win.setSize(800, 400);
        win.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        FencingClientModel cgm = win.model;
        StripDisplay sd = new StripDisplay(cgm);
        HandDisplay hd = new HandDisplay(cgm);

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, sd, hd);
        sd.setMinimumSize(new Dimension(100,110));
        hd.setMinimumSize(new Dimension(100,200));
        win.setMinimumSize(new Dimension(500,310));
        
        win.add(split);
        win.setVisible(true);
        return win;
    }
    
    /**
     * local opcodes:
     * a = whp
     * b = bhp
     * h = hand
     * m = mode "state" ((f)ree/(a)ttack/(p)at/endattac(k)/endpa(t))
     * s = side "who am I" (white/black)
     * t = turn (white/black)
     * x = bp
     * y = wp
     */
    @Override
    public void handleMessage(String userName, char opcode, String message)
    {
        switch(opcode)
        {
            case 'a': model.setWhiteHP(new Integer(message).intValue());
            case 'b': model.setBlackHP(new Integer(message).intValue());
            case 'h': model.setCards(parseCards(message));
            case 'm': model.setMode(parseMode(message));
            case 's': model.setSide(parseSide(message));
            case 't': model.setTurn(parseSide(message));
            case 'x': model.setBlackPos(new Integer(message).intValue());
            case 'y': model.setWhitePos(new Integer(message).intValue());
        }
    }

    private Card[] parseCards(String values)
    {
        Card[] card = new Card[5];
        card[0] = new Card(new Integer(values.charAt(0)).intValue());
        card[1] = new Card(new Integer(values.charAt(1)).intValue());
        card[2] = new Card(new Integer(values.charAt(2)).intValue());
        card[3] = new Card(new Integer(values.charAt(3)).intValue());
        card[4] = new Card(new Integer(values.charAt(4)).intValue());
        return card;
    }
    
    @Override
    public void init()
    {
        ClientSession.send(convName, 'R', "");
    }
    
    private int parseMode(String mode)
    {
        char m = mode.charAt(0);
        switch(m)
        {
            case 'a': return FencingClientModel.ATTACK_MODE;
            case 'f': return FencingClientModel.FREE_MODE;
            case 'p': return FencingClientModel.PAT_MODE;
            case 'k': return FencingClientModel.ENDATTACK_MODE;
            case 't': return FencingClientModel.ENDPAT_MODE;
            case 'e': return FencingClientModel.GAMEOVER_MODE;
            default : showError("Error - Illegal value for FencingClientWindow <mode>.");
        }
        return FencingClientModel.GAMEOVER_MODE;
    }
    
    private int parseSide(String side)
    {
        char s = side.charAt(0);
        switch(s)
        {
            case 'b': return FencingClientModel.BLACK_SIDE;
            case 'w': return FencingClientModel.WHITE_SIDE;
            case 'n': return FencingClientModel.NO_SIDE;
            default : showError("Error - Illegal value for FencingClientWindow <side>.");
        }
        return FencingClientModel.NO_SIDE;
    }
    
    public final void showError(String message)
    {
        JOptionPane.showMessageDialog(this, message, "Error - "+super.getName(), JOptionPane.ERROR_MESSAGE);
    }
}
