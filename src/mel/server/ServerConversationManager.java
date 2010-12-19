/*
 * Copyright (c) 2009  Mel Nicholson.
 * All Rights Reserved.
 */

package mel.server;

import mel.common.Conversation;
import mel.common.User;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author nicholson
 */
public class ServerConversationManager
{
    // TODO unhardcode game save directory
    private Map<String, ConversationFactory> name2factory = new HashMap<String, ConversationFactory>();
    private Map<String, ServerConversation> name2conversation = new HashMap<String, ServerConversation>();
    private Set<ServerSession> sessions = new HashSet<ServerSession>();

    private final File dir = new File("C:/gameServer/games");

    public ServerConversationManager()
    {
        name2conversation.put("lobby", new LobbyConversation(this));
    }

    public void registerConversationType(String name, ConversationFactory factory)
    {
        name2factory.put(name, factory);
    }

    public ConversationFactory getConversationFactory(String type)
    {
        return name2factory.get(type);
    }

    synchronized public void createSavableConversation(String name, String type)
            throws IOException
    {
        File f = new File(dir, name+".gsf");
        if(f.exists()) throw new IOException("Conversation already exists");
        ConversationFactory cf = name2factory.get(type);
        if(cf == null) throw new IOException("Unknown Conversation type");
        PrintWriter out = new PrintWriter(new FileWriter(f));
        out.println(type);
        ServerConversation c = cf.newSavedConversation(out, name);
        name2conversation.put(name, c);
        out.close();
    }

    /**
     * @return the named conversation, or null if it doesn't exist
     */
    synchronized public ServerConversation getConversation(String name)
    {
        return name2conversation.get(name);
    }

    /**
     * Get a conversation from disk unless it has already been loaded,
     * in which case just return it.
     */
    synchronized protected ServerConversation loadConversation(String name)
            throws IOException
    {
        ServerConversation c = getConversation(name);
        if(c != null) return c;
        File f = new File(dir, name+".gsf");
        if(!f.exists()) throw new FileNotFoundException();
        BufferedReader in = new BufferedReader(new FileReader(f));
        String controllerType = in.readLine();

        ConversationFactory factory = name2factory.get(controllerType);
        c = factory.makeConversation(in, name);
        name2conversation.put(name, c);
        return c;
    }



    synchronized public void add(Socket s) throws IOException
    {
        sessions.add(new ServerSession(s, this));
    }

    /**
     * user-driven joins and leaves happen through a lobby command.  This is
     * a special join that is used by the server to add a user automatically,
     * such as putting the user into the lobby on login.
     */
    public void joinConversation(String conName, User u)
    {
        try
        {
            Conversation c = getConversation(conName);
            c.join(u);
        }
        catch(NullPointerException ex)
        {
            // should never happen
            ex.printStackTrace();
        }
    }

    //TODO write leave conversation
}
