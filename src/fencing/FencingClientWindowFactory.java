package fencing;

import mel.client.ClientWindow;
import mel.client.ClientWindowFactory;
import mel.client.ClientWindowManager;

public class FencingClientWindowFactory implements ClientWindowFactory
{
    static 
    {
        ClientWindowManager.register("Fencing", new FencingClientWindowFactory());
    }

    @Override
    public ClientWindow makeWindow(String conversationName)
    {
        return FencingClientWindow.makeWindow(conversationName);
    }

}
