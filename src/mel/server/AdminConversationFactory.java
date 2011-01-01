/*
 * Copyright (c) 2009  Mel Nicholson.
 * All Rights Reserved.
 */

package mel.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author nicholson
 */
public class AdminConversationFactory implements ConversationFactory
{
    public ServerConversation makeConversation(BufferedReader in, String name)
    {
        return new AdminConversation(name);
    }

    public ServerConversation newSavedConversation(PrintWriter out, String name)
    {
        return new AdminConversation(name);
    }
    
    public ServerConversation newUnsavedConversation(String name) throws IOException
    {
        throw new IOException("Admin conversations must be audited");
    }

    public String requireAuth()
    {
        return "admin";
    }
}
