/*
 * Copyright (c) 2009  Mel Nicholson.
 * All Rights Reserved.
 */

package mel.client;

/**
 *
 * @author nicholson
 */
public interface ClientWindowFactory
{
    public ClientWindow makeWindow(String conversationName);
}
