/*
 * Copyright (c) 2009  Mel Nicholson.
 * All Rights Reserved.
 */

package mel.common;

/**
 *
 * @author nicholson
 */
public interface Conversation
{
    public String getType();
    public void registerCommand(char opcode, Command command);
    public void join(User user);
    public void invoke(User user, String opcode, String content);
    public boolean seatUser(Seat seat, User user);
    public Seat getSeat(String name);

}
