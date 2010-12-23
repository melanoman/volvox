/*
 * Copyright (c) 2009  Mel Nicholson.
 * All Rights Reserved.
 */

package mel.server;

import java.io.BufferedReader;
import java.io.PrintWriter;

/**
 *
 * @author nicholson
 */
public class ChatConversationFactory implements ConversationFactory
{
    public ServerConversation makeConversation(BufferedReader in, String name)
    {
        return new BasicServerConversation(name);
    }

    public ServerConversation newSavedConversation(PrintWriter out, String name)
    {
        // note, currently chat data is not saved
        return new BasicServerConversation(name);
    }

    public String requireAuth()
    {
        return "create";
    }
}
