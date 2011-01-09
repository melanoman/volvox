package mel.client;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import mel.common.Command;
import mel.common.MessageDispatch;

public class ConversationPanel extends JList
{
    private static final long serialVersionUID = 1L;
    private DefaultListModel lm = new DefaultListModel();
    private final String convName;

    public ConversationPanel(MessageDispatch md, String conversationName)
    {
        setModel(lm);
        convName = conversationName;
        md.registerCommand('A', new AddCommand());
        addMouseListener(new ClickCatcher());
    }

    public class ClickCatcher extends MouseAdapter
    {
        public void mouseClicked(MouseEvent e)
        {
            if (e.getClickCount() == 2)
            {
                int index = locationToIndex(e.getPoint());
                System.out.println("Double clicked on Item " + index);
                String conv = ""+lm.elementAt(index); //data in form <name:type>
                String[] target = conv.split(":"); //two strings <name> and <type>
                ClientSession.send(convName, 'J', target[0]); //send only the <name> string
            }
        }
    }

    public class AddCommand implements Command
    {
        /**
         * Receives notice that a conversation was created and adds it to the
         * list of conversations.
         * 
         * @param userName
         *            name of the user creating the conversation
         * @param content
         *            name of the conversation created
         */
        @Override
        public void execute(String userName, String content)
        {
            lm.addElement(content);
        }
    }
}
