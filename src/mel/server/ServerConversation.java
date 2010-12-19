/*
 * Copyright (c) 2009  Mel Nicholson.
 * All Rights Reserved.
 */

package mel.server;

import mel.common.Conversation;
import mel.common.Seat;
import mel.common.User;

/**
 *
 * @author nicholson
 */
public interface ServerConversation extends Conversation
{
    /**
     * @return "create" if no special permission is required, or the feature
     * name if special permission is required to create this conversation
     */
    public String requireAuth();
    public void sendError(User target, String message);
    public void send(User sender, Seat target, char opcode, String message);
    public void send(User sender, User target, char opcode, String message);
    public void sendAll(User sender, char opcode, String message);
}
