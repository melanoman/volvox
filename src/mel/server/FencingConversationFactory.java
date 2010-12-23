package mel.server;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class FencingConversationFactory implements ConversationFactory
{

    @Override
    public ServerConversation makeConversation(BufferedReader in, String name)
    {
        return new FencingConversation(name);
    }

    @Override
    public ServerConversation newSavedConversation(PrintWriter out, String name)
    {
        // TODO find a way to load conversations
        return new FencingConversation(name);
    }

    @Override
    public String requireAuth()
    {
        return "create";
    }

}
