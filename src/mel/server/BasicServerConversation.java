/*
 * Copyright (c) 2009  Mel Nicholson.
 * All Rights Reserved.
 */

package mel.server;

import mel.common.Seat;
import mel.common.User;
import mel.common.Command;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import mel.common.MessageDispatch;

/**
 * This Conversation implementation is a no-frills implementation that provides
 * chat service and forwards to an optional game controller.  It always accepts
 * seat requests with no moderation.  At some point we'll need an enhancement
 * that will allow someone to return to their old seat or make private games.
 *
 * Join to get public notices, take a seat to fill a role in a game
 *
 * CLIENT-SIDE OPCODES
 *      C:message = Chat
 *      E = Error
 *      J:conType = Joined
 *      S:seat = Seated
 *      L = Left (exited)
 * @author nicholson
 */
public class BasicServerConversation implements ServerConversation
{
    private String name;
    private Collection<User> users = new HashSet<User>();
    private Collection<Seat> seats = new ArrayList<Seat>();
    private ServerSessionManager ssm = ServerSessionManager.getServerSessionManager();
    private MessageDispatch md = new MessageDispatch();

    public BasicServerConversation(String name)
    {
        this.name = name;
        registerCommand('C', new ChatCommand());
        registerCommand('R', new RefreshCommand());
    }

    public String getName() { return name; }

    public void join(User user)
    {
        users.add(user);
        sendAll(user, 'J', getType());
    }

    protected void addSeat(String name) { seats.add(new Seat(name)); }
    
    public boolean seatUser(Seat seat, User user)
    {
        seat.setUser(user);
        sendAll(user, 'S', seat.getName());
        return true;
    }
    
    /**
     * @param user - user whose seat is requested
     * @return the seat of the user, or null if the user is not seated.
     */
    public Seat getSeat(User user)
    {
        for(Seat seat: seats)
        {
            if(seat.getUser().equals(user)) return seat;
        }
        return null;
    }

    public Seat getSeat(String name)
    {
        for(Seat seat: seats)
        {
            if(name.equals(seat.getName())) return seat;
        }
        return null;
    }

    public void send(User sender, Seat target, char opcode, String message)
    {
        send(sender, target.getUser(), opcode, message);
    }

    /**
     *
     * @param user
     * @param message message content without sender/session
     */
    public void send(User sender, User target, char opcode, String message)
    {
        send(sender, ssm.getSession(target), opcode, message);
    }

    /**
     * Message format is sender:conversation:content
     *
     * The empty string is reserved for the moderator's name.
     *
     * @param session
     * @param message raw message
     */
    public void send(User sender, ServerSession session, char opcode, String message)
    {
        if (session == null) return;
        session.send(sender.getName() + ":" + getName() + ":" + opcode + message);
    }
    
    public void sendError(User target, String message)
    {
        send(User.MODERATOR, target, 'E', message);
    }

    public void sendAll(User sender, char opcode, String message)
    {
        for(User user: users) send(sender, user, opcode, message);
    }

    public void registerCommand(char opcode, Command command)
    {
        md.registerCommand(opcode, command);
    }

    public void invoke(User user, String opcode, String content)
    {
        if(!users.contains(user))
        {
            sendError(user, "Must join conversation to send messages.");
            return;
        }
        Command com = md.getCommand(opcode);
        if(com == null) 
        {
            sendError(user, "No such command");
            return;
        }
        com.execute(user.getName(), content);
    }

    public String requireAuth()
    {
        return null;
    }
    
    /**
     * Intentionally blank, this is intended to be overridden.
     * It is called as part of the RefreshCommand.
     * @param u
     */
    public void refreshState(User u) {}

    public class ChatCommand extends AbstractCommand implements Command
    {
        public void execute(User user, String content)
        {
            // BASIC OPCODE C = chat
            sendAll(user, 'C', content);
        }
    }
    
    public class RefreshCommand extends AbstractCommand
    {
        @Override
        public void execute(User user, String content)
        {
            for(User u: users)
            {
                send(u, user, 'J', getType());
            }
            refreshState(user);
        }
    }
    
    abstract public class AbstractCommand implements Command
    {
        public void execute(String userName, String content)
        {
            execute(new User(userName), content);
        }
        
        public abstract void execute(User user, String content);
    }

    @Override
    public String getType() { return "Chat"; }
}
