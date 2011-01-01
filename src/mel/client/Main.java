/**
 * copyright (c)2011 Mel Nicholson
 */
package mel.client;

import fencing.FencingClientWindowFactory;

public class Main
{
    static boolean debug = true;
	
    public static void main(String arg[])
    {
        createClient();
        registerConversationTypes();
    }
    
    private static void registerConversationTypes()
    {
        // TODO register LobbyWindowFactory
        ClientWindowManager.register("Chat", new ChatWindowFactory());
        ClientWindowManager.register("Fencing", new FencingClientWindowFactory());
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
