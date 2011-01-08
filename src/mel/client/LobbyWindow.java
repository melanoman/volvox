package mel.client;

import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import mel.common.Command;
import mel.common.MessageDispatch;

public class LobbyWindow extends JFrame implements ClientWindow
{
    private static final long serialVersionUID = 1L;
    private final MessageDispatch md = new MessageDispatch();
    private final ChatPanel chat;
    private final UserPanel users = new UserPanel(md);
    private final ConversationPanel convs = new ConversationPanel(md);
    
    public LobbyWindow(String name)
    {
        super(name);
        chat = new ChatPanel(md, name);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(chat, BorderLayout.CENTER);
        getContentPane().add(users, BorderLayout.EAST);
        getContentPane().add(convs, BorderLayout.WEST);
        chat.setBorder(BorderFactory.createEtchedBorder());
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
        
    }
    
    public void registerCommand(char opcode, Command command)
    {
        md.registerCommand(opcode, command);
    }
}
