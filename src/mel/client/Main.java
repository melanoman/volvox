/**
 * copyright (c)2011 Mel Nicholson
 */
package mel.client;

public class Main
{
    static boolean debug = true;
	
    public static void main(String arg[])
    {
        createClient();
    }

    private static void createClient()
    {
        LoginDialog ld = new LoginDialog();
        ld.setVisible(true);
    }
	
    static void createDebugWindow()
    {
        DebugWindow.makeDebugWindow();
    }
}
