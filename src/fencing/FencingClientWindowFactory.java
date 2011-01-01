package fencing;

import mel.client.ClientWindow;
import mel.client.ClientWindowFactory;

public class FencingClientWindowFactory implements ClientWindowFactory
{
    @Override
    public ClientWindow makeWindow(String conversationName)
    {
        return FencingClientWindow.makeWindow(conversationName);
    }

}
