/*
 * Copyright (c) 2009  Mel Nicholson.
 * All Rights Reserved.
 */

package mel.common;

/**
 *
 * @author nicholson
 */
public interface Command
{
    /**
     * A message in the system causes a Command to be activated.  The message
     * format on the wire is "sender:conversation:content" The first character
     * of content will be an opcode that determines which command to run.  The
     * remainder of the content is specific to the command.
     *
     * @param user
     * @param content
     */
    public void execute(String userName, String content);
}
