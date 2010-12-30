/**
 * copyright (c)2011 Mel Nicholson
 */
package mel.client;

public class Main
{
    static boolean debug = true;
	
    public static void main(String arg[])
    {
        registerWindowFactories();
	createClient();
    }
	
    private static void registerWindowFactories()
    {
        // TODO register chat
        // TODO register lobby
        // TODO register fencing
    }

    private static void createClient()
    {
        // TODO connect to server (HINT: showDialog)
        LoginDialog ld = new LoginDialog();
        ld.setVisible(true);
    }
	
    static void createDebugWindow()
    {
        DebugWindow.makeDebugWindow();
    }
}
