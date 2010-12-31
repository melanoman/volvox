/*
 * Copyright (c) 2009  Mel Nicholson.
 * All Rights Reserved.
 */

package mel.common;

import java.util.HashMap;
import java.util.Map;

/**
 * Simple registry of commands for dispatching messages.  Each conversation
 * should have its own dispatcher so it can register the specific commands
 * for that conversation.  Command instances are specific to a conversation
 * so message dispatchers cannot be shared, even by conversations of the same
 * type.  For example, the ChatCommand knows which window it should display
 * the Chat message in.
 *
 * @author nicholson
 */
public class MessageDispatch
{
    private Map<String,Command> opcode2command = new HashMap<String,Command>();

    public void registerCommand(char opcode, Command command)
    {
        // TODO raise warning for reuse of opcodes or allow multiples?
        opcode2command.put(String.valueOf(opcode), command);
    }

    public Command getCommand(String opcode)
    {
        return opcode2command.get(opcode);
    }

    public Command getCommand(char opcode)
    {
        return getCommand(String.valueOf(opcode));
    }
    
    public void executeCommand(String userName, char opcode, String content)
    {
    	Command c = getCommand(opcode);
    	if(c == null) System.err.println("Unknown opcode: "+opcode); // TODO throw exception
    	else c.execute(userName, content);
    }
}
