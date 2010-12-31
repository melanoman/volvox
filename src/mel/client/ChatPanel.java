package mel.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import mel.common.Command;
import mel.common.MessageDispatch;

public class ChatPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	private JTextField input = new JTextField(40);
	private JTextArea display = new JTextArea("", 40, 10);
	
    public ChatPanel(MessageDispatch md)
    {
    	md.registerCommand('C', new ChatCommand());
    	setLayout(new BorderLayout());
    	display.setEditable(false);
    	JScrollPane scroll = new JScrollPane(display);
    	scroll.setPreferredSize(new Dimension(200,50));
    	add(display, BorderLayout.CENTER);
    	add(input, BorderLayout.SOUTH);
    	input.addActionListener(new ChatSender());
    }
    
    class ChatSender implements ActionListener
    {
		@Override
		public void actionPerformed(ActionEvent e)
		{
			// TODO report back to window
		}
    }
	
	public class ChatCommand implements Command 
	{
		@Override
		public void execute(String userName, String content)
		{
			display.append("["+userName+"] "+content);
			display.setCaretPosition(display.getText().length()-1);
		}	
	}
}
