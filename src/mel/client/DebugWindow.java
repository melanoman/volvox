package mel.client;

import javax.swing.JFrame;

public class DebugWindow extends JFrame
{
    public static DebugWindow singleton = null;
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public static void makeDebugWindow()
    {
        singleton = new DebugWindow();
        
    }
    
    public static void recieve(String s)
    {
        if(singleton == null) return;
        System.out.println(s);
    }
}
