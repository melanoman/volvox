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
public class AdminConversationFactory implements ConversationFactory
{
    public ServerConversation makeConversation(BufferedReader in, String name)
    {
        return new AdminConversation(name);
    }

    // TODO: Figure out how create conversation will work wrt saving.
    // should things save by default?  Specify whether to how?
    public ServerConversation newSavedConversation(PrintWriter out, String name)
    {
        return new AdminConversation(name);
    }

    public String requireAuth()
    {
        return "admin";
    }
}
