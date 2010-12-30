package mel.client;

import java.awt.BorderLayout;
import javax.swing.JFrame;

public class ChatWindow extends JFrame implements ClientWindow
{
	private static final long serialVersionUID = 1L;
	private ChatPanel chat = new ChatPanel();
	private UserPanel users = new UserPanel();
	
	public ChatWindow(String name)
	{
		super(name);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(chat, BorderLayout.CENTER);
        getContentPane().add(users, BorderLayout.EAST);
	}
	
	@Override
	public void handleMessage(String userName, char opcode, String message)
	{
		// TODO implement either a listener patter or a big switch
	}

}
