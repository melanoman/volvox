/* Copyright (c) 2011, Mel Nicholson */

package mel.client;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import mel.common.MessageDispatch;
import mel.common.Command;

public class UserPanel extends JList
{
    private static final long serialVersionUID = 1L;
    private DefaultListModel lm = new DefaultListModel();

    public UserPanel(MessageDispatch md)
    {
        setModel(lm);
        md.registerCommand('J', new JoinCommand());
        // TODO add infrastucture for displaying seats and who is in them
        // maybe change this to a JPanel with seats at top and list of others
        // below
    }

    public class JoinCommand implements Command
    {
        @Override
        public void execute(String userName, String content)
        {
            lm.addElement(userName);
        }
    }

}
