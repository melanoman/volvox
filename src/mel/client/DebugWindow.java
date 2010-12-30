package mel.client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class DebugWindow extends JFrame
{
    public static DebugWindow singleton = null;
    private JTextField inBox = new JTextField(80);
    
    private static final long serialVersionUID = 1L;
    
    public DebugWindow()
    {
        super();
        getContentPane().setLayout(new BorderLayout());
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
        System.out.println(s);
    }
    
    class InputListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            ClientSession.debugSend(inBox.getText());
            System.out.println("-->"+inBox.getText());
            inBox.setText("");
        }
    }
}
