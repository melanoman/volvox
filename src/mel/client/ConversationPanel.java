package mel.client;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import mel.common.Command;
import mel.common.MessageDispatch;

public class ConversationPanel extends JList
{
    private static final long serialVersionUID = 1L;
    private DefaultListModel lm = new DefaultListModel();

    public ConversationPanel(MessageDispatch md)
    {
        setModel(lm);
        md.registerCommand('A', new AddCommand());
    }
    
    public class AddCommand implements Command
    {
        /**
         * Receives notice that a conversation was created and adds it to the list of conversations.
         * @param userName name of the user creating the conversation
         * @param content name of the conversation created
         */
        @Override
        public void execute(String userName, String content)
        {
            lm.addElement(content);
        }
    }
}
