package mel.client;

import mel.client.ClientWindow;
import mel.client.ClientWindowFactory;

public class LobbyWindowFactory implements ClientWindowFactory
{
    @Override
    public ClientWindow makeWindow(String conversationName)
    {
        return new LobbyWindow(conversationName);
    }
}
