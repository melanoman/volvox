/*
 * Copyright (c) 2009  Mel Nicholson.
 * All Rights Reserved.
 */

package mel.server;

import mel.common.Conversation;
import mel.common.User;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import mel.security.Account;
import mel.security.AccountManager;

/**
 * This class hold all the connection information for a login.
 * @author nicholson
 */
public class ServerSession
{
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private ServerConversationManager scm;
    private Account account;
    private User u;
    private ServerSession session;

    ServerSession(Socket s, ServerConversationManager scm)
            throws IOException
    {
        this.scm = scm;
        this.session = this;
        account = null;
        socket = s;
        in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        out = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
        new SessionThread().start();
    }

    /**
     * Send a message, but catch any exceptions.
     */
    public void send(String message)
    {
        out.println(message);
        out.flush();
    }

    public void tryClose(String msg)
    {
        this.account = null;
        try
        {
            if(msg != null) send(""+User.MODERATOR+"::E"+msg);
            socket.close();
        }
        catch(Throwable t)
        {
            //ignore
        }
    }

    /**
     * Message format is user:conversation:content, but the user value is
     * not trusted by the server; only the client.  The server should use
     * the u variable in place of the value in the message.
     *
     * The opcode is the first character of the content
     *
     * @param message
     */
    public void receive(String message)
    {
        String[] args = message.split(":", 3);
        if(args.length != 3 || args[2].length() == 0)
        {
            send(""+User.MODERATOR+"::Esyntax error");
            return;
        }
        if (args[1].isEmpty())
        {
            send(""+User.MODERATOR+"::EConversation not specified");
            return;
        }
        try
        {
            Conversation con = scm.loadConversation(args[1]);
            if(con == null) send(""+User.MODERATOR+"::ENo such conversation");
            con.invoke(u, args[2].substring(0, 1), args[2].substring(1));
        } catch (IOException ex)
        {
            send(""+User.MODERATOR+"::EError loading conversation: "+ex.getMessage());
        }
    }

    public class SessionThread extends Thread
    {
        //TODO clean up session object if exception thrown
        @Override
        public void run()
        {
            try
            {
                String user = in.readLine();
                String passwd = in.readLine();

                Account a = AccountManager.getAccountManager().getAccount(user);
                a.authenticate(passwd);
                account = a;
                u = new User(user);
            }
            catch(IOException ex)
            {
                tryClose("Access Denied");
                return;
            }
            catch(SecurityException ex)
            {   
                tryClose("Access Denied");
                return;
            }
            catch(NullPointerException ex)
            {
                // no such account
                tryClose("Access Denied");
                return;
            }

            ServerSessionManager.getServerSessionManager().resgisterSession(session, u);
            scm.joinConversation("lobby", u);

            while(account != null)
            {
                try
                {
                    receive(in.readLine());
                } catch (IOException ex)
                {
                    tryClose(ex.getMessage());
                    return;
                }
            }
        }
    }
}
