/*
 * Copyright (c) 2009  Mel Nicholson.
 * All Rights Reserved.
 */

package mel.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import fencing.FencingConversationFactory;
import mel.security.AccountManager;

/**
 *
 * @author nicholson
 */
public class ServerMain
{
    private static ServerConversationManager scm = new ServerConversationManager();
    public static final int port = 2435;

    public static void main(String args[]) throws IOException
    {
        //TODO read port and such from options

        String accountDir = AccountManager.defaultDir;
        
        registerConversationTypes();
        
        // TODO unhardcode password directory
        new AccountManager(accountDir);
        ServerSocket ss = new ServerSocket(port);
        while(true)
        {
            Socket s = ss.accept();
            scm.add(s);
        }
    }

    public static void registerConversationTypes()
    {
        scm.registerConversationType("Admin", new AdminConversationFactory());
        scm.registerConversationType("Chat", new ChatConversationFactory());
        scm.registerConversationType("Fencing", new FencingConversationFactory());
    }

}
