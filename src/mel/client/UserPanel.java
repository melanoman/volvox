/* Copyright (c) 2011, Mel Nicholson */

package mel.client;

import java.util.TreeSet;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import mel.common.MessageDispatch;
import mel.common.Command;

public class UserPanel extends JList
{
    private static final long serialVersionUID = 1L;
    private DefaultListModel lm = new DefaultListModel();
    private TreeSet<String> users = new TreeSet<String>();

    public UserPanel(MessageDispatch md)
    {
        setModel(lm);
        md.registerCommand('J', new JoinCommand());
        // TODO add infrastucture for displaying seats and who is in them
        // maybe change this to a JPanel with seats at top and list of others
        // below
    }

    public void join(String userName)
    {
        if(users.add(userName))
        {
            // TODO find the position in the TreeSet to sort the visible list
            lm.addElement(userName);
        }
    }
    
    public class JoinCommand implements Command
    {
        @Override
        public void execute(String userName, String content)
        {
            join(userName);
        }
    }

}
