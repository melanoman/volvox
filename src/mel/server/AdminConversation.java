/*
 * Copyright (c) 2009  Mel Nicholson.
 * All Rights Reserved.
 */

package mel.server;

import mel.common.User;
import mel.security.Account;
import mel.security.AccountManager;

/**
 * Client Codes: E= Error, cdgpa = success
 * @author charidan
 */
public class AdminConversation extends BasicServerConversation
{
    public AdminConversation(String name)
    {
        super(name);
        registerCommand('c', new CreateCommand());
        registerCommand('d', new DeleteCommand());
        registerCommand('g', new GetCommand());
        registerCommand('p', new SetPasswordCommand());
    }

    public class CreateCommand extends AbstractCommand
    {
        /**
         * @param user The user creating the account
         * @param content The name and password of the account, separated by a colon.
         */
        public void execute(User user, String content)
        {
            AccountManager accMan = AccountManager.getAccountManager();
            String[] args = content.split(":", 2);
            if(args.length != 2)
            {
                sendError(user, "Syntax Error in createAccount");
                return;
            }
            try
            {
                accMan.createAccount(args[0], args[1]);
            } catch (SecurityException ex)
            {
                sendError(user, ex.getMessage());
                return;
            }
            reply(user, 'c', args[0]);
        }
    }

    public class GetCommand extends AbstractCommand
    {
        /**
         * @param user The user asking the question
         * @param content The name of the account to fetch
         */
        public void execute(User user, String content)
        {
            AccountManager accMan = AccountManager.getAccountManager();
            Account acc = accMan.getAccount(content);
            reply(user, 'g', acc.getName()+":"+acc.getAuth().toString());
        }
    }
    

    public class DeleteCommand extends AbstractCommand
    {
        /**
         * @param user The user performing the command
         * @param content The account name to be deleted
         */
        public void execute(User user, String content)
        {
            AccountManager accMan = getAccountManager();
            try
            {
                accMan.deleteAccount(content);
            }
            catch(SecurityException ex)
            {
                sendError(user, ex.getMessage());
                return;
            }
            reply(user, 'd', content);
        }
    }

    public class SetPasswordCommand extends AbstractCommand
    {
        /**
         * @param user The user performing the command
         * @param content The name and password of the account, separated by a colon.
         */
        public void execute(User user, String content)
        {
            String[] args = content.split(":", 2);
            if(args.length != 2)
            {
                sendError(user, "Syntax Error in setPassword");
                return;
            }
            AccountManager accMan = getAccountManager();
            try
            {
                accMan.setPassword(args[0], args[1]);
            }
            catch(SecurityException ex)
            {
                sendError(user, ex.getMessage());
                return;
            }
            reply(user, 'p', args[0]);
        }
    }

    public class addAuthCommand extends AbstractCommand
    {   
        /**
         * @param user The user performing
         * @param content The name of the account and feature to add, separated by a colon.
         */
        public void execute(User user, String content)
        {
            // TODO RICHARD add auth(String name, String auth)
            // TODO RICHARD register as 'a'
        }
    }

    private static AccountManager getAccountManager()
    {
        return AccountManager.getAccountManager();
    }

    // only admins can join this conversation
    @Override
    public String requireAuth() { return "admin"; }

    private void reply(User u, char opcode, String message)
    {
        send(User.MODERATOR, u, opcode, message);
    }

    @Override
    public String getType() { return "Admin"; }
}


