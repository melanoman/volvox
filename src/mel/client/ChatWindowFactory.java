/**
 * Copyright (c) 2011, Mel Nicholson
 */
package mel.client;

/**
 * @author nicholson
 * 
 */
public class ChatWindowFactory implements ClientWindowFactory
{
    static
    {
        ClientWindowManager.register("Chat", new ChatWindowFactory());
    }

    @Override
    public ClientWindow makeWindow(String conversationName)
    {
        return new ChatWindow(conversationName);
    }
}
