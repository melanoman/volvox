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
public interface ConversationFactory
{
    public ServerConversation makeConversation(BufferedReader in, String name);
    public ServerConversation newSavedConversation(PrintWriter out, String name);
    public String requireAuth();
}
