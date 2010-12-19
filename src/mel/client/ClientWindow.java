/*
 * Copyright (c) 2009  Mel Nicholson.
 * All Rights Reserved.
 */

package mel.client;

/**
 *
 * @author nicholson
 */
public interface ClientWindow
{
    public void handleMessage(String userName, char opcode, String message);
}
