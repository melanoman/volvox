package mel.client;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import mel.common.Command;
import mel.common.MessageDispatch;

public class ChatWindow extends JFrame implements ClientWindow
{
    private static final long serialVersionUID = 1L;
    private final MessageDispatch md = new MessageDispatch();
    private final ChatPanel chat;
    private final UserPanel users = new UserPanel(md);
    private final String convName;

    public ChatWindow(String name)
    {
        super(name);
        convName = name;
        chat = new ChatPanel(md, name);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(chat, BorderLayout.CENTER);
        getContentPane().add(users, BorderLayout.EAST);
        pack();
        setVisible(true);
    }

    @Override
    public void handleMessage(String userName, char opcode, String message)
    {
        // TODO show an error if no such command
        md.executeCommand(userName, opcode, message);
    }

    @Override
    public void init()
    {
        ClientSession.send(convName, 'R', "");
    }
    
    public void registerCommand(char opcode, Command command)
    {
        md.registerCommand(opcode, command);
    }
}
