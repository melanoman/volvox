/*
 * Copyright (c) 2011, Mel Nicholson
 */

package mel.client;

import javax.swing.JList;
import mel.common.MessageDispatch;
import mel.common.Command;

public class UserPanel extends JList
{
	private static final long serialVersionUID = 1L;

	public UserPanel(MessageDispatch md)
	{
		md.registerCommand('J', new JoinCommand());
	}
	
	public class JoinCommand implements Command
	{
		@Override
		public void execute(String userName, String content)
		{
			// TODO add a user name to the list
		}	
	}

}
