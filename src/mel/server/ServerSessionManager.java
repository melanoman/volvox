/*
 * Copyright (c) 2009  Mel Nicholson.
 * All Rights Reserved.
 */
package mel.server;

import mel.common.User;
import java.util.HashMap;
import java.util.Map;

/**
 * This is a singleton that maps users to session.  All methods must be
 * synchronized for concurrent access.
 *
 * @author nicholson
 */
public class ServerSessionManager
{

    private static final ServerSessionManager singleton = new ServerSessionManager();
    private static final Map<User, ServerSession> user2session = new HashMap<User, ServerSession>();

    /**
     * register a user with a session.  if the user is already registered,
     * be sure to kill the old session.
     * 
     * @param s
     * @param u
     */
    synchronized public void resgisterSession(ServerSession s, User u)
    {
        ServerSession old = user2session.put(u, s);
        if(old != null) old.tryClose("Session closed for duplicate login");
    }

    /**
     * @param u
     * @return the session for the named user, or null if not found.
     */
    synchronized public ServerSession getSession(User u)
    {
        return user2session.get(u);
    }

    public static final ServerSessionManager getServerSessionManager()
    {
        return singleton;
    }
}
