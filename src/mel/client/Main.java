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
        ClientWindowManager.register("Chat", new ChatWindowFactory());
        // TODO register lobby
        //ClientWindowManager.register("Lobby", new LobbyWindowFactory());
        // TODO register fencing
        //ClientWindowManager.register("Fencing", new FencingWindowFactory());
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
