package mel.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class DebugWindow extends JFrame
{
    public static DebugWindow singleton = null;
    private JTextArea display = new JTextArea("", 40, 20);
    private JTextField inBox = new JTextField(80);
    
    private static final long serialVersionUID = 1L;
    
    public DebugWindow()
    {
        super();
        if(singleton != null) throw new Error("Only one debug window allowed");
        
        JScrollPane scroll = new JScrollPane(display);
        scroll.setPreferredSize(new Dimension(300,200));
        getContentPane().setLayout(new BorderLayout());
        display.setEditable(false);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        getContentPane().add(scroll, BorderLayout.CENTER);
        inBox.addActionListener(new InputListener());
        getContentPane().add(inBox, BorderLayout.SOUTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }
    
    public static void makeDebugWindow()
    {
        singleton = new DebugWindow();
    }
    
    public static void recieve(String s)
    {
        if(singleton == null) return;
        singleton.display.append(s+"\n");
        singleton.display.setCaretPosition(singleton.display.getText().length()-1);
    }
    
    class InputListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
        	String s = inBox.getText();
            ClientSession.debugSend(s);
            singleton.display.append("-->"+s+"\n");
            singleton.display.setCaretPosition(singleton.display.getText().length()-1);
            inBox.setText("");
        }
    }
}
