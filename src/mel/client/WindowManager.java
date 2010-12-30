/**
 * copyright (c)2011 Mel Nicholson
 */
package mel.client;


import java.util.HashMap;
import java.util.Map;

/*
 * Copyright (c) 2009  Mel Nicholson.
 * All Rights Reserved.
 */

/**
 *
 * @author nicholson
 */
public class WindowManager
{
    private static Map<String,ClientWindow> name2window = new HashMap<String,ClientWindow>();
    private static Map<String,ClientWindowFactory> name2factory = new HashMap<String,ClientWindowFactory>();

    public static void register(String conversationType, ClientWindowFactory factory)
    {
        name2factory.put(conversationType, factory);
    }

    public static ClientWindow makeWindow(String conversationName, String conversationType)
    {
        ClientWindowFactory cwf = name2factory.get(conversationType);
        if(cwf==null)
        {
            //TODO Richard make an error dialog and delete debug line
            //showError unknown conversation type
            System.out.println("DEBUG: unknown conversation type");
            return null;
        }
        ClientWindow result = cwf.makeWindow(conversationName);
        name2window.put(conversationName, result);
        return result;
    }

    public static ClientWindow get(String conversationName)
    {
        return name2window.get(conversationName);
    }

}
