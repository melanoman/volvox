package mel.client;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import mel.server.ServerMain;

public class LoginDialog extends JDialog
{
    private static final long serialVersionUID = 1L;
    private JTextField nameBox = new JTextField(20);
    private JTextField pwBox = new JPasswordField(20);
    private JTextField ipBox = new JTextField("localhost", 20);
    private JButton okButton = new JButton("OK");
    private JButton cancelButton = new JButton("Cancel");
    
    public LoginDialog()
    {
        setLayout(new GridBagLayout());
        GridBagConstraints startline = new GridBagConstraints();
        GridBagConstraints endline = new GridBagConstraints();
        startline.anchor = GridBagConstraints.EAST;
        endline.gridwidth = GridBagConstraints.REMAINDER;
        endline.fill = GridBagConstraints.HORIZONTAL;
        endline.weightx = 1;
        
        add(new JLabel("Username:"), startline);
        add(nameBox, endline);
        add(new JLabel("Password:"), startline);
        add(pwBox, endline);
        add(new JLabel("Host Address:"), startline);
        add(ipBox, endline);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        
        add(buttonPanel, endline);
        pack();
        setResizable(false);
        
        okButton.addActionListener(new OkListener());
        cancelButton.addActionListener(new CancelListener());
    }
    
    class OkListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent evt)
        {
            try
            {
                Socket socket = new Socket(ipBox.getText(), ServerMain.port);
                new ClientSession(nameBox.getText(), pwBox.getText(), socket);
                setVisible(false);
                if(Main.debug) Main.createDebugWindow();
                dispose();
            }
            catch (UnknownHostException e)
            {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Unknown Host", JOptionPane.ERROR_MESSAGE);
            }
            catch (IOException e)
            {
                JOptionPane.showMessageDialog(null, e.getMessage(), "IOException", JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
        }
    }
    
    class CancelListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent evt)
        {
            System.exit(0);
        }
    }
}
