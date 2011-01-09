/* Copyright (c) 2011, Mel Nicholson */

package mel.client;

import javax.swing.JList;
import mel.common.MessageDispatch;
import mel.common.Command;
import mel.common.SortedListModel;

public class UserPanel extends JList
{
    private static final long serialVersionUID = 1L;
    private SortedListModel<String> lm = new SortedListModel<String>();

    public UserPanel(MessageDispatch md)
    {
        setModel(lm);
        md.registerCommand('J', new JoinCommand());
        // TODO add infrastucture for displaying seats and who is in them
        // maybe change this to a JPanel with seats at top and list of others
        // below -- maybe make that a SeatPanel instead
    }

    public void join(String userName)
    {
        lm.add(userName);
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
