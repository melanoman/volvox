/**
 * copyright (c)2011 Mel Nicholson
 */
package mel.client;


import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;

/*
 * Copyright (c) 2009  Mel Nicholson.
 * All Rights Reserved.
 */

/**
 *
 * @author nicholson
 */
public class ClientWindowManager
{
    private static Map<String,ClientWindow> name2window = new HashMap<String,ClientWindow>();
    private static Map<String,ClientWindowFactory> name2factory = new HashMap<String,ClientWindowFactory>();

    public static void register(String conversationType, ClientWindowFactory factory)
    {
        name2factory.put(conversationType, factory);
    }

    public static ClientWindow makeWindow(String conversationName, String windowType)
    {
        ClientWindowFactory cwf = name2factory.get(windowType);
        if(cwf==null)
        {
            JOptionPane.showMessageDialog(null, "ERROR: unknown conversation type: "+windowType, "ERROR: unknown conversation type", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        ClientWindow result = cwf.makeWindow(conversationName);
        name2window.put(conversationName, result);
        result.init();
        return result;
    }

    public static ClientWindow get(String conversationName)
    {
        return name2window.get(conversationName);
    }
}
